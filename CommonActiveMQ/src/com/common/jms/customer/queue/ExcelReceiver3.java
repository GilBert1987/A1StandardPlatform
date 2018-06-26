package com.common.jms.customer.queue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import com.caucho.hessian.io.HessianInput;
import com.common.entity.query.Column;
import com.common.entity.query.PageStyle;
import com.common.entity.query.Query;
import com.common.entity.query.RowInfo;
import com.common.entity.query.Search;
import com.common.entity.query.ShowStyle;
import com.common.entity.query.Title;
import com.common.service.CommonService;
import com.common.service.QueueSenderService;

/**
 * 
 * @author liang
 * @description  队列消息监听器
 * 
 */
@Component("excelReceiver3")
public class ExcelReceiver3 implements MessageListener {
	
	private static Logger log = Logger.getLogger(ExcelReceiver3.class);
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private QueueSenderService queueSender;
	
	@Override
	public void onMessage(Message message) {
		File outfile;
		File[] files;
		File fileInfo;
		String strMsg;
		String strKey;
		Query queryObj;
		Object[] params;
		Integer[] iList;
		Integer[] iShow;
		String strWhere;
		String strMapKey;
		String strMapVal;
		String strSqlInfo;
		String strCurrKey;
		String strSendMsg;
		String strQueryId;
		String strTitleId;
		Iterator iterShow;
		Iterator iterData;		
		JSONObject jsonMsg;
		String strRelColId;
		String strTitColId;
		String strParamKey;
		String strExtendKey;
		String strCurrValId;
		String strCurrColId;
		List<Column> colList;
		JSONArray jsonParams;
		String strRelColValue;
		JSONObject jsonExtend;
		JSONArray jsonExtends;
		JSONObject jsonSubObj;
		JSONArray jsonSubList;
		List<RowInfo> listRow;
		ObjectInputStream ois;
		Object[] updateParams;
		boolean blHasMoreTable;
		List<Map> listTitleMap;
		SXSSFWorkbook workBook;
		JSONArray jsonExtendCol;
		List<Map> listMapDataShow;
		List<Map> listMapDataInfo;
		List<Map> listBodyDataMap;
		Map<String,Map> mapInfoList;
		Map<String, Object> mapInfo;
		JSONArray jsonExtendCountCol;
		Map<String,List<Map>> mapList;
		Map<String,List<Map>> mapShow;
		Map<String,String> mapDataInfo;
		Map<String, Integer[]> mapUnit;
		Map<String,Object> excelMapInfo;
		FileOutputStream fileOutputStream;
		Map<String, Integer[]> mapShowInfo;
		Map.Entry<String, String> mapEntryDataInfo;
		Map.Entry<String, Integer[]> mapEntryShowInfo;
		ois=null;
		strKey="";
		outfile=null;
		jsonExtend=null;
		files=new File[1];
		params=new Object[2];
		blHasMoreTable=false;
		listBodyDataMap=null;
		fileOutputStream=null;
		updateParams=new Object[1];
		mapInfoList=new HashMap<String,Map>();
		try {
			strMsg=((TextMessage)message).getText();
			if("".equals(strMsg)==false)
			{
				jsonMsg=JSONObject.fromObject(strMsg);
				strKey=jsonMsg.getString("key");
				strTitleId=jsonMsg.getString("titleid");
				strQueryId=jsonMsg.getString("queryid");
				strSqlInfo=jsonMsg.getString("sql");
				strWhere=jsonMsg.getString("where");
				jsonSubList=jsonMsg.getJSONArray("subList");
				jsonExtends=jsonMsg.getJSONArray("extend");
				jsonParams=jsonMsg.getJSONArray("listParams");
				jsonExtendCol=jsonMsg.getJSONArray("extendcol");
				jsonExtendCountCol=jsonMsg.getJSONArray("extendcountcol");
				outfile=new File("../tmp/"+strKey+".xlsx");
				files[0]=outfile;
				fileOutputStream=new FileOutputStream(outfile);
				listTitleMap=(List<Map>)commonService.loadRedisObj(strTitleId);
				queryObj=(Query)commonService.loadRedisObj(strQueryId);
				params[0]=4;
				params[1]=0;
				commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
				workBook=getNewWorkbook();
				if(1<jsonSubList.size())
				{
					blHasMoreTable=true;
				}
				for(int i=0;i<jsonExtends.size();i++){
					jsonExtend=jsonExtends.getJSONObject(i);
					strExtendKey=jsonExtend.getString("key");
					strParamKey=jsonExtend.getString("paramKey");
					mapInfo=(Map)commonService.loadRedisObj(strParamKey);
					mapInfoList.put(strExtendKey, mapInfo);
				}
				mapList=new HashMap<String,List<Map>>();
				mapShow=new HashMap<String,List<Map>>();
				colList=queryObj.getColumns();
				for(Column colInfo:colList){
					if("1".equals(colInfo.getTypeid())==true){
						strRelColId = colInfo.getRelColId();
						strCurrValId = colInfo.getCurrValId();
						strCurrColId = colInfo.getCurrColId();
						strTitColId = colInfo.getTitColId();
						mapInfo=mapInfoList.get(colInfo.getKey());
						listBodyDataMap=(List<Map>)mapInfo.get("retMap");
						if(null!=listBodyDataMap)
						{
							for(Map dataMapInfo:listBodyDataMap){
								if(null!=dataMapInfo.get(strCurrValId) && null!=dataMapInfo.get(strTitColId) && null!=dataMapInfo.get(strCurrColId)){
									iList=new Integer[3];
									iList[0]=1;
									iList[1]=1;
									strRelColValue=dataMapInfo.get(strCurrValId).toString();
									mapShowInfo=new HashMap<String,Integer[]>();
									mapDataInfo=new HashMap<String,String>();
									if("1".equals(colInfo.getTitle().getIsshow()))
									{
										iList[2]=1;
									}
									else
									{
										iList[2]=0;
									}
									mapShowInfo.put(colInfo.getKey()+"_"+dataMapInfo.get(strTitColId).toString(), iList);
									mapDataInfo.put(colInfo.getKey()+"_"+dataMapInfo.get(strTitColId).toString(), dataMapInfo.get(strCurrColId).toString());
									if(null!=mapShow.get(colInfo.getKey()+"_"+strRelColValue))
									{
										listMapDataShow=mapShow.get(colInfo.getKey()+"_"+strRelColValue);
										listMapDataShow.add(mapShowInfo);
									}
									else
									{
										listMapDataShow=new ArrayList<Map>();
										listMapDataShow.add(mapShowInfo);
										mapShow.put(colInfo.getKey()+"_"+strRelColValue, listMapDataShow);
									}
									if(null!=mapList.get(colInfo.getKey()+"_"+strRelColValue)){
										listMapDataInfo=mapList.get(colInfo.getKey()+"_"+strRelColValue);
										listMapDataInfo.add(mapDataInfo);
									}
									else
									{
										listMapDataInfo=new ArrayList<Map>();
										listMapDataInfo.add(mapDataInfo);
										mapList.put(colInfo.getKey()+"_"+strRelColValue, listMapDataInfo);
									}
								}
							}
						}
					}
				}
				for(int x=0;x<jsonSubList.size();x++)
				{
					jsonSubObj=jsonSubList.getJSONObject(x);
					strCurrKey=jsonSubObj.getString("key");
					listRow=fileLoadByKey(strCurrKey);
					updateParams[0]=2;
					commonService.updateObj("com", "sc_exportexcel", "se_status", updateParams, "and id='"+strCurrKey+"'");
					fileInfo=new File("../tmp/"+strCurrKey+".bin");
					fileInfo.delete();
					colList=queryObj.getColumns();
					for(Column colInfo:colList){
						if("1".equals(colInfo.getTypeid())==true){
							strRelColId = colInfo.getRelColId();
							mapInfo=mapInfoList.get(colInfo.getKey());
							listBodyDataMap=(List<Map>)mapInfo.get("retMap");
							if(null!=listBodyDataMap)
							{
								for(RowInfo rowInfo:listRow)
								{
									excelMapInfo=rowInfo.getRowInfo();
									mapUnit=rowInfo.getColUnit();
									if(null!=excelMapInfo.get(strRelColId)){
										strRelColValue=excelMapInfo.get(strRelColId).toString();
										if(null!=strRelColValue && "".equals(strRelColValue)==false)
										{
											if(null!=mapShow.get(colInfo.getKey()+"_"+strRelColValue))
											{
												listMapDataShow=mapShow.get(colInfo.getKey()+"_"+strRelColValue);
												for(Map mapDataShow:listMapDataShow)
												{
													iterShow=mapDataShow.entrySet().iterator();
													while(iterShow.hasNext())
													{
														mapEntryShowInfo=(Entry<String, Integer[]>) iterShow.next();
														strMapKey=mapEntryShowInfo.getKey();
														iShow=mapEntryShowInfo.getValue();
														mapUnit.put(strMapKey, iShow);
													}
												}
											}
											if(null!=mapList.get(colInfo.getKey()+"_"+strRelColValue))
											{
												listMapDataInfo=mapList.get(colInfo.getKey()+"_"+strRelColValue);
												for(Map mapInfoData:listMapDataInfo)
												{
													iterData=mapInfoData.entrySet().iterator();
													while(iterData.hasNext())
													{
														mapEntryDataInfo=(Entry<String, String>)iterData.next();
														strMapKey=mapEntryDataInfo.getKey();
														strMapVal=mapEntryDataInfo.getValue();
														excelMapInfo.put(strMapKey, strMapVal);
													}
												}
											}
										}
									}
								}
							}
						}
					}
					updateParams[0]=3;
					commonService.updateObj("com", "sc_exportexcel", "se_status", updateParams, "and id='"+strCurrKey+"'");
					exportExcelStream(workBook,queryObj,listTitleMap,listRow,fileOutputStream,(x+1),blHasMoreTable,jsonExtendCol,jsonExtendCountCol,jsonParams,strSqlInfo,strWhere);
					updateParams[0]=4;
					commonService.updateObj("com", "sc_exportexcel", "se_status", updateParams, "and id='"+strCurrKey+"'");
				}
				writeWorkBook(workBook,fileOutputStream);
				params[0]=4;
				params[1]=1;
				commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
				strSendMsg="{\"key\":\""+strKey+"\",\"filename\":\""+strKey+".xlsx\"}";
				queueSender.send("4", strSendMsg);
			}
		}
		catch (Exception e) {
			params[0]=0;
			params[1]=-1;
			try {
				commonService.updateObj("com", "sc_fileinfo", "type,status", params, "and id='"+strKey+"'");
			} catch (Exception e1) {
				log.error(e1.toString(),e1);
			}
			log.error(e.toString(),e);
		}
		finally{
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(fileOutputStream);
		}
	}

