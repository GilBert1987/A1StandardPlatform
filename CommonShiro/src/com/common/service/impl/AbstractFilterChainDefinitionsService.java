package com.common.service.impl;

import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.service.FilterChainDefinitionsService;

/**
 * 
 * 安全框架角色资源配置服务类
 * 
 * @author shadow
 * 
 */
public abstract class AbstractFilterChainDefinitionsService implements FilterChainDefinitionsService {

	private static Logger log = Logger.getLogger(AbstractFilterChainDefinitionsService.class);

	private String definitions = "";
	
	private Map<String,String> lastPermission;

	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;

	public void intiPermission() {
		updatePermission();
	}

	public void updatePermission() {
		synchronized (shiroFilterFactoryBean) {
			AbstractShiroFilter shiroFilter = null;
			try {
				shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			// 获取过滤管理器
			PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
					.getFilterChainResolver();
			DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

			// 清空初始权限配置
			manager.getFilterChains().clear();
			shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

			// 重新构建生成
			shiroFilterFactoryBean.setFilterChainDefinitionMap(obtainPermission());
			Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();

			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue().trim().replace(" ", "");
				manager.createChain(url, chainDefinition);
			}
		}
	}

	/** 读取配置资源 */
	private Section obtainPermission() {
		Ini ini = new Ini();
		ini.load(definitions); // 加载资源文件节点串
		Section section = ini.getSection("urls"); // 使用默认节点
		Map<String, String> permissionMap = initOtherPermission();
		if (CollectionUtils.isEmpty(section)) {
			section = ini.getSection(Ini.DEFAULT_SECTION_NAME); // 如不存在默认节点切割,则使用空字符转换
		}
		if (permissionMap != null && !permissionMap.isEmpty()) {
			section.putAll(permissionMap);
		}
		if(lastPermission !=null && !lastPermission.isEmpty()){
			section.putAll(lastPermission);
		}
		return section;
	}

	public abstract Map<String, String> initOtherPermission();

	public String getDefinitions() {
		return definitions;
	}

	public void setDefinitions(String definitions) {
		this.definitions = definitions;
	}

	public Map<String,String> getLastPermission() {
		return lastPermission;
	}

	public void setLastPermission(Map<String,String> lastPermission) {
		this.lastPermission = lastPermission;
	}

}
