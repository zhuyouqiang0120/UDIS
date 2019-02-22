package com.zens.udis.entity;

public class RoadEntity {
	private String city;// 城市快速路
	private String velocity;// 高速公路
	private String intercity;// 长 三角城际高速
	private String createtime;
	private String remark;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVelocity() {
		return velocity;
	}

	public void setVelocity(String velocity) {
		this.velocity = velocity;
	}

	public String getIntercity() {
		return intercity;
	}

	public void setIntercity(String intercity) {
		this.intercity = intercity;
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
