package com.common.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.common.db.handler.CustomerMapHandler;
import com.common.db.template.DbutilsTemplate;
import com.common.entity.login.User;

@Repository
public class ShiroDao {

	private static Logger log = Logger.getLogger(ShiroDao.class);
	
	private DbutilsTemplate dbutilsTemplate;
	
	public User getUser(String username){
		User user;
		String strSql;
		QueryRunner qr;
		Object[] params;
		DataSource dataSource;
		Map<String,DataSource> mapDs;
		ResultSetHandler<User> userHandler;
		user=null;
		mapDs=dbutilsTemplate.getTargetSqlDataSources();
		dataSource=mapDs.get("shiro");
		qr = new QueryRunner(dataSource);
		userHandler=new BeanHandler<User>(User.class);
		params=new Object[]{
			username
		};
		strSql="SELECT id,create_time,PASSWORD,salt,STATUS,username,email,realname,phone,platform_id AS platformid FROM sc_user	where username=?";
		try
		{
			user=qr.query(strSql, userHandler,params);
		}
		catch(Exception e)
		{
			log.error("-----------------getUser error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e);
		}
		return user;
	}
	
	public List<Map<String,Object>> getUserOrgList(String userid){
		StringBuilder strSql;
		Object[] params;
		QueryRunner qr;
		DataSource dataSource;
		Map<String, Object> mapInfo;
		Map<String,DataSource> mapDs;
		List<Map<String,Object>> list;
		CustomerMapHandler mapHandler;
		strSql=new StringBuilder();
		list=null;
		strSql.append("SELECT distinct sc_organization.id,sc_organization.name,sc_organization.description,sc_organization.platform_id ");
		strSql.append("FROM sc_user_post ");
		strSql.append("JOIN sc_post_org ");
		strSql.append("ON sc_post_org.id=sc_user_post.por_id ");
		strSql.append("JOIN sc_organization ");
		strSql.append("ON sc_post_org.org_id=sc_organization.id ");
		strSql.append("LEFT JOIN sc_menu ");
		strSql.append("ON sc_organization.platform_id=sc_menu.code ");
		strSql.append("WHERE sc_user_post.user_id=? ");
		strSql.append("ORDER BY sc_menu.level ");
		params=new Object[]{
			userid
		};
		mapDs=dbutilsTemplate.getTargetSqlDataSources();
		dataSource=mapDs.get("shiro");
		qr = new QueryRunner(dataSource);
		try{
			mapHandler=new CustomerMapHandler();
			mapInfo=qr.query(strSql.toString(), mapHandler, params);
			if(null!=mapInfo && 0!=mapInfo.size())
			{
				list=(List<Map<String,Object>>)mapInfo.get("retMap");
			}
		}
		catch(Exception e)
		{
			log.error("-----------------getUserOrgList error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e);
		}
		if(null==list)
		{
			list=new ArrayList<Map<String,Object>>();
		}
		return list;
	}
	
	public List<Map<String,Object>> getUserRoleList(String userid){
		StringBuilder strSql;
		Object[] params;
		QueryRunner qr;
		DataSource dataSource;
		Map<String, Object> mapInfo;
		Map<String,DataSource> mapDs;
		List<Map<String,Object>> list;
		CustomerMapHandler mapHandler;
		strSql=new StringBuilder();
		list=null;
		strSql.append("SELECT sc_role.id,sc_role.name,sc_role.type ");
		strSql.append("FROM sc_role ");
		strSql.append("JOIN sc_user_role ");
		strSql.append("ON sc_user_role.role_id=sc_role.id ");
		strSql.append("WHERE sc_user_role.user_id=? and sc_role.type=1 ");
		params=new Object[]{
			userid
		};
		mapDs=dbutilsTemplate.getTargetSqlDataSources();
		dataSource=mapDs.get("shiro");
		qr = new QueryRunner(dataSource);
		try{
			mapHandler=new CustomerMapHandler();
			mapInfo=qr.query(strSql.toString(), mapHandler, params);
			if(null!=mapInfo && 0!=mapInfo.size())
			{
				list=(List<Map<String,Object>>)mapInfo.get("retMap");
			}
		}
		catch(Exception e)
		{
			log.error("-----------------getUserRoleList error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e);
		}
		if(null==list)
		{
			list=new ArrayList<Map<String,Object>>();
		}
		return list;
	}
	
	
	public List<Map<String,Object>> getUserPostList(String userid){
		StringBuilder strSql;
		Object[] params;
		QueryRunner qr;
		DataSource dataSource;
		Map<String, Object> mapInfo;
		Map<String,DataSource> mapDs;
		List<Map<String,Object>> list;
		CustomerMapHandler mapHandler;
		strSql=new StringBuilder();
		list=null;
		strSql.append("SELECT sc_post.id,sc_post.name,sc_post.level ");
		strSql.append("FROM sc_user_post ");
		strSql.append("JOIN sc_post_org ");
		strSql.append("ON sc_user_post.por_id=sc_post_org.id ");
		strSql.append("JOIN sc_post ");
		strSql.append("ON sc_post_org.post_id=sc_post.id ");
		strSql.append("WHERE sc_user_post.user_id=? ");
		params=new Object[]{
			userid
		};
		mapDs=dbutilsTemplate.getTargetSqlDataSources();
		dataSource=mapDs.get("shiro");
		qr = new QueryRunner(dataSource);
		try{
			mapHandler=new CustomerMapHandler();
			mapInfo=qr.query(strSql.toString(), mapHandler, params);
			if(null!=mapInfo && 0!=mapInfo.size())
			{
				list=(List<Map<String,Object>>)mapInfo.get("retMap");
			}
		}
		catch(Exception e)
		{
			log.error("-----------------getUserPostList error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e);
		}
		if(null==list)
		{
			list=new ArrayList<Map<String,Object>>();
		}
		return list;
	}

	public List<Map<String,Object>> getUserPermissionListByUserId(String userid){
		StringBuilder strSql;
		Object[] params;
		QueryRunner qr;
		DataSource dataSource;
		Map<String, Object> mapInfo;
		Map<String,DataSource> mapDs;
		List<Map<String,Object>> list;
		CustomerMapHandler mapHandler;
		strSql=new StringBuilder();
		list=null;
		strSql.append("SELECT DISTINCT sc_role_permission.permission ");
		strSql.append("FROM sc_role_permission ");
		strSql.append("JOIN sc_user_role ");
		strSql.append("ON sc_role_permission.role_id=sc_user_role.role_id ");
		strSql.append("WHERE sc_user_role.user_id=? ");
		strSql.append("UNION ");
		strSql.append("SELECT DISTINCT sc_role_permission.permission ");
		strSql.append("FROM sc_role_permission ");
		strSql.append("JOIN sc_post_org ");
		strSql.append("ON sc_post_org.post_id=sc_role_permission.role_id ");
		strSql.append("JOIN sc_user_post ");
		strSql.append("ON sc_post_org.id=sc_user_post.por_id ");
		strSql.append("WHERE sc_user_post.user_id=? ");
		strSql.append("UNION ");
		strSql.append("SELECT DISTINCT sc_role_permission.permission ");
		strSql.append("FROM sc_role_permission ");
		strSql.append("JOIN sc_role_dynamicpermission ");
		strSql.append("ON sc_role_permission.role_id=sc_role_dynamicpermission.id ");
		strSql.append("JOIN sc_user_role ");
		strSql.append("ON sc_role_dynamicpermission.roleid=sc_user_role.role_id ");
		strSql.append("WHERE sc_user_role.user_id=? ");
		params=new Object[]{
			userid,
			userid,
			userid
		};
		mapDs=dbutilsTemplate.getTargetSqlDataSources();
		dataSource=mapDs.get("shiro");
		qr = new QueryRunner(dataSource);
		try{
			mapHandler=new CustomerMapHandler();
			mapInfo=qr.query(strSql.toString(), mapHandler, params);
			if(null!=mapInfo && 0!=mapInfo.size())
			{
				list=(List<Map<String,Object>>)mapInfo.get("retMap");
			}
		}
		catch(Exception e)
		{
			log.error("-----------------getUserPermissionListByUserId error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e);
		}
		if(null==list)
		{
			list=new ArrayList<Map<String,Object>>();
		}
		return list;
	}
	
	public void updateUserPasswd(String userid,String passwd,String salt)
	{
		QueryRunner qr;
		Connection conn;
		String strSql;
		Object[] params;
		DataSource dataSource;
		Map<String,DataSource> mapDs;
		strSql="";
		mapDs=dbutilsTemplate.getTargetSqlDataSources();
		dataSource=mapDs.get("shiro");
		qr = new QueryRunner(dataSource);
		conn=null;
		params=null;
		try {
			strSql="UPDATE sc_user SET PASSWORD=?,salt=? WHERE id=?";
			params=new Object[]{
				passwd,
				salt,
				userid
			};
			conn=dataSource.getConnection();
			conn.setAutoCommit(false);
			qr.update(strSql, params);
			conn.commit();
		} catch (SQLException e) {
			if(null!=conn)
			{
				log.error("-----------------updateUserPasswd error begin---------------------");
				log.error(strSql);
				log.error(params);
				log.error("-----------------error end-------------------------------------");
				log.error(e);
				try {
					DbUtils.rollback(conn);
				} catch (SQLException el) {
					log.error(el);
				}
			}
		}finally{
			DbUtils.closeQuietly(conn);
		}
	}

	public List<Map<String, Object>> getUserAllRoleList(String userId) {
		StringBuilder strSql;
		Object[] params;
		QueryRunner qr;
		DataSource dataSource;
		Map<String, Object> mapInfo;
		Map<String,DataSource> mapDs;
		List<Map<String,Object>> list;
		CustomerMapHandler mapHandler;
		strSql=new StringBuilder();
		list=null;
		strSql.append("SELECT sc_role.name,sc_role.type ");
		strSql.append("FROM sc_role ");
		strSql.append("JOIN sc_user_role ");
		strSql.append("ON sc_user_role.role_id=sc_role.id ");
		strSql.append("WHERE sc_user_role.user_id=? ");
		params=new Object[]{
			userId
		};
		mapDs=dbutilsTemplate.getTargetSqlDataSources();
		dataSource=mapDs.get("shiro");
		qr = new QueryRunner(dataSource);
		try{
			mapHandler=new CustomerMapHandler();
			mapInfo=qr.query(strSql.toString(), mapHandler, params);
			if(null!=mapInfo && 0!=mapInfo.size())
			{
				list=(List<Map<String,Object>>)mapInfo.get("retMap");
			}
		}
		catch(Exception e)
		{
			log.error("-----------------getUserAllRoleList error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e);
		}
		if(null==list)
		{
			list=new ArrayList<Map<String,Object>>();
		}
		return list;
	}
	
	public List<Map<String, String>> getUserOrgPost(String userid){
		StringBuilder strSql;
		Object[] params;
		QueryRunner qr;
		DataSource dataSource;
		Map<String, Object> mapInfo;
		Map<String,DataSource> mapDs;
		List<Map<String,String>> list;
		CustomerMapHandler mapHandler;
		strSql=new StringBuilder();
		list=null;
		strSql.append("SELECT sc_post_org.id ");
		strSql.append("FROM sc_user_post ");
		strSql.append("JOIN sc_post_org ");
		strSql.append("ON sc_user_post.por_id=sc_post_org.id ");
		strSql.append("WHERE sc_user_post.user_id=? ");
		params=new Object[]{
			userid
		};
		mapDs=dbutilsTemplate.getTargetSqlDataSources();
		dataSource=mapDs.get("shiro");
		qr = new QueryRunner(dataSource);
		try{
			mapHandler=new CustomerMapHandler();
			mapInfo=qr.query(strSql.toString(), mapHandler, params);
			if(null!=mapInfo && 0!=mapInfo.size())
			{
				list=(List<Map<String,String>>)mapInfo.get("retMap");
			}
		}
		catch(Exception e)
		{
			log.error("-----------------getUserOrgPost error begin---------------------");
			log.error(strSql);
			log.error(params);
			log.error("-----------------error end-------------------------------------");
			log.error(e);
		}
		if(null==list)
		{
			list=new ArrayList<Map<String,String>>();
		}
		return list;
	}

	/**
	 * @return the dbutilsTemplate
	 */
	public DbutilsTemplate getDbutilsTemplate() {
		return dbutilsTemplate;
	}

	/**
	 * @param dbutilsTemplate the dbutilsTemplate to set
	 */
	public void setDbutilsTemplate(DbutilsTemplate dbutilsTemplate) {
		this.dbutilsTemplate = dbutilsTemplate;
	}
}
