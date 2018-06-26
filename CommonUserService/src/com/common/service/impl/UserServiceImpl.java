package com.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.ShiroDao;
import com.common.entity.login.HashPassword;
import com.common.entity.login.Organization;
import com.common.entity.login.Post;
import com.common.entity.login.Role;
import com.common.entity.login.User;
import com.common.service.UserService;
import com.common.unit.Digests;
import com.common.unit.Encodes;

@Path("UserService")
@Service
public class UserServiceImpl implements UserService {

	private static Logger log = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private ShiroDao shiroDao;
	
	@Override
	public User getByName(String username) {
		return shiroDao.getUser(username);
	}
	
	@Override
	public List<Role> getRoleByUserId(String userId) {
		List<Map<String,Object>> listMap;
		List<Role> listRole;
		Role role;
		listMap=shiroDao.getUserRoleList(userId);
		listRole=new ArrayList<Role>();
		for(Map<String, Object> hMap:listMap)
		{
			role=new Role();
			for (Map.Entry<String, Object> entry: hMap.entrySet()) {
				if("name".equals(entry.getKey())==true)
				{
					role.setName(entry.getValue().toString());
					continue;
				}
				if("id".equals(entry.getKey())==true)
				{
					role.setId(entry.getValue().toString());
					continue;
				}
				if("type".equals(entry.getKey())==true && "".equals(entry.getValue().toString())==false)
				{
					role.setType(Integer.parseInt(entry.getValue().toString()));
					continue;
				}
			}
			listRole.add(role);
		}
		return listRole;
	}

	@Override
	public List<Post> getPostByUserId(String userId) {
		List<Map<String,Object>> listMap;
		List<Post> listPost;
		Post post;
		listMap=shiroDao.getUserPostList(userId);
		listPost=new ArrayList<Post>();
		for(Map<String, Object> hMap:listMap)
		{
			post=new Post();
			for (Map.Entry<String, Object> entry: hMap.entrySet()) {
				if("id".equals(entry.getKey())==true)
				{
					post.setId(entry.getValue().toString());
					continue;
				}
				if("name".equals(entry.getKey())==true)
				{
					post.setName(entry.getValue().toString());
					continue;
				}
				if("level".equals(entry.getKey())==true)
				{
					post.setLevel(entry.getValue().toString());
					continue;
				}
			}
			listPost.add(post);
		}
		return listPost;
	}

	@Override
	public List<Organization> getOrgByUserId(String userId) {
		List<Map<String,Object>> listMap;
		List<Organization> listOrg;
		Organization org;
		listMap=shiroDao.getUserOrgList(userId);
		listOrg=new ArrayList<Organization>();
		for(Map<String, Object> hMap:listMap)
		{
			org=new Organization();
			for (Map.Entry<String, Object> entry: hMap.entrySet()) {
				if("id".equals(entry.getKey())==true)
				{
					if(null!=entry.getValue()){
						org.setId(entry.getValue().toString());
					}
					continue;
				}
				if("name".equals(entry.getKey())==true)
				{
					if(null!=entry.getValue()){
						org.setName(entry.getValue().toString());
					}
					continue;
				}
				if("description".equals(entry.getKey())==true)
				{
					if(null!=entry.getValue()){
						org.setDescription(entry.getValue().toString());
					}
					continue;
				}
				if("platform_id".equals(entry.getKey())==true)
				{
					if(null!=entry.getValue()){
						org.setPlatformid(entry.getValue().toString());
					}
					continue;
				}
			}
			listOrg.add(org);
		}
		return listOrg;
	}
	
	@Override
	public void updateUser(String strJson,String changePwd){
		String jsonData;
		String strKey;
		String strPasswd;
		JSONObject jsonInfo;
		JSONObject jsonkey;
		JSONArray jsarray;
		HashPassword hashPassword;
		strKey="";
		strPasswd="";
		if(strJson!=null && "".equals(strJson)==false)
		{
			jsonData=strJson.toString();
			jsonInfo=JSONObject.fromObject(jsonData);
			if(jsonInfo.isEmpty()==false)
			{
				if(((JSONArray)jsonInfo.get("id")).isArray()==true)
				{
					jsarray=(JSONArray)jsonInfo.get("id");
					jsonkey=(JSONObject)jsarray.get(0);
					strKey=jsonkey.getString("value");
				}
				if(StringUtils.isNotBlank(jsonInfo.getString("password"))==true)
				{
					strPasswd=jsonInfo.getString("password");
				}
			}
			if(StringUtils.isNotBlank(strKey)==true && StringUtils.isNotBlank(strPasswd)==true)
			{
				hashPassword = encrypt(strPasswd);
				if("1".equals(changePwd)==true)
				{
					shiroDao.updateUserPasswd(strKey, hashPassword.getPassword(), hashPassword.getSalt());
				}
			}
		}
	}

	@Override
	public List<Map<String,String>> getUserOrgPost(String userid) {
		return shiroDao.getUserOrgPost(userid);
	}
	
	public HashPassword encrypt(String plainText) {
		byte[] salt;
		byte[] hashPassword;
		HashPassword result;
		result = new HashPassword();
		salt = Digests.generateSalt(SALT_SIZE);
		result.setSalt(Encodes.encodeHex(salt));
		hashPassword = Digests.sha1(plainText.getBytes(), salt, INTERATIONS);
		result.setPassword(Encodes.encodeHex(hashPassword));
		return result;
	}
}
