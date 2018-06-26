package com.common.entity.login;

import java.io.Serializable;

public class UserOrg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 10871947532527916L;

	private User user;
	
	private Organization org;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
}
