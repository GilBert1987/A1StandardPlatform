package com.common.comparator;

import java.util.Comparator;

import com.common.entity.tree.TreeButton;


public class ComparatorTreeButton  implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		Integer orderOne;
		Integer orderTwo;
		TreeButton treeButtonOne;
		TreeButton treeButtonTwo;
		treeButtonOne=(TreeButton)arg0;
		treeButtonTwo=(TreeButton)arg1;
		orderOne=0;
		orderTwo=0;
		if(treeButtonOne.getOrder()!=null)
		{
			if("".equals(treeButtonOne.getOrder())==true)
			{
				orderOne=0;
			}
			else
			{
				orderOne=Integer.parseInt(treeButtonOne.getOrder());
			}
		}
		if(treeButtonTwo.getOrder()!=null)
		{
			if("".equals(treeButtonTwo.getOrder())==true)
			{
				orderTwo=0;
			}
			else
			{
				orderTwo=Integer.parseInt(treeButtonTwo.getOrder());
			}
		}
		return orderOne.compareTo(orderTwo);
	}

}
