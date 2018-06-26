package com.common.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.common.iface.ILoginPass;

public class ShiroLoginPass implements ILoginPass{

	@Override
	public Boolean checkPassword(String userId, String password) {
		Subject subject;
		subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(userId, password,false)); 
		return subject.isAuthenticated();
	}

}
