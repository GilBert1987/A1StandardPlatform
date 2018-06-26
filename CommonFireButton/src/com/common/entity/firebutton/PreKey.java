package com.common.entity.firebutton;

import java.io.Serializable;

public class PreKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4680187981723198773L;
	
	private String id;
	private String keyinfo;
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	private String typeinfo;
	private int numinfo;
	private long num;
	
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyinfo() {
		return keyinfo;
	}
	public void setKeyinfo(String keyinfo) {
		this.keyinfo = keyinfo;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTypeinfo() {
		return typeinfo;
	}
	public void setTypeinfo(String typeinfo) {
		this.typeinfo = typeinfo;
	}
	public int getNuminfo() {
		return numinfo;
	}
	public void setNuminfo(int numinfo) {
		this.numinfo = numinfo;
	}
}
