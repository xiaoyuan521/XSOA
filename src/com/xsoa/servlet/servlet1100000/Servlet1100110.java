package com.xsoa.servlet.servlet1100000;

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
import com.xsoa.pojo.basetable.Pojo_BMXX;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040150;
import com.xsoa.pojo.custom.pojo_1100000.Pojo1100110;
import com.xsoa.service.service1100000.Service1100110;

/**
 *
 * @ClassName: Servlet1100110
 * @Package:com.xsoa.servlet.servlet1100000
 * @Description: 论坛管理控制类
 * @author czl
 * @date 2016-9-19
 */
/**
 * Servlet implementation class Servlet1100110
 */
@WebServlet("/Servlet1100110")
public class Servlet1100110 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_GETBMXX ="CMD_GETBMXX";
	public static final String CMD_DELETE = "CMD_DELETE";
	public static final String CMD_SAVE = "CMD_SAVE";

	/* 本Servlet对应的Service */
	private Service1100110 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet1100110() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1100110();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getDataList();
		} else if (CMD_DELETE.equals(CMD)) {
            deleteYXXX(inputdata);
        } else if (CMD_GETBMXX.equals(CMD)) {
	        getBmList(inputdata);
		} else if (CMD_SAVE.equals(CMD)) {
			saveData(inputdata);
		}
	}
	/**
	 *
	 * @FunctionName: getDataList
	 * @Description: 获取论坛版块列表
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-21
	 */
	public void getDataList() throws Exception {
	    List<Pojo1100110> dataList = new ArrayList<Pojo1100110>();
	    List<Pojo1100110> bkList = new ArrayList<Pojo1100110>();
	    Pojo1100110 data;
        try {
            dataList = service.getDataList();
            for (Pojo1100110 pojo : dataList) {
                data = new Pojo1100110();
                String bmids = MyStringUtils.getStrWithDy(pojo.getLTBK_KJID());
                String bmmcs = service.getBmmcByBmids(bmids);
                data.setLTBK_BKID(pojo.getLTBK_BKID());
                data.setLTBK_BKMC(pojo.getLTBK_BKMC());
                data.setLTBK_BKSM(pojo.getLTBK_BKSM());
                data.setLTBK_TPLJ(pojo.getLTBK_TPLJ());
                data.setBZMC(pojo.getBZMC());
                data.setKJMC(bmmcs);
                bkList.add(data);
            }
            arrResult.add(bkList);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
        } finally {
            // 输出Ajax返回结果。
            print(arrResult);
        }
	}

    /**
    *
    * @FunctionName: getBmList
    * @Description: 获取部门信息
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-9-26
    */
   private void getBmList(Map<String, String[]> inputdata) throws Exception {
       List<Pojo_BMXX> dataList = new ArrayList<Pojo_BMXX>();

       try {
           dataList = service.getBmxxList();
           arrResult.add(dataList);
       } catch (Exception e) {
           MyLogger.error(this.getClass().getName(), e);
       } finally {
           // 输出Ajax返回结果。
           print(arrResult);
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
	 * @Description: 保存游戏版块
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-26
	 */
	private void saveData(Map<String, String[]> inputdata) throws Exception {
		Pojo1100110 beanIn = (Pojo1100110) this.getObject(inputdata, "BeanIn",Pojo1100110.class);
		beanIn.setLTBK_CJR(beanUser.getYHXX_YHID());
		beanIn.setLTBK_GXR(beanUser.getYHXX_YHID());

		boolean result = false;

		try {
			if (MyStringUtils.isBlank(beanIn.getLTBK_BKID())) {
			    result = service.insertData(beanIn);
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

}