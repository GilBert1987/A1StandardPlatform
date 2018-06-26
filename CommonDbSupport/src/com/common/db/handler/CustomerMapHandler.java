package com.common.db.handler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;


public class CustomerMapHandler implements ResultSetHandler<Map<String, Object>> {

	private static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();
	
	/**
     * Converts the first row in the <code>ResultSet</code> into a
     * <code>Map</code>.
     * @param rs <code>ResultSet</code> to process.
     * @return A <code>Map</code> with the values from the first row or
     * <code>null</code> if there are no rows in the <code>ResultSet</code>.
     *
     * @throws SQLException if a database access error occurs
     *
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    @Override
    public Map<String, Object> handle(ResultSet rs) throws SQLException {
    	int iColumnCount;
    	String strColName;
    	String strColType;
    	Map<String,Object> mapResult;
    	List<Map<String,Object>> mapList;
    	Map<String,String> mapColInfo;
    	ResultSetMetaData rsMetaData;
    	mapList=new ArrayList<Map<String,Object>>();
    	mapResult=new HashMap<String, Object>();
    	mapColInfo=new HashMap<String, String>();
    	rsMetaData=rs.getMetaData();
    	iColumnCount=rsMetaData.getColumnCount();
    	for(int i=0;i<iColumnCount;i++)
    	{
    		strColName=rsMetaData.getColumnName(i+1);
    		strColType=rsMetaData.getColumnTypeName(i+1);
    		mapColInfo.put(strColName, strColType);
    	}
    	while (rs.next()) {
    		mapList.add(this.handleRow(rs));
        }
    	mapResult.put("colInfo",mapColInfo);
    	if(null!=mapList)
    	{
    		mapResult.put("retMap",mapList);
    	}
        return mapResult;
    }

	private Map<String, Object> handleRow(ResultSet rs) throws SQLException {
		return ROW_PROCESSOR.toMap(rs);
	}
}
