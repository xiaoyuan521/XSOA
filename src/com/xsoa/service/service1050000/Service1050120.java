package com.xsoa.service.service1050000;

import java.util.List;
import java.util.UUID;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050120;
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050121;

public class Service1050120 extends BaseService {
	private DBManager db = null;

	public Service1050120() {
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
	 * @date 2015-03-02
	 */
	public int getDayReportCount(Pojo1050120 beanIn ,String ygid) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.RBGL_RBID) ");//日报ID
			strbuf.append(" FROM ");
			strbuf.append("     RBGL A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND (A.RBGL_ZTID = '0' OR A.RBGL_ZTID = '1' OR A.RBGL_ZTID = '4' OR A.RBGL_ZTID = '5')");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准）
			strbuf.append(" AND A.RBGL_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND A.RBGL_SPR = '").append(ygid).append("'");
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
	 * @return List<Pojo1050120>
	 * @author czl
	 * @date 2015-03-02
	 */
	public List<Pojo1050120> getDayReportList(Pojo1050120 beanIn, int page,
			int limit, String sort ,String ygid) throws Exception {
		List<Pojo1050120> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.RBGL_RBID, ");//日报ID
			strbuf.append("     CONCAT(F.YHXX_YHID, '-', B.YGXX_XM) AS SQR, ");//申请人
			strbuf.append("     X.BMXX_BMMC AS SQBM, ");//申请部门
			strbuf.append("     A.RBGL_SQRQ, ");//申请日期
			strbuf.append("     A.RBGL_SQR, ");//申请人ID
			strbuf.append("     A.RBGL_SPR, ");//审批人ID
			strbuf.append("     A.RBGL_JHNR, ");//计划内容
			strbuf.append("     A.RBGL_ZJNR, ");//总结内容
			strbuf.append("     A.RBGL_ZTID, ");//状态ID
			strbuf.append("     D.DICT_ZDMC AS RBZT");//日报状态
			strbuf.append(" FROM ");
			strbuf.append("     RBGL A, YGXX B, DICT D, YHJS E, BMXX X, YHXX F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.RBGL_SQR = B.YGXX_YGID ");
			strbuf.append(" AND A.RBGL_ZTID = D.DICT_ZDDM ");
			strbuf.append(" AND B.YGXX_JSID = E.YHJS_JSID ");
			strbuf.append(" AND E.YHJS_BMID = X.BMXX_BMID ");
			strbuf.append(" AND B.YGXX_YGID = F.YHXX_YGID ");
			strbuf.append(" AND (A.RBGL_ZTID = '0' OR A.RBGL_ZTID = '1' OR A.RBGL_ZTID = '4' OR A.RBGL_ZTID = '5')");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准）
			strbuf.append(" AND A.RBGL_SCBZ = '0'");
			strbuf.append(" AND D.DICT_ZDLX = 'RBZT'");
			strbuf.append(" AND A.RBGL_SPR = '").append(ygid).append("'");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.RBGL_SQRQ DESC ");
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1050120>> rs = new BeanListHandler<Pojo1050120>(
					Pojo1050120.class);
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
	 * @date 2015-03-02
	 */
	private String searchSql(Pojo1050120 beanIn) throws Exception {
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
	 * 
	 * @FunctionName: approveDayReport
	 * @Description: 审批日报信息
	 * @param beanIn
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2015-03-02
	 */
	public boolean approveDayReport(Pojo1050121 beanIn ,String ygid) throws Exception {
		boolean result = false;
		int countRB = 0;
		int countMX = 0;
		String mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		try {
			db.openConnection();
			db.beginTran();
			//修改RBGL表
			StringBuffer strbufRB = new StringBuffer();
			strbufRB.append(" UPDATE ");
			strbufRB.append("     RBGL ");
			strbufRB.append(" SET ");
			strbufRB.append("     RBGL_SPR='").append(beanIn.getRBMX_XYCZR()).append("',");//审批人ID
			strbufRB.append("     RBGL_ZTID='").append(beanIn.getRBMX_ZTID()).append("',");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准
			strbufRB.append("     RBGL_GXR='").append(beanIn.getRBMX_GXR()).append("',");//更新人
			strbufRB.append("     RBGL_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbufRB.append(" WHERE ");
			strbufRB.append("     RBGL_RBID='").append(beanIn.getRBMX_RBID()).append("'");//日报ID
			countRB = db.executeSQL(strbufRB);
			//向RBMX表插入一条数据
			StringBuffer strbufMX = new StringBuffer();
			strbufMX.append(" INSERT INTO ");
			strbufMX.append("     RBMX ");
			strbufMX.append(" ( ");
			strbufMX.append("     RBMX_MXID, ");//日报明细ID
			strbufMX.append("     RBMX_RBID, ");//日报ID
			strbufMX.append("     RBMX_CZR, ");//操作人ID
			strbufMX.append("     RBMX_CZSJ, ");//操作时间
			strbufMX.append("     RBMX_CZNR, ");//操作内容
			strbufMX.append("     RBMX_XYCZR, ");//下一操作人
			strbufMX.append("     RBMX_ZTID, ");//日报状态ID
			strbufMX.append("     RBMX_CJR, ");//创建人
			strbufMX.append("     RBMX_CJSJ, ");//创建时间
			strbufMX.append("     RBMX_GXR, ");//更新人
			strbufMX.append("     RBMX_GXSJ ");//更新时间
			strbufMX.append(" ) ");
			strbufMX.append(" VALUES ");
			strbufMX.append(" ( ");
			strbufMX.append("     '"+mxid+"', ");//日报明细ID
			strbufMX.append("     '"+beanIn.getRBMX_RBID()+"', ");//日报ID
			strbufMX.append("     '"+ygid+"', ");//操作人ID
			strbufMX.append("     DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'), ");//操作时间
			strbufMX.append("     '"+beanIn.getRBMX_CZNR()+"', ");//操作内容
			strbufMX.append("     '"+beanIn.getRBMX_XYCZR()+"', ");//下一操作人ID
			strbufMX.append("     '"+beanIn.getRBMX_ZTID()+"', ");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准）
			strbufMX.append("     '"+beanIn.getRBMX_GXR()+"', ");//创建人
			strbufMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbufMX.append("     '"+beanIn.getRBMX_GXR()+"', ");//更新人
			strbufMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//更新时间
			strbufMX.append(" ) ");
			countMX = db.executeSQL(strbufMX);
			if (countRB > 0 && countMX > 0) {
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
	 * @FunctionName: getSFZJ
	 * @Description: 获得审批人是否有终结权限
	 * @param sqrid,ygid
	 * @throws Exception
	 * @return Pojo1050121
	 * @author czl
	 * @date 2015-03-09
	 */
	public Pojo1050121 getSFZJ(String sqrid, String ygid) throws Exception {
		Pojo1050121 result = null;
		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.LCMX_KFZJ AS SFZJ ");//是否终结
			sql.append("     FROM ");
			sql.append("     LCMX A");
			sql.append("     WHERE A.LCMX_SCBZ='0'");
			if (MyStringUtils.isNotBlank(ygid)) {
				sql.append("     AND A.LCMX_SPID = '").append(ygid).append("'");
			}
			sql.append("     AND A.LCMX_LCID =(");			
			sql.append(" SELECT ");
			sql.append("     A.LCXX_LCID ");//申请人ID
			sql.append("     FROM ");
			sql.append("     LCXX A");
			sql.append("     WHERE A.LCXX_SCBZ='0'");
			sql.append("     AND A.LCXX_GLGN = 'SPLC_RBGL'");
			if (MyStringUtils.isNotBlank(sqrid)) {
				sql.append("     AND A.LCXX_SQDM = '").append(sqrid).append("'");
			}
			sql.append(")");
			ResultSetHandler<Pojo1050121> rsh = new BeanHandler<Pojo1050121>(
					Pojo1050121.class);
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
	 * @FunctionName: getTodayInfo
	 * @Description: 获取日报信息
	 * @param yhid,date
	 * @throws Exception
	 * @return Pojo1050120
	 * @author czl
	 * @date 2015-03-04
	 */
	public Pojo1050120 getTodayInfo(String rbid) throws Exception {
		Pojo1050120 result = null;
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
			ResultSetHandler<Pojo1050120> rsh = new BeanHandler<Pojo1050120>(
					Pojo1050120.class);
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
	 * @return Pojo1050121
	 * @author czl
	 * @date 2015-03-04
	 */
	public List<Pojo1050121> getDayReportList(String rbid)
			throws Exception {
		List<Pojo1050121> result = null;
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

			ResultSetHandler<List<Pojo1050121>> rsh = new BeanListHandler<Pojo1050121>(
					Pojo1050121.class);
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
	 * @return Pojo1050121
	 * @author czl
	 * @date 2015-03-04
	 */
	public Pojo1050121 getNext(String rbid) throws Exception {
		Pojo1050121 result = null;
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
			
			
			ResultSetHandler<Pojo1050121> rsh = new BeanHandler<Pojo1050121>(
					Pojo1050121.class);
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