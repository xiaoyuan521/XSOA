package com.xsoa.service.service1040000;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.basetable.Pojo_DWDJ;
import com.xsoa.pojo.custom.pojo_1040000.Pojo1040160;

public class Service1040160 extends BaseService {
	private DBManager db = null;

	public Service1040160() {
		db = new DBManager();
	}

	/**
    *
    * @FunctionName: getDataList
    * @Description: 获取玩家段位信息
    * @throws Exception
    * @return List<Pojo1040130>
    * @author czl
    * @date 2016-12-30
    */
   public List<Pojo1040160> getDataList() throws Exception {
       List<Pojo1040160> dataList = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     Y.YHXX_YHID, ");//用户ID
           strbuf.append("     Y.YHXX_YHMC, ");//用户名称
           strbuf.append("     D.DWXX_GRJF ");//个人积分
           strbuf.append(" FROM ");
           strbuf.append("     YHXX Y");
           strbuf.append(" LEFT JOIN ");
           strbuf.append("     DWXX D");
           strbuf.append(" ON ");
           strbuf.append(" Y.YHXX_YHID = D.DWXX_YHID");
           strbuf.append(" WHERE 1=1 ");
           strbuf.append(" AND Y.YHXX_SCBZ = '0'");//删除标志（0：未删除，1：已删除）
           strbuf.append(" AND Y.YHXX_JSID != 'J101'");
           strbuf.append(" ORDER BY ");
           strbuf.append("     D.DWXX_GRJF DESC ");
           ResultSetHandler<List<Pojo1040160>> rs = new BeanListHandler<Pojo1040160>(Pojo1040160.class);
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
   * @FunctionName: getDwdjList
   * @Description: 获取段位等级信息
   * @throws Exception
   * @return List<Pojo_DWDJ>
   * @author czl
   * @date 2016-12-30
   */
  public List<Pojo_DWDJ> getDwdjList() throws Exception {
      List<Pojo_DWDJ> dataList = null;

      try {
          db.openConnection();

          StringBuffer strbuf = new StringBuffer();
          strbuf.append(" SELECT ");
          strbuf.append("     D.DWDJ_MIN, ");//最小
          strbuf.append("     D.DWDJ_MAX, ");//最大
          strbuf.append("     D.DWDJ_UP, ");//加分
          strbuf.append("     D.DWDJ_DOWN, ");//减分
          strbuf.append("     D.DWDJ_DWMC ");//段位名称
          strbuf.append(" FROM ");
          strbuf.append("     DWDJ D");
          strbuf.append(" ORDER BY ");
          strbuf.append("     DWDJ_DJID");
          ResultSetHandler<List<Pojo_DWDJ>> rs = new BeanListHandler<Pojo_DWDJ>(Pojo_DWDJ.class);
          dataList = db.queryForBeanListHandler(strbuf, rs);
      } catch (Exception e) {
          MyLogger.error(this.getClass().getName(), e);
          throw e;
      } finally {
          db.closeConnection();
      }
      return dataList;
  }
}