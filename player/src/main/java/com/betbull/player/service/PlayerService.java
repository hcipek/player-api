package com.betbull.player.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.betbull.player.exception.PlayerException;
import com.betbull.player.model.Contract;
import com.betbull.player.model.Player;
import com.betbull.player.model.PlayerRequest;
import com.betbull.player.model.PlayerRequestDto;
import com.betbull.player.model.DefaultPlayerResponse;
import com.betbull.player.model.MultiPlayerResponse;
import com.betbull.player.repository.PlayerRepository;
import com.betbull.player.util.ResponseCodesUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlayerService {
	
	@Autowired
    private PlayerRepository playerRepository;
	
	@Autowired
	private ContractService contractService;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public DefaultPlayerResponse createPlayer(PlayerRequest request) {
		log.debug("Create player started...");
		try {
			for(PlayerRequestDto dto : request.getPlayerList()) {
				if(playerRepository.existsByCitizenshipNumberAndNation(dto.getCitizenshipNumber(), dto.getNation())) {
					log.error("{} with {} citizenship number already exists in country with code {}",dto.getName(), dto.getCitizenshipNumber(), dto.getNation());
					throw new PlayerException(ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.ALREADY_EXISTS), ResponseCodesUtil.ALREADY_EXISTS);
				}
			
				Player player = new Player(dto.getName(), dto.getTeamName(), dto.getBirthDate(), dto.getPosition(), 
						dto.getAttackPower(), dto.getDefencePower(), dto.getPhysicalPower(), 
						dto.getNation(), dto.getCareerBeginDate(), dto.getCitizenshipNumber());
				player = playerRepository.save(player);
				
				Contract contract = contractService.createContract(dto, player.getId());
				player.setContract(contract);
				player = playerRepository.save(player);
			}
			return createResponseForSuccess();
		} catch (PlayerException e) {
			return createResponseForPlayerException(e);
		} catch (Exception e) {
			return createResponseForUnknownError(e);
		}
	}
	
	public MultiPlayerResponse getPlayerByName(String name) {
		log.debug("Find player with name : {}", name);
		return createMultiResponseForSuccess(playerRepository.findByNameContainingIgnoreCase(name));
	}
	
	public MultiPlayerResponse getPlayerById(Long id) {
		log.debug("Find player with id : {}", id);
		try {
			Optional<Player> optional = playerRepository.findById(id);
			if(optional.isEmpty())
				throw new PlayerException(ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.NOT_EXISTS), ResponseCodesUtil.NOT_EXISTS);
			return createMultiResponseForSuccess(Collections.singletonList(optional.get()));
		} catch (PlayerException e) {
			return createMultiResponseForPlayerException(e);
		}
	}
	
	public MultiPlayerResponse getAllPlayers() {
		log.debug("Find all players...");
		return createMultiResponseForSuccess(playerRepository.findAll());
	}
	
	public MultiPlayerResponse getActivePlayers() {
		log.debug("Find active players...");
		return createMultiResponseForSuccess(playerRepository.findByIsValid(Boolean.TRUE));
	}
	
	public MultiPlayerResponse getRetiredPlayers() {
		log.debug("Find retired players...");
		return createMultiResponseForSuccess(playerRepository.findByIsValid(Boolean.FALSE));
	}
	
	public MultiPlayerResponse getPlayersWithoutTeam() {
		log.debug("Find retired players...");
		return createMultiResponseForSuccess(playerRepository.findByTeamNameIsNullAndIsValid(Boolean.TRUE));
	}
	
	public MultiPlayerResponse getPlayersByTeam(String name) {
		log.debug("Find players with team {}", name);
		return createMultiResponseForSuccess(playerRepository.findByTeamName(name));
	}
	
	public DefaultPlayerResponse retirePlayerById(Long id) {
		log.debug("Retiring player with id : {}", id);
		try {
			Optional<Player> player = playerRepository.findById(id);
			if(player.isEmpty()) {
				throw new PlayerException(ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.NOT_EXISTS), ResponseCodesUtil.NOT_EXISTS);
			}
			contractService.passivateContract(player.get().getContract());
			playerRepository.retirePlayerById(id, Boolean.FALSE);
			return createResponseForSuccess();
		} catch (PlayerException e) {
			return createResponseForPlayerException(e);
		} catch (Exception e) {
			return createResponseForUnknownError(e);
		}
		
	}
	
	public DefaultPlayerResponse deletePlayerById(Long id) {
		log.debug("Delete player with id : {}", id);
		try {
			if(!playerRepository.existsById(id)) {
				throw new PlayerException(ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.NOT_EXISTS), ResponseCodesUtil.NOT_EXISTS);
			}
			playerRepository.deleteById(id);
			contractService.deleteAllContractsOfDeletedPlayer(id);
			return createResponseForSuccess();
		} catch (PlayerException e) {
			return createResponseForPlayerException(e);
		} catch (Exception e) {
			return createResponseForUnknownError(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public DefaultPlayerResponse deleteAll() {
		log.info("Deleting All Players");
		playerRepository.deleteAll();
		contractService.deleteAll();
		return createResponseForSuccess();
	}
	
	public DefaultPlayerResponse unAssignPlayerById(Long id) {
		log.info("Unassigning player with id");
		try {
			Optional<Player> optional = playerRepository.findById(id);
			if(optional.isEmpty())
				throw new PlayerException(ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.NOT_EXISTS), ResponseCodesUtil.NOT_EXISTS);
			Player player = optional.get();
			if(!player.getIsValid())
				throw new PlayerException(ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.RETIRED), ResponseCodesUtil.RETIRED);
			if(player.getTeamName() == null)
				throw new PlayerException(ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.HAVE_NO_TEAM), ResponseCodesUtil.HAVE_NO_TEAM);
			contractService.passivateContract(player.getContract());
			player = prepareForUpdate(player);
			player.setTeamName(null);
			player.setContract(null);
			playerRepository.save(player);
			return createResponseForSuccess();
		} catch (PlayerException e) {
			return createResponseForPlayerException(e);
		} catch (Exception e) {
			return createResponseForUnknownError(e);
		}
	}
	
	private DefaultPlayerResponse createResponseForSuccess() {
		return new DefaultPlayerResponse(ResponseCodesUtil.SUCCESS, ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.SUCCESS));
	}
	
	private DefaultPlayerResponse createResponseForPlayerException(PlayerException e) {
		return new DefaultPlayerResponse(e.getErrorCode(), e.getMessage());
	}
	
	private DefaultPlayerResponse createResponseForUnknownError(Exception e) {
		log.error("Something went wrong... exception message is {}", e.getMessage());
		return new DefaultPlayerResponse(ResponseCodesUtil.COMMON_UNKNOWN_ERROR, ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.COMMON_UNKNOWN_ERROR));
	}
	
	private MultiPlayerResponse createMultiResponseForSuccess(List<Player> playerList) {
		return new MultiPlayerResponse(ResponseCodesUtil.SUCCESS, ResponseCodesUtil.getDescriptionByCode(ResponseCodesUtil.SUCCESS), playerList);
	}
	
	private MultiPlayerResponse createMultiResponseForPlayerException(PlayerException e) {
		return new MultiPlayerResponse(e.getErrorCode(), e.getMessage(), new ArrayList<Player>());
	}
	
	private Player prepareForUpdate(Player player) {
		player.setLastModifiedDate(new Date());
		player.setVersion(player.getVersion() + 1);
		return player;
	}
	
//	private List<PlayerResponseDto> convertListToRequestDtoList(List<Player> playerList) {
//		List<PlayerResponseDto> dtos = new ArrayList<PlayerResponseDto>();
//		playerList.forEach(e -> dtos.add(convertToRequestDto(e)));
//		return dtos;
//	}
//	
//	private PlayerResponseDto convertToRequestDto(Player player) {
//		PlayerResponseDto dto = new PlayerResponseDto();
//		dto.setAttackPower(player.getAttackPower());
//		dto.setBirthDate(player.getBirthDate());
//		dto.setCareerBeginDate(player.getCareerBeginDate());
//		dto.setDefencePower(player.getDefencePower());
//		dto.setId(player.getId());
//		dto.setName(player.getName());
//		dto.setNation(player.getNation());
//		dto.setOverallPower(player.getOverallPower());
//		dto.setPhysicalPower(player.getPhysicalPower());
//		dto.setTeamName(player.getTeamName());
//		dto.setPosition(player.getPosition());
//		return dto;
//	}

}
