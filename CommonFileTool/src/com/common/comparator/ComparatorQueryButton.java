package com.common.comparator;

import java.util.Comparator;

import com.common.entity.query.QueryButton;

public class ComparatorQueryButton  implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		Integer orderOne;
		Integer orderTwo;
		QueryButton buttonOne;
		QueryButton buttonTwo;
		buttonOne=(QueryButton)arg0;
		buttonTwo=(QueryButton)arg1;
		orderOne=0;
		orderTwo=0;
		if("".equals(buttonOne.getOrder())==true)
		{
			orderOne=0;
		}
		else
		{
			orderOne=Integer.parseInt(buttonOne.getOrder());
		}
		if("".equals(buttonTwo.getOrder())==true)
		{
			orderTwo=0;
		}
		else
		{
			orderTwo=Integer.parseInt(buttonTwo.getOrder());
		}
		return orderOne.compareTo(orderTwo);
	}

}
