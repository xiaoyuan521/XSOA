package com.xsoa.service.login;

import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.basetable.Pojo_MENU;
import com.xsoa.pojo.basetable.Pojo_YHXX;


public class ServiceLogin {
	private DBManager db = null;

	public ServiceLogin() {
		db = new DBManager();
	}

	/**
	 * @FunctionName: checkUserID
	 * @Description: 验证用户ID
	 * @param UserID
	 * @return
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2016-9-6
	 */
	public Pojo_YHXX checkUserID(String strUserId) throws Exception {
		Pojo_YHXX result = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YHXX_YHID, ");//用户id
			strbuf.append("     A.YHXX_YHMC, ");//用户名
			strbuf.append("     A.YHXX_YHMM, ");//密码
			strbuf.append("     A.YHXX_JSID, ");//角色id
			strbuf.append("     A.YHXX_BMID, ");//部門ID
			strbuf.append("     A.YHXX_GRZP, ");//个人照片
			strbuf.append("     A.YHXX_SDZT, ");//锁定状态(0--正常 1--冻结)
			strbuf.append("     A.YHXX_CJR, ");//创建人
			strbuf.append("     A.YHXX_CJSJ, ");//创建日期
			strbuf.append("     A.YHXX_GXR, ");//修改人
			strbuf.append("     A.YHXX_GXSJ, ");//修改日期
			strbuf.append("     A.YHXX_DLSJ ");//登陆时间
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


	/**
	 * @FunctionName: getMenuInfoByUser
	 * @Description: 根据用户取得菜单信息
	 * @param strUserRole
	 * @return
	 * @throws Exception
	 * @return List<USER_MENU>
	 * @author czl
	 * @date 2016-9-6
	 */
	public List<Pojo_MENU> getMenuInfoByUser(String strUserId)
			throws Exception {
		List<Pojo_MENU> result = null;
		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append("SELECT C.MENU_CDID,");
			strbuf.append("  	  C.MENU_CDMC,");
			strbuf.append("  	  C.MENU_URL,");
			strbuf.append("  	  C.MENU_CDCJ,");
			strbuf.append("  	  C.MENU_FJDID,");
			strbuf.append("  	  C.MENU_XH");
			strbuf.append("  FROM YHXX A,");
			strbuf.append("  	  JSQX B,");
			strbuf.append("  	  MENU C");
			strbuf.append(" WHERE A.YHXX_JSID  =B.JSQX_JSID");
			strbuf.append("   AND B.JSQX_CDID  =C.MENU_CDID");
			strbuf.append("   AND C.MENU_SCBZ = '0' ");
			strbuf.append("   AND A.YHXX_YHID  ='").append(strUserId).append("'");
			//strbuf.append(" ORDER BY C.MENU_CDID,C.MENU_XH");
			strbuf.append(" ORDER BY C.MENU_CDCJ,(C.MENU_XH+0)");

			ResultSetHandler<List<Pojo_MENU>> rsh = new BeanListHandler<Pojo_MENU>(
					Pojo_MENU.class);
			result = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}

	/**
	 * @FunctionName: getUserMessCount
	 * @Description: 取得消息数
	 * @param UserID
	 * @return
	 * @throws Exception
	 * @return String
	 * @author ljg
	 * @date 2014年12月10日 上午9:25:00
	 */
	public String getUserMessCount(String strUserId) throws Exception {
		String result = "";

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     TO_CHAR(COUNT(A.XXMX_XXID)) AS MESS_CONT ");//用户角色类型
			strbuf.append(" FROM ");
			strbuf.append("     XXMX A ");
			strbuf.append(" WHERE A.XXMX_CKZT = '0'");
			strbuf.append("   AND A.XXMX_JSRID  ='").append(strUserId).append("'");

			result = db.queryForString(strbuf);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
	/**
	 * @FunctionName: getUserNameByUserID
	 * @Description: 取得用户名称
	 * @param strUserID
	 * @throws Exception
	 * @return String
	 * @author czl
	 * @date 2016-9-6
	 */
	public String getUserNameByUserID(String strUserId) throws Exception {
		String username = "";

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YHXX_YHMC ");//用户名称
			strbuf.append(" FROM ");
			strbuf.append("     YHXX A ");
			strbuf.append(" WHERE A.YHXX_SCBZ = '0'");
			strbuf.append("   AND A.YHXX_YHID  ='").append(strUserId).append("'");

			username = db.queryForString(strbuf);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return username;
	}
	/**
	 *
	 * @FunctionName: setLoginInfo
	 * @Description: 记录登录人信息
	 * @param beanIn
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2016-9-6
	 */
	public int setLoginInfo(String yhid,String dlip) throws Exception {
		int result = 0;
		int resultRZ = 0;
		int resultYH = 0;
		String dlid = UUID.randomUUID().toString().replace("-", "").toUpperCase(); //获取32位随机数
		String yhmc = this.getUserNameByUserID(yhid);
		try {
			db.openConnection();
			db.beginTran();

			/* 向DLRZ表插入数据Start */
			StringBuffer strbuf_DLRZ = new StringBuffer();
			strbuf_DLRZ.append(" INSERT INTO ");
			strbuf_DLRZ.append("     DLRZ ");
			strbuf_DLRZ.append(" ( ");
			strbuf_DLRZ.append("     DLRZ_DLID, ");//登录ID
			strbuf_DLRZ.append("     DLRZ_YHMC, ");//用户名称
			strbuf_DLRZ.append("     DLRZ_YHID, ");//用户ID
			strbuf_DLRZ.append("     DLRZ_DLSJ, ");//登录时间
			strbuf_DLRZ.append("     DLRZ_DLIP ");//登录IP
			strbuf_DLRZ.append(" ) ");
			strbuf_DLRZ.append(" VALUES ");
			strbuf_DLRZ.append(" ( ");
			strbuf_DLRZ.append("     '"+dlid+"', ");//登录ID
			strbuf_DLRZ.append("     '"+yhmc+"', ");//用户名称
			strbuf_DLRZ.append("     '"+yhid+"', ");//用户ID
			strbuf_DLRZ.append("     DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//登录时间
			strbuf_DLRZ.append("     '"+dlip+"' ");//登录IP
			strbuf_DLRZ.append(" ) ");
			resultRZ = db.executeSQL(strbuf_DLRZ);
			/* 向DLRZ表插入数据End */
			/* 修改YHXX表数据Start */
			StringBuffer strbuf_YHXX = new StringBuffer();
			strbuf_YHXX.append(" UPDATE ");
			strbuf_YHXX.append("     YHXX ");
			strbuf_YHXX.append(" SET ");
			strbuf_YHXX.append("     YHXX_DLSJ=DATE_FORMAT(now(),'%Y%m%d %H:%i:%s') ");//登录时间
			strbuf_YHXX.append(" WHERE ");
			strbuf_YHXX.append("     YHXX_YHID='").append(yhid).append("' ");//用户id
			resultYH = db.executeSQL(strbuf_YHXX);

			if (resultRZ > 0 && resultYH > 0 ) {
				result = 1;
				db.commit();
			} else {
				result = 0;
				db.rollback();
			}
		} catch (Exception e) {
			db.rollback();
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}

	/**
    *
    * @FunctionName: getDataList
    * @Description: 获取用户信息
    * @param beanIn
    * @throws Exception
    * @return List<Pojo_YHXX>
    * @author czl
    * @date 2017-01-13
    */
   public List<Pojo_YHXX> getDataList() throws Exception {
       List<Pojo_YHXX> result = null;

       try {
           db.openConnection();

           StringBuffer strbuf = new StringBuffer();
           strbuf.append(" SELECT ");
           strbuf.append("     Y.YHXX_YHID, ");//用户ID
           strbuf.append("     Y.YHXX_YHMC, ");//用户名称
           strbuf.append("     Y.YHXX_GRZP ");//头像
           strbuf.append(" FROM ");
           strbuf.append("     YHXX Y ");
           strbuf.append(" WHERE ");
           strbuf.append("     1=1 ");
           strbuf.append(this.searchSql());
           strbuf.append(" ORDER BY ");
           strbuf.append("     Y.YHXX_YHID");

           ResultSetHandler<List<Pojo_YHXX>> rs = new BeanListHandler<Pojo_YHXX>(Pojo_YHXX.class);
           result = db.queryForBeanListHandler(strbuf, rs);
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
   * @FunctionName: getGLY
   * @Description: 获取管理员信息
   * @throws Exception
   * @return Pojo_YHXX
   * @author czl
   * @date 2017-01-13
   */
  public Pojo_YHXX getGLY() throws Exception {
      Pojo_YHXX result = null;
      try {
          db.openConnection();
          StringBuffer sql = new StringBuffer();
          sql.append(" SELECT");
          sql.append("     Y.YHXX_YHID,");// 用户ID
          sql.append("     Y.YHXX_YHMC, ");//用户名称
          sql.append("     Y.YHXX_GRZP ");//个人照片
          sql.append("   FROM YHXX Y ");
          sql.append(" WHERE 1=1 ");
          sql.append(" AND Y.YHXX_JSID = 'J101'");

          ResultSetHandler<Pojo_YHXX> rsh = new BeanHandler<Pojo_YHXX>(
                  Pojo_YHXX.class);
          result = db.queryForBeanHandler(sql, rsh);

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
   * @FunctionName: searchSql
   * @Description: 查询条件部分
   * @param beanIn
   * @throws Exception
   * @return String
   * @author czl
   * @date 2017-01-13
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

}
