package com.common.workflow.user;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;

public class CustomGroupEntityManagerFactory implements SessionFactory {

	private CustomGroupEntityManager customRoleManager;
	
	@Override
	public Class<?> getSessionType() {
		return GroupIdentityManager.class;
	}

	@Override
	public Session openSession() {
		return customRoleManager;
	}
	
	public CustomGroupEntityManager getCustomGroupEntityManager() {
		return customRoleManager;
	}

	public void setCustomGroupEntityManager(
			CustomGroupEntityManager customGroupEntityManager) {
		this.customRoleManager = customGroupEntityManager;
	}
}
