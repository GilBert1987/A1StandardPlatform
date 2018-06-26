package com.common.service.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.common.dao.CommonDBDao;
import com.common.dao.RedisDao;
import com.common.entity.query.Column;
import com.common.entity.query.PageInfo;
import com.common.entity.query.Query;
import com.common.entity.query.RowInfo;
import com.common.entity.query.Title;
import com.common.entity.query.Unit;
import com.common.service.CommonService;


@Path("CommonService")
public class CommonServiceImpl implements CommonService {

	private static Logger log = Logger.getLogger(CommonServiceImpl.class);
	
	@Autowired
	private CommonDBDao commDbDao;
	
	@Autowired
	private RedisDao redisDao;
	
	public DataSource getDataSource(String dbid){
		DataSource ds;
		ds=commDbDao.getDataSource(dbid);
		return ds;
	}

	public Map<String,Object> queryFullSql(String name, String strSql,Object[] params) throws Exception {
		return commDbDao.queryFullSql(commDbDao.getDataSource(name), strSql, params);
	}
	
	public void execNoListSqlByTran(String name,List<String> listSql,Object[][] params) throws Exception{
		commDbDao.execNoListSqlByTran(commDbDao.getDataSource(name), listSql, params);
	}
	
	public List<Map> queryListSql(String name, String strSql,Object[] params) throws Exception{
		List<Map> mapList;
		Map<String, Object> mapInfo;
		mapList=null;
		mapInfo=commDbDao.queryFullSql(commDbDao.getDataSource(name), strSql, params);
		if(null!=mapInfo && 0!=mapInfo.size())
		{
			mapList=(List<Map>)mapInfo.get("retMap");
		}
		if(null==mapList)
		{
			mapList=new ArrayList<Map>();
		}
		return mapList;
	}
	
	public List<Map<String, Object>> execSqlByList(String name,String strSql,Object[] params) throws Exception {
		return commDbDao.execSqlByList(commDbDao.getDataSource(name), strSql, params);
	}
	
	public long queryCountSql(String name, String strSql,Object[] params) throws Exception {
		return commDbDao.queryCountSql(commDbDao.getDataSource(name), strSql, params);
	}

	public void execNoListSql(String strSysid,String strSqlInfo,Object[] params) throws Exception{
		commDbDao.execNoListSql(commDbDao.getDataSource(strSysid), strSqlInfo,params);
	}
	
	public Map<String,Object> queryPageSql(String name,String colInfo,String strSql,String strWhere,String strOrder,Object[] params,int pageNum,int pageSize) throws Exception{
		Map<String,Object> retMap;
		PageInfo<Map> pageInfo;
		retMap=new HashMap<String,Object>();
		pageInfo=commDbDao.queryPageSql(commDbDao.getDataSource(name),colInfo,strSql,strWhere,strOrder,"",params,pageNum,pageSize);
		retMap.put("list", pageInfo.getList());
		retMap.put("rowCount", pageInfo.getRowCount());
		retMap.put("colInfo", pageInfo.getMapColInfo());
		return retMap;
	}
	
	public void updateObj(String name, String strtable, String strcoll,Object[] params,String strwhere) throws Exception {
		commDbDao.updateObj(commDbDao.getDataSource(name), strtable, strcoll, params, strwhere);
	}

	public void insertObj(String name, String strtable, String strcoll,Object[] params) throws Exception {
		commDbDao.insertObj(commDbDao.getDataSource(name), strtable, strcoll, params);
	}
	
	public void deleteObj(String name, String strtable, String strkey,String strvalue) throws Exception {
		commDbDao.deleteObj(commDbDao.getDataSource(name), strtable, strkey, strvalue);
	}
	
	public String getKeybyTabCol(String dbname, String strTable, String strCol) throws Exception {
		return commDbDao.getTabKeyNo(commDbDao.getDataSource("shiro"), dbname, strTable, strCol);
	}

