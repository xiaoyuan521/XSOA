package com.xsoa.service.service9040000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.basetable.Pojo_ZWXX;

/**	
 * @ClassName: Service9040130	
 * @Package:com.xsoa.service.service9040000	
 * @Description: 职务信息Service	
 * @author ljg	
 * @date 2015年2月5日 下午4:11:03	
 * @update		
 */	
public class Service9040130 extends BaseService {
	private DBManager db = null;

	public Service9040130() {
		db = new DBManager();
	}

	/**	
	 * @FunctionName: getZWXXDataCount	
	 * @Description: 获取部门信息列表数据个数	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return int	
	 * @author ljg	
	 * @date 2015年2月5日 下午3:20:46	
	 */	
	public int getZWXXDataCount(Pojo_ZWXX beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(ZWXX_ZWID) ");//职务信息个数
			strbuf.append(" FROM ");
			strbuf.append("     ZWXX A");
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
	 * @FunctionName: getZWXXDataList	
	 * @Description: 获取职务信息列表数据明细	
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception	
	 * @return List<Pojo_ZWXX>	
	 * @author ljg	
	 * @date 2015年2月5日 下午3:23:07	
	 */	
	public List<Pojo_ZWXX> getZWXXDataList(Pojo_ZWXX beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo_ZWXX> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.ZWXX_ZWID, ");//职务ID	
			strbuf.append("     A.ZWXX_ZWMC, ");//职务名称	
			strbuf.append("     A.ZWXX_BMID, ");//部门ID
			strbuf.append("     C.BMXX_BMMC AS ZWXX_BMMC, ");//部门名称
			strbuf.append("     A.ZWXX_JSID, ");//角色ID
			strbuf.append("     B.YHJS_JSMC AS ZWXX_JSMC, ");//角色名称
			strbuf.append("     A.ZWXX_BZXX, ");//备注	
			strbuf.append("     A.ZWXX_CJR, ");//创建人
			strbuf.append("     D.YHXX_YHMC AS ZWXX_CJRM, ");//创建人名
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.ZWXX_CJSJ, '%Y%m%d'), '%Y-%m-%d') AS ZWXX_CJSJ, ");//创建时间	
			strbuf.append("     A.ZWXX_GXR, ");//更新人
			strbuf.append("     E.YHXX_YHMC AS ZWXX_GXRM, ");//更新人名
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.ZWXX_GXSJ, '%Y%m%d'), '%Y-%m-%d') AS ZWXX_GXSJ, ");//更新时间	
			strbuf.append("     CASE WHEN A.ZWXX_SCBZ = '0' THEN '未删除' ELSE '已删除' END AS SCBZ ");//删除标志显示名称
			strbuf.append(" FROM ");
			strbuf.append("     ZWXX AS A ");
			strbuf.append(" LEFT JOIN YHJS AS B ON A.ZWXX_JSID = B.YHJS_JSID");
			strbuf.append(" LEFT JOIN BMXX AS C ON A.ZWXX_BMID = C.BMXX_BMID");
			strbuf.append(" LEFT JOIN YHXX AS D ON A.ZWXX_CJR = D.YHXX_YHID");
			strbuf.append(" LEFT JOIN YHXX AS E ON A.ZWXX_GXR = E.YHXX_YHID");
						
			strbuf.append(" WHERE 1=1 ");

			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.ZWXX_BMID, A.ZWXX_JSID ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo_ZWXX>> rs = new BeanListHandler<Pojo_ZWXX>(
					Pojo_ZWXX.class);
			
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
	private String searchSql(Pojo_ZWXX beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 部门ID */
			if (MyStringUtils.isNotBlank(beanIn.getZWXX_BMID()) && !("000".equals(beanIn.getZWXX_BMID()))) {
				strbuf.append(" AND A.ZWXX_BMID = '").append(beanIn.getZWXX_BMID()).append("'");
			}
			/* 角色ID */
			if (MyStringUtils.isNotBlank(beanIn.getZWXX_JSID()) && !("000".equals(beanIn.getZWXX_JSID()))) {
				strbuf.append(" AND A.ZWXX_JSID = '").append(beanIn.getZWXX_JSID()).append("'");
			}
			
			/* 职务ID */
			if (MyStringUtils.isNotBlank(beanIn.getZWXX_ZWID())) {
				strbuf.append(" AND A.ZWXX_ZWID = '").append(beanIn.getZWXX_ZWID()).append("'");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}

	/**	
	 * @FunctionName: insertZWXX	
	 * @Description: 新增职务信息	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月5日 下午3:54:33	
	 */	
	public boolean insertZWXX(Pojo_ZWXX beanIn) throws Exception {
		boolean result = false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     ZWXX ");
			strbuf.append(" ( ");
			strbuf.append("     ZWXX_ZWID, ");//职务ID
			strbuf.append("     ZWXX_ZWMC, ");//职务名称
			strbuf.append("     ZWXX_BMID, ");//部门ID
			strbuf.append("     ZWXX_JSID, ");//角色ID
			strbuf.append("     ZWXX_BZXX, ");//备注
			strbuf.append("     ZWXX_CJR, ");//创建人
			strbuf.append("     ZWXX_CJSJ, ");//创建时间
			strbuf.append("     ZWXX_GXR, ");//更新人
			strbuf.append("     ZWXX_GXSJ, ");//更新时间
			strbuf.append("     ZWXX_SCBZ  ");//删除标志
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+beanIn.getZWXX_ZWID()+"', ");//职务ID
			strbuf.append("     '"+beanIn.getZWXX_ZWMC()+"', ");//职务名称
			strbuf.append("     '"+beanIn.getZWXX_BMID()+"', ");//部门ID
			strbuf.append("     '"+beanIn.getZWXX_JSID()+"', ");//角色ID
			strbuf.append("     '"+beanIn.getZWXX_BZXX()+"', ");//备注
			strbuf.append("     '"+beanIn.getZWXX_CJR()+"', ");//创建人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getZWXX_GXR()+"', ");//更新人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),  ");//修改时间
			strbuf.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" ) ");
			
			count = db.executeSQL(strbuf);
			
			if (count > 0) {
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
	 * @FunctionName: updateZWXX	
	 * @Description: 修改职务信息
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:00:42	
	 */	
	public boolean updateZWXX(Pojo_ZWXX beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     ZWXX ");
			strbuf.append(" SET ");
			strbuf.append("     ZWXX_ZWMC='").append(beanIn.getZWXX_ZWMC()).append("',");//职务名称
			strbuf.append("     ZWXX_BMID='").append(beanIn.getZWXX_BMID()).append("',");//部门ID
			strbuf.append("     ZWXX_JSID='").append(beanIn.getZWXX_JSID()).append("',");//角色ID
			strbuf.append("     ZWXX_BZXX='").append(beanIn.getZWXX_BZXX()).append("',");//备注

			strbuf.append("     ZWXX_GXR='").append(beanIn.getZWXX_GXR()).append("',");//更新人
			strbuf.append("     ZWXX_GXSJ= DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     ZWXX_ZWID='").append(beanIn.getZWXX_ZWID()).append("' ");//职务ID
			
			count = db.executeSQL(strbuf);
			
			if (count > 0) {
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
	 * @FunctionName: deleteZWXX	
	 * @Description: 删除职务信息	
	 * @param beanIn
	 * @return
	 * @throws Exception	
	 * @return boolean	
	 * @author ljg	
	 * @date 2015年2月5日 下午4:05:35	
	 */	
	public boolean deleteZWXX(Pojo_ZWXX beanIn) throws Exception {
		boolean result =false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" DELETE FROM ");
			strbuf.append("     ZWXX ");
			strbuf.append(" WHERE ");
			strbuf.append("     ZWXX_ZWID='").append(beanIn.getZWXX_ZWID()).append("' ");//职务id
			count = db.executeSQL(strbuf);
			
			if (count > 0) {
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