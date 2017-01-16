package com.xsoa.service.service1010000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010150;

public class Service1010150 extends BaseService {
	private DBManager db = null;

	public Service1010150() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取档案信息列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年2月27日 下午4:50:02
	 */
	public int getDataCount(Pojo1010150 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.YGSJ_SJID) ");//档案个数
			strbuf.append(" FROM ");
			strbuf.append("     YGSJ A, YGXX B, YHXX C, YHJS D, BMXX E, DICT F ");
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
	 * @Description: 获取档案信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1010150>
	 * @author ztz
	 * @date 2015年2月27日 下午4:50:12
	 */
	public List<Pojo1010150> getDataList(Pojo1010150 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1010150> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YGSJ_SJID, ");//事件ID
			strbuf.append("     E.BMXX_BMID, ");//部门ID
			strbuf.append("     E.BMXX_BMMC, ");//部门名称
			strbuf.append("     A.YGSJ_YGID, ");//员工ID
			strbuf.append("     CONCAT(C.YHXX_YHID, '-', B.YGXX_XM) AS YGXM, ");//员工姓名
			strbuf.append("     A.YGSJ_SJLX, ");//事件类型（0：奖励，1：惩罚，2：加班，3：请假）
			strbuf.append("     F.DICT_ZDMC AS SJLX, ");//事件类型
			strbuf.append("     A.YGSJ_SJMS, ");//事件描述
			strbuf.append("     A.YGSJ_FSSJ, ");//事件发生时间
			strbuf.append("     A.YGSJ_JCJE, ");//奖惩金额
			strbuf.append("     A.YGSJ_JLR, ");//记录人
			strbuf.append("     C.YHXX_YHMC AS JLRXM, ");//记录人姓名
			strbuf.append("     A.YGSJ_CJR, ");//创建人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.YGSJ_CJSJ, '%Y%m%d %H:%i:%s'), '%Y-%m-%d %H:%i:%s') AS YGSJ_CJSJ, ");//创建时间
			strbuf.append("     A.YGSJ_GXR, ");//更新人
			strbuf.append("     A.YGSJ_GXSJ, ");//更新时间
			strbuf.append("     A.YGSJ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     YGSJ A, YGXX B, YHXX C, YHJS D, BMXX E, DICT F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.YGSJ_YGID, A.YGSJ_SJLX ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1010150>> rs = new BeanListHandler<Pojo1010150>(
					Pojo1010150.class);
			
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
	 * @date 2015年2月27日 下午4:50:23
	 */
	private String searchSql(Pojo1010150 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.YGSJ_YGID = B.YGXX_YGID ");
			strbuf.append(" AND A.YGSJ_JLR = C.YHXX_YHID ");
			strbuf.append(" AND B.YGXX_JSID = D.YHJS_JSID ");
			strbuf.append(" AND D.YHJS_BMID = E.BMXX_BMID ");
			strbuf.append(" AND A.YGSJ_SJLX = F.DICT_ZDDM ");
			strbuf.append(" AND A.YGSJ_SCBZ = '0'");
			strbuf.append(" AND B.YGXX_SCBZ = '0'");
			strbuf.append(" AND C.YHXX_SCBZ = '0'");
			strbuf.append(" AND D.YHJS_SCBZ = '0'");
			strbuf.append(" AND E.BMXX_SCBZ = '0'");
			strbuf.append(" AND F.DICT_SCBZ = '0'");
			strbuf.append(" AND F.DICT_ZDLX = 'SJLX'");
			/* 所在部门 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMID())) {
				if (!"000".equals(beanIn.getBMXX_BMID())) {
					strbuf.append(" AND E.BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("'");
				}
			}
			/* 员工姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getYGSJ_YGID())) {
				if (!"000".equals(beanIn.getYGSJ_YGID())) {
					strbuf.append(" AND A.YGSJ_YGID = '").append(beanIn.getYGSJ_YGID()).append("'");
				}
			}
			/* 事件类型 */
			if (MyStringUtils.isNotBlank(beanIn.getYGSJ_SJLX())) {
				if (!"000".equals(beanIn.getYGSJ_SJLX())) {
					strbuf.append(" AND A.YGSJ_SJLX = '").append(beanIn.getYGSJ_SJLX()).append("'");
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
	 * @FunctionName: insertData
	 * @Description: 新增档案信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:50:34
	 */
	public boolean insertData(Pojo1010150 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		String sjId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数

		try {
			db.openConnection();
			db.beginTran();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     YGSJ ");
			strbuf.append(" ( ");
			strbuf.append("     YGSJ_SJID, ");//事件ID
			strbuf.append("     YGSJ_YGID, ");//员工ID
			strbuf.append("     YGSJ_SJLX, ");//事件类型（0：奖励，1：惩罚，2：加班，3：请假）
			strbuf.append("     YGSJ_SJMS, ");//事件描述
			strbuf.append("     YGSJ_FSSJ, ");//事件发生时间
			if ("0".equals(beanIn.getYGSJ_SJLX()) || "1".equals(beanIn.getYGSJ_SJLX())) {
				strbuf.append("     YGSJ_JCJE, ");//奖惩金额
			}
			strbuf.append("     YGSJ_JLR, ");//记录人
			strbuf.append("     YGSJ_CJR, ");//创建人
			strbuf.append("     YGSJ_CJSJ, ");//创建时间
			strbuf.append("     YGSJ_GXR, ");//更新人
			strbuf.append("     YGSJ_GXSJ, ");//更新时间
			strbuf.append("     YGSJ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+sjId+"', ");//事件ID
			strbuf.append("     '"+beanIn.getYGSJ_YGID()+"', ");//员工ID
			strbuf.append("     '"+beanIn.getYGSJ_SJLX()+"', ");//事件类型（0：奖励，1：惩罚，2：加班，3：请假）
			strbuf.append("     '"+beanIn.getYGSJ_SJMS()+"', ");//事件描述
			strbuf.append("     '"+beanIn.getYGSJ_FSSJ()+"', ");//事件发生时间
			if ("0".equals(beanIn.getYGSJ_SJLX()) || "1".equals(beanIn.getYGSJ_SJLX())) {
				strbuf.append("     '"+beanIn.getYGSJ_JCJE()+"', ");//奖惩金额
			}
			strbuf.append("     '"+beanIn.getYGSJ_CJR()+"', ");//记录人
			strbuf.append("     '"+beanIn.getYGSJ_CJR()+"', ");//创建人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getYGSJ_CJR()+"', ");//更新人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf.append("     '0'  ");//删除标志（0：未删除，1：已删除）
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
	 * @FunctionName: updateData
	 * @Description: 修改档案信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:50:45
	 */
	public boolean updateData(Pojo1010150 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YGSJ ");
			strbuf.append(" SET ");
			strbuf.append("     YGSJ_YGID='").append(beanIn.getYGSJ_YGID()).append("',");//员工ID
			strbuf.append("     YGSJ_SJLX='").append(beanIn.getYGSJ_SJLX()).append("',");//事件类型（0：奖励，1：惩罚，2：加班，3：请假）
			strbuf.append("     YGSJ_SJMS='").append(beanIn.getYGSJ_SJMS()).append("',");//事件描述
			strbuf.append("     YGSJ_FSSJ='").append(beanIn.getYGSJ_FSSJ()).append("',");//事件发生时间
			if ("0".equals(beanIn.getYGSJ_SJLX()) || "1".equals(beanIn.getYGSJ_SJLX())) {
				strbuf.append("     YGSJ_JCJE='").append(beanIn.getYGSJ_JCJE()).append("',");//奖惩金额
			} else {
				strbuf.append("     YGSJ_JCJE=null,");//奖惩金额
			}
			strbuf.append("     YGSJ_GXR='").append(beanIn.getYGSJ_GXR()).append("',");//更新人
			strbuf.append("     YGSJ_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     YGSJ_SJID='").append(beanIn.getYGSJ_SJID()).append("'");//事件ID
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
	 * @FunctionName: deleteData
	 * @Description: 删除档案信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:50:53
	 */
	public boolean deleteData(Pojo1010150 beanIn) throws Exception {
		boolean result =false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YGSJ ");
			strbuf.append(" SET ");
			strbuf.append("     YGSJ_GXR='").append(beanIn.getYGSJ_GXR()).append("',");//更新人
			strbuf.append("     YGSJ_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf.append("     YGSJ_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" WHERE ");
			strbuf.append("     YGSJ_SJID='").append(beanIn.getYGSJ_SJID()).append("'");//事件ID
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
	 * @FunctionName: getBmid
	 * @Description: 获取当前登陆用户的部门ID
	 * @param ygid
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年3月19日 上午11:43:03
	 */
	public String getBmid(String ygid) throws Exception {
		String bmid = "";
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     C.BMXX_BMID ");//部门ID
			strbuf.append(" FROM ");
			strbuf.append("     YGXX A, YHJS B, BMXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGXX_JSID = B.YHJS_JSID ");
			strbuf.append(" AND B.YHJS_BMID = C.BMXX_BMID ");
			strbuf.append(" AND A.YGXX_SCBZ = '0'");
			strbuf.append(" AND B.YHJS_SCBZ = '0'");
			strbuf.append(" AND C.BMXX_SCBZ = '0'");
			strbuf.append(" AND A.YGXX_YGID = '").append(ygid).append("'");
			
			bmid = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return bmid;
	}
}