package com.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.ShiroDao;
import com.common.service.UserRoleService;

@Path("UserRoleService")
@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private ShiroDao shiroDao;
	
	@Override
	public List<String> findPermissionListByUserId(String id) {
		List<Map<String,Object>> listMap;
		List<String> listPermission;
		listMap=shiroDao.getUserPermissionListByUserId(id);
		listPermission=new ArrayList<String>();
		for(Map<String, Object> hMap:listMap)
		{
			for (Map.Entry<String, Object> entry: hMap.entrySet()) {
				if("permission".equals(entry.getKey())==true)
				{
					listPermission.add(entry.getValue().toString());
					continue;
				}
			}
		}
		return listPermission;
	}

}
