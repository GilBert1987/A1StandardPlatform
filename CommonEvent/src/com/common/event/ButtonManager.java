package com.common.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ButtonManager {
	private List<IButtonClickEventListener> listeners = null;
	
	public void addButtonListener(IButtonClickEventListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<IButtonClickEventListener>();
        }
        listeners.add(listener);
    }
	
	public void removeButtonListener(IButtonClickEventListener listener) {
        if (listeners == null)
            return;
        listeners.remove(listener);
    }
	
	public void fireButtonClick(ButtonClickEvent event) {
        if (listeners != null)
        {
        	ClickListeners(event);
        }
    }
	
	private void ClickListeners(ButtonClickEvent event) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
        	IButtonClickEventListener listener = (IButtonClickEventListener) iter.next();
            listener.runButtonClick(event);
        }
    }
}
