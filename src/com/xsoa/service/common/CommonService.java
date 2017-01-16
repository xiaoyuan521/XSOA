package com.xsoa.service.common;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.common.Pojo_SELECT_LIST;

/**
 * @ClassName: CommonService
 * @Package:com.blank.service.common
 * @Description: 共通Service
 * @author ljg
 * @date 2014年7月21日 上午8:33:24
 * @update
 */
public class CommonService {

	private DBManager db = null;

	public CommonService() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getRoleList
	 * @Description: 角色下拉列表信息
	 * @return
	 * @throws Exception
	 * @return List<SELECT_LIST>
	 * @author ljg
	 * @date 2014年7月21日 上午10:59:16
	 */
	public List<Pojo_SELECT_LIST> getYhjsList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.YHJS_JSID AS CODE,");
			sql.append("        A.YHJS_JSMC AS NAME");
			sql.append("   FROM YHJS A ");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);
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
	 * @FunctionName: getZdmcList
	 * @Description: 站点下拉列表信息
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return List<SELECT_LIST>
	 * @author ztz
	 * @date 2014年10月28日 下午5:50:56
	 */
	public List<Pojo_SELECT_LIST> getZdmcList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.PXWD_ZDID AS CODE,");
			sql.append("        A.PXWD_ZDMC AS NAME");
			sql.append("   FROM PXWD A ");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);

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
	 * @FunctionName: getDayListByNY
	 * @Description: 通过年月取得日下拉列表内容
	 * @param strYearMonth
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author
	 * @date 2014-12-19
	 * @author czl
	 */
	public List<Pojo_SELECT_LIST> getDayListByNY(String strYearMonth) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			strYearMonth = strYearMonth.replace("-", "");
			if(strYearMonth.length()!=6) return result;

			db.openConnection();


			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strYearMonth).append("','YYYYMM')+(ROWNUM-1),'DD') AS CODE, ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strYearMonth).append("','YYYYMM')+(ROWNUM-1),'DD') || '日' AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         DUAL ");
			strbuf.append(" CONNECT BY ROWNUM<=TO_NUMBER(TO_CHAR(LAST_DAY(TO_DATE('").append(strYearMonth).append("','YYYYMM')),'DD')) ");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);

			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getDayListBegin_End
	 * @Description: 取得不足一个月的日
	 * @param strMonth
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author
	 * @date 2014-12-23
	 * @author czl
	 */
	public List<Pojo_SELECT_LIST> getDayListBegin_End(String strbMonth,String streMonth) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			strbMonth = strbMonth.replace("-", "");
			streMonth = streMonth.replace("-", "");
			db.openConnection();


			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strbMonth).append("','YYYYMMDD')+(ROWNUM-1),'DD') AS CODE, ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strbMonth).append("','YYYYMMDD')+(ROWNUM-1),'DD') || '日' AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         DUAL ");
			strbuf.append(" CONNECT BY ROWNUM<='").append(Integer.parseInt(streMonth.substring(6, 8))-Integer.parseInt(strbMonth.substring(6, 8))+1).append("'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);

			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getDayListEnd
	 * @Description: 取得不足一个月的日
	 * @param strMonth
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author
	 * @date 2014-12-22
	 * @author czl
	 */
	public List<Pojo_SELECT_LIST> getDayListEnd(String strMonth) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			strMonth = strMonth.replace("-", "");

			db.openConnection();


			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strMonth.substring(0, 6)).append("','YYYYMM')+(ROWNUM-1),'DD') AS CODE, ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strMonth.substring(0, 6)).append("','YYYYMM')+(ROWNUM-1),'DD') || '日' AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         DUAL ");
			strbuf.append(" CONNECT BY ROWNUM<='").append(strMonth.substring(6, 8)).append("'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);

			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getDayListBegin
	 * @Description: 取得不足一个月的日
	 * @param strMonth
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author
	 * @date 2014-12-22
	 * @author czl
	 */
	public List<Pojo_SELECT_LIST> getDayListBegin(String strMonth) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			strMonth = strMonth.replace("-", "");
			db.openConnection();


			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strMonth).append("','YYYYMMDD')+(ROWNUM-1),'DD') AS CODE, ");
			strbuf.append("        TO_CHAR(TO_DATE('").append(strMonth).append("','YYYYMMDD')+(ROWNUM-1),'DD') || '日' AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("         DUAL ");
			strbuf.append(" CONNECT BY ROWNUM<=TO_NUMBER(TO_CHAR(LAST_DAY(TO_DATE('").append(strMonth.substring(0, 6)).append("','YYYYMM')),'DD')) +1-").append(strMonth.substring(6, 8));

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);

			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getBmmcList
	 * @Description: 部门名称下拉列表
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2015年1月30日 下午1:40:41
	 */
	public List<Pojo_SELECT_LIST> getBmmcList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.BMXX_BMID AS CODE,");
			strbuf.append("     A.BMXX_BMMC AS NAME");
			strbuf.append(" FROM ");
			strbuf.append("     BMXX A");
			strbuf.append(" WHERE ");
			strbuf.append("     A.BMXX_SCBZ = '0'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);

			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getBmjsList
	 * @Description: 部门-角色下拉列表
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2015年2月4日 下午3:14:49
	 */
	public List<Pojo_SELECT_LIST> getBmjsList(String bmId) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YHJS_JSID AS CODE,");
			strbuf.append("     A.YHJS_JSMC AS NAME");
			strbuf.append(" FROM ");
			strbuf.append("     YHJS A");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YHJS_SCBZ = '0'");
			strbuf.append(" AND A.YHJS_BMID = '").append(bmId).append("'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getBmygList
	 * @Description: 部门-员工下拉列表
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author czl
	 * @date 2015年3月12日 上午11:52:49
	 */
	public List<Pojo_SELECT_LIST> getBmygList(String bmId) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     B.YGXX_YGID AS CODE,");
			strbuf.append("     CONCAT(C.YHXX_YHID, '-', B.YGXX_XM) AS NAME");
			strbuf.append(" FROM ");
			strbuf.append("     YHJS A, YGXX B, YHXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YHJS_JSID = B.YGXX_JSID");
			strbuf.append(" AND B.YGXX_YGID = C.YHXX_YGID");
			strbuf.append(" AND A.YHJS_SCBZ = '0' AND B.YGXX_SCBZ = '0'");
			strbuf.append(" AND A.YHJS_BMID = '").append(bmId).append("'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getZwmcList
	 * @Description: 获取职务名称下拉列表
	 * @param jsId
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2015年2月10日 下午2:44:32
	 */
	public List<Pojo_SELECT_LIST> getZwmcList(String jsId, String flag) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("      A.ZWXX_ZWID AS CODE, ");
			strbuf.append("      A.ZWXX_ZWMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     ZWXX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.ZWXX_SCBZ = '0'");
			if ("part".equals(flag)) {
				strbuf.append(" AND A.ZWXX_JSID = '").append(jsId).append("'");
			}

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**
	 * @FunctionName: getDICTList
	 * @Description: 取得数据字典列表
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ljg
	 * @date 2015年2月25日 下午4:08:39
	 */
	public List<Pojo_SELECT_LIST> getDICTList(String flag) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("      A.DICT_ZDDM AS CODE, ");
			strbuf.append("      A.DICT_ZDMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     DICT A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.DICT_SCBZ = '0'");
			strbuf.append(" AND A.DICT_ZDLX = '").append(flag).append("'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**
	 * @FunctionName: getGLGNList
	 * @Description: 审批关联功能列表
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ljg
	 * @date 2015年2月9日 下午2:15:37
	 */
	public List<Pojo_SELECT_LIST> getGLGNList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("      A.LCGN_GNID AS CODE, ");
			strbuf.append("      A.LCGN_GNMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     LCGN A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.LCGN_SCBZ = '0'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getYgxmList
	 * @Description: 获取员工姓名下拉列表
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2015年2月10日 下午2:46:04
	 */
	public List<Pojo_SELECT_LIST> getYgxmList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("      A.YGXX_YGID AS CODE, ");
			strbuf.append("      CONCAT(B.YHXX_YHID, '-', A.YGXX_XM) AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     YGXX A, YHXX B ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGXX_YGID = B.YHXX_YGID ");
			strbuf.append(" AND A.YGXX_SCBZ = '0'");
			strbuf.append(" AND B.YHXX_SCBZ = '0'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getKhmcList
	 * @Description: 获取客户名称下拉列表
	 * @param yhid
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2015年2月26日 上午10:27:13
	 * @update ztz at 2015年3月23日 下午2:08:45
	 */
	public List<Pojo_SELECT_LIST> getKhmcList(String yhid) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("      A.KHXX_KHID AS CODE, ");
			strbuf.append("      A.KHXX_KHMC AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     KHXX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.KHXX_SCBZ = '0'");
			strbuf.append(" AND A.KHXX_CJR = '").append(yhid).append("'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getNyList
	 * @Description: 年月下拉列表信息(当前月上下5个月)
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2015年2月26日 下午2:28:41
	 */
	public List<Pojo_SELECT_LIST> getNyList() throws Exception {
		List<Pojo_SELECT_LIST> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT TO_CHAR(ADD_MONTHS(ADD_MONTHS(sysdate,-5),ROWNUM-1 ),'YYYY-MM') AS CODE,");
			sql.append("        TO_CHAR(ADD_MONTHS(ADD_MONTHS(sysdate,-5),ROWNUM-1 ),'YYYY-MM') AS NAME");
			sql.append("   FROM DUAL");
			sql.append("   CONNECT BY ROWNUM<=months_between (ADD_MONTHS(sysdate,5),ADD_MONTHS(sysdate,-5))+1");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(sql, rs);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**
	 * @FunctionName: getSPRList
	 * @Description: 取得审批人列表
	 *               员工----则审批人为最小的审批级别
	 *               管理级----则审批人为大于当前用户级别后的最小审批级别
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ljg
	 * @date 2015年2月28日 上午10:41:40
	 */
	public List<Pojo_SELECT_LIST> getSPRList(String strLCXX_GLGN,String strCurrYGID,String strSQID) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT DISTINCT");
			strbuf.append("      B.LCMX_SPID AS CODE, ");//审批人
			strbuf.append("      CONCAT(E.ZWXX_ZWMC,'-',C.YGXX_XM) AS NAME ");   //审批人姓名
			//strbuf.append("		 A.LCXX_LCID,");//流程ID
			//strbuf.append("		 A.LCXX_LCMC,");//流程名称
			//strbuf.append("		 B.LCMX_SPJB,");//审批级别
			//strbuf.append("		 D.YGZW_ZWID,");//职务ID
			//strbuf.append("		 E.ZWXX_ZWMC,");//职务名称
			//strbuf.append("		 A.LCXX_SQDM"); //申请代码
			strbuf.append(" FROM ");
			strbuf.append("     LCXX AS A INNER JOIN (SELECT X.* FROM LCMX X INNER JOIN (");
			strbuf.append("     					   SELECT A.LCMX_LCID,MIN(A.LCMX_SPJB) AS LCMX_SPJB FROM	LCMX A ");//下一审批
			if(!strCurrYGID.equals(strSQID)){//审批
				strbuf.append("                        INNER JOIN (SELECT LCMX_LCID,LCMX_SPJB FROM LCMX WHERE LCMX_SPID = '").append(strCurrYGID).append("') B ON A.LCMX_LCID=B.LCMX_LCID AND A.LCMX_SPJB>B.LCMX_SPJB");//大于当前用户的级别
			}
			strbuf.append("     					   GROUP BY A.LCMX_LCID) Y ON X.LCMX_LCID = Y.LCMX_LCID AND X.LCMX_SPJB = Y.LCMX_SPJB ");
			strbuf.append("           ) B ON A.LCXX_LCID = B.LCMX_LCID");
			strbuf.append("     INNER JOIN YGXX AS C ON B.LCMX_SPID = C.YGXX_YGID");
			strbuf.append("     INNER JOIN YGZW AS D ON C.YGXX_YGID = D.YGZW_YGID");
			strbuf.append("     INNER JOIN ZWXX AS E ON D.YGZW_ZWID = E.ZWXX_ZWID");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.LCXX_SCBZ = '0'");
			strbuf.append(" AND FIND_IN_SET(A.LCXX_SQDM,");
			strbuf.append(" 			(SELECT GROUP_CONCAT(A.YHXX_YGID,',',B.YGZW_ZWID,',',C.ZWXX_JSID,',',C.ZWXX_BMID) AS SQDM");
			strbuf.append(" 			  FROM YHXX AS A");
			strbuf.append(" 				   LEFT JOIN YGZW AS B ON A.YHXX_YGID = B.YGZW_YGID");
			strbuf.append(" 				   LEFT JOIN ZWXX AS C ON B.YGZW_ZWID = C.ZWXX_ZWID");
			strbuf.append(" 			 WHERE A.YHXX_YGID = '").append(strSQID).append("'");
			strbuf.append(" 			))");
			strbuf.append(" AND A.LCXX_GLGN = '").append(strLCXX_GLGN).append("'");
			strbuf.append(" AND B.LCMX_SPID != '").append(strCurrYGID).append("'");


			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**
	 * @FunctionName: getSPR_KFZJ
	 * @Description: 根据当前审批人取得是否可以终结
	 * @param strLCXX_GLGN
	 * @param strCurrYGID
	 * @param strSQID
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ljg
	 * @date 2015年3月7日 下午4:58:11
	 */
	public Integer getSPR_KFZJ(String strLCXX_GLGN,String strCurrYGID,String strSQID) throws Exception {
		Integer result = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("		 COUNT(B.LCMX_KFZJ)");//可否终结
			//strbuf.append("      B.LCMX_SPID AS CODE, ");//审批人
			//strbuf.append("      CONCAT(E.ZWXX_ZWMC,'-',C.YGXX_XM) AS NAME ");   //审批人姓名
			//strbuf.append("		 A.LCXX_LCID,");//流程ID
			//strbuf.append("		 A.LCXX_LCMC,");//流程名称
			//strbuf.append("		 B.LCMX_SPJB,");//审批级别
			//strbuf.append("		 D.YGZW_ZWID,");//职务ID
			//strbuf.append("		 E.ZWXX_ZWMC,");//职务名称
			//strbuf.append("		 A.LCXX_SQDM"); //申请代码
			strbuf.append(" FROM ");
			strbuf.append("     LCXX AS A ");
			strbuf.append("     INNER JOIN LCMX AS B ON A.LCXX_LCID = B.LCMX_LCID");
			strbuf.append("     AND A.LCXX_GLGN = '").append(strLCXX_GLGN).append("'");//关联功能
			strbuf.append("     AND B.LCMX_SPID = '").append(strCurrYGID).append("'");//当前审批人所在的流程配置
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.LCXX_SCBZ = '0'");
			strbuf.append(" AND B.LCMX_KFZJ = '1'");
			strbuf.append(" AND FIND_IN_SET(A.LCXX_SQDM,");
			strbuf.append(" 			(SELECT GROUP_CONCAT(A.YHXX_YGID,',',B.YGZW_ZWID,',',C.ZWXX_JSID,',',C.ZWXX_BMID) AS SQDM");
			strbuf.append(" 			  FROM YHXX AS A");
			strbuf.append(" 				   LEFT JOIN YGZW AS B ON A.YHXX_YGID = B.YGZW_YGID");
			strbuf.append(" 				   LEFT JOIN ZWXX AS C ON B.YGZW_ZWID = C.ZWXX_ZWID");
			strbuf.append(" 			 WHERE A.YHXX_YGID = '").append(strSQID).append("'");
			strbuf.append(" 			))");

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
	 * @FunctionName: getSjzgList
	 * @Description: 获取上级主管下拉列表
	 * @param ygid
	 * @param zwlx
	 * @param bmid
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2015年3月18日 下午2:43:49
	 */
	public List<Pojo_SELECT_LIST> getSjzgList(String ygid, String bmid, String zwdj) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YGXX_YGID AS CODE, ");
			strbuf.append("     CONCAT(C.ZWXX_ZWMC, '-', A.YGXX_XM) AS NAME ");
			strbuf.append(" FROM ");
			strbuf.append("     YGXX A, YGZW B, ZWXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGXX_YGID = B.YGZW_YGID ");
			strbuf.append(" AND B.YGZW_ZWID = C.ZWXX_ZWID ");
			strbuf.append(" AND A.YGXX_SCBZ = '0'");
			strbuf.append(" AND B.YGZW_SCBZ = '0'");
			strbuf.append(" AND C.ZWXX_SCBZ = '0'");
			strbuf.append(" AND A.YGXX_YGID <> '").append(ygid).append("'");
			strbuf.append(" AND C.ZWXX_BMID = '").append(bmid).append("'");
			strbuf.append(" AND B.YGZW_ZWDJ IN (");
			strbuf.append(" SELECT ");
			strbuf.append("     MIN(T.YGZW_ZWDJ)");
			strbuf.append(" FROM ");
			strbuf.append("     YGZW T, ZWXX V ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND T.YGZW_ZWID = V.ZWXX_ZWID ");
			strbuf.append(" AND T.YGZW_ZWDJ > '").append(zwdj).append("'");
			strbuf.append(" AND V.ZWXX_BMID = '").append(bmid).append("'");
			strbuf.append(" AND T.YGZW_YGID <> '").append(ygid).append("'");
			strbuf.append(")");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);
			result = db.queryForBeanListHandler(strbuf, rs);
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
	 * @FunctionName: getYgpjBmmcList
	 * @Description: 获取员工评价-部门名称下拉列表
	 * @param ygid
	 * @return
	 * @throws Exception
	 * @return List<Pojo_SELECT_LIST>
	 * @author ztz
	 * @date 2015年3月19日 下午3:03:41
	 */
	public List<Pojo_SELECT_LIST> getYgpjBmmcList(String ygid) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     C.BMXX_BMID AS CODE,");
			strbuf.append("     C.BMXX_BMMC AS NAME");
			strbuf.append(" FROM ");
			strbuf.append("     YGZW A, ZWXX B, BMXX C");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGZW_ZWID = B.ZWXX_ZWID ");
			strbuf.append(" AND B.ZWXX_BMID = C.BMXX_BMID ");
			strbuf.append(" AND A.YGZW_SCBZ = '0'");
			strbuf.append(" AND B.ZWXX_SCBZ = '0'");
			strbuf.append(" AND C.BMXX_SCBZ = '0'");
			strbuf.append(" AND A.YGZW_YGID = '").append(ygid).append("'");
			strbuf.append(" AND A.YGZW_ZWDJ > '0'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);

			result = db.queryForBeanListHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	public List<Pojo_SELECT_LIST> getYgpjBmygList(String bmid, String ygid) throws Exception {
		List<Pojo_SELECT_LIST> result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     T.YGXX_YGID AS CODE,");
			strbuf.append("     CONCAT(V.YHXX_YHID, '-', T.YGXX_XM) AS NAME");
			strbuf.append(" FROM ");
			strbuf.append("     YGXX T, YHXX V, YGZW A, ZWXX B, BMXX C");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND T.YGXX_YGID = A.YGZW_YGID ");
			strbuf.append(" AND T.YGXX_YGID = V.YHXX_YGID ");
			strbuf.append(" AND A.YGZW_ZWID = B.ZWXX_ZWID ");
			strbuf.append(" AND B.ZWXX_BMID = C.BMXX_BMID ");
			strbuf.append(" AND T.YGXX_SCBZ = '0'");
			strbuf.append(" AND A.YGZW_SCBZ = '0'");
			strbuf.append(" AND B.ZWXX_SCBZ = '0'");
			strbuf.append(" AND C.BMXX_SCBZ = '0'");
			strbuf.append(" AND C.BMXX_BMID = '").append(bmid).append("'");
			strbuf.append(" AND A.YGZW_SJZG = '").append(ygid).append("'");

			ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
					Pojo_SELECT_LIST.class);

			result = db.queryForBeanListHandler(strbuf, rs);
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
     * @FunctionName: getWjmcList
     * @Description: 玩家名称下拉列表
     * @return
     * @throws Exception
     * @return List<Pojo_SELECT_LIST>
     * @author czl
     * @date 2016-9-12
     */
    public List<Pojo_SELECT_LIST> getWjmcList() throws Exception {
        List<Pojo_SELECT_LIST> result = null;

        try {
            db.openConnection();

            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     Y.YHXX_YHID AS CODE,");
            strbuf.append("     Y.YHXX_YHMC AS NAME");
            strbuf.append(" FROM ");
            strbuf.append("     YHXX Y");
            strbuf.append(" WHERE ");
            strbuf.append("     Y.YHXX_SCBZ = '0'");
            strbuf.append(" AND ");
            strbuf.append("     Y.YHXX_JSID != 'J101'");

            ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
                    Pojo_SELECT_LIST.class);

            result = db.queryForBeanListHandler(strbuf, rs);
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
    * @FunctionName: getWjjsList
    * @Description: 玩家角色下拉列表
    * @return
    * @throws Exception
    * @return List<Pojo_SELECT_LIST>
    * @author czl
    * @date 2016-9-29
    */
   public List<Pojo_SELECT_LIST> getWjjsList() throws Exception {
       List<Pojo_SELECT_LIST> result = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     L.LRXX_LRID AS CODE,");
           strbuf.append("     L.LRXX_LRMC AS NAME");
           strbuf.append(" FROM ");
           strbuf.append("     LRXX L");
           strbuf.append(" WHERE ");
           strbuf.append("     L.LRXX_SCBZ = '0'");

           ResultSetHandler<List<Pojo_SELECT_LIST>> rs = new BeanListHandler<Pojo_SELECT_LIST>(
                   Pojo_SELECT_LIST.class);

           result = db.queryForBeanListHandler(strbuf, rs);
       } catch (Exception e) {
           MyLogger.error(this.getClass().getName(), e);
           throw e;
       } finally {
           db.closeConnection();
       }
       return result;
   }
}