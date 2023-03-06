package com.egovframework.ple.coreframework.springdwr.util;

import com.egovframework.ple.coreframework.springdwr.domain.User;
import com.egovframework.ple.coreframework.springdwr.service.Chat;

import java.util.LinkedHashSet;
import java.util.Set;

public class Global {

	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String USERNAME = "username";
	public static final Set<User> onlineSet = new LinkedHashSet<User>();
	public static Chat chat;
}
