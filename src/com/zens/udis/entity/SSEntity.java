package com.zens.udis.entity;


/**
 * 社保信息
 * 
 */
public class SSEntity {

    private UserInfo userInfo;//用户信息
    private  SSInfo ssInfo;//社保信息


    /**
     * 用户信息
     * @return
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SSInfo getSsInfo() {
        return ssInfo;
    }

    public void setSsInfo(SSInfo ssInfo) {
        this.ssInfo = ssInfo;
    }

    /**
     * 社保信息
     */
    public class SSInfo{
        private Maternity maternity;
        private EmploymentInjury employmentInjury;
        private Unemployment unemployment;
        private Medicare medicare;
        private Pension pension;

        public Maternity getMaternity() {
            return maternity;
        }

        public void setMaternity(Maternity maternity) {
            this.maternity = maternity;
        }

        public EmploymentInjury getEmploymentInjury() {
            return employmentInjury;
        }

        public void setEmploymentInjury(EmploymentInjury employmentInjury) {
            this.employmentInjury = employmentInjury;
        }

        public Unemployment getUnemployment() {
            return unemployment;
        }

        public void setUnemployment(Unemployment unemployment) {
            this.unemployment = unemployment;
        }

        public Medicare getMedicare() {
            return medicare;
        }

        public void setMedicare(Medicare medicare) {
            this.medicare = medicare;
        }

        public Pension getPension() {
            return pension;
        }

        public void setPension(Pension pension) {
            this.pension = pension;
        }
    }

    /**
     * 生育保险
     */
    public class Maternity extends SS{

        public Maternity() {
            super.setName("生育保险");
        }
    }

    /**
     * 工伤保险
     */
    public class EmploymentInjury  extends SS{

        public EmploymentInjury() {
            super.setName("工伤保险");
        }
    }

    /**
     * 失业保险
     */
    public class Unemployment extends SS{

        public Unemployment() {
            super.setName("失业保险");
        }
    }
    /**
     * 医疗保险
     */
    public class Medicare extends SS{
        public Medicare() {
            super.setName("医疗保险");
        }

    }

    /**
     * 养老保险
     */
    public class Pension extends SS{
        public Pension() {
            super.setName("养老保险");
        }

    }

    private class SS{

        private String count;//总金额
        private String status;//缴费状态
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "SS{" +
                    "count='" + count + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
    /**
     * 社保个人信息
     */
    public class UserInfo{
        private String name;//姓名
        private String pid;//身份证号
        private String sid;//社保号

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", pid='" + pid + '\'' +
                    ", sid='" + sid + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SSEntity{" +
                "userInfo=" + userInfo +
                ", ssInfo=" + ssInfo +
                '}';
    }
}
