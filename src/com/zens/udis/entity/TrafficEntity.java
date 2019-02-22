package com.zens.udis.entity;

import java.util.List;

/**
 * 社保实体
 * @author zyq 
 * @mail zhuyq@zensvision.com
 * @2016年2月29日 下午5:41:48
 *
 */

public class TrafficEntity {
    private String hid_vcq_lpn;
    private String vcq_City;
    private String vcq_Province;
    private String date;
    private String status;
    private String flag = "queryTraffic";
    private List<Datas> datas;
    
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHid_vcq_lpn() {
        return hid_vcq_lpn;
    }

    public void setHid_vcq_lpn(String hid_vcq_lpn) {
        this.hid_vcq_lpn = hid_vcq_lpn;
    }

    public String getVcq_City() {
        return vcq_City;
    }

    public void setVcq_City(String vcq_City) {
        this.vcq_City = vcq_City;
    }

    public String getVcq_Province() {
        return vcq_Province;
    }

    public void setVcq_Province(String vcq_Province) {
        this.vcq_Province = vcq_Province;
    }

    public List<Datas> getDatas() {
        return datas;
    }

    public void setDatas(List<Datas> datas) {
        this.datas = datas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class Datas{
        private String date;
        private String addr;
        private String violation;
        private String points;
        private String money;

        public Datas() {
        }

        public Datas(String date, String addr, String violation, String points, String money) {
            this.date = date;
            this.addr = addr;
            this.violation = violation;
            this.points = points;
            this.money = money;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getViolation() {
            return violation;
        }

        public void setViolation(String violation) {
            this.violation = violation;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }

}