	public void saveRoleUsers(String roleid, String usersid) throws Exception {
		StringBuilder strDelSqlInfo;
		String strInsertSqlInfo;
		String[] strArr;
		String strKey;
		List<Object> delParams;
		List<Object> insertParams;
		delParams=new ArrayList<Object>();
		strDelSqlInfo=new StringBuilder();
		strArr=null;
		strDelSqlInfo.append("DELETE FROM sc_user_role WHERE role_id=? ");
		delParams.add(roleid);
		if("".equals(usersid)==false)
		{
			strArr=usersid.split(",");
			strDelSqlInfo.append(" AND user_id NOT IN ( ");
			for(int i=0;i<strArr.length;i++)
			{
				if(i!=0){
					strDelSqlInfo.append(" union ");
				}
				strDelSqlInfo.append(" select ? as userid ");
				delParams.add(strArr[i]);
			}
			strDelSqlInfo.append(" ) ");
		}
		commDbDao.execNoListSql(commDbDao.getDataSource("shiro"), strDelSqlInfo.toString(),delParams.toArray());
		if(null!=strArr)
		{
			for(int i=0;i<strArr.length;i++)
			{
				insertParams=new ArrayList<Object>();
				strKey=this.getKeybyTabCol("shiro", "sc_user_role", "id");
				strInsertSqlInfo="INSERT INTO sc_user_role (id,role_id,user_id) ";
				strInsertSqlInfo+=" SELECT DISTINCT ? AS id,? AS roleid,? AS userid ";
				strInsertSqlInfo+=" FROM sc_role ";
				strInsertSqlInfo+=" JOIN ( ";
				strInsertSqlInfo+=" 	SELECT COUNT(*) AS usercount,? AS roleid,? AS userid ";
				strInsertSqlInfo+=" 	FROM sc_user_role WHERE role_id=? AND user_id = ? "; 
				strInsertSqlInfo+=" )AS t ";
				strInsertSqlInfo+=" ON sc_role.id=t.roleid ";
				strInsertSqlInfo+=" WHERE sc_role.type='1' AND t.usercount=0 ";
				insertParams.add(strKey);
				insertParams.add(roleid);
				insertParams.add(strArr[i]);
				insertParams.add(roleid);
				insertParams.add(strArr[i]);
				insertParams.add(roleid);
				insertParams.add(strArr[i]);
				commDbDao.execNoListSql(commDbDao.getDataSource("shiro"), strInsertSqlInfo,insertParams.toArray());
			}
		}
	}
	
	public void defaultUserRole(String strUserId) throws Exception{
		String strSql;
		String strId;
		List<Map> list;
		Map<String, Object> mapInfo;
		Object[] selParams;
		Object[] insertParams;
		list=null;
		strSql="select id FROM sc_user_role WHERE role_id='SR_201502260001' AND user_id=?";
		selParams=new Object[]{
			strUserId
		};
		list=this.queryListSql("shiro", strSql,selParams);
		if(null!=list && 0==list.size())
		{
			strId=this.getKeybyTabCol("shiro", "sc_user_role", "id");
			strSql="insert into sc_user_role(id,role_id,user_id)values(?,'SR_201502260001',?)";
			insertParams=new Object[]{
				strId,
				strUserId
			};
			commDbDao.execNoListSql(commDbDao.getDataSource("shiro"), strSql,insertParams);
		}
	}
	
	

	public Boolean existKey(String sysName, String table, String key, String value,String perKey,String id) throws Exception {
		Boolean blInfo;
		List<Map> mapInfo;
		String strSql;
		Object[] params;
		params=new Object[]{
			value,
			id
		};
		strSql="select "+key+ " from "+table +" where "+key+"=? and "+perKey+"!=?";
		mapInfo=this.queryListSql(sysName, strSql,params);
		blInfo=mapInfo.size()==0?true:false;
		return blInfo;
	}

	
	
	
	public Map<String,Object> getQueryMapByQuery(Map<String,Object> mapQuery, Object[] params) throws Exception{
		Map<String,Object> retMap;
		retMap=null;
		retMap=commDbDao.getQueryList(mapQuery,params);
		return retMap;
	}

	public Map<String,Object[]> getKeybyMapTabCol(String name, String strTable, String strCol,Map<String, Object[]> mapInfo) throws Exception {
		String strKey;
		Object[] objlist;
		objlist=new Object[1];
		strKey=this.getKeybyTabCol(name,strTable, strCol);
		objlist[0]=strKey;
		mapInfo.put("map._saveObj", objlist);
		return mapInfo;
	}

