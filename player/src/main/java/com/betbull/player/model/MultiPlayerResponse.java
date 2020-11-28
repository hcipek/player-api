package com.betbull.player.model;

import java.util.List;

import com.betbull.player.model.base.BasePlayerResponse;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MultiPlayerResponse extends BasePlayerResponse {

	private List<Player> playerList;
	
	public MultiPlayerResponse() {
		super();
	}
	
	public MultiPlayerResponse(int code, String desc, List<Player> playerList) {
		super(code, desc);
		this.playerList=playerList;
	}
}