	private List<RowInfo> fileLoadByKey(String strRedisKey) {
		File fileInfo;
		List<RowInfo> list;
		HessianInput hinput;
		FileInputStream fileInStream;
		BufferedInputStream bufferedIn;
		list=null;
		bufferedIn=null;
		fileInStream=null;
		try
		{
			fileInfo=new File("../tmp/"+strRedisKey+".bin");
			fileInStream=new FileInputStream(fileInfo);
			bufferedIn=new BufferedInputStream(fileInStream);
			hinput = new HessianInput(bufferedIn);  
			list=(List<RowInfo>)hinput.readObject();
		}
		catch(Exception e)
		{
			log.error(e.toString(),e);
		}
		finally{
			IOUtils.closeQuietly(bufferedIn);
			IOUtils.closeQuietly(fileInStream);
		}
		return list;
	}
	
	private SXSSFWorkbook getNewWorkbook(){
		SXSSFWorkbook workbook;
		workbook=new SXSSFWorkbook(100);
		return workbook;
	}
	
	private void writeWorkBook(SXSSFWorkbook wb,OutputStream outStream){
		try  
        {  
			wb.write(outStream);
            outStream.flush();
        }  
        catch (IOException e)  
        {  
            log.error(e.toString(),e);
        }
		finally{
			IOUtils.closeQuietly(outStream);
		}
	}
	
