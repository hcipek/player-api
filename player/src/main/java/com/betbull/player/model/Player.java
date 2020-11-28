package com.betbull.player.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.betbull.player.model.base.BaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "player")
public class Player extends BaseModel{
	
	@Column(name = "team_name")
	private String teamName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date")
	private Date birthDate;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "attack_power")
	private BigDecimal attackPower;
	
	@Column(name = "defence_power")
	private BigDecimal defencePower;
	
	@Column(name = "physical_power")
	private BigDecimal physicalPower;
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "overall_power")
	private BigDecimal overallPower;

	@JoinColumn(name = "contract_id")
    @OneToOne
    private Contract contract;
	
	@Column(name = "nation")
	private String nation;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "career_begin_date")
	private Date careerBeginDate;
	
	@Column(name = "citizenship_number")
	private Long citizenshipNumber; 
	
	public Player() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Player(String name) {
		super(name);
	}

	public Player(String name, String teamName, Date birthDate, String position, 
			BigDecimal attackPower, BigDecimal defencePower, BigDecimal physicalPower,
			String nation, Date careerBeginDate, Long citizenshipNumber) {
		super(name);
		this.teamName = teamName;
		this.birthDate = birthDate;
		this.position = position;
		this.attackPower = attackPower != null ? attackPower : BigDecimal.ZERO;
		this.defencePower = defencePower != null ? defencePower : BigDecimal.ZERO;
		this.physicalPower = physicalPower != null ? physicalPower : BigDecimal.ZERO;
		overallPowerCalculator();
		this.nation = nation;
		this.careerBeginDate = careerBeginDate;
		this.citizenshipNumber = citizenshipNumber;
	}
	
	private void overallPowerCalculator() {
		BigDecimal percent25 = BigDecimal.valueOf(0.25);
		BigDecimal percent50 = BigDecimal.valueOf(0.5);
		BigDecimal percent125 = BigDecimal.valueOf(1.25);
		BigDecimal percent150 = BigDecimal.valueOf(1.5);
		BigDecimal percent300 = BigDecimal.valueOf(3);
		
		switch(this.position) {
			case "GK" : 
				this.overallPower = this.attackPower.multiply(percent25)
						.add(this.defencePower.multiply(percent150))
						.add(this.physicalPower.multiply(percent125))
						.divide(percent300, 2, RoundingMode.CEILING);
				break;
			case "DF" :
				this.overallPower = this.attackPower.multiply(percent50)
						.add(this.defencePower.multiply(percent125))
						.add(this.physicalPower.multiply(percent125))
						.divide(percent300, 2, RoundingMode.CEILING);
				break;
			case "FW" :
				this.overallPower = this.attackPower.multiply(percent150)
						.add(this.defencePower.multiply(percent25))
						.add(this.physicalPower.multiply(percent125))
						.divide(percent300, 2, RoundingMode.CEILING);
				break;
			default :
				this.overallPower = this.attackPower
						.add(this.defencePower)
						.add(this.physicalPower)
						.divide(percent300, 2, RoundingMode.CEILING);
			
		}
	}
	
	public void setAttackPower(BigDecimal bd) {
		this.attackPower = bd;
		overallPowerCalculator();
	}
	
	public void setDefencePower(BigDecimal bd) {
		this.attackPower = bd;
		overallPowerCalculator();
	}
	
	public void setPhysicalPower(BigDecimal bd) {
		this.attackPower = bd;
		overallPowerCalculator();
	}
}
