package com.common.security.shiro;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import com.common.entity.login.HashPassword;
import com.common.entity.login.User;
import com.common.service.UserRoleService;
import com.common.service.UserService;
import com.common.unit.Digests;
import com.common.unit.Encodes;

public class ShiroDbRealm extends AuthorizingRealm {
	private UserService userService;
	private UserRoleService userRoleService;
	

	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(userService.ALGORITHM);
		matcher.setHashIterations(userService.INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
		
		String parm = token.getCaptcha();
		String code = (String)SecurityUtils.getSubject().getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//忽略大小写
		if(code!=null)
		{
			if(parm.equals(code)==false){
				throw new IncorrectCaptchaException("验证码错误！"); 
			}
		}
		User user = userService.getByName(token.getUsername());
		if (user != null) {
			user.setUserOrgs(userService.getOrgByUserId(user.getId()));
			user.setUserPosts(userService.getPostByUserId(user.getId()));
			user.setUserRoles(userService.getRoleByUserId(user.getId()));
			user.setUserOrgPost(userService.getUserOrgPost(user.getId()));
			if (user.getStatus().equals("disabled")) {
				throw new DisabledAccountException();
			}
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(user, user.getPassword(),
					ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
		
	}


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User shiroUser;
		List<String> permissionList;
		List<String> roleList;
		SimpleAuthorizationInfo info;
		shiroUser = (User) principals.fromRealm(getName()).iterator().next();
		if(null!=shiroUser)
		{
			info=new SimpleAuthorizationInfo();
			permissionList = userRoleService.findPermissionListByUserId(shiroUser.getId());
			if (!permissionList.isEmpty()) {
				info.addStringPermissions(permissionList);
			}
			if(null!=shiroUser.getUserRoleIdAndPostId()){
				roleList=Arrays.asList(shiroUser.getUserRoleIdAndPostId());
				info.addRoles(roleList);
			}
		}
		else
		{
			info=null;
		}
		return info;
	}
	
	
	public HashPassword encrypt(String plainText) {
		byte[] salt;
		byte[] hashPassword;
		HashPassword result;
		result = new HashPassword();
		salt = Digests.generateSalt(userService.SALT_SIZE);
		result.setSalt(Encodes.encodeHex(salt));
		hashPassword = Digests.sha1(plainText.getBytes(), salt, userService.INTERATIONS);
		result.setPassword(Encodes.encodeHex(hashPassword));
		return result;
	}

	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
	
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
}
