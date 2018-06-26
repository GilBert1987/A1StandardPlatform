package com.common.service;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.common.entity.query.Query;
import com.common.entity.query.RowInfo;

public interface CommonService {
	
	DataSource getDataSource(String dbid);
	
	String queryBigSql(String name, String strSql,Object[] params) throws Exception;
	
	Map<String,Object> queryFullSql(String name, String strSql,Object[] params) throws Exception;
	
	List<Map> queryListSql(String name, String strSql,Object[] params) throws Exception;
		
	long queryCountSql(String name, String strSql,Object[] params) throws Exception;

	void execNoListSql(String strSysid,String strSqlInfo,Object[] params) throws Exception;
	
	void execNoListSqlByTran(String name,List<String> listSql,Object[][] params) throws Exception;
	
	Map<String,Object> queryPageSql(String name,String colInfo,String strSql,String strWhere,String strOrder,Object[] params,int pageNum,int pageSize) throws Exception;
	
	void updateObj(String name, String strtable, String strcoll,Object[] params,String strwhere) throws Exception;

	void insertObj(String name, String strtable, String strcoll,Object[] params) throws Exception;
	
	void deleteObj(String name, String strtable, String strkey,String strvalue) throws Exception;
	
	String getKeybyTabCol(String dbname, String strTable, String strCol) throws Exception;

	void saveRoleUsers(String roleid, String usersid) throws Exception;
	
	void saveDeptPostUsers(String deptPostid, String usersid) throws Exception;
	
	void defaultUserRole(String strUserId) throws Exception;
	
	Boolean existKey(String sysName, String table, String key, String value,String perKey,String id) throws Exception;
		
	Map<String,Object> getQueryMapByQuery(Map<String,Object> mapQuery, Object[] params) throws Exception;

	Map<String,Object[]> getKeybyMapTabCol(String name, String strTable, String strCol,Map<String, Object[]> mapInfo) throws Exception;

	Map<String, Object[]> importControl(Map<String, Object[]> mapInfo) throws Exception;
	
	Map<String, Object[]> importLogic(Map<String, Object[]> mapInfo) throws Exception;

	List<RowInfo> getQueryList(long iRow, Query query, List<Map> retList);

	List<Map> loadMapByReadisKey(String strKey);

	String getQueryBigList(long iRow, Query query, String strKey) throws Exception;

	String saveRedisObj(Object objInfo) throws Exception;

	Object loadRedisObj(String strId) throws Exception;
	
	List<Map> loadMapByReadisKeyNoDel(String strKey);
	
	void saveMapByReadisKeyNoDel(String strKey,List<Map> list);
	
	List<Map<String, Object>> execSqlByList(String name,String strSql,Object[] params) throws Exception;

	Map<String, Object> getQueryTotalByQuery(Map<String, Object> sqlMap,String grouptype, String key, Object[] array) throws Exception;

	Map<String, Object> getQueryDynamicTotalByQuery(Map<String, Object> sqlCountMap, String grouptype,String strCountColId, String strCountcurrColId, Object[] array) throws Exception;
		
}
