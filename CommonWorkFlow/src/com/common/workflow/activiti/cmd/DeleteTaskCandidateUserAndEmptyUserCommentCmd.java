package com.common.workflow.activiti.cmd;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteTaskCandidateUserAndEmptyUserCommentCmd implements Command<Object>{
	
	private static Logger log = LoggerFactory.getLogger(DeleteTaskCandidateUserAndEmptyUserCommentCmd.class);
	
	private String taskId;
	
	public DeleteTaskCandidateUserAndEmptyUserCommentCmd(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		String[] strArr;
		String[] strTemp;
		List<String[]> listEntity;
		TaskEntity taskEntity;
		TaskService taskService;
        List<IdentityLinkEntity> listIdent;
        IdentityLinkEntity idLinkEntity;
        ProcessDefinitionEntity processEntity;
        listEntity=new ArrayList<String[]>();
        taskService=commandContext.getProcessEngineConfiguration().getTaskService();
        taskEntity = commandContext.getTaskEntityManager().findTaskById(taskId);
        processEntity=commandContext.getProcessDefinitionEntityManager().findProcessDefinitionById(taskEntity.getProcessDefinitionId());
        taskEntity.setTenantId(taskEntity.getProcessInstance().getTenantId());
        listIdent=taskEntity.getIdentityLinks();
        taskEntity.setCategory(processEntity.getCategory());
        for(int i=0;i<listIdent.size();i++)
        {
        	idLinkEntity=listIdent.get(i);
        	if(IdentityLinkType.CANDIDATE.equals(idLinkEntity.getType())||"".equals(idLinkEntity.getUserId())==true)
        	{
        		strArr=new String[3];
        		taskService.claim(taskId, idLinkEntity.getUserId());
        		log.debug("delIdentityLink UserId:"+idLinkEntity.getUserId()+" GroupId:"+idLinkEntity.getGroupId()+" TypeId:"+idLinkEntity.getType());
        		strArr[0]=idLinkEntity.getUserId();
        		strArr[1]=idLinkEntity.getGroupId();
        		strArr[2]=idLinkEntity.getType();
        		listEntity.add(strArr);
        	}	
        }
        for(int i=0;i<listEntity.size();i++){
        	strTemp=listEntity.get(i);
        	taskEntity.deleteIdentityLink(strTemp[0], strTemp[1], strTemp[2]);
        }
		return null;
	}
	
}
