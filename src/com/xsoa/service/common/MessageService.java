package com.xsoa.service.common;

import java.util.UUID;

import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.common.Pojo_MESSAGE;

/**
 * @ClassName: MessageService
 * @Package:com.xsoa.service.common
 * @Description: 消息Service
 * @author czl
 * @date 2017-01-23
 * @update
 */
public class MessageService {

	private DBManager db = null;

	public MessageService() {
		db = new DBManager();
	}
	/**
	 * @FunctionName: saveMessage
	 * @Description: 保存消息记录
	 * @throws Exception
	 * @return List<SELECT_LIST>
	 * @author czl
	 * @date 2017-01-23
	 */
	public boolean saveMessage(Pojo_MESSAGE beanIn) throws Exception {
	    boolean result = false;
	    String messageid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数，用于插入消息ID
        int count = 0;
        try {
            db.openConnection();
            db.beginTran();
            StringBuffer strbuf_MS = new StringBuffer();
            strbuf_MS.append(" INSERT INTO ");
            strbuf_MS.append("     MESSAGE ");
            strbuf_MS.append(" ( ");
            strbuf_MS.append("     MESSAGE_ID, ");//消息ID
            strbuf_MS.append("     MESSAGE_FROM, ");//发送方
            strbuf_MS.append("     MESSAGE_TO, ");//接收方
            strbuf_MS.append("     MESSAGE_CONTENT, ");//内容
            strbuf_MS.append("     MESSAGE_TIME, ");//时间
            strbuf_MS.append("     MESSAGE_TYPE ");//类型
            strbuf_MS.append(" ) ");
            strbuf_MS.append(" VALUES ");
            strbuf_MS.append(" ( ");
            strbuf_MS.append("     '"+messageid+"', ");
            strbuf_MS.append("     '"+beanIn.getMESSAGE_FROM()+"', ");
            strbuf_MS.append("     '"+beanIn.getMESSAGE_TO()+"', ");
            strbuf_MS.append("     '"+beanIn.getMESSAGE_CONTENT()+"', ");
            strbuf_MS.append("     '"+beanIn.getMESSAGE_TIME()+"', ");
            strbuf_MS.append("     '"+beanIn.getMESSAGE_TYPE()+"' ");
            strbuf_MS.append(" ) ");
            count = db.executeSQL(strbuf_MS);
            if (count > 0) {
                db.commit();
                result = true;
            } else {
                db.rollback();
            }
        } catch (Exception e) {
            db.rollback();
            result = false;
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
	}

}