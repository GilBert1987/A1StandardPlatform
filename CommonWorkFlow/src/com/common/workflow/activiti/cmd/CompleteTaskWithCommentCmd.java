package com.common.workflow.activiti.cmd;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompleteTaskWithCommentCmd implements Command<Object> {
    private String taskId;
    private String comment;
    private Map<String, Object> variables;

    private static Logger log = LoggerFactory.getLogger(CompleteTaskWithCommentCmd.class);
    
    public CompleteTaskWithCommentCmd(String taskId,Map<String, Object> variables, String comment) {
        this.taskId = taskId;
        this.variables = variables;
        this.comment = comment;
    }

    public Void execute(CommandContext commandContext) {
        TaskEntity taskEntity;
        ExecutionEntity execution;
        taskEntity = commandContext.getTaskEntityManager().findTaskById(taskId);
        if (variables != null) {
            taskEntity.setExecutionVariables(variables);
        }
        taskEntity.fireEvent(TaskListener.EVENTNAME_COMPLETE);
        if ((Authentication.getAuthenticatedUserId() != null) && (taskEntity.getProcessInstanceId() != null)) {
            taskEntity.getProcessInstance().involveUser(Authentication.getAuthenticatedUserId(),IdentityLinkType.ASSIGNEE);
        }
        Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, comment, false);
        if (taskEntity.getExecutionId() != null) {
            execution = taskEntity.getExecution();
            execution.removeTask(taskEntity);
            execution.signal(null, null);
        }
        return null;
    }
}
