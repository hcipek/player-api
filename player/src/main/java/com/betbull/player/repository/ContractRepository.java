package com.betbull.player.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.betbull.player.model.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

	@Transactional
	void deleteByPlayerId(Long playerId);
}
