package com.xsoa.service.common;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.basetable.Pojo_YHXX;
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
        int count_in = 0;
        int count_del = 0;
        try {
            db.openConnection();
            db.beginTran();
            StringBuffer strbuf_IN = new StringBuffer();
            strbuf_IN.append(" INSERT INTO ");
            strbuf_IN.append("     MESSAGE ");
            strbuf_IN.append(" ( ");
            strbuf_IN.append("     MESSAGE_ID, ");//消息ID
            strbuf_IN.append("     MESSAGE_FROM, ");//发送方
            strbuf_IN.append("     MESSAGE_TO, ");//接收方
            strbuf_IN.append("     MESSAGE_CONTENT, ");//内容
            strbuf_IN.append("     MESSAGE_TIME, ");//时间
            strbuf_IN.append("     MESSAGE_TYPE, ");//类型
            strbuf_IN.append("     MESSAGE_READ ");//是否已读
            strbuf_IN.append(" ) ");
            strbuf_IN.append(" VALUES ");
            strbuf_IN.append(" ( ");
            strbuf_IN.append("     '"+messageid+"', ");
            strbuf_IN.append("     '"+beanIn.getMESSAGE_FROM()+"', ");
            strbuf_IN.append("     '"+beanIn.getMESSAGE_TO()+"', ");
            strbuf_IN.append("     '"+beanIn.getCONTENT()+"', ");
            strbuf_IN.append("     '"+beanIn.getMESSAGE_TIME()+"', ");
            strbuf_IN.append("     '"+beanIn.getMESSAGE_TYPE()+"', ");
            strbuf_IN.append("     '1' ");
            strbuf_IN.append(" ) ");
            count_in = db.executeSQL(strbuf_IN);

            StringBuffer strbuf_DEL = new StringBuffer();
            strbuf_DEL.append(" DELETE FROM ");
            strbuf_DEL.append("     MESSAGE ");
            strbuf_DEL.append(" WHERE ");
            strbuf_DEL.append("     MESSAGE_TIME<=(SELECT ");//消息ID
            strbuf_DEL.append(" MIN(b.MESSAGE_TIME) ");
            strbuf_DEL.append(" FROM(SELECT m.MESSAGE_TIME FROM MESSAGE m ");
            strbuf_DEL.append(" WHERE ");
            strbuf_DEL.append("     (m.MESSAGE_FROM = '"+beanIn.getMESSAGE_FROM()+"' ");
            strbuf_DEL.append(" AND ");
            strbuf_DEL.append("     m.MESSAGE_TO = '"+beanIn.getMESSAGE_TO()+"') ");
            strbuf_DEL.append(" OR ");
            strbuf_DEL.append("     (m.MESSAGE_FROM = '"+beanIn.getMESSAGE_TO()+"' ");
            strbuf_DEL.append(" AND ");
            strbuf_DEL.append("     m.MESSAGE_TO = '"+beanIn.getMESSAGE_FROM()+"') ");
            strbuf_DEL.append(" ORDER BY ");
            strbuf_DEL.append(" m.MESSAGE_TIME DESC LIMIT 200,1) AS b) ");
            count_del = db.executeSQL(strbuf_DEL);

            StringBuffer strbuf_GROUP = new StringBuffer();
            strbuf_GROUP.append(" DELETE FROM ");
            strbuf_GROUP.append("     MESSAGE ");
            strbuf_GROUP.append(" WHERE ");
            strbuf_GROUP.append("     MESSAGE_TIME<=(SELECT ");//消息ID
            strbuf_GROUP.append(" MIN(b.MESSAGE_TIME) ");
            strbuf_GROUP.append(" FROM(SELECT m.MESSAGE_TIME FROM MESSAGE m ");
            strbuf_GROUP.append(" WHERE ");
            strbuf_GROUP.append("     m.MESSAGE_TO = '"+beanIn.getMESSAGE_TO()+"' ");
            strbuf_GROUP.append(" ORDER BY ");
            strbuf_GROUP.append(" m.MESSAGE_TIME DESC LIMIT 2000,1) AS b) ");
            db.executeSQL(strbuf_GROUP);

            if (count_in > 0) {
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

	/**
    *
    * @FunctionName: getOnlineList
    * @Description: 获取在线用户
    * @throws Exception
    * @return List<Pojo_YHXX>
    * @author czl
    * @date 2017-02-06
    */
   public List<Pojo_YHXX> getOnlineList() throws Exception {
       List<Pojo_YHXX> dataList = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     Y.YHXX_YHID, ");//用户ID
           strbuf.append("     Y.YHXX_YHMC ");//用户名称
           strbuf.append(" FROM ");
           strbuf.append("     YHXX Y");
           strbuf.append(" WHERE Y.YHXX_ZXZT = '0' ");

           ResultSetHandler<List<Pojo_YHXX>> rs = new BeanListHandler<Pojo_YHXX>(Pojo_YHXX.class);
           dataList = db.queryForBeanListHandler(strbuf, rs);
       } catch (Exception e) {
           MyLogger.error(this.getClass().getName(), e);
           throw e;
       } finally {
           db.closeConnection();
       }
       return dataList;
   }

   /**
   *
   * @FunctionName: getOfflineList
   * @Description: 获取离线用户
   * @throws Exception
   * @return List<Pojo_YHXX>
   * @author czl
   * @date 2017-02-06
   */
  public List<Pojo_YHXX> getOfflineList() throws Exception {
      List<Pojo_YHXX> dataList = null;

      try {
          db.openConnection();

          StringBuffer strbuf = new StringBuffer();
          strbuf.append(" SELECT ");
          strbuf.append("     Y.YHXX_YHID, ");//用户ID
          strbuf.append("     Y.YHXX_YHMC ");//用户名称
          strbuf.append(" FROM ");
          strbuf.append("     YHXX Y");
          strbuf.append(" WHERE Y.YHXX_ZXZT = '1' ");

          ResultSetHandler<List<Pojo_YHXX>> rs = new BeanListHandler<Pojo_YHXX>(Pojo_YHXX.class);
          dataList = db.queryForBeanListHandler(strbuf, rs);
      } catch (Exception e) {
          MyLogger.error(this.getClass().getName(), e);
          throw e;
      } finally {
          db.closeConnection();
      }
      return dataList;
  }

  /**
  *
  * @FunctionName: getMessageList
  * @Description: 获取消息记录
  * @throws Exception
  * @return List<Pojo_YHXX>
  * @author czl
  * @date 2017-02-06
  */
 public List<Pojo_MESSAGE> getMessageList(String from, String to, String type) throws Exception {
     List<Pojo_MESSAGE> dataList = null;

     try {
         db.openConnection();

         StringBuffer strbuf = new StringBuffer();
         strbuf.append(" SELECT ");
         strbuf.append("     M.MESSAGE_FROM, ");//发送方
         strbuf.append("     M.MESSAGE_TO, ");//接受方
         strbuf.append("     M.MESSAGE_CONTENT, ");//消息内容
         strbuf.append("     M.MESSAGE_TIME, ");//消息时间
         strbuf.append("     M.MESSAGE_READ ");//是否读取
         strbuf.append(" FROM ");
         strbuf.append("     MESSAGE M");
         if ("one".equals(type)) {
             strbuf.append(" WHERE (M.MESSAGE_FROM = '"+from+"' ");
             strbuf.append(" AND ");
             strbuf.append("       M.MESSAGE_TO = '"+to+"') ");
             strbuf.append(" OR ");
             strbuf.append(" (M.MESSAGE_FROM = '"+to+"' ");
             strbuf.append(" AND ");
             strbuf.append("       M.MESSAGE_TO = '"+from+"') ");
         } else if ("group".equals(type)) {
             strbuf.append(" WHERE ");
             strbuf.append("       M.MESSAGE_TO = '"+to+"' ");
         }
         strbuf.append(" ORDER BY ");
         strbuf.append("     M.MESSAGE_TIME ASC ");
         ResultSetHandler<List<Pojo_MESSAGE>> rs = new BeanListHandler<Pojo_MESSAGE>(Pojo_MESSAGE.class);
         dataList = db.queryForBeanListHandler(strbuf, rs);
     } catch (Exception e) {
         MyLogger.error(this.getClass().getName(), e);
         throw e;
     } finally {
         db.closeConnection();
     }
     return dataList;
 }

 /**
 *
 * @FunctionName: getUnReadList
 * @Description: 获取未读消息
 * @throws Exception
 * @return List<Pojo_MESSAGE>
 * @author czl
 * @date 2017-02-08
 */
public List<Pojo_MESSAGE> getUnReadList(String userid) throws Exception {
    List<Pojo_MESSAGE> dataList = null;

    try {
        db.openConnection();

        StringBuffer strbuf = new StringBuffer();
        strbuf.append(" SELECT ");
        strbuf.append("     M.MESSAGE_FROM, ");//发送方
        strbuf.append("     COUNT(M.MESSAGE_FROM) AS UNREAD_COUNT ");//未读数
        strbuf.append(" FROM ");
        strbuf.append("     MESSAGE M");
        strbuf.append(" WHERE ");
        strbuf.append("     M.MESSAGE_TO = '"+userid+"' ");
        strbuf.append(" AND ");
        strbuf.append("     M.MESSAGE_FROM != '"+userid+"' ");
        strbuf.append(" AND ");
        strbuf.append("     M.MESSAGE_READ = '1' ");
        strbuf.append(" GROUP BY ");
        strbuf.append("     M.MESSAGE_FROM ");
        ResultSetHandler<List<Pojo_MESSAGE>> rs = new BeanListHandler<Pojo_MESSAGE>(Pojo_MESSAGE.class);
        dataList = db.queryForBeanListHandler(strbuf, rs);
    } catch (Exception e) {
        MyLogger.error(this.getClass().getName(), e);
        throw e;
    } finally {
        db.closeConnection();
    }
    return dataList;
}

    /**
    *
    * @FunctionName: setMessageRead
    * @Description: 设置消息已读
    * @throws Exception
    * @author czl
    * @date 2017-02-08
    */
    public void setMessageRead(String from, String to, String type) throws Exception {
        int result = 0;
        try {
            db.openConnection();
            db.beginTran();
            StringBuffer strbuf = new StringBuffer();
            if ("one".equals(type)) {
                strbuf.append(" UPDATE ");
                strbuf.append("     MESSAGE ");
                strbuf.append(" SET ");
                strbuf.append("     MESSAGE_READ='0' ");//设置已读
                strbuf.append(" WHERE ");
                strbuf.append("     MESSAGE_FROM='").append(to).append("' ");
                strbuf.append(" AND ");
                strbuf.append("     MESSAGE_TO='").append(from).append("' ");
            } else if ("group".equals(type)) {
                strbuf.append(" UPDATE ");
                strbuf.append("     MESSAGE ");
                strbuf.append(" SET ");
                strbuf.append("     MESSAGE_READ=CONCAT(MESSAGE_READ, ',").append(from).append("')");
                strbuf.append(" WHERE ");
                strbuf.append("     MESSAGE_TO='").append(to).append("'");
                strbuf.append(" AND ");
                strbuf.append("     LOCATE('").append(from).append("',MESSAGE_READ)=0 ");
            }
            result = db.executeSQL(strbuf);
            if (result > 0) {
                db.commit();
            } else {
                db.rollback();
            }
        } catch (Exception e) {
            db.rollback();
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
    }

 /**
  * @FunctionName: getUserInfo
  * @Description: 获得用户信息
  * @param UserID
  * @return Pojo_YHXX
  * @author czl
  * @date 2017-02-07
  */
 public Pojo_YHXX getUserInfo(String strUserId) throws Exception {
     Pojo_YHXX result = null;

     try {
         db.openConnection();

         StringBuffer strbuf = new StringBuffer();
         strbuf.append(" SELECT ");
         strbuf.append("     A.YHXX_YHMC, ");//用户名
         strbuf.append("     A.YHXX_GRZP ");//个人照片
         strbuf.append(" FROM ");
         strbuf.append("     YHXX A ");
         strbuf.append(" WHERE A.YHXX_YHID  ='").append(strUserId).append("'");
         strbuf.append(" AND A.YHXX_SCBZ = '0' ");

         ResultSetHandler<Pojo_YHXX> rsh = new BeanHandler<Pojo_YHXX>(
                 Pojo_YHXX.class);

         result = db.queryForBeanHandler(strbuf, rsh);

     } catch (Exception e) {
         MyLogger.error(this.getClass().getName(), e);
         throw e;
     } finally {
         db.closeConnection();
     }

     return result;
 }

}