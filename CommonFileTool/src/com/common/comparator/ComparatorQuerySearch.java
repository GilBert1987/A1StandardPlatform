package com.common.comparator;

import java.util.Comparator;

import com.common.entity.query.Column;

public class ComparatorQuerySearch implements Comparator{
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
		if(columnOne.getSearch()!=null)
		{
			if("".equals(columnOne.getSearch().getOrder())==true)
			{
				orderOne=0;
			}
			else
			{
				if(null!=columnOne.getSearch().getOrder())
				{
					orderOne=Integer.parseInt(columnOne.getSearch().getOrder());
				}
			}
		}
		if(columnTwo.getSearch()!=null)
		{
			if("".equals(columnTwo.getSearch().getOrder())==true)
			{
				orderTwo=0;
			}
			else
			{
				if(null!=columnTwo.getSearch().getOrder())
				{
					orderTwo=Integer.parseInt(columnTwo.getSearch().getOrder());
				}
			}
		}
		return orderOne.compareTo(orderTwo);
	}
}
