package com.common.workflow.activiti.support;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.service.CommonService;
import com.common.service.WorkflowService;

public class DefaultTaskListener implements TaskListener {
	
	private static Logger logger = LoggerFactory.getLogger(DefaultTaskListener.class);
	
	@Resource
	private CommonService commService;
	
	@Resource
	private WorkflowService workflowService;
	
	public CommonService getCommService() {
		return commService;
	}

	public WorkflowService getWorkflowService() {
		return workflowService;
	}

    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        logger.debug("{}", this);
        logger.debug("{} : {}", eventName, delegateTask);

        if ("create".equals(eventName)) {
            try {
                this.onCreate(delegateTask);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        if ("assignment".equals(eventName)) {
            try {
                this.onAssignment(delegateTask);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        if ("complete".equals(eventName)) {
            try {
                this.onComplete(delegateTask);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        if ("delete".equals(eventName)) {
            try {
                this.onDelete(delegateTask);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        ((TaskEntity) delegateTask).setEventName(eventName);
    }

    public void onCreate(DelegateTask delegateTask) throws Exception {
    }

    public void onAssignment(DelegateTask delegateTask) throws Exception {
    }

    public void onComplete(DelegateTask delegateTask) throws Exception {
    }

    public void onDelete(DelegateTask delegateTask) throws Exception {
    }
}
