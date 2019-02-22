package com.zens.udis.entity;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @2016年2月29日 下午2:29:22
 *
 */
public class NewsEntity {
	private int id;// 文章ID
	private String tid;// 文章tID
	private String title;// 标题
	private String time;//时间
	private String titlepic;// 标题图片
	private String contents;// 文章内容
	private String createtime;//创建时间
	private int columnid;//栏目ID


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTitle() {
		return title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitlepic() {
		return titlepic;
	}

	public void setTitlepic(String titlepic) {
		this.titlepic = titlepic;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public int getColumnid() {
		return columnid;
	}

	public void setColumnid(int columnid) {
		this.columnid = columnid;
	}

}
