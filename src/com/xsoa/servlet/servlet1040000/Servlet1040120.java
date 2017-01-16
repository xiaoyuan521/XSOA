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
import com.xsoa.pojo.basetable.Pojo_LRXX;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040120;
import com.xsoa.service.service1040000.Service1040120;

/**
 *
 * @ClassName: Servlet1040120
 * @Package:com.xsoa.servlet.servlet1040000
 * @Description: 伙伴客户
 * @author ztz
 * @date 2015年2月27日 下午5:10:03
 */
/**
 * Servlet implementation class Servlet1040120
 */
@WebServlet("/Servlet1040120")
public class Servlet1040120 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SELECT_SL = "CMD_SELECT_SL";

	/* 本Servlet对应的Service */
	private Service1040120 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

    public Servlet1040120() {
        super();
    }

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1040120();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_SELECT_SL.equals(CMD)) {
		    getSlList(inputdata);
		}
	}
	/**
	 *
	 * @FunctionName: getDataList
	 * @Description: 获取拿牌率
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-10-18
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
	    List<Pojo1040120> dataList = new ArrayList<Pojo1040120>();
	    List<Pojo_LRXX> LrList = service.getLrxxList();
	    Pojo1040120 data;
        try {
            for (Pojo_LRXX po : LrList) {
                data = new Pojo1040120();
                data.setLRJS(po.getLRXX_LRMC());
                Double npl = service.getData(po.getLRXX_LRID(), beanUser.getYHXX_YHID()).getNPL();
                data.setNPL(npl);
                dataList.add(data);
            }
            arrResult.add(dataList);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
        } finally {
            // 输出Ajax返回结果。
            print(arrResult);
        }
	}

	/**
    *
    * @FunctionName: getSlList
    * @Description: 获取个人胜率
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-10-18
    */
   private void getSlList(Map<String, String[]> inputdata) throws Exception {
       List<Pojo1040120> dataList = new ArrayList<Pojo1040120>();
       List<Pojo_LRXX> LrList = service.getLrxxList();
       Pojo1040120 data;
       try {
           for (Pojo_LRXX po : LrList) {
               data = new Pojo1040120();
               data.setLRJS(po.getLRXX_LRMC());
               Double grsl = service.getSl(po.getLRXX_LRID(), beanUser.getYHXX_YHID()).getGRSL();
               data.setGRSL(grsl);
               dataList.add(data);
           }
           arrResult.add(dataList);
       } catch (Exception e) {
           MyLogger.error(this.getClass().getName(), e);
       } finally {
           // 输出Ajax返回结果。
           print(arrResult);
       }
   }
}