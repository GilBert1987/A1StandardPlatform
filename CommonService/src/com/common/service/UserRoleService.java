package com.common.service;

import java.util.List;

public interface UserRoleService{
	
	List<String> findPermissionListByUserId(String id);

}
