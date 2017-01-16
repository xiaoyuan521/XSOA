package com.xsoa.service.service9010000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010110;

public class Service9010110 extends BaseService {

	private DBManager db = null;

	public Service9010110() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getUserCount
	 * @Description: 获取用户信息列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年1月29日 上午11:01:03
	 */
	public int getUserCount(Pojo9010110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.YHXX_YHID) ");//用户信息个数
			strbuf.append(" FROM ");
			strbuf.append("     YHXX A ");
			strbuf.append(" WHERE 1=1");
			strbuf.append(this.searchSql(beanIn));

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
	 * @FunctionName: getUserList
	 * @Description: 获取用户信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo9010110>
	 * @author ztz
	 * @date 2015年1月29日 上午11:00:35
	 */
	public List<Pojo9010110> getUserList(Pojo9010110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo9010110> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YHXX_YHID, ");//用户ID
			strbuf.append("     A.YHXX_YHMC, ");//用户名称
			strbuf.append("     A.YHXX_YHMM, ");//用户密码
			strbuf.append("     A.YHXX_JSID, ");//角色ID
			strbuf.append("     B.YHJS_JSMC, ");//角色名称
			strbuf.append("     A.YHXX_SDZT, ");//锁定状态(0--正常 1--冻结)
			strbuf.append("     CASE WHEN A.YHXX_SDZT = '0' THEN '正常' ELSE '冻结' END AS SDZT, ");//锁定状态显示名称
			strbuf.append("     A.YHXX_CJR, ");//创建人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.YHXX_CJSJ, '%Y%m%d'), '%Y-%m-%d') AS YHXX_CJSJ, ");//创建时间
			strbuf.append("     A.YHXX_GXR, ");//更新人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.YHXX_GXSJ, '%Y%m%d'), '%Y-%m-%d') AS YHXX_GXSJ, ");//更新时间
			strbuf.append("     A.YHXX_SCBZ, ");//删除标志（0：未删除，1：已删除）
			strbuf.append("     CASE WHEN A.YHXX_SCBZ = '0' THEN '未删除' ELSE '已删除' END AS SCBZ ");//删除标志显示名称
			strbuf.append(" FROM ");
			strbuf.append("     YHXX A,YHJS B ");
			strbuf.append(" WHERE ");
			strbuf.append("     A.YHXX_JSID = B.YHJS_JSID ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.YHXX_JSID,A.YHXX_CJSJ");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);

