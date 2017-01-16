package com.xsoa.service.service1040000;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040110;

public class Service1040110 extends BaseService {
	private DBManager db = null;

	public Service1040110() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取客户信息列表数据个数
	 * @param beanIn
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年2月27日 下午5:07:58
	 * @update ztz at 2015年3月11日 下午4:46:49
	 */
	public int getDataCount(Pojo1040110 beanIn, String flag) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.KHXX_KHID) ");//客户ID
			strbuf.append(" FROM ");
			strbuf.append("     KHXX A, YHXX B, YHJS C, BMXX D ");
			strbuf.append(" WHERE 1=1 ");
			if ("check".equals(flag)) {
				strbuf.append(" AND A.KHXX_KHMC = '").append(beanIn.getKHXX_KHMC()).append("'");
			} else {
				strbuf.append(this.searchSql(beanIn));
			}
			
			result = db.queryForInteger(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取客户信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1040110>
	 * @author ztz
	 * @date 2015年2月27日 下午5:08:07
	 */
	public List<Pojo1040110> getDataList(Pojo1040110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1040110> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.KHXX_KHID, ");//客户ID
			strbuf.append("     A.KHXX_KHBH, ");//客户编号
			strbuf.append("     A.KHXX_KHMC, ");//客户名称
			strbuf.append("     A.KHXX_PROV, ");//所属省份
			strbuf.append("     A.KHXX_CITY, ");//所属城镇
			strbuf.append("     A.KHXX_HY, ");//行业
			strbuf.append("     CASE WHEN A.KHXX_HY = '0' THEN '电脑及通讯' WHEN A.KHXX_HY = '1' THEN '办公用品' ELSE '' END AS HYMC, ");//行业名称
			strbuf.append("     A.KHXX_GSGM, ");//公司规模
			strbuf.append("     CASE WHEN A.KHXX_GSGM = '0' THEN '20人以下' WHEN A.KHXX_GSGM = '1' THEN '20-50人' ELSE '' END AS GSGM, ");//公司规模
			strbuf.append("     A.KHXX_LX, ");//类型（0：交易，1：伙伴，2：潜在）
			strbuf.append("     CASE WHEN A.KHXX_LX = '0' THEN '交易' WHEN A.KHXX_LX = '1' THEN '伙伴' WHEN A.KHXX_LX = '2' THEN '潜在' ELSE '' END AS LXMC, ");//类型名称
			strbuf.append("     A.KHXX_XQDJ, ");//需求等级（0：一星，1：二星，2：三星）
			strbuf.append("     CASE WHEN A.KHXX_XQDJ = '0' THEN '一星' WHEN A.KHXX_XQDJ = '1' THEN '二星' WHEN A.KHXX_XQDJ = '2' THEN '三星' ELSE '' END AS XQDJ, ");//需求等级
			strbuf.append("     A.KHXX_LY, ");//来源（0：互联网，1：朋友介绍，2：公司分配，3：主动开拓）
			strbuf.append("     CASE WHEN A.KHXX_LY = '0' THEN '互联网' WHEN A.KHXX_LY = '1' THEN '朋友介绍' WHEN A.KHXX_LY = '2' THEN '公司分配' WHEN A.KHXX_LY ='3' THEN '主动开拓' ELSE '' END AS LYMC, ");//来源名称
			strbuf.append("     A.KHXX_KHJJ, ");//客户简介
			strbuf.append("     A.KHXX_GDDH, ");//固定电话
			strbuf.append("     A.KHXX_GSCZ, ");//公司传真
			strbuf.append("     A.KHXX_GSDZ, ");//公司地址
			strbuf.append("     A.KHXX_GXDJ, ");//关系等级（0：密切，1：较好，2：一般，3：较差）
			strbuf.append("     CASE WHEN A.KHXX_GXDJ = '0' THEN '密切' WHEN A.KHXX_GXDJ = '1' THEN '较好' WHEN A.KHXX_GXDJ = '2' THEN '一般' WHEN A.KHXX_GXDJ = '3' THEN '较差' ELSE '' END AS GXDJ, ");//关系等级
			strbuf.append("     A.KHXX_XSSC, ");//销售市场（0：出口，1：内销，2：混合）
			strbuf.append("     CASE WHEN A.KHXX_XSSC = '0' THEN '出口' WHEN A.KHXX_XSSC = '1' THEN '内销' WHEN A.KHXX_XSSC = '2' THEN '混合' ELSE '' END AS XSSC, ");//销售市场
			strbuf.append("     A.KHXX_XYDJ, ");//信用等级（0：优秀，1：良好，2：较差）
			strbuf.append("     CASE WHEN A.KHXX_XYDJ = '0' THEN '优秀' WHEN A.KHXX_XYDJ = '1' THEN '良好' WHEN A.KHXX_XYDJ = '2' THEN '较差' ELSE '' END AS XYDJ, ");//信用等级
			strbuf.append("     A.KHXX_ZYCD, ");//重要程度（0：一般，1：重要，2：VIP）
			strbuf.append("     CASE WHEN A.KHXX_ZYCD = '0' THEN '一般' WHEN A.KHXX_ZYCD = '1' THEN '重要' WHEN A.KHXX_ZYCD = '2' THEN 'VIP' ELSE '' END AS ZYCD, ");//重要程度
			strbuf.append("     A.KHXX_LXRBH, ");//联系人编号
			strbuf.append("     A.KHXX_LXRXM, ");//联系人姓名
			strbuf.append("     A.KHXX_LXRSJHM, ");//联系人手机号码
			strbuf.append("     A.KHXX_LXRQQ, ");//联系人QQ
			strbuf.append("     A.KHXX_LXREMAIL, ");//联系人E-Mail
			strbuf.append("     CONCAT(B.YHXX_YHID, '-', B.YHXX_YHMC) AS KHXX_CJR, ");//创建人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.KHXX_CJSJ, '%Y%m%d'), '%Y-%m-%d') AS KHXX_CJSJ, ");//创建时间
			strbuf.append("     A.KHXX_GXR, ");//更新人
			strbuf.append("     A.KHXX_GXSJ, ");//更新时间
			strbuf.append("     A.KHXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     KHXX A, YHXX B, YHJS C, BMXX D ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.KHXX_KHBH ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1040110>> rs = new BeanListHandler<Pojo1040110>(
					Pojo1040110.class);
			
			result = db.queryForBeanListHandler(execSql, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * 
	 * @FunctionName: searchSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年2月27日 下午5:08:17
	 */
	private String searchSql(Pojo1040110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.KHXX_CJR = B.YHXX_YHID ");
			strbuf.append(" AND B.YHXX_JSID = C.YHJS_JSID ");
			strbuf.append(" AND C.YHJS_BMID = D.BMXX_BMID ");
			strbuf.append(" AND A.KHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.YHJS_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.BMXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND A.KHXX_LX = '0'");//类型（0：交易，1：伙伴，2：潜在）
			/* 判断当前登陆用户是否是管理人员 */
			if ("0".equals(beanIn.getDQYH_ZWDJ())) {//职员
				strbuf.append(" AND A.KHXX_CJR = '").append(beanIn.getDQYH_YHID()).append("'");
			} else {//管理人员
				strbuf.append(" AND D.BMXX_BMID = '").append(beanIn.getDQYH_BMID()).append("'");
			}
			/* 客户编号 */
			if (MyStringUtils.isNotBlank(beanIn.getKHXX_KHBH())) {
				strbuf.append(" AND A.KHXX_KHBH = '").append(beanIn.getKHXX_KHBH()).append("'");
			}
			/* 客户名称 */
			if (MyStringUtils.isNotBlank(beanIn.getKHXX_KHMC())) {
				strbuf.append(" AND A.KHXX_KHMC LIKE '%").append(beanIn.getKHXX_KHMC()).append("%'");
			}
			/* 联系人 */
			if (MyStringUtils.isNotBlank(beanIn.getKHXX_LXRXM())) {
				strbuf.append(" AND A.KHXX_LXRXM LIKE '%").append(beanIn.getKHXX_LXRXM()).append("%'");
			}
			/* 联系方式 */
			if (MyStringUtils.isNotBlank(beanIn.getKHXX_LXRSJHM())) {
				strbuf.append(" AND A.KHXX_LXRSJHM = '").append(beanIn.getKHXX_LXRSJHM()).append("'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: getBaseData
	 * @Description: 获取当前登陆用户基础数据（如用户ID，部门ID，职务等级等）
	 * @param yhid
	 * @return
	 * @throws Exception
	 * @return Pojo1040110
	 * @author ztz
	 * @date 2015年3月23日 上午10:39:39
	 */
	public Pojo1040110 getBaseData(String yhid) throws Exception {
		Pojo1040110 baseBean = new Pojo1040110();
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YHXX_YHID AS DQYH_YHID, ");//用户ID
			strbuf.append("     E.BMXX_BMID AS DQYH_BMID, ");//部门ID
			strbuf.append("     C.YGZW_ZWDJ AS DQYH_ZWDJ ");//职务等级
			strbuf.append(" FROM ");
			strbuf.append("     YHXX A, YGXX B, YGZW C, YHJS D, BMXX E ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YHXX_YGID = B.YGXX_YGID ");
			strbuf.append(" AND B.YGXX_YGID = C.YGZW_YGID ");
			strbuf.append(" AND B.YGXX_JSID = D.YHJS_JSID ");
			strbuf.append(" AND D.YHJS_BMID = E.BMXX_BMID ");
			strbuf.append(" AND A.YHXX_SCBZ = '0'");
			strbuf.append(" AND B.YGXX_SCBZ = '0'");
			strbuf.append(" AND C.YGZW_SCBZ = '0'");
			strbuf.append(" AND D.YHJS_SCBZ = '0'");
			strbuf.append(" AND E.BMXX_SCBZ = '0'");
			strbuf.append(" AND A.YHXX_YHID = '").append(yhid).append("'");
			strbuf.append("");
			
			ResultSetHandler<Pojo1040110> rs = new BeanHandler<Pojo1040110>(
					Pojo1040110.class);
			
			baseBean = db.queryForBeanHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return baseBean;
	}
	/**
	 * 
	 * @FunctionName: daysBetween
	 * @Description: 字符串的日期格式的计算_计算两个日期之差的天数
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 * @return int
	 * @author ztz
	 * @date 2015年2月13日 上午10:25:54
	 */
	public static int daysBetween(String startDate, String endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(startDate));
		long startTime = cal.getTimeInMillis();//起始日期毫秒数
		cal.setTime(sdf.parse(endDate));
		long endTime = cal.getTimeInMillis();//截止日期毫秒数
        long between_days = (endTime - startTime) / (1000*3600*24);//间隔天数
        
        return Integer.parseInt(String.valueOf(between_days));
    }
	/**
	 * 
	 * @FunctionName: getKhbh
	 * @Description: 生成客户编号
	 * @return
	 * @return String
	 * @author ztz
	 * @date 2015年2月16日 下午3:14:47
	 */
	public String getKhbh(String flag) throws Exception {
		String khbh = "";
		
		try {
			Random rnd = new Random();
			int num = 100 + rnd.nextInt(900);//最后三位
			SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");//时间戳12位
			if ("KH".equals(flag)) {
				khbh = "CRM" + sdf.format(new Date()) + String.valueOf(num);
			} else {
				khbh = "LXR" + sdf.format(new Date()) + String.valueOf(num);
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return khbh;
	}
	/**
	 * 
	 * @FunctionName: insertData
	 * @Description: 新增客户信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午5:08:29
	 */
	public boolean insertData(Pojo1040110 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		String khId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     KHXX ");
			strbuf.append(" ( ");
			strbuf.append("     KHXX_KHID, ");//客户ID
			strbuf.append("     KHXX_KHBH, ");//客户编号
			strbuf.append("     KHXX_KHMC, ");//客户名称
			strbuf.append("     KHXX_PROV, ");//所属省份
			strbuf.append("     KHXX_CITY, ");//所属城镇
			strbuf.append("     KHXX_HY, ");//行业
			strbuf.append("     KHXX_GSGM, ");//公司规模
			strbuf.append("     KHXX_LX, ");//类型（0：交易，1：伙伴，2：潜在）
			strbuf.append("     KHXX_XQDJ, ");//需求等级（0：一星，1：二星，2：三星）
			strbuf.append("     KHXX_LY, ");//来源（0：互联网，1：朋友介绍，2：公司分配，3：主动开拓）
			strbuf.append("     KHXX_KHJJ, ");//客户简介
			strbuf.append("     KHXX_GDDH, ");//固定电话
			strbuf.append("     KHXX_GSCZ, ");//公司传真
			strbuf.append("     KHXX_GSDZ, ");//公司地址
			strbuf.append("     KHXX_GXDJ, ");//关系等级（0：密切，1：较好，2：一般，3：较差）
			strbuf.append("     KHXX_XSSC, ");//销售市场（0：出口，1：内销，2：混合）
			strbuf.append("     KHXX_XYDJ, ");//信用等级（0：优秀，1：良好，2：较差）
			strbuf.append("     KHXX_ZYCD, ");//重要程度（0：一般，1：重要，2：VIP）
			strbuf.append("     KHXX_LXRBH, ");//联系人编号
			strbuf.append("     KHXX_LXRXM, ");//联系人姓名
			strbuf.append("     KHXX_LXRSJHM, ");//联系人手机号码
			strbuf.append("     KHXX_LXRQQ, ");//联系人QQ
			strbuf.append("     KHXX_LXREMAIL, ");//联系人E-Mail
			strbuf.append("     KHXX_CJR, ");//创建人
			strbuf.append("     KHXX_CJSJ, ");//创建时间
			strbuf.append("     KHXX_GXR, ");//更新人
			strbuf.append("     KHXX_GXSJ, ");//更新时间
			strbuf.append("     KHXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+khId+"', ");//客户ID
			strbuf.append("     '"+getKhbh("KH")+"', ");//客户编号
			strbuf.append("     '"+beanIn.getKHXX_KHMC()+"', ");//客户名称
			strbuf.append("     '"+beanIn.getKHXX_PROV()+"', ");//所属省份
			strbuf.append("     '"+beanIn.getKHXX_CITY()+"', ");//所属城镇
			strbuf.append("     '"+beanIn.getKHXX_HY()+"', ");//行业
			strbuf.append("     '"+beanIn.getKHXX_GSGM()+"', ");//公司规模
			strbuf.append("     '0', ");//类型（0：交易，1：伙伴，2：潜在）
			strbuf.append("     '"+beanIn.getKHXX_XQDJ()+"', ");//需求等级（0：一星，1：二星，2：三星）
			strbuf.append("     '"+beanIn.getKHXX_LY()+"', ");//来源（0：互联网，1：朋友介绍，2：公司分配，3：主动开拓）
			strbuf.append("     '"+beanIn.getKHXX_KHJJ()+"', ");//客户简介
			strbuf.append("     '"+beanIn.getKHXX_GDDH()+"', ");//固定电话
			strbuf.append("     '"+beanIn.getKHXX_GSCZ()+"', ");//公司传真
			strbuf.append("     '"+beanIn.getKHXX_GSDZ()+"', ");//公司地址
			strbuf.append("     '"+beanIn.getKHXX_GXDJ()+"', ");//关系等级（0：密切，1：较好，2：一般，3：较差）
			strbuf.append("     '"+beanIn.getKHXX_XSSC()+"', ");//销售市场（0：出口，1：内销，2：混合）
			strbuf.append("     '"+beanIn.getKHXX_XYDJ()+"', ");//信用等级（0：优秀，1：良好，2：较差）
			strbuf.append("     '"+beanIn.getKHXX_ZYCD()+"', ");//重要程度（0：一般，1：重要，2：VIP）
			strbuf.append("     '"+getKhbh("LXR")+"', ");//联系人编号
			strbuf.append("     '"+beanIn.getKHXX_LXRXM()+"', ");//联系人姓名
			strbuf.append("     '"+beanIn.getKHXX_LXRSJHM()+"', ");//联系人手机号码
			strbuf.append("     '"+beanIn.getKHXX_LXRQQ()+"', ");//联系人QQ
			strbuf.append("     '"+beanIn.getKHXX_LXREMAIL()+"', ");//联系人E-Mail
			strbuf.append("     '"+beanIn.getKHXX_CJR()+"', ");//创建人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getKHXX_CJR()+"', ");//更新人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" ) ");
			count = db.executeSQL(strbuf);
			
			if (count > 0) {
				db.commit();
				result = true;
			} else {
				db.rollback();
			}
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * 
	 * @FunctionName: updateData
	 * @Description: 修改客户信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午5:08:53
	 */
	public boolean updateData(Pojo1040110 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     KHXX ");
			strbuf.append(" SET ");
			strbuf.append("     KHXX_KHMC='").append(beanIn.getKHXX_KHMC()).append("',");//客户名称
			strbuf.append("     KHXX_PROV='").append(beanIn.getKHXX_PROV()).append("',");//所属省份
			strbuf.append("     KHXX_CITY='").append(beanIn.getKHXX_CITY()).append("',");//所属城镇
			strbuf.append("     KHXX_HY='").append(beanIn.getKHXX_HY()).append("',");//行业
			strbuf.append("     KHXX_GSGM='").append(beanIn.getKHXX_GSGM()).append("',");//公司规模
			strbuf.append("     KHXX_XQDJ='").append(beanIn.getKHXX_XQDJ()).append("',");//需求等级（0：一星，1：二星，2：三星）
			strbuf.append("     KHXX_LY='").append(beanIn.getKHXX_LY()).append("',");//来源（0：互联网，1：朋友介绍，2：公司分配，3：主动开拓）
			strbuf.append("     KHXX_KHJJ='").append(beanIn.getKHXX_KHJJ()).append("',");//客户简介
			strbuf.append("     KHXX_GDDH='").append(beanIn.getKHXX_GDDH()).append("',");//固定电话
			strbuf.append("     KHXX_GSCZ='").append(beanIn.getKHXX_GSCZ()).append("',");//公司传真
			strbuf.append("     KHXX_GSDZ='").append(beanIn.getKHXX_GSDZ()).append("',");//公司地址
			strbuf.append("     KHXX_GXDJ='").append(beanIn.getKHXX_GXDJ()).append("',");//关系等级（0：密切，1：较好，2：一般，3：较差）
			strbuf.append("     KHXX_XSSC='").append(beanIn.getKHXX_XSSC()).append("',");//销售市场（0：出口，1：内销，2：混合）
			strbuf.append("     KHXX_XYDJ='").append(beanIn.getKHXX_XYDJ()).append("',");//信用等级（0：优秀，1：良好，2：较差）
			strbuf.append("     KHXX_ZYCD='").append(beanIn.getKHXX_ZYCD()).append("',");//重要程度（0：一般，1：重要，2：VIP）
			strbuf.append("     KHXX_LXRBH='").append(beanIn.getKHXX_LXRBH()).append("',");//联系人编号
			strbuf.append("     KHXX_LXRXM='").append(beanIn.getKHXX_LXRXM()).append("',");//联系人姓名
			strbuf.append("     KHXX_LXRSJHM='").append(beanIn.getKHXX_LXRSJHM()).append("',");//联系人手机号码
			strbuf.append("     KHXX_LXRQQ='").append(beanIn.getKHXX_LXRQQ()).append("',");//联系人QQ
			strbuf.append("     KHXX_LXREMAIL='").append(beanIn.getKHXX_LXREMAIL()).append("',");//联系人E-Mail
			strbuf.append("     KHXX_GXR='").append(beanIn.getKHXX_GXR()).append("',");//更新人
			strbuf.append("     KHXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     KHXX_KHID='").append(beanIn.getKHXX_KHID()).append("'");//客户ID
			count = db.executeSQL(strbuf);
			
			if (count > 0) {
				db.commit();
				result = true;
			} else {
				db.rollback();
			}
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * 
	 * @FunctionName: deleteData
	 * @Description: 删除客户信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午5:09:03
	 */
	public boolean deleteData(Pojo1040110 beanIn) throws Exception {
		boolean result =false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     KHXX ");
			strbuf.append(" SET ");
			strbuf.append("     KHXX_GXR='").append(beanIn.getKHXX_GXR()).append("',");//更新人
			strbuf.append("     KHXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf.append("     KHXX_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" WHERE ");
			strbuf.append("     KHXX_KHID='").append(beanIn.getKHXX_KHID()).append("'");//客户ID
			count = db.executeSQL(strbuf);
			
			if (count > 0) {
				db.commit();
				result = true;
			} else {
				db.rollback();
			}
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
}