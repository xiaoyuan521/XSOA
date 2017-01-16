package com.xsoa.service.service9030000;


import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_9030000.Pojo9030120;

public class Service9030120 extends BaseService {
	private DBManager db = null;

	public Service9030120() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getInfo
	 * @Description: 获得个人信息
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo9030120
	 * @author czl
	 * @date 2015-3-12
	 */
	public Pojo9030120 getInfo(String yhid) throws Exception {
		Pojo9030120 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.YHXX_YHID, ");//用户ID
			sql.append("     A.YHXX_YHMC, ");//用户姓名
			sql.append("     B.YHJS_JSMC AS JSMC, ");//角色名称
			sql.append("     A.YHXX_YHMM");//用户密码
			sql.append("     FROM ");
			sql.append("     YHXX A ,YHJS B");
			sql.append("     WHERE A.YHXX_JSID = B.YHJS_JSID");
			if (MyStringUtils.isNotBlank(yhid)) {
				sql.append(" AND A.YHXX_YHID ='").append(yhid).append("'");	
						
			}
			ResultSetHandler<Pojo9030120> rsh = new BeanHandler<Pojo9030120>(
					Pojo9030120.class);
			result = db.queryForBeanHandler(sql, rsh);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: updateInfo
	 * @Description: 修改个人信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-3-12
	 */
	public int updateInfo(Pojo9030120 beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YHXX ");
			strbuf.append(" SET ");
			strbuf.append("     YHXX_YHMM='").append(beanIn.getYHXX_YHMM()).append("',");// 用户密码
			strbuf.append("     YHXX_GXR='").append(beanIn.getYHXX_GXR()).append("',");// 修改人
			strbuf.append("     YHXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf.append(" WHERE 1 = 1");
			strbuf.append("  AND   YHXX_YHID='").append(beanIn.getYHXX_YHID()).append("'");// UUID
			result = db.executeSQL(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: validatePas
	 * @Description: 验证原密码
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo9030120
	 * @author czl
	 * @date 2015-3-12
	 */
	public Pojo9030120 validatePas(Pojo9030120 beanIn) throws Exception {
		Pojo9030120 result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.YHXX_YHID, ");//用户ID
			sql.append("     A.YHXX_YHMC ");//用户姓名
			sql.append("     FROM ");
			sql.append("     YHXX A");
			sql.append("     WHERE A.YHXX_SCBZ='0'");
			sql.append(" AND A.YHXX_YHID ='").append(beanIn.getYHXX_YHID()).append("'");	
			sql.append(" AND A.YHXX_YHMM ='").append(beanIn.getYHXX_YHMM()).append("'");	
			ResultSetHandler<Pojo9030120> rsh = new BeanHandler<Pojo9030120>(
					Pojo9030120.class);
			result = db.queryForBeanHandler(sql, rsh);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
}