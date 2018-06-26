package com.common.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class RedisDao {
	
	private static Logger log = Logger.getLogger(RedisDao.class);
	
	@Resource
    protected RedisTemplate<Serializable, Integer> redisTemplate;
	
	@Resource
    protected RedisTemplate<String, Object> redisObjTemplate;
	
	private Map<String,String[]> mapOldKeyList=null;
	
	/*
	 * strNewKey 新建值
	 * strOldKey 上一键值
	 * blAdd 是否添加
	 */
	public Integer getRedisKey(final String strNewKey,final String strOldKey,final Boolean blAdd)
	{
		Integer iNumber;
		Long lNumber;
		String strOldKeyInfo;
		String strNewKeyInfo;
		String[] strOldKeyList;
		String[] strDelKeyList;
		RedisAtomicLong redisLong;
		strOldKeyList=null;
		strDelKeyList=null;
		strOldKeyInfo=new String(strOldKey);
		strNewKeyInfo=new String(strNewKey);
		if(null==mapOldKeyList)
		{
			mapOldKeyList=new HashMap<String,String[]>();
			strOldKeyList=new String[2];
			strOldKeyList[0]=new String(strNewKeyInfo);
			strOldKeyList[1]=new String(strOldKeyInfo);
			mapOldKeyList.put(new String(strNewKeyInfo), strOldKeyList);
		}
		if(false==blAdd)
		{
			if(null == mapOldKeyList.get(strNewKeyInfo))
			{
				strOldKeyList = new String[2];
				strOldKeyList[0]=new String(strNewKeyInfo);
				strOldKeyList[1]=new String(strOldKeyInfo);
				mapOldKeyList.put(new String(strNewKeyInfo), strOldKeyList);
			}
			if(null != mapOldKeyList.get(strOldKeyInfo))
			{
				strOldKeyList = mapOldKeyList.get(strOldKeyInfo);
			}
			strDelKeyList = getDelKeyList(strOldKeyList,strOldKeyInfo,3);
		}
		redisLong = new RedisAtomicLong(strNewKey, redisTemplate.getConnectionFactory());
		lNumber=redisLong.addAndGet(1);
		iNumber=lNumber.intValue();
		if(false==blAdd && null != strDelKeyList)
		{
			this.delete(strDelKeyList[1]);
			if(null!=mapOldKeyList.get(strDelKeyList[1]))
			{
				mapOldKeyList.remove(strDelKeyList[1]);
			}
		}
		return iNumber;
	}
	
	private String[] getDelKeyList(String[] strKeyList,String strOldKeyInfo,int iNumber){
		String[] strOldKeyList;
		String[] strDelKeyList;
		strDelKeyList=null;
		if(null != strKeyList)
		{
			strOldKeyList=mapOldKeyList.get(strKeyList[1]);
			if(0 != iNumber){
				if(null!=strOldKeyList)
				{
					strDelKeyList=getDelKeyList(strOldKeyList,strOldKeyInfo,(iNumber-1));
				}
			}
			else
			{
				if(null!=strOldKeyList && strOldKeyInfo.equals(strOldKeyList[1])==false)
				{
					strDelKeyList=mapOldKeyList.get(strOldKeyList[1]);
				}
			}
		}
		return strDelKeyList;
	}
	
	public void addOrUpdate(String key,Object obj) {
		redisObjTemplate.opsForValue().set(key, obj);
	}
	
	public Object load(String key) {
		return redisObjTemplate.opsForValue().get(key);
	}

	public void delete(String key) {
		redisObjTemplate.delete(key);
	}
}
