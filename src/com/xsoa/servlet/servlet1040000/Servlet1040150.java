package com.xsoa.servlet.servlet1040000;

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
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.basetable.Pojo_DWDJ;
import com.xsoa.pojo.basetable.Pojo_LRXX;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040150;
import com.xsoa.service.service1040000.Service1040150;
import com.xsoa.service.service1040000.Service1040160;

/**
 *
 * @ClassName: Servlet1040150
 * @Package:com.xsoa.servlet.servlet1040000
 * @Description: 拜访设定控制类
 * @author ztz
 * @date 2015年2月28日 上午9:11:04
 */
/**
 * Servlet implementation class Servlet1040150
 */
@WebServlet("/Servlet1040150")
public class Servlet1040150 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_SAVE = "CMD_SAVE";
	public static final String CMD_GETYXXX = "CMD_GETYXXX";
	public static final String CMD_GETLRXX = "CMD_GETLRXX";

	/* 本Servlet对应的Service */
	private Service1040150 service;

	/* 本Servlet对应的Service */
    private Service1040160 service1;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet1040150() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1040150();
		service1 = new Service1040160();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_DELETE.equals(CMD)) {
	        deleteYXXX(inputdata);
		} else if (CMD_SAVE.equals(CMD)) {
			saveData(inputdata);
		} else if (CMD_GETYXXX.equals(CMD)) {
            getYXXXRow(inputdata);
        } else if (CMD_GETLRXX.equals(CMD)) {
            getLrxxList(inputdata);
        }
	}
	/**
	 *
	 * @FunctionName: getDataList
	 * @Description: 获取游戏信息列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-12
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		String sort = this.getString(inputdata, "sort");// 排序关键字
		int page = this.getInt(inputdata, "page");// 当前页码
		int limit = this.getInt(inputdata, "limit");// 每页行数
		Pojo1040150 beanIn = (Pojo1040150) this.getObject(inputdata, "beanLoad",
				Pojo1040150.class);

		int TotalCount = 0;
		List<Pojo1040150> dataList = new ArrayList<Pojo1040150>();

		try {
			TotalCount = service.getDataCount(beanIn);
			dataList = service.getDataList(beanIn, page, limit, sort);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
		} finally {
			// 输出Ajax返回结果。
			printGrid(TotalCount, dataList);
		}
	}

	/**
     * @FunctionName: deleteYXXX
     * @Description: 删除游戏信息
     * @param inputdata
     * @throws Exception
     * @return void
     * @author czl
     * @date 2016-9-12
     */
    private void deleteYXXX(Map<String, String[]> inputdata) throws Exception {
        Pojo1040150 beanIn = (Pojo1040150) this.getObject(inputdata, "BeanIn",Pojo1040150.class);
        beanIn.setYXXX_GXR(beanUser.getYHXX_YHID());
        boolean result = false;

        try {
            result = service.deleteYXXX(beanIn);
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
	 * @FunctionName: saveData
	 * @Description: 保存游戏信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-12
	 */
	private void saveData(Map<String, String[]> inputdata) throws Exception {
		Pojo1040150 beanIn = (Pojo1040150) this.getObject(inputdata, "BeanIn",Pojo1040150.class);
		beanIn.setYXXX_CJR(beanUser.getYHXX_YHID());
		beanIn.setYXXX_GXR(beanUser.getYHXX_YHID());
		List <Pojo_DWDJ> dwList = service1.getDwdjList();
		boolean result = false;

		try {
			if (MyStringUtils.isBlank(beanIn.getYXXX_YXID())) {
			    result = service.insertData(beanIn, dwList);
			}
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

	private void getYXXXRow(Map<String, String[]> inputdata) throws Exception{
        Pojo1040150 beanIn = (Pojo1040150) this.getObject(inputdata, "BeanIn",Pojo1040150.class);
        try {
            if (MyStringUtils.isNotBlank(beanIn.getYXXX_YXID())){
                beanIn = service.getDetailData(beanIn);
            }
            if (beanIn!=null) {
                arrResult.add("SUCCESS");
                arrResult.add(beanIn);
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
    * @FunctionName: getLrxxList
    * @Description: 获取狼人信息
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-9-12
    */
   private void getLrxxList(Map<String, String[]> inputdata) throws Exception {
       List<Pojo_LRXX> dataList = new ArrayList<Pojo_LRXX>();

       try {
           dataList = service.getLrxxList();
           arrResult.add(dataList);
       } catch (Exception e) {
           MyLogger.error(this.getClass().getName(), e);
       } finally {
           // 输出Ajax返回结果。
           print(arrResult);
       }
   }
}