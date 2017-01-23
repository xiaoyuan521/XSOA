package com.xsoa.servlet.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.framework.core.BaseServlet;
import com.framework.log.MyLogger;
import com.xsoa.pojo.common.Pojo_MESSAGE;
import com.xsoa.service.common.MessageService;

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

    public static final String OFF_LINE     = "OFF_LINE"; //离线消息
    public static final String WINDOW_CLOSE     = "WINDOW_CLOSE"; //关闭窗口消息


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
        po.setMESSAGE_CONTENT(content);
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

}
