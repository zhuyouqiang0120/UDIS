package com.zens.udis.entity;

/**
 * 
 * @author zyq
 * @mail zhuyq@zensvision.com
 * @time 2016年4月1日 上午11:21:59
 */
public class AqiEntity {
	//private int id;// ID
	private String image;// 空气质量图片
	private String color;// 颜色
	private String ND;// 浓度
	private String aq;// 空气质量等级
	private String health;// 对健康的影响
	private String method;// 建议措施
	private String lblpp;// 首要污染物
	private String lblND;// 污染物浓度
	private String aqi24hr;// 过去24消失AQI
	private String flbltime;// 空气质量预报时间
	private String flblcontent;// 空气质量预报内容
	private String createtime; // 创建时间
	private String remark;// 备注


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getND() {
		return ND;
	}

	public void setND(String nD) {
		ND = nD;
	}

	public String getAq() {
		return aq;
	}

	public void setAq(String aq) {
		this.aq = aq;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getLblpp() {
		return lblpp;
	}

	public void setLblpp(String lblpp) {
		this.lblpp = lblpp;
	}

	public String getLblND() {
		return lblND;
	}

	public void setLblND(String lblND) {
		this.lblND = lblND;
	}

	public String getAqi24hr() {
		return aqi24hr;
	}

	public void setAqi24hr(String aqi24hr) {
		this.aqi24hr = aqi24hr;
	}

	public String getFlbltime() {
		return flbltime;
	}

	public void setFlbltime(String flbltime) {
		this.flbltime = flbltime;
	}

	public String getFlblcontent() {
		return flblcontent;
	}

	public void setFlblcontent(String flblcontent) {
		this.flblcontent = flblcontent;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