	public Map<String, Object[]> importControl(Map<String, Object[]> mapInfo) throws Exception {
		String strIndex;
		String strControls;
		String strSql;
		String strId;
		String strLabel;
		String strShortName;
		String strParam;
		JSONArray jsonArray;
		JSONObject jsonObj;
		JSONArray jsonTags;
		List<Map> listMap;
		Object[] objlist;
		Object[] selParams;
		Object[] updateParams;
		Object[] insertParams;
		DataSource dataSource;
		objlist=new Object[1];
		strIndex="";
		strControls="";
		dataSource=commDbDao.getDataSource("com");
		if(null!=mapInfo.get("map.rIndex"))
		{
			strIndex=mapInfo.get("map.rIndex")[0].toString();
		}
		if(null!=mapInfo.get("map.hControlInfo"))
		{
			strControls=mapInfo.get("map.hControlInfo")[0].toString();
		}
		if("".equals(strIndex)==false && "".equals(strControls)==false)
		{
			jsonArray=JSONArray.fromObject(strControls);
			for(int i=0;i<jsonArray.size();i++)
			{
				jsonObj=jsonArray.getJSONObject(i);
				strParam="<%@ taglib prefix=\"{0}\" uri=\""+jsonObj.getString("uri")+"\" %>";
				if(strIndex.equals(jsonObj.getString("uri"))==true){
					selParams=new Object[]{
						strParam
					};
					strSql="SELECT id FROM sc_control WHERE cn_format=? ";
					listMap=this.queryListSql("com", strSql,selParams);
					if(listMap.size()!=0)
					{
						strId=listMap.get(0).get("id").toString();
					}
					else
					{
						strShortName=jsonObj.getString("uri").substring(1, jsonObj.getString("uri").length());
						strId=this.getKeybyTabCol("com", "sc_control", "id");
						strLabel="<%@ taglib "+String.format("prefix=\"%s\"",strShortName)+" uri=\""+jsonObj.getString("uri")+"\" %>";
						insertParams=new Object[]{
							strId,
							jsonObj.getString("shortname"),
							strLabel,
							strShortName,
							strParam
						};
						strSql="INSERT INTO sc_control(id,cn_name,cn_label,cn_shorthand,cn_format)";
						strSql+="VALUE(?,?,?,?,?)";
						commDbDao.execNoListSql(dataSource, strSql,insertParams);
					}
					objlist[0]=strId;
					jsonTags=jsonObj.getJSONArray("tags");
					if(null!=jsonTags)
					{
						updateParams=new Object[]{
							jsonTags.size(),
							strId
						};
						strSql="update sc_control set cn_count=? where id=?";
						commDbDao.execNoListSql(dataSource, strSql,updateParams);
						importControlTag(strId,jsonObj);
					}
				}
			}
		}
		mapInfo.put("map._saveObj", objlist);
		return mapInfo;
	}
	
	private void importControlTag(String strParentId, JSONObject jsonObj) throws Exception{
		List<Map> listMap;
		JSONArray jsonArrTag;
		JSONObject jsonTag;
		String strName;
		String strNames;
		String strInfo;
		String strSql;
		String strId;
		String strTagClass;
		Object[] selParams;
		Object[] delParams;
		Object[] insertParams;
		DataSource dataSource;
		strNames="";
		dataSource=commDbDao.getDataSource("com");
		jsonArrTag=jsonObj.getJSONArray("tags");
		for(int i=0;i<jsonArrTag.size();i++)
		{
			jsonTag=jsonArrTag.getJSONObject(i);
			strName=jsonTag.getString("name");
			strInfo=jsonTag.getString("info");
			strTagClass=jsonTag.getString("tagclass");
			selParams=new Object[]{
				strParentId,
				strName
			};
			strSql="SELECT id FROM sc_control_tld where control_id=? and code=?";
			listMap=this.queryListSql("com", strSql,selParams);
			strNames=(i==0?strName:strNames+","+strName);
			if(listMap.size()!=0)
			{
				strId=listMap.get(0).get("id").toString();
			}
			else
			{
				strId=this.getKeybyTabCol("com", "sc_control_tld", "id");
				insertParams=new Object[]{
					strId,
					strParentId,
					strName,
					strInfo,
					strTagClass
				};
				strSql="INSERT INTO sc_control_tld(id,control_id,code,name,classinfo)";
				strSql+="VALUE(?,?,?,?,?)";
				commDbDao.execNoListSql(dataSource, strSql,insertParams);
			}
			importControlTagAttr(strId,jsonTag);
		}
		delParams=new Object[]{
			strParentId,
			strNames
		};
		strSql="delete from sc_control_tld where control_id=? and LOCATE(code,?)=0";
		commDbDao.execNoListSql(dataSource, strSql,delParams);
	}
	
