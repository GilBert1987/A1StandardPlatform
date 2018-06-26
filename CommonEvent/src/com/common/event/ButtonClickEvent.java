package com.common.event;

import java.util.*;

public class ButtonClickEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2867880270329497030L;
	
	private Object objsource;
	private ButtonEventMessage message;

	public ButtonClickEvent(Object source, Object message) {
		super(source);
		this.objsource = source;
		this.message = (ButtonEventMessage)message;
	}

	public Object getObjsource() {
		return objsource;
	}

	public void setObjsource(Object objsource) {
		this.objsource = objsource;
	}

	public ButtonEventMessage getMessage() {
		return message;
	}

	public void setMessage(ButtonEventMessage message) {
		this.message = message;
	}
}