	@SuppressWarnings("resource")
	private void exportExcelStream(SXSSFWorkbook workbook,Query query,List<Map> listTitleMap, List<RowInfo> list,OutputStream outStream, int iIndex, boolean blHasMoreTable, JSONArray jsonExtendCol,JSONArray jsonExtendCountCol,JSONArray jsonParams,String strSqlInfo,String strWhere) {
		Row row;
		Map mapRow;
		int initCol;
		int initRow;
		Title title;
		Map mapInfo;
		Map colInfo;
		Sheet sheet;
		RowInfo rowInfo;
		Cell headCell;
		Cell bodyCell;
		Search searchInfo;
		List<Map> listRow;
		List<Column> cols;
		boolean blShowNum;
		boolean blHasHead;
		boolean blHasGroup;
		ShowStyle showStyle;
		PageStyle pageStyle;
		Integer[] intColUnit;
		List<Map> showCol;
		List<ShowStyle> showList;
		Font headFont;
		Font bodyFont;
		Font titleFont;
		Font sequenFont;
		CellStyle headStyle;
		CellStyle titleStyle;
		CellStyle sequenStyle;
		String strSearchCommon;
		String strSearchSqlInfo;
		CellStyle bodyleftStyle;
		CellStyle bodycenterStyle;
		CellStyle bodyrightStyle;
		CellRangeAddress rangeAddress;
		Map<String, Integer[]> colUnit;
		blShowNum=false;
		initCol=0;
		initRow=2;
		blHasGroup=false;
		blHasHead=false;
		searchInfo=null;
		showCol=new ArrayList<Map>();
		bodyFont=workbook.createFont();
		titleFont=workbook.createFont();
		headFont=workbook.createFont();
		sequenFont=workbook.createFont();
		headStyle=workbook.createCellStyle();
		titleStyle=workbook.createCellStyle();
		sequenStyle=workbook.createCellStyle();
		bodyleftStyle=workbook.createCellStyle();
		bodyleftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		bodyleftStyle.setAlignment(HorizontalAlignment.LEFT);
		bodyleftStyle.setFont(bodyFont);
		bodycenterStyle=workbook.createCellStyle();
		bodycenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		bodycenterStyle.setAlignment(HorizontalAlignment.CENTER);
		bodycenterStyle.setFont(bodyFont);
		bodyrightStyle=workbook.createCellStyle();
		bodyrightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		bodyrightStyle.setAlignment(HorizontalAlignment.RIGHT);
		bodyrightStyle.setFont(bodyFont);
		headStyle.setAlignment(HorizontalAlignment.CENTER);
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		sequenStyle.setAlignment(HorizontalAlignment.CENTER);
		sequenStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		bodyFont.setFontName("微软雅黑");  
		bodyFont.setFontHeightInPoints((short) 11);
		titleFont.setFontName("微软雅黑");  
		titleFont.setFontHeightInPoints((short) 12);
		sequenFont.setFontName("微软雅黑");  
		sequenFont.setFontHeightInPoints((short) 12);
		headFont.setFontName("微软雅黑");  
		headFont.setFontHeightInPoints((short) 11);
		headStyle.setFont(headFont);
		titleStyle.setFont(titleFont);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		sequenStyle.setFont(sequenFont);
		cols=query.getColumns();
		for(Column colObj :cols){
			title=colObj.getTitle();
			if("".equals(title.getSehead())==false && "1".equals(title.getIsshow()))
			{
				blHasHead=true;
				break;
			}
		}
		for(Column colObj :cols){
			title=colObj.getTitle();
			if("1".equals(title.getIsshow()) && null != colObj.getGrouptype() && "".equals(colObj.getGrouptype())==false && "0".equals(colObj.getGrouptype())==false)
			{
				blHasGroup=true;
				break;
			}
		}
		if(false==blHasMoreTable)
		{
			sheet = workbook.createSheet(query.getName());
		}
		else
		{
			sheet = workbook.createSheet(query.getName()+"_"+iIndex);
		}
		sheet.setDefaultColumnWidth(15);
		for(int i=0;i<listTitleMap.size();i++)
		{
			colInfo=listTitleMap.get(i);
			if("1".equals(colInfo.get("isshow"))==true){
				showCol.add(colInfo);
			}
		}
		row = sheet.createRow(0);
        headCell=row.createCell(0);
        headCell.setCellStyle(titleStyle);
        headCell.setCellValue(query.getName());
        pageStyle=query.getPagestyle();
        showList=pageStyle.getShowlist();
        for(int i=0;i<showList.size();i++)
        {
        	showStyle=showList.get(i);
        	if("6".equals(showStyle.getType())==true && "1".equals(showStyle.getIsshow())==true){
        		blShowNum=true;
        		break;
        	}
        }
        if(false==blHasHead)
        {
        	getSingleTableHeadByQuery(query,sheet,blShowNum,initCol,headCell,sequenStyle,showCol,headStyle);
        }
        else
        {
        	getMultipleTableHeadByQuery(query,sheet,blShowNum,initCol,headCell,sequenStyle,showCol,headStyle,jsonExtendCol);
        	initRow++;
        }
        if(true==blHasGroup){
        	getGroupTableHeadByQuery(query,sheet,blShowNum,initRow,initCol,headCell,sequenStyle,showCol,headStyle,jsonExtendCol,jsonExtendCountCol,jsonParams,strSqlInfo,strWhere);
        	initRow++;
        }
        sheet.createFreezePane(0,initRow,0,initCol+showCol.size()-1);
        for(int i=0;i<list.size();i++)
        {
        	row = sheet.createRow(initRow+i);
        	rowInfo=list.get(i);
        	mapInfo=rowInfo.getRowInfo();
        	colUnit=rowInfo.getColUnit();
        	if(true==blShowNum)
        	{
        		bodyCell=row.createCell(0);
        		bodyCell.setCellValue(rowInfo.getSerialNum());
        		bodyCell.setCellStyle(sequenStyle);
        		initCol=1;
        	}
        	for(int j=0;j<showCol.size();j++)
        	{
        		colInfo=showCol.get(j);
        		if(null!=colInfo.get("id") && null!=mapInfo.get(colInfo.get("id").toString()))
        		{
        			intColUnit=colUnit.get(colInfo.get("id").toString());
        			if(1==intColUnit[2])
        			{
        				searchInfo=null;
        				bodyCell=row.createCell(initCol+j);
        				for(Column colObj :cols){
        					if(colObj.getKey().equals(colInfo.get("id").toString()))
        					{
        						searchInfo=colObj.getSearch();
        						break;
        					}
        				}
        				if(null!=searchInfo && "sql".equals(searchInfo.getDatatype()) && "select".equals(searchInfo.getType()) && "".equals(searchInfo.getSearchinfo())==false)
        				{
        					listRow=null;
        					strSearchCommon=query.getSysid();
        					strSearchSqlInfo=searchInfo.getSearchinfo();
        					try {
        						listRow=commonService.queryListSql(strSearchCommon, strSearchSqlInfo, null);
							} catch (Exception e) {
								log.error(e.toString(),e);
							}
        					if(null!=listRow)
        					{
        						for(int x=0;x<listRow.size();x++)
        						{
        							mapRow=listRow.get(x);
        							if(null!=mapRow && null!=mapRow.get("value"))
        							{
        								if(mapRow.get("value").equals(mapInfo.get(colInfo.get("id").toString()).toString())){
        									mapInfo.put(colInfo.get("id").toString(),mapRow.get("code"));
        									break;
        								}
        							}
        						}
        					}
        				}
        				bodyCell.setCellValue(mapInfo.get(colInfo.get("id").toString()).toString());
        				if("left".equals(colInfo.get("align").toString())==true){
        					bodyCell.setCellStyle(bodyleftStyle);
        				}
        				if("center".equals(colInfo.get("align").toString())==true){
        					bodyCell.setCellStyle(bodycenterStyle);
        				}
        				if("right".equals(colInfo.get("align").toString())==true){
        					bodyCell.setCellStyle(bodyrightStyle);
        				}
        				if(1!=intColUnit[0] || 1!=intColUnit[1])
        				{
        					rangeAddress=new CellRangeAddress(initRow+i,initRow+i+intColUnit[0]-1,initCol+j,initCol+j+intColUnit[1]-1);
        					sheet.addMergedRegion(rangeAddress);
        				}
        			}
        		}
        	}
        }
	}
	