			ResultSetHandler<List<Pojo9010110>> rs = new BeanListHandler<Pojo9010110>(
					Pojo9010110.class);

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
	 * @date 2015年1月29日 上午11:01:33
	 */
	private String searchSql(Pojo9010110 beanIn) throws Exception {
		StringBuffer sql = new StringBuffer();

		try {
            sql.append(" AND A.YHXX_SCBZ = '0'");
			/* 用户ID */
			if (MyStringUtils.isNotBlank(beanIn.getYHXX_YHID())) {
				sql.append(" AND A.YHXX_YHID = '").append(beanIn.getYHXX_YHID()).append("'");
			}
			/* 用户名称 */
			if (MyStringUtils.isNotBlank(beanIn.getYHXX_YHMC())) {
				sql.append(" AND A.YHXX_YHMC LIKE '%").append(beanIn.getYHXX_YHMC()).append("%'");
			}
			/* 角色 */
			if (MyStringUtils.isNotBlank(beanIn.getYHXX_JSID())) {
				if (!"000".equals(beanIn.getYHXX_JSID())) {
					sql.append(" AND A.YHXX_JSID = '").append(beanIn.getYHXX_JSID()).append("'");
				}
			}
			/* 锁定状态 */
			if (MyStringUtils.isNotBlank(beanIn.getYHXX_SDZT())) {
				if (!"000".equals(beanIn.getYHXX_SDZT())) {
					sql.append(" AND A.YHXX_SDZT = '").append(beanIn.getYHXX_SDZT()).append("'");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return sql.toString();
	}
	/**
	 *
	 * @FunctionName: insertUser
	 * @Description: 新增用户信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2016-9-6
	 */
	public boolean insertUser(Pojo9010110 beanIn) throws Exception {
		boolean result = false;
		int resultYHXX = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbufYHXX = new StringBuffer();
			strbufYHXX.append(" INSERT INTO ");
			strbufYHXX.append("     YHXX ");
			strbufYHXX.append(" ( ");
			strbufYHXX.append("     YHXX_YHID, ");// 用户id
			strbufYHXX.append("     YHXX_YHMC, ");// 用户名
			strbufYHXX.append("     YHXX_YHMM, ");// 密码
			strbufYHXX.append("     YHXX_JSID, ");// 角色id
			strbufYHXX.append("     YHXX_SDZT, ");// 锁定状态(0--正常 1--冻结)
			strbufYHXX.append("     YHXX_CJR, ");// 创建人
			strbufYHXX.append("     YHXX_CJSJ, ");// 创建时间
			strbufYHXX.append("     YHXX_GXR, ");// 修改人
			strbufYHXX.append("     YHXX_GXSJ, ");// 修改时间
			strbufYHXX.append("     YHXX_DLSJ, ");// 登陆时间
			strbufYHXX.append("     YHXX_SCBZ ");// 删除标志（0：未删除，1：已删除）
			strbufYHXX.append(" ) ");
			strbufYHXX.append(" VALUES ");
			strbufYHXX.append(" ( ");
			strbufYHXX.append("     '" + beanIn.getYHXX_YHID() + "', ");// 用户id
			strbufYHXX.append("     '" + beanIn.getYHXX_YHMC() + "', ");// 用户名
			strbufYHXX.append("     '" + beanIn.getYHXX_YHMM() + "', ");// 密码
			strbufYHXX.append("     '" + beanIn.getYHXX_JSID() + "', ");// 角色id
			strbufYHXX.append("     '" + beanIn.getYHXX_SDZT() + "', ");// 锁定状态
			strbufYHXX.append("     '" + beanIn.getYHXX_CJR() + "', ");// 创建人
			strbufYHXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");// 创建时间
			strbufYHXX.append("     '" + beanIn.getYHXX_GXR() + "', ");// 修改人
			strbufYHXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");// 修改时间
			strbufYHXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");// 登陆时间
			strbufYHXX.append("     '0'  ");// 删除标志（0：未删除，1：已删除）
			strbufYHXX.append(" ) ");
			resultYHXX = db.executeSQL(strbufYHXX);

			if (resultYHXX > 0) {
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
	 * @FunctionName: updateUser
	 * @Description: 修改用户信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月30日 上午10:05:24
	 */
	public boolean updateUser(Pojo9010110 beanIn) throws Exception {
		int resultYHXX = 0;
		boolean result = false;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbufYHXX = new StringBuffer();
			strbufYHXX.append(" UPDATE ");
			strbufYHXX.append("     YHXX ");
			strbufYHXX.append(" SET ");
			strbufYHXX.append("     YHXX_YHMC='").append(beanIn.getYHXX_YHMC()).append("', ");//用户名
			if(!"**********".equals(beanIn.getYHXX_YHMM())){
				strbufYHXX.append("     YHXX_YHMM='").append(beanIn.getYHXX_YHMM()).append("', ");//密码
			}
			strbufYHXX.append("     YHXX_JSID='").append(beanIn.getYHXX_JSID()).append("', ");//用户角色
			strbufYHXX.append("     YHXX_SDZT='").append(beanIn.getYHXX_SDZT()).append("', ");//锁定状态
			strbufYHXX.append("     YHXX_GXR='").append(beanIn.getYHXX_GXR()).append("', ");//修改人
			strbufYHXX.append("     YHXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//修改时间
			strbufYHXX.append(" WHERE ");
			strbufYHXX.append("     YHXX_YHID='").append(beanIn.getYHXX_YHID()).append("' ");//用户id
			resultYHXX = db.executeSQL(strbufYHXX);

			if(resultYHXX > 0) {
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
	 * @FunctionName: deleteUser
	 * @Description: 删除用户信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月30日 上午10:07:43
	 */
	public boolean deleteUser(Pojo_YHXX beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YHXX ");
			strbuf.append(" SET ");
			strbuf.append("     YHXX_SCBZ='1', ");//用户名
			strbuf.append("     YHXX_GXR='").append(beanIn.getYHXX_GXR()).append("', ");//修改人
			strbuf.append("     YHXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//修改时间
			strbuf.append(" WHERE ");
			strbuf.append("     YHXX_YHID='").append(beanIn.getYHXX_YHID()).append("' ");//用户id
			count = db.executeSQL(strbuf);

			if(count > 0) {
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
	 * @FunctionName: recoveryUser
	 * @Description: 恢复删除的用户信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月30日 上午10:07:58
	 */
	public boolean recoveryUser(Pojo_YHXX beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YHXX ");
			strbuf.append(" SET ");
			strbuf.append("     YHXX_SCBZ='0', ");//用户名
			strbuf.append("     YHXX_GXR='").append(beanIn.getYHXX_GXR()).append("', ");//修改人
			strbuf.append("     YHXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//修改时间
			strbuf.append(" WHERE ");
			strbuf.append("     YHXX_YHID='").append(beanIn.getYHXX_YHID()).append("' ");//用户id
			count = db.executeSQL(strbuf);

			if(count > 0) {
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