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
import com.xsoa.pojo.custom.pojo_1010000.Pojo1010120;
import com.xsoa.service.service1010000.Service1010120;

/**
 * 
 * @ClassName: Servlet1010120
 * @Package:com.xsoa.servlet.servlet1010000
 * @Description: 员工职务控制类
 * @author ztz
 * @date 2015年2月10日 上午10:27:16
 */
/**
 * Servlet implementation class Servlet1010120
 */
@WebServlet("/Servlet1010120")
public class Servlet1010120 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_CHK_EXIST = "CMD_CHK_EXIST";
	public static final String CMD_INSERT = "CMD_INSERT";
	public static final String CMD_UPDATE = "CMD_UPDATE";
	public static final String CMD_DELETE = "CMD_DELETE";
	
	/* 本Servlet对应的Service */
	private Service1010120 service;
	
	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;
	
	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;
	
    public Servlet1010120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1010120();
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
	 * @Description: 获取员工职务数据个数及列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:31:02
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1010120 beanIn = (Pojo1010120) this.getObject(inputdata, "beanLoad",
				Pojo1010120.class);

		int TotalCount = 0;
		List<Pojo1010120> dataList = new ArrayList<Pojo1010120>();
		
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
	 * @Description: 判断员工职务是否已存在
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:31:57
	 */
	private void chkDataExist(Map<String, String[]> inputdata) throws Exception {
		Pojo1010120 beanIn = (Pojo1010120) this.getObject(inputdata, "BeanIn",Pojo1010120.class);
		String optionFlag = this.getString(inputdata, "FLAG");
		int count = 0;

		try {
			count = service.getDataCount(beanIn, "NAME");
			if (count > 0) {
				if ("Add".equals(optionFlag)) {
					arrResult.add("DATA_NAME_EXIST");
				} else {
					String nameUuid = service.chkNameExist(beanIn);
					if (beanIn.getYGZW_UUID().equals(nameUuid)) {
						count = service.getDataCount(beanIn, "LX");
						if (count > 0) {
							String lxUuid = service.chkLxExist(beanIn);
							if ("1".equals(beanIn.getYGZW_ZWLX()) && !beanIn.getYGZW_UUID().equals(lxUuid)) {
								arrResult.add("DATA_LX_EXIST");
							} else {
								arrResult.add("DATA_NOEXIST");
							}
						} else {
							arrResult.add("DATA_NOEXIST");
						}
					} else {
						arrResult.add("DATA_NAME_EXIST");
					}
				}
			} else {
				count = service.getDataCount(beanIn, "LX");
				if (count > 0) {
					if ("1".equals(beanIn.getYGZW_ZWLX())) {
						if ("Add".equals(optionFlag)) {
							arrResult.add("DATA_LX_EXIST");
						} else {
							String lxUuid = service.chkLxExist(beanIn);
							if (beanIn.getYGZW_UUID().equals(lxUuid)) {
								arrResult.add("DATA_NOEXIST");
							} else {
								arrResult.add("DATA_LX_EXIST");
							}
						}
					} else {
						arrResult.add("DATA_NOEXIST");
					}
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
	 * @Description: 新增员工职务
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:32:08
	 */
	private void insertData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010120 beanIn = (Pojo1010120) this.getObject(inputdata, "BeanIn",Pojo1010120.class);
		beanIn.setYGZW_CJR(beanUser.getYHXX_YHID());
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
	 * @Description: 修改员工职务
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:32:18
	 */
	private void updateData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010120 beanIn = (Pojo1010120) this.getObject(inputdata, "BeanIn",Pojo1010120.class);
		beanIn.setYGZW_GXR(beanUser.getYHXX_YHID());
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
	 * @Description: 删除员工职务
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author ztz
	 * @date 2015年2月27日 下午4:32:28
	 */
	private void deleteData(Map<String, String[]> inputdata) throws Exception {
		Pojo1010120 beanIn = (Pojo1010120) this.getObject(inputdata, "BeanIn",Pojo1010120.class);
		beanIn.setYGZW_GXR(beanUser.getYHXX_YHID());
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