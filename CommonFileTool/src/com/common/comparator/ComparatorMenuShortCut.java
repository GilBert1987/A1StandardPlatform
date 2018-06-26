package com.common.comparator;

import java.util.Comparator;

import com.common.entity.menu.ShortCut;


public class ComparatorMenuShortCut  implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		Integer orderOne;
		Integer orderTwo;
		ShortCut shortcutOne;
		ShortCut shortcutTwo;
		shortcutOne=(ShortCut)arg0;
		shortcutTwo=(ShortCut)arg1;
		orderOne=0;
		orderTwo=0;
		if("".equals(shortcutOne.getOrder())==true)
		{
			orderOne=0;
		}
		else
		{
			orderOne=Integer.parseInt(shortcutOne.getOrder());
		}
		if("".equals(shortcutTwo.getOrder())==true)
		{
			orderTwo=0;
		}
		else
		{
			orderTwo=Integer.parseInt(shortcutTwo.getOrder());
		}
		return orderOne.compareTo(orderTwo);
	}

}
