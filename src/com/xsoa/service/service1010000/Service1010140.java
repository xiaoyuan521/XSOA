package com.xsoa.service.service1010000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010140;

public class Service1010140 extends BaseService {
	private DBManager db = null;

	public Service1010140() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取评价信息列表数据个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年2月27日 下午4:45:01
	 * @update ztz at 2015年3月19日 上午10:15:50
	 */
	public int getDataCount(Pojo1010140 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.YGPJ_PJID) ");//评价个数
			strbuf.append(" FROM ");
			strbuf.append("     YGPJ A, YGXX B, YGXX C, YHXX D, YHXX E, YHJS F, BMXX G, DICT H ");
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
	 * @Description: 获取评价信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1010140>
	 * @author ztz
	 * @date 2015年2月27日 下午4:45:11
	 * @update ztz at 2015年3月19日 上午10:15:59
	 */
	public List<Pojo1010140> getDataList(Pojo1010140 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1010140> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YGPJ_PJID, ");//评价ID
			strbuf.append("     A.YGPJ_PJR, ");//评价人ID
			strbuf.append("     CONCAT(D.YHXX_YHID, '-', B.YGXX_XM) AS PJRXM, ");//评价人姓名
			strbuf.append("     A.YGPJ_BPJR, ");//被评价人ID
			strbuf.append("     CONCAT(E.YHXX_YHID, '-', C.YGXX_XM) AS BPJRXM, ");//被评价人姓名
			strbuf.append("     G.BMXX_BMID, ");//部门ID
			strbuf.append("     G.BMXX_BMMC, ");//部门名称
			strbuf.append("     A.YGPJ_PJDJ, ");//评价等级
			strbuf.append("     H.DICT_ZDMC AS PJDJ, ");//评价等级
			strbuf.append("     A.YGPJ_PJNR, ");//评价内容
			strbuf.append("     A.YGPJ_PJSJ, ");//评价时间
			strbuf.append("     A.YGPJ_CJR, ");//创建人
			strbuf.append("     A.YGPJ_CJSJ, ");//创建时间
			strbuf.append("     A.YGPJ_GXR, ");//更新人
			strbuf.append("     A.YGPJ_GXSJ, ");//更新时间
			strbuf.append("     A.YGPJ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     YGPJ A, YGXX B, YGXX C, YHXX D, YHXX E, YHJS F, BMXX G, DICT H ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.YGPJ_PJR, A.YGPJ_PJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1010140>> rs = new BeanListHandler<Pojo1010140>(
					Pojo1010140.class);
			
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
	 * @date 2015年2月27日 下午4:45:22
	 * @update ztz at 2015年3月19日 上午10:16:08
	 */
	private String searchSql(Pojo1010140 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共方法 */
			strbuf.append(" AND A.YGPJ_PJR = B.YGXX_YGID ");
			strbuf.append(" AND B.YGXX_YGID = D.YHXX_YGID ");
			strbuf.append(" AND A.YGPJ_BPJR = C.YGXX_YGID ");
			strbuf.append(" AND C.YGXX_YGID = E.YHXX_YGID ");
			strbuf.append(" AND C.YGXX_JSID = F.YHJS_JSID ");
			strbuf.append(" AND F.YHJS_BMID = G.BMXX_BMID ");
			strbuf.append(" AND A.YGPJ_PJDJ = H.DICT_ZDDM ");
			strbuf.append(" AND A.YGPJ_SCBZ = '0'");
			strbuf.append(" AND B.YGXX_SCBZ = '0'");
			strbuf.append(" AND C.YGXX_SCBZ = '0'");
			strbuf.append(" AND D.YHXX_SCBZ = '0'");
			strbuf.append(" AND E.YHXX_SCBZ = '0'");
			strbuf.append(" AND F.YHJS_SCBZ = '0'");
			strbuf.append(" AND G.BMXX_SCBZ = '0'");
			strbuf.append(" AND H.DICT_SCBZ = '0'");
			strbuf.append(" AND H.DICT_ZDLX = 'PJLX'");
			/* 所在部门 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMID())) {
				if (!"000".equals(beanIn.getBMXX_BMID())) {
					strbuf.append(" AND G.BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("'");
				}
			}
			/* 被评价人 */
			if (MyStringUtils.isNotBlank(beanIn.getYGPJ_BPJR())) {
				if (!"000".equals(beanIn.getYGPJ_BPJR())) {
					strbuf.append(" AND A.YGPJ_BPJR = '").append(beanIn.getYGPJ_BPJR()).append("'");
				}
			}
			/* 评价等级 */
			if (MyStringUtils.isNotBlank(beanIn.getYGPJ_PJDJ())) {
				if (!"000".equals(beanIn.getYGPJ_PJDJ())) {
					strbuf.append(" AND A.YGPJ_PJDJ = '").append(beanIn.getYGPJ_PJDJ()).append("'");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**
	 * 
	 * @FunctionName: insertData
	 * @Description: 新增评价信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:45:44
	 * @update ztz at 2015年3月19日 上午10:16:15
	 */
	public boolean insertData(Pojo1010140 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		String pjId = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" INSERT INTO ");
			strbuf.append("     YGPJ ");
			strbuf.append(" ( ");
			strbuf.append("     YGPJ_PJID, ");//评价ID
			strbuf.append("     YGPJ_PJR, ");//评价人ID
			strbuf.append("     YGPJ_BPJR, ");//被评价人ID
			strbuf.append("     YGPJ_PJDJ, ");//评价等级
			strbuf.append("     YGPJ_PJNR, ");//评价内容
			strbuf.append("     YGPJ_PJSJ, ");//评价时间
			strbuf.append("     YGPJ_CJR, ");//创建人
			strbuf.append("     YGPJ_CJSJ, ");//创建时间
			strbuf.append("     YGPJ_GXR, ");//更新人
			strbuf.append("     YGPJ_GXSJ, ");//更新时间
			strbuf.append("     YGPJ_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" ) ");
			strbuf.append(" VALUES ");
			strbuf.append(" ( ");
			strbuf.append("     '"+pjId+"', ");//评价ID
			strbuf.append("     '"+beanIn.getYGPJ_PJR()+"', ");//评价人ID
			strbuf.append("     '"+beanIn.getYGPJ_BPJR()+"', ");//被评价人ID
			strbuf.append("     '"+beanIn.getYGPJ_PJDJ()+"', ");//评价等级
			strbuf.append("     '"+beanIn.getYGPJ_PJNR()+"', ");//评价内容
			strbuf.append("     DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'), ");//评价时间
			strbuf.append("     '"+beanIn.getYGPJ_CJR()+"', ");//创建人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf.append("     '"+beanIn.getYGPJ_CJR()+"', ");//更新人
			strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
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
	 * 
	 * @FunctionName: updateData
	 * @Description: 修改评价信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:45:53
	 * @update ztz at 2015年3月19日 上午10:16:22
	 */
	public boolean updateData(Pojo1010140 beanIn) throws Exception {
		boolean result = false;
		int count = 0;
		
		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YGPJ ");
			strbuf.append(" SET ");
			strbuf.append("     YGPJ_BPJR='").append(beanIn.getYGPJ_BPJR()).append("',");//被评价人ID
			strbuf.append("     YGPJ_PJDJ='").append(beanIn.getYGPJ_PJDJ()).append("',");//评价等级
			strbuf.append("     YGPJ_PJNR='").append(beanIn.getYGPJ_PJNR()).append("',");//评价内容
			strbuf.append("     YGPJ_PJSJ=DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),");//评价时间
			strbuf.append("     YGPJ_GXR='").append(beanIn.getYGPJ_GXR()).append("',");//更新人
			strbuf.append("     YGPJ_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     YGPJ_PJID='").append(beanIn.getYGPJ_PJID()).append("'");//评价ID
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
	 * 
	 * @FunctionName: deleteData
	 * @Description: 删除评价信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:46:03
	 */
	public boolean deleteData(Pojo1010140 beanIn) throws Exception {
		boolean result =false;
		int count = 0;

		try {
			db.openConnection();
			db.beginTran();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YGPJ ");
			strbuf.append(" SET ");
			strbuf.append("     YGPJ_GXR='").append(beanIn.getYGPJ_GXR()).append("',");//更新人
			strbuf.append("     YGPJ_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf.append("     YGPJ_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" WHERE ");
			strbuf.append("     YGPJ_PJID='").append(beanIn.getYGPJ_PJID()).append("'");//评价ID
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
	 * 
	 * @FunctionName: getBmid
	 * @Description: 获取当前登陆用户的部门ID
	 * @param ygid
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年3月19日 上午11:43:03
	 */
	public String getBmid(String ygid) throws Exception {
		String bmid = "";
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     C.BMXX_BMID ");//部门ID
			strbuf.append(" FROM ");
			strbuf.append("     YGXX A, YHJS B, BMXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGXX_JSID = B.YHJS_JSID ");
			strbuf.append(" AND B.YHJS_BMID = C.BMXX_BMID ");
			strbuf.append(" AND A.YGXX_SCBZ = '0'");
			strbuf.append(" AND B.YHJS_SCBZ = '0'");
			strbuf.append(" AND C.BMXX_SCBZ = '0'");
			strbuf.append(" AND A.YGXX_YGID = '").append(ygid).append("'");
			
			bmid = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return bmid;
	}
	/**
	 * 
	 * @FunctionName: getBmsl
	 * @Description: 获取当前登陆用户管理的部门数量
	 * @param ygid
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年3月19日 上午11:51:56
	 */
	public int getBmsl(String ygid) throws Exception {
		int count = 0;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(DISTINCT D.BMXX_BMID) ");
			strbuf.append(" FROM ");
			strbuf.append("     YGXX A, YGZW B, ZWXX C, BMXX D");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGXX_YGID = B.YGZW_YGID ");
			strbuf.append(" AND B.YGZW_ZWID = C.ZWXX_ZWID ");
			strbuf.append(" AND C.ZWXX_BMID = D.BMXX_BMID ");
			strbuf.append(" AND A.YGXX_SCBZ = '0'");
			strbuf.append(" AND B.YGZW_SCBZ = '0'");
			strbuf.append(" AND C.ZWXX_SCBZ = '0'");
			strbuf.append(" AND D.BMXX_SCBZ = '0'");
			strbuf.append(" AND A.YGXX_YGID = '").append(ygid).append("'");
			strbuf.append(" AND B.YGZW_ZWDJ > '0'");
			
			count = db.queryForInteger(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return count;
	}
}