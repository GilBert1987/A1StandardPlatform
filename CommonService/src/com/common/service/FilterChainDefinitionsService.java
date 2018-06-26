package com.common.service;

import java.util.Map;

public interface FilterChainDefinitionsService {
	public static final String PREMISSION_STRING = "perms[\"%1$s\"]"; // 资源结构格式  
    public static final String ROLE_STRING = "role[\"%1$s\"]"; // 角色结构格式  
  
    /** 初始化框架权限资源配置 */  
    abstract void intiPermission();  
  
    /** 重新加载框架权限资源配置 (强制线程同步) */  
    abstract void updatePermission();  
  
    /** 初始化第三方权限资源配置 */  
    abstract Map<String, String> initOtherPermission(); 
}
