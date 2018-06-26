package com.common.service;

public interface TagShow {
	void setInfoname(String infoname);
	void setDataBind(String dataBind);
	void setShowtype(Integer itagtype);
	Integer getShowtype();
	String getDataBind();
	String getInfoname();
}
