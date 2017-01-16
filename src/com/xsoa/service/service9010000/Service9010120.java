package com.xsoa.service.service9010000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.basetable.Pojo_YHJS;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010120;

public class Service9010120 extends BaseService {
	private DBManager db = null;

	public Service9010120() {
		db = new DBManager();
	}
	/**
	 *
	 * @FunctionName: getRoleDataCount
	 * @Description: 获取角色信息列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2016-9-6
	 */
	public int getRoleDataCount(Pojo9010120 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(YHJS_JSID) ");//角色信息个数
			strbuf.append(" FROM ");
			strbuf.append("     YHJS ");
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
	 * @FunctionName: getRoleDataList
	 * @Description: 获取角色信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo9010120>
	 * @author czl
	 * @date 2016-9-6
	 */
	public List<Pojo9010120> getRoleDataList(Pojo9010120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo9010120> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YHJS_JSID, ");//角色ID
			strbuf.append("     A.YHJS_JSMC, ");//角色名称
			strbuf.append("     A.YHJS_BMID, ");//部门ID
			strbuf.append("     C.BMXX_BMMC, ");//部门名称
			strbuf.append("     A.YHJS_JSMS, ");//角色描述
			strbuf.append("     B.YHXX_YHMC AS YHJS_CJR, ");//创建人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.YHJS_CJSJ, '%Y%m%d'), '%Y-%m-%d') AS YHJS_CJSJ, ");//创建时间
			strbuf.append("     B.YHXX_YHMC AS YHJS_GXR, ");//更新人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.YHJS_GXSJ, '%Y%m%d'), '%Y-%m-%d') AS YHJS_GXSJ, ");//更新时间
			strbuf.append("     A.YHJS_SCBZ, ");//删除标志（0：未删除，1：已删除）
			strbuf.append("     CASE WHEN A.YHJS_SCBZ = '0' THEN '未删除' ELSE '已删除' END AS SCBZ ");//删除标志显示名称
			strbuf.append(" FROM ");
			strbuf.append("     YHJS A, YHXX B, BMXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YHJS_CJR = B.YHXX_YHID ");
			strbuf.append(" AND A.YHJS_GXR = B.YHXX_YHID ");
			strbuf.append(" AND A.YHJS_BMID = C.BMXX_BMID ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.YHJS_BMID,A.YHJS_CJSJ ");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);

			ResultSetHandler<List<Pojo9010120>> rs = new BeanListHandler<Pojo9010120>(
					Pojo9010120.class);

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
	 * @date 2015年1月30日 下午2:57:27
	 */
	private String searchSql(Pojo9010120 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 角色ID */
			if (MyStringUtils.isNotBlank(beanIn.getYHJS_JSID())) {
				strbuf.append(" AND YHJS_JSID = '").append(beanIn.getYHJS_JSID()).append("'");
			}
			/* 角色名称 */
			if (MyStringUtils.isNotBlank(beanIn.getYHJS_JSMC())) {
				strbuf.append(" AND YHJS_JSMC LIKE '%").append(beanIn.getYHJS_JSMC()).append("%'");
			}
			/* 所属部门 */
			if (MyStringUtils.isNotBlank(beanIn.getYHJS_BMID())) {
				if (!"000".equals(beanIn.getYHJS_BMID())) {
					strbuf.append(" AND YHJS_BMID = '").append(beanIn.getYHJS_BMID()).append("'");
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
	 * @FunctionName: insertRole
	 * @Description: 新增角色信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月30日 下午2:59:45
	 */
	public boolean insertRole(Pojo_YHJS beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     YHJS ");
			strbuf.append(" ( ");
			strbuf.append("     YHJS_JSID, ");//角色id
			strbuf.append("     YHJS_JSMC, ");//角色名称
			strbuf.append("     YHJS_BMID, ");//部门ID
			strbuf.append("     YHJS_JSMS, ");//角色描述
			strbuf.append("     YHJS_CJR, ");//创建人
			strbuf.append("     YHJS_CJSJ, ");//创建时间
			strbuf.append("     YHJS_GXR, ");//修改人
			strbuf.append("     YHJS_GXSJ,  ");//修改时间
			strbuf.append("     YHJS_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+beanIn.getYHJS_JSID()+"', ");//角色id
			strbuf.append("     '"+beanIn.getYHJS_JSMC()+"', ");//角色名称
			strbuf.append("     '"+beanIn.getYHJS_BMID()+"', ");//部门ID
			strbuf.append("     '"+beanIn.getYHJS_JSMS()+"', ");//角色描述
			strbuf.append("     '"+beanIn.getYHJS_CJR()+"', ");//创建人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getYHJS_GXR()+"', ");//修改人
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
	 * @FunctionName: updateRole
	 * @Description: 修改角色信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月30日 下午3:00:51
	 */
	public boolean updateRole(Pojo_YHJS beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YHJS ");
			strbuf.append(" SET ");
			strbuf.append("     YHJS_JSMC='").append(beanIn.getYHJS_JSMC()).append("', ");//角色名称
			strbuf.append("     YHJS_BMID='").append(beanIn.getYHJS_BMID()).append("', ");//部门ID
			strbuf.append("     YHJS_JSMS='").append(beanIn.getYHJS_JSMS()).append("', ");//角色描述
			strbuf.append("     YHJS_GXR='").append(beanIn.getYHJS_GXR()).append("', ");//修改人
			strbuf.append("     YHJS_GXSJ= DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//修改时间
			strbuf.append(" WHERE ");
			strbuf.append("     YHJS_JSID='").append(beanIn.getYHJS_JSID()).append("' ");//角色id
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
	 * @FunctionName: deleteRole
	 * @Description: 删除角色信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年1月30日 下午3:01:01
	 */
	public boolean deleteRole(Pojo_YHJS beanIn) throws Exception {
		boolean result =false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("     YHJS ");
			strbuf.append(" WHERE ");
			strbuf.append("     YHJS_JSID='").append(beanIn.getYHJS_JSID()).append("' ");//角色id
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