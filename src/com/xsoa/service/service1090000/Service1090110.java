package com.xsoa.service.service1090000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1090000.Pojo1090110;

/**	
 * @ClassName: Service1090110	
 * @Package:com.xsoa.service.service1090000	
 * @Description: 流程配置Service	
 * @author ljg	
 * @date 2015年2月6日 上午10:54:29	
 * @update	
 */
public class Service1090110 extends BaseService {
	private DBManager db = null;

	public Service1090110() {
		db = new DBManager();
	}
	
	public int getCheckLCXXCount(Pojo1090110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(LCXX_LCID) ");//流程信息个数
			strbuf.append(" FROM ");
			strbuf.append("     LCXX A");
			strbuf.append(" WHERE 1=1 ");
			
			/* 关联功能 */
			if (MyStringUtils.isNotBlank(beanIn.getLCXX_GLGN())) {
				if(!"000".equals(beanIn.getLCXX_GLGN())){
					strbuf.append(" AND A.LCXX_GLGN = '").append(beanIn.getLCXX_GLGN()).append("'");	
				}
			}
			
			/* 申请类别 */
			if (MyStringUtils.isNotBlank(beanIn.getLCXX_SQLB())) {
				strbuf.append(" AND A.LCXX_SQLB = '").append(beanIn.getLCXX_SQLB()).append("'");
			}
			
			/* 申请代码 */
			if (MyStringUtils.isNotBlank(beanIn.getLCXX_SQDM())) {
				strbuf.append(" AND A.LCXX_SQDM = '").append(beanIn.getLCXX_SQDM()).append("'");
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
	 * @FunctionName: getLCXXDataCount	
	 * @Description: 获取流程信息列表数据个数	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return int	
	 * @author ljg	
	 * @date 2015年2月5日 下午3:20:46	
	 */	
	public int getLCXXDataCount(Pojo1090110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(LCXX_LCID) ");//流程信息个数
			strbuf.append(" FROM ");
			strbuf.append("     LCXX A");
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
	 * @FunctionName: getLCXXDataList	
	 * @Description: 获取流程信息列表数据明细	
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception	
	 * @return List<Pojo1090110>	
	 * @author ljg	
	 * @date 2015年2月5日 下午3:23:07	
	 */	
	public List<Pojo1090110> getLCXXDataList(Pojo1090110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1090110> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.LCXX_LCID	, ");//流程ID
			strbuf.append("     A.LCXX_LCMC	, ");//流程名称
			strbuf.append("     A.LCXX_LCMS	, ");//流程描述
			strbuf.append("     A.LCXX_GLGN , ");//关联功能
			strbuf.append("     C.LCGN_GNMC AS LCXX_GLMC, ");//关联名称
			strbuf.append("     A.LCXX_SQLB	, ");//申请类别
			strbuf.append("     A.LCXX_SQDM , ");//申请代码
			strbuf.append("     CASE");//申请名称
			strbuf.append("     WHEN A.LCXX_SQLB = 'CMD_BM' THEN CONCAT('部门-',E.BMXX_BMMC)");//申请名称:部门
			strbuf.append("     WHEN A.LCXX_SQLB = 'CMD_JS' THEN CONCAT('角色-',F.YHJS_JSMC)");//申请名称:角色
			strbuf.append("     WHEN A.LCXX_SQLB = 'CMD_ZW' THEN CONCAT('职务-',G.ZWXX_ZWMC)");//申请名称:职务
			strbuf.append("     WHEN A.LCXX_SQLB = 'CMD_YG' THEN IF (ISNULL(A.LCXX_SQZW),H.YGXX_XM,CONCAT(A.LCXX_SQZW, '-', H.YGXX_XM))");//申请名称:个人
			strbuf.append("     END AS LCXX_SQMC ,");//申请名称
			strbuf.append("     B.LCMX_SPID AS LCXX_SPID,  ");//初始审批ID
			strbuf.append("     D.YGXX_XM AS LCXX_CSSP  ");//初始审批
			strbuf.append(" FROM ");
			strbuf.append("     LCXX AS A LEFT JOIN (");
			//strbuf.append("     SELECT LCMX_LCID,MIN(LCMX_SPJB) AS LCMX_SPJB,MIN(LCMX_CJSJ),LCMX_SPID FROM LCMX GROUP BY LCMX_LCID ORDER BY LCMX_LCID) AS B ON A.LCXX_LCID = B.LCMX_LCID ");//关联流程明细(初始审批,最小创建人)
			strbuf.append("     SELECT X.LCMX_LCID, MIN(X.LCMX_CJSJ),Y.LCMX_SPJB, X.LCMX_SPID FROM LCMX X INNER JOIN ( SELECT LCMX_LCID, MIN(LCMX_SPJB) AS LCMX_SPJB FROM LCMX GROUP BY LCMX_LCID ORDER BY LCMX_LCID ) Y ON X.LCMX_LCID = Y.LCMX_LCID AND X.LCMX_SPJB = Y.LCMX_SPJB GROUP BY X.LCMX_LCID");
			strbuf.append("     ) AS B ON A.LCXX_LCID = B.LCMX_LCID ");
			strbuf.append("     LEFT JOIN LCGN AS C ON A.LCXX_GLGN = C.LCGN_GNID");
			strbuf.append("     LEFT JOIN YGXX AS D ON B.LCMX_SPID = D.YGXX_YGID");
			strbuf.append("     LEFT JOIN BMXX AS E ON A.LCXX_SQDM = E.BMXX_BMID AND A.LCXX_SQLB = 'CMD_BM'");
			strbuf.append("     LEFT JOIN YHJS AS F ON A.LCXX_SQDM = F.YHJS_JSID AND A.LCXX_SQLB = 'CMD_JS'");
			strbuf.append("     LEFT JOIN ZWXX AS G ON A.LCXX_SQDM = G.ZWXX_ZWID AND A.LCXX_SQLB = 'CMD_ZW'");
			strbuf.append("     LEFT JOIN YGXX AS H ON A.LCXX_SQDM = H.YGXX_YGID AND A.LCXX_SQLB = 'CMD_YG'");
			strbuf.append(" WHERE 1=1 ");

			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.LCXX_GLGN, A.LCXX_SQLB ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1090110>> rs = new BeanListHandler<Pojo1090110>(
					Pojo1090110.class);
			
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
	 * @date 2015年2月5日 下午3:54:05	
	 */	
	private String searchSql(Pojo1090110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 流程名称 */
			if (MyStringUtils.isNotBlank(beanIn.getLCXX_LCMC())) {
				strbuf.append(" AND A.LCXX_LCMC = '").append(beanIn.getLCXX_LCMC()).append("'");
			}
			/* 关联功能 */
			if (MyStringUtils.isNotBlank(beanIn.getLCXX_GLGN())) {
				if(!"000".equals(beanIn.getLCXX_GLGN())){
					strbuf.append(" AND A.LCXX_GLGN = '").append(beanIn.getLCXX_GLGN()).append("'");	
				}
			}
			
			/* 申请类别 */
			if (MyStringUtils.isNotBlank(beanIn.getLCXX_SQDM()) && MyStringUtils.isNotBlank(beanIn.getLCXX_SQLB())) {
				if("CMD_BM".equals(beanIn.getLCXX_SQLB())){/*类别为部门,取其下属角色.职务.员工*/
					strbuf.append(" AND (");
					strbuf.append(" A.LCXX_SQDM = '").append(beanIn.getLCXX_SQDM()).append("'");
					strbuf.append(" OR A.LCXX_SQDM IN (SELECT YHJS_JSID FROM YHJS WHERE YHJS_BMID = '").append(beanIn.getLCXX_SQDM()).append("')");
					strbuf.append(" OR A.LCXX_SQDM IN (SELECT ZWXX_ZWID FROM ZWXX WHERE ZWXX_BMID = '").append(beanIn.getLCXX_SQDM()).append("')");
					strbuf.append(" OR A.LCXX_SQDM IN (SELECT A.YGZW_YGID FROM YGZW AS A INNER JOIN ZWXX AS B ON A.YGZW_ZWID = B.ZWXX_ZWID WHERE B.ZWXX_BMID = '").append(beanIn.getLCXX_SQDM()).append("')");
					strbuf.append(" )");
				} else if("CMD_JS".equals(beanIn.getLCXX_SQLB())){/*类别为角色,取其下属职务.员工*/
					strbuf.append(" AND (");
					strbuf.append(" A.LCXX_SQDM = '").append(beanIn.getLCXX_SQDM()).append("'");
					strbuf.append(" OR A.LCXX_SQDM IN (SELECT ZWXX_ZWID FROM ZWXX WHERE ZWXX_JSID = '").append(beanIn.getLCXX_SQDM()).append("')");
					strbuf.append(" OR A.LCXX_SQDM IN (SELECT A.YGZW_YGID FROM YGZW AS A INNER JOIN ZWXX AS B ON A.YGZW_ZWID = B.ZWXX_ZWID WHERE B.ZWXX_JSID = '").append(beanIn.getLCXX_SQDM()).append("')");
					strbuf.append(" )");
				} else if("CMD_ZW".equals(beanIn.getLCXX_SQLB())){/*类别为职务,取其下属员工*/
					strbuf.append(" AND (");
					strbuf.append(" A.LCXX_SQDM = '").append(beanIn.getLCXX_SQDM()).append("'");
					strbuf.append(" OR A.LCXX_SQDM IN (SELECT YGZW_YGID FROM YGZW WHERE YGZW_ZWID = '").append(beanIn.getLCXX_SQDM()).append("')");
					strbuf.append(" )");
				} else if("CMD_YG".equals(beanIn.getLCXX_SQLB())){/*类别为职务,取其下属员工*/
					strbuf.append(" AND A.LCXX_SQDM = '").append(beanIn.getLCXX_SQDM()).append("'");
				}

			}
			
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}

	/**	
	 * @FunctionName: insertLCXX	
	 * @Description: 新增职务信息	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月5日 下午3:54:33	
	 */	
	public boolean insertLCXX(Pojo1090110 beanIn) throws Exception {
		boolean result = false;

		try {
			db.openConnection();
			
			String strId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     LCXX ");
			strbuf.append(" ( ");
			strbuf.append("     LCXX_LCID	, ");//流程ID	
			strbuf.append("     LCXX_LCMC	, ");//流程名称	
			strbuf.append("     LCXX_LCMS	, ");//流程描述	
			strbuf.append("     LCXX_GLGN	, ");//关联功能	
			strbuf.append("     LCXX_SQLB	, ");//申请类别	
			strbuf.append("     LCXX_SQDM	, ");//申请人代码
			strbuf.append("     LCXX_SQZW	, ");//申请人职务
			strbuf.append("     LCXX_CJR	, ");//创建人	
			strbuf.append("     LCXX_CJSJ, ");//创建时间	
			strbuf.append("     LCXX_GXR, ");//更新人	
			strbuf.append("     LCXX_GXSJ, ");//更新时间	
			strbuf.append("     LCXX_SCBZ  ");//删除标志	
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+strId+"', ");//流程ID	
			strbuf.append("     '"+beanIn.getLCXX_LCMC()+"', ");//流程名称	
			strbuf.append("     '"+beanIn.getLCXX_LCMS()+"', ");//流程描述	
			strbuf.append("     '"+beanIn.getLCXX_GLGN()+"', ");//关联功能	
			strbuf.append("     '"+beanIn.getLCXX_SQLB()+"', ");//申请类别	
			strbuf.append("     '"+beanIn.getLCXX_SQDM()+"', ");//申请人代码
			strbuf.append("     '"+beanIn.getLCXX_SQZW()+"', ");//申请人职务
			strbuf.append("     '"+beanIn.getLCXX_CJR()+"', ");//创建人	
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getLCXX_GXR()+"', ");//更新人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");//修改时间
			strbuf.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" ) ");
			
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
	 * @FunctionName: updateLCXX	
	 * @Description: 修改职务信息
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:00:42	
	 */	
	public boolean updateLCXX(Pojo1090110 beanIn) throws Exception {
		boolean result = false;
		
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     LCXX ");
			strbuf.append(" SET ");
			
			strbuf.append("     LCXX_LCMC	='").append(beanIn.getLCXX_LCMC()).append("',");//流程名称	
			strbuf.append("     LCXX_LCMS	='").append(beanIn.getLCXX_LCMS()).append("',");//流程描述	
			strbuf.append("     LCXX_GLGN	='").append(beanIn.getLCXX_GLGN()).append("',");//关联功能	
			strbuf.append("     LCXX_SQLB	='").append(beanIn.getLCXX_SQLB()).append("',");//申请类别	
			strbuf.append("     LCXX_SQDM	='").append(beanIn.getLCXX_SQDM()).append("',");//申请人代码
			strbuf.append("     LCXX_SQZW	='").append(beanIn.getLCXX_SQZW()).append("',");//申请人职务
			
			strbuf.append("     LCXX_GXR='").append(beanIn.getLCXX_GXR()).append("',");//更新人	
			strbuf.append("     LCXX_GXSJ= DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间	
			strbuf.append("     LCXX_SCBZ='0'");//删除标志	

			strbuf.append(" WHERE ");
			strbuf.append("     LCXX_LCID='").append(beanIn.getLCXX_LCID()).append("' ");//流程ID
			
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
	 * @FunctionName: deleteLCXX	
	 * @Description: 删除职务信息	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:05:35	
	 */	
	public boolean deleteLCXX(Pojo1090110 beanIn) throws Exception {
		boolean result =false;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("     LCXX ");
			strbuf.append(" WHERE ");
			strbuf.append("     LCXX_LCID='").append(beanIn.getLCXX_LCID()).append("' ");//流程id
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
}
