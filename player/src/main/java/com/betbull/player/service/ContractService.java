package com.betbull.player.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.betbull.player.model.Contract;
import com.betbull.player.model.PlayerRequestDto;
import com.betbull.player.repository.ContractRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContractService {
	
	@Autowired
    private ContractRepository contractRepository;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Contract createContract(PlayerRequestDto dto, Long playerId) {
		
		log.debug("Create contract of player started...");
		
		String contractName = "Contract of " + dto.getName();
		
		Contract contract = new Contract(contractName, playerId, dto.getContractBeginDate(), dto.getContractEndDate(), null, dto.getTeamName());
		return contractRepository.save(contract);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void passivateContract(Contract contract) {
		
		log.info("{} is terminating...", contract.getName());
		contract.setIsValid(Boolean.FALSE);
		contract.setLastModifiedDate(new Date());
		contract.setVersion(contract.getVersion() + 1);
		contract.setName("EXPIRED : ".concat(contract.getName()));
		
		contractRepository.save(contract);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAllContractsOfDeletedPlayer(Long playerId) {

		log.info("Contracts of player with id : {} are deleting...", playerId);
		contractRepository.deleteByPlayerId(playerId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAll() {

		log.info("Deleting all contracts");
		contractRepository.deleteAll();
	}

}
