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
import com.xsoa.pojo.custom.pojo_1070000.Pojo1070110;

public class Service1070110 extends BaseService {
	private DBManager db = null;

	public Service1070110() {
		db = new DBManager();
	}
	/**
	 * 
	 * @FunctionName: getDataCount
	 * @Description: 获取楼宇列表数据个数
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author CZL
	 * @date 2015-4-29
	 */
	public int getDataCount(Pojo1070110 beanIn) throws Exception {
		int result = 0;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     COUNT(A.GZZF_GZID) ");//工资ID
			strbuf.append(" FROM ");
			strbuf.append("     GZZF A, YGXX B, BMXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.GZZF_YGID = B.YGXX_YGID AND B.YGXX_BMID = C.BMXX_BMID ");
			strbuf.append(" AND A.GZZF_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.BMXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
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
	 * @Description: 获取工资支付列表数据明细
	 * @param beanIn,page,limit,sort
	 * @throws Exception
	 * @return List<Pojo1070110>
	 * @author CZL
	 * @date 2015-4-29
	 */
	public List<Pojo1070110> getDataList(Pojo1070110 beanIn, int page,
			int limit, String sort) throws Exception {
		List<Pojo1070110> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.GZZF_GZID, ");//工资ID
			strbuf.append("     B.YGXX_XM AS YGXM, ");//员工姓名
			strbuf.append("     C.BMXX_BMMC AS BMMC, ");//部门名称
			strbuf.append("     A.GZZF_GZNY, ");//工资年月
			strbuf.append("     A.GZZF_JBGZ, ");//基本工资
			strbuf.append("     A.GZZF_JXGZ, ");//绩效工资
			strbuf.append("     A.GZZF_JJ, ");//奖金
			strbuf.append("     A.GZZF_YFGZ, ");//应发工资
			strbuf.append("     A.GZZF_GRSDS, ");//个人所得税
			strbuf.append("     A.GZZF_SFGZ ");//实发工资
			strbuf.append(" FROM ");
			strbuf.append("     GZZF A, YGXX B, BMXX C ");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.GZZF_YGID = B.YGXX_YGID AND B.YGXX_BMID = C.BMXX_BMID ");
			strbuf.append(" AND A.GZZF_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.YGXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND C.BMXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.GZZF_GZID ");
			
			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);
			
			ResultSetHandler<List<Pojo1070110>> rs = new BeanListHandler<Pojo1070110>(
					Pojo1070110.class);
			
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
	private String searchSql(Pojo1070110 beanIn) throws Exception {
		StringBuffer strbuf = new StringBuffer();
		
		try {
			/* 工资年月 */
			if (MyStringUtils.isNotBlank(beanIn.getGZZF_GZNY())) {
				strbuf.append(" AND A.GZZF_GZNY = '").append(beanIn.getGZZF_GZNY()).append("'");		
			}
			/* 部门名称 */
			if (MyStringUtils.isNotBlank(beanIn.getBMMC())) {
				if (!"000".equals(beanIn.getBMMC())) {
					strbuf.append(" AND C.BMXX_BMID = '").append(beanIn.getBMMC()).append("'");
				}
			}
			/* 员工姓名 */
			if (MyStringUtils.isNotBlank(beanIn.getGZZF_YGID())) {
				if (!"000".equals(beanIn.getGZZF_YGID())) {
					strbuf.append(" AND B.YGXX_YGID = '").append(beanIn.getGZZF_YGID()).append("'");
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
	 * @return Pojo1070110	
	 * @author CZL	
	 * @date 2015-4-20
	 */	
	public Pojo1070110 getDataInfo(Pojo1070110 beanIn) throws Exception {
		Pojo1070110 result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.GZZF_LYID, ");// 楼宇ID
			strbuf.append("     A.LYXX_XMID, ");// 项目ID
			strbuf.append("     A.LYXX_LYBH, ");// 楼宇编号
			strbuf.append("     A.LYXX_LYMC, ");// 楼宇名称
			strbuf.append("     A.LYXX_LYDZ, ");// 楼宇地址
			strbuf.append("     A.LYXX_LYLX, ");// 楼宇类型
			strbuf.append("     A.LYXX_LYJG, ");// 楼宇结构
			strbuf.append("     A.LYXX_LYCX, ");// 楼宇朝向
			strbuf.append("     A.LYXX_BZXX ");// 备注信息
			strbuf.append(" FROM ");
			strbuf.append("     LYXX A ");
			strbuf.append(" WHERE 1=1 AND A.LYXX_SCBZ = '0'");
			strbuf.append("   AND A.LYXX_LYID = '").append(beanIn.getLYXX_LYID()).append("'");// 楼宇ID

			ResultSetHandler<Pojo1070110> rsh = new BeanHandler<Pojo1070110>(Pojo1070110.class);

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
	public int chkCodeExist(Pojo1070110 beanIn) throws Exception {
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
	public int updateData(Pojo1070110 beanIn) throws Exception {
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
	public int deleteData(Pojo1070110 beanIn) throws Exception {
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
	/**
	 * 
	 * @FunctionName: checkCellData
	 * @Description: 检查Excel每行每列的值是否符合填写规定
	 * @param sheet,strBufMsg
	 * @return String
	 * @author CZL
	 * @date 2015-4-24
	 */
	public String checkCellData(HSSFSheet sheet, StringBuffer strBufMsg) {
		String excelSign = ExcelValidate.validateCellType(sheet.getRow(0).getCell(20));
		//1.判断导入的是否是指定的模板
		String templetSign = "xsoa-xmxx";//模板标识名称
		if ("".equals(excelSign) || !templetSign.equals(excelSign)) {
			strBufMsg.append("对不起，无法识别您的模板，请您使用我们提供的模板进行上传！");
			return strBufMsg.toString();//返回错误信息
		}
		
		ExcelValidate excelValidate = new ExcelValidate();
		int rowNum  = excelValidate.getEffectRowCount(sheet, 0, 7);//获取有效的输入行数
		
		String strMsg = null;
		boolean bool = false;
		//2.对空值内容进行合法性判断
		bool = excelValidate.cellCheck(1, rowNum, 0, 2, sheet, strBufMsg, "checkNull", null);
		if (!bool) {
	    	strMsg = strBufMsg.toString();
	    	return strMsg;//返回错误提示信息
	    }
		//3.对项目编号进行合法性判断
		bool = excelValidate.cellCheck(1, rowNum, 0, 0, sheet, strBufMsg, "checkXmbh", null);
		if (!bool) {
			strMsg = strBufMsg.toString();
			return strMsg;//返回错误提示信息
		}
		//4.对数据已存在内容进行合法性判断
		bool = excelValidate.cellCheck(1, rowNum, 1, 1, sheet, strBufMsg, "checkLybh", null);
		if (!bool) {
			strMsg = strBufMsg.toString();
			return strMsg;//返回错误提示信息
		}
		return strMsg;
	}
	/**
	 * 
	 * @FunctionName: excelToBeanList
	 * @Description: 将Excel的数据每行形成一个Bean存入List中
	 * @param sheet
	 * @return List<Pojo_LYXX>
	 * @author czl
	 * @throws Exception 
	 * @date 2015-4-24
	 */
	public List<Pojo_LYXX> excelToBeanList(HSSFSheet sheet) throws Exception {
		List<Pojo_LYXX> dataBeanList = new ArrayList<Pojo_LYXX>();//返回封装数据的List
		Pojo_LYXX dataBean = null;//每一个项目信息对象
		String cellValue = null;//单元格的值，最终按字符串处理
		
		// 开始循环遍历行，表头不处理，从1开始
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			dataBean = new Pojo_LYXX();//实例化Student对象
			HSSFRow row = sheet.getRow(i);//获取行对象
			if (row == null) {//如果整行为空，则不做任何处理，直接忽略掉
				continue;
			}
			//循环遍历每行的单元格
			for (int j = 0; j < row.getLastCellNum(); j++) {
				HSSFCell cell = row.getCell(j);//获取单元格对象
				cellValue = ExcelValidate.validateCellType(cell);
				if (cellValue != null) {
					cellValue = cellValue.trim();
				}
				
				//下面按照数据出现位置封装到bean中
				if (j == 0) {
					String xmid = this.getXMIDbyXMBH(cellValue);
					dataBean.setLYXX_XMID(xmid);
				} else if (j == 1) {
					dataBean.setLYXX_LYBH(cellValue);
				} else if (j == 2) {
					dataBean.setLYXX_LYMC(cellValue);
				} else if (j == 3) {
					dataBean.setLYXX_LYDZ(cellValue);
				} else if (j == 4) {
					String lylx = null;
					if(cellValue.equals("商业")){
						lylx = "0";
					}else if(cellValue.equals("住宅")){
						lylx = "1";
					}else if(cellValue.equals("商住两用")){
						lylx = "2";
					}
					dataBean.setLYXX_LYLX(lylx);
				} else if (j == 5) {
					String lyjg = getLYJGbyJGMC(cellValue);
					dataBean.setLYXX_LYJG(lyjg);
				} else if (j == 6) {
					String lycx = getLYCXbyCXMC(cellValue);
					dataBean.setLYXX_LYCX(lycx);
				} else if (j == 7) {
					dataBean.setLYXX_BZXX(cellValue);
				}
			}
			dataBeanList.add(dataBean);// 数据装入List
		}
		return dataBeanList;
	}
	/**
	 * 
	 * @FunctionName: saveListToDb
	 * @Description: 将List数据保存到数据库
	 * @param dataBeanList,beanIn
	 * @throws Exception
	 * @return void
	 * @author CZL
	 * @date 2015-4-27
	 */
	public void saveListToDb(List<Pojo_LYXX> dataBeanList, Pojo_LYXX beanIn) throws Exception {
		int count = 0;
		try {
			db.openConnection();
			db.beginTran();
			for (Pojo_LYXX insertBean : dataBeanList) {
				System.out.println("LYXX [lybh=" + insertBean.getLYXX_LYBH() + ", lymc=" + insertBean.getLYXX_LYMC() + ", lydz=" + insertBean.getLYXX_LYDZ() + "]");
				insertBean.setLYXX_CJR(beanIn.getLYXX_CJR());
				insertBean.setLYXX_GXR(beanIn.getLYXX_GXR());
				count += this.insertSql(insertBean);
			}
			if (count > 0 && count == dataBeanList.size()) {
				db.commit();
			} else {
				db.rollback();
			}
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			db.closeConnection();
		}
	}
	/**
	 * 
	 * @FunctionName: generateExcel2007
	 * @Description: 获取导出数据，生成2007版Excel文件
	 * @param filePath,beanIn
	 * @throws Exception
	 * @return void
	 * @author CZL
	 * @date 2015-4-27
	 */
	public void generateExcel2007(String filePath, Pojo1070110 beanIn) throws Exception {
		// 创建Excel2007工作簿对象
		XSSFWorkbook workbook2007 = new XSSFWorkbook();
		// 创建工作表对象并命名
		XSSFSheet sheet = workbook2007.createSheet("楼宇信息");
		// 设置行列的默认宽度和高度
		sheet.setColumnWidth(0, 32 * 100);// 对A列设置宽度为80像素
		sheet.setColumnWidth(1, 32 * 200);
		sheet.setColumnWidth(2, 32 * 300);
		sheet.setColumnWidth(3, 32 * 100);
		sheet.setColumnWidth(4, 32 * 100); 
		sheet.setColumnWidth(5, 32 * 100); 
		sheet.setColumnWidth(6, 32 * 120); 
		sheet.setColumnWidth(7, 32 * 120);
		// 创建样式
		XSSFFont headerFont = workbook2007.createFont();
		XSSFCellStyle headerStyle = workbook2007.createCellStyle();
		// 设置垂直居中
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置边框
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		// 字体加粗
		headerFont.setBold(true);
		//设置字体类型
		headerFont.setFontName("宋体");
		//设置字体大小
		headerFont.setFontHeightInPoints((short) 10);
		// 设置长文本自动换行
		headerStyle.setWrapText(true);
		headerStyle.setFont(headerFont);
		// 创建样式
		XSSFFont cellFont = workbook2007.createFont();
		XSSFCellStyle cellStyle = workbook2007.createCellStyle();
		// 设置垂直居中
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置边框
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		//设置字体类型
		cellFont.setFontName("宋体");
		//设置字体大小
		cellFont.setFontHeightInPoints((short) 10);
		// 设置长文本自动换行
		cellStyle.setWrapText(true);
		cellStyle.setFont(cellFont);
		// 创建表头
		XSSFRow headerRow = sheet.createRow(0);
		headerRow.setHeightInPoints(25f);// 设置行高度
		XSSFCell xmmcHeader = headerRow.createCell(0);
		xmmcHeader.setCellValue("项目名称");
		xmmcHeader.setCellStyle(headerStyle);
		XSSFCell lybhHeader = headerRow.createCell(1);
		lybhHeader.setCellValue("楼宇编号");
		lybhHeader.setCellStyle(headerStyle);
		XSSFCell lymcHeader = headerRow.createCell(2);
		lymcHeader.setCellValue("楼宇名称");
		lymcHeader.setCellStyle(headerStyle);
		XSSFCell lydzHeader = headerRow.createCell(3);
		lydzHeader.setCellValue("楼宇地址");
		lydzHeader.setCellStyle(headerStyle);
		XSSFCell lylxHeader = headerRow.createCell(4);
		lylxHeader.setCellValue("楼宇类型");
		lylxHeader.setCellStyle(headerStyle);
		XSSFCell lyjgHeader = headerRow.createCell(5);
		lyjgHeader.setCellValue("楼宇结构");
		lyjgHeader.setCellStyle(headerStyle);
		XSSFCell lycxHeader = headerRow.createCell(6);
		lycxHeader.setCellValue("楼宇朝向");
		lycxHeader.setCellStyle(headerStyle);
		XSSFCell bzHeader = headerRow.createCell(7);
		bzHeader.setCellValue("备注");
		bzHeader.setCellStyle(headerStyle);

		
		List<Pojo1070110> dataList = getExportDataList(beanIn);
		// 遍历集合对象创建行和单元格
		for (int i = 0; i < dataList.size(); i++) {
			XSSFRow row = sheet.createRow(i + 1);
			row.setHeightInPoints(20f);
			Pojo1070110 infoBean = dataList.get(i);
			XSSFCell xmmcCell = row.createCell(0);
			xmmcCell.setCellValue(infoBean.getXMMC());
			xmmcCell.setCellStyle(cellStyle);
			XSSFCell lybhCell = row.createCell(1);
			lybhCell.setCellValue(infoBean.getLYXX_LYBH());
			lybhCell.setCellStyle(cellStyle);
			XSSFCell lymcCell = row.createCell(2);
			lymcCell.setCellValue(infoBean.getLYXX_LYMC());
			lymcCell.setCellStyle(cellStyle);
			XSSFCell lydzCell = row.createCell(3);
			lydzCell.setCellValue(infoBean.getLYXX_LYDZ());
			lydzCell.setCellStyle(cellStyle);
			XSSFCell lylxCell = row.createCell(4);
			lylxCell.setCellValue(infoBean.getLYXX_LYLX());
			lylxCell.setCellStyle(cellStyle);
			XSSFCell lyjgCell = row.createCell(5);
			lyjgCell.setCellValue(infoBean.getLYXX_LYJG());
			lyjgCell.setCellStyle(cellStyle);
			XSSFCell lycxCell = row.createCell(6);
			lycxCell.setCellValue(infoBean.getLYXX_LYCX());
			lycxCell.setCellStyle(cellStyle);
			XSSFCell bzCell = row.createCell(7);
			bzCell.setCellValue(infoBean.getLYXX_BZXX());
			bzCell.setCellStyle(cellStyle);
			
		}
		// 生成文件
		File file = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2007.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
	 * @FunctionName: generateExcel2003
	 * @Description: 获取导出数据，生成2003版Excel文件
	 * @param filePath,beanIn
	 * @throws Exception
	 * @return void
	 * @author CZL
	 * @date 2015-4-27
	 */
	public void generateExcel2003(String filePath, Pojo1070110 beanIn) throws Exception {
		// 创建Excel2007工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet("楼宇信息");
		// 设置行列的默认宽度和高度
		sheet.setColumnWidth(0, 32 * 100);// 对A列设置宽度为80像素
		sheet.setColumnWidth(1, 32 * 200);
		sheet.setColumnWidth(2, 32 * 300);
		sheet.setColumnWidth(3, 32 * 100);
		sheet.setColumnWidth(4, 32 * 100); 
		sheet.setColumnWidth(5, 32 * 100); 
		sheet.setColumnWidth(6, 32 * 120); 
		sheet.setColumnWidth(7, 32 * 120);
		
		// 创建样式
		HSSFFont headerFont = workbook2003.createFont();
		HSSFCellStyle headerStyle = workbook2003.createCellStyle();
		// 设置垂直居中
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 设置边框
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 字体加粗
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//设置字体类型
		headerFont.setFontName("宋体");
		//设置字体大小
		headerFont.setFontHeightInPoints((short) 10);
		// 设置长文本自动换行
		headerStyle.setWrapText(true);
		headerStyle.setFont(headerFont);
		// 创建样式
		HSSFFont cellFont = workbook2003.createFont();
		HSSFCellStyle cellStyle = workbook2003.createCellStyle();
		// 设置垂直居中
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 设置边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//设置字体类型
		cellFont.setFontName("宋体");
		//设置字体大小
		cellFont.setFontHeightInPoints((short) 10);
		// 设置长文本自动换行
		cellStyle.setWrapText(true);
		cellStyle.setFont(cellFont);
		// 创建表头
		HSSFRow headerRow = sheet.createRow(0);
		headerRow.setHeightInPoints(25f);// 设置行高度
		HSSFCell xmmcHeader = headerRow.createCell(0);
		xmmcHeader.setCellValue("项目名称");
		xmmcHeader.setCellStyle(headerStyle);
		HSSFCell lybhHeader = headerRow.createCell(1);
		lybhHeader.setCellValue("楼宇编号");
		lybhHeader.setCellStyle(headerStyle);
		HSSFCell lymcHeader = headerRow.createCell(2);
		lymcHeader.setCellValue("楼宇名称");
		lymcHeader.setCellStyle(headerStyle);
		HSSFCell lydzHeader = headerRow.createCell(3);
		lydzHeader.setCellValue("楼宇地址");
		lydzHeader.setCellStyle(headerStyle);
		HSSFCell lylxHeader = headerRow.createCell(4);
		lylxHeader.setCellValue("楼宇类型");
		lylxHeader.setCellStyle(headerStyle);
		HSSFCell lyjgHeader = headerRow.createCell(5);
		lyjgHeader.setCellValue("楼宇结构");
		lyjgHeader.setCellStyle(headerStyle);
		HSSFCell lycxHeader = headerRow.createCell(6);
		lycxHeader.setCellValue("楼宇朝向");
		lycxHeader.setCellStyle(headerStyle);
		HSSFCell bzHeader = headerRow.createCell(7);
		bzHeader.setCellValue("备注");
		bzHeader.setCellStyle(headerStyle);
		
		List<Pojo1070110> dataList = getExportDataList(beanIn);
		// 遍历集合对象创建行和单元格
		for (int i = 0; i < dataList.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.setHeightInPoints(20f);
			Pojo1070110 infoBean = dataList.get(i);
			HSSFCell xmmcCell = row.createCell(0);
			xmmcCell.setCellValue(infoBean.getXMMC());
			xmmcCell.setCellStyle(cellStyle);
			HSSFCell lybhCell = row.createCell(1);
			lybhCell.setCellValue(infoBean.getLYXX_LYBH());
			lybhCell.setCellStyle(cellStyle);
			HSSFCell lymcCell = row.createCell(2);
			lymcCell.setCellValue(infoBean.getLYXX_LYMC());
			lymcCell.setCellStyle(cellStyle);
			HSSFCell lydzCell = row.createCell(3);
			lydzCell.setCellValue(infoBean.getLYXX_LYDZ());
			lydzCell.setCellStyle(cellStyle);
			HSSFCell lylxCell = row.createCell(4);
			lylxCell.setCellValue(infoBean.getLYXX_LYLX());
			lylxCell.setCellStyle(cellStyle);
			HSSFCell lyjgCell = row.createCell(5);
			lyjgCell.setCellValue(infoBean.getLYXX_LYJG());
			lyjgCell.setCellStyle(cellStyle);
			HSSFCell lycxCell = row.createCell(6);
			lycxCell.setCellValue(infoBean.getLYXX_LYCX());
			lycxCell.setCellStyle(cellStyle);
			HSSFCell bzCell = row.createCell(7);
			bzCell.setCellValue(infoBean.getLYXX_BZXX());
			bzCell.setCellStyle(cellStyle);
		
		}
		// 生成文件
		File file = new File(filePath);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
	 * @FunctionName: getExportDataList
	 * @Description: 获取项目信息列表数据明细
	 * @param beanIn
	 * @throws Exception
	 * @return List<Pojo1070110>
	 * @author CZL
	 * @date 2015-4-27
	 */
	public List<Pojo1070110> getExportDataList(Pojo1070110 beanIn) throws Exception {
		List<Pojo1070110> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.LYXX_LYID, ");//楼宇ID
			strbuf.append("     A.LYXX_XMID, ");//项目ID
			strbuf.append("     B.XMXX_XMMC AS XMMC, ");//项目名称
			strbuf.append("     A.LYXX_LYBH, ");//楼宇编号
			strbuf.append("     A.LYXX_LYMC, ");//楼宇名称
			strbuf.append("     A.LYXX_LYDZ, ");//楼宇地址
			strbuf.append("     CASE " +
					                    "WHEN A.LYXX_LYLX = '0' THEN '商业' " +
					                    "WHEN A.LYXX_LYLX = '1' THEN '住宅' " +
					                    "WHEN A.LYXX_LYLX = '2' THEN '商住两用' " +
								        "ELSE '' END AS LYXX_LYLX, ");//楼宇类型
			strbuf.append("     C1.DICT_ZDMC AS LYXX_LYJG, ");//楼宇结构
			strbuf.append("     C2.DICT_ZDMC AS LYXX_LYCX, ");//楼宇朝向
			strbuf.append("     A.LYXX_BZXX ");//备注	
			strbuf.append(" FROM ");
			strbuf.append("     (LYXX A, XMXX B) ");
			strbuf.append("     LEFT JOIN DICT C1 ON A.LYXX_LYJG = C1.DICT_ZDDM AND C1.DICT_SCBZ = '0' AND C1.DICT_ZDLX = 'LYJG'");
			strbuf.append("     LEFT JOIN DICT C2 ON A.LYXX_LYCX = C2.DICT_ZDDM AND C2.DICT_SCBZ = '0' AND C2.DICT_ZDLX = 'FJCX'");
			strbuf.append(" WHERE 1=1 ");
			strbuf.append(" AND A.LYXX_XMID = B.XMXX_XMID ");
			strbuf.append(" AND A.LYXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(" AND B.XMXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
			strbuf.append(this.searchSql(beanIn));
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.LYXX_CJSJ DESC ");
			
			ResultSetHandler<List<Pojo1070110>> rs = new BeanListHandler<Pojo1070110>(
					Pojo1070110.class);
			
			result = db.queryForBeanListHandler(strbuf, rs);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
}