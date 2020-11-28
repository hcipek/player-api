package com.betbull.player.model;

import com.betbull.player.model.base.BasePlayerResponse;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SinglePlayerResponse extends BasePlayerResponse {

	private Player player;
}
