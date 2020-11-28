package com.betbull.player.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PlayerException extends RuntimeException{
	
	private static final long serialVersionUID = 2599617287426807584L;
	
	private String message;
	private int errorCode;

}
