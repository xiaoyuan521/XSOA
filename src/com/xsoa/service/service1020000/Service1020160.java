package com.xsoa.service.service1020000;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020160;
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020161;

public class Service1020160 extends BaseService {
	private DBManager db = null;

	public Service1020160() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取出差申请列表数据个数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-3-9
	 */
	public int getDataCount(Pojo1020160 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			format: 'YYYY/MM'format: 'YYYY/MM'format: 'YYYY/MM'format: 'YYYY/MM'format: 'YYYY/MM'laydate({
			    elem: '#test1',
			    format: 'YYYY/MM', // 分隔符可以任意定义，该例子表示只显示年月
			    festival: true, //显示节日
			    choose: function(datas){ //选择日期完毕的回调
			        alert('得到：'+datas);
			    }
			});laydate({
			    elem: '#test1',
			    format: 'YYYY/MM', // 分隔符可以任意定义，该例子表示只显示年月
			    festival: true, //显示节日
			    choose: function(datas){ //选择日期完毕的回调
			        alert('得到：'+datas);
			    }
			});laydate({
			    elem: '#test1',
			    format: 'YYYY/MM', // 分隔符可以任意定义，该例子表示只显示年月
			    festival: true, //显示节日
			    choose: function(datas){ //选择日期完毕的回调
			        alert('得到：'+datas);
			    }
			});laydate({
			    elem: '#test1',
			    format: 'YYYY/MM', // 分隔符可以任意定义，该例子表示只显示年月
			    festival: true, //显示节日
			    choose: function(datas){ //选择日期完毕的回调
			        alert('得到：'+datas);
			    }
			});laydate({
			    elem: '#test1',
			    format: 'YYYY/MM', // 分隔符可以任意定义，该例子表示只显示年月
			    festival: true, //显示节日
			    choose: function(datas){ //选择日期完毕的回调
			        alert('得到：'+datas);
			    }
			});			strbuf.append("     COUNT(A.CCSQ_SQID) ");//出差申请个数
			strbuf.append(" FROM ");
			strbuf.append("     CCSQ A, YGXX B, YGXX C, DICT D, DICT E ");
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
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1020160>
	 * @author czl
	 * @date 2015-3-9
	 */
	public List<Pojo1020160> getDataList(Pojo1020160 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1020160> result = null;
		
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
			strbuf.append("     C.YGXX_XM AS XYSPXM, ");//下一审批人姓名
			strbuf.append("     A.CCSQ_CCMDD, ");//出差目的地
			strbuf.append("     A.CCSQ_CCYY, ");//加班原因
			strbuf.append("     A.CCSQ_CJR, ");//创建人
			strbuf.append("     A.CCSQ_CJSJ, ");//创建时间
			strbuf.append("     A.CCSQ_GXR, ");//更新人
			strbuf.append("     A.CCSQ_GXSJ, ");//更新时间
			strbuf.append("     A.CCSQ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     CCSQ A, YGXX B, YGXX C, DICT D, DICT E ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.CCSQ_SQR, A.CCSQ_SQSJ DESC");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1020160>> rs = new BeanListHandler<Pojo1020160>(
					Pojo1020160.class);
			
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
	 * @date 2015-3-9
	 */
	private String searchSql(Pojo1020160 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.CCSQ_SQR = B.YGXX_YGID ");
			strbuf.append(" AND A.CCSQ_XYSP = C.YGXX_YGID ");
			strbuf.append(" AND A.CCSQ_JTGJ = D.DICT_ZDDM ");
			strbuf.append(" AND A.CCSQ_SPZT = E.DICT_ZDDM ");
			strbuf.append(" AND A.CCSQ_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND E.DICT_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.DICT_ZDLX = 'JTGJ'");
			strbuf.append(" AND E.DICT_ZDLX = 'SPZT'");
			/* 审批状态 */
			if (MyStringUtils.isNotBlank(beanIn.getCCSQ_SPZT())) {
				if (!"000".equals(beanIn.getCCSQ_SPZT())) {
					strbuf.append(" AND A.CCSQ_SPZT = '").append(beanIn.getCCSQ_SPZT()).append("'");
				}
			}
			/* 开始时间 */
			if (MyStringUtils.isNotBlank(beanIn.getCCSQ_KSSJ())) {
				strbuf.append(" AND A.CCSQ_KSSJ <= '").append(beanIn.getCCSQ_KSSJ()).append("'");
			}
			/* 结束时间 */
			if (MyStringUtils.isNotBlank(beanIn.getCCSQ_JSSJ())) {
				strbuf.append(" AND A.CCSQ_JSSJ >= '").append(beanIn.getCCSQ_JSSJ()).append("'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: getDataInfo
	 * @Description: 根据ID获取日常列表
	 * @param beanIn
	 * @throws Exception
	 * @return Pojo1020161
	 * @author czl
	 * @date 2015-3-9
	 */
	public Pojo1020161 getDataInfo(Pojo1020161 beanIn) throws Exception {
		Pojo1020161 result = null;
		
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.BCRC_BCRCID, ");//班次日程ID
			strbuf.append("     A.BCRC_DAY01, ");//01日
			strbuf.append("     A.BCRC_DAY02, ");//02日
			strbuf.append("     A.BCRC_DAY03, ");//03日
			strbuf.append("     A.BCRC_DAY04, ");//04日
			strbuf.append("     A.BCRC_DAY05, ");//05日
			strbuf.append("     A.BCRC_DAY06, ");//06日
			strbuf.append("     A.BCRC_DAY07, ");//07日
			strbuf.append("     A.BCRC_DAY08, ");//08日
			strbuf.append("     A.BCRC_DAY09, ");//09日
			strbuf.append("     A.BCRC_DAY10, ");//10日
			strbuf.append("     A.BCRC_DAY11, ");//11日
			strbuf.append("     A.BCRC_DAY12, ");//12日
			strbuf.append("     A.BCRC_DAY13, ");//13日
			strbuf.append("     A.BCRC_DAY14, ");//14日
			strbuf.append("     A.BCRC_DAY15, ");//15日
			strbuf.append("     A.BCRC_DAY16, ");//16日
			strbuf.append("     A.BCRC_DAY17, ");//17日
			strbuf.append("     A.BCRC_DAY18, ");//18日
			strbuf.append("     A.BCRC_DAY19, ");//19日
			strbuf.append("     A.BCRC_DAY20, ");//20日
			strbuf.append("     A.BCRC_DAY21, ");//21日
			strbuf.append("     A.BCRC_DAY22, ");//22日
			strbuf.append("     A.BCRC_DAY23, ");//23日
			strbuf.append("     A.BCRC_DAY24, ");//24日
			strbuf.append("     A.BCRC_DAY25, ");//25日
			strbuf.append("     A.BCRC_DAY26, ");//26日
			strbuf.append("     A.BCRC_DAY27, ");//27日
			strbuf.append("     A.BCRC_DAY28, ");//28日
			strbuf.append("     A.BCRC_DAY29, ");//29日
			strbuf.append("     A.BCRC_DAY30, ");//30日
			strbuf.append("     A.BCRC_DAY31,  ");//31日
			strbuf.append("     F01.SKSD_SDMC AS SDJC01, ");//01日
			strbuf.append("     F02.SKSD_SDMC AS SDJC02, ");//02日
			strbuf.append("     F03.SKSD_SDMC AS SDJC03, ");//03日
			strbuf.append("     F04.SKSD_SDMC AS SDJC04, ");//04日
			strbuf.append("     F05.SKSD_SDMC AS SDJC05, ");//05日
			strbuf.append("     F06.SKSD_SDMC AS SDJC06, ");//06日
			strbuf.append("     F07.SKSD_SDMC AS SDJC07, ");//07日
			strbuf.append("     F08.SKSD_SDMC AS SDJC08, ");//08日
			strbuf.append("     F09.SKSD_SDMC AS SDJC09, ");//09日
			strbuf.append("     F10.SKSD_SDMC AS SDJC10, ");//10日
			strbuf.append("     F11.SKSD_SDMC AS SDJC11, ");//11日
			strbuf.append("     F12.SKSD_SDMC AS SDJC12, ");//12日
			strbuf.append("     F13.SKSD_SDMC AS SDJC13, ");//13日
			strbuf.append("     F14.SKSD_SDMC AS SDJC14, ");//14日
			strbuf.append("     F15.SKSD_SDMC AS SDJC15, ");//15日
			strbuf.append("     F16.SKSD_SDMC AS SDJC16, ");//16日
			strbuf.append("     F17.SKSD_SDMC AS SDJC17, ");//17日
			strbuf.append("     F18.SKSD_SDMC AS SDJC18, ");//18日
			strbuf.append("     F19.SKSD_SDMC AS SDJC19, ");//19日
			strbuf.append("     F20.SKSD_SDMC AS SDJC20, ");//20日
			strbuf.append("     F21.SKSD_SDMC AS SDJC21, ");//21日
			strbuf.append("     F22.SKSD_SDMC AS SDJC22, ");//22日
			strbuf.append("     F23.SKSD_SDMC AS SDJC23, ");//23日
			strbuf.append("     F24.SKSD_SDMC AS SDJC24, ");//24日
			strbuf.append("     F25.SKSD_SDMC AS SDJC25, ");//25日
			strbuf.append("     F26.SKSD_SDMC AS SDJC26, ");//26日
			strbuf.append("     F27.SKSD_SDMC AS SDJC27, ");//27日
			strbuf.append("     F28.SKSD_SDMC AS SDJC28, ");//28日
			strbuf.append("     F29.SKSD_SDMC AS SDJC29, ");//29日
			strbuf.append("     F30.SKSD_SDMC AS SDJC30, ");//30日
			strbuf.append("     F31.SKSD_SDMC AS SDJC31  ");//31日

			strbuf.append("  FROM BCRC A ");
			strbuf.append("      ,SKSD F01,SKSD F02,SKSD F03,SKSD F04,SKSD F05,SKSD F06,SKSD F07 ");
			strbuf.append("      ,SKSD F08,SKSD F09,SKSD F10,SKSD F11,SKSD F12,SKSD F13,SKSD F14 ");
			strbuf.append("      ,SKSD F15,SKSD F16,SKSD F17,SKSD F18,SKSD F19,SKSD F20,SKSD F21 ");
			strbuf.append("      ,SKSD F22,SKSD F23,SKSD F24,SKSD F25,SKSD F26,SKSD F27,SKSD F28 ");
			strbuf.append("      ,SKSD F29,SKSD F30,SKSD F31");
			strbuf.append(" WHERE 1=1 ");
			
			strbuf.append("   AND A.BCRC_DAY01  =F01.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY02  =F02.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY03  =F03.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY04  =F04.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY05  =F05.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY06  =F06.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY07  =F07.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY08  =F08.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY09  =F09.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY10  =F10.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY11  =F11.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY12  =F12.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY13  =F13.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY14  =F14.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY15  =F15.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY16  =F16.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY17  =F17.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY18  =F18.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY19  =F19.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY20  =F20.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY21  =F21.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY22  =F22.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY23  =F23.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY24  =F24.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY25  =F25.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY26  =F26.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY27  =F27.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY28  =F28.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY29  =F29.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY30  =F30.SKSD_SDID(+)");
			strbuf.append("   AND A.BCRC_DAY31  =F31.SKSD_SDID(+)");
			
			strbuf.append("   AND A.BCRC_BCRCID  = '").append(beanIn.getBCRC_BCRCID()).append("'");
			ResultSetHandler<Pojo1020161> rs = new BeanHandler<Pojo1020161>(
					Pojo1020161.class);
			result = db.queryForBeanHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}	
}