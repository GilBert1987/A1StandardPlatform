package com.common.comparator;

import java.util.Comparator;

import com.common.entity.tool.Item;


public class ComparatorToolItem  implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		Integer orderOne;
		Integer orderTwo;
		Item itemOne;
		Item itemTwo;
		itemOne=(Item)arg0;
		itemTwo=(Item)arg1;
		orderOne=0;
		orderTwo=0;
		if(itemOne.getOrder()!=null)
		{
			if("".equals(itemOne.getOrder())==true)
			{
				orderOne=0;
			}
			else
			{
				orderOne=Integer.parseInt(itemOne.getOrder());
			}
		}
		if(itemTwo.getOrder()!=null)
		{
			if("".equals(itemTwo.getOrder())==true)
			{
				orderTwo=0;
			}
			else
			{
				orderTwo=Integer.parseInt(itemTwo.getOrder());
			}
		}
		return orderOne.compareTo(orderTwo);
	}

}
