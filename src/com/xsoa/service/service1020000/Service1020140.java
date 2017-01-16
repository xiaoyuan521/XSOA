package com.xsoa.service.service1020000;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020140;

public class Service1020140 extends BaseService {
	private DBManager db = null;

	public Service1020140() {
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
	 * @date 2015年3月3日 上午10:14:52
	 */
	public int getDataCount(Pojo1020140 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.JBSQ_SQID) ");//加班申请个数
			strbuf.append(" FROM ");
			strbuf.append("     JBSQ A, YGXX B, YGXX C, DICT D, DICT E, YHXX F ");
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
	 * @return List<Pojo1020140>
	 * @author ztz
	 * @date 2015年3月3日 上午10:15:18
	 */
	public List<Pojo1020140> getDataList(Pojo1020140 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1020140> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JBSQ_SQID, ");//申请ID
			strbuf.append("     A.JBSQ_JBLX, ");//加班类型
			strbuf.append("     D.DICT_ZDMC AS JBLX, ");//加班类型
			strbuf.append("     A.JBSQ_SQR, ");//申请人
			strbuf.append("     B.YGXX_XM AS SQRXM, ");//申请人姓名
			strbuf.append("     A.JBSQ_SQSJ, ");//申请时间
			strbuf.append("     A.JBSQ_KSSJ, ");//开始时间
			strbuf.append("     A.JBSQ_JSSJ, ");//结束时间
			strbuf.append("     A.JBSQ_JBXSS, ");//加班小时数
			strbuf.append("     A.JBSQ_JBZT, ");//状态
			strbuf.append("     E.DICT_ZDMC AS JBZT, ");//状态
			strbuf.append("     A.JBSQ_SFWJ, ");//是否完结
			strbuf.append("     CASE WHEN A.JBSQ_SFWJ = '0' THEN '未完结' WHEN A.JBSQ_SFWJ = '1' THEN '已完结' ELSE '' END AS SFWJ, ");//是否完结
			strbuf.append("     A.JBSQ_XYSP, ");//下一审批人
			strbuf.append("     CONCAT(F.YHXX_YHID, '-', C.YGXX_XM) AS XYSPXM, ");//下一审批人姓名
			strbuf.append("     A.JBSQ_JBYY, ");//加班原因
			strbuf.append("     A.JBSQ_CJR, ");//创建人
			strbuf.append("     A.JBSQ_CJSJ, ");//创建时间
			strbuf.append("     A.JBSQ_GXR, ");//更新人
			strbuf.append("     A.JBSQ_GXSJ, ");//更新时间
			strbuf.append("     A.JBSQ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     JBSQ A, YGXX B, YGXX C, DICT D, DICT E, YHXX F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.JBSQ_SQR, A.JBSQ_SQSJ DESC");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1020140>> rs = new BeanListHandler<Pojo1020140>(
					Pojo1020140.class);
			
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
	 * @date 2015年3月3日 上午10:15:39
	 */
	private String searchSql(Pojo1020140 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.JBSQ_SQR = B.YGXX_YGID ");
			strbuf.append(" AND A.JBSQ_XYSP = C.YGXX_YGID ");
			strbuf.append(" AND A.JBSQ_JBLX = D.DICT_ZDDM ");
			strbuf.append(" AND A.JBSQ_JBZT = E.DICT_ZDDM ");
			strbuf.append(" AND C.YGXX_YGID = F.YHXX_YGID ");
			strbuf.append(" AND A.JBSQ_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND E.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_ZDLX = 'JBLX'");
			strbuf.append(" AND E.DICT_ZDLX = 'SPZT'");
			strbuf.append(" AND A.JBSQ_SQR = '").append(beanIn.getJBSQ_SQR()).append("'");
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
	 * @FunctionName: hourDiffer
	 * @Description: 字符串的日期格式的计算_计算两个日期之差的小时数
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 * @return int
	 * @author ztz
	 * @date 2015年3月6日 上午11:04:32
	 */
	public int hourDiffer(String startDate, String endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(startDate));
		long startTime = cal.getTimeInMillis();//起始日期毫秒数
		cal.setTime(sdf.parse(endDate));
		long endTime = cal.getTimeInMillis();//截止日期毫秒数
        long between_days = (endTime - startTime) / (1000*3600);//间隔小时数
        
        return Integer.parseInt(String.valueOf(between_days));
    }
	/**
	 * 
	 * @FunctionName: initData
	 * @Description: 获取加班申请初始化部分数据
	 * @param yhid
	 * @return
	 * @throws Exception
	 * @return Pojo1020140
	 * @author ztz
	 * @date 2015年3月4日 上午9:50:13
	 */
	public Pojo1020140 initData(String yhid) throws Exception {
		Pojo1020140 infoBean = new Pojo1020140();
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     B.YGXX_YGID AS JBSQ_SQR, ");//申请人
			strbuf.append("     B.YGXX_XM AS SQRXM, ");//申请人姓名
			strbuf.append("     D.BMXX_BMID AS BMID, ");//部门ID
			strbuf.append("     D.BMXX_BMMC AS BMMC, ");//部门名称
			strbuf.append("     DATE_FORMAT(now(), '%Y-%m-%d') AS JBSQ_SQSJ ");//申请时间
			strbuf.append(" FROM ");
			strbuf.append("     YHXX A, YGXX B, YHJS C, BMXX D ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YHXX_JSID = C.YHJS_JSID ");
			strbuf.append(" AND C.YHJS_BMID = D.BMXX_BMID ");
			strbuf.append(" AND A.YHXX_YGID = B.YGXX_YGID ");
			strbuf.append(" AND A.YHXX_SCBZ = '0'");
			strbuf.append(" AND B.YGXX_SCBZ = '0'");
			strbuf.append(" AND C.YHJS_SCBZ = '0'");
			strbuf.append(" AND D.BMXX_SCBZ = '0'");
			strbuf.append(" AND A.YHXX_YHID = '").append(yhid).append("'");
			
			ResultSetHandler<Pojo1020140> rs = new BeanHandler<Pojo1020140>(
					Pojo1020140.class);
			
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
	 * @FunctionName: insertData
	 * @Description: 新增加班申请信息
	 * @param infoBean
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年3月4日 上午9:57:45
	 */
	public boolean insertData(Pojo1020140 infoBean) throws Exception {
		boolean result = false;
		StringBuffer strbuf_JBSQ = new StringBuffer();
		StringBuffer strbuf_JBMX = new StringBuffer();
		int count_JBSQ = 0;
		int count_JBMX = 0;
		String jbsqId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		String jbmxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		
		try {
			db.openConnection();
			db.beginTran();
			/* 向JBSQ表插入数据Start */
			strbuf_JBSQ.append(" INSERT INTO ");
			strbuf_JBSQ.append("     JBSQ ");
			strbuf_JBSQ.append(" ( ");
			strbuf_JBSQ.append("     JBSQ_SQID, ");//申请ID
			strbuf_JBSQ.append("     JBSQ_JBLX, ");//加班类型
			strbuf_JBSQ.append("     JBSQ_SQR, ");//申请人
			strbuf_JBSQ.append("     JBSQ_SQSJ, ");//申请时间
			strbuf_JBSQ.append("     JBSQ_KSSJ, ");//开始时间
			strbuf_JBSQ.append("     JBSQ_JSSJ, ");//结束时间
			strbuf_JBSQ.append("     JBSQ_JBXSS, ");//加班小时数
			strbuf_JBSQ.append("     JBSQ_JBZT, ");//状态
			strbuf_JBSQ.append("     JBSQ_SFWJ, ");//是否完结
			strbuf_JBSQ.append("     JBSQ_XYSP, ");//下一审批人
			strbuf_JBSQ.append("     JBSQ_JBYY, ");//加班原因
			strbuf_JBSQ.append("     JBSQ_CJR, ");//创建人
			strbuf_JBSQ.append("     JBSQ_CJSJ, ");//创建时间
			strbuf_JBSQ.append("     JBSQ_GXR, ");//更新人
			strbuf_JBSQ.append("     JBSQ_GXSJ, ");//更新时间
			strbuf_JBSQ.append("     JBSQ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_JBSQ.append(" ) ");
			strbuf_JBSQ.append(" VALUES ");
			strbuf_JBSQ.append(" ( ");
			strbuf_JBSQ.append("     '"+jbsqId+"', ");//申请ID
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_JBLX()+"', ");//加班类型
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_SQR()+"', ");//申请人
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_SQSJ()+"', ");//申请时间
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_KSSJ()+"', ");//开始时间
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_JSSJ()+"', ");//结束时间
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_JBXSS()+"', ");//加班小时数
			strbuf_JBSQ.append("     '0', ");//状态
			strbuf_JBSQ.append("     '0', ");//是否完结
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_XYSP()+"', ");//下一审批人
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_JBYY()+"', ");//加班原因
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_CJR()+"', ");//创建人
			strbuf_JBSQ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_JBSQ.append("     '"+infoBean.getJBSQ_CJR()+"', ");//更新人
			strbuf_JBSQ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_JBSQ.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_JBSQ.append(" ) ");
			count_JBSQ = db.executeSQL(strbuf_JBSQ);
			/* 向JBSQ表插入数据End */
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
			strbuf_JBMX.append("     '"+jbsqId+"', ");//申请ID
			strbuf_JBMX.append("     '0', ");//操作状态
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_SQR()+"', ");//操作人
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_SQSJ()+"', ");//操作时间
			strbuf_JBMX.append("     '加班申请', ");//操作内容
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_XYSP()+"', ");//下一操作人
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_CJR()+"', ");//创建人
			strbuf_JBMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_CJR()+"', ");//更新人
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
	/**
	 * 
	 * @FunctionName: updateData
	 * @Description: 修改加班申请信息
	 * @param infoBean
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年3月4日 上午9:57:56
	 */
	public boolean updateData(Pojo1020140 infoBean) throws Exception {
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
			strbuf_JBSQ.append("     JBSQ_JBLX='").append(infoBean.getJBSQ_JBLX()).append("',");//加班类型
			strbuf_JBSQ.append("     JBSQ_SQR='").append(infoBean.getJBSQ_SQR()).append("',");//申请人
			strbuf_JBSQ.append("     JBSQ_SQSJ='").append(infoBean.getJBSQ_SQSJ()).append("',");//申请时间
			strbuf_JBSQ.append("     JBSQ_KSSJ='").append(infoBean.getJBSQ_KSSJ()).append("',");//开始时间
			strbuf_JBSQ.append("     JBSQ_JSSJ='").append(infoBean.getJBSQ_JSSJ()).append("',");//结束时间
			strbuf_JBSQ.append("     JBSQ_JBXSS='").append(infoBean.getJBSQ_JBXSS()).append("',");//加班小时数
			strbuf_JBSQ.append("     JBSQ_XYSP='").append(infoBean.getJBSQ_XYSP()).append("',");//下一审批人
			strbuf_JBSQ.append("     JBSQ_JBZT='0',");//状态
			strbuf_JBSQ.append("     JBSQ_JBYY='").append(infoBean.getJBSQ_JBYY()).append("',");//加班原因
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
			strbuf_JBMX.append("     '0', ");//操作状态
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_SQR()+"', ");//操作人
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_SQSJ()+"', ");//操作时间
			strbuf_JBMX.append("     '更新加班申请', ");//操作内容
			strbuf_JBMX.append("     '"+infoBean.getJBSQ_XYSP()+"', ");//下一操作人
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
	/**
	 * 
	 * @FunctionName: deleteData
	 * @Description: 删除加班申请信息
	 * @param infoBean
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年3月4日 上午9:58:31
	 */
	public boolean deleteData(Pojo1020140 infoBean) throws Exception {
		boolean result = false;
		StringBuffer strbuf_JBSQ = new StringBuffer();
		StringBuffer strbuf_JBMX = new StringBuffer();
		int count_JBSQ = 0;
		int count_JBMX = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 删除JBSQ表数据Start */
			strbuf_JBSQ.append(" UPDATE ");
			strbuf_JBSQ.append("     JBSQ ");
			strbuf_JBSQ.append(" SET ");
			strbuf_JBSQ.append("     JBSQ_GXR='").append(infoBean.getJBSQ_GXR()).append("',");//更新人
			strbuf_JBSQ.append("     JBSQ_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf_JBSQ.append("     JBSQ_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf_JBSQ.append(" WHERE ");
			strbuf_JBSQ.append("     JBSQ_SQID='").append(infoBean.getJBSQ_SQID()).append("'");//申请ID
			count_JBSQ = db.executeSQL(strbuf_JBSQ);
			/* 删除JBSQ表数据End */
			/* 删除JBMX表数据Start */
			strbuf_JBMX.append(" UPDATE ");
			strbuf_JBMX.append("     JBMX ");
			strbuf_JBMX.append(" SET ");
			strbuf_JBMX.append("     JBMX_GXR='").append(infoBean.getJBSQ_GXR()).append("',");//更新人
			strbuf_JBMX.append("     JBMX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf_JBMX.append("     JBMX_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf_JBMX.append(" WHERE ");
			strbuf_JBMX.append("     JBMX_SQID='").append(infoBean.getJBSQ_SQID()).append("'");//申请ID
			count_JBMX = db.executeSQL(strbuf_JBMX);
			/* 删除JBMX表数据End */
			if (count_JBSQ > 0 && count_JBMX > 0) {
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