	private void importControlTagAttr(String strParentId, JSONObject jsonObj) throws Exception{
		List<Map> listMap;
		JSONArray jsonArrTag;
		JSONObject jsonTag;
		String strName;
		String strRequired;
		String strSql;
		String strId;
		String strNames;
		String strRtexprvalue;
		Object[] selParams;
		Object[] delParams;
		Object[] insertParams;
		DataSource comSource;
		comSource=commDbDao.getDataSource("com");
		strNames="";
		jsonArrTag=jsonObj.getJSONArray("attrs");
		for(int i=0;i<jsonArrTag.size();i++)
		{
			jsonTag=jsonArrTag.getJSONObject(i);
			strName=jsonTag.getString("name");
			strRequired=jsonTag.getString("required");
			strRtexprvalue=jsonTag.getString("rtexprvalue");
			strNames=(i==0?strName:strNames+","+strName);
			selParams=new Object[]{
				strParentId,
				strName
			};
			strSql="SELECT id FROM sc_control_tld_attr where tldid=? and code=?";
			listMap=this.queryListSql("com", strSql,selParams);
			if(listMap.size()==0)
			{
				strId=this.getKeybyTabCol("com", "sc_control_tld_attr", "id");
				insertParams=new Object[]{
					strId,
					strParentId,
					strName,
					strName,
					strRequired,
					strRtexprvalue,
				};
				strSql="INSERT INTO sc_control_tld_attr(id,tldid,code,name,required,rtexprvalue)";
				strSql+="VALUE(?,?,?,?,?,?)";
				commDbDao.execNoListSql(comSource, strSql,insertParams);
			}
		}
		delParams=new Object[]{
			strParentId,
			strNames
		};
		strSql="delete from sc_control_tld_attr where tldid=? and LOCATE(code,?)=0";
		commDbDao.execNoListSql(comSource, strSql,delParams);
	}
	
	public Map<String, Object[]> importLogic(Map<String, Object[]> mapInfo) throws Exception {
		String strLogics;
		String strSql;
		String strId;
		String strType;
		JSONArray jsonArray;
		JSONObject jsonObj;
		List<Map> listMap;
		Object[] selParams;
		Object[] insertParams;
		strLogics="";
		strType="";
		if(null!=mapInfo.get("map.hLogicInfo"))
		{
			strLogics=mapInfo.get("map.hLogicInfo")[0].toString();
		}
		if(null!=mapInfo.get("map.hType"))
		{
			strType=mapInfo.get("map.hType")[0].toString();
		}
		if("".equals(strLogics)==false && "".equals(strType)==false)
		{
			jsonArray=JSONArray.fromObject(strLogics);
			for(int i=0;i<jsonArray.size();i++)
			{
				jsonObj=jsonArray.getJSONObject(i);
				if("".equals(jsonObj.getString("classinfo"))==false){
					selParams=new Object[]{
						jsonObj.getString("classinfo"),
						strType
					};
					strSql="SELECT id,name,typeid FROM sc_logic where name=? and typeid=?";
					listMap=this.queryListSql("com", strSql,selParams);
					if(listMap.size()!=0)
					{
						strId=listMap.get(0).get("id").toString();
					}
					else
					{
						strId=this.getKeybyTabCol("com", "sc_logic", "id");
						insertParams=new Object[]{
							strId,
							jsonObj.getString("classinfo"),
							strType
						};
						strSql="INSERT INTO sc_logic(id,name,typeid)";
						strSql+="VALUE(?,?,?)";
						commDbDao.execNoListSql(commDbDao.getDataSource("com"), strSql,insertParams);
					}
					if(null!=jsonObj.getString("method") && null!=jsonObj.getString("rettype"))
					{
						importLogicMethod(strId,jsonObj);
					}
				}
			}
		}
		return mapInfo;
	}
	
