package com.common.comparator;

import java.util.Comparator;

import com.common.entity.menu.SubMenu;


public class ComparatorMenuSubMenu  implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		Integer orderOne;
		Integer orderTwo;
		SubMenu submenuOne;
		SubMenu submenuTwo;
		submenuOne=(SubMenu)arg0;
		submenuTwo=(SubMenu)arg1;
		orderOne=0;
		orderTwo=0;
		if("".equals(submenuOne.getOrder())==true)
		{
			orderOne=0;
		}
		else
		{
			orderOne=Integer.parseInt(submenuOne.getOrder());
		}
		if("".equals(submenuTwo.getOrder())==true)
		{
			orderTwo=0;
		}
		else
		{
			orderTwo=Integer.parseInt(submenuTwo.getOrder());
		}
		return orderOne.compareTo(orderTwo);
	}

}