	private void getGroupTableHeadByQuery(Query query, Sheet sheet,
			boolean blShowNum,int initRow, int initCol, Cell headCell,
			CellStyle sequenStyle, List<Map> showCol, CellStyle headStyle,
			JSONArray jsonExtendCol,JSONArray jsonExtendCountCol,JSONArray jsonParams,String strSqlInfo,String strWhere) {
		Row row;
		Title title;
		int iColInfo;
		int iDynamicWidth;
		List<Column> cols;
		List<Map> mapList;
		boolean blHasGroup;
		String strTitleKey;
		String strTitleVal;
		String strCountCol;
		String strTitleSysId;
		String strCountSysId;
		List<Map> rowListMap;
		List<Map> mapCountList;
		String strCurrCountCol;
		String strTitleSqlInfo;
		String strCountSqlInfo;
		List<Object> listParam;
		Map<String,Object> sqlMap;
		JSONObject jsonObjExtendCol;
		Map<String,Object> sqlCountMap;
		Map<String, Object> mapRowInfo;
		Map<String, Object> retTotalMap;
		Map<String, Object> retCountMap;
		JSONObject jsonObjCountExtendCol;
		sqlMap=new HashMap<String,Object>();
		iColInfo=0;
		mapList=null;
		mapCountList=null;
		listParam=new ArrayList<Object>();
		row = sheet.createRow(initRow);
		if (true == blShowNum) {
			initCol = 1;
			headCell = row.createCell(0);
			headCell.setCellValue("合计");
			headCell.setCellStyle(sequenStyle);
		}
		for(int i=0;i<jsonParams.size();i++)
		{
			listParam.add(jsonParams.getString(i));
		}
		sqlMap.put("sysid",query.getSysid());
		sqlMap.put("sqllist", JSONObject.fromObject("{\"sql\":\""+strSqlInfo.replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",\"where\":\""+strWhere.replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\"}"));
		cols=query.getColumns();
		for(Column colObj :cols){
			title=colObj.getTitle();
			blHasGroup=false;
			if("1".equals(title.getIsshow()) && "0".equals(colObj.getTypeid())==true && null != colObj.getGrouptype() && "".equals(colObj.getGrouptype())==false && "0".equals(colObj.getGrouptype())==false)
			{
				blHasGroup=true;
				try {
					retTotalMap=commonService.getQueryTotalByQuery(sqlMap,colObj.getGrouptype(),colObj.getKey(),listParam.toArray());
					if(null!=retTotalMap)
					{
						rowListMap=(List<Map>)retTotalMap.get("retMap");
						if(null!=rowListMap && 1==rowListMap.size()){
							mapRowInfo=(Map<String, Object>)rowListMap.get(0);
							headCell = row.createCell(initCol+iColInfo);
							if(null!=mapRowInfo.get(colObj.getKey()))
							{
								headCell.setCellValue(mapRowInfo.get(colObj.getKey()).toString());
							}
							headCell.setCellStyle(sequenStyle);
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
				iColInfo++;
			}
			if("1".equals(title.getIsshow())==true && "1".equals(colObj.getTypeid())==true  && null != colObj.getGrouptype() && "".equals(colObj.getGrouptype())==false && "0".equals(colObj.getGrouptype())==false)
			{
				blHasGroup=true;
				strTitleKey=colObj.getKey();
				strTitleSysId=colObj.getTitleSysId();
				strCountCol=colObj.getTitColId();
				strCurrCountCol=colObj.getCurrColId();
				strCountSysId=colObj.getDataSysId();
				strTitleSqlInfo="";
				strCountSqlInfo="";
				sqlCountMap=new HashMap<String,Object>();
				sqlCountMap.put("sysid",strCountSysId);
				for(int i=0;i<jsonExtendCol.size();i++)
				{
					jsonObjExtendCol=jsonExtendCol.getJSONObject(i);
					if(strTitleKey.equals(jsonObjExtendCol.getString("key"))==true)
					{
						strTitleSqlInfo=jsonObjExtendCol.getString("sqlinfo");
						break;
					}
				}
				for(int i=0;i<jsonExtendCountCol.size();i++)
				{
					jsonObjCountExtendCol=jsonExtendCountCol.getJSONObject(i);
					if(strTitleKey.equals(jsonObjCountExtendCol.getString("key"))==true)
					{
						strCountSqlInfo=jsonObjCountExtendCol.getString("sqlinfo");
						break;
					}
				}
				sqlCountMap.put("sqllist", JSONObject.fromObject("{\"sql\":\""+strCountSqlInfo.replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\",\"where\":\""+strWhere.replace("\"", "\\\"").replaceAll("[\\t\\n\\r]", " ")+"\"}"));
				if("".equals(strTitleKey)==false && "".equals(strTitleSysId)==false  && "".equals(strTitleSqlInfo)==false)
				{
					retCountMap=null;
					try {
						mapList=commonService.queryListSql(strTitleSysId, strTitleSqlInfo, null);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
					try {
						retCountMap=commonService.getQueryDynamicTotalByQuery(sqlCountMap, colObj.getGrouptype(), strCountCol, strCurrCountCol, listParam.toArray());
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
					if(null!=mapList && null!=retCountMap)
					{
						mapCountList=(List<Map>)retCountMap.get("retMap");
						iDynamicWidth=mapList.size();
						for(int i=0;i<iDynamicWidth;i++)
						{
							strTitleVal="";
							if(null!=mapList.get(i).get("code")){
								strTitleVal=mapList.get(i).get("code").toString();
								for(int j=0;j<mapCountList.size();j++)
								{
									if(null!=mapCountList.get(j).get(strCountCol) && strTitleVal.equals(mapCountList.get(j).get(strCountCol).toString())==true)
									{
										if(null!=mapCountList.get(j).get(strCurrCountCol))
										{
											headCell = row.createCell(initCol + iColInfo + i);
											headCell.setCellValue(mapCountList.get(j).get(strCurrCountCol).toString());
											headCell.setCellStyle(headStyle);
										}
										break;
									}
								}
							}
							
						}
						iColInfo=iColInfo+iDynamicWidth;
					}
				}
			}
			if(false==blHasGroup && "1".equals(title.getIsshow())==true)
			{
				iColInfo++;
			}
		}
	}

	private void getSingleTableHeadByQuery(Query query,Sheet sheet,boolean blShowNum,int initCol,Cell headCell,CellStyle sequenStyle,List<Map> showCol,CellStyle headStyle)
	{
		Row row;
		Map colInfo;
		CellRangeAddress rangeAddress;
		row = sheet.createRow(1);
		if (true == blShowNum) {
			initCol = 1;
			headCell = row.createCell(0);
			headCell.setCellValue("序号");
			headCell.setCellStyle(sequenStyle);
			rangeAddress = new CellRangeAddress(0, 0, 0, showCol.size());
			sheet.addMergedRegion(rangeAddress);
		} else {
			rangeAddress = new CellRangeAddress(0, 0, 0, showCol.size() - 1);
			sheet.addMergedRegion(rangeAddress);
		}
		for (int i = 0; i < showCol.size(); i++) {
			headCell = row.createCell(initCol + i);
			colInfo = showCol.get(i);
			headCell.setCellValue(colInfo.get("title").toString());
			headCell.setCellStyle(headStyle);
		}
	}
	
	private void getMultipleTableHeadByQuery(Query query,Sheet sheet,boolean blShowNum,int initCol,Cell headCell,CellStyle sequenStyle,List<Map> showCol,CellStyle headStyle, JSONArray jsonExtendCol)
	{
		Row row;
		Title title;
		Map colInfo;
		int iColInfo;
		int iColWidth;
		int iRangeCount;
		String strSeHead;
		int iStaticWidth;
		int iDynamicWidth;
		List<Map> mapList;
		String strTitleKey;
		String strTitleSysId;
		String strTitleSqlInfo;
		List<Column> columnList;
		JSONObject jsonObjExtendCol;
		CellRangeAddress rangeAddress;
		iColInfo=0;
		strSeHead="";
		mapList=null;
		columnList=query.getColumns();
		row = sheet.createRow(1);
		if (true == blShowNum) {
			initCol = 1;
			iStaticWidth=1;
			iColWidth=0;
		}
		else
		{
			initCol = 0;
			iStaticWidth=0;
			iColWidth=1;
		}
		for(Column col:columnList){
			title=col.getTitle();
			if("1".equals(title.getIsshow())==true && "0".equals(col.getTypeid())==true)
			{
				if(true==strSeHead.equals(title.getSehead())){
					iStaticWidth++;
					iColInfo++;
					continue;
				}
				iRangeCount=((initCol + iColInfo - iStaticWidth) < 0? 0 :(initCol + iColInfo - iStaticWidth));
				if(0 != (iRangeCount-(iColInfo-iColWidth)) && iRangeCount < (iColInfo-iColWidth))
				{
					rangeAddress = new CellRangeAddress(1, 1, iRangeCount, iColInfo-iColWidth);
					sheet.addMergedRegion(rangeAddress);
				}
				if(false==strSeHead.equals(title.getSehead()))
				{
					iStaticWidth=1;
					strSeHead=title.getSehead();
				}
				headCell = row.createCell(initCol + iColInfo);
				headCell.setCellValue(strSeHead);
				headCell.setCellStyle(headStyle);
				iColInfo++;
			}
			if("1".equals(title.getIsshow())==true && "1".equals(col.getTypeid())==true)
			{
				if(1 != iStaticWidth && 0 != iStaticWidth)
				{
					iRangeCount=((iColInfo - (iStaticWidth-1))<0?0:(initCol + iColInfo - iStaticWidth));
					rangeAddress = new CellRangeAddress(1, 1, iRangeCount, iColInfo-iColWidth);
					sheet.addMergedRegion(rangeAddress);
					iStaticWidth=1;
				}
				strTitleKey=col.getKey();
				strTitleSysId=col.getTitleSysId();
				strTitleSqlInfo="";
				for(int i=0;i<jsonExtendCol.size();i++)
				{
					jsonObjExtendCol=jsonExtendCol.getJSONObject(i);
					if(strTitleKey.equals(jsonObjExtendCol.getString("key"))==true)
					{
						strTitleSqlInfo=jsonObjExtendCol.getString("sqlinfo");
						break;
					}
				}
				if("".equals(strTitleKey)==false && "".equals(strTitleSysId)==false  && "".equals(strTitleSqlInfo)==false)
				{
					try {
						mapList=commonService.queryListSql(strTitleSysId, strTitleSqlInfo, null);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
					if(null!=mapList)
					{
						iDynamicWidth=mapList.size();
						if(1!=iDynamicWidth)
						{
							if(null!=title.getSehead() && "".equals(title.getSehead())==false)
							{
								strSeHead=title.getSehead();
							}
							else
							{
								strSeHead="";
							}
							headCell = row.createCell(initCol + iColInfo);
							headCell.setCellValue(strSeHead);
							headCell.setCellStyle(headStyle);
							rangeAddress = new CellRangeAddress(1, 1, initCol + iColInfo, initCol + iColInfo+iDynamicWidth-1);
							sheet.addMergedRegion(rangeAddress);
						}
						iColInfo=iColInfo+iDynamicWidth;
					}
				}
			}
		}
		if(1!=iStaticWidth)
		{
			iRangeCount=((iColInfo - (iStaticWidth-1))<0?0:(initCol + iColInfo - iStaticWidth));
			rangeAddress = new CellRangeAddress(1, 1, iRangeCount, iColInfo-iColWidth);
			sheet.addMergedRegion(rangeAddress);
		}
		row = sheet.createRow(2);
		if (true == blShowNum) {
			initCol = 1;
			headCell = row.createCell(0);
			headCell.setCellValue("序号");
			headCell.setCellStyle(sequenStyle);
			rangeAddress = new CellRangeAddress(0, 0, 0, showCol.size());
			sheet.addMergedRegion(rangeAddress);
		} else {
			rangeAddress = new CellRangeAddress(0, 0, 0, showCol.size() - 1);
			sheet.addMergedRegion(rangeAddress);
		}
		for (int i = 0; i < showCol.size(); i++) {
			headCell = row.createCell(initCol + i);
			colInfo = showCol.get(i);
			headCell.setCellValue(colInfo.get("title").toString());
			headCell.setCellStyle(headStyle);
		}
	}
}