	private void importLogicMethod(String strParentId, JSONObject jsonObj) throws Exception{
		List<Map> listMap;
		String strMethod;
		String strRettype;
		String strSql;
		String strId;
		DataSource dataSource;
		Object[] selParams;
		Object[] insertParams;
		Object[] updateParams;
		Object[] delParams;
		strMethod=jsonObj.getString("method");
		strRettype=jsonObj.getString("rettype");
		dataSource=commDbDao.getDataSource("com");
		selParams=new Object[]{
			strParentId,
			strMethod
		};
		strSql="SELECT id FROM sc_logicmethod where logid=? and name=?";
		listMap=this.queryListSql("com", strSql,selParams);
		if(listMap.size()!=0)
		{
			strId=listMap.get(0).get("id").toString();
			strSql="update sc_logicmethod ";
			strSql+="set backtype=? ";
			strSql+="where id=? ";
			updateParams=new Object[]{
				strRettype,
				strId
			};
			commDbDao.execNoListSql(dataSource, strSql,updateParams);
		}
		else
		{
			strId=this.getKeybyTabCol("com", "sc_logicmethod", "id");
			strSql="INSERT INTO sc_logicmethod(id,logid,name,backtype)";
			strSql+="VALUE(?,?,?,?)";
			insertParams=new Object[]{
				strId,
				strParentId,
				strMethod,
				strRettype
			};
			commDbDao.execNoListSql(dataSource, strSql,insertParams);
		}
		delParams=new Object[]{
			strId
		};
		strSql="delete from sc_logicparam where lmid=?";
		commDbDao.execNoListSql(dataSource, strSql,delParams);
		importLogicMetodParam(strId,jsonObj);
	}
	
	private void importLogicMetodParam(String strParentId, JSONObject jsonObj) throws Exception{
		JSONArray jsonArrParam;
		JSONObject jsonParam;
		String strParam;
		String strSql;
		String strId;
		Object[] params;
		jsonArrParam=jsonObj.getJSONArray("params");
		for(int i=0;i<jsonArrParam.size();i++)
		{
			jsonParam=jsonArrParam.getJSONObject(i);
			strParam=jsonParam.getString("param");
			strId=this.getKeybyTabCol("com", "sc_logicparam", "id");
			strSql="INSERT INTO sc_logicparam(id,lmid,name,lp_index)";
			strSql+="VALUE(?,?,?,?)";
			params=new Object[]{
				strId,
				strParentId,
				strParam,
				(i+1)
			};
			commDbDao.execNoListSql(commDbDao.getDataSource("com"), strSql,params);
		}
	}

	@Override
	public String getQueryBigList(long iRow,Query query, String strKey) throws Exception {
		String strNewKey;
		List<Map> newList;
		List<RowInfo> listRow;
		List<Map> excelDatalList;
		Map<String,Object> mapInfo;
		Map<String,Object> retInfo;
		newList=new ArrayList<Map>();
		mapInfo=new HashMap<String,Object>();
		retInfo=new HashMap<String,Object>();
		excelDatalList=(List<Map>)redisDao.load(strKey);
		listRow=getQueryList(iRow, query, excelDatalList);
		mapInfo.put("key", listRow);
		newList.add(mapInfo);
		retInfo.put("retMap", newList);
		strNewKey=this.getKeybyTabCol("com", "sc_redis", "id");
		redisDao.addOrUpdate(strNewKey, retInfo);
		return strNewKey;
	}
	
