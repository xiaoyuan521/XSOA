package com.xsoa.service.service1050000;

import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050130;
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050131;

public class Service1050130 extends BaseService {
	private DBManager db = null;

	public Service1050130() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDayReportCount
	 * @Description: 获取日报信息列表数据个数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-03-06
	 */
	public int getDayReportCount(Pojo1050130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.RBGL_RBID) ");//日报ID
			strbuf.append(" FROM ");
			strbuf.append("     RBGL A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.RBGL_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
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
	 * @FunctionName: getDayReportList
	 * @Description: 获取日报信息列表数据明细
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1050130>
	 * @author czl
	 * @date 2015-03-06
	 */
	public List<Pojo1050130> getDayReportList(Pojo1050130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1050130> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.RBGL_RBID, ");//日报ID
			strbuf.append("     B.YGXX_XM AS SQR, ");//申请人
			strbuf.append("     X.BMXX_BMMC AS SQBM, ");//申请部门
			strbuf.append("     A.RBGL_SQRQ, ");//申请日期
			strbuf.append("     A.RBGL_SPR, ");//审批人ID
			strbuf.append("     C.YGXX_XM AS SPR, ");//审批人
			strbuf.append("     Y.BMXX_BMMC AS SPBM, ");//审批部门
			strbuf.append("     A.RBGL_JHNR, ");//计划内容
			strbuf.append("     A.RBGL_ZJNR, ");//总结内容
			strbuf.append("     A.RBGL_ZTID, ");//状态ID
			strbuf.append("     D.DICT_ZDMC AS RBZT");//日报状态
			strbuf.append(" FROM ");
			strbuf.append("     RBGL A, YGXX B, YGXX C, DICT D, YHJS E, YHJS F, BMXX X, BMXX Y ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.RBGL_SQR = B.YGXX_YGID ");
			strbuf.append(" AND A.RBGL_SPR = C.YGXX_YGID ");
			strbuf.append(" AND A.RBGL_ZTID = D.DICT_ZDDM ");
			strbuf.append(" AND B.YGXX_JSID = E.YHJS_JSID ");
			strbuf.append(" AND C.YGXX_JSID = F.YHJS_JSID ");
			strbuf.append(" AND E.YHJS_BMID = X.BMXX_BMID ");
			strbuf.append(" AND F.YHJS_BMID = Y.BMXX_BMID ");
			strbuf.append(" AND A.RBGL_SCBZ = '0'");
			strbuf.append(" AND D.DICT_ZDLX = 'RBZT'");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.RBGL_SQRQ DESC ");
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1050130>> rs = new BeanListHandler<Pojo1050130>(
					Pojo1050130.class);
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
	 * @author czl
	 * @date 2015-03-06
	 */
	private String searchSql(Pojo1050130 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		try {
			/* 申请日期 */
			if (MyStringUtils.isNotBlank(beanIn.getRBGL_SQRQ())) {
				strbuf.append(" AND A.RBGL_SQRQ ='").append(beanIn.getRBGL_SQRQ()).append("'");	
			}
			/* 申请人 */
			if (MyStringUtils.isNotBlank(beanIn.getRBGL_SQR())) {
				strbuf.append(" AND A.RBGL_SQR IN (SELECT YGXX_YGID FROM YGXX WHERE YGXX_XM LIKE '%").append(beanIn.getRBGL_SQR()).append("%')");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * @FunctionName: getTodayInfo
	 * @Description: 获取日报信息
	 * @param yhid,date
	 * @throws Exception
	 * @return Pojo1050130
	 * @author czl
	 * @date 2015-03-06
	 */
	public Pojo1050130 getTodayInfo(String rbid) throws Exception {
		Pojo1050130 result = null;
		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.RBGL_RBID, ");//日报ID
			sql.append("     A.RBGL_SQR, ");//申请人ID
			sql.append("     A.RBGL_SQRQ, ");//申请日期
			sql.append("     A.RBGL_JHNR, ");//计划内容
			sql.append("     A.RBGL_ZJNR, ");//总结内容
			sql.append("     A.RBGL_SPR, ");//审批人
			sql.append("     A.RBGL_ZTID,");//日报状态ID
			sql.append("     C.DICT_ZDMC AS RBZT");//日报状态
			sql.append("     FROM ");
			sql.append("     RBGL A,DICT C");
			sql.append("     WHERE 1=1");
			sql.append("     AND A.RBGL_ZTID = C.DICT_ZDDM");
			sql.append("     AND C.DICT_ZDLX = 'RBZT'");
			if (MyStringUtils.isNotBlank(rbid)) {
				sql.append(" AND A.RBGL_RBID ='").append(rbid).append("'");				
			}
			ResultSetHandler<Pojo1050130> rsh = new BeanHandler<Pojo1050130>(
					Pojo1050130.class);
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
	 * @FunctionName: getDayReportList
	 * @Description: 获取日报流程
	 * @param rbid
	 * @throws Exception
	 * @return Pojo1050131
	 * @author czl
	 * @date 2015-03-06
	 */
	public List<Pojo1050131> getDayReportList(String rbid)
			throws Exception {
		List<Pojo1050131> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT A.RBMX_MXID,");//日报明细ID
			sql.append("  	  A.RBMX_CZSJ,");//操作时间
			sql.append("  	  C.DICT_ZDMC AS RBMX_CZFS,");//操作方式
			sql.append("  	  A.RBMX_CZNR,");//操作内容
			sql.append("  	  B.YGXX_XM AS RBMX_CZRXM");//操作人
			sql.append("  FROM RBMX A,YGXX B,DICT C");
			sql.append("  WHERE A.RBMX_CZR = B.YGXX_YGID AND A.RBMX_ZTID=C.DICT_ZDDM");
			sql.append("  AND A.RBMX_SCBZ = '0'");
			sql.append("  AND C.DICT_ZDLX = 'RBZT'");
			if (MyStringUtils.isNotBlank(rbid)) {
				sql.append(" AND A.RBMX_RBID ='").append(rbid).append("'");					
			}
			sql.append("  ORDER BY A.RBMX_CZSJ ASC");

			ResultSetHandler<List<Pojo1050131>> rsh = new BeanListHandler<Pojo1050131>(
					Pojo1050131.class);
			result = db.queryForBeanListHandler(sql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getNext
	 * @Description: 获取下一等待处理人
	 * @param rbid,reg
	 * @throws Exception
	 * @return Pojo1050131
	 * @author czl
	 * @date 2015-03-06
	 */
	public Pojo1050131 getNext(String rbid) throws Exception {
		Pojo1050131 result = null;
		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.RBMX_MXID, ");//日报明细ID		
			sql.append("     A.RBMX_XYCZR, ");//下一操作人ID
			sql.append("     B.YGXX_XM AS RBMX_CZRXM ");//下一操作人姓名
			sql.append("     FROM ");
			sql.append("     RBMX A,YGXX B");
			sql.append("     WHERE 1=1");
			sql.append("     AND A.RBMX_XYCZR = B.YGXX_YGID");
			if (MyStringUtils.isNotBlank(rbid)) {
				sql.append(" AND A.RBMX_RBID ='").append(rbid).append("'");				
			}
			sql.append("     ORDER BY RBMX_CZSJ DESC LIMIT 0,1 ");
			
			
			ResultSetHandler<Pojo1050131> rsh = new BeanHandler<Pojo1050131>(
					Pojo1050131.class);
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