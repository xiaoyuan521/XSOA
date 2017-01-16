package com.xsoa.servlet.servlet1010000;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.framework.session.SessionAttribute;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010160;
import com.xsoa.service.service1010000.Service1010160;

/**
 * 
 * @ClassName: Servlet1010160
 * @Package:com.xsoa.servlet.servlet1010000
 * @Description: 员工合同控制类
 * @author ztz
 * @date 2015年2月27日 下午4:51:19
 */
/**
 * Servlet implementation class Servlet1010160
 */
@WebServlet("/Servlet1010160")
public class Servlet1010160 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service1010160 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1010160() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1010160();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);
		
		String CMD = this.getString(inputdata, "CMD");
		
		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_CHK_EXIST.equals(CMD)) {
			chkDataExist(inputdata);
		} else if (CMD_INSERT.equals(CMD)) {
			insertData(inputdata);
		} else if (CMD_UPDATE.equals(CMD)) {
			updateData(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
			deleteData(inputdata);
		}		
	}
	/**
	 * 
	 * @FunctionName: getDataList
	 * @Description: 获取合同信息数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:55:12
	 * @update ztz at 2015年3月11日 上午10:44:50
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1010160 beanIn = (Pojo1010160) this.getObject(inputdata, "beanLoad",
				Pojo1010160.class);

		int TotalCount = 0;
		List<Pojo1010160> dataList = new ArrayList<Pojo1010160>();
		
		try {
			TotalCount = service.getDataCount(beanIn, "list");
			dataList = service.getDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}
	/**
	 * 
	 * @FunctionName: chkDataExist
	 * @Description: 判断合同信息是否已存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:55:20
	 * @update ztz at 2015年3月11日 上午10:44:50
	 */
	private void chkDataExist(Map<String, String[]> inputdata) throws Exception {
		Pojo1010160 beanIn = (Pojo1010160) this.getObject(inputdata, "BeanIn",Pojo1010160.class);
		String optionFlag = this.getString(inputdata, "FLAG");

		try {
			if ("Add".equals(optionFlag)) {//新增判断
				int TotalCount = service.getDataCount(beanIn, "check");
				if (TotalCount > 0) {
					arrResult.add("DATA_EXIST");
				} else {
					arrResult.add("DATA_NOEXIST");
				}
			} else if ("Upd".equals(optionFlag)) {//修改判断
				String uuid = service.chkDataExist(beanIn);
				if (!beanIn.getYGHT_HTID().equals(uuid)) {
					arrResult.add("DATA_EXIST");
				} else {
					arrResult.add("DATA_NOEXIST");
				}
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: insertData
	 * @Description: 新增合同信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:55:29
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010160 beanIn = (Pojo1010160) this.getObject(inputdata, "BeanIn",Pojo1010160.class);
		beanIn.setYGHT_CJR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.insertData(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: updateData
	 * @Description: 修改合同信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:55:37
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010160 beanIn = (Pojo1010160) this.getObject(inputdata, "BeanIn",Pojo1010160.class);
		beanIn.setYGHT_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.updateData(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
	/**
	 * 
	 * @FunctionName: deleteData
	 * @Description: 删除合同信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:55:49
	 */
	private void deleteData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010160 beanIn = (Pojo1010160) this.getObject(inputdata, "BeanIn",Pojo1010160.class);
		beanIn.setYGHT_GXR(beanUser.getYHXX_YHID());
		boolean result = false;
		
		try {
			result = service.deleteData(beanIn);
			if (result) {
				arrResult.add("SUCCESS");
			} else {
				arrResult.add("FAILURE");
			}
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			arrResult.add("EXCEPTION");
		} finally {
			// 输出Ajax返回结果。
			print(arrResult);
		}
	}
}