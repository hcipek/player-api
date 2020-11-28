package com.betbull.player.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.betbull.player.model.base.BaseModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "contract")
public class Contract extends BaseModel {

    @Column(name = "player_id")
	private Long playerId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "begin_date")
	private Date beginDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "team_name")
	private String teamName;
	
	@Column(name = "fee_amount")
	private BigDecimal feeAmount;
	
	public Contract() {
		// TODO Auto-generated constructor stub
	}

	public Contract(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Contract(String name, Long playerId, Date beginDate, Date endDate, BigDecimal feeAmount, String teamName) {
		super(name);
		this.playerId = playerId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.feeAmount = feeAmount;		
		this.teamName = teamName;
	}

}
