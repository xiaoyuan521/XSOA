package com.xsoa.service.service9020000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_9020000.Pojo9020110;


/**
 * @ClassName: Service9020110
 * @Package:com.xsoa.service.service9020000
 * @Description: 登录日志Service
 * @author czl
 * @date 2015-3-20
 */
public class Service9020110 extends BaseService {
	private DBManager db = null;

	public Service9020110() {
		db = new DBManager();
	}

	/**
	 * @FunctionName: getDLRZDataCount
	 * @Description: 获取登录日志列表数据个数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-3-20
	 */
	public int getDLRZDataCount(Pojo9020110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(DLRZ_DLID) ");//登录日志个数
			strbuf.append(" FROM ");
			strbuf.append("     DLRZ A");
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
	 * @FunctionName: getDLRZDataList
	 * @Description: 获取登录日志列表数据明细
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo9020110>
	 * @author czl
	 * @date 2015-3-20
	 */
	public List<Pojo9020110> getDLRZDataList(Pojo9020110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo9020110> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.DLRZ_DLID, ");//职务ID
			strbuf.append("     A.DLRZ_YHMC, ");//职务名称
			strbuf.append("     A.DLRZ_YHID, ");//部门ID
			strbuf.append("     A.DLRZ_DLSJ, ");//角色ID
			strbuf.append("     A.DLRZ_DLIP ");//备注
			strbuf.append(" FROM ");
			strbuf.append("     DLRZ AS A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.DLRZ_DLSJ DESC");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);

			ResultSetHandler<List<Pojo9020110>> rs = new BeanListHandler<Pojo9020110>(
					Pojo9020110.class);

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
	 * @FunctionName: searchSql
	 * @Description: 查询条件部分
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2015-3-20
	 */
	private String searchSql(Pojo9020110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();

		try {
			/* 用户ID */
			if (MyStringUtils.isNotBlank(beanIn.getDLRZ_YHID()) && !("000".equals(beanIn.getDLRZ_YHID()))) {
				strbuf.append(" AND A.DLRZ_YHID = '").append(beanIn.getDLRZ_YHID()).append("'");
			}
			/* 开始时间 */
			if (MyStringUtils.isNotBlank(beanIn.getDLRZ_KSSJ())) {
				strbuf.append(" AND A.DLRZ_DLSJ >= '").append(beanIn.getDLRZ_KSSJ()).append("'");
			}
			/* 结束时间 */
			if (MyStringUtils.isNotBlank(beanIn.getDLRZ_JSSJ())) {
				strbuf.append(" AND A.DLRZ_DLSJ <= '").append(beanIn.getDLRZ_JSSJ()).append("'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
}