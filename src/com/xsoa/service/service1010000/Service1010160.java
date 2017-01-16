package com.xsoa.service.service1010000;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010160;

public class Service1010160 extends BaseService {
	private DBManager db = null;

	public Service1010160() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取合同信息列表数据个数
	 * @param beanIn
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年2月27日 下午4:56:11
	 * @update ztz at 2015年3月11日 上午10:44:50
	 */
	public int getDataCount(Pojo1010160 beanIn, String flag) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.YGHT_HTID) ");//合同个数
			strbuf.append(" FROM ");
			strbuf.append("     YGHT A, YGXX B, BMXX C, YHXX D ");
			strbuf.append(" WHERE 1=1 ");
			if ("check".equals(flag)) {
				strbuf.append(" AND A.YGHT_HTBH = '").append(beanIn.getYGHT_HTBH()).append("'");
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
	 * @Description: 获取合同信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1010160>
	 * @author ztz
	 * @date 2015年2月27日 下午4:56:20
	 */
	public List<Pojo1010160> getDataList(Pojo1010160 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1010160> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YGHT_HTID, ");//合同ID
			strbuf.append("     A.YGHT_HTBH, ");//合同编号
			strbuf.append("     A.YGHT_YGID, ");//员工ID
			strbuf.append("     A.YGHT_BMID, ");//部门ID
			strbuf.append("     C.BMXX_BMMC AS BMMC, ");//部门名称
			strbuf.append("     CONCAT(D.YHXX_YHID, '-', B.YGXX_XM) AS YGXM, ");//员工姓名
			strbuf.append("     A.YGHT_SXRQ, ");//生效日期
			strbuf.append("     A.YGHT_JZRQ, ");//截止日期（失效日期）
			strbuf.append("     A.YGHT_HTNX, ");//合同年限
			strbuf.append("     A.YGHT_HTLX, ");//合同类型（0：临时合同，1：实习合同，2：正式合同，3：返聘合同，4：无固定期限劳动合同，5：固定期限劳动合同）
			strbuf.append("     CASE WHEN A.YGHT_HTLX = '0' THEN '临时合同' WHEN A.YGHT_HTLX = '1' THEN '实习合同' WHEN A.YGHT_HTLX = '2' THEN '正式合同' WHEN A.YGHT_HTLX = '3' THEN '返聘合同' WHEN A.YGHT_HTLX = '4' THEN '无固定期限劳动合同' WHEN A.YGHT_HTLX = '5' THEN '固定期限劳动合同' ELSE '' END AS HTLX, ");//合同类型
			strbuf.append("     A.YGHT_QDPXXY, ");//签订培训协议（0：否，1：是）
			strbuf.append("     CASE WHEN A.YGHT_QDPXXY = '0' THEN '否' WHEN A.YGHT_QDPXXY = '1' THEN '是' ELSE '' END AS QDPXXY, ");//签订培训协议
			strbuf.append("     A.YGHT_SFFJ, ");//是否上传附件（0：否，1：是）
			strbuf.append("     CASE WHEN A.YGHT_SFFJ = '0' THEN '否' WHEN A.YGHT_SFFJ = '1' THEN '是' ELSE '' END AS SFSCFJ, ");//是否上传附件
			strbuf.append("     A.YGHT_BZXX, ");//备注信息
			strbuf.append("     A.YGHT_SFYX, ");//合同状态
			strbuf.append("     CASE WHEN A.YGHT_SFYX = '0' THEN '无效' WHEN A.YGHT_SFYX = '1' THEN '有效' ELSE '' END AS SFYX, ");//是否有效
			strbuf.append("     A.YGHT_CJR, ");//创建人
			strbuf.append("     A.YGHT_CJSJ, ");//创建时间
			strbuf.append("     A.YGHT_GXR, ");//更新人
			strbuf.append("     A.YGHT_GXSJ, ");//更新时间
			strbuf.append("     A.YGHT_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     YGHT A, YGXX B, BMXX C, YHXX D ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.YGHT_YGID, A.YGHT_HTBH ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1010160>> rs = new BeanListHandler<Pojo1010160>(
					Pojo1010160.class);
			
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
	 * @FunctionName: chkDataExist
	 * @Description: 判断合同是否存在
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年3月10日 下午3:42:50
	 */
	public String chkDataExist(Pojo1010160 beanIn) throws Exception {
		String uuid = "";

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YGHT_HTID ");//合同ID
			strbuf.append(" FROM ");
			strbuf.append("     YGHT A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGHT_SCBZ = '0'");
			strbuf.append(this.searchSql(beanIn));
			
			uuid = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return uuid;
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
	 * @date 2015年2月27日 下午4:56:30
	 */
	private String searchSql(Pojo1010160 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.YGHT_YGID = B.YGXX_YGID ");
			strbuf.append(" AND A.YGHT_BMID = C.BMXX_BMID ");
			strbuf.append(" AND B.YGXX_YGID = D.YHXX_YGID ");
			strbuf.append(" AND A.YGHT_SCBZ = '0'");
			strbuf.append(" AND B.YGXX_SCBZ = '0'");
			/* 合同编号 */
			if (MyStringUtils.isNotBlank(beanIn.getYGHT_HTBH())) {
				strbuf.append(" AND A.YGHT_HTBH LIKE '%").append(beanIn.getYGHT_HTBH()).append("%'");
			}
			/* 部门名称 */
			if (MyStringUtils.isNotBlank(beanIn.getYGHT_BMID())) {
				if (!"000".equals(beanIn.getYGHT_BMID())) {
					strbuf.append(" AND A.YGHT_BMID = '").append(beanIn.getYGHT_BMID()).append("'");
				}
			}
			/* 员工姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getYGHT_YGID())) {
				if (!"000".equals(beanIn.getYGHT_YGID())) {
					strbuf.append(" AND A.YGHT_YGID = '").append(beanIn.getYGHT_YGID()).append("'");
				}
			}
			/* 合同类型 */
			if (MyStringUtils.isNotBlank(beanIn.getYGHT_HTLX())) {
				if (!"000".equals(beanIn.getYGHT_HTLX())) {
					strbuf.append(" AND A.YGHT_HTLX = '").append(beanIn.getYGHT_HTLX()).append("'");
				}
			}
			/* 合同状态 */
			if (MyStringUtils.isNotBlank(beanIn.getYGHT_SFYX())) {
				if (!"000".equals(beanIn.getYGHT_SFYX())) {
					strbuf.append(" AND A.YGHT_SFYX = '").append(beanIn.getYGHT_SFYX()).append("'");
					if ("1".equals(beanIn.getYGHT_SFYX())) {
						strbuf.append(" AND B.YGXX_YGZT IN (0, 1)");
					}
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
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
	 * @FunctionName: getHtnx
	 * @Description: 根据生效日期和失效日期，计算合同年限
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 * @return String
	 * @author ztz
	 * @date 2015年2月13日 上午11:05:58
	 */
	private String getHtnx(String startDate,String endDate) throws Exception {  
		String htnx = "";
		
		try {
			if (MyStringUtils.isNotBlank(endDate)) {
				int between_days =  daysBetween(startDate, endDate);
				int year = 0, month = 0, other_day = 0;
				if (between_days >= 365) {
					year = between_days / 365;
					other_day = between_days - year * 365;
					if (other_day >= 30) {
						month = (between_days - year * 365) / 30;
						other_day = between_days - year * 365 - month * 30;
						if (other_day > 0) {
							htnx = year + "年" + month + "个月零" + other_day + "天";
						} else {
							htnx = year + "年零" + month + "个月";
						}
					} else if (other_day < 30 && other_day > 0) {
						htnx = year + "年零" + other_day + "天";
					} else {
						htnx = year + "年整";
					}
				} else if (between_days < 365 && between_days >= 30) {
					month = between_days / 30;
					other_day = between_days - month * 30;
					if (other_day > 0) {
						htnx = month + "个月零" + other_day + "天";
					} else {
						htnx = month + "个月整";
					}
				} else {
					htnx = between_days + "天";
				}
			} else {
				htnx = "无期限";
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return htnx;
	}
	/**
	 * 
	 * @FunctionName: insertData
	 * @Description: 新增合同信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:56:46
	 */
	public boolean insertData(Pojo1010160 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		String htId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     YGHT ");
			strbuf.append(" ( ");
			strbuf.append("     YGHT_HTID, ");//合同ID
			strbuf.append("     YGHT_HTBH, ");//合同编号
			strbuf.append("     YGHT_BMID, ");//部门ID
			strbuf.append("     YGHT_YGID, ");//员工ID
			strbuf.append("     YGHT_SXRQ, ");//生效日期
			strbuf.append("     YGHT_JZRQ, ");//截止日期（失效日期）
			strbuf.append("     YGHT_HTNX, ");//合同年限
			strbuf.append("     YGHT_HTLX, ");//合同类型（0：临时合同，1：实习合同，2：正式合同，3：返聘合同，4：无固定期限劳动合同，5：固定期限劳动合同）
			//strbuf.append("     YGHT_QDPXXY, ");//签订培训协议（0：否，1：是）
			//strbuf.append("     YGHT_SFFJ, ");//是否上传附件（0：否，1：是）
			strbuf.append("     YGHT_BZXX, ");//备注信息
			strbuf.append("     YGHT_SFYX, ");//是否有效（0：无效，1：有效）
			strbuf.append("     YGHT_CJR, ");//创建人
			strbuf.append("     YGHT_CJSJ, ");//创建时间
			strbuf.append("     YGHT_GXR, ");//更新人
			strbuf.append("     YGHT_GXSJ, ");//更新时间
			strbuf.append("     YGHT_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+htId+"', ");//合同ID
			strbuf.append("     '"+beanIn.getYGHT_HTBH()+"', ");//合同编号
			strbuf.append("     '"+beanIn.getYGHT_BMID()+"', ");//部门ID
			strbuf.append("     '"+beanIn.getYGHT_YGID()+"', ");//员工ID
			strbuf.append("     '"+beanIn.getYGHT_SXRQ()+"', ");//生效日期
			strbuf.append("     '"+beanIn.getYGHT_JZRQ()+"', ");//截止日期（失效日期）
			strbuf.append("     '"+getHtnx(beanIn.getYGHT_SXRQ(), beanIn.getYGHT_JZRQ())+"', ");//合同年限
			strbuf.append("     '"+beanIn.getYGHT_HTLX()+"', ");//合同类型（0：临时合同，1：实习合同，2：正式合同，3：返聘合同，4：无固定期限劳动合同，5：固定期限劳动合同）
			//strbuf.append("     '"+beanIn.getYGHT_QDPXXY()+"', ");//签订培训协议（0：否，1：是）
			//strbuf.append("     '"+beanIn.getYGHT_SFFJ()+"', ");//是否上传附件（0：否，1：是）
			strbuf.append("     '"+beanIn.getYGHT_BZXX()+"', ");//备注信息
			strbuf.append("     '"+beanIn.getYGHT_SFYX()+"', ");//是否有效（0：无效，1：有效）
			strbuf.append("     '"+beanIn.getYGHT_CJR()+"', ");//创建人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getYGHT_CJR()+"', ");//更新人
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
	 * @Description: 修改合同信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:56:58
	 */
	public boolean updateData(Pojo1010160 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YGHT ");
			strbuf.append(" SET ");
			strbuf.append("     YGHT_HTBH='").append(beanIn.getYGHT_HTBH()).append("',");//合同编号
			strbuf.append("     YGHT_BMID='").append(beanIn.getYGHT_BMID()).append("',");//部门ID
			strbuf.append("     YGHT_YGID='").append(beanIn.getYGHT_YGID()).append("',");//员工ID
			strbuf.append("     YGHT_SXRQ='").append(beanIn.getYGHT_SXRQ()).append("',");//生效日期
			strbuf.append("     YGHT_JZRQ='").append(beanIn.getYGHT_JZRQ()).append("',");//截止日期（失效日期）
			strbuf.append("     YGHT_HTNX='").append(getHtnx(beanIn.getYGHT_SXRQ(), beanIn.getYGHT_JZRQ())).append("',");//合同年限
			strbuf.append("     YGHT_HTLX='").append(beanIn.getYGHT_HTLX()).append("',");//合同类型（0：临时合同，1：实习合同，2：正式合同，3：返聘合同，4：无固定期限劳动合同，5：固定期限劳动合同）
			//strbuf.append("     YGHT_QDPXXY='").append(beanIn.getYGHT_QDPXXY()).append("',");//签订培训协议（0：否，1：是）
			//strbuf.append("     YGHT_SFFJ='").append(beanIn.getYGHT_SFFJ()).append("',");//是否上传附件（0：否，1：是）
			strbuf.append("     YGHT_BZXX='").append(beanIn.getYGHT_BZXX()).append("',");//备注信息
			strbuf.append("     YGHT_SFYX='").append(beanIn.getYGHT_SFYX()).append("',");//是否有效
			strbuf.append("     YGHT_GXR='").append(beanIn.getYGHT_GXR()).append("',");//更新人
			strbuf.append("     YGHT_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     YGHT_HTID='").append(beanIn.getYGHT_HTID()).append("'");//合同ID
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
	 * @Description: 删除合同信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:57:08
	 */
	public boolean deleteData(Pojo1010160 beanIn) throws Exception {
		boolean result =false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YGHT ");
			strbuf.append(" SET ");
			strbuf.append("     YGHT_GXR='").append(beanIn.getYGHT_GXR()).append("',");//更新人
			strbuf.append("     YGHT_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf.append("     YGHT_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" WHERE ");
			strbuf.append("     YGHT_HTID='").append(beanIn.getYGHT_HTID()).append("'");//合同ID
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