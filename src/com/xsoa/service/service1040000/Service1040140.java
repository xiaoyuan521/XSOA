package com.xsoa.service.service1040000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.basetable.Pojo_JRFG;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040140;

public class Service1040140 extends BaseService {
    private DBManager db = null;

    public Service1040140() {
        db = new DBManager();
    }

    /**
     *
     * @FunctionName: getDataList
     * @Description: 获取玩家信息
     * @param beanIn
     * @param page
     * @param limit
     * @param sort
     * @return
     * @throws Exception
     * @return List<Pojo1040140>
     * @author czl
     * @date 2016-9-6
     */
    public List<Pojo1040140> getDataList() throws Exception {
        List<Pojo1040140> dataList = null;

        try {
            db.openConnection();

            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     Y.YHXX_YHID, ");//用户ID
            strbuf.append("     Y.YHXX_YHMC, ");//用户名称
            strbuf.append("     Y.YHXX_GRCH, ");//个人称号
            strbuf.append("     Y.YHXX_EMAIL, ");//EMAIL
            strbuf.append("     Y.YHXX_TEL, ");//TEL
            strbuf.append("     Y.YHXX_GRZP, ");//头像
            strbuf.append("     B.BMXX_BMMC ");//部门名称
            strbuf.append(" FROM ");
            strbuf.append("     YHXX Y");
            strbuf.append(" LEFT JOIN ");
            strbuf.append("     BMXX B");
            strbuf.append(" ON ");
            strbuf.append(" Y.YHXX_BMID = B.BMXX_BMID");
            strbuf.append(" WHERE 1=1 ");
            strbuf.append(this.searchSql());
            strbuf.append(" ORDER BY ");
            strbuf.append("     Y.YHXX_YHID ");
            ResultSetHandler<List<Pojo1040140>> rs = new BeanListHandler<Pojo1040140>(Pojo1040140.class);
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
     * @FunctionName: searchSql
     * @Description: 查询条件部分
     * @param beanIn
     * @return
     * @throws Exception
     * @return String
     * @author czl
     * @date 2016-9-6
     */
    private String searchSql() throws Exception {
        StringBuffer strbuf = new StringBuffer();

        try {
            /* 公共方法 */
            strbuf.append(" AND Y.YHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
            strbuf.append(" AND Y.YHXX_JSID != 'J101'");//角色不是管理员
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        }
        return strbuf.toString();
    }

    /**
    *
    * @FunctionName: getFgid
    * @Description: 获取法官ID
    * @param beanIn
    * @throws Exception
    * @return Pojo_JRFG
    * @author czl
    * @date 2016-11-03
    */
    public Pojo_YHXX getFgid() throws Exception {
        Pojo_YHXX result = null;

        try {
            db.openConnection();

            StringBuffer strbuf = new StringBuffer();
            strbuf.append(" SELECT ");
            strbuf.append("     * ");
            strbuf.append(" FROM ");
            strbuf.append("     YHXX ");
            strbuf.append(" WHERE YHXX_JSID != 'J101' ");
            strbuf.append(" ORDER BY rand() LIMIT 1 ");

            ResultSetHandler<Pojo_YHXX> rs = new BeanHandler<Pojo_YHXX>(
                    Pojo_YHXX.class);

            result = db.queryForBeanHandler(strbuf, rs);
        } catch (Exception e) {
            MyLogger.error(this.getClass().getName(), e);
            throw e;
        } finally {
            db.closeConnection();
        }
        return result;
    }

    /**
    *
    * @FunctionName: updateJrfg
    * @Description: 更新今日法官
    * @param beanIn
    * @throws Exception
    * @return boolean
    * @author czl
    * @date 2016-11-03
    */
   public boolean updateJrfg(Pojo_JRFG beanIn) throws Exception {
       boolean result = false;
       int count = 0;

       try {
           db.openConnection();
           db.beginTran();
           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" UPDATE ");
           strbuf.append("     JRFG ");
           strbuf.append(" SET ");
           strbuf.append("     JRFG_FGID='").append(beanIn.getJRFG_FGID()).append("',");
           strbuf.append("     JRFG_BYID='").append(beanIn.getJRFG_BYID()).append("',");
           strbuf.append("     JRFG_GXSJ='").append(beanIn.getJRFG_GXSJ()).append("'");

           count = db.executeSQL(strbuf);

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