package com.common.workflow.activiti.transaction;

import java.util.Map;
import javax.sql.DataSource;
import org.activiti.engine.ProcessEngine;
import org.activiti.spring.SpringProcessEngineConfiguration;
import com.common.db.template.DbutilsTemplate;

public class ProcessEngineConfiguration extends SpringProcessEngineConfiguration{
	
	private DbutilsTemplate dbutilsTemplate;

	@Override
	public ProcessEngine buildProcessEngine()
	{
		try
		{
			ProcessEngine processEngine = super.buildProcessEngine();
			return processEngine;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void initDataSource() {
		DataSource ds;
		Map<String, DataSource> mapDataSource;
		mapDataSource=dbutilsTemplate.getTargetSqlDataSources();
		ds=mapDataSource.get("wf");
		super.setDataSource(ds);
		super.initDataSource();
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
