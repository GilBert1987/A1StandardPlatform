package com.common.security.shiro;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class RoleAuthorizationFilter extends AuthorizationFilter {
	
	private static Logger log = Logger.getLogger(RoleAuthorizationFilter.class);
	
  	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
  		Boolean blInfo;
  		Subject subject = getSubject(request, response);
  		String[] rolesArray = (String[]) mappedValue;
  		blInfo=false;
  		if(null!=subject.getPrincipal())
    	{
	        if (rolesArray == null || rolesArray.length == 0) {
	        	blInfo = true;  
	        }
	        else
	        {
	        	Set<String> roles = CollectionUtils.asSet(rolesArray);  
	        	for (String role : roles) {
	        		if (subject.hasRole(role)) {
	        				log.debug("has role:"+role);
			            	blInfo = true;
			            	break;
	        		}
	        	}
	        }
    	}
        return blInfo;  
	}
}