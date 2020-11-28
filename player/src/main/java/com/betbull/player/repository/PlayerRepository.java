package com.betbull.player.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.betbull.player.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	
	List<Player> findByNameContainingIgnoreCase(String name);
	List<Player> findByIsValid(Boolean isValid);
	List<Player> findByTeamNameIsNullAndIsValid(Boolean isValid);
	List<Player> findByTeamName(String name);
	Boolean existsByCitizenshipNumberAndNation(Long citizenshipNumber, String nation);
	@Transactional
	@Modifying
	@Query("update Player p set p.isValid=:isValid, p.contract=null, p.teamName=null where p.id=:id")
	void retirePlayerById(@Param(value = "id") Long id, @Param(value = "isValid") Boolean isValid);

}
