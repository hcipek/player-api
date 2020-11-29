package com.betbull.player.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PlayerRequestDto {
	
	private String name;
	private String teamName;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date contractBeginDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date contractEndDate;
	private String position;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date birthDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date careerBeginDate;
	private String nation;
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	private BigDecimal attackPower;
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	private BigDecimal defencePower;
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	private BigDecimal physicalPower;
	private Long citizenshipNumber;
	
	public PlayerRequestDto() {
		
	}

}
