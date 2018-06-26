package com.common.db.transactionmanager;

import java.util.Map;
import javax.sql.DataSource;
import com.common.db.template.DbutilsTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class DSTransactionManager extends DataSourceTransactionManager{
	
	private static final long serialVersionUID = -2436115276489268353L;
	private DbutilsTemplate dbutilsTemplate;
	
	@Override
	public void afterPropertiesSet() {
		DataSource ds;
		Map<String,DataSource> mapDataSource;
		mapDataSource=dbutilsTemplate.getTargetSqlDataSources();
		ds=mapDataSource.get("wf");
		super.setDataSource(ds);
		super.afterPropertiesSet();
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
