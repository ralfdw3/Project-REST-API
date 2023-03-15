package com.api.webvote.v1.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VotoEnum {
	
	SIM ("SIM"), 
	NAO ("NAO");
	
	private final String value;

	VotoEnum(String voto) {
		this.value = voto;
	}
	
	@JsonValue
	public String getValue() {
		return value;
	}

}
