package com.betbull.player.util;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResponseCodesUtil {

	public static Integer SUCCESS = 0;
	public static Integer ALREADY_EXISTS = 700;
	public static Integer ALREADY_ACTIVE = 701;
	public static Integer ALREADY_PASSIVE = 702;
	public static Integer NOT_EXISTS = 703;
	public static Integer RETIRED = 704;
	public static Integer HAVE_NO_TEAM = 705;
	public static Integer COMMON_UNKNOWN_ERROR = 900;
	
	private static Map<Integer, String> responseCodesMap = Stream.of(
			new AbstractMap.SimpleEntry<>(SUCCESS, "SUCCESS"),
			new AbstractMap.SimpleEntry<>(ALREADY_EXISTS, "PLAYER_ALREADY_EXISTS"), 
			  new AbstractMap.SimpleEntry<>(ALREADY_ACTIVE, "PLAYER_ALREADY_ACTIVE"), 
			  new AbstractMap.SimpleEntry<>(ALREADY_PASSIVE, "PLAYER_ALREADY_RETIRED"),
			  new AbstractMap.SimpleEntry<>(NOT_EXISTS, "PLAYER_NOT_EXISTS"),
			  new AbstractMap.SimpleEntry<>(RETIRED, "PLAYER_IS_RETIRED"),
			  new AbstractMap.SimpleEntry<>(HAVE_NO_TEAM, "PLAYER_HAVE_NO_TEAM"),
			  new AbstractMap.SimpleEntry<>(COMMON_UNKNOWN_ERROR, "COMMON_UNKNOWN_ERROR"))
			  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
	public static String getDescriptionByCode(Integer code) {
		return responseCodesMap.get(code);
	}
}
