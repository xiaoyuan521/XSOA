package com.xsoa.service.service9010000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.basetable.Pojo_BMXX;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010150;

public class Service9010150 extends BaseService {
	private DBManager db = null;

	public Service9010150() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getBmxxDataCount
	 * @Description: 获取部门信息列表数据个数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2016-12-27
	 */
	public int getBmxxDataCount(Pojo9010150 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(BMXX_BMID) ");//部门信息个数
			strbuf.append(" FROM ");
			strbuf.append("     BMXX ");
			strbuf.append(" WHERE 1=1 ");
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
	 * @FunctionName: getBmxxDataList
	 * @Description: 获取部门信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @throws Exception
	 * @return List<Pojo9010150>
	 * @author czl
	 * @date 2016-12-27
	 */
	public List<Pojo9010150> getBmxxDataList(Pojo9010150 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo9010150> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.BMXX_BMID, ");//部门ID
			strbuf.append("     A.BMXX_BMMC, ");//部门名称
			strbuf.append("     A.BMXX_BMDH, ");//部门电话
			strbuf.append("     A.BMXX_BZXX, ");//备注信息
			strbuf.append("     B.YHXX_YHMC AS BMXX_CJR, ");//创建人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.BMXX_CJSJ, '%Y%m%d'), '%Y-%m-%d') AS BMXX_CJSJ, ");//创建时间
			strbuf.append("     B.YHXX_YHMC AS BMXX_GXR, ");//更新人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.BMXX_GXSJ, '%Y%m%d'), '%Y-%m-%d') AS BMXX_GXSJ, ");//更新时间
			strbuf.append("     A.BMXX_SCBZ, ");//删除标志（0：未删除，1：已删除）
			strbuf.append("     CASE WHEN A.BMXX_SCBZ = '0' THEN '未删除' ELSE '已删除' END AS SCBZ ");//删除标志显示名称
			strbuf.append(" FROM ");
			strbuf.append("     BMXX A, YHXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.BMXX_CJR = B.YHXX_YHID ");
			strbuf.append(" AND A.BMXX_GXR = B.YHXX_YHID ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.BMXX_CJSJ, A.BMXX_BMID ");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);

			ResultSetHandler<List<Pojo9010150>> rs = new BeanListHandler<Pojo9010150>(
					Pojo9010150.class);

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
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2016-12-27
	 */
	private String searchSql(Pojo9010150 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 部门ID */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMID())) {
				strbuf.append(" AND BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("'");
			}
			/* 部门名称 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMMC())) {
				strbuf.append(" AND BMXX_BMMC LIKE '%").append(beanIn.getBMXX_BMMC()).append("%'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 *
	 * @FunctionName: insertBmxx
	 * @Description: 新增部门信息
	 * @param beanIn
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2016-12-27
	 */
	public boolean insertBmxx(Pojo_BMXX beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     BMXX ");
			strbuf.append(" ( ");
			strbuf.append("     BMXX_BMID, ");//部门ID
			strbuf.append("     BMXX_BMMC, ");//部门名称
			strbuf.append("     BMXX_BMDH, ");//部门电话
			strbuf.append("     BMXX_BZXX, ");//备注信息
			strbuf.append("     BMXX_CJR, ");//创建人
			strbuf.append("     BMXX_CJSJ, ");//创建时间
			strbuf.append("     BMXX_GXR, ");//修改人
			strbuf.append("     BMXX_GXSJ,  ");//修改时间
			strbuf.append("     BMXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     'B"+beanIn.getBMXX_BMID()+"', ");//部门ID
			strbuf.append("     '"+beanIn.getBMXX_BMMC()+"', ");//部门名称
			strbuf.append("     '"+beanIn.getBMXX_BMDH()+"', ");//部门电话
			strbuf.append("     '"+beanIn.getBMXX_BZXX()+"', ");//备注信息
			strbuf.append("     '"+beanIn.getBMXX_CJR()+"', ");//创建人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getBMXX_GXR()+"', ");//修改人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");//修改时间
			strbuf.append("        '0'  ");//删除标志（0：未删除，1：已删除）
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
	 * @FunctionName: updateBmxx
	 * @Description: 修改部门信息
	 * @param beanIn
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2016-12-27
	 */
	public boolean updateBmxx(Pojo_BMXX beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     BMXX ");
			strbuf.append(" SET ");
			strbuf.append("     BMXX_BMMC='").append(beanIn.getBMXX_BMMC()).append("', ");//部门名称
			strbuf.append("     BMXX_BMDH='").append(beanIn.getBMXX_BMDH()).append("', ");//部门电话
			strbuf.append("     BMXX_BZXX='").append(beanIn.getBMXX_BZXX()).append("', ");//备注信息
			strbuf.append("     BMXX_GXR='").append(beanIn.getBMXX_GXR()).append("', ");//修改人
			strbuf.append("     BMXX_GXSJ= DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//修改时间
			strbuf.append(" WHERE ");
			strbuf.append("     BMXX_BMID='").append(beanIn.getBMXX_BMID()).append("' ");//部门ID
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
	 * @FunctionName: deleteBmxx
	 * @Description: 删除部门信息
	 * @param beanIn
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2016-12-27
	 */
	public boolean deleteBmxx(Pojo_BMXX beanIn) throws Exception {
		boolean result =false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("     BMXX ");
			strbuf.append(" WHERE ");
			strbuf.append("     BMXX_BMID='").append(beanIn.getBMXX_BMID()).append("' ");//部门ID
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