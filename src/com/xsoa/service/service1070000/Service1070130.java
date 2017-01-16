package com.xsoa.service.service1070000;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.custom.pojo_1070000.Pojo1070130;

public class Service1070130 extends BaseService {
	private DBManager db = null;

	public Service1070130() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取基本工资列表数据个数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author CZL
	 * @date 2015-4-29
	 */
	public int getDataCount(Pojo1070130 beanIn) throws Exception {
		int result = 0;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.JBGZ_GZID) ");//工资ID
			strbuf.append(" FROM ");
			strbuf.append("     YGXX B ");
			strbuf.append("     LEFT JOIN JBGZ A ON B.YGXX_YGID = A.JBGZ_YGID AND A.JBGZ_SCBZ = '0' ");
			strbuf.append("     LEFT JOIN BMXX C ON B.YGXX_BMID = C.BMXX_BMID AND C.BMXX_SCBZ = '0' ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）	
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
	 * @Description: 获取基本工资列表数据明细
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1070130>
	 * @author CZL
	 * @date 2015-4-29
	 */
	public List<Pojo1070130> getDataList(Pojo1070130 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1070130> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JBGZ_GZID, ");//工资ID
			strbuf.append("     B.YGXX_XM AS YGXM, ");//员工姓名
			strbuf.append("     C.BMXX_BMMC AS BMMC, ");//部门名称
			strbuf.append("     A.JBGZ_JBGZ, ");//基本工资
			strbuf.append("     A.JBGZ_GZYH, ");//工资银行
			strbuf.append("     A.JBGZ_GZKH, ");//工资卡号
			strbuf.append("     A.JBGZ_SBJS, ");//社保基数
			strbuf.append("     A.JBGZ_GJJS ");//公积金基数
			strbuf.append(" FROM ");
			strbuf.append("     YGXX B ");
			strbuf.append("     LEFT JOIN JBGZ A ON B.YGXX_YGID = A.JBGZ_YGID AND A.JBGZ_SCBZ = '0' ");
			strbuf.append("     LEFT JOIN BMXX C ON B.YGXX_BMID = C.BMXX_BMID AND C.BMXX_SCBZ = '0' ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.JBGZ_GZID ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1070130>> rs = new BeanListHandler<Pojo1070130>(
					Pojo1070130.class);
			
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
	 * @throws Exception
	 * @return String
	 * @author CZL
	 * @date 2015-4-29
	 */
	private String searchSql(Pojo1070130 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 部门名称 */
			if (MyStringUtils.isNotBlank(beanIn.getBMMC())) {
				if (!"000".equals(beanIn.getBMMC())) {
					strbuf.append(" AND B.YGXX_BMID = '").append(beanIn.getBMMC()).append("'");
				}
			}
			/* 员工姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getJBGZ_YGID())) {
				if (!"000".equals(beanIn.getJBGZ_YGID())) {
					strbuf.append(" AND B.YGXX_YGID = '").append(beanIn.getJBGZ_YGID()).append("'");
				}
			}
			
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		}
		return strbuf.toString();
	}
	/**	
	 * @FunctionName: getDataInfo	
	 * @Description: 获得楼宇详细信息
	 * @param beanIn
	 * @throws Exception	
	 * @return Pojo1070130	
	 * @author CZL	
	 * @date 2015-4-20
	 */	
	public Pojo1070130 getDataInfo(Pojo1070130 beanIn) throws Exception {
		Pojo1070130 result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.JBGZ_GZID, ");// 工资ID
			strbuf.append("     A.JBGZ_XMID, ");// 项目ID
			strbuf.append("     A.JBGZ_LYBH, ");// 楼宇编号
			strbuf.append("     A.JBGZ_LYMC, ");// 楼宇名称
			strbuf.append("     A.JBGZ_LYDZ, ");// 楼宇地址
			strbuf.append("     A.JBGZ_LYLX, ");// 楼宇类型
			strbuf.append("     A.JBGZ_LYJG, ");// 楼宇结构
			strbuf.append("     A.JBGZ_LYCX, ");// 楼宇朝向
			strbuf.append("     A.JBGZ_BZXX ");// 备注信息
			strbuf.append(" FROM ");
			strbuf.append("     JBGZ A ");
			strbuf.append(" WHERE 1=1 AND A.LYXX_SCBZ = '0'");
			strbuf.append("   AND A.LYXX_LYID = '").append(beanIn.getLYXX_LYID()).append("'");// 楼宇ID

			ResultSetHandler<Pojo1070130> rsh = new BeanHandler<Pojo1070130>(Pojo1070130.class);

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
	 * 
	 * @FunctionName: chkCodeExist
	 * @Description: 判断楼宇编号是否存在
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author CZL
	 * @date 2015-4-20
	 */
	public int chkCodeExist(Pojo1070130 beanIn) throws Exception {
		int result = 0;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.LYXX_LYID) ");//楼宇ID
			strbuf.append(" FROM ");
			strbuf.append("     LYXX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.LYXX_SCBZ = '0'");
			if (MyStringUtils.isNotBlank(beanIn.getLYXX_LYBH())) {
				strbuf.append(" AND REPLACE(A.LYXX_LYBH,' ','') = '").append(beanIn.getLYXX_LYBH()).append("'");
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
	 * @FunctionName: insertData
	 * @Description: 新增楼宇信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author CZL
	 * @date 2015-4-20
	 */
	public int insertData(Pojo_LYXX beanIn) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			db.beginTran();		
			result = this.insertSql(beanIn);
			
			if (result > 0) {
				db.commit();
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
	 * @FunctionName: insertData
	 * @Description: 新增楼宇信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author CZL
	 * @date 2015-4-20
	 */
	public int insertSql(Pojo_LYXX beanIn) throws Exception {
		int result = 0;
		String lyid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数	
		
		/* 向LYXX表插入数据Start */
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(" INSERT INTO ");
		strbuf.append("     LYXX ");
		strbuf.append(" ( ");
		strbuf.append("     LYXX_LYID, ");//楼宇ID
		strbuf.append("     LYXX_XMID, ");//项目ID
		strbuf.append("     LYXX_LYBH, ");//楼宇编号
		strbuf.append("     LYXX_LYMC, ");//楼宇名称
		strbuf.append("     LYXX_LYDZ, ");//楼宇地址
		strbuf.append("     LYXX_LYLX, ");//楼宇类型（0-商业 1-住宅 2-商住两用）
		strbuf.append("     LYXX_LYJG, ");//楼宇结构（关联DICT表）
		strbuf.append("     LYXX_LYCX, ");//楼宇朝向（关联DICT表）
		strbuf.append("     LYXX_BZXX, ");//备注信息
		strbuf.append("     LYXX_CJR, ");//创建人
		strbuf.append("     LYXX_CJSJ, ");//创建时间
		strbuf.append("     LYXX_GXR, ");//更新人
		strbuf.append("     LYXX_GXSJ, ");//更新时间
		strbuf.append("     LYXX_SCBZ  ");//删除标志（0：未删除，1：已删除）
		strbuf.append(" ) ");
		strbuf.append(" VALUES ");
		strbuf.append(" ( ");
		strbuf.append("     '"+lyid+"', ");//楼宇ID
		strbuf.append("     '"+beanIn.getLYXX_XMID()+"', ");
		strbuf.append("     '"+beanIn.getLYXX_LYBH()+"', ");
		strbuf.append("     '"+beanIn.getLYXX_LYMC()+"', ");
		strbuf.append("     '"+beanIn.getLYXX_LYDZ()+"', ");
		strbuf.append("     '"+beanIn.getLYXX_LYLX()+"', ");
		strbuf.append("     '"+beanIn.getLYXX_LYJG()+"', ");
		strbuf.append("     '"+beanIn.getLYXX_LYCX()+"', ");
		strbuf.append("     '"+beanIn.getLYXX_BZXX()+"', ");
		strbuf.append("     '"+beanIn.getLYXX_CJR()+"', ");
		strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
		strbuf.append("     '"+beanIn.getLYXX_CJR()+"', ");
		strbuf.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");
		strbuf.append("     '0'  ");
		strbuf.append(" ) ");
		result = db.executeSQL(strbuf);
		/* 向LYXX表插入数据End */
		return result;
	}
	/**
	 * 
	 * @FunctionName: updateData
	 * @Description: 修改楼宇信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author CZL
	 * @date 2015-4-20
	 */
	public int updateData(Pojo1070130 beanIn) throws Exception {
		int result = 0;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 更新LYXX表数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     LYXX ");
			strbuf.append(" SET ");
			strbuf.append("     LYXX_XMID='").append(beanIn.getLYXX_XMID()).append("',");//项目ID
			strbuf.append("     LYXX_LYBH='").append(beanIn.getLYXX_LYBH()).append("',");//楼宇编号
			strbuf.append("     LYXX_LYMC='").append(beanIn.getLYXX_LYMC()).append("',");//楼宇名称
			strbuf.append("     LYXX_LYDZ='").append(beanIn.getLYXX_LYDZ()).append("',");//楼宇地址
			strbuf.append("     LYXX_LYLX='").append(beanIn.getLYXX_LYLX()).append("',");//楼宇类型
			strbuf.append("     LYXX_LYJG='").append(beanIn.getLYXX_LYJG()).append("',");//楼宇结构
			strbuf.append("     LYXX_LYCX='").append(beanIn.getLYXX_LYCX()).append("',");//楼宇类型
			strbuf.append("     LYXX_BZXX='").append(beanIn.getLYXX_BZXX()).append("',");//备注信息
			strbuf.append("     LYXX_GXR='").append(beanIn.getLYXX_GXR()).append("',");//更新人
			strbuf.append("     LYXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     LYXX_LYID='").append(beanIn.getLYXX_LYID()).append("'");//楼宇ID
			result = db.executeSQL(strbuf);
			/* 更新LYXX表数据End */
			if(result>0){
				db.commit();
			}else{
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
	 * @Description: 删除楼宇信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author CZL
	 * @date 2015-4-20
	 */
	public int deleteData(Pojo1070130 beanIn) throws Exception {
		int result =0;
		
		try {
			db.openConnection();
			db.beginTran();
			/* 删除LYXX表数据Start */
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" UPDATE ");
			strbuf.append("     LYXX ");
			strbuf.append(" SET ");
			strbuf.append("     LYXX_SCBZ='1',");//删除标志（0：未删除，1：已删除）
			strbuf.append("     LYXX_GXR='").append(beanIn.getLYXX_GXR()).append("',");//更新人
			strbuf.append("     LYXX_GXSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')");//更新时间
			strbuf.append(" WHERE ");
			strbuf.append("     LYXX_LYID='").append(beanIn.getLYXX_LYID()).append("'");//楼宇ID
			result = db.executeSQL(strbuf);
			/* 删除LYXX表数据End */			
			if (result > 0) {
				db.commit();
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
	 * @FunctionName: getXMIDbyXMBH
	 * @Description: 通过项目编号获得项目ID
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author CZL
	 * @date 2015-4-24
	 */
	public String getXMIDbyXMBH(String xmbh) throws Exception {
		String xmid = null;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.XMXX_XMID) ");//项目ID
			strbuf.append(" FROM ");
			strbuf.append("     XMXX A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.XMXX_SCBZ = '0'");
			strbuf.append(" AND A.XMXX_XMBH = '").append(xmbh).append("'");		

			xmid = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return xmid;
	}
	/**
	 * 
	 * @FunctionName: getLYJGbyJGMC
	 * @Description: 通过楼宇结构名称获得ID
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author CZL
	 * @date 2015-4-27
	 */
	public String getLYJGbyJGMC(String jgmc) throws Exception {
		String lyjg = null;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.DICT_ZDDM) ");
			strbuf.append(" FROM ");
			strbuf.append("     DICT A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.DICT_SCBZ = '0' AND A.DICT_ZDLX = 'LYJG' ");
			strbuf.append(" AND A.DICT_ZDMC = '").append(jgmc).append("'");		

			lyjg = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return lyjg;
	}
	/**
	 * 
	 * @FunctionName: getLYCXbyCXMC
	 * @Description: 通过楼宇朝向名称获得ID
	 * @param beanIn
	 * @throws Exception
	 * @return String
	 * @author CZL
	 * @date 2015-4-27
	 */
	public String getLYCXbyCXMC(String cxmc) throws Exception {
		String lycx = null;

		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.DICT_ZDDM) ");
			strbuf.append(" FROM ");
			strbuf.append("     DICT A ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.DICT_SCBZ = '0' AND A.DICT_ZDLX = 'FJCX' ");
			strbuf.append(" AND A.DICT_ZDMC = '").append(cxmc).append("'");		

			lycx = db.queryForString(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return lycx;
	}




}