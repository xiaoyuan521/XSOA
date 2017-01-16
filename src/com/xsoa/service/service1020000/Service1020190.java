package com.xsoa.service.service1020000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020190;

public class Service1020190 extends BaseService {
	private DBManager db = null;

	public Service1020190() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取出差申请列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年3月9日 上午10:24:07
	 */
	public int getDataCount(Pojo1020190 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.CCSQ_SQID) ");//出差申请个数
			strbuf.append(" FROM ");
			strbuf.append("     CCSQ A, YGXX B, YGXX C, DICT D, DICT E, YHXX F, YHXX G ");
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
	 * @Description: 获取出差申请列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1020190>
	 * @author ztz
	 * @date 2015年3月9日 上午10:24:20
	 */
	public List<Pojo1020190> getDataList(Pojo1020190 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1020190> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.CCSQ_SQID, ");//申请ID
			strbuf.append("     A.CCSQ_JTGJ, ");//交通工具
			strbuf.append("     D.DICT_ZDMC AS JTGJ, ");//交通工具
			strbuf.append("     A.CCSQ_SQR, ");//申请人
			strbuf.append("     CONCAT(F.YHXX_YHID, '-', B.YGXX_XM) AS SQRXM, ");//申请人姓名
			strbuf.append("     A.CCSQ_SQSJ, ");//申请时间
			strbuf.append("     A.CCSQ_KSSJ, ");//开始时间
			strbuf.append("     A.CCSQ_JSSJ, ");//结束时间
			strbuf.append("     A.CCSQ_CCTS, ");//出差天数
			strbuf.append("     A.CCSQ_SPZT, ");//状态
			strbuf.append("     E.DICT_ZDMC AS SPZT, ");//状态
			strbuf.append("     A.CCSQ_SFWJ, ");//是否完结
			strbuf.append("     CASE WHEN A.CCSQ_SFWJ = '0' THEN '未完结' WHEN A.CCSQ_SFWJ = '1' THEN '已完结' ELSE '' END AS SFWJ, ");//是否完结
			strbuf.append("     A.CCSQ_XYSP, ");//下一审批人
			strbuf.append("     CONCAT(G.YHXX_YHID, '-', C.YGXX_XM) AS XYSPXM, ");//下一审批人姓名
			strbuf.append("     A.CCSQ_CCMDD, ");//出差目的地
			strbuf.append("     A.CCSQ_CCYY, ");//加班原因
			strbuf.append("     A.CCSQ_CJR, ");//创建人
			strbuf.append("     A.CCSQ_CJSJ, ");//创建时间
			strbuf.append("     A.CCSQ_GXR, ");//更新人
			strbuf.append("     A.CCSQ_GXSJ, ");//更新时间
			strbuf.append("     A.CCSQ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     CCSQ A, YGXX B, YGXX C, DICT D, DICT E, YHXX F, YHXX G ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.CCSQ_SQR, A.CCSQ_SQSJ DESC");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1020190>> rs = new BeanListHandler<Pojo1020190>(
					Pojo1020190.class);
			
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
	 * @date 2015年3月9日 上午10:24:33
	 */
	private String searchSql(Pojo1020190 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.CCSQ_SQR = B.YGXX_YGID ");
			strbuf.append(" AND A.CCSQ_XYSP = C.YGXX_YGID ");
			strbuf.append(" AND A.CCSQ_JTGJ = D.DICT_ZDDM ");
			strbuf.append(" AND A.CCSQ_SPZT = E.DICT_ZDDM ");
			strbuf.append(" AND B.YGXX_YGID = F.YHXX_YGID ");
			strbuf.append(" AND C.YGXX_YGID = G.YHXX_YGID ");
			strbuf.append(" AND A.CCSQ_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND E.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_ZDLX = 'JTGJ'");
			strbuf.append(" AND E.DICT_ZDLX = 'SPZT'");
			/* 操作类型 */
			if ("0".equals(beanIn.getCZLX())) {
				strbuf.append(" AND A.CCSQ_XYSP = '").append(beanIn.getCCSQ_XYSP()).append("'");//下一审批人
				strbuf.append(" AND A.CCSQ_SFWJ = '0'");//是否完结
				strbuf.append(" AND A.CCSQ_SPZT IN (0, 2)");//审批状态
			} else {
				strbuf.append(" AND A.CCSQ_SQID IN (SELECT CCMX_SQID FROM CCMX WHERE CCMX_CZR = '").append(beanIn.getCCSQ_XYSP()).append("' AND CCMX_CZZT > '0')");//审批人
			}
			/* 审批状态 */
			if (MyStringUtils.isNotBlank(beanIn.getCCSQ_SPZT())) {
				if (!"000".equals(beanIn.getCCSQ_SPZT())) {
					strbuf.append(" AND A.CCSQ_SPZT = '").append(beanIn.getCCSQ_SPZT()).append("'");
				}
			}
			/* 开始时间 */
			if (MyStringUtils.isNotBlank(beanIn.getCCSQ_KSSJ())) {
				strbuf.append(" AND A.CCSQ_KSSJ >= '").append(beanIn.getCCSQ_KSSJ()).append("'");
			}
			/* 结束时间 */
			if (MyStringUtils.isNotBlank(beanIn.getCCSQ_JSSJ())) {
				strbuf.append(" AND A.CCSQ_JSSJ <= '").append(beanIn.getCCSQ_JSSJ()).append("'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: initData
	 * @Description: 获取出差申请详情数据
	 * @param dataId
	 * @return
	 * @throws Exception
	 * @return Pojo1020190
	 * @author ztz
	 * @date 2015年3月9日 上午10:24:42
	 */
	public Pojo1020190 initData(String dataId) throws Exception {
		Pojo1020190 infoBean = new Pojo1020190();
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.CCSQ_SQID, ");//申请ID
			strbuf.append("     A.CCSQ_SQR, ");//申请人
			strbuf.append("     D.YGXX_XM AS SQRXM, ");//申请人姓名
			strbuf.append("     G.BMXX_BMID AS BMID, ");//部门ID
			strbuf.append("     G.BMXX_BMMC AS BMMC, ");//部门名称
			strbuf.append("     A.CCSQ_SQSJ, ");//申请时间
			strbuf.append("     A.CCSQ_CCTS, ");//出差天数
			strbuf.append("     A.CCSQ_KSSJ, ");//开始时间
			strbuf.append("     A.CCSQ_JSSJ, ");//结束时间
			strbuf.append("     A.CCSQ_SPZT, ");//状态
			strbuf.append("     C.DICT_ZDMC AS SPZT, ");//状态
			strbuf.append("     A.CCSQ_JTGJ, ");//交通工具
			strbuf.append("     B.DICT_ZDMC AS JTGJ, ");//交通工具
			strbuf.append("     A.CCSQ_XYSP, ");//下一审批人
			strbuf.append("     E.YGXX_XM AS XYSPXM, ");//下一审批人姓名
			strbuf.append("     A.CCSQ_CCMDD, ");//出差目的地
			strbuf.append("     A.CCSQ_CCYY ");//出差原因
			strbuf.append(" FROM ");
			strbuf.append("     CCSQ A, DICT B, DICT C, YGXX D, YGXX E, YHJS F, BMXX G, YHXX H ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.CCSQ_JTGJ = B.DICT_ZDDM ");
			strbuf.append(" AND A.CCSQ_SPZT = C.DICT_ZDDM ");
			strbuf.append(" AND A.CCSQ_SQR = D.YGXX_YGID ");
			strbuf.append(" AND A.CCSQ_XYSP = E.YGXX_YGID ");
			strbuf.append(" AND D.YGXX_YGID = H.YHXX_YGID ");
			strbuf.append(" AND H.YHXX_JSID = F.YHJS_JSID ");
			strbuf.append(" AND F.YHJS_BMID = G.BMXX_BMID ");
			strbuf.append(" AND A.CCSQ_SCBZ = '0'");
			strbuf.append(" AND B.DICT_SCBZ = '0'");
			strbuf.append(" AND C.DICT_SCBZ = '0'");
			strbuf.append(" AND D.YGXX_SCBZ = '0'");
			strbuf.append(" AND E.YGXX_SCBZ = '0'");
			strbuf.append(" AND F.YHJS_SCBZ = '0'");
			strbuf.append(" AND G.BMXX_SCBZ = '0'");
			strbuf.append(" AND B.DICT_ZDLX = 'JTGJ'");
			strbuf.append(" AND C.DICT_ZDLX = 'SPZT'");
			strbuf.append(" AND A.CCSQ_SQID = '").append(dataId).append("'");
			
			ResultSetHandler<Pojo1020190> rs = new BeanHandler<Pojo1020190>(
					Pojo1020190.class);
			
			infoBean = db.queryForBeanHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return infoBean;
	}
	/**
	 * 
	 * @FunctionName: saveData
	 * @Description: 保存审批（更新加班申请数据，新增加班申请记录）
	 * @param infoBean
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年3月9日 下午2:52:40
	 */
	public boolean saveData(Pojo1020190 infoBean) throws Exception {
		boolean result = false;
		StringBuffer strbuf_CCSQ = new StringBuffer();
		StringBuffer strbuf_CCMX = new StringBuffer();
		int count_CCSQ = 0;
		int count_CCMX = 0;
		String ccmxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		
		try {
			db.openConnection();
			db.beginTran();
			/* 更新CCSQ表数据Start */
			strbuf_CCSQ.append(" UPDATE ");
			strbuf_CCSQ.append("     CCSQ ");
			strbuf_CCSQ.append(" SET ");
			
			if ("5".equals(infoBean.getCCSQ_SPZT())) {//驳回完结
				strbuf_CCSQ.append("     CCSQ_SPZT='4',");//状态
			} else {
				strbuf_CCSQ.append("     CCSQ_SPZT='").append(infoBean.getCCSQ_SPZT()).append("',");//状态
			}
			if ("3".equals(infoBean.getCCSQ_SPZT()) || "5".equals(infoBean.getCCSQ_SPZT())) {//批准完结/驳回完结
				strbuf_CCSQ.append("     CCSQ_SFWJ='1',");//已完结
			}
			if ("2".equals(infoBean.getCCSQ_SPZT())) {//流转
				if (MyStringUtils.isNotBlank(infoBean.getCCSQ_XYSP())) {
					if (!"000".equals(infoBean.getCCSQ_XYSP())) {
						strbuf_CCSQ.append("     CCSQ_XYSP='").append(infoBean.getCCSQ_XYSP()).append("',");//下一审批人
					}
				}
			}
			strbuf_CCSQ.append("     CCSQ_GXR='").append(infoBean.getCCSQ_GXR()).append("',");//更新人
			strbuf_CCSQ.append("     CCSQ_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf_CCSQ.append(" WHERE ");
			strbuf_CCSQ.append("     CCSQ_SQID='").append(infoBean.getCCSQ_SQID()).append("'");//申请ID
			count_CCSQ = db.executeSQL(strbuf_CCSQ);
			/* 更新CCSQ表数据End */
			/* 向CCMX表插入数据Start */
			strbuf_CCMX.append(" INSERT INTO ");
			strbuf_CCMX.append("     CCMX ");
			strbuf_CCMX.append(" ( ");
			strbuf_CCMX.append("     CCMX_MXID, ");//出差明细ID
			strbuf_CCMX.append("     CCMX_SQID, ");//申请ID
			strbuf_CCMX.append("     CCMX_CZZT, ");//操作状态
			strbuf_CCMX.append("     CCMX_CZR, ");//操作人
			strbuf_CCMX.append("     CCMX_CZSJ, ");//操作时间
			strbuf_CCMX.append("     CCMX_CZNR, ");//操作内容
			strbuf_CCMX.append("     CCMX_XYCZR, ");//下一操作人
			strbuf_CCMX.append("     CCMX_CJR, ");//创建人
			strbuf_CCMX.append("     CCMX_CJSJ, ");//创建时间
			strbuf_CCMX.append("     CCMX_GXR, ");//更新人
			strbuf_CCMX.append("     CCMX_GXSJ, ");//更新时间
			strbuf_CCMX.append("     CCMX_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_CCMX.append(" ) ");
			strbuf_CCMX.append(" VALUES ");
			strbuf_CCMX.append(" ( ");
			strbuf_CCMX.append("     '"+ccmxId+"', ");//加班明细ID
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_SQID()+"', ");//申请ID
			if ("5".equals(infoBean.getCCSQ_SPZT())) {//驳回完结
				strbuf_CCMX.append("     '4', ");//操作状态
			} else {
				strbuf_CCMX.append("     '"+infoBean.getCCSQ_SPZT()+"', ");//操作状态
			}
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_SQR()+"', ");//操作人
			strbuf_CCMX.append("     DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'), ");//操作时间
			strbuf_CCMX.append("     '"+infoBean.getCCMX_CZNR()+"', ");//操作内容
			if (MyStringUtils.isNotBlank(infoBean.getCCSQ_XYSP())) {
				if (!"000".equals(infoBean.getCCSQ_XYSP())) {
					strbuf_CCMX.append("     '"+infoBean.getCCSQ_XYSP()+"', ");//下一操作人
				} else {
					strbuf_CCMX.append("     '', ");//下一操作人
				}
			}
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_GXR()+"', ");//创建人
			strbuf_CCMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_GXR()+"', ");//更新人
			strbuf_CCMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_CCMX.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_CCMX.append(" ) ");
			count_CCMX = db.executeSQL(strbuf_CCMX);
			/* 向CCMX表插入数据End */
			if (count_CCSQ > 0 && count_CCMX > 0 && count_CCSQ == count_CCMX) {
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