	@Override
	public List<RowInfo> getQueryList(long iRow,Query query, List<Map> retList) {
		Unit unit;
		int iEnd;
		Title title;
		RowInfo rowIt;
		String strValue;
		RowInfo rowInfo;
		Integer[] intThis;
		Integer[] intThat;
		Integer[] intOther;
		RowInfo rowParentIt;
		Integer[] intParent;
		Integer[] colConfig;
		List<Column> listCol;
		String strParentValue;
		List<RowInfo> rowList;
		RowInfo rowParentOther;
		Integer[] intParentThis;
		Map<String, Object> mapIt;
		Map<String, Object> mapParent;
		Map<String,Integer[]> colThis;
		Map<String,Integer[]> colUnit;
		Map<String,Integer[]> colOther;
		Map<String,Integer[]> colParent;
		rowParentIt=null;
		listCol=query.getColumns();
		rowList=new ArrayList<RowInfo>();
		colUnit=new HashMap<String,Integer[]>();
		for(Column colInfo:listCol)
		{
			colConfig=new Integer[3];
			title=colInfo.getTitle();
			colConfig[0]=1;
			colConfig[1]=1;
			if("1".equals(title.getIsshow())==true)
			{
				colConfig[2]=1;
			}
			else
			{
				colConfig[2]=0;
			}
			colUnit.put(colInfo.getKey(),colConfig);
		}
		for(Map hasMap:retList)
		{
			rowInfo=new RowInfo();
			rowInfo.setSerialNum(iRow);
			rowInfo.setRowInfo(hasMap);
			rowInfo.setColUnit(colUnit);
			rowList.add(rowInfo);
			iRow++;
		}
		for(Column col:listCol)
		{
			unit=col.getUnit();
			iEnd=0;
			if("1".equals(unit.getMerge())==true){
				if("0".equals(unit.getMergerefer())==true || "".equals(unit.getMergecolumn())==true)
				{
					iEnd=rowList.size();
				}
				for(int i=1;i<rowList.size();i++)
				{
					if(i==1)
					{
						rowParentIt=rowList.get(0);
					}
					rowIt=rowList.get(i);
					mapIt=rowIt.getRowInfo();
					mapParent=rowParentIt.getRowInfo();
					if(colUnit!=rowParentIt.getColUnit())
					{
						colParent=rowParentIt.getColUnit();
					}
					else
					{
						colParent=new HashMap<String,Integer[]>();
						colParent.putAll(colUnit);
						rowParentIt.setColUnit(colParent);
					}
					if(colUnit!=rowIt.getColUnit())
					{
						colThis=rowIt.getColUnit();
					}
					else
					{
						colThis=new HashMap<String,Integer[]>();
						colThis.putAll(colUnit);
						rowIt.setColUnit(colThis);
					}
					if("1".equals(unit.getMergerefer())==true && "".equals(unit.getMergecolumn())==false && i>iEnd)
					{
						rowParentOther=rowList.get(i-1);
						if(colUnit!=rowParentOther.getColUnit())
						{
							colOther=rowParentOther.getColUnit();
							intOther=colOther.get(unit.getMergecolumn());
						}
						else
						{
							intOther=colUnit.get(unit.getMergecolumn());
						}
						iEnd+=intOther[0];
					}
					if(null!=mapIt.get(col.getKey()))
					{
						strValue=mapIt.get(col.getKey()).toString();
					}
					else
					{
						strValue="";
					}
					if(null!=mapParent.get(col.getKey()))
					{
						strParentValue=mapParent.get(col.getKey()).toString();
					}
					else
					{
						strParentValue="";
					}
					if(strValue.equals(strParentValue)==true && i<iEnd)
					{
						intParent=colParent.get(col.getKey());
						intThis=colThis.get(col.getKey());
						intThat=intThis.clone();
						intParentThis=intParent.clone();
						intParentThis[0]++;
						intThat[2]=0;
						colThis.put(col.getKey(), intThat);
						colParent.put(col.getKey(), intParentThis);
					}
					else
					{
						rowParentIt=rowList.get(i);
					}
				}
			}
		}
		return rowList;
	}

	@Override
	public String queryBigSql(String name, String strSql, Object[] params) throws Exception {
		String strKey;
		Map<String, Object> mapInfo;
		mapInfo=this.queryFullSql(name, strSql, params);
		strKey=this.getKeybyTabCol("com", "sc_redis", "id");
		redisDao.addOrUpdate(strKey, mapInfo);
		return strKey;
	}
	
