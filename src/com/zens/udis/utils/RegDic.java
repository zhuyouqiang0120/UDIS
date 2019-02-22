package com.zens.udis.utils;

/**
 * 
 * @author ZYQ(zhuyq@zensvision.com)
 * @date  2015年9月6日上午10:09:08
 */

public class RegDic {
	
	/**
	 * 医院挂号词典
	 * @param code
	 * @return
	 */

	public String XB(String code) {
		String xb = "男";
		switch (code) {
		case "1":
			xb = "男";
			break;
		case "2":
			xb = "女";
			break;
		case "3":
			xb = "未知";
			break;
		default:
			break;
		}
		return xb;
	}

	public String BBDM(String code) {
		String bbdm = "上午";
		switch (code) {
		case "1":
			bbdm = "上午";
			break;
		case "2":
			bbdm = "下午";
			break;
		case "3":
			bbdm = "晚上";
			break;
		default:
			break;
		}
		return bbdm;
	}

	public String YYJB(String code){
		String yyjb = "综合医院";
		switch (code) {
		case "A1":
			yyjb = "综合医院";
			break;
		case "A2":
			yyjb = "中医医院";
			break;
		case "A3":
			yyjb = "中西结合医院";
			break;
		case "A5":
			yyjb = "专科医院";
			break;
		case "A6":
			yyjb = "疗养医院";
			break;
		case "1J":
			yyjb = "一甲";
			break;
		case "1Y":
			yyjb = "一乙";
			break;
		case "2J":
			yyjb = "二甲";
			break;
		case "2Y":
			yyjb = "二乙";
			break;
		case "3J":
			yyjb = "三甲";
			break;
		case "3Y":
			yyjb = "三乙";
			break;
		default:
			break;
		}
		return yyjb;
	}
	
	public String XZQDM(String code){
		String xzqdm = "市直属";
		switch (code) {
		case "440700":
			xzqdm = "市直属";
			break;
		case "440703":
			xzqdm = "蓬江区";
			break;
		case "440704":
			xzqdm = "江海区";
			break;
		case "440705":
			xzqdm = "新会区";
			break;
		case "440781":
			xzqdm = "台山区";
			break;
		case "440783":
			xzqdm = "开平区";
			break;
		case "440784":
			xzqdm = "鹤山市";
			break;
		case "440785":
			xzqdm = "恩平市";
			break;
		default:
			break;
		}
		return xzqdm;
	}
	
	public String KSZCDM(String code){
		String kszcdm = "普通";
		switch (code) {
		case "10":
			kszcdm = "普通";
			break;
		case "20":
			kszcdm = "副专家";
			break;
		case "30":
			kszcdm = "专科";
			break;
		case "40":
			kszcdm = "急诊";
			break;
		case "50":
			kszcdm = "正专家";
			break;
		case "60":
			kszcdm = "特诊";
			break;
		case "99":
			kszcdm = "其他";
			break;

		default:
			break;
		}
		return kszcdm;
	}
	
	public String XLDM(String code){
		String xldm = "研究生";
		switch (code) {
		case "1":
			xldm = "研究生";
			break;
		case "2":
			xldm = "大学本科";
			break;
		case "3":
			xldm = "大专及以下";
			break;

		default:
			break;
		}
		return xldm;
	}
	
	public String XWDM(String code){
		String xwdm = "名誉博士";
		switch (code) {
		case "1":
			xwdm = "名誉博士";
			break;
		case "2":
			xwdm = "博士";
			break;
		case "3":
			xwdm = "硕士";
			break;
		case "4":
			xwdm = "学士";
			break;
		case "9":
			xwdm = "其他";
			break;

		default:
			break;
		}
		return xwdm;
	}
	
	public String ZCDM(String code){
		String zcdm = "主任医师";
		switch (code) {
		case "231A":
			zcdm = "主任医师";
			break;
		case "231B":
			zcdm = "主任中医师";
			break;
		case "232A":
			zcdm = "副主任医师";
			break;
		case "232B":
			zcdm = "副主任中医师";
			break;
		case "233A":
			zcdm = "主治医师";
			break;
		case "233B":
			zcdm = "主治中医师";
			break;
		case "234A":
			zcdm = "医师";
			break;
		case "234B":
			zcdm = "中医医师";
			break;
		case "235A":
			zcdm = "医士";
			break;
		case "235B":
			zcdm = "中医医士";
			break;

		default:
			break;
		}
		return zcdm;
	}
	
	public String YYFS(String code) {
		String yyfs = "";
		switch (code) {
		case "10":
			yyfs = "网上预约";
			break;
		case "20":
			yyfs = "诊间预约";
			break;
		case "21":
			yyfs = "自助";
			break;
		case "30":
			yyfs = "微信预约";
			break;
		case "31":
			yyfs = "支付宝";
			break;
		case "32":
			yyfs = "电话12580";
			break;
		case "33":
			yyfs = "网电话114";
			break;
		case "34":
			yyfs = "手机APP";
			break;
		case "40":
			yyfs = "电视";
			break;

		default:
			yyfs = "电视";
			break;
		}

		return yyfs;
	}

	public String PBYXZT(String code) {
		String zt = "取消";
		switch (code) {
		case "1":
			zt = "取消";
			break;
		case "2":
			zt = "停诊";
			break;
		case "3":
			zt = "替珍";
			break;

		default:
			zt = "取消";
			break;
		}
		return zt;
	}

	public String YYDZT(String code){
		String yydzt = "取消";
		switch (code) {
		case "0":
			yydzt = "取消";
			break;
		case "1":
			yydzt = "预约成功";
			break;
		case "2":
			yydzt = "支付成功";
			break;
		case "3":
			yydzt = "已退费";
			break;

		default:
			break;
		}
		return yydzt;
	}
	
	public String LYZT(String code){
		String lyzt = "未反馈";
		switch (code) {
		case "0":
			lyzt = "未反馈";
			break;
		case "1":
			lyzt = "履约";
			break;
		case "2":
			lyzt = "爽约";
			break;

		default:
			break;
		}
		return lyzt;
	}
	
	public String PJZT(String code){
		String pjzt = "未评价";
		switch (code) {
		case "0":
			pjzt = "未评价";
			break;
		case "1":
			pjzt = "已评价";
			break;

		default:
			break;
		}
		return pjzt;
	}
	
	public String YXZT(String code){
		String lyzt = "正常";
		switch (code) {
		case "1":
			lyzt = "正常";
			break;
		case "2":
			lyzt = "停诊";
			break;
		case "3":
			lyzt = "替珍";
			break;

		default:
			break;
		}
		return lyzt;
	}
	
	public String YYQK(String code){
		String lyzt = "";
		switch (code) {
		case "0":
			lyzt = "已满";
			break;
		case "1":
			lyzt = "未满";
			break;
		case "2":
			lyzt = "过期";
			break;

		default:
			break;
		}
		return lyzt;
	}
	
	public String YYLX(String code){
		String yylx = "";
		switch (code) {
		case "0":
			yylx = "医生";
			break;
		case "1":
			yylx = "科室";
			break;

		default:
			break;
		}
		return yylx;
	}
}
