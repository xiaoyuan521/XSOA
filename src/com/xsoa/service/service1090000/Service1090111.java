package com.xsoa.service.service1090000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1090000.Pojo1090111;

/**	
 * @ClassName: Service1090111	
 * @Package:com.xsoa.service.service1090000	
 * @Description: 审批设定Service	
 * @author ljg	
 * @date 2015年2月13日 上午9:40:04	
 * @update	
 */
public class Service1090111 extends BaseService {
	private DBManager db = null;

	public Service1090111() {
		db = new DBManager();
	}
	
	public int getCheckLCMXCount(Pojo1090111 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(LCMX_JDID) ");//审批人ID数
			strbuf.append(" FROM ");
			strbuf.append("     LCMX A");
			strbuf.append(" WHERE 1=1 ");
			
			/* 流程ID */
			if (MyStringUtils.isNotBlank(beanIn.getLCMX_LCID())) {
				strbuf.append(" AND A.LCMX_LCID = '").append(beanIn.getLCMX_LCID()).append("'");
			}
			
			/* 审批人ID */
			if (MyStringUtils.isNotBlank(beanIn.getLCMX_SPID())) {
				strbuf.append(" AND A.LCMX_SPID = '").append(beanIn.getLCMX_SPID()).append("'");
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
	 * @FunctionName: getLCMXDataCount	
	 * @Description: 获取审批人员列表数据个数	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return int	
	 * @author ljg	
	 * @date 2015年2月5日 下午3:20:46	
	 */	
	public int getLCMXDataCount(Pojo1090111 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(LCMX_JDID) ");//流程信息个数
			strbuf.append(" FROM ");
			strbuf.append("     LCMX A");
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
	 * @FunctionName: getLCMXDataList	
	 * @Description: 获取审批人员列表数据明细	
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception	
	 * @return List<Pojo1090111>	
	 * @author ljg	
	 * @date 2015年2月5日 下午3:23:07	
	 */	
	public List<Pojo1090111> getLCMXDataList(Pojo1090111 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1090111> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.LCMX_JDID, ");//节点配置ID
			strbuf.append("     A.LCMX_LCID, ");//流程ID
			strbuf.append("     CONCAT('第',A.LCMX_SPJB,'审批人') AS LCMX_SPJB, ");//审批级别
			strbuf.append("     A.LCMX_SPID, ");//员工ID
			strbuf.append("     B.YGXX_XM, ");//员工姓名
			strbuf.append("     B.YGXX_JSID, ");//角色ID
			strbuf.append("     C.YHJS_JSMC, ");//角色名称
			strbuf.append("     C.YHJS_BMID, ");//部门ID
			strbuf.append("     D.BMXX_BMMC, ");//部门名称
			strbuf.append("     IF (A.LCMX_KFZJ='1','可','否') AS LCMX_KFZJ, ");//可否终结
			strbuf.append("     A.LCMX_CJR, ");//创建人
			strbuf.append("     A.LCMX_CJSJ, ");//创建时间
			strbuf.append("     A.LCMX_GXR, ");//更新人
			strbuf.append("     A.LCMX_GXSJ, ");//更新时间
			strbuf.append("     A.LCMX_SCBZ  ");//删除标志
			strbuf.append(" FROM ");
			strbuf.append("     LCMX A ");
			strbuf.append("     LEFT JOIN YGXX AS B ON A.LCMX_SPID = B.YGXX_YGID");
			strbuf.append("     LEFT JOIN YHJS AS C ON B.YGXX_JSID = C.YHJS_JSID");
			strbuf.append("     LEFT JOIN BMXX AS D ON C.YHJS_BMID = D.BMXX_BMID");
			
			strbuf.append(" WHERE 1=1 ");

			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.LCMX_LCID, A.LCMX_SPJB, A.LCMX_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1090111>> rs = new BeanListHandler<Pojo1090111>(
					Pojo1090111.class);
			
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
	private String searchSql(Pojo1090111 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 流程ID */
			if (MyStringUtils.isNotBlank(beanIn.getLCMX_LCID())) {
				strbuf.append(" AND A.LCMX_LCID = '").append(beanIn.getLCMX_LCID()).append("'");
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
	public boolean insertLCMX(Pojo1090111 beanIn) throws Exception {
		boolean result = false;

		try {
			db.openConnection();
			
			String strId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入站点ID
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     LCMX ");
			strbuf.append(" ( ");
			strbuf.append("     LCMX_JDID, ");//节点配置ID
			strbuf.append("     LCMX_LCID, ");//流程ID
			strbuf.append("     LCMX_SPJB, ");//审批级别
			strbuf.append("     LCMX_SPID, ");//员工ID
			strbuf.append("     LCMX_KFZJ, ");//可否终结
			strbuf.append("     LCMX_CJR, ");//创建人
			strbuf.append("     LCMX_CJSJ, ");//创建时间
			strbuf.append("     LCMX_GXR, ");//更新人
			strbuf.append("     LCMX_GXSJ, ");//更新时间
			strbuf.append("     LCMX_SCBZ  ");//删除标志
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+strId+"', ");//节点配置ID
			strbuf.append("     '"+beanIn.getLCMX_LCID()+"', ");//流程ID
			strbuf.append("     '"+beanIn.getLCMX_SPJB()+"', ");//审批级别
			strbuf.append("     '"+beanIn.getLCMX_SPID()+"', ");//员工ID
			strbuf.append("     '"+beanIn.getLCMX_KFZJ()+"', ");//可否终结
			strbuf.append("     '"+beanIn.getLCMX_CJR()+"', ");//创建人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getLCMX_GXR()+"', ");//更新人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");//更新时间
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
	 * @FunctionName: updateLCMX	
	 * @Description: 修改节点配置信息
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:00:42	
	 */	
	public boolean updateLCMX(Pojo1090111 beanIn) throws Exception {
		boolean result = false;
		
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     LCMX ");
			strbuf.append(" SET ");
			strbuf.append("     LCMX_SPJB='").append(beanIn.getLCMX_SPJB()).append("',");//审批级别
			strbuf.append("     LCMX_SPID='").append(beanIn.getLCMX_SPID()).append("',");//员工ID
			strbuf.append("     LCMX_KFZJ='").append(beanIn.getLCMX_KFZJ()).append("',");//可否终结
			strbuf.append("     LCMX_CJR='").append(beanIn.getLCMX_CJR()).append("',");//创建人
			strbuf.append("     LCMX_CJSJ='").append(beanIn.getLCMX_CJSJ()).append("',");//创建时间
			strbuf.append("     LCMX_GXR='").append(beanIn.getLCMX_GXR()).append("',");//更新人
			strbuf.append("     LCXX_GXSJ= DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//更新时间	
			strbuf.append("     LCXX_SCBZ='0'");//删除标志

			strbuf.append(" WHERE ");
			strbuf.append("     LCMX_JDID='").append(beanIn.getLCMX_JDID()).append("' ");//节点配置ID
			
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
	 * @FunctionName: deleteLCMX	
	 * @Description: 删除节点配置信息	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:05:35	
	 */	
	public boolean deleteLCMX(Pojo1090111 beanIn) throws Exception {
		boolean result =false;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("     LCMX ");
			strbuf.append(" WHERE ");
			strbuf.append("     LCMX_JDID='").append(beanIn.getLCMX_JDID()).append("' ");//节点配置ID
			
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
