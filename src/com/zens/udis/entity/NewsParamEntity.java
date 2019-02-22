package com.zens.udis.entity;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @2016年2月29日 下午5:41:06
 *
 */

public class NewsParamEntity {

	private String id;// 文章ID
	private String page;// 页码
	private String pagesize; // 每页数目
	private String columnid;// 栏目ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getColumnid() {
		return columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

}
