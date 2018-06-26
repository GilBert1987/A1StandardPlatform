package com.common.quartz.factory;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import com.common.db.template.DbutilsTemplate;
public class QuartzFactoryBean extends SchedulerFactoryBean{
	
	private DbutilsTemplate dbutilsTemplate;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		DataSource ds;
		Map<String, DataSource> mapDataSource;
		mapDataSource=dbutilsTemplate.getTargetSqlDataSources();
		ds=mapDataSource.get("qz");
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
