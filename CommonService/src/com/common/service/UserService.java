package com.common.service;

import java.util.List;
import java.util.Map;

import com.common.entity.login.Organization;
import com.common.entity.login.Post;
import com.common.entity.login.Role;
import com.common.entity.login.User;

public interface UserService{
	public static final int INTERATIONS = 1024;
	public static final String ALGORITHM = "SHA-1";
	public static final int SALT_SIZE = 8;
	
	User getByName(String username);
	
	List<Role> getRoleByUserId(String userId);
	
	List<Post> getPostByUserId(String userId);
	
	List<Map<String,String>> getUserOrgPost(String userid);
	
	List<Organization> getOrgByUserId(String userId);
	
	void updateUser(String strJson,String changePwd);
	
}
