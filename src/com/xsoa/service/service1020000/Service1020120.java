package com.xsoa.service.service1020000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.basetable.Pojo_BMXX;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020120;

public class Service1020120 extends BaseService {
	private DBManager db = null;

	public Service1020120() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取签到记录列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年2月28日 下午2:38:47
	 */
	public int getDataCount(Pojo1020120 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.QDJL_QDID) ");//签到记录个数
			strbuf.append(" FROM ");
			strbuf.append("     QDJL A, YGXX B, YHJS C, BMXX D ");
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
	 * @FunctionName: getDataList
	 * @Description: 获取签到记录列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1020120>
	 * @author ztz
	 * @date 2015年2月28日 下午2:39:09
	 */
	public List<Pojo1020120> getDataList(Pojo1020120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1020120> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.QDJL_QDID, ");//签到ID
			strbuf.append("     A.QDJL_YGID, ");//员工ID
			strbuf.append("     B.YGXX_XM AS YGXM, ");//员工姓名
			strbuf.append("     D.BMXX_BMID, ");//部门ID
			strbuf.append("     D.BMXX_BMMC, ");//部门名称
			strbuf.append("     A.QDJL_QDRQ, ");//签到日期
			strbuf.append("     A.QDJL_QDSJ, ");//签到时间
			strbuf.append("     A.QDJL_QDLX, ");//签到类型
			strbuf.append("     CASE WHEN A.QDJL_QDLX = '0' THEN '签到' WHEN A.QDJL_QDLX = '1' THEN '签退' ELSE '' END AS QDLX, ");//签到类型
			strbuf.append("     A.QDJL_CJR, ");//创建人
			strbuf.append("     A.QDJL_CJSJ, ");//创建时间
			strbuf.append("     A.QDJL_GXR, ");//更新人
			strbuf.append("     A.QDJL_GXSJ, ");//更新时间
			strbuf.append("     A.QDJL_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     QDJL A, YGXX B, YHJS C, BMXX D ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.QDJL_YGID, A.QDJL_QDRQ DESC, A.QDJL_QDSJ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1020120>> rs = new BeanListHandler<Pojo1020120>(
					Pojo1020120.class);
			
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
	 * @date 2015年2月28日 下午2:39:30
	 */
	private String searchSql(Pojo1020120 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.QDJL_YGID = B.YGXX_YGID ");
			strbuf.append(" AND B.YGXX_JSID = C.YHJS_JSID ");
			strbuf.append(" AND C.YHJS_BMID = D.BMXX_BMID ");
			strbuf.append(" AND A.QDJL_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.YHJS_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.BMXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			/* 所在部门 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMID())) {
				if (!"000".equals(beanIn.getBMXX_BMID())) {
					strbuf.append(" AND D.BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("'");
				}
			}
			/* 员工 */
			if (MyStringUtils.isNotBlank(beanIn.getQDJL_YGID())) {
				if (!"000".equals(beanIn.getQDJL_YGID())) {
					strbuf.append(" AND B.YGXX_YGID = '").append(beanIn.getQDJL_YGID()).append("'");
				}
			}
			/* 签到开始日期 */
			if (MyStringUtils.isNotBlank(beanIn.getKSSJ())) {
				strbuf.append(" AND A.QDJL_QDRQ >= '").append(beanIn.getKSSJ()).append("'");
			}
			/* 签到截止日期 */
			if (MyStringUtils.isNotBlank(beanIn.getJSSJ())) {
				strbuf.append(" AND A.QDJL_QDRQ <= '").append(beanIn.getJSSJ()).append("'");
			}
			/* 签到类型 */
			if (MyStringUtils.isNotBlank(beanIn.getQDJL_QDLX())) {
				if (!"000".equals(beanIn.getQDJL_QDLX())) {
					strbuf.append(" AND A.QDJL_QDLX = '").append(beanIn.getQDJL_QDLX()).append("'");
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
	 * @FunctionName: getBmxx
	 * @Description: 获取当前登陆用户的部门信息
	 * @param ygid
	 * @return
	 * @throws Exception
	 * @return Pojo_BMXX
	 * @author ztz
	 * @date 2015年3月20日 下午3:43:35
	 */
	public Pojo_BMXX getBmxx(String ygid) throws Exception {
		Pojo_BMXX bmxx = new Pojo_BMXX();
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     C.BMXX_BMID, ");//部门ID
			strbuf.append("     C.BMXX_BMMC ");//部门名称
			strbuf.append(" FROM ");
			strbuf.append("     YGXX A, YHJS B, BMXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGXX_JSID = B.YHJS_JSID ");
			strbuf.append(" AND B.YHJS_BMID = C.BMXX_BMID ");
			strbuf.append(" AND A.YGXX_SCBZ = '0'");
			strbuf.append(" AND B.YHJS_SCBZ = '0'");
			strbuf.append(" AND C.BMXX_SCBZ = '0'");
			strbuf.append(" AND A.YGXX_YGID = '").append(ygid).append("'");
			
			ResultSetHandler<Pojo_BMXX> rs = new BeanHandler<Pojo_BMXX>(
					Pojo_BMXX.class);
			
			bmxx = db.queryForBeanHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return bmxx;
	}
	/**
	 * 
	 * @FunctionName: getZwdj
	 * @Description: 获取当前登陆用户的职务等级
	 * @param ygid
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年3月20日 下午3:05:48
	 */
	public String getZwdj(String ygid) throws Exception {
		String zwdj = "";
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     B.YGZW_ZWDJ ");//职务等级
			strbuf.append(" FROM ");
			strbuf.append("     YGXX A, YGZW B");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGXX_YGID = B.YGZW_YGID ");
			strbuf.append(" AND A.YGXX_SCBZ = '0'");
			strbuf.append(" AND B.YGZW_SCBZ = '0'");
			strbuf.append(" AND A.YGXX_YGID = '").append(ygid).append("'");
			
			zwdj = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return zwdj;
	}
}