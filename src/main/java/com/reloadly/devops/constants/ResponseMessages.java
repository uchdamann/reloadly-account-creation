package com.reloadly.devops.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseMessages {
	SUCCESSFUL("00", "Operation successful"),
	FAILURE("99", "Operation failed"),
	INVALIDFIELDS("81", "Selected field(s) has/have invalid value(s) in payload"),
	USERNAMEMISMATCH("73", "User mismatch"),
	DATAINTEGRITYERROR("30", "Data integrity exception"),
	OPTIMISTICLOCK("419", "Concurrent update on record"),
	EXTERNAL_SERVER_UNAVAILABLE("420", "External server cannot be reached");

	private String code;
	private String message;
}