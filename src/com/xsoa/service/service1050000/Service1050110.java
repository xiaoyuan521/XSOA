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
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050110;
import com.xsoa.pojo.custom.pojo_1050000.Pojo1050111;

public class Service1050110 extends BaseService {
	private DBManager db = null;

	public Service1050110() {
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
	 * @date 2015-02-27
	 */
	public int getDayReportCount(Pojo1050110 beanIn ,String yhid) throws Exception {
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
			strbuf.append(" AND A.RBGL_SQR = (SELECT YHXX_YGID FROM YHXX WHERE YHXX_YHID='").append(yhid).append("')");
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
	 * @return List<Pojo1050110>
	 * @author czl
	 * @date 2015-02-27
	 */
	public List<Pojo1050110> getDayReportList(Pojo1050110 beanIn, int page,
			int limit, String sort ,String yhid) throws Exception {
		List<Pojo1050110> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.RBGL_RBID, ");//日报ID
			strbuf.append("     B.YGXX_XM AS SQR, ");//申请人
			strbuf.append("     X.BMXX_BMMC AS SQBM, ");//申请部门
			strbuf.append("     A.RBGL_SQRQ, ");//申请日期
			strbuf.append("     A.RBGL_JHNR, ");//计划内容
			strbuf.append("     A.RBGL_ZJNR, ");//总结内容
			strbuf.append("     CONCAT(G.YHXX_YHID, '-', C.YGXX_XM) AS SPR, ");//审批人
			strbuf.append("     Y.BMXX_BMMC AS SPBM, ");//审批部门
			strbuf.append("     A.RBGL_ZTID, ");//状态ID
			strbuf.append("     D.DICT_ZDMC AS RBZT");//日报状态
			strbuf.append(" FROM ");
			strbuf.append("      RBGL A, YGXX B, YGXX C, DICT D, YHJS E, YHJS F, BMXX X, BMXX Y, YHXX G ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.RBGL_SQR = B.YGXX_YGID ");
			strbuf.append(" AND A.RBGL_SPR = C.YGXX_YGID ");
			strbuf.append(" AND A.RBGL_ZTID = D.DICT_ZDDM ");
			strbuf.append(" AND B.YGXX_JSID = E.YHJS_JSID ");
			strbuf.append(" AND C.YGXX_JSID = F.YHJS_JSID ");
			strbuf.append(" AND E.YHJS_BMID = X.BMXX_BMID ");
			strbuf.append(" AND F.YHJS_BMID = Y.BMXX_BMID ");
			strbuf.append(" AND C.YGXX_YGID = G.YHXX_YGID ");
			strbuf.append(" AND A.RBGL_SCBZ = '0'");
			strbuf.append(" AND D.DICT_ZDLX = 'RBZT'");
			strbuf.append(" AND A.RBGL_SQR = (SELECT YHXX_YGID FROM YHXX WHERE YHXX_YHID='").append(yhid).append("')");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.RBGL_SQRQ DESC ");
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			ResultSetHandler<List<Pojo1050110>> rs = new BeanListHandler<Pojo1050110>(
					Pojo1050110.class);
			
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
	 * @date 2015-02-27
	 */
	private String searchSql(Pojo1050110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		try {
			/* 申请日期 */
			if (MyStringUtils.isNotBlank(beanIn.getRBGL_SQRQ())) {
				strbuf.append(" AND A.RBGL_SQRQ ='").append(beanIn.getRBGL_SQRQ()).append("'");	
			}
			/* 日报状态 */
			if (MyStringUtils.isNotBlank(beanIn.getRBGL_ZTID())&&!"000".equals(beanIn.getRBGL_ZTID())) {
				strbuf.append(" AND A.RBGL_ZTID = '").append(beanIn.getRBGL_ZTID()).append("'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: insertDayReport
	 * @Description: 新增日报信息
	 * @param beanIn
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2015-02-28
	 */
	public boolean insertDayReport(Pojo1050110 beanIn) throws Exception {
		boolean result = false;
		int countRB = 0;
		int countMX = 0;
		String rbid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		String mxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		try {
			db.openConnection();
			db.beginTran();
			StringBuffer strbufRB = new StringBuffer();
			if(beanIn.getRBGL_REG().equals("sqAdd")){
				//向RBGL表插入一条数据
				strbufRB.append(" INSERT INTO ");
				strbufRB.append("     RBGL ");
				strbufRB.append(" ( ");
				strbufRB.append("     RBGL_RBID, ");//日报ID
				strbufRB.append("     RBGL_SQR, ");//申请人ID
				strbufRB.append("     RBGL_SQRQ, ");//申请日期
				strbufRB.append("     RBGL_JHNR, ");//计划内容
				strbufRB.append("     RBGL_SPR, ");//审批人ID
				strbufRB.append("     RBGL_ZTID, ");//日报状态
				strbufRB.append("     RBGL_CJR, ");//创建人
				strbufRB.append("     RBGL_CJSJ, ");//创建时间
				strbufRB.append("     RBGL_GXR, ");//更新人
				strbufRB.append("     RBGL_GXSJ ");//更新时间
				strbufRB.append(" ) ");
				strbufRB.append(" VALUES ");
				strbufRB.append(" ( ");
				strbufRB.append("     '"+rbid+"', ");//日报ID
				strbufRB.append("     '"+beanIn.getRBGL_SQR()+"', ");//申请人ID
				strbufRB.append("     '"+beanIn.getRBGL_SQRQ()+"', ");//申请日期
				strbufRB.append("     '"+beanIn.getRBGL_JHNR()+"', ");//计划内容
				strbufRB.append("     '"+beanIn.getRBGL_SPR()+"', ");//审批人ID
				strbufRB.append("     '0', ");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准）
				strbufRB.append("     '"+beanIn.getRBGL_CJR()+"', ");//创建人
				strbufRB.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
				strbufRB.append("     '"+beanIn.getRBGL_CJR()+"', ");//更新人
				strbufRB.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//更新时间
				strbufRB.append(" ) ");
			}else if(beanIn.getRBGL_REG().equals("zjAdd")){
				//修改RBGL表
				strbufRB.append(" UPDATE ");
				strbufRB.append("     RBGL ");
				strbufRB.append(" SET ");
				strbufRB.append("     RBGL_ZJNR='").append(beanIn.getRBGL_ZJNR()).append("',");//总结内容
				strbufRB.append("     RBGL_SPR='").append(beanIn.getRBGL_ZJSPR()).append("',");//总结审批人ID
				strbufRB.append("     RBGL_ZTID='4',");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准
				strbufRB.append("     RBGL_GXR='").append(beanIn.getRBGL_GXR()).append("',");//更新人
				strbufRB.append("     RBGL_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
				strbufRB.append(" WHERE ");
				strbufRB.append("     RBGL_RBID='").append(beanIn.getRBGL_RBID()).append("'");//日报ID
				countRB = db.executeSQL(strbufRB);
			}
			countRB = db.executeSQL(strbufRB);
			//向RBMX表插入一条数据
			StringBuffer strbufMX = new StringBuffer();
			strbufMX.append(" INSERT INTO ");
			strbufMX.append("     RBMX ");
			strbufMX.append(" ( ");
			strbufMX.append("     RBMX_MXID, ");//日报明细ID
			strbufMX.append("     RBMX_RBID, ");//日报ID
			strbufMX.append("     RBMX_XYCZR, ");//下一操作人
			strbufMX.append("     RBMX_ZTID, ");//日报状态ID
			strbufMX.append("     RBMX_CZR, ");//操作人ID
			strbufMX.append("     RBMX_CZSJ, ");//操作时间
			strbufMX.append("     RBMX_CJR, ");//创建人
			strbufMX.append("     RBMX_CJSJ, ");//创建时间
			strbufMX.append("     RBMX_GXR, ");//更新人
			strbufMX.append("     RBMX_GXSJ ");//更新时间
			strbufMX.append(" ) ");
			strbufMX.append(" VALUES ");
			strbufMX.append(" ( ");
			strbufMX.append("     '"+mxid+"', ");//日报明细ID
			if(beanIn.getRBGL_REG().equals("sqAdd")){
				strbufMX.append("     '"+rbid+"', ");//日报ID
				strbufMX.append("     '"+beanIn.getRBGL_SPR()+"', ");//下一操作人ID
				strbufMX.append("     '0', ");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准）
			}else if(beanIn.getRBGL_REG().equals("zjAdd")){
				strbufMX.append("     '"+beanIn.getRBGL_RBID()+"', ");//日报ID
				strbufMX.append("     '"+beanIn.getRBGL_ZJSPR()+"', ");//下一操作人ID
				strbufMX.append("     '4', ");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准）
			}
			strbufMX.append("     '"+beanIn.getRBGL_SQR()+"', ");//操作人ID
			strbufMX.append("     DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'), ");//操作时间		
			strbufMX.append("     '"+beanIn.getRBGL_CJR()+"', ");//创建人
			strbufMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbufMX.append("     '"+beanIn.getRBGL_CJR()+"', ");//更新人
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
	 * 
	 * @FunctionName: updateDayReport
	 * @Description: 修改日报信息
	 * @param beanIn
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2015-03-02
	 */
	public boolean updateDayReport(Pojo1050110 beanIn) throws Exception {
		boolean result = false;
		int countRB = 0;
		int countMX = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			//修改RBGL表
			StringBuffer strbufRB = new StringBuffer();
			strbufRB.append(" UPDATE ");
			strbufRB.append("     RBGL ");
			strbufRB.append(" SET ");
			if(beanIn.getRBGL_REG().equals("sqUpd")){
				strbufRB.append("     RBGL_JHNR='").append(beanIn.getRBGL_JHNR()).append("',");//计划内容
				strbufRB.append("     RBGL_SPR='").append(beanIn.getRBGL_SPR()).append("',");//审批人ID
				strbufRB.append("     RBGL_ZTID='0',");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准
			}else if(beanIn.getRBGL_REG().equals("zjUpd")){
				strbufRB.append("     RBGL_ZJNR='").append(beanIn.getRBGL_ZJNR()).append("',");//总结内容
				strbufRB.append("     RBGL_SPR='").append(beanIn.getRBGL_ZJSPR()).append("',");//总结审批人ID
				strbufRB.append("     RBGL_ZTID='4',");//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准
			}		
			strbufRB.append("     RBGL_GXR='").append(beanIn.getRBGL_GXR()).append("',");//更新人
			strbufRB.append("     RBGL_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbufRB.append(" WHERE ");
			strbufRB.append("     RBGL_RBID='").append(beanIn.getRBGL_RBID()).append("'");//日报ID
			countRB = db.executeSQL(strbufRB);
			//修改RBMX表
			StringBuffer strbufMX = new StringBuffer();
			strbufMX.append(" UPDATE ");
			strbufMX.append("     RBMX ");
			strbufMX.append(" SET ");
			strbufMX.append("     RBMX_XYCZR='").append(beanIn.getRBGL_SPR()).append("',");//下一操作人ID
			//日报状态（0：计划申请 1：计划流转 2：计划驳回 3：计划批准 4：总结申请 5：总结流转 6：总结驳回 7：总结批准
			strbufMX.append("     RBMX_GXR='").append(beanIn.getRBGL_GXR()).append("',");//更新人
			strbufMX.append("     RBMX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbufMX.append(" WHERE ");
			strbufMX.append(" 	  RBMX_ZTID = '0' ");
			strbufMX.append("     AND RBMX_RBID='").append(beanIn.getRBGL_RBID()).append("'");//日报ID
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
	 * 
	 * @FunctionName: deleteDayReport
	 * @Description: 删除日报信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author czl
	 * @date 2015-02-28
	 */
	public boolean deleteDayReport(Pojo1050110 beanIn) throws Exception {
		boolean result =false;
		int countRB = 0;
		int countMX = 0;

		try {
			db.openConnection();
			db.beginTran();
			//删除RBGL表
			StringBuffer strbufRB = new StringBuffer();
			strbufRB.append(" DELETE ");
			strbufRB.append("     FROM RBGL ");
			strbufRB.append(" WHERE ");
			strbufRB.append("     RBGL_RBID='").append(beanIn.getRBGL_RBID()).append("'");//日报ID
			countRB = db.executeSQL(strbufRB);
			//删除RBGL表
			StringBuffer strbufMX = new StringBuffer();
			strbufMX.append(" DELETE ");
			strbufMX.append("    FROM RBMX ");
			strbufMX.append(" WHERE ");
			strbufMX.append("     RBMX_RBID='").append(beanIn.getRBGL_RBID()).append("'");//日报ID
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
	 * @FunctionName: getTodayInfo
	 * @Description: 获取日报信息
	 * @param yhid,date
	 * @throws Exception
	 * @return Pojo1050110
	 * @author czl
	 * @date 2015-02-28
	 */
	public Pojo1050110 getTodayInfo(String yhid,String rbid,String date) throws Exception {
		Pojo1050110 result = null;
		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.RBGL_RBID, ");//日报ID
			sql.append("     A.RBGL_SQR, ");//申请人ID
			sql.append("     D.YGXX_XM AS SQR, ");//申请人
			sql.append("     A.RBGL_SQRQ, ");//申请日期
			sql.append("     A.RBGL_JHNR, ");//计划内容
			sql.append("     A.RBGL_ZJNR, ");//总结内容
			sql.append("     A.RBGL_SPR, ");//审批人
			sql.append("     A.RBGL_ZTID,");//日报状态ID
			sql.append("     C.DICT_ZDMC AS RBZT");//日报状态
			sql.append("     FROM ");
			sql.append("     RBGL A,YHXX B,DICT C,YGXX D");
			sql.append("     WHERE 1=1");
			sql.append("     AND A.RBGL_SQR = B.YHXX_YGID");
			sql.append("     AND A.RBGL_ZTID = C.DICT_ZDDM");
			sql.append("     AND A.RBGL_SQR = D.YGXX_YGID");
			sql.append("     AND C.DICT_ZDLX = 'RBZT'");
			if (MyStringUtils.isNotBlank(yhid)) {
				sql.append(" AND B.YHXX_YHID ='").append(yhid).append("'");				
			}
			if (MyStringUtils.isNotBlank(rbid)){
				sql.append(" AND A.RBGL_RBID ='").append(rbid).append("'");		
			}else{
				if (MyStringUtils.isNotBlank(date)) {	
					sql.append(" AND A.RBGL_SQRQ ='").append(date).append("'");	
				}
			}
			
			ResultSetHandler<Pojo1050110> rsh = new BeanHandler<Pojo1050110>(
					Pojo1050110.class);
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
	 * @return Pojo1050111
	 * @author czl
	 * @date 2015-02-28
	 */
	public List<Pojo1050111> getDayReportList(String rbid)
			throws Exception {
		List<Pojo1050111> result = null;
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

			ResultSetHandler<List<Pojo1050111>> rsh = new BeanListHandler<Pojo1050111>(
					Pojo1050111.class);
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
	 * @return Pojo1050111
	 * @author czl
	 * @date 2015-03-02
	 */
	public Pojo1050111 getNext(String rbid ,String reg) throws Exception {
		Pojo1050111 result = null;
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
			if(reg.equals("this")){
				sql.append(" AND A.RBMX_ZTID ='0'");
				sql.append("     ORDER BY RBMX_CZSJ ASC LIMIT 0,1 ");
			}else if(reg.equals("that")){
				sql.append(" AND A.RBMX_ZTID ='4'");
				sql.append("     ORDER BY RBMX_CZSJ DESC LIMIT 0,1 ");
			}else if(reg.equals("next")){
				sql.append("     ORDER BY RBMX_CZSJ DESC LIMIT 0,1 ");
			}
			ResultSetHandler<Pojo1050111> rsh = new BeanHandler<Pojo1050111>(
					Pojo1050111.class);
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
	 * @FunctionName: getRbzt
	 * @Description: 获取日报状态
	 * @param 
	 * @throws Exception
	 * @return Pojo1050110
	 * @author czl
	 * @date 2015-03-02
	 */
	public Pojo1050110 getRbzt() throws Exception {
		Pojo1050110 result = null;
		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.DICT_ZDMC AS RBZT ");//日报状态
			sql.append("     FROM ");
			sql.append("     DICT A");
			sql.append("     WHERE 1=1");
			sql.append(" AND A.DICT_ZDLX ='RBZT'");	
			sql.append(" AND A.DICT_ZDDM ='3'");				
			ResultSetHandler<Pojo1050110> rsh = new BeanHandler<Pojo1050110>(
					Pojo1050110.class);
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
	 * @FunctionName: getXMandBM
	 * @Description: 获取上报人姓名和部门
	 * @param yhid
	 * @throws Exception
	 * @return Pojo1050110
	 * @author czl
	 * @date 2015-02-27
	 */
	public Pojo1050110 getXMandBM(String yhid) throws Exception {
		Pojo1050110 result = null;
		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ");
			sql.append("     A.YGXX_YGID AS RBGL_SQR, ");//申请人ID
			sql.append("     A.YGXX_XM AS SQR, ");//申请人
			sql.append("     B.BMXX_BMMC AS SQBM ");//申请部门
			sql.append("     FROM ");
			sql.append("     YGXX A ,BMXX B,YHXX C, YHJS D");
			sql.append("     WHERE 1=1");
			sql.append("     AND A.YGXX_YGID = C.YHXX_YGID");
			sql.append("     AND A.YGXX_JSID = D.YHJS_JSID");
			sql.append("     AND D.YHJS_BMID = B.BMXX_BMID");
			if (MyStringUtils.isNotBlank(yhid)) {
				sql.append(" AND C.YHXX_YHID ='").append(yhid).append("'");				
			}
			ResultSetHandler<Pojo1050110> rsh = new BeanHandler<Pojo1050110>(
					Pojo1050110.class);
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