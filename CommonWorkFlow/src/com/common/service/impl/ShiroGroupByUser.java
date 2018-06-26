package com.common.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.common.entity.login.Role;
import com.common.entity.login.User;
import com.common.iface.IGroupByUser;

public class ShiroGroupByUser implements IGroupByUser {

	@Override
	public List<?> findGroupsByUser(String userId) {
		User user;
		List<Role> listRole;
		Subject subject;
		subject = SecurityUtils.getSubject();
		user=(User)subject.getPrincipal();
		listRole=user.getUserRoles();
		return listRole;
	}

}
