package com.xsoa.service.service1010000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010130;

public class Service1010130 extends BaseService {
	private DBManager db = null;

	public Service1010130() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取调动信息列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年2月27日 下午4:37:03
	 */
	public int getDataCount(Pojo1010130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.ZWDD_UUID) ");//调动个数
			strbuf.append(" FROM ");
			strbuf.append("     ZWDD A LEFT JOIN BMXX B ON A.ZWDD_YBMID = B.BMXX_BMID ");
			strbuf.append("                     LEFT JOIN BMXX C ON A.ZWDD_XBMID = C.BMXX_BMID ");
			strbuf.append("                     LEFT JOIN ZWXX D ON A.ZWDD_YZWID = D.ZWXX_ZWID ");
			strbuf.append("                     LEFT JOIN ZWXX E ON A.ZWDD_XZWID = E.ZWXX_ZWID, ");
			strbuf.append("     YGXX F, YHXX G, YHXX H ");
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
	 * @Description: 获取调动信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1010130>
	 * @author ztz
	 * @date 2015年2月27日 下午4:37:25
	 */
	public List<Pojo1010130> getDataList(Pojo1010130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1010130> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.ZWDD_UUID, ");//职务调动ID
			strbuf.append("     A.ZWDD_YGID, ");//员工ID
			strbuf.append("     CONCAT(H.YHXX_YHID, '-', F.YGXX_XM) AS YGXM, ");//员工姓名
			strbuf.append("     A.ZWDD_YBMID, ");//原部门ID
			strbuf.append("     B.BMXX_BMMC AS YBMMC, ");//原部门名称
			strbuf.append("     A.ZWDD_YZWID, ");//原职务ID
			strbuf.append("     D.ZWXX_ZWMC AS YZWMC, ");//原职务名称
			strbuf.append("     A.ZWDD_YZWDJ, ");//原职务等级
			strbuf.append("     A.ZWDD_XBMID, ");//新部门ID
			strbuf.append("     C.BMXX_BMMC AS XBMMC, ");//新部门名称
			strbuf.append("     A.ZWDD_XZWID, ");//新职务ID
			strbuf.append("     E.ZWXX_ZWMC AS XZWMC, ");//新职务名称
			strbuf.append("     A.ZWDD_XZWDJ, ");//新职务等级
			strbuf.append("     A.ZWDD_BZXX, ");//备注信息
			strbuf.append("     A.ZWDD_CJR, ");//创建人
			strbuf.append("     G.YHXX_YHMC AS CJRXM, ");//创建人姓名
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.ZWDD_CJSJ, '%Y%m%d %H:%i:%s'), '%Y-%m-%d %H:%i:%s') AS ZWDD_CJSJ, ");//创建时间
			strbuf.append("     A.ZWDD_GXR, ");//更新人
			strbuf.append("     A.ZWDD_GXSJ, ");//更新时间
			strbuf.append("     A.ZWDD_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     ZWDD A LEFT JOIN BMXX B ON A.ZWDD_YBMID = B.BMXX_BMID ");
			strbuf.append("                     LEFT JOIN BMXX C ON A.ZWDD_XBMID = C.BMXX_BMID ");
			strbuf.append("                     LEFT JOIN ZWXX D ON A.ZWDD_YZWID = D.ZWXX_ZWID ");
			strbuf.append("                     LEFT JOIN ZWXX E ON A.ZWDD_XZWID = E.ZWXX_ZWID, ");
			strbuf.append("     YGXX F, YHXX G, YHXX H ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.ZWDD_YGID, A.ZWDD_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1010130>> rs = new BeanListHandler<Pojo1010130>(
					Pojo1010130.class);
			
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
	 * @date 2015年2月27日 下午4:37:47
	 */
	private String searchSql(Pojo1010130 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.ZWDD_YGID = F.YGXX_YGID ");
			strbuf.append(" AND A.ZWDD_CJR = G.YHXX_YHID ");
			strbuf.append(" AND F.YGXX_YGID = H.YHXX_YGID ");
			strbuf.append(" AND A.ZWDD_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND F.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND G.YHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND H.YHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			/* 所在部门 */
			if (MyStringUtils.isNotBlank(beanIn.getBMID())) {
				if (!"000".equals(beanIn.getBMID())) {
					strbuf.append(" AND (A.ZWDD_YBMID = '").append(beanIn.getBMID()).append("'");
					strbuf.append(" OR A.ZWDD_XBMID = '").append(beanIn.getBMID()).append("')");
				}
			}
			/* 员工姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getZWDD_YGID())) {
				if (!"000".equals(beanIn.getZWDD_YGID())) {
					strbuf.append(" AND A.ZWDD_YGID = '").append(beanIn.getZWDD_YGID()).append("'");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
}