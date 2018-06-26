package com.common.tool;

public class Page {
	private int pageNum = 1;
	private int numPerPage = 15;
	private int totalPage = 1;
	private int prePage = 1;
	private int nextPage = 1;
	private long totalCount = 0;
	private String queryWhere;
	private String orderField;
	
	public String getQueryWhere() {
		return queryWhere;
	}

	public void setQueryWhere(String queryWhere) {
		this.queryWhere = queryWhere;
	}

	/**  
	 * 返回 pageNum 的值   
	 * @return pageNum  
	 */
	public int getPageNum() {
		if (pageNum > totalPage) {
			pageNum = totalPage;
		}
		return pageNum;
	}

	/**  
	 * 设置 pageNum 的值  
	 * @param pageNum
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum > 0 ? pageNum : 1;
	}

	/**  
	 * 返回 numPerPage 的值   
	 * @return numPerPage  
	 */
	public int getNumPerPage() {
		return numPerPage;
	}

	/**  
	 * 设置 numPerPage 的值  
	 * @param numPerPage
	 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage > 0 ? numPerPage : 10;
	}

	/**  
	 * 返回 orderField 的值   
	 * @return orderField  
	 */
	public String getOrderField() {
		return orderField;
	}

	/**  
	 * 设置 orderField 的值  
	 * @param orderField
	 */
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	/**  
	 * 返回 totalPage 的值   
	 * @return totalPage  
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**  
	 * 设置 totalPage 的值  
	 * @param totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**  
	 * 返回 prePage 的值   
	 * @return prePage  
	 */
	public int getPrePage() {
		prePage = pageNum - 1;
		if (prePage < 1) {
			prePage = 1;
		}
		return prePage;
	}

	/**  
	 * 设置 prePage 的值  
	 * @param prePage
	 */
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	/**  
	 * 返回 nextPage 的值   
	 * @return nextPage  
	 */
	public int getNextPage() {
		nextPage = pageNum + 1;
		if (nextPage > totalPage) {
			nextPage = totalPage;
		}
		
		return nextPage;
	}

	/**  
	 * 设置 nextPage 的值  
	 * @param nextPage
	 */
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	/**  
	 * 返回 totalCount 的值   
	 * @return totalCount  
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**  
	 * 设置 totalCount 的值  
	 * @param totalCount
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		totalPage = (int)(totalCount - 1) / this.numPerPage + 1;
	}
}
