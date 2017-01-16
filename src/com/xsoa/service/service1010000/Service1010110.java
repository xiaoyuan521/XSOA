package com.xsoa.service.service1010000;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010110;

public class Service1010110 extends BaseService {
	private DBManager db = null;

	public Service1010110() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getYgInfoCount
	 * @Description: 获取员工信息记录个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年2月4日 上午9:45:51
	 * @update ztz at 2015年2月9日 下午1:46:00
	 */
	public int getYgInfoCount(Pojo1010110 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.YGXX_YGID) ");//员工信息个数
			strbuf.append(" FROM ");
			strbuf.append("     YGXX A LEFT JOIN YHXX F ON A.YGXX_YGID = F.YHXX_YGID AND F.YHXX_SCBZ = '0' ");
			strbuf.append("                   LEFT JOIN YGZW B ON A.YGXX_YGID = B.YGZW_YGID AND B.YGZW_ZWLX = '1' AND B.YGZW_SCBZ = '0' ");
			strbuf.append("                   LEFT JOIN ZWXX C ON B.YGZW_ZWID = C.ZWXX_ZWID AND C.ZWXX_SCBZ = '0', ");
			strbuf.append("     YHJS D, BMXX E");
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
	 * @FunctionName: getYhxxCount
	 * @Description: 获取用户信息记录个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年3月7日 下午3:27:15
	 */
	public int getYhxxCount(Pojo1010110 beanIn) throws Exception {
		int result = 0;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.YHXX_YHID) ");//用户信息个数
			strbuf.append(" FROM ");
			strbuf.append("     YHXX A ");
			strbuf.append(" WHERE 1=1");
			strbuf.append(" AND A.YHXX_SCBZ = '0'");
			strbuf.append(" AND A.YHXX_YHID = '").append(beanIn.getYHXX_YHID()).append("'");
			
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
	 * @FunctionName: getYgzwCount
	 * @Description: 获取员工职务记录个数
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年3月7日 下午3:44:37
	 */
	public int getYgzwCount(Pojo1010110 beanIn) throws Exception {
		int result = 0;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.YGZW_UUID) ");//员工职务个数
			strbuf.append(" FROM ");
			strbuf.append("     YGZW A, YGXX B ");
			strbuf.append(" WHERE 1=1");
			strbuf.append(" AND A.YGZW_YGID = B.YGXX_YGID ");
			strbuf.append(" AND A.YGZW_SCBZ = '0'");
			strbuf.append(" AND B.YGXX_SCBZ = '0'");
			strbuf.append(" AND A.YGZW_ZWID = '").append(beanIn.getZWXX_ZWID()).append("'");
			strbuf.append(" AND B.YGXX_XM LIKE '%").append(beanIn.getYGXX_XM()).append("%'");
			strbuf.append(" AND B.YGXX_SFZHM = '").append(beanIn.getYGXX_SFZHM()).append("'");
			
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
	 * @FunctionName: getYgInfoList
	 * @Description: 获取员工信息列表数据明细
	 * @param beanIn
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo1010110>
	 * @author ztz
	 * @date 2015年2月4日 上午9:46:11
	 * @update ztz at 2015年2月9日 下午1:46:00
	 */
	public List<Pojo1010110> getYgInfoList(Pojo1010110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1010110> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YGXX_YGID, ");//员工ID
			strbuf.append("     A.YGXX_GH, ");//工号
			strbuf.append("     A.YGXX_XM, ");//姓名
			strbuf.append("     A.YGXX_CSRQ, ");//出生日期
			strbuf.append("     A.YGXX_XB, ");//性别（0：男 1：女）
			strbuf.append("     CASE WHEN A.YGXX_XB = '0' THEN '男' WHEN A.YGXX_XB = '1' THEN '女' ELSE '' END AS XB, ");//性别
			strbuf.append("     A.YGXX_NL, ");//年龄
			strbuf.append("     CASE WHEN A.YGXX_XX = '000' THEN '' ELSE A.YGXX_XX END AS YGXX_XX, ");//血型
			strbuf.append("     CASE WHEN A.YGXX_HYZK = '000' THEN '' ELSE A.YGXX_HYZK END AS YGXX_HYZK, ");//婚姻状况（未婚/已婚）
			strbuf.append("     A.YGXX_MZ, ");//民族
			strbuf.append("     A.YGXX_SFZHM, ");//身份证号码
			strbuf.append("     CASE WHEN A.YGXX_HKLX = '000' THEN '' ELSE A.YGXX_HKLX END AS YGXX_HKLX, ");//户口类型（城镇/农村）
			strbuf.append("     A.YGXX_HKSZD, ");//户口所在地
			strbuf.append("     CASE WHEN A.YGXX_ZZMM = '000' THEN '' ELSE A.YGXX_ZZMM END AS YGXX_ZZMM, ");//政治面貌（共青团员/党员/群众）
			strbuf.append("     A.YGXX_BYXX, ");//毕业学校
			strbuf.append("     A.YGXX_BYSJ, ");//毕业时间
			strbuf.append("     A.YGXX_SSZY, ");//所学专业
			strbuf.append("     A.YGXX_ZGXL, ");//最高学历
			strbuf.append("     A.YGXX_BGDH, ");//办公电话
			strbuf.append("     A.YGXX_YDDH, ");//移动电话
			strbuf.append("     A.YGXX_GSYJ, ");//公司邮件
			strbuf.append("     A.YGXX_SRYJ, ");//私人邮件
			strbuf.append("     A.YGXX_ZZ, ");//住址
			strbuf.append("     A.YGXX_BZ, ");//备注
			strbuf.append("     A.YGXX_JJLXR, ");//紧急联系人
			strbuf.append("     A.YGXX_JJLXDH, ");//紧急联系电话
			strbuf.append("     A.YGXX_WYLX, ");//外语类型
			strbuf.append("     A.YGXX_WYJB, ");//外语级别
			strbuf.append("     A.YGXX_KQJKH, ");//考勤机卡号
			strbuf.append("     A.YGXX_RZSJ, ");//入职时间
			strbuf.append("     A.YGXX_ZZSJ, ");//转正时间
			strbuf.append("     A.YGXX_LZSJ, ");//离职时间
			strbuf.append("     A.YGXX_ZZSM, ");//转正说明
			strbuf.append("     A.YGXX_LZSM, ");//离职说明
			strbuf.append("     A.YGXX_YGZT, ");//员工状态（0：试用 1：正式 2：离职）
			strbuf.append("     A.YGXX_JSID, ");//角色ID
			strbuf.append("     CASE WHEN A.YGXX_YGZT = '0' THEN '试用' WHEN A.YGXX_YGZT = '1' THEN '正式' ELSE '离职' END AS YGZT, ");//员工状态
			strbuf.append("     A.YGXX_CJR, ");//创建人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.YGXX_CJSJ, '%Y%m%d'), '%Y-%m-%d') AS YGXX_CJSJ, ");//创建时间
			strbuf.append("     A.YGXX_GXR, ");//更新人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.YGXX_GXSJ, '%Y%m%d'), '%Y-%m-%d') AS YGXX_GXSJ, ");//更新时间
			strbuf.append("     A.YGXX_SCBZ, ");//删除标志（0：未删除，1：已删除）
			strbuf.append("     E.BMXX_BMID AS BMID, ");//部门ID
			strbuf.append("     E.BMXX_BMMC AS BMMC, ");//部门名称
			strbuf.append("     D.YHJS_JSMC AS JSMC, ");//角色名称
			strbuf.append("     C.ZWXX_ZWID, ");//职务ID
			strbuf.append("     C.ZWXX_ZWMC, ");//职务名称
			strbuf.append("     B.YGZW_UUID, ");//员工职务ID
			strbuf.append("     B.YGZW_JXJB, ");//绩效级别(1级～9级(1级最低，9级最高))
			strbuf.append("     B.YGZW_ZWLX, ");//职务类型(1-专职 0-兼职)
			strbuf.append("     CASE WHEN B.YGZW_ZWLX = '1' THEN '专职' ELSE '兼职' END AS ZWLX, ");//职务类型
			strbuf.append("     B.YGZW_SJZG, ");//上级主管ID
			strbuf.append("     B.YGZW_ZWDJ, ");//职务等级（董事级（9,8,7）经理级（6,5,4）管理级（3,2,1）职员0）
			strbuf.append("     F.YHXX_YHID ");//用户ID
			strbuf.append(" FROM ");
			strbuf.append("     YGXX A LEFT JOIN YHXX F ON A.YGXX_YGID = F.YHXX_YGID AND F.YHXX_SCBZ = '0' ");
			strbuf.append("                   LEFT JOIN YGZW B ON A.YGXX_YGID = B.YGZW_YGID AND B.YGZW_ZWLX = '1' AND B.YGZW_SCBZ = '0' ");
			strbuf.append("                   LEFT JOIN ZWXX C ON B.YGZW_ZWID = C.ZWXX_ZWID AND C.ZWXX_SCBZ = '0', ");
			strbuf.append("     YHJS D, BMXX E");
			strbuf.append(" WHERE 1=1 ");	
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.YGXX_CJSJ ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1010110>> rs = new BeanListHandler<Pojo1010110>(
					Pojo1010110.class);
			
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
	 * @date 2015年2月4日 上午9:46:41
	 * @update ztz at 2015年2月9日 下午1:46:00
	 */
	private String searchSql(Pojo1010110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 公共部分 */
			strbuf.append(" AND A.YGXX_JSID = D.YHJS_JSID ");
			strbuf.append(" AND D.YHJS_BMID = E.BMXX_BMID ");
			strbuf.append(" AND A.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND D.YHJS_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND E.BMXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			/* 账号 */
			if (MyStringUtils.isNotBlank(beanIn.getYHXX_YHID())) {
				strbuf.append(" AND F.YHXX_YHID LIKE '%").append(beanIn.getYHXX_YHID()).append("%'");
			}
			/* 所属部门 */
			if (MyStringUtils.isNotBlank(beanIn.getBMID())) {
				if (!"000".equals(beanIn.getBMID())) {
					strbuf.append(" AND D.YHJS_BMID = '").append(beanIn.getBMID()).append("'");
				}
			}
			/* 角色 */
			if (MyStringUtils.isNotBlank(beanIn.getYGXX_JSID())) {
				if (!"000".equals(beanIn.getYGXX_JSID())) {
					strbuf.append(" AND A.YGXX_JSID = '").append(beanIn.getYGXX_JSID()).append("'");
				}
			}
			/* 职务 */
			if (MyStringUtils.isNotBlank(beanIn.getZWXX_ZWID())) {
				if (!"000".equals(beanIn.getZWXX_ZWID())) {
					strbuf.append(" AND C.ZWXX_ZWID = '").append(beanIn.getZWXX_ZWID()).append("'");
				}
			}
			/* 姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getYGXX_XM())) {
				strbuf.append(" AND A.YGXX_XM LIKE '%").append(beanIn.getYGXX_XM()).append("%'");
			}
			/* 身份证号码 */
			if (MyStringUtils.isNotBlank(beanIn.getYGXX_SFZHM())) {
				strbuf.append(" AND A.YGXX_SFZHM = '").append(beanIn.getYGXX_SFZHM()).append("'");
			}
			/* 员工状态 */
			if (MyStringUtils.isNotBlank(beanIn.getYGXX_YGZT())) {
				if (!"000".equals(beanIn.getYGXX_YGZT())) {
					strbuf.append(" AND A.YGXX_YGZT = '").append(beanIn.getYGXX_YGZT()).append("'");
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
	 * @FunctionName: insertYgInfo
	 * @Description: 新增员工信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月9日 下午1:46:56
	 */
	public boolean insertYgInfo(Pojo1010110 beanIn) throws Exception {
		boolean result = false;
		int count_YGXX = 0;
		int count_YHXX = 0;
		int count_YGZW = 0;
		String ygxxid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		String ygzwid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数

		try {
			db.openConnection();
			db.beginTran();
			/* 向YGXX表插入数据Start */
			StringBuffer strbuf_YGXX = new StringBuffer();
			strbuf_YGXX.append(" INSERT INTO ");
			strbuf_YGXX.append("     YGXX ");
			strbuf_YGXX.append(" ( ");
			strbuf_YGXX.append("     YGXX_YGID, ");//员工ID
			strbuf_YGXX.append("     YGXX_GH, ");//工号
			strbuf_YGXX.append("     YGXX_XM, ");//姓名
			strbuf_YGXX.append("     YGXX_CSRQ, ");//出生日期
			strbuf_YGXX.append("     YGXX_XB, ");//性别（0：男 1：女）
			strbuf_YGXX.append("     YGXX_NL, ");//年龄
			strbuf_YGXX.append("     YGXX_XX, ");//血型
			strbuf_YGXX.append("     YGXX_HYZK, ");//婚姻状况（未婚/已婚）
			strbuf_YGXX.append("     YGXX_MZ, ");//民族
			strbuf_YGXX.append("     YGXX_SFZHM, ");//身份证号码
			strbuf_YGXX.append("     YGXX_HKLX, ");//户口类型（城镇/农村）
			strbuf_YGXX.append("     YGXX_HKSZD, ");//户口所在地
			strbuf_YGXX.append("     YGXX_ZZMM, ");//政治面貌（共青团员/党员/群众）
			strbuf_YGXX.append("     YGXX_BYXX, ");//毕业学校
			strbuf_YGXX.append("     YGXX_BYSJ, ");//毕业时间
			strbuf_YGXX.append("     YGXX_SSZY, ");//所学专业
			strbuf_YGXX.append("     YGXX_ZGXL, ");//最高学历
			strbuf_YGXX.append("     YGXX_BGDH, ");//办公电话
			strbuf_YGXX.append("     YGXX_YDDH, ");//移动电话
			strbuf_YGXX.append("     YGXX_GSYJ, ");//公司邮件
			strbuf_YGXX.append("     YGXX_SRYJ, ");//私人邮件
			strbuf_YGXX.append("     YGXX_ZZ, ");//住址
			strbuf_YGXX.append("     YGXX_BZ, ");//备注
			strbuf_YGXX.append("     YGXX_JJLXR, ");//紧急联系人
			strbuf_YGXX.append("     YGXX_JJLXDH, ");//紧急联系电话
			strbuf_YGXX.append("     YGXX_WYLX, ");//外语类型
			strbuf_YGXX.append("     YGXX_WYJB, ");//外语级别
			strbuf_YGXX.append("     YGXX_KQJKH, ");//考勤机卡号
			strbuf_YGXX.append("     YGXX_RZSJ, ");//入职时间
			strbuf_YGXX.append("     YGXX_ZZSJ, ");//转正时间
			strbuf_YGXX.append("     YGXX_LZSJ, ");//离职时间
			strbuf_YGXX.append("     YGXX_ZZSM, ");//转正说明
			strbuf_YGXX.append("     YGXX_LZSM, ");//离职说明
			strbuf_YGXX.append("     YGXX_YGZT, ");//员工状态（0：试用 1：正式 2：离职）
			strbuf_YGXX.append("     YGXX_JSID, ");//角色ID
			strbuf_YGXX.append("     YGXX_CJR, ");//创建人
			strbuf_YGXX.append("     YGXX_CJSJ, ");//创建时间
			strbuf_YGXX.append("     YGXX_GXR, ");//更新人
			strbuf_YGXX.append("     YGXX_GXSJ, ");//更新时间
			strbuf_YGXX.append("     YGXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_YGXX.append(" ) ");
			strbuf_YGXX.append(" VALUES ");
			strbuf_YGXX.append(" ( ");
			strbuf_YGXX.append("     '"+ygxxid+"', ");//员工ID
			strbuf_YGXX.append("     '"+beanIn.getYGXX_GH()+"', ");//工号
			strbuf_YGXX.append("     '"+beanIn.getYGXX_XM()+"', ");//姓名
			strbuf_YGXX.append("     '"+beanIn.getYGXX_CSRQ()+"', ");//出生日期
			strbuf_YGXX.append("     '"+beanIn.getYGXX_XB()+"', ");//性别（0：男 1：女）
			strbuf_YGXX.append("     '"+beanIn.getYGXX_NL()+"', ");//年龄
			strbuf_YGXX.append("     '"+beanIn.getYGXX_XX()+"', ");//血型
			strbuf_YGXX.append("     '"+beanIn.getYGXX_HYZK()+"', ");//婚姻状况（未婚/已婚）
			strbuf_YGXX.append("     '"+beanIn.getYGXX_MZ()+"', ");//民族
			strbuf_YGXX.append("     '"+beanIn.getYGXX_SFZHM()+"', ");//身份证号码
			strbuf_YGXX.append("     '"+beanIn.getYGXX_HKLX()+"', ");//户口类型（城镇/农村）
			strbuf_YGXX.append("     '"+beanIn.getYGXX_HKSZD()+"', ");//户口所在地
			strbuf_YGXX.append("     '"+beanIn.getYGXX_ZZMM()+"', ");//政治面貌（共青团员/党员/群众）
			strbuf_YGXX.append("     '"+beanIn.getYGXX_BYXX()+"', ");//毕业学校
			strbuf_YGXX.append("     '"+beanIn.getYGXX_BYSJ()+"', ");//毕业时间
			strbuf_YGXX.append("     '"+beanIn.getYGXX_SSZY()+"', ");//所学专业
			strbuf_YGXX.append("     '"+beanIn.getYGXX_ZGXL()+"', ");//最高学历
			strbuf_YGXX.append("     '"+beanIn.getYGXX_BGDH()+"', ");//办公电话
			strbuf_YGXX.append("     '"+beanIn.getYGXX_YDDH()+"', ");//移动电话
			strbuf_YGXX.append("     '"+beanIn.getYGXX_GSYJ()+"', ");//公司邮件
			strbuf_YGXX.append("     '"+beanIn.getYGXX_SRYJ()+"', ");//私人邮件
			strbuf_YGXX.append("     '"+beanIn.getYGXX_ZZ()+"', ");//住址
			strbuf_YGXX.append("     '"+beanIn.getYGXX_BZ()+"', ");//备注
			strbuf_YGXX.append("     '"+beanIn.getYGXX_JJLXR()+"', ");//紧急联系人
			strbuf_YGXX.append("     '"+beanIn.getYGXX_JJLXDH()+"', ");//紧急联系电话
			strbuf_YGXX.append("     '"+beanIn.getYGXX_WYLX()+"', ");//外语类型
			strbuf_YGXX.append("     '"+beanIn.getYGXX_WYJB()+"', ");//外语级别
			strbuf_YGXX.append("     '"+beanIn.getYGXX_KQJKH()+"', ");//考勤机卡号
			strbuf_YGXX.append("     '"+beanIn.getYGXX_RZSJ()+"', ");//入职时间
			strbuf_YGXX.append("     '"+beanIn.getYGXX_ZZSJ()+"', ");//转正时间
			strbuf_YGXX.append("     '"+beanIn.getYGXX_LZSJ()+"', ");//离职时间
			strbuf_YGXX.append("     '"+beanIn.getYGXX_ZZSM()+"', ");//转正说明
			strbuf_YGXX.append("     '"+beanIn.getYGXX_LZSM()+"', ");//离职说明
			strbuf_YGXX.append("     '"+beanIn.getYGXX_YGZT()+"', ");//员工状态（0：试用 1：正式 2：离职）
			strbuf_YGXX.append("     '"+beanIn.getYGXX_JSID()+"', ");//角色ID
			strbuf_YGXX.append("     '"+beanIn.getYGXX_CJR()+"', ");//创建人
			strbuf_YGXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_YGXX.append("     '"+beanIn.getYGXX_CJR()+"', ");//更新人
			strbuf_YGXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_YGXX.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_YGXX.append(" ) ");
			count_YGXX = db.executeSQL(strbuf_YGXX);
			/* 向YGXX表插入数据End */
			/* 向YHXX表插入数据Start */
			StringBuffer strbuf_YHXX = new StringBuffer();
			strbuf_YHXX.append(" INSERT INTO ");
			strbuf_YHXX.append("     YHXX ");
			strbuf_YHXX.append(" ( ");
			strbuf_YHXX.append("     YHXX_YHID, ");//用户ID
			strbuf_YHXX.append("     YHXX_YHMC, ");//用户名称
			strbuf_YHXX.append("     YHXX_YHMM, ");//用户密码
			strbuf_YHXX.append("     YHXX_JSID, ");//角色ID
			strbuf_YHXX.append("     YHXX_YGID, ");//员工ID
			strbuf_YHXX.append("     YHXX_SDZT, ");//锁定状态(0--正常 1--冻结)
			strbuf_YHXX.append("     YHXX_DLSJ, ");//登录时间
			strbuf_YHXX.append("     YHXX_CJR, ");//创建人
			strbuf_YHXX.append("     YHXX_CJSJ, ");//创建时间
			strbuf_YHXX.append("     YHXX_GXR, ");//更新人
			strbuf_YHXX.append("     YHXX_GXSJ, ");//更新时间
			strbuf_YHXX.append("     YHXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
			strbuf_YHXX.append(" ) ");
			strbuf_YHXX.append(" VALUES ");
			strbuf_YHXX.append(" ( ");
			strbuf_YHXX.append("     '"+beanIn.getYHXX_YHID()+"', ");//用户ID
			strbuf_YHXX.append("     '"+beanIn.getYGXX_XM()+"', ");//用户名称
			strbuf_YHXX.append("     '000000', ");//用户密码
			strbuf_YHXX.append("     '"+beanIn.getYGXX_JSID()+"', ");//角色ID
			strbuf_YHXX.append("     '"+ygxxid+"', ");//员工ID
			strbuf_YHXX.append("     '0', ");//锁定状态(0--正常 1--冻结)
			strbuf_YHXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//登录时间
			strbuf_YHXX.append("     '"+beanIn.getYGXX_CJR()+"', ");//创建人
			strbuf_YHXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_YHXX.append("     '"+beanIn.getYGXX_CJR()+"', ");//更新人
			strbuf_YHXX.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_YHXX.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_YHXX.append(" ) ");
			count_YHXX = db.executeSQL(strbuf_YHXX);
			/* 向YHXX表插入数据End */
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
			strbuf_YGZW.append("     '"+ygzwid+"', ");//员工职务ID
			strbuf_YGZW.append("     '"+ygxxid+"', ");//员工ID
			strbuf_YGZW.append("     '"+beanIn.getZWXX_ZWID()+"', ");//职务ID
			strbuf_YGZW.append(       beanIn.getYGZW_JXJB().equals("000")?"'',":"'" + beanIn.getYGZW_JXJB()+"', ");//绩效级别(1级～9级(1级最低，9级最高))
			strbuf_YGZW.append("     '"+beanIn.getYGZW_ZWLX()+"', ");//职务类型(1-专职 0-兼职)
			strbuf_YGZW.append("     '0', ");//上级主管ID
			strbuf_YGZW.append(       beanIn.getYGZW_ZWDJ().equals("000")?"'',":"'" + beanIn.getYGZW_ZWDJ()+"', ");//职务等级(董事级(9,8,7)经理级(6,5,4)管理级(3,2,1)职员0)
			strbuf_YGZW.append("     '"+beanIn.getYGXX_CJR()+"', ");//创建人
			strbuf_YGZW.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
			strbuf_YGZW.append("     '"+beanIn.getYGXX_CJR()+"', ");//更新人
			strbuf_YGZW.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//更新时间
			strbuf_YGZW.append("     '0'  ");//删除标志（0：未删除，1：已删除）
			strbuf_YGZW.append(" ) ");
			count_YGZW = db.executeSQL(strbuf_YGZW);
			/* 向YGZW表插入数据End */
			if (count_YGXX > 0 && count_YHXX > 0 && count_YGZW > 0) {
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
	 * @FunctionName: updateYgInfo
	 * @Description: 修改员工信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月9日 下午1:47:13
	 */
	public boolean updateYgInfo(Pojo1010110 beanIn) throws Exception {
		boolean result = false;
		int count_YGXX = 0;
		int count_YHXX = 0;
		int count_YGZW = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 更新YGXX表数据Start */
			StringBuffer strbuf_YGXX = new StringBuffer();
			strbuf_YGXX.append(" UPDATE ");
			strbuf_YGXX.append("     YGXX ");
			strbuf_YGXX.append(" SET ");
			strbuf_YGXX.append("     YGXX_GH='").append(beanIn.getYGXX_GH()).append("',");//工号
			strbuf_YGXX.append("     YGXX_XM='").append(beanIn.getYGXX_XM()).append("',");//姓名
			strbuf_YGXX.append("     YGXX_CSRQ='").append(beanIn.getYGXX_CSRQ()).append("',");//出生日期
			strbuf_YGXX.append("     YGXX_XB='").append(beanIn.getYGXX_XB()).append("',");//性别（0：男 1：女）
			strbuf_YGXX.append("     YGXX_NL='").append(beanIn.getYGXX_NL()).append("',");//年龄
			strbuf_YGXX.append("     YGXX_XX='").append(beanIn.getYGXX_XX()).append("',");//血型
			strbuf_YGXX.append("     YGXX_HYZK='").append(beanIn.getYGXX_HYZK()).append("',");//婚姻状况（未婚/已婚）
			strbuf_YGXX.append("     YGXX_MZ='").append(beanIn.getYGXX_MZ()).append("',");//民族
			strbuf_YGXX.append("     YGXX_HKLX='").append(beanIn.getYGXX_HKLX()).append("',");//户口类型（城镇/农村）
			strbuf_YGXX.append("     YGXX_HKSZD='").append(beanIn.getYGXX_HKSZD()).append("',");//户口所在地
			strbuf_YGXX.append("     YGXX_ZZMM='").append(beanIn.getYGXX_ZZMM()).append("',");//政治面貌（共青团员/党员/群众）
			strbuf_YGXX.append("     YGXX_BYXX='").append(beanIn.getYGXX_BYXX()).append("',");//毕业学校
			strbuf_YGXX.append("     YGXX_BYSJ='").append(beanIn.getYGXX_BYSJ()).append("',");//毕业时间
			strbuf_YGXX.append("     YGXX_SSZY='").append(beanIn.getYGXX_SSZY()).append("',");//所学专业
			strbuf_YGXX.append("     YGXX_ZGXL='").append(beanIn.getYGXX_ZGXL()).append("',");//最高学历
			strbuf_YGXX.append("     YGXX_BGDH='").append(beanIn.getYGXX_BGDH()).append("',");//办公电话
			strbuf_YGXX.append("     YGXX_YDDH='").append(beanIn.getYGXX_YDDH()).append("',");//移动电话
			strbuf_YGXX.append("     YGXX_GSYJ='").append(beanIn.getYGXX_GSYJ()).append("',");//公司邮件
			strbuf_YGXX.append("     YGXX_SRYJ='").append(beanIn.getYGXX_SRYJ()).append("',");//私人邮件
			strbuf_YGXX.append("     YGXX_ZZ='").append(beanIn.getYGXX_ZZ()).append("',");//住址
			strbuf_YGXX.append("     YGXX_BZ='").append(beanIn.getYGXX_BZ()).append("',");//备注
			strbuf_YGXX.append("     YGXX_JJLXR='").append(beanIn.getYGXX_JJLXR()).append("',");//紧急联系人
			strbuf_YGXX.append("     YGXX_JJLXDH='").append(beanIn.getYGXX_JJLXDH()).append("',");//紧急联系电话
			strbuf_YGXX.append("     YGXX_WYLX='").append(beanIn.getYGXX_WYLX()).append("',");//外语类型
			strbuf_YGXX.append("     YGXX_WYJB='").append(beanIn.getYGXX_WYJB()).append("',");//外语级别
			strbuf_YGXX.append("     YGXX_KQJKH='").append(beanIn.getYGXX_KQJKH()).append("',");//考勤机卡号
			strbuf_YGXX.append("     YGXX_RZSJ='").append(beanIn.getYGXX_RZSJ()).append("',");//入职时间
			strbuf_YGXX.append("     YGXX_ZZSJ='").append(beanIn.getYGXX_ZZSJ()).append("',");//转正时间
			strbuf_YGXX.append("     YGXX_LZSJ='").append(beanIn.getYGXX_LZSJ()).append("',");//离职时间
			strbuf_YGXX.append("     YGXX_ZZSM='").append(beanIn.getYGXX_ZZSM()).append("',");//转正说明
			strbuf_YGXX.append("     YGXX_LZSM='").append(beanIn.getYGXX_LZSM()).append("',");//离职说明
			strbuf_YGXX.append("     YGXX_YGZT='").append(beanIn.getYGXX_YGZT()).append("',");//员工状态（0：试用 1：正式 2：离职）
			strbuf_YGXX.append("     YGXX_JSID ='").append(beanIn.getYGXX_JSID()).append("',");//角色ID
			strbuf_YGXX.append("     YGXX_GXR='").append(beanIn.getYGXX_GXR()).append("',");//更新人
			strbuf_YGXX.append("     YGXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf_YGXX.append(" WHERE 1=1");
			strbuf_YGXX.append(" AND YGXX_YGID='").append(beanIn.getYGXX_YGID()).append("'");//员工ID
			count_YGXX = db.executeSQL(strbuf_YGXX);
			/* 更新YGXX表数据End */
			/* 更新YHXX表数据Start */
			StringBuffer strbuf_YHXX = new StringBuffer();
			strbuf_YHXX.append(" UPDATE ");
			strbuf_YHXX.append("     YHXX ");
			strbuf_YHXX.append(" SET ");
			strbuf_YHXX.append("     YHXX_YHMC='").append(beanIn.getYGXX_XM()).append("',");//用户名称
			strbuf_YHXX.append("     YHXX_JSID='").append(beanIn.getYGXX_JSID()).append("',");//角色ID
			strbuf_YHXX.append("     YHXX_DLSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//登录时间
			strbuf_YHXX.append("     YHXX_GXR='").append(beanIn.getYGXX_GXR()).append("',");//更新人
			strbuf_YHXX.append("     YHXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf_YHXX.append(" WHERE 1=1");
			strbuf_YHXX.append(" AND YHXX_YGID='").append(beanIn.getYGXX_YGID()).append("'");//员工ID
			count_YHXX = db.executeSQL(strbuf_YHXX);
			/* 更新YHXX表数据End */
			/* 更新YGZW表数据Start */
			StringBuffer strbuf_YGZW = new StringBuffer();
			strbuf_YGZW.append(" UPDATE ");
			strbuf_YGZW.append("     YGZW ");
			strbuf_YGZW.append(" SET ");
			strbuf_YGZW.append("     YGZW_ZWID='").append(beanIn.getZWXX_ZWID()).append("',");//职务ID
			strbuf_YGZW.append("     YGZW_JXJB='").append(beanIn.getYGZW_JXJB().equals("000")?"":beanIn.getYGZW_JXJB()).append("',");//绩效级别(1级～9级(1级最低，9级最高))
			strbuf_YGZW.append("     YGZW_ZWLX='").append(beanIn.getYGZW_ZWLX()).append("',");//职务类型(1-专职 0-兼职)
			strbuf_YGZW.append("     YGZW_ZWDJ='").append(beanIn.getYGZW_ZWDJ().equals("000")?"":beanIn.getYGZW_ZWDJ()).append("',");//职务等级(董事级(9,8,7)经理级(6,5,4)管理级(3,2,1)职员0)
			strbuf_YGZW.append("     YGZW_GXR='").append(beanIn.getYGXX_GXR()).append("',");//更新人
			strbuf_YGZW.append("     YGZW_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf_YGZW.append(" WHERE 1=1");
			strbuf_YGZW.append(" AND YGZW_YGID='").append(beanIn.getYGXX_YGID()).append("'");//员工ID
			count_YGZW = db.executeSQL(strbuf_YGZW);
			/* 更新YGZW表数据End */
			if (count_YGXX > 0 && count_YHXX > 0 && count_YGZW > 0) {
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
	 * @FunctionName: deleteYgInfo
	 * @Description: 删除员工信息
	 * @param beanIn
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author ztz
	 * @date 2015年2月9日 下午1:47:36
	 */
	public boolean deleteYgInfo(Pojo1010110 beanIn) throws Exception {
		boolean result =false;
		int count_YGXX = 0;
		int count_YHXX = 0;
		int count_YGZW = 0;

		try {
			db.openConnection();
			db.beginTran();
			/* 删除YGXX表数据Start */
			StringBuffer strbuf_YGXX = new StringBuffer();
			strbuf_YGXX.append(" UPDATE ");
			strbuf_YGXX.append("     YGXX ");
			strbuf_YGXX.append(" SET ");
			strbuf_YGXX.append("     YGXX_GXR='").append(beanIn.getYGXX_GXR()).append("',");//更新人
			strbuf_YGXX.append("     YGXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf_YGXX.append("     YGXX_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf_YGXX.append(" WHERE 1=1");
			strbuf_YGXX.append(" AND YGXX_YGID='").append(beanIn.getYGXX_YGID()).append("'");//员工ID
			count_YGXX = db.executeSQL(strbuf_YGXX);
			/* 删除YGXX表数据End */
			/* 删除YHXX表数据Start */
			StringBuffer strbuf_YHXX = new StringBuffer();
			strbuf_YHXX.append(" UPDATE ");
			strbuf_YHXX.append("     YHXX ");
			strbuf_YHXX.append(" SET ");
			strbuf_YHXX.append("     YHXX_GXR='").append(beanIn.getYGXX_GXR()).append("',");//更新人
			strbuf_YHXX.append("     YHXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf_YHXX.append("     YHXX_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf_YHXX.append(" WHERE 1=1");
			strbuf_YHXX.append(" AND YHXX_YGID='").append(beanIn.getYGXX_YGID()).append("'");//员工ID
			count_YHXX = db.executeSQL(strbuf_YHXX);
			/* 删除YHXX表数据End */
			/* 删除YGZW表数据Start */
			StringBuffer strbuf_YGZW = new StringBuffer();
			strbuf_YGZW.append(" UPDATE ");
			strbuf_YGZW.append("     YGZW ");
			strbuf_YGZW.append(" SET ");
			strbuf_YGZW.append("     YGZW_GXR='").append(beanIn.getYGXX_GXR()).append("',");//更新人
			strbuf_YGZW.append("     YGZW_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'),");//更新时间
			strbuf_YGZW.append("     YGZW_SCBZ='1'");//删除标志（0：未删除，1：已删除）
			strbuf_YGZW.append(" WHERE 1=1");
			strbuf_YGZW.append(" AND YGZW_YGID='").append(beanIn.getYGXX_YGID()).append("'");//员工ID
			count_YGZW = db.executeSQL(strbuf_YGZW);
			/* 删除YGZW表数据End */
			if (count_YGXX > 0 && count_YHXX > 0 && count_YGZW > 0) {
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