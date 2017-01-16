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
import com.xsoa.pojo.basetable.Pojo_DWDJ;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040160;
import com.xsoa.service.service1040000.Service1040160;

/**
 *
 * @ClassName: Servlet1040160
 * @Package:com.xsoa.servlet.servlet1040000
 * @Description: 排位赛
 * @author czl
 * @date 2016-12-30
 */
/**
 * Servlet implementation class Servlet1040160
 */
@WebServlet("/Servlet1040160")
public class Servlet1040160 extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /* 命令定义部分 */
    public static final String CMD_SELECT = "CMD_SELECT";
    public static final String CMD_SELECT_DW = "CMD_SELECT_DW";

    /* 本Servlet对应的Service */
    private Service1040160 service;

    /* Ajax返回前台的结果集 */
    private ArrayList<Object> arrResult;

    /* 当前登录系统的用户对象 */
    Pojo_YHXX beanUser;

    public Servlet1040160() {
        super();
    }

    @Override
    public void process(HttpServletRequest request,
            HttpServletResponse response, Map<String, String[]> inputdata)
            throws IOException, ServletException, Exception {
        service = new Service1040160();
        arrResult = new ArrayList<Object>();
        beanUser = (Pojo_YHXX)getSessionObject(SessionAttribute.LOGIN_USER);

        String CMD = this.getString(inputdata, "CMD");

        if (CMD_SELECT.equals(CMD)) {
            getDataList(inputdata);
        } else if (CMD_SELECT_DW.equals(CMD)) {
            getDwList();
        }
    }

    /**
    *
    * @FunctionName: getDataList
    * @Description: 获取玩家段位信息
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2016-12-30
    */
   private void getDataList(Map<String, String[]> inputdata) throws Exception {
       List<Pojo1040160> dataList = new ArrayList<Pojo1040160>();
       List<Pojo1040160> dwxxList = service.getDataList();
       List<Pojo_DWDJ> dwdjList = service.getDwdjList();
       Pojo1040160 data;
       try {
           for (Pojo1040160 po : dwxxList) {
               data = new Pojo1040160();
               data.setYHXX_YHMC(po.getYHXX_YHMC());
               data.setDWXX_GRJF(po.getDWXX_GRJF());
               for (Pojo_DWDJ dj : dwdjList) {
                   if (po.getDWXX_GRJF() == null || po.getDWXX_GRJF() == 0) {
                       data.setDWXX_DWMC("无段位");
                       data.setDWXX_GRJF(0);
                   } else if ((po.getDWXX_GRJF() >= dj.getDWDJ_MIN()) && (po.getDWXX_GRJF() <= dj.getDWDJ_MAX())) {
                       data.setDWXX_DWMC(dj.getDWDJ_DWMC());
                   }
               }
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
   * @FunctionName: getDwList
   * @Description: 获取段位基本信息
   * @param inputdata
   * @throws Exception
   * @return void
   * @author czl
   * @date 2016-12-30
   */
  private void getDwList() throws Exception {
      List<Pojo_DWDJ> dwdjList = new ArrayList<Pojo_DWDJ>();
      try {
          dwdjList = service.getDwdjList();
          arrResult.add(dwdjList);
      } catch (Exception e) {
          MyLogger.error(this.getClass().getName(), e);
      } finally {
          // 输出Ajax返回结果。
          print(arrResult);
      }
  }

}