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
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020150;

public class Service1020150 extends BaseService {
	private DBManager db = null;

	public Service1020150() {
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
	 * @date 2015年3月3日 上午10:14:52
	 */
	public int getDataCount(Pojo1020150 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.CCSQ_SQID) ");//出差申请个数
			strbuf.append(" FROM ");
			strbuf.append("     CCSQ A, YGXX B, YGXX C, DICT D, DICT E, YHXX F ");
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
	 * @return List<Pojo1020150>
	 * @author ztz
	 * @date 2015年3月3日 上午10:15:18
	 */
	public List<Pojo1020150> getDataList(Pojo1020150 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1020150> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.CCSQ_SQID, ");//申请ID
			strbuf.append("     A.CCSQ_JTGJ, ");//交通工具
			strbuf.append("     D.DICT_ZDMC AS JTGJ, ");//交通工具
			strbuf.append("     A.CCSQ_SQR, ");//申请人
			strbuf.append("     B.YGXX_XM AS SQRXM, ");//申请人姓名
			strbuf.append("     A.CCSQ_SQSJ, ");//申请时间
			strbuf.append("     A.CCSQ_KSSJ, ");//开始时间
			strbuf.append("     A.CCSQ_JSSJ, ");//结束时间
			strbuf.append("     A.CCSQ_CCTS, ");//出差天数
			strbuf.append("     A.CCSQ_SPZT, ");//状态
			strbuf.append("     E.DICT_ZDMC AS SPZT, ");//状态
			strbuf.append("     A.CCSQ_SFWJ, ");//是否完结
			strbuf.append("     CASE WHEN A.CCSQ_SFWJ = '0' THEN '未完结' WHEN A.CCSQ_SFWJ = '1' THEN '已完结' ELSE '' END AS SFWJ, ");//是否完结
			strbuf.append("     A.CCSQ_XYSP, ");//下一审批人
			strbuf.append("     CONCAT(F.YHXX_YHID, '-', C.YGXX_XM) AS XYSPXM, ");//下一审批人姓名
			strbuf.append("     A.CCSQ_CCMDD, ");//出差目的地
			strbuf.append("     A.CCSQ_CCYY, ");//加班原因
			strbuf.append("     A.CCSQ_CJR, ");//创建人
			strbuf.append("     A.CCSQ_CJSJ, ");//创建时间
			strbuf.append("     A.CCSQ_GXR, ");//更新人
			strbuf.append("     A.CCSQ_GXSJ, ");//更新时间
			strbuf.append("     A.CCSQ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     CCSQ A, YGXX B, YGXX C, DICT D, DICT E, YHXX F ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.CCSQ_SQR, A.CCSQ_SQSJ DESC");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1020150>> rs = new BeanListHandler<Pojo1020150>(
					Pojo1020150.class);
			
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
	private String searchSql(Pojo1020150 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.CCSQ_SQR = B.YGXX_YGID ");
			strbuf.append(" AND A.CCSQ_XYSP = C.YGXX_YGID ");
			strbuf.append(" AND A.CCSQ_JTGJ = D.DICT_ZDDM ");
			strbuf.append(" AND A.CCSQ_SPZT = E.DICT_ZDDM ");
			strbuf.append(" AND C.YGXX_YGID = F.YHXX_YGID ");
			strbuf.append(" AND A.CCSQ_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND E.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_ZDLX = 'JTGJ'");
			strbuf.append(" AND E.DICT_ZDLX = 'SPZT'");
			strbuf.append(" AND A.CCSQ_SQR = '").append(beanIn.getCCSQ_SQR()).append("'");
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
	 * @FunctionName: dayDiffer
	 * @Description: 字符串的日期格式的计算_计算两个日期之差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 * @return int
	 * @author ztz
	 * @date 2015年3月6日 上午11:04:32
	 */
	public int dayDiffer(String startDate, String endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(startDate));
		long startTime = cal.getTimeInMillis();//起始日期毫秒数
		cal.setTime(sdf.parse(endDate));
		long endTime = cal.getTimeInMillis();//截止日期毫秒数
        long between_days = (endTime - startTime) / (1000*3600*24);//间隔天数
        
        return Integer.parseInt(String.valueOf(between_days));
    }
	/**
	 * 
	 * @FunctionName: initData
	 * @Description: 获取出差申请初始化部分数据
	 * @param yhid
	 * @return
	 * @throws Exception
	 * @return Pojo1020150
	 * @author ztz
	 * @date 2015年3月4日 上午9:50:13
	 */
	public Pojo1020150 initData(String yhid) throws Exception {
		Pojo1020150 infoBean = new Pojo1020150();
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     B.YGXX_YGID AS CCSQ_SQR, ");//申请人
			strbuf.append("     B.YGXX_XM AS SQRXM, ");//申请人姓名
			strbuf.append("     D.BMXX_BMID AS BMID, ");//部门ID
			strbuf.append("     D.BMXX_BMMC AS BMMC, ");//部门名称
			strbuf.append("     DATE_FORMAT(now(), '%Y-%m-%d') AS CCSQ_SQSJ ");//申请时间
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
			
			ResultSetHandler<Pojo1020150> rs = new BeanHandler<Pojo1020150>(
					Pojo1020150.class);
			
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
	 * @Description: 新增出差申请信息
	 * @param infoBean
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年3月4日 上午9:57:45
	 */
	public boolean insertData(Pojo1020150 infoBean) throws Exception {
		boolean result = false;
		StringBuffer strbuf_CCSQ = new StringBuffer();
		StringBuffer strbuf_CCMX = new StringBuffer();
		int count_CCSQ = 0;
		int count_CCMX = 0;
		String ccsqId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		String ccmxId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		
		try {
			db.openConnection();
			db.beginTran();
			/* 向CCSQ表插入数据Start */
			strbuf_CCSQ.append(" INSERT INTO ");
			strbuf_CCSQ.append("     CCSQ ");
			strbuf_CCSQ.append(" ( ");
			strbuf_CCSQ.append("     CCSQ_SQID, ");//申请ID
			strbuf_CCSQ.append("     CCSQ_JTGJ, ");//交通工具
			strbuf_CCSQ.append("     CCSQ_SQR, ");//申请人
			strbuf_CCSQ.append("     CCSQ_SQSJ, ");//申请时间
			strbuf_CCSQ.append("     CCSQ_KSSJ, ");//开始时间
			strbuf_CCSQ.append("     CCSQ_JSSJ, ");//结束时间
			strbuf_CCSQ.append("     CCSQ_CCTS, ");//出差天数
			strbuf_CCSQ.append("     CCSQ_SPZT, ");//状态
			strbuf_CCSQ.append("     CCSQ_SFWJ, ");//是否完结
			strbuf_CCSQ.append("     CCSQ_XYSP, ");//下一审批人
			strbuf_CCSQ.append("     CCSQ_CCMDD, ");//出差目的地
			strbuf_CCSQ.append("     CCSQ_CCYY, ");//出差原因
			strbuf_CCSQ.append("     CCSQ_CJR, ");//创建人
			strbuf_CCSQ.append("     CCSQ_CJSJ, ");//创建时间
			strbuf_CCSQ.append("     CCSQ_GXR, ");//更新人
			strbuf_CCSQ.append("     CCSQ_GXSJ, ");//更新时间
			strbuf_CCSQ.append("     CCSQ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_CCSQ.append(" ) ");
			strbuf_CCSQ.append(" VALUES ");
			strbuf_CCSQ.append(" ( ");
			strbuf_CCSQ.append("     '"+ccsqId+"', ");//申请ID
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_JTGJ()+"', ");//交通工具
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_SQR()+"', ");//申请人
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_SQSJ()+"', ");//申请时间
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_KSSJ()+"', ");//开始时间
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_JSSJ()+"', ");//结束时间
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_CCTS()+"', ");//出差天数
			strbuf_CCSQ.append("     '0', ");//状态
			strbuf_CCSQ.append("     '0', ");//是否完结
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_XYSP()+"', ");//下一审批人
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_CCMDD()+"', ");//出差目的地
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_CCYY()+"', ");//出差原因
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_CJR()+"', ");//创建人
			strbuf_CCSQ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_CCSQ.append("     '"+infoBean.getCCSQ_CJR()+"', ");//更新人
			strbuf_CCSQ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_CCSQ.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_CCSQ.append(" ) ");
			count_CCSQ = db.executeSQL(strbuf_CCSQ);
			/* 向CCSQ表插入数据End */
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
			strbuf_CCMX.append("     '"+ccsqId+"', ");//申请ID
			strbuf_CCMX.append("     '0', ");//操作状态
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_SQR()+"', ");//操作人
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_SQSJ()+"', ");//操作时间
			strbuf_CCMX.append("     '出差申请', ");//操作内容
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_XYSP()+"', ");//下一操作人
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_CJR()+"', ");//创建人
			strbuf_CCMX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_CJR()+"', ");//更新人
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
	/**
	 * 
	 * @FunctionName: updateData
	 * @Description: 修改出差申请信息
	 * @param infoBean
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年3月4日 上午9:57:56
	 */
	public boolean updateData(Pojo1020150 infoBean) throws Exception {
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
			strbuf_CCSQ.append("     CCSQ_JTGJ='").append(infoBean.getCCSQ_JTGJ()).append("',");//交通工具
			strbuf_CCSQ.append("     CCSQ_SQR='").append(infoBean.getCCSQ_SQR()).append("',");//申请人
			strbuf_CCSQ.append("     CCSQ_SQSJ='").append(infoBean.getCCSQ_SQSJ()).append("',");//申请时间
			strbuf_CCSQ.append("     CCSQ_KSSJ='").append(infoBean.getCCSQ_KSSJ()).append("',");//开始时间
			strbuf_CCSQ.append("     CCSQ_JSSJ='").append(infoBean.getCCSQ_JSSJ()).append("',");//结束时间
			strbuf_CCSQ.append("     CCSQ_CCTS='").append(infoBean.getCCSQ_CCTS()).append("',");//出差天数
			strbuf_CCSQ.append("     CCSQ_XYSP='").append(infoBean.getCCSQ_XYSP()).append("',");//下一审批人
			strbuf_CCSQ.append("     CCSQ_SPZT='0',");//状态
			strbuf_CCSQ.append("     CCSQ_CCMDD='").append(infoBean.getCCSQ_CCMDD()).append("',");//出差目的地
			strbuf_CCSQ.append("     CCSQ_CCYY='").append(infoBean.getCCSQ_CCYY()).append("',");//出差原因
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
			strbuf_CCMX.append("     '0', ");//操作状态
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_SQR()+"', ");//操作人
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_SQSJ()+"', ");//操作时间
			strbuf_CCMX.append("     '更新出差申请', ");//操作内容
			strbuf_CCMX.append("     '"+infoBean.getCCSQ_XYSP()+"', ");//下一操作人
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
	/**
	 * 
	 * @FunctionName: deleteData
	 * @Description: 删除出差申请信息
	 * @param infoBean
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年3月4日 上午9:58:31
	 */
	public boolean deleteData(Pojo1020150 infoBean) throws Exception {
		boolean result = false;
		StringBuffer strbuf_CCSQ = new StringBuffer();
		StringBuffer strbuf_CCMX = new StringBuffer();
		int count_CCSQ = 0;
		int count_CCMX = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 删除JBSQ表数据Start */
			strbuf_CCSQ.append(" UPDATE ");
			strbuf_CCSQ.append("     CCSQ ");
			strbuf_CCSQ.append(" SET ");
			strbuf_CCSQ.append("     CCSQ_GXR='").append(infoBean.getCCSQ_GXR()).append("',");//更新人
			strbuf_CCSQ.append("     CCSQ_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf_CCSQ.append("     CCSQ_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf_CCSQ.append(" WHERE ");
			strbuf_CCSQ.append("     CCSQ_SQID='").append(infoBean.getCCSQ_SQID()).append("'");//申请ID
			count_CCSQ = db.executeSQL(strbuf_CCSQ);
			/* 删除JBSQ表数据End */
			/* 删除JBMX表数据Start */
			strbuf_CCMX.append(" UPDATE ");
			strbuf_CCMX.append("     CCMX ");
			strbuf_CCMX.append(" SET ");
			strbuf_CCMX.append("     CCMX_GXR='").append(infoBean.getCCSQ_GXR()).append("',");//更新人
			strbuf_CCMX.append("     CCMX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf_CCMX.append("     CCMX_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf_CCMX.append(" WHERE ");
			strbuf_CCMX.append("     CCMX_SQID='").append(infoBean.getCCSQ_SQID()).append("'");//申请ID
			count_CCMX = db.executeSQL(strbuf_CCMX);
			/* 删除JBMX表数据End */
			if (count_CCSQ > 0 && count_CCMX > 0) {
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