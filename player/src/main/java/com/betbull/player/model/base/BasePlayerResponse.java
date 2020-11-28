package com.betbull.player.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public abstract class BasePlayerResponse {
	
	private int resultCode;
    private String description;

    public BasePlayerResponse() {
    	
    }
}
