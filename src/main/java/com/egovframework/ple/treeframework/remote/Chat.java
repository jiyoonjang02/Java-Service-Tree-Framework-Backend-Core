/*
 * @author Dongmin.lee
 * @since 2023-03-13
 * @version 23.03.13
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.egovframework.ple.treeframework.remote;

import com.egovframework.ple.treeframework.model.Global;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
@RemoteProxy(creator = SpringCreator.class, name = "Chat")
@Service
public class Chat {

	public Chat() {
		Global.chat = this;
	}

	@RemoteMethod
	public String sendMessage(final String message) {
		final String username;
		try {
			username = (String) WebContextFactory.get().getScriptSession()
					.getAttribute(Global.USERNAME);
		} catch (Exception e) {
			return Global.ERROR;
		}

		if (null == username) {
			return Global.ERROR;
		}

		final String time = time();
		Browser.withAllSessions(new Runnable() {
			@Override
			public void run() {
				ScriptSessions.addFunctionCall("addMessage", username, time, message);
			}
		});

		return Global.SUCCESS;
	}

	@RemoteMethod
	public static Set<User> getOnlineSet() {
		return Global.onlineSet;
	}

	@RemoteMethod
	public void updateOnlineList() {
		Browser.withCurrentPage(new Runnable() {
			@Override
			public void run() {
				ScriptSessions.addFunctionCall("updateOnlineList", Global.onlineSet);
			}
		});
	}

	@RemoteMethod
	public String login(final String username) {
		if (Global.onlineSet.contains(new User(username)) || "".equals(username)) {
			return Global.ERROR;
		} else {
			Global.onlineSet.add(new User(username, time()));
			updateOnlineList();
			WebContextFactory.get().getScriptSession().setAttribute(Global.USERNAME, username);
			return Global.SUCCESS;
		}
	}

	@RemoteMethod
	public String logout(final String username) {
		if (!Global.onlineSet.contains(new User(username))) {
			return Global.ERROR;
		} else {
			Global.onlineSet.remove(new User(username));
			updateOnlineList();
			WebContextFactory.get().getScriptSession().invalidate();
			return Global.SUCCESS;
		}
	}

	@RemoteMethod
	private String time() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	@RemoteMethod
	public List<String> getOptionList() {
		List<String> option = new LinkedList<>();
		for (int i = 0; i < 5; i++)
			option.add("field_" + i);
		return option;
	}

}
