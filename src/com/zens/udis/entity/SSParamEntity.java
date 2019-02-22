package com.zens.udis.entity;

/**
 * 
 * @author zyq 
 * @mail zhuyq@zensvision.com
 * @2016年2月29日 下午5:41:06
 *
 */

public class SSParamEntity {
    private String pid;//身份证号
    private String name;//姓名
    private String passwd;//密码
    private String sid;//社保号
    private String prov; //省
    private String region;//地市
    
    public String getPid() {
        return pid;
    }

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public void setPid(String pid) {
        this.pid = pid;
    }
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
