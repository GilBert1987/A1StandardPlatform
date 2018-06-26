package com.common.db.support;

public class CustomerContextHolder {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    
    public static void setContextType(String contextType) {  
        contextHolder.set(contextType);  
    }  
      
    public static String getContextType() {  
        return contextHolder.get();  
    }  
      
    public static void clearContextType() {  
        contextHolder.remove();  
    } 
}
