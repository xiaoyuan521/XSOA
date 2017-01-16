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
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020180;

public class Service1020180 extends BaseService {
	private DBManager db = null;

	public Service1020180() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取加班申请列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年3月9日 上午10:23:11
	 */
	public int getDataCount(Pojo1020180 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.JBSQ_SQID) ");//加班申请个数
			strbuf.append(" FROM ");
			strbuf.append("     JBSQ A, YGXX B, YGXX C, DICT D, DICT E, YHXX F, YHXX G ");
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
	 * @Description: 获取加班申请列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1020180>
	 * @author ztz
	 * @date 2015年3月9日 上午10:23:24
	 */
	public List<Pojo1020180> getDataList(Pojo1020180 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1020180> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JBSQ_SQID, ");//申请ID
			strbuf.append("     A.JBSQ_JBLX, ");//加班类型
			strbuf.append("     D.DICT_ZDMC AS JBLX, ");//加班类型
			strbuf.append("     A.JBSQ_SQR, ");//申请人
			strbuf.append("     CONCAT(F.YHXX_YHID, '-', B.YGXX_XM) AS SQRXM, ");//申请人姓名
			strbuf.append("     A.JBSQ_SQSJ, ");//申请时间
			strbuf.append("     A.JBSQ_KSSJ, ");//开始时间
			strbuf.append("     A.JBSQ_JSSJ, ");//结束时间
			strbuf.append("     A.JBSQ_JBXSS, ");//加班小时数
			strbuf.append("     A.JBSQ_JBZT, ");//状态
			strbuf.append("     E.DICT_ZDMC AS JBZT, ");//状态
			strbuf.append("     A.JBSQ_SFWJ, ");//是否完结
			strbuf.append("     CASE WHEN A.JBSQ_SFWJ = '0' THEN '未完结' WHEN A.JBSQ_SFWJ = '1' THEN '已完结' ELSE '' END AS SFWJ, ");//是否完结
			strbuf.append("     A.JBSQ_XYSP, ");//下一审批人
			strbuf.append("     CONCAT(G.YHXX_YHID, '-', C.YGXX_XM) AS XYSPXM, ");//下一审批人姓名
			strbuf.append("     A.JBSQ_JBYY, ");//加班原因
			strbuf.append("     A.JBSQ_CJR, ");//创建人
			strbuf.append("     A.JBSQ_CJSJ, ");//创建时间
			strbuf.append("     A.JBSQ_GXR, ");//更新人
			strbuf.append("     A.JBSQ_GXSJ, ");//更新时间
			strbuf.append("     A.JBSQ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     JBSQ A, YGXX B, YGXX C, DICT D, DICT E, YHXX F, YHXX G ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.JBSQ_SQR, A.JBSQ_SQSJ DESC");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1020180>> rs = new BeanListHandler<Pojo1020180>(
					Pojo1020180.class);
			
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
	 * @date 2015年3月9日 上午10:23:38
	 */
	private String searchSql(Pojo1020180 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.JBSQ_SQR = B.YGXX_YGID ");
			strbuf.append(" AND A.JBSQ_XYSP = C.YGXX_YGID ");
			strbuf.append(" AND A.JBSQ_JBLX = D.DICT_ZDDM ");
			strbuf.append(" AND A.JBSQ_JBZT = E.DICT_ZDDM ");
			strbuf.append(" AND B.YGXX_YGID = F.YHXX_YGID ");
			strbuf.append(" AND C.YGXX_YGID = G.YHXX_YGID ");
			strbuf.append(" AND A.JBSQ_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND E.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_ZDLX = 'JBLX'");
			strbuf.append(" AND E.DICT_ZDLX = 'SPZT'");
			/* 操作类型 */
			if ("0".equals(beanIn.getCZLX())) {
				strbuf.append(" AND A.JBSQ_XYSP = '").append(beanIn.getJBSQ_XYSP()).append("'");//下一审批人
				strbuf.append(" AND A.JBSQ_SFWJ = '0'");//是否完结
				strbuf.append(" AND A.JBSQ_JBZT IN (0, 2)");//审批状态
			} else {
				strbuf.append(" AND A.JBSQ_SQID IN (SELECT JBMX_SQID FROM JBMX WHERE JBMX_CZR = '").append(beanIn.getJBSQ_XYSP()).append("' AND JBMX_CZZT > '0')");//审批人
			}
			/* 加班类型 */
			if (MyStringUtils.isNotBlank(beanIn.getJBSQ_JBLX())) {
				if (!"000".equals(beanIn.getJBSQ_JBLX())) {
					strbuf.append(" AND A.JBSQ_JBLX = '").append(beanIn.getJBSQ_JBLX()).append("'");
				}
			}
			/* 审批状态 */
			if (MyStringUtils.isNotBlank(beanIn.getJBSQ_JBZT())) {
				if (!"000".equals(beanIn.getJBSQ_JBZT())) {
					strbuf.append(" AND A.JBSQ_JBZT = '").append(beanIn.getJBSQ_JBZT()).append("'");
				}
			}
			/* 开始时间 */
			if (MyStringUtils.isNotBlank(beanIn.getJBSQ_KSSJ())) {
				strbuf.append(" AND LEFT(A.JBSQ_KSSJ, 10) >= '").append(beanIn.getJBSQ_KSSJ()).append("'");
			}
			/* 结束时间 */
			if (MyStringUtils.isNotBlank(beanIn.getJBSQ_JSSJ())) {
				strbuf.append(" AND LEFT(A.JBSQ_JSSJ, 10) <= '").append(beanIn.getJBSQ_JSSJ()).append("'");
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
	 * @Description: 获取加班申请详情数据
	 * @param dataId
	 * @return
	 * @throws Exception
	 * @return Pojo1020180
	 * @author ztz
	 * @date 2015年3月9日 上午10:23:48
	 */
	public Pojo1020180 initData(String dataId) throws Exception {
		Pojo1020180 infoBean = new Pojo1020180();
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JBSQ_SQID, ");//申请ID
			strbuf.append("     A.JBSQ_SQR, ");//申请人
			strbuf.append("     D.YGXX_XM AS SQRXM, ");//申请人姓名
			strbuf.append("     G.BMXX_BMID AS BMID, ");//部门ID
			strbuf.append("     G.BMXX_BMMC AS BMMC, ");//部门名称
			strbuf.append("     A.JBSQ_SQSJ, ");//申请时间
			strbuf.append("     A.JBSQ_JBXSS, ");//加班小时数
			strbuf.append("     A.JBSQ_KSSJ, ");//开始时间
			strbuf.append("     A.JBSQ_JSSJ, ");//结束时间
			strbuf.append("     A.JBSQ_JBZT, ");//状态
			strbuf.append("     C.DICT_ZDMC AS JBZT, ");//状态
			strbuf.append("     A.JBSQ_JBLX, ");//加班类型
			strbuf.append("     B.DICT_ZDMC AS JBLX, ");//加班类型
			strbuf.append("     A.JBSQ_XYSP, ");//下一审批人
			strbuf.append("     E.YGXX_XM AS XYSPXM, ");//下一审批人姓名
			strbuf.append("     A.JBSQ_JBYY ");//加班原因
			strbuf.append(" FROM ");
			strbuf.append("     JBSQ A, DICT B, DICT C, YGXX D, YGXX E, YHJS F, BMXX G, YHXX H ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.JBSQ_JBLX = B.DICT_ZDDM ");
			strbuf.append(" AND A.JBSQ_JBZT = C.DICT_ZDDM ");
			strbuf.append(" AND A.JBSQ_SQR = D.YGXX_YGID ");
			strbuf.append(" AND A.JBSQ_XYSP = E.YGXX_YGID ");
			strbuf.append(" AND D.YGXX_YGID = H.YHXX_YGID ");
			strbuf.append(" AND H.YHXX_JSID = F.YHJS_JSID ");
			strbuf.append(" AND F.YHJS_BMID = G.BMXX_BMID ");
			strbuf.append(" AND A.JBSQ_SCBZ = '0'");
			strbuf.append(" AND B.DICT_SCBZ = '0'");
			strbuf.append(" AND C.DICT_SCBZ = '0'");
			strbuf.append(" AND D.YGXX_SCBZ = '0'");
			strbuf.append(" AND E.YGXX_SCBZ = '0'");
			strbuf.append(" AND F.YHJS_SCBZ = '0'");
			strbuf.append(" AND G.BMXX_SCBZ = '0'");
			strbuf.append(" AND B.DICT_ZDLX = 'JBLX'");
			strbuf.append(" AND C.DICT_ZDLX = 'SPZT'");
			strbuf.append(" AND A.JBSQ_SQID = '").append(dataId).append("'");
			
			ResultSetHandler<Pojo1020180> rs = new BeanHandler<Pojo1020180>(
					Pojo1020180.class);
			
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
	 * @date 2015年3月9日 下午2:50:33
	 */
	public boolean saveData(Pojo1020180 infoBean) throws Exception {
		boolean result = false;
		StringBuffer strbuf_JBSQ = new StringBuffer();
		StringBuffer strbuf_JBMX = new StringBuffer();
		int count_JBSQ = 0;
		int count_JBMX = 0;
		String jbmxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		
		try {
			db.openConnection();
			db.beginTran();
			/* 更新JBSQ表数据Start */
			strbuf_JBSQ.append(" UPDATE ");
			strbuf_JBSQ.append("     JBSQ ");
			strbuf_JBSQ.append(" SET ");
			if ("5".equals(infoBean.getJBSQ_JBZT())) {//驳回完结
				strbuf_JBSQ.append("     JBSQ_JBZT='4',");//状态
			} else {
				strbuf_JBSQ.append("     JBSQ_JBZT='").append(infoBean.getJBSQ_JBZT()).append("',");//状态
			}
			if ("3".equals(infoBean.getJBSQ_JBZT()) || "5".equals(infoBean.getJBSQ_JBZT())) {//批准完结/驳回完结
				strbuf_JBSQ.append("     JBSQ_SFWJ='1',");//已完结
			}
			if ("2".equals(infoBean.getJBSQ_JBZT())) {//流转
				if (MyStringUtils.isNotBlank(infoBean.getJBSQ_XYSP())) {
					if (!"000".equals(infoBean.getJBSQ_XYSP())) {
						strbuf_JBSQ.append("     JBSQ_XYSP='").append(infoBean.getJBSQ_XYSP()).append("',");//下一审批人
					}
				}
			}
			strbuf_JBSQ.append("     JBSQ_GXR='").append(infoBean.getJBSQ_GXR()).append("',");//更新人
			strbuf_JBSQ.append("     JBSQ_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf_JBSQ.append(" WHERE ");
			strbuf_JBSQ.append("     JBSQ_SQID='").append(infoBean.getJBSQ_SQID()).append("'");//申请ID
			count_JBSQ = db.executeSQL(strbuf_JBSQ);
			/* 更新JBSQ表数据End */
			/* 向JBMX表插入数据Start */
			strbuf_JBMX.append(" INSERT INTO ");
			strbuf_JBMX.append("     JBMX ");
			strbuf_JBMX.append(" ( ");
			strbuf_JBMX.append("     JBMX_MXID, ");//加班明细ID
			strbuf_JBMX.append("     JBMX_SQID, ");//申请ID
			strbuf_JBMX.append("     JBMX_CZZT, ");//操作状态
			strbuf_JBMX.append("     JBMX_CZR, ");//操作人
			strbuf_JBMX.append("     JBMX_CZSJ, ");//操作时间
			strbuf_JBMX.append("     JBMX_CZNR, ");//操作内容
			strbuf_JBMX.append("     JBMX_XYCZR, ");//下一操作人
			strbuf_JBMX.append("     JBMX_CJR, ");//创建人
			strbuf_JBMX.append("     JBMX_CJSJ, ");//创建时间
			strbuf_JBMX.append("     JBMX_GXR, ");//更新人
			strbuf_JBMX.append("     JBMX_GXSJ, ");//更新时间
			strbuf_JBMX.append("     JBMX_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_JBMX.append(" ) ");
			strbuf_JBMX.append(" VALUES ");
			strbuf_JBMX.append(" ( ");
			strbuf_JBMX.append("     '"+jbmxId+"', ");//加班明细ID
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_SQID()+"', ");//申请ID
			if ("5".equals(infoBean.getJBSQ_JBZT())) {//驳回完结
				strbuf_JBMX.append("     '4', ");//操作状态
			} else {
				strbuf_JBMX.append("     '"+infoBean.getJBSQ_JBZT()+"', ");//操作状态
			}
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_SQR()+"', ");//操作人
			strbuf_JBMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//操作时间
			strbuf_JBMX.append("     '"+infoBean.getJBMX_CZNR()+"', ");//操作内容
			if (MyStringUtils.isNotBlank(infoBean.getJBSQ_XYSP())) {
				if (!"000".equals(infoBean.getJBSQ_XYSP())) {
					strbuf_JBMX.append("     '"+infoBean.getJBSQ_XYSP()+"', ");//下一操作人
				} else {
					strbuf_JBMX.append("     '', ");//下一操作人
				}
			}
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_GXR()+"', ");//创建人
			strbuf_JBMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_GXR()+"', ");//更新人
			strbuf_JBMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_JBMX.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_JBMX.append(" ) ");
			count_JBMX = db.executeSQL(strbuf_JBMX);
			/* 向JBMX表插入数据End */
			if (count_JBSQ > 0 && count_JBMX > 0 && count_JBSQ == count_JBMX) {
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