package com.betbull.player.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PlayerRequest{
	
	List<PlayerRequestDto> playerList;
	
	public PlayerRequest() {
		
	}
	

}
