package com.common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.common.service.TagShow;

public class QueryPage extends TagSupport implements TagShow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9161022933956283892L;
	private int pageSize = 10; // 每页要显示的记录数
	private int pageNo = 1; // 当前页号
	private int recordCount = 0; // 总记录数
	private String titleString="&nbsp;共";
	private String unitString="项";
	private String currString="当前:";
	private String pageString="页";
	private String prePageString ="&laquo;&nbsp;上一页";
	private String nextPageString = "下一页&nbsp;&raquo;";
	private Integer showtype;
	private static Logger log = Logger.getLogger(QueryPage.class);

	public Integer getShowtype() {
		return showtype;
	}

	public void setShowtype(Integer showtype) {
		this.showtype = showtype;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	@SuppressWarnings("static-access")
	public int doStartTag() throws JspException {
		return super.EVAL_BODY_INCLUDE;
	}

	/* 标签结束方法 */
	@SuppressWarnings("static-access")
	public int doEndTag() throws JspException {
		JspWriter out;
		int pageCount; // 计算总页数
		// 拼写要输出到页面的HTML文本
		StringBuilder sb;
		out = pageContext.getOut();
		sb = new StringBuilder();
		if (0 != showtype) {
			pageCount = (recordCount + pageSize - 1) / pageSize; // 计算总页数
			sb.append("<div class=\"pagination\">\r\n");
			sb.append("<div class=\"pagination_left\">\r\n");
			// 页号越界处理
			if (pageNo > pageCount) {
				pageNo = pageCount;
			}
			if (pageNo < 1) {
				pageNo = 1;
			}
			// 输出统计数据
			sb.append(titleString);
			sb.append("<strong>");
			sb.append(recordCount);
			sb.append("</strong>");
			sb.append(unitString);
			sb.append("<strong>");
			sb.append(currString);
			sb.append(pageNo);
			sb.append("</strong>");
			sb.append(pageString);
			sb.append("\r\n");
			sb.append("</div>");
			// 上一页处理
			sb.append("<div class=\"pagination_right\">");
			if (pageNo == 1) {
				sb.append("<span class=\"disabled\">");
				sb.append(prePageString);
				sb.append("</span>\r\n");
			} else {
				sb.append("<a href=\"javascript:changePage(");
				sb.append((pageNo - 1));
				sb.append(")\">");
				sb.append(prePageString);
				sb.append("</a>\r\n");
			}
			// 如果前面页数过多,显示"..."
			int start = 1;
			if (this.pageNo > 4) {
				start = this.pageNo - 1;
				sb.append("<a href=\"javascript:changePage(1)\">1</a>\r\n");
				sb.append("<a href=\"javascript:changePage(2)\">2</a>\r\n");
				sb.append("&hellip;\r\n");
			}
			// 显示当前页附近的页
			int end = this.pageNo + 2;
			if (end > pageCount) {
				end = pageCount;
			}
			for (int i = start; i <= end; i++) {
				if (pageNo == i) { // 当前页号不需要超链接
					sb.append("<span class=\"current\">");
					sb.append(i);
					sb.append("</span>\r\n");
				} else {
					sb.append("<a href=\"javascript:changePage(");
					sb.append(i);
					sb.append(")\">");
					sb.append(i);
					sb.append("</a>\r\n");
				}
			}
			// 如果后面页数过多,显示"..."
			if (end < pageCount - 2) {
				sb.append("&hellip;\r\n");
			}
			if (end < pageCount) {
				sb.append("<a href=\"javascript:changePage(");
				sb.append(pageCount);
				sb.append(")\">");
				sb.append(pageCount).append("</a>\r\n");
			}
			// 下一页处理
			if (pageNo == pageCount) {
				sb.append("<span class=\"disabled\">");
				sb.append(nextPageString);
				sb.append("</span>\r\n");
			} else {
				sb.append("<a href=\"javascript:changePage(");
				sb.append((pageNo + 1));
				sb.append(")\">");
				sb.append(nextPageString);
				sb.append("</a>\r\n");
			}
			//显示当前页信息
			sb.append("<span>共 "+String.valueOf(pageCount).trim()+"页</span>");
			sb.append("</div>\r\n");
			sb.append("</div>\r\n");
		}
		try {
			out.println(sb.toString());
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}

	@Override
	public void setDataBind(String dataBind) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDataBind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInfoname(String infoname) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInfoname() {
		// TODO Auto-generated method stub
		return null;
	}
}
