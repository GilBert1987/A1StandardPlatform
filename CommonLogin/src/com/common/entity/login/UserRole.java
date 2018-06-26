package com.common.entity.login;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 * @hibernate.class table="T_UsersRoles"
 */
public class UserRole implements Serializable{
	
	/** 描述  */
	private static final long serialVersionUID = -8888778227379780116L;
	
	/**
	 * 值越小，优先级越高
	 */
	private Integer priority = 99;
	
	private Role role;
	
	private User user;

	/**  
	 * 返回 role 的值   
	 * @return role  
	 */
	public Role getRole() {
		return role;
	}

	/**  
	 * 设置 role 的值  
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**  
	 * 返回 user 的值   
	 * @return user  
	 */
	public User getUser() {
		return user;
	}

	/**  
	 * 设置 user 的值  
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**  
	 * 返回 priority 的值   
	 * @return priority  
	 */
	public Integer getPriority() {
		return priority;
	}

	/**  
	 * 设置 priority 的值  
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}