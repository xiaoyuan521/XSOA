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
import com.xsoa.pojo.custom.pojo_1020000.Pojo1020130;

/**	
 * @ClassName: Service1020130	
 * @Package:com.xsoa.service.service1020000	
 * @Description: 休假申请Service	
 * @author ljg	
 * @date 2015年2月25日 上午11:39:27	
 * @update		
 */	
public class Service1020130 extends BaseService {
	private DBManager db = null;

	public Service1020130() {
		db = new DBManager();
	}
	
	public int getCheckXJSQCount(Pojo1020130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(XJSQ_SQID) ");//休假申请个数
			strbuf.append(" FROM ");
			strbuf.append("     XJSQ A");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append("   AND A.XJSQ_SQR = '").append(beanIn.getXJSQ_SQR()).append("'");// 申请人
			strbuf.append("   AND A.XJSQ_XJZT IN ('0','2','3') ");// 状态:0-申请2-流转3-批准
			
			/* 休假类型 */
			if (MyStringUtils.isNotBlank(beanIn.getXJSQ_XJLX())) {
				if(!"000".equals(beanIn.getXJSQ_XJLX())){
					strbuf.append(" AND A.XJSQ_XJLX = '").append(beanIn.getXJSQ_XJLX()).append("'");	
				}
			}
			
			/* 请假期限 */
			if (MyStringUtils.isNotBlank(beanIn.getXJSQ_KSSJ()) && MyStringUtils.isNotBlank(beanIn.getXJSQ_JSSJ())) {
				strbuf.append(" AND (");
				strbuf.append("      A.XJSQ_KSSJ BETWEEN '").append(beanIn.getXJSQ_KSSJ()).append("' AND '").append(beanIn.getXJSQ_JSSJ()).append("'");
				strbuf.append("      OR A.XJSQ_JSSJ BETWEEN '").append(beanIn.getXJSQ_KSSJ()).append("' AND '").append(beanIn.getXJSQ_JSSJ()).append("'");
				strbuf.append("     )");
			}
			
			if (MyStringUtils.isNotBlank(beanIn.getXJSQ_SQID())) {
				strbuf.append(" AND A.XJSQ_SQID != '").append(beanIn.getXJSQ_SQID()).append("'");
			}
			
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
	 * @FunctionName: getXJSQDataCount	
	 * @Description: 获取休假申请列表数据个数	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return int	
	 * @author ljg	
	 * @date 2015年2月25日 下午2:25:01	
	 */	
	public int getXJSQDataCount(Pojo1020130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(XJSQ_SQID) ");//休假申请个数
			strbuf.append(" FROM ");
			strbuf.append("     XJSQ A");
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
	 * @FunctionName: getXJSQDataList	
	 * @Description: 获取休假申请列表数据明细	
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception	
	 * @return List<Pojo1020130>	
	 * @author ljg	
	 * @date 2015年2月25日 下午3:23:07	
	 */	
	public List<Pojo1020130> getXJSQDataList(Pojo1020130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1020130> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(selectSql());

			strbuf.append(" WHERE 1=1 ");

			strbuf.append(searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.XJSQ_SQSJ ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1020130>> rs = new BeanListHandler<Pojo1020130>(
					Pojo1020130.class);
			
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
	 * @FunctionName: searchSql	
	 * @Description: 查询条件部分	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return String	
	 * @author ljg	
	 * @date 2015年2月25日 下午2:46:52	
	 */	
	private String searchSql(Pojo1020130 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			strbuf.append("   AND A.XJSQ_SQR = '").append(beanIn.getXJSQ_SQR()).append("'");// 申请人
			
			/*状态*/
			if (MyStringUtils.isNotBlank(beanIn.getXJSQ_XJZT())) {
				if(!"000".equals(beanIn.getXJSQ_XJZT())){
					strbuf.append("   AND A.XJSQ_XJZT ='").append(beanIn.getXJSQ_XJZT()).append("'");	
				}
			}
			/* 申请时间 */
			if (MyStringUtils.isNotBlank(beanIn.getXJSQ_SQSJ())) {
				strbuf.append(" AND A.XJSQ_SQSJ = '").append(beanIn.getXJSQ_SQSJ()).append("'");
			}
			/* 休假类型 */
			if (MyStringUtils.isNotBlank(beanIn.getXJSQ_XJLX())) {
				if(!"000".equals(beanIn.getXJSQ_XJLX())){
					strbuf.append(" AND A.XJSQ_XJLX = '").append(beanIn.getXJSQ_XJLX()).append("'");	
				}
			}
			/* 开始时间 */
			if (MyStringUtils.isNotBlank(beanIn.getXJSQ_KSSJ())) {
				strbuf.append(" AND A.XJSQ_KSSJ >= '").append(beanIn.getXJSQ_KSSJ()).append("'");
			}
			/* 结束时间 */
			if (MyStringUtils.isNotBlank(beanIn.getXJSQ_JSSJ())) {
				strbuf.append(" AND A.XJSQ_JSSJ <= '").append(beanIn.getXJSQ_JSSJ()).append("'");
			}
			
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}

	/**	
	 * @FunctionName: insertXJSQ	
	 * @Description: 新增请假申请	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月25日 下午3:11:45	
	 */	
	public boolean insertXJSQ(Pojo1020130 beanIn) throws Exception {
		boolean result = false;

		try {
			db.openConnection();
			
			String strId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     XJSQ ");
			strbuf.append(" ( ");
			strbuf.append("     XJSQ_SQID, ");//申请ID	
			strbuf.append("     XJSQ_XJLX, ");//休假类型	
			strbuf.append("     XJSQ_SQR, ");//申请人
			strbuf.append("     XJSQ_SQSJ, ");//申请时间	
			strbuf.append("     XJSQ_KSSJ, ");//开始时间	
			strbuf.append("     XJSQ_JSSJ, ");//结束时间	
			strbuf.append("     XJSQ_XJTS, ");//请假天数	
			strbuf.append("     XJSQ_XJZT, ");//状态
			strbuf.append("     XJSQ_SFWJ, ");//是否完结	
			strbuf.append("     XJSQ_XYSP, ");//下一审批人
			strbuf.append("     XJSQ_XJYY, ");//请假原因	
			strbuf.append("     XJSQ_CJR, ");//创建人	
			strbuf.append("     XJSQ_CJSJ, ");//创建时间	
			strbuf.append("     XJSQ_GXR, ");//更新人	
			strbuf.append("     XJSQ_GXSJ, ");//更新时间	
			strbuf.append("     XJSQ_SCBZ  ");//删除标志	
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+strId+"', ");//申请ID	
			strbuf.append("     '"+beanIn.getXJSQ_XJLX()+"', ");//休假类型	
			strbuf.append("     '"+beanIn.getXJSQ_SQR()+"', ");//申请人
			strbuf.append("     '"+beanIn.getXJSQ_SQSJ()+"', ");//申请时间	
			strbuf.append("     '"+beanIn.getXJSQ_KSSJ()+"', ");//开始时间	
			strbuf.append("     '"+beanIn.getXJSQ_JSSJ()+"', ");//结束时间	
			strbuf.append("     '"+beanIn.getXJSQ_XJTS()+"', ");//请假天数	
			strbuf.append("     '0', ");//状态:0-申请
			strbuf.append("     '0', ");//是否完结	
			strbuf.append("     '"+beanIn.getXJSQ_XYSP()+"', ");//下一审批人
			strbuf.append("     '"+beanIn.getXJSQ_XJYY()+"', ");//请假原因	
			strbuf.append("     '"+beanIn.getXJSQ_CJR()+"', ");//创建人	
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getXJSQ_GXR()+"', ");//更新人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");//修改时间
			strbuf.append("     '0'  ");//删除标志（0：未删除，1：已删除）	
			strbuf.append(" ) ");
			
			int i = db.executeSQL(strbuf);
			if(i>0){
				String strMXId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
				strbuf.setLength(0);
				strbuf.append(" INSERT INTO ");
				strbuf.append("     XJMX ");
				strbuf.append(" ( ");
				strbuf.append("     XJMX_MXID, ");//休假明细ID
				strbuf.append("     XJMX_SQID, ");//申请ID
				strbuf.append("     XJMX_CZZT, ");//操作状态
				strbuf.append("     XJMX_CZR, ");//操作人
				strbuf.append("     XJMX_CZSJ, ");//操作时间
				strbuf.append("     XJMX_CZNR, ");//操作内容
				strbuf.append("     XJMX_XYCZR, ");//下一操作人
				strbuf.append("     XJMX_CJR, ");//创建人
				strbuf.append("     XJMX_CJSJ, ");//创建时间
				strbuf.append("     XJMX_GXR, ");//更新人
				strbuf.append("     XJMX_GXSJ, ");//更新时间
				strbuf.append("     XJMX_SCBZ	  ");//删除标志	
				strbuf.append(" ) ");
				strbuf.append(" VALUES ");
				strbuf.append(" ( ");
				strbuf.append("     '"+strMXId+"', ");//休假明细ID
				strbuf.append("     '"+strId+"', ");//申请ID
				strbuf.append("     '0', ");//操作状态
				strbuf.append("     '"+beanIn.getXJSQ_SQR()+"', ");//操作人
				strbuf.append("     '"+beanIn.getXJSQ_SQSJ()+"', ");//操作时间
				strbuf.append("     '"+beanIn.getXJSQ_XJYY()+"', ");//操作内容
				strbuf.append("     '"+beanIn.getXJSQ_XYSP()+"', ");//下一操作人
				strbuf.append("     '"+beanIn.getXJSQ_CJR()+"', ");//创建人
				strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
				strbuf.append("     '"+beanIn.getXJSQ_GXR()+"', ");//更新人
				strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");//修改时间
				strbuf.append("     '0'  ");//删除标志（0：未删除，1：已删除）	
				strbuf.append(" ) ");
				db.executeSQL(strbuf);
			}
			result = true;
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**	
	 * @FunctionName: updateXJSQ	
	 * @Description: 修改请假申请	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月25日 下午3:11:22	
	 */	
	public boolean updateXJSQ(Pojo1020130 beanIn) throws Exception {
		boolean result = false;
		
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     XJSQ ");
			strbuf.append(" SET ");
			
			strbuf.append("     XJSQ_XJLX='").append(beanIn.getXJSQ_XJLX()).append("',");//休假类型	
			strbuf.append("     XJSQ_SQR='").append(beanIn.getXJSQ_SQR()).append("',");//申请人
			strbuf.append("     XJSQ_SQSJ='").append(beanIn.getXJSQ_SQSJ()).append("',");//申请时间	
			strbuf.append("     XJSQ_KSSJ='").append(beanIn.getXJSQ_KSSJ()).append("',");//开始时间	
			strbuf.append("     XJSQ_JSSJ='").append(beanIn.getXJSQ_JSSJ()).append("',");//结束时间	
			strbuf.append("     XJSQ_XJTS='").append(beanIn.getXJSQ_XJTS()).append("',");//请假天数	
			strbuf.append("     XJSQ_XJZT='0',");//状态
			strbuf.append("     XJSQ_XYSP='").append(beanIn.getXJSQ_XYSP()).append("',");//下一审批人
			strbuf.append("     XJSQ_XJYY='").append(beanIn.getXJSQ_XJYY()).append("',");//请假原因	
			
			strbuf.append("     XJSQ_GXR='").append(beanIn.getXJSQ_GXR()).append("',");//更新人	
			strbuf.append("     XJSQ_GXSJ= DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间	
			strbuf.append("     XJSQ_SCBZ='0'");//删除标志	

			strbuf.append(" WHERE ");
			strbuf.append("     XJSQ_SQID='").append(beanIn.getXJSQ_SQID()).append("' ");//申请ID
			
			db.executeSQL(strbuf);
			result = true;
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**	
	 * @FunctionName: deleteXJSQ	
	 * @Description: 删除休假申请	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月25日 下午3:12:15	
	 */	
	public boolean deleteXJSQ(Pojo1020130 beanIn) throws Exception {
		boolean result =false;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("     XJSQ ");
			strbuf.append(" WHERE ");
			strbuf.append("     XJSQ_SQID='").append(beanIn.getXJSQ_SQID()).append("' ");//申请id
			db.executeSQL(strbuf);
			
			result = true;
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	
	/**	
	 * @FunctionName: getDataBySQID	
	 * @Description: 根据休假ID取得休假申请记录
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return Pojo1020130	
	 * @author ljg	
	 * @date 2015年2月27日 下午4:34:54	
	 */	
	public Pojo1020130 getDataBySQID(Pojo1020130 beanIn) throws Exception {
		Pojo1020130 result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(selectSql());

			strbuf.append(" WHERE 1=1 ");
			strbuf.append("   AND A.XJSQ_SQID = '").append(beanIn.getXJSQ_SQID()).append("'");// 申请ID

			strbuf.append(" ORDER BY ");
			strbuf.append("     A.XJSQ_SQSJ ");

			ResultSetHandler<Pojo1020130> rsh = new BeanHandler<Pojo1020130>(Pojo1020130.class);

			result = db.queryForBeanHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**	
	 * @FunctionName: getDataByUSER	
	 * @Description: 根据用户取得部门
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return Pojo1020130	
	 * @author ljg	
	 * @date 2015年2月28日 上午9:55:37	
	 */	
	public Pojo1020130 getDataByUSER(Pojo1020130 beanIn) throws Exception {
		Pojo1020130 result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append("SELECT");
			strbuf.append("		A.YHXX_YHID AS XJSQ_SQR,");
			strbuf.append("		A.YHXX_YHMC AS XJSQ_SQRM,");
			strbuf.append("		C.BMXX_BMID AS XJSQ_BMID,");
			strbuf.append("		C.BMXX_BMMC AS XJSQ_BMMC");
			strbuf.append("  FROM YHXX AS A");
			strbuf.append("		  LEFT JOIN YHJS AS B ON A.YHXX_JSID = B.YHJS_JSID");
			strbuf.append("		  LEFT JOIN BMXX AS C ON B.YHJS_BMID = C.BMXX_BMID");

			strbuf.append(" WHERE 1=1 ");
			strbuf.append("   AND A.YHXX_YGID = '").append(beanIn.getXJSQ_SQR()).append("'");// 申请人

			ResultSetHandler<Pojo1020130> rsh = new BeanHandler<Pojo1020130>(Pojo1020130.class);

			result = db.queryForBeanHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**	
	 * @FunctionName: selectSql	
	 * @Description: 查询SQL主体	
	 * @return
	 * @throws Exception	
	 * @return String	
	 * @author ljg	
	 * @date 2015年2月28日 上午9:46:29	
	 */	
	private String selectSql() throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			strbuf.append(" SELECT ");
			strbuf.append("     A.XJSQ_SQID, ");// 申请ID
			strbuf.append("     A.XJSQ_XJLX, ");// 休假类型
			strbuf.append("     B.DICT_ZDMC AS XJSQ_XJMC, ");// 休假名称
			strbuf.append("     A.XJSQ_SQR, ");// 申请人
			strbuf.append("     D.YHXX_YHMC AS XJSQ_SQRM, ");// 申请人名
			strbuf.append("     A.XJSQ_SQSJ, ");// 申请时间
			strbuf.append("     A.XJSQ_KSSJ, ");// 开始时间
			strbuf.append("     A.XJSQ_JSSJ, ");// 结束时间
			strbuf.append("     A.XJSQ_XJTS, ");// 请假天数
			strbuf.append("     A.XJSQ_XJZT, ");// 状态
			strbuf.append("     C.DICT_ZDMC AS XJSQ_ZTMC, ");// 状态名称
			strbuf.append("     IF(A.XJSQ_SFWJ='0','未完结','已完结') AS XJSQ_SFWJ, ");// 是否完结
			strbuf.append("     A.XJSQ_XYSP, ");// 下一审批人
			strbuf.append("     CONCAT(E.YHXX_YHID, '-', E.YHXX_YHMC) AS XJSQ_SPMC, ");// 审批人名
			strbuf.append("     A.XJSQ_XJYY, ");// 请假原因
			strbuf.append("     G.BMXX_BMID AS XJSQ_BMID, ");// 申请人部门ID
			strbuf.append("     G.BMXX_BMMC AS XJSQ_BMMC, ");// 申请人部门名称
			strbuf.append("     A.XJSQ_CJR, ");// 创建人
			strbuf.append("     A.XJSQ_CJSJ, ");// 创建时间
			strbuf.append("     A.XJSQ_GXR, ");// 更新人
			strbuf.append("     A.XJSQ_GXSJ, ");// 更新时间
			strbuf.append("     A.XJSQ_SCBZ  ");// 删除标志

			strbuf.append(" FROM ");
			strbuf.append("     XJSQ AS A ");
			strbuf.append("     LEFT JOIN DICT AS B ON A.XJSQ_XJLX = B.DICT_ZDDM AND B.DICT_ZDLX = 'XJLX'");
			strbuf.append("     LEFT JOIN DICT AS C ON A.XJSQ_XJZT = C.DICT_ZDDM AND C.DICT_ZDLX = 'SPZT'");
			strbuf.append("     LEFT JOIN YHXX AS D ON A.XJSQ_SQR = D.YHXX_YGID");
			strbuf.append("     LEFT JOIN YHXX AS E ON A.XJSQ_XYSP = E.YHXX_YGID");
			strbuf.append("     LEFT JOIN YHJS AS F ON D.YHXX_JSID = F.YHJS_JSID");
			strbuf.append("     LEFT JOIN BMXX AS G ON F.YHJS_BMID = G.BMXX_BMID");

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	
}
