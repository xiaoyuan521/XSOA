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
import com.xsoa.pojo.basetable.Pojo_JRFG;
import com.xsoa.pojo.basetable.Pojo_JSXX;
import com.xsoa.pojo.basetable.Pojo_LRXX;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040130;
import com.xsoa.service.service1040000.Service1040130;

/**
 *
 * @ClassName: Servlet1040130
 * @Package:com.xsoa.servlet.servlet1040000
 * @Description: 胜率信息控制类
 * @author czl
 * @date 2016-9-7
 */
/**
 * Servlet implementation class Servlet1040130
 */
@WebServlet("/Servlet1040130")
public class Servlet1040130 extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/* 命令定义部分 */
	public static final String CMD_SELECT = "CMD_SELECT";
	public static final String CMD_SELECT_JRFG = "CMD_SELECT_JRFG";
	public static final String CMD_SELECT_LRXX = "CMD_SELECT_LRXX";
	public static final String CMD_SELECT_LRSL = "CMD_SELECT_LRSL";

	/* 本Servlet对应的Service */
	private Service1040130 service;

	/* Ajax返回前台的结果集 */
	private ArrayList<Object> arrResult;

	/* 当前登录系统的用户对象 */
	Pojo_YHXX beanUser;

	@Override
	public void process(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> inputdata)
			throws IOException, ServletException, Exception {
		service = new Service1040130();
		arrResult = new ArrayList<Object>();
		beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

		String CMD = this.getString(inputdata, "CMD");

		if (CMD_SELECT_JRFG.equals(CMD)) {
            getJrfg();
        } else if (CMD_SELECT.equals(CMD)) {
			getDataList(inputdata);
		} else if (CMD_SELECT_LRXX.equals(CMD)) {
		    getLrxxList(inputdata);
        } else if (CMD_SELECT_LRSL.equals(CMD)) {
            getLrslList(inputdata);
        }
	}
	/**
     * Constructor of the object.
     */
    public Servlet1040130() {
        super();
    }
    /**
     *
     * @FunctionName: getJrfg
     * @Description: 获得今日法官
     * @param inputdata
     * @throws Exception
     * @return void
     * @author czl
     * @date 2016-11-30
     */
    private void getJrfg() throws Exception {
        Pojo_JRFG Data = null;
        try {
            Data = service.getJrfg();
            if(Data != null)
            {
                arrResult.add("SUCCESS");
                arrResult.add(Data);
            }else
            {
                arrResult.add("DATA_NULL");
                arrResult.add("");
            }
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            arrResult.add("CMD_EXCEPTION");
        } finally {
            // 输出Ajax返回结果。
            print(arrResult);
        }
    }
	/**
	 *
	 * @FunctionName: getDataList
	 * @Description: 获取玩家胜率信息
	 * @param inputdata
	 * @throws Exception
	 * @return void
	 * @author czl
	 * @date 2016-9-6
	 */
	private void getDataList(Map<String, String[]> inputdata) throws Exception {
		List<Pojo1040130> dataList = new ArrayList<Pojo1040130>();

		try {
			dataList = service.getDataList();
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
    * @FunctionName: getLrxxList
    * @Description: 获取狼人信息
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-9-14
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
   /**
   *
   * @FunctionName: getLrslList
   * @Description: 获取狼人胜率
   * @param inputdata
   * @throws Exception
   * @return void
   * @author czl
   * @date 2016-9-14
   */
  private void getLrslList(Map<String, String[]> inputdata) throws Exception {
      List<Pojo_JSXX> dataList = new ArrayList<Pojo_JSXX>();

      try {
          dataList = service.getLrslList();
          arrResult.add(dataList);
      } catch (Exception e) {
          MyLogger.error(this.getClass().getName(), e);
      } finally {
          // 输出Ajax返回结果。
          print(arrResult);
      }
  }
}