package com.xsoa.servlet.common;

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
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.common.Pojo_MESSAGE;
import com.xsoa.service.common.MessageService;
import com.xsoa.util.StringControl;

/**
 * @ClassName: MessageRecord
 * @Package:com.xsoa.servlet.common
 * @Description: 消息Servlet
 * @author czl
 * @date 2017-01-23
 * @update
 */
/**
 * Servlet implementation class CommonServlet
 */
@WebServlet("/MessageRecord")
public class MessageRecord extends BaseServlet {
    private static final long serialVersionUID = 1L;
    /* 命令定义部分 */
    public static final String SAVE_MESSAGE = "SAVE_MESSAGE";
    public static final String SELECT_ONLINE     = "SELECT_ONLINE"; //取得在线列表
    public static final String SELECT_OFFLINE     = "SELECT_OFFLINE"; //取得离线列表
    public static final String SELECT_MESSAGE   = "SELECT_MESSAGE";//获得消息记录

    public static final String OFF_LINE     = "OFF_LINE"; //离线消息
    public static final String WINDOW_CLOSE     = "WINDOW_CLOSE"; //关闭窗口消息
    public static final String SET_MESSAGE_READ = "SET_MESSAGE_READ";//设置已读


    /* 本Servlet对应的Service */
    private MessageService service;
    /* Ajax返回前台的结果集 */
    private ArrayList<Object> arrResult;

    public MessageRecord() {
        super();
    }

    @Override
    public void process(HttpServletRequest request,
            HttpServletResponse response, Map<String, String[]> inputdata)
            throws IOException, ServletException, Exception {

        service = new MessageService();
        arrResult = new ArrayList<Object>();

        String CMD = this.getString(inputdata, "CMD");

        if (SAVE_MESSAGE.equals(CMD)) {
            saveMessage(inputdata);
        } else if (SELECT_ONLINE.equals(CMD)) {
            getOnlineList(inputdata);
        } else if (SELECT_OFFLINE.equals(CMD)) {
            getOfflineList(inputdata);
        } else if (SELECT_MESSAGE.equals(CMD)) {
            getMessageList(inputdata);
        } else if (SET_MESSAGE_READ.equals(CMD)) {
            setMessageRead(inputdata);
        }
    }

    private void saveMessage(Map<String, String[]> inputdata) throws Exception{
        String from = this.getString(inputdata, "MESSAGE_FROM");// 发送方
        String to = this.getString(inputdata, "MESSAGE_TO");// 接收方
        String content = this.getString(inputdata, "MESSAGE_CONTENT");// 内容
        String time = this.getString(inputdata, "MESSAGE_TIME");// 时间
        String type = this.getString(inputdata, "MESSAGE_TYPE");// 类型
        Pojo_MESSAGE po = new Pojo_MESSAGE();
        po.setMESSAGE_FROM(from);
        po.setMESSAGE_TO(to);
        po.setCONTENT(content);
        po.setMESSAGE_TIME(time);
        po.setMESSAGE_TYPE(type);
        boolean result = false;
        try{
            if (OFF_LINE.equals(type)) {
                result = service.saveMessage(po);
            } else if (WINDOW_CLOSE.equals(type)) {
                result = service.saveMessage(po);
            }
            if (result) {
                arrResult.add("SUCCESS");
            } else {
                arrResult.add("FAILURE");
            }
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            arrResult.add("ERROR");
        } finally {
            // 输出Ajax返回结果。
            print(arrResult);
        }
    }

    /**
    *
    * @FunctionName: getOnlineList
    * @Description: 获取在线用户
    * @param inputdata
    * @throws Exception
    * @return void
    * @author czl
    * @date 2017-02-06
    */
   private void getOnlineList(Map<String, String[]> inputdata) throws Exception {
       List<Pojo_YHXX> dataList = new ArrayList<Pojo_YHXX>();
       List<String> list = new ArrayList<String>();
       try {
           dataList = service.getOnlineList();
           for (Pojo_YHXX po : dataList) {
               list.add(po.getYHXX_YHID());
           }
           arrResult.add(list);
       } catch (Exception e) {
           MyLogger.error(this.getClass().getName(), e);
       } finally {
           // 输出Ajax返回结果。
           print(arrResult);
       }
   }

   /**
   *
   * @FunctionName: getOfflineList
   * @Description: 获取离线用户
   * @param inputdata
   * @throws Exception
   * @return void
   * @author czl
   * @date 2017-02-06
   */
  private void getOfflineList(Map<String, String[]> inputdata) throws Exception {
      List<Pojo_YHXX> dataList = new ArrayList<Pojo_YHXX>();
      List<String> list = new ArrayList<String>();
      try {
          dataList = service.getOfflineList();
          for (Pojo_YHXX po : dataList) {
              list.add(po.getYHXX_YHID());
          }
          arrResult.add(list);
      } catch (Exception e) {
          MyLogger.error(this.getClass().getName(), e);
      } finally {
          // 输出Ajax返回结果。
          print(arrResult);
      }
  }

  /**
  *
  * @FunctionName: getMessageList
  * @Description: 获取聊天记录
  * @param inputdata
  * @throws Exception
  * @return void
  * @author czl
  * @date 2017-02-07
  */
 private void getMessageList(Map<String, String[]> inputdata) throws Exception {
     String from = this.getString(inputdata, "FROM");// 发送方
     String to = this.getString(inputdata, "TO");// 接收方
     String type = this.getString(inputdata, "TYPE");// 类型
     List<Pojo_MESSAGE> chatList = new ArrayList<Pojo_MESSAGE>();
     List<Pojo_MESSAGE> dataList = new ArrayList<Pojo_MESSAGE>();
     Pojo_MESSAGE data;
     try {
         service.setMessageRead(from, to, type);
         chatList = service.getMessageList(from, to, type);
         for (Pojo_MESSAGE po : chatList) {
             data = new Pojo_MESSAGE();
             data.setMESSAGE_FROM(po.getMESSAGE_FROM());
             data.setMESSAGE_TO(po.getMESSAGE_TO());
             data.setCONTENT(StringControl.valueOf(po.getMESSAGE_CONTENT()));
             data.setMESSAGE_TIME(po.getMESSAGE_TIME());
             data.setMESSAGE_TYPE(po.getMESSAGE_TYPE());
             if ("group".equals(type)) {
                 data.setMESSAGE_READ(po.getMESSAGE_READ());
                 data.setNAME(service.getUserInfo(po.getMESSAGE_FROM()).getYHXX_YHMC());
                 data.setFACE(service.getUserInfo(po.getMESSAGE_FROM()).getYHXX_GRZP());
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

 private void setMessageRead(Map<String, String[]> inputdata) throws Exception{
     String from = this.getString(inputdata, "FROM");// 发送方
     String to = this.getString(inputdata, "TO");// 接收方
     String type = this.getString(inputdata, "TYPE");// 类型

     try{
         service.setMessageRead(to, from, type);
     } catch (Exception e) {
         MyLogger.error(this.getClass().getName(), e);
         arrResult.add("ERROR");
     } finally {
         // 输出Ajax返回结果。
         print(arrResult);
     }
 }

}
