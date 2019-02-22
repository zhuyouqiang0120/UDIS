package com.zens.udis.entity;

/**
 * 
 * @author zyq 
 * @mail zhuyq@zensvision.com
 * @2016年2月29日 下午5:43:51
 *
 */

public class TrafficParamEntity {
    private String vehicle;//车牌号
    private String city;//违章省份
    private String vin;//发动机号
    private String ein;//车架号
    private String prov;//省份
    private String keys;//
    private String hpzl; //车型编号
    private String hpzl_text; //车型

    public String getHpzl() {
		return hpzl;
	}

	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}

	public String getHpzl_text() {
		return hpzl_text;
	}

	public void setHpzl_text(String hpzl_text) {
		this.hpzl_text = hpzl_text;
	}

	public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEin() {
        return ein;
    }

    public void setEin(String ein) {
        this.ein = ein;
    }

}
