package com.framework.core;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;

public class BaseService {

	private DBManager db = null;
	private QueryRunner run = null;

	public BaseService() {
		db = new DBManager();
		run = new QueryRunner();
	}

	public String getSystemDate() {
		String result;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DATE_FORMAT(NOW(), '%Y-%m-%d')");

			ResultSetHandler<String> rsh = new ScalarHandler<String>(1);
			result = (String) run.query(db.openConnection(), sql.toString(),
					rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			result = null;
		} finally {
			db.closeConnection();
		}

		return result;
	}

	public String getSystemTimeStamp() {
		String result;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT DATE_FORMAT(NOW(), '%Y%m%d_%H%i%s')");

			ResultSetHandler<String> rsh = new ScalarHandler<String>(1);
			result = (String) run.query(db.openConnection(), sql.toString(),
					rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			result = null;
		} finally {
			db.closeConnection();
		}

		return result;
	}

	public StringBuffer getPageSqL(String strBaseSQL, int page, int limit,
			String sort) {
		StringBuffer pageSql = new StringBuffer();

		try {
			pageSql.append(strBaseSQL);
			pageSql.append(" LIMIT ");
			// 分页处理，下限。
			pageSql.append((page - 1) * limit + ", ");
			// 分页处理，上限。
			pageSql.append(limit);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			pageSql.setLength(0);
		}

		return pageSql;
	}

	/**
	 * @Method: safeNull
	 * @Description: 如果字符串为NULL，则返回空串
	 * @param para-字符串对象
	 * @return
	 * @throws Exception 返回值说明
	 * @author misty
	 * @date 2014年5月19日 下午9:31:00
	 */
	public String safeNull(String para) throws Exception{
		String result = "";
		try{
			result = MyStringUtils.safeToString(para);
		}catch (Exception e){
			MyLogger.error(this.getClass().getName(), e);
			result = "";
		}
		return result;
	}

	/**
	 * @Method: trimNull
	 * @Description: 去空格安全返回字符串
	 * @param para-字符串对象
	 * @return
	 * @throws Exception 返回值说明
	 * @author misty
	 * @date 2014年5月19日 下午9:31:00
	 */
	public String trimNull(String para) throws Exception{
		String result = "";
		try{
			result = MyStringUtils.trimToEmpty(para);
		}catch (Exception e){
			MyLogger.error(this.getClass().getName(), e);
			result = "";
		}
		return result;
	}

	/**
	 * @Method: safeNull
	 * @Description: 去空格安全返回字符串
	 * @param para-字符串对象
	 * @return
	 * @throws Exception 返回值说明
	 * @author misty
	 * @date 2014年5月19日 下午9:31:00
	 */
	public String trimNumberNull(String para) throws Exception{
		String result = "NULL";
		try{
			result = MyStringUtils.trimToEmpty(para);
			if("".equals(result)) result = "NULL";
		}catch (Exception e){
			MyLogger.error(this.getClass().getName(), e);
			result = "NULL";
		}
		return result;
	}

}
