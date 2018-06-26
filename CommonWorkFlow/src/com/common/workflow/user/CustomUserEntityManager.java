package com.common.workflow.user;

import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import com.common.iface.ILoginPass;

public class CustomUserEntityManager extends UserEntityManager {
	
	private ILoginPass iloginpass;
	
	public void setIloginpass(ILoginPass iloginpass) {
		this.iloginpass = iloginpass;
	}

	@Override
    public Boolean checkPassword(String userId, String password) {
		return iloginpass.checkPassword(userId, password);
    }
}
