package com.xsoa.service.common;

import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.common.Pojo_PROCESS_LOGS;

public class ProcessLogsService extends BaseService {
	private DBManager db = null;

	public ProcessLogsService() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: getProcessList
	 * @Description: 获取审批流程
	 * @param sqid,logtype
	 * @throws Exception
	 * @return Pojo_PROCESS_LOGS
	 * @author czl
	 * @date 2015-03-18
	 */
	public List<Pojo_PROCESS_LOGS> getProcessList(String sqid, String logtype)
			throws Exception {
		List<Pojo_PROCESS_LOGS> result = null;
		try {
			db.openConnection();
			StringBuffer sql = new StringBuffer();
			if(logtype.equals("SPLC_KQCC")){//考勤出差
				sql.append("SELECT A.CCMX_MXID,");//出差明细ID
				sql.append("  	  A.CCMX_CZSJ AS CZSJ,");//操作时间
				sql.append("  	  C.DICT_ZDMC AS CZLX,");//操作类型
				sql.append("  	  A.CCMX_CZNR AS CZNR,");//操作内容
				sql.append("  	  B.YGXX_XM AS CZR");//操作人
				sql.append("  FROM CCMX A,YGXX B,DICT C");
				sql.append("  WHERE A.CCMX_CZR = B.YGXX_YGID AND A.CCMX_CZZT=C.DICT_ZDDM");
				sql.append("  AND A.CCMX_SCBZ = '0'");
				sql.append("  AND C.DICT_ZDLX = 'SPZT'");
				if (MyStringUtils.isNotBlank(sqid)) {
					sql.append(" AND A.CCMX_SQID ='").append(sqid).append("'");					
				}
				sql.append("  ORDER BY A.CCMX_CJSJ ASC");
			}else if(logtype.equals("SPLC_KQJB")){//考勤加班
				sql.append("SELECT A.JBMX_MXID,");//加班明细ID
				sql.append("  	  A.JBMX_CZSJ AS CZSJ,");//操作时间
				sql.append("  	  C.DICT_ZDMC AS CZLX,");//操作类型
				sql.append("  	  A.JBMX_CZNR AS CZNR,");//操作内容
				sql.append("  	  B.YGXX_XM AS CZR");//操作人
				sql.append("  FROM JBMX A,YGXX B,DICT C");
				sql.append("  WHERE A.JBMX_CZR = B.YGXX_YGID AND A.JBMX_CZZT=C.DICT_ZDDM");
				sql.append("  AND A.JBMX_SCBZ = '0'");
				sql.append("  AND C.DICT_ZDLX = 'SPZT'");
				if (MyStringUtils.isNotBlank(sqid)) {
					sql.append(" AND A.JBMX_SQID ='").append(sqid).append("'");					
				}
				sql.append("  ORDER BY A.JBMX_CJSJ ASC");
			}else if(logtype.equals("SPLC_KQXJ")){//考勤休假
				sql.append("SELECT A.XJMX_MXID,");//休假明细ID
				sql.append("  	  A.XJMX_CZSJ AS CZSJ,");//操作时间
				sql.append("  	  C.DICT_ZDMC AS CZLX,");//操作类型
				sql.append("  	  A.XJMX_CZNR AS CZNR,");//操作内容
				sql.append("  	  B.YGXX_XM AS CZR");//操作人
				sql.append("  FROM XJMX A,YGXX B,DICT C");
				sql.append("  WHERE A.XJMX_CZR = B.YGXX_YGID AND A.XJMX_CZZT=C.DICT_ZDDM");
				sql.append("  AND A.XJMX_SCBZ = '0'");
				sql.append("  AND C.DICT_ZDLX = 'RBZT'");
				if (MyStringUtils.isNotBlank(sqid)) {
					sql.append(" AND A.XJMX_SQID ='").append(sqid).append("'");					
				}
				sql.append("  ORDER BY A.XJMX_CJSJ ASC");
			}
			ResultSetHandler<List<Pojo_PROCESS_LOGS>> rsh = new BeanListHandler<Pojo_PROCESS_LOGS>(
					Pojo_PROCESS_LOGS.class);
			result = db.queryForBeanListHandler(sql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
}