package com.common.workflow.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.workflow.activiti.cmd.CompleteTaskWithCommentCmd;
import com.common.workflow.activiti.cmd.DeleteTaskCandidateUserAndEmptyUserCommentCmd;
import com.common.workflow.activiti.support.DefaultTaskListener;

public class AutoCompleteFirstTaskListener extends DefaultTaskListener implements EventHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -241450557805439524L;
	
	private static final Logger logger = LoggerFactory.getLogger(AutoCompleteFirstTaskListener.class);
    
    @Override
    public void onCreate(DelegateTask delegateTask) throws Exception {
        ActivityImpl startActivity;
        PvmTransition pvmTransition;
        PvmActivity targetActivity;
        ProcessDefinitionEntity processDefinitionEntity;
        DeleteTaskCandidateUserAndEmptyUserCommentCmd cmd;
        processDefinitionEntity = Context.getProcessEngineConfiguration().getProcessDefinitionCache().get(delegateTask.getProcessDefinitionId());
        startActivity = processDefinitionEntity.getInitial();
        if (startActivity.getOutgoingTransitions().size() != 1) {
            throw new IllegalStateException(
                    "start activity outgoing transitions cannot more than 1, now is : "
                            + startActivity.getOutgoingTransitions().size());
        }
        pvmTransition = startActivity.getOutgoingTransitions().get(0);
        targetActivity = pvmTransition.getDestination();
        if (!"userTask".equals(targetActivity.getProperty("type"))) {
            logger.debug("first activity is not userTask, just skip");
            return;
        }
        cmd=new DeleteTaskCandidateUserAndEmptyUserCommentCmd(delegateTask.getId());
		cmd.execute(Context.getCommandContext());
        if (targetActivity.getId().equals(delegateTask.getExecution().getCurrentActivityId())) {
            if ((delegateTask.getCandidates().size()!=0)||("".equals(delegateTask.getAssignee())==false)) {
                logger.debug("auto complete first task : {}", delegateTask);
                new CompleteTaskWithCommentCmd(delegateTask.getId(), delegateTask.getVariables(),"发起流程").execute(Context.getCommandContext());
            }
        }
    }

	@Override
	public void handle(ActivitiEvent event) {
		ActivitiEntityEventImpl eventImpl;
		TaskEntity taskEntity;
		eventImpl=(ActivitiEntityEventImpl)event;
		taskEntity=(TaskEntity)eventImpl.getEntity();
		try {
			this.onCreate(taskEntity);
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}
}