	@Override
	public String saveRedisObj(Object objInfo) throws Exception
	{
		String strKey;
		strKey=this.getKeybyTabCol("com", "sc_redis", "id");
		redisDao.addOrUpdate(strKey, objInfo);
		return strKey;
	}
	
	@Override
	public Object loadRedisObj(String strId) throws Exception
	{
		Object objInfo;
		objInfo=redisDao.load(strId);
		return objInfo;
	}
	
	@Override
	public List<Map> loadMapByReadisKey(String strKey)
	{
		List<Map> list;
		list=(List<Map>)redisDao.load(strKey);
		redisDao.delete(strKey);
		return list;
	}

	@Override
	public List<Map> loadMapByReadisKeyNoDel(String strKey) {
		List<Map> list;
		list=(List<Map>)redisDao.load(strKey);
		return list;
	}

	@Override
	public void saveMapByReadisKeyNoDel(String strKey, List<Map> list) {
		redisDao.addOrUpdate(strKey, list);
	}

	@Override
	public void saveDeptPostUsers(String deptPostid, String usersid) throws Exception {
		StringBuilder strDelSqlInfo;
		String strInsertSqlInfo;
		String[] strArr;
		String strKey;
		List<Object> delParams;
		List<Object> insertParams;
		delParams=new ArrayList<Object>();
		strDelSqlInfo=new StringBuilder();
		strArr=null;
		strDelSqlInfo.append("DELETE FROM sc_user_post WHERE por_id=? ");
		delParams.add(deptPostid);
		if("".equals(usersid)==false)
		{
			strArr=usersid.split(",");
			strDelSqlInfo.append(" AND user_id NOT IN ( ");
			for(int i=0;i<strArr.length;i++)
			{
				if(i!=0){
					strDelSqlInfo.append(" union ");
				}
				strDelSqlInfo.append(" select ? as userid ");
				delParams.add(strArr[i]);
			}
			strDelSqlInfo.append(" ) ");
		}
		commDbDao.execNoListSql(commDbDao.getDataSource("shiro"), strDelSqlInfo.toString(),delParams.toArray());
		if(null!=strArr)
		{
			for(int i=0;i<strArr.length;i++)
			{
				insertParams=new ArrayList<Object>();
				strKey=this.getKeybyTabCol("shiro", "sc_user_post", "id");
				strInsertSqlInfo="INSERT INTO sc_user_post (id,por_id,user_id) ";
				strInsertSqlInfo+=" SELECT DISTINCT ? AS id,? AS por_id,? AS userid ";
				strInsertSqlInfo+=" FROM sc_post_org ";
				strInsertSqlInfo+=" JOIN ( ";
				strInsertSqlInfo+=" 	SELECT COUNT(*) AS usercount,? AS por_id,? AS userid ";
				strInsertSqlInfo+=" 	FROM sc_user_post WHERE por_id=? AND user_id = ? "; 
				strInsertSqlInfo+=" )AS t ";
				strInsertSqlInfo+=" ON sc_post_org.id=t.por_id ";
				strInsertSqlInfo+=" WHERE t.usercount=0 ";
				insertParams.add(strKey);
				insertParams.add(deptPostid);
				insertParams.add(strArr[i]);
				insertParams.add(deptPostid);
				insertParams.add(strArr[i]);
				insertParams.add(deptPostid);
				insertParams.add(strArr[i]);
				commDbDao.execNoListSql(commDbDao.getDataSource("shiro"), strInsertSqlInfo,insertParams.toArray());
			}
		}
	}

	@Override
	public Map<String, Object> getQueryTotalByQuery(Map<String, Object> mapQuery,String grouptype,String key, Object[] params) throws Exception {
		Map<String,Object> retMap;
		retMap=null;
		retMap=commDbDao.getQueryTotalList(mapQuery,grouptype,key,params);
		return retMap;
	}

	@Override
	public Map<String, Object> getQueryDynamicTotalByQuery(
			Map<String, Object> mapQuery, String grouptype,
			String key, String showvalue, Object[] params) throws Exception {
		Map<String,Object> retMap;
		retMap=null;
		retMap=commDbDao.getQueryDynamicTotalByQuery(mapQuery,grouptype,key,showvalue,params);
		return retMap;
	}
}
