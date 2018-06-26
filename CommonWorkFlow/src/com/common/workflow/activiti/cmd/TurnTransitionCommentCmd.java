package com.common.workflow.activiti.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TurnTransitionCommentCmd implements Command<Object>{

	private String taskId;
	private String activityId;
	private Map<String, Object> variables;
	
	private static Logger log = LoggerFactory.getLogger(TurnTransitionCommentCmd.class);
    
    public TurnTransitionCommentCmd(String tkId,String actId,Map<String,Object> map){
    	taskId=tkId;
    	activityId=actId;
    	variables=map;
    }
	
	@Override
	public Void execute(CommandContext commandContext) {
		TaskEntity taskEntity;
		ActivityImpl currActivity;
		ActivityImpl pointActivity;
		ProcessDefinitionEntity processDefinitionEntity;
		List<PvmTransition> oriPvmTransitionList;
		TransitionImpl newTransition;
		taskEntity = commandContext.getTaskEntityManager().findTaskById(taskId);
        processDefinitionEntity = Context.getProcessEngineConfiguration().getProcessDefinitionCache().get(taskEntity.getProcessDefinitionId());
        currActivity=processDefinitionEntity.findActivity(activityId);
        if (variables != null) {
            taskEntity.setExecutionVariables(variables);
        }
        try {
        	//当前节点 
			currActivity=findActivitiImpl(taskEntity,null,commandContext);
			//清空当前流向
			oriPvmTransitionList = clearTransition(currActivity);
			//创建新流向
			newTransition = currActivity.createOutgoingTransition();
			//目标节点
			pointActivity = findActivitiImpl(taskEntity, activityId,commandContext);
			//设置新流向的目标节点
			newTransition.setDestination(pointActivity);
			//执行转向任务
			taskEntity.fireEvent(TaskListener.EVENTNAME_COMPLETE);
			//删除目标节点新流入
			pointActivity.getIncomingTransitions().remove(newTransition);
			//还原以前流向
			restoreTransition(currActivity, oriPvmTransitionList); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/** 
     * 根据任务ID和节点ID获取活动节点 <br> 
     *  
     * @param taskId 
     *            任务ID 
     * @param activityId 
     *            活动节点ID <br> 
     *            如果为null或""，则默认查询当前活动节点 <br> 
     *            如果为"end"，则查询结束节点 <br> 
     *  
     * @return 
     * @throws Exception 
     */  
    private ActivityImpl findActivitiImpl(TaskEntity taskEntity, String activityId,CommandContext commandContext)throws Exception {
    	ActivityImpl activityImpl;
    	List<PvmTransition> pvmTransitionList;
    	ProcessDefinitionEntity processDefinition;
    	activityImpl=null;
    	processDefinition = Context.getProcessEngineConfiguration().getProcessDefinitionCache().get(taskEntity.getProcessDefinitionId());
        // 获取当前活动节点ID  
        if (StringUtils.isEmpty(activityId)==true) {
        	activityId = taskEntity.getTaskDefinitionKey();
        }
		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activitImpl : processDefinition.getActivities()) {
				pvmTransitionList = activitImpl.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					activityImpl=activitImpl;
					break;
				}
			}
		}
		//根据节点ID，获取对应的活动节点
		if(null==activityImpl)
		{
			activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);
		}
		return activityImpl;
	}
    
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {  
        // 存储当前节点所有流向临时变量  
        List<PvmTransition> oriPvmTransitionList;
        List<PvmTransition> pvmTransitionList;
        oriPvmTransitionList = new ArrayList<PvmTransition>();  
        // 获取当前节点所有流向，存储到临时变量，然后清空  
        pvmTransitionList = activityImpl.getOutgoingTransitions();  
        for (PvmTransition pvmTransition : pvmTransitionList) {  
            oriPvmTransitionList.add(pvmTransition);  
        }  
        pvmTransitionList.clear();  
        return oriPvmTransitionList;  
    }
    
    private void restoreTransition(ActivityImpl activityImpl,List<PvmTransition> oriPvmTransitionList) {  
        // 清空现有流向 
        List<PvmTransition> pvmTransitionList;
        pvmTransitionList = activityImpl.getOutgoingTransitions();  
        pvmTransitionList.clear();  
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
    }
}
