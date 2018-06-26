package com.common.workflow.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.entity.activiti.GroupInfo;
import com.common.entity.login.Role;
import com.common.service.UserService;

public class CustomGroupEntityManager extends GroupEntityManager{
	
	@Resource
	private UserService userService;
	
	@Override
	public List<Group> findGroupsByUser(String userId)
	{
		Group group;
		List<Group> listReturn;
		List<Role> list;
		list = userService.getRoleByUserId(userId);
		listReturn=new ArrayList<Group>();
		for(int i=0;i<list.size();i++)
		{
			group=new GroupInfo();
			group.setId(list.get(i).getId());
			group.setName(list.get(i).getName());
			group.setType(String.valueOf(list.get(i).getType()));
			listReturn.add(group);
		}
		return listReturn;
	}
	
}
