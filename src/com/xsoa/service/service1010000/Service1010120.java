package com.xsoa.service.service1010000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010120;

public class Service1010120 extends BaseService {
	private DBManager db = null;

	public Service1010120() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取员工职务列表数据个数
	 * @param beanIn
	 * @param flag
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年2月27日 下午4:32:41
	 * @update ztz at 2015年3月10日 上午11:27:45
	 */
	public int getDataCount(Pojo1010120 beanIn, String flag) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.YGZW_UUID) ");//职务个数
			strbuf.append(" FROM ");
			strbuf.append("     YGZW A, YGXX C, ZWXX E, YHXX B, YHJS G, BMXX H ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGZW_YGID = C.YGXX_YGID ");
			strbuf.append(" AND C.YGXX_YGID = B.YHXX_YGID ");
			strbuf.append(" AND A.YGZW_ZWID = E.ZWXX_ZWID ");
			strbuf.append(" AND E.ZWXX_JSID = G.YHJS_JSID ");
			strbuf.append(" AND G.YHJS_BMID = H.BMXX_BMID ");
			strbuf.append(" AND A.YGZW_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND E.ZWXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND G.YHJS_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND H.BMXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			if ("LX".equals(flag)) {
				strbuf.append(" AND A.YGZW_YGID = '").append(beanIn.getYGZW_YGID()).append("'");
				strbuf.append(" AND A.YGZW_ZWLX = '1'");
			} else if ("NAME".equals(flag)) {
				strbuf.append(" AND A.YGZW_YGID = '").append(beanIn.getYGZW_YGID()).append("'");
				strbuf.append(" AND A.YGZW_ZWID = '").append(beanIn.getYGZW_ZWID()).append("'");
			} else {
				strbuf.append(this.searchSql(beanIn));
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
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取员工职务列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1010120>
	 * @author ztz
	 * @date 2015年2月27日 下午4:32:50
	 */
	public List<Pojo1010120> getDataList(Pojo1010120 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1010120> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YGZW_UUID, ");//员工职务ID
			strbuf.append("     A.YGZW_YGID, ");//员工ID
			strbuf.append("     CONCAT(B.YHXX_YHID, '-', C.YGXX_XM) AS YGXM, ");//员工姓名
			strbuf.append("     G.YHJS_JSID, ");//角色ID
			strbuf.append("     G.YHJS_JSMC, ");//角色名称
			strbuf.append("     H.BMXX_BMID, ");//部门ID
			strbuf.append("     H.BMXX_BMMC, ");//部门名称
			strbuf.append("     A.YGZW_ZWID, ");//职务ID
			strbuf.append("     E.ZWXX_ZWMC, ");//职务名称
			strbuf.append("     E.ZWXX_BMID, ");//部门ID
			strbuf.append("     A.YGZW_JXJB, ");//绩效级别(1级～9级(1级最低，9级最高))
			strbuf.append("     A.YGZW_ZWLX, ");//职务类型(1-专职 0-兼职)
			strbuf.append("     CASE WHEN A.YGZW_ZWLX = '1' THEN '专职' ELSE '兼职' END AS ZWLX, ");//职务类型
			strbuf.append("     A.YGZW_SJZG, ");//上级主管ID
			strbuf.append("     CONCAT(F.YHXX_YHID, '-', D.YGXX_XM) AS SJZGXM, ");//上级主管姓名
			strbuf.append("     A.YGZW_ZWDJ, ");//职务等级(董事级(9,8,7)经理级(6,5,4)管理级(3,2,1)职员0)
			strbuf.append("     A.YGZW_CJR, ");//创建人
			strbuf.append("     A.YGZW_CJSJ, ");//创建时间
			strbuf.append("     A.YGZW_GXR, ");//更新人
			strbuf.append("     A.YGZW_GXSJ, ");//更新时间
			strbuf.append("     A.YGZW_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf.append(" FROM ");
			strbuf.append("     YGZW A LEFT JOIN YGXX D ON A.YGZW_SJZG = D.YGXX_YGID LEFT JOIN YHXX F ON D.YGXX_YGID = F.YHXX_YGID, YGXX C, ZWXX E, YHXX B, YHJS G, BMXX H ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGZW_YGID = C.YGXX_YGID ");
			strbuf.append(" AND C.YGXX_YGID = B.YHXX_YGID ");
			strbuf.append(" AND A.YGZW_ZWID = E.ZWXX_ZWID ");
			strbuf.append(" AND E.ZWXX_JSID = G.YHJS_JSID ");
			strbuf.append(" AND G.YHJS_BMID = H.BMXX_BMID ");
			strbuf.append(" AND A.YGZW_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND E.ZWXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND G.YHJS_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND H.BMXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.YGZW_YGID, A.YGZW_CJSJ DESC ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1010120>> rs = new BeanListHandler<Pojo1010120>(
					Pojo1010120.class);
			
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
	 * @FunctionName: chkNameExist
	 * @Description: 判断职务名称是否存在
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年3月11日 下午3:52:05
	 */
	public String chkNameExist(Pojo1010120 beanIn) throws Exception {
		String uuid = "";

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YGZW_UUID ");//职务ID
			strbuf.append(" FROM ");
			strbuf.append("     YGZW A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGZW_SCBZ = '0'");
			strbuf.append(" AND A.YGZW_YGID = '").append(beanIn.getYGZW_YGID()).append("'");
			strbuf.append(" AND A.YGZW_ZWID = '").append(beanIn.getYGZW_ZWID()).append("'");
			
			uuid = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return uuid;
	}
	/**
	 * 
	 * @FunctionName: chkLxExist
	 * @Description: 判断职务类型是否存在
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年3月18日 下午4:51:20
	 */
	public String chkLxExist(Pojo1010120 beanIn) throws Exception {
		String uuid = "";
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YGZW_UUID ");//职务ID
			strbuf.append(" FROM ");
			strbuf.append("     YGZW A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.YGZW_SCBZ = '0'");
			strbuf.append(" AND A.YGZW_YGID = '").append(beanIn.getYGZW_YGID()).append("'");
			strbuf.append(" AND A.YGZW_ZWLX = '1'");
			
			uuid = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return uuid;
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
	 * @date 2015年2月27日 下午4:38:02
	 */
	private String searchSql(Pojo1010120 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 所在部门 */
			if (MyStringUtils.isNotBlank(beanIn.getBMXX_BMID())) {
				if (!"000".equals(beanIn.getBMXX_BMID())) {
					strbuf.append(" AND H.BMXX_BMID = '").append(beanIn.getBMXX_BMID()).append("'");
				}
			}
			/* 员工姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getYGZW_YGID())) {
				if (!"000".equals(beanIn.getYGZW_YGID())) {
					strbuf.append(" AND A.YGZW_YGID = '").append(beanIn.getYGZW_YGID()).append("'");
				}
			}
			/* 职务名称 */
			if (MyStringUtils.isNotBlank(beanIn.getYGZW_ZWID())) {
				if (!"000".equals(beanIn.getYGZW_ZWID())) {
					strbuf.append(" AND A.YGZW_ZWID = '").append(beanIn.getYGZW_ZWID()).append("'");
				}
			}
			/* 绩效级别 */
			if (MyStringUtils.isNotBlank(beanIn.getYGZW_JXJB())) {
				if (!"000".equals(beanIn.getYGZW_JXJB())) {
					strbuf.append(" AND A.YGZW_JXJB = '").append(beanIn.getYGZW_JXJB()).append("'");
				}
			}
			/* 职务类型 */
			if (MyStringUtils.isNotBlank(beanIn.getYGZW_ZWLX())) {
				if (!"000".equals(beanIn.getYGZW_ZWLX())) {
					strbuf.append(" AND A.YGZW_ZWLX = '").append(beanIn.getYGZW_ZWLX()).append("'");
				}
			}
			/* 职务等级 */
			if (MyStringUtils.isNotBlank(beanIn.getYGZW_ZWDJ())) {
				if (!"000".equals(beanIn.getYGZW_ZWDJ())) {
					strbuf.append(" AND A.YGZW_ZWDJ = '").append(beanIn.getYGZW_ZWDJ()).append("'");
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
	 * @Description: 新增员工职务
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:33:14
	 */
	public boolean insertData(Pojo1010120 beanIn) throws Exception {
		boolean result = false;
		int count_YGZW = 0;
		int count_ZWDD = 0;
		int count_YGXX = 0;
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		String zwddid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数

		try {
			db.openConnection();
			db.beginTran();
			/* 向YGZW表插入数据Start */
			StringBuffer strbuf_YGZW = new StringBuffer();
			strbuf_YGZW.append(" INSERT INTO ");
			strbuf_YGZW.append("     YGZW ");
			strbuf_YGZW.append(" ( ");
			strbuf_YGZW.append("     YGZW_UUID, ");//员工职务ID
			strbuf_YGZW.append("     YGZW_YGID, ");//员工ID
			strbuf_YGZW.append("     YGZW_ZWID, ");//职务ID
			strbuf_YGZW.append("     YGZW_JXJB, ");//绩效级别(1级～9级(1级最低，9级最高))
			strbuf_YGZW.append("     YGZW_ZWLX, ");//职务类型(1-专职 0-兼职)
			strbuf_YGZW.append("     YGZW_SJZG, ");//上级主管ID
			strbuf_YGZW.append("     YGZW_ZWDJ, ");//职务等级(董事级(9,8,7)经理级(6,5,4)管理级(3,2,1)职员0)
			strbuf_YGZW.append("     YGZW_CJR, ");//创建人
			strbuf_YGZW.append("     YGZW_CJSJ, ");//创建时间
			strbuf_YGZW.append("     YGZW_GXR, ");//更新人
			strbuf_YGZW.append("     YGZW_GXSJ, ");//更新时间
			strbuf_YGZW.append("     YGZW_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_YGZW.append(" ) ");
			strbuf_YGZW.append(" VALUES ");
			strbuf_YGZW.append(" ( ");
			strbuf_YGZW.append("     '"+uuid+"', ");//员工职务ID
			strbuf_YGZW.append("     '"+beanIn.getYGZW_YGID()+"', ");//员工ID
			strbuf_YGZW.append("     '"+beanIn.getYGZW_ZWID()+"', ");//职务ID
			strbuf_YGZW.append("     '"+beanIn.getYGZW_JXJB()+"', ");//绩效级别(1级～9级(1级最低，9级最高))
			strbuf_YGZW.append("     '"+beanIn.getYGZW_ZWLX()+"', ");//职务类型(1-专职 0-兼职)
			strbuf_YGZW.append("     '"+beanIn.getYGZW_SJZG()+"', ");//上级主管ID
			strbuf_YGZW.append("     '"+beanIn.getYGZW_ZWDJ()+"', ");//职务等级(董事级(9,8,7)经理级(6,5,4)管理级(3,2,1)职员0)
			strbuf_YGZW.append("     '"+beanIn.getYGZW_CJR()+"', ");//创建人
			strbuf_YGZW.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_YGZW.append("     '"+beanIn.getYGZW_CJR()+"', ");//更新人
			strbuf_YGZW.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_YGZW.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_YGZW.append(" ) ");
			count_YGZW = db.executeSQL(strbuf_YGZW);
			/* 向YGZW表插入数据End */
			/* 向ZWDD表插入数据Start */
			String xbmid = this.getBmId(beanIn.getYGZW_ZWID());//新部门ID
			StringBuffer strbuf_ZWDD = new StringBuffer();
			strbuf_ZWDD.append(" INSERT INTO ");
			strbuf_ZWDD.append("     ZWDD ");
			strbuf_ZWDD.append(" ( ");
			strbuf_ZWDD.append("     ZWDD_UUID, ");//职务调动ID
			strbuf_ZWDD.append("     ZWDD_YGID, ");//员工ID
			strbuf_ZWDD.append("     ZWDD_YBMID, ");//原部门ID
			strbuf_ZWDD.append("     ZWDD_YZWID, ");//原职务ID
			strbuf_ZWDD.append("     ZWDD_YZWDJ, ");//原职务等级
			strbuf_ZWDD.append("     ZWDD_XBMID, ");//新部门ID
			strbuf_ZWDD.append("     ZWDD_XZWID, ");//新职务ID
			strbuf_ZWDD.append("     ZWDD_XZWDJ, ");//新职务等级
			strbuf_ZWDD.append("     ZWDD_BZXX, ");//备注信息
			strbuf_ZWDD.append("     ZWDD_CJR, ");//创建人
			strbuf_ZWDD.append("     ZWDD_CJSJ, ");//创建时间
			strbuf_ZWDD.append("     ZWDD_GXR, ");//更新人
			strbuf_ZWDD.append("     ZWDD_GXSJ, ");//更新时间
			strbuf_ZWDD.append("     ZWDD_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_ZWDD.append(" ) ");
			strbuf_ZWDD.append(" VALUES ");
			strbuf_ZWDD.append(" ( ");
			strbuf_ZWDD.append("     '"+zwddid+"', ");//职务调动ID
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_YGID()+"', ");//员工ID
			strbuf_ZWDD.append("     '', ");//原部门ID
			strbuf_ZWDD.append("     '', ");//原职务ID
			strbuf_ZWDD.append("     '', ");//原职务等级
			strbuf_ZWDD.append("     '"+xbmid+"', ");//新部门ID
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_ZWID()+"', ");//新职务ID
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_ZWDJ()+"', ");//新职务等级
			strbuf_ZWDD.append("     '新增职务操作', ");//备注信息
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_CJR()+"', ");//创建人
			strbuf_ZWDD.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_CJR()+"', ");//更新人
			strbuf_ZWDD.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_ZWDD.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_ZWDD.append(" ) ");
			count_ZWDD = db.executeSQL(strbuf_ZWDD);
			/* 向ZWDD表插入数据End */
			if ("1".equals(beanIn.getYGZW_ZWLX())) {
				/* 更新YGXX表数据Start */
				StringBuffer strbuf_YGXX = new StringBuffer();
				strbuf_YGXX.append(" UPDATE ");
				strbuf_YGXX.append("     YGXX ");
				strbuf_YGXX.append(" SET ");
				strbuf_YGXX.append("     YGXX_JSID='").append(beanIn.getYHJS_JSID()).append("',");//角色ID
				strbuf_YGXX.append("     YGXX_GXR='").append(beanIn.getYGZW_CJR()).append("',");//更新人
				strbuf_YGXX.append("     YGXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
				strbuf_YGXX.append(" WHERE ");
				strbuf_YGXX.append("     YGXX_YGID='").append(beanIn.getYGZW_YGID()).append("'");//员工ID
				count_YGXX = db.executeSQL(strbuf_YGXX);
				/* 更新YGXX表数据End */
			}
			if ("1".equals(beanIn.getYGZW_ZWLX())) {
				if (count_YGZW > 0 && count_ZWDD > 0 && count_YGZW == count_ZWDD && count_YGXX > 0) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
			} else {
				if (count_YGZW > 0 && count_ZWDD > 0 && count_YGZW == count_ZWDD) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
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
	 * @FunctionName: getBmId
	 * @Description: 根据职务ID获取部门ID
	 * @param zwId
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ztz
	 * @date 2015年2月11日 上午10:25:23
	 */
	private String getBmId(String zwId) throws Exception {
		String bmId = "";
		
		try {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("    ZWXX_BMID ");//部门ID
			strbuf.append(" FROM ");
			strbuf.append("    ZWXX ");
			strbuf.append(" WHERE 1=1");
			strbuf.append(" AND ZWXX_SCBZ = '0'");
			strbuf.append(" AND ZWXX_ZWID = '").append(zwId).append("'");
			
			bmId = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return bmId;
	}
	/**
	 * 
	 * @FunctionName: updateData
	 * @Description: 修改员工职务
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:33:26
	 */
	public boolean updateData(Pojo1010120 beanIn) throws Exception {
		boolean result = false;
		int count_YGZW = 0;
		int count_ZWDD = 0;
		int count_YGXX = 0;
		String zwddid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		
		try {
			db.openConnection();
			db.beginTran();
			/* 更新YGZW表数据Start */
			StringBuffer strbuf_YGZW = new StringBuffer();
			strbuf_YGZW.append(" UPDATE ");
			strbuf_YGZW.append("     YGZW ");
			strbuf_YGZW.append(" SET ");
			strbuf_YGZW.append("     YGZW_YGID='").append(beanIn.getYGZW_YGID()).append("',");//员工ID
			strbuf_YGZW.append("     YGZW_ZWID='").append(beanIn.getYGZW_ZWID()).append("',");//职务ID
			strbuf_YGZW.append("     YGZW_JXJB='").append(beanIn.getYGZW_JXJB()).append("',");//绩效级别(1级～9级(1级最低，9级最高))
			strbuf_YGZW.append("     YGZW_ZWLX='").append(beanIn.getYGZW_ZWLX()).append("',");//职务类型(1-专职 0-兼职)
			strbuf_YGZW.append("     YGZW_SJZG='").append(beanIn.getYGZW_SJZG()).append("',");//上级主管ID
			strbuf_YGZW.append("     YGZW_ZWDJ='").append(beanIn.getYGZW_ZWDJ()).append("',");//职务等级(董事级(9,8,7)经理级(6,5,4)管理级(3,2,1)职员0)
			strbuf_YGZW.append("     YGZW_GXR='").append(beanIn.getYGZW_GXR()).append("',");//更新人
			strbuf_YGZW.append("     YGZW_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf_YGZW.append(" WHERE ");
			strbuf_YGZW.append("     YGZW_UUID='").append(beanIn.getYGZW_UUID()).append("'");//员工职务ID
			count_YGZW = db.executeSQL(strbuf_YGZW);
			/* 更新YGZW表数据End */
			/* 向ZWDD表插入数据Start */
			String xbmid = this.getBmId(beanIn.getYGZW_ZWID());//新部门ID
			StringBuffer strbuf_ZWDD = new StringBuffer();
			strbuf_ZWDD.append(" INSERT INTO ");
			strbuf_ZWDD.append("     ZWDD ");
			strbuf_ZWDD.append(" ( ");
			strbuf_ZWDD.append("     ZWDD_UUID, ");//职务调动ID
			strbuf_ZWDD.append("     ZWDD_YGID, ");//员工ID
			strbuf_ZWDD.append("     ZWDD_YBMID, ");//原部门ID
			strbuf_ZWDD.append("     ZWDD_YZWID, ");//原职务ID
			strbuf_ZWDD.append("     ZWDD_YZWDJ, ");//原职务等级
			strbuf_ZWDD.append("     ZWDD_XBMID, ");//新部门ID
			strbuf_ZWDD.append("     ZWDD_XZWID, ");//新职务ID
			strbuf_ZWDD.append("     ZWDD_XZWDJ, ");//新职务等级
			strbuf_ZWDD.append("     ZWDD_BZXX, ");//备注信息
			strbuf_ZWDD.append("     ZWDD_CJR, ");//创建人
			strbuf_ZWDD.append("     ZWDD_CJSJ, ");//创建时间
			strbuf_ZWDD.append("     ZWDD_GXR, ");//更新人
			strbuf_ZWDD.append("     ZWDD_GXSJ, ");//更新时间
			strbuf_ZWDD.append("     ZWDD_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_ZWDD.append(" ) ");
			strbuf_ZWDD.append(" VALUES ");
			strbuf_ZWDD.append(" ( ");
			strbuf_ZWDD.append("     '"+zwddid+"', ");//职务调动ID
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_YGID()+"', ");//员工ID
			strbuf_ZWDD.append("     '"+beanIn.getZWDD_YBMID()+"', ");//原部门ID
			strbuf_ZWDD.append("     '"+beanIn.getZWDD_YZWID()+"', ");//原职务ID
			strbuf_ZWDD.append("     '"+beanIn.getZWDD_YZWDJ()+"', ");//原职务等级
			strbuf_ZWDD.append("     '"+xbmid+"', ");//新部门ID
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_ZWID()+"', ");//新职务ID
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_ZWDJ()+"', ");//新职务等级
			strbuf_ZWDD.append("     '修改职务操作', ");//备注信息
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_GXR()+"', ");//创建人
			strbuf_ZWDD.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_GXR()+"', ");//更新人
			strbuf_ZWDD.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_ZWDD.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_ZWDD.append(" ) ");
			count_ZWDD = db.executeSQL(strbuf_ZWDD);
			/* 向ZWDD表插入数据End */
			if ("1".equals(beanIn.getYGZW_ZWLX())) {
				/* 更新YGXX表数据Start */
				StringBuffer strbuf_YGXX = new StringBuffer();
				strbuf_YGXX.append(" UPDATE ");
				strbuf_YGXX.append("     YGXX ");
				strbuf_YGXX.append(" SET ");
				strbuf_YGXX.append("     YGXX_JSID='").append(beanIn.getYHJS_JSID()).append("',");//角色ID
				strbuf_YGXX.append("     YGXX_GXR='").append(beanIn.getYGZW_CJR()).append("',");//更新人
				strbuf_YGXX.append("     YGXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
				strbuf_YGXX.append(" WHERE ");
				strbuf_YGXX.append("     YGXX_YGID='").append(beanIn.getYGZW_YGID()).append("'");//员工ID
				count_YGXX = db.executeSQL(strbuf_YGXX);
				/* 更新YGXX表数据End */
			}
			if ("1".equals(beanIn.getYGZW_ZWLX())) {
				if (count_YGZW > 0 && count_ZWDD > 0 && count_YGZW == count_ZWDD && count_YGXX > 0) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
			} else {
				if (count_YGZW > 0 && count_ZWDD > 0 && count_YGZW == count_ZWDD) {
					db.commit();
					result = true;
				} else {
					db.rollback();
				}
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
	 * @Description: 删除员工职务
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月27日 下午4:33:37
	 */
	public boolean deleteData(Pojo1010120 beanIn) throws Exception {
		boolean result =false;
		int count_YGZW = 0;
		int count_ZWDD = 0;
		String zwddid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数

		try {
			db.openConnection();
			db.beginTran();
			/* 删除YGZW表数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     YGZW ");
			strbuf.append(" SET ");
			strbuf.append("     YGZW_SCBZ='1',");//删除标志（0：未删除，1：已删除）
			strbuf.append("     YGZW_GXR='").append(beanIn.getYGZW_GXR()).append("',");//更新人
			strbuf.append("     YGZW_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     YGZW_UUID='").append(beanIn.getYGZW_UUID()).append("'");//员工职务ID
			count_YGZW = db.executeSQL(strbuf);
			/* 删除YGZW表数据End */
			/* 向ZWDD表插入数据Start */
			StringBuffer strbuf_ZWDD = new StringBuffer();
			strbuf_ZWDD.append(" INSERT INTO ");
			strbuf_ZWDD.append("     ZWDD ");
			strbuf_ZWDD.append(" ( ");
			strbuf_ZWDD.append("     ZWDD_UUID, ");//职务调动ID
			strbuf_ZWDD.append("     ZWDD_YGID, ");//员工ID
			strbuf_ZWDD.append("     ZWDD_YBMID, ");//原部门ID
			strbuf_ZWDD.append("     ZWDD_YZWID, ");//原职务ID
			strbuf_ZWDD.append("     ZWDD_YZWDJ, ");//原职务等级
			strbuf_ZWDD.append("     ZWDD_XBMID, ");//新部门ID
			strbuf_ZWDD.append("     ZWDD_XZWID, ");//新职务ID
			strbuf_ZWDD.append("     ZWDD_XZWDJ, ");//新职务等级
			strbuf_ZWDD.append("     ZWDD_BZXX, ");//备注信息
			strbuf_ZWDD.append("     ZWDD_CJR, ");//创建人
			strbuf_ZWDD.append("     ZWDD_CJSJ, ");//创建时间
			strbuf_ZWDD.append("     ZWDD_GXR, ");//更新人
			strbuf_ZWDD.append("     ZWDD_GXSJ, ");//更新时间
			strbuf_ZWDD.append("     ZWDD_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_ZWDD.append(" ) ");
			strbuf_ZWDD.append(" VALUES ");
			strbuf_ZWDD.append(" ( ");
			strbuf_ZWDD.append("     '"+zwddid+"', ");//职务调动ID
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_YGID()+"', ");//员工ID
			strbuf_ZWDD.append("     '"+beanIn.getZWDD_YBMID()+"', ");//原部门ID
			strbuf_ZWDD.append("     '"+beanIn.getZWDD_YZWID()+"', ");//原职务ID
			strbuf_ZWDD.append("     '"+beanIn.getZWDD_YZWDJ()+"', ");//原职务等级
			strbuf_ZWDD.append("     '', ");//新部门ID
			strbuf_ZWDD.append("     '', ");//新职务ID
			strbuf_ZWDD.append("     '', ");//新职务等级
			strbuf_ZWDD.append("     '删除职务操作', ");//备注信息
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_GXR()+"', ");//创建人
			strbuf_ZWDD.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_ZWDD.append("     '"+beanIn.getYGZW_GXR()+"', ");//更新人
			strbuf_ZWDD.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_ZWDD.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_ZWDD.append(" ) ");
			count_ZWDD = db.executeSQL(strbuf_ZWDD);
			/* 向ZWDD表插入数据End */
			if (count_YGZW > 0 && count_ZWDD > 0 && count_YGZW == count_ZWDD) {
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