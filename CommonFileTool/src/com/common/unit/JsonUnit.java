package com.common.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

public class JsonUnit {
	
	public static Map<String, Object> parseJSONToMap(String jsonStr) {
		Object v;
		List<Map<String, Object>> list;
		Iterator<JSONObject> it;
		Map<String, Object> map;
		JSONObject json;
		JSONObject json2;
		map = new HashMap<String, Object>();
		// 最外层解析
		json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				list = new ArrayList<Map<String, Object>>();
				it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					json2 = it.next();
					list.add(parseJSONToMap(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
	
	public static JSONArray jsonArrayNullValue(JSONArray arrList){
		Object v;
		JSONArray arrNewList;
		JSONObject jsonObject;
		JSONObject newJSONObject;
		arrNewList=new JSONArray();
		for(int i=0;i<arrList.size();i++)
		{
			newJSONObject=new JSONObject();
			jsonObject=arrList.getJSONObject(i);
			for (Object k : jsonObject.keySet()) {
				v = jsonObject.get(k);
				if(false==(v instanceof JSONNull))
				{
					newJSONObject.put(k.toString(),v);
				}
			}
			arrNewList.add(newJSONObject);
		}
		return arrNewList;
	}
	
	public static JSONObject jsonObjectNullValue(JSONObject jsonObject){
		Object v;
		JSONObject newJSONObject;
		newJSONObject=new JSONObject();
		for (Object k : jsonObject.keySet()) {
			v = jsonObject.get(k);
			if(false==(v instanceof JSONNull))
			{
				newJSONObject.put(k.toString(),v);
			}
		}
		return newJSONObject;
	}
}
