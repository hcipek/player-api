package com.betbull.player.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PlayerResponseDto {
	
	private Long id;
	private String name;
	private String nation;
	private String teamName;
	private Date careerBeginDate;
	private Date birthDate;
	private BigDecimal attackPower;
	private BigDecimal defencePower;
	private BigDecimal physicalPower;
	private BigDecimal overallPower;
	private String position;
	
	public PlayerResponseDto() {
		
	}

}
