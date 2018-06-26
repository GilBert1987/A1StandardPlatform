package com.common.comparator;

import java.util.Comparator;

import com.common.entity.query.Column;

public class ComparatorQueryTitle  implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		Integer orderOne;
		Integer orderTwo;
		Column columnOne;
		Column columnTwo;
		columnOne=(Column)arg0;
		columnTwo=(Column)arg1;
		orderOne=0;
		orderTwo=0;
		if(columnOne.getTitle()!=null)
		{
			if("".equals(columnOne.getTitle().getOrder())==true)
			{
				orderOne=0;
			}
			else
			{
				orderOne=Integer.parseInt(columnOne.getTitle().getOrder());
			}
		}
		if(columnTwo.getTitle()!=null)
		{
			if("".equals(columnTwo.getTitle().getOrder())==true)
			{
				orderTwo=0;
			}
			else
			{
				orderTwo=Integer.parseInt(columnTwo.getTitle().getOrder());
			}
		}
		return orderOne.compareTo(orderTwo);
	}

}
