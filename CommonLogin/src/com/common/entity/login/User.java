package com.common.entity.login;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//import org.apache.commons.lang3.builder.ToStringBuilder;
import com.google.common.collect.Lists;

public class User implements Serializable {

	private static final long serialVersionUID = -4277639149589431277L;
	
	private String id;

	private String realname;

	private String username;
	
	private String password;
	
	private String plainPassword;
	
	private String salt;
	
	private String phone;
	
	private String email;
	
	private Date createTime;
	
	private String status = "enabled";
	
	private String tenantid;
	
	private List<Role> userRoles = Lists.newArrayList();
	
	private List<Post> userPosts = Lists.newArrayList();
	
	private List<Organization> userOrgs = Lists.newArrayList();
	
	private List<String> userRoleIdAndPostId = Lists.newArrayList();
	
	private List<String> userOrgsIds = Lists.newArrayList();
	
	private List<Map<String,String>> userOrgPost = Lists.newArrayList();
	
	private List<Map<String,String>> userPermissionList;
	
	private List<String> userOrgsPosts = Lists.newArrayList();
	
	private String platformid;

	public String getPlatformid() {
		return platformid;
	}

	public void setPlatformid(String platformid) {
		this.platformid = platformid;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
		syncRoleAndPost();
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Post> getUserPosts() {
		return userPosts;
	}

	public void setUserPosts(List<Post> userPosts) {
		this.userPosts = userPosts;
		syncRoleAndPost();
	}
	
	public List<Organization> getUserOrgs() {
		return userOrgs;
	}

	public void setUserOrgs(List<Organization> userOrgs) {
		Organization orgInfo;
		this.userOrgs = userOrgs;
		userOrgsIds.clear();
		for(int i=0;i<userOrgs.size();i++){
			orgInfo=userOrgs.get(i);
			if(null!=orgInfo.getId() && "".equals(orgInfo.getId().trim())==false)
			{
				userOrgsIds.add(orgInfo.getId());
			}
		}
	}
	
	/*
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	*/
	
	@Override
	public String toString() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
	
	private void syncRoleAndPost(){
		Role role;
		Post post;
		userRoleIdAndPostId.clear();
		for(int i=0;i<userRoles.size();i++)
		{
			role=userRoles.get(i);
			if(null!=role.getId() && "".equals(role.getId().trim())==false && userRoleIdAndPostId.contains(role.getId())==false)
			{
				userRoleIdAndPostId.add(role.getId());
			}
		}
		for(int i=0;i<userPosts.size();i++)
		{
			post=userPosts.get(i);
			if(null!=post.getId() && "".equals(post.getId().trim())==false && userRoleIdAndPostId.contains(post.getId())==false)
			{
				userRoleIdAndPostId.add(post.getId());
			}
		}
	}
	
	public String[] getUserRoleIdAndPostId() {
		return userRoleIdAndPostId.toArray(new String[userRoleIdAndPostId.size()]);
	}

	public String[] getUserOrgsIds() {
		return userOrgsIds.toArray(new String[userOrgsIds.size()]);
	}

	public String[] getUserOrgsPosts(){
		return userOrgsPosts.toArray(new String[userOrgsPosts.size()]);
	}
	
	public List<Map<String,String>> getUserOrgPost() {
		return userOrgPost;
	}

	public void setUserOrgPost(List<Map<String,String>> userOrgPost) {
		String strId;
		Map<String,String> mapInfo;
		this.userOrgPost = userOrgPost;
		for(int i=0;i<userOrgPost.size();i++)
		{
			mapInfo=userOrgPost.get(i);
			strId=mapInfo.get("id");
			if(null!=strId && "".equals(strId.trim())==false)
			{
				userOrgsPosts.add(strId.trim());
			}
		}
	}

	public List<Map<String,String>> getUserPermissionList() {
		return userPermissionList;
	}

	public void setUserPermissionList(List<Map<String,String>> userPermissionList) {
		this.userPermissionList = userPermissionList;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

}
