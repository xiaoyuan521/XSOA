package com.framework.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.framework.log.MyLogger;

public class DBManager {

	private Connection conn = null;
	private QueryRunner run = null;

	/**
	 * @Method: openConnection
	 * @Description: 打开数据库连接
	 * @return 返回值说明
	 * @author czl:
	 * @date 2016-9-6
	 */
	public Connection openConnection() {
		try {
			conn = DriverManager.getConnection("proxool.DBURL");
		} catch (SQLException e) {
			MyLogger.error(this.getClass().getName(), e);
		}
		return conn;
	}

	/**
	 * @Method: closeConnection
	 * @Description: 关闭数据库连接 注意：使用后必须关闭。
	 * @author misty:
	 * @date 2014年2月18日 下午12:03:18
	 */
	public void closeConnection() {
		try {
			DbUtils.close(conn);
		} catch (SQLException e) {
			MyLogger.error(this.getClass().getName(), e);
		}
	}

	/**
	 * @Method: beginTran
	 * @Description: 开启事务
	 * @throws SQLException
	 *             返回值说明
	 * @author misty:
	 * @date 2014年2月18日 下午12:03:46
	 */
	public void beginTran() throws SQLException {
		this.conn.setAutoCommit(false);
	}

	/**
	 * @Method: commit
	 * @Description: 提交事务
	 * @author misty:
	 * @date 2014年2月18日 下午12:04:06
	 */
	public void commit() {
		try {
			this.conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Method: rollback
	 * @Description: 回滚事务
	 * @author misty:
	 * @date 2014年2月18日 下午12:04:21
	 */
	public void rollback() {
		try {
			this.conn.rollback();
			this.conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数据查询操作.<br>
	 * ArrayHandler,将ResultSet中第一行的数据转化成对象数组.<br>
	 * 使用举例：<br>
	 * Object[] result;<br>
	 * StringBuffer sql = new
	 * StringBuffer("SELECT USER_ID,USER_NAME FROM TBL_USER");<br>
	 * result = queryForArrayHandler(sql);<br>
	 * String USER_ID = result[0];<br>
	 * String USER_NAME = result[1];<br>
	 *
	 * @Method: queryForArrayHandler
	 * @Description: 将ResultSet中第一行的数据转化成对象数组.
	 * @param sql
	 *            --SQL语句
	 * @return Object[] --返回值说明
	 * @throws Exception
	 * @author misty:
	 * @date 2014年2月18日 下午12:05:08
	 */
	public Object[] queryForArrayHandler(StringBuffer sql) throws Exception {
		// SQL语句输入Debug日志。
		MyLogger.debug(sql.toString());

		run = new QueryRunner();
		return run.query(this.conn, formartSQL(sql.toString()),
				new ArrayHandler());
	}

	/**
	 * 数据查询操作.<br>
	 * ArrayListHandler,将ResultSet中所有的数据转化成List，List中存放的是Object[].<br>
	 * 使用举例：<br>
	 * List&lt;Object[]&gt; result;<br>
	 * StringBuffer sql = new
	 * StringBuffer("SELECT USER_ID,USER_NAME FROM TBL_USER");<br>
	 * result = queryForArrayListHandler(sql);<br>
	 * for(int i=0,len=result.length(); i&lt;len; i++){ <br>
	 * &nbsp;&nbsp;String USER_ID = result[i][0];<br>
	 * &nbsp;&nbsp;String USER_NAME = result[i][1];<br>
	 * }
	 *
	 * @Method: queryForArrayListHandler
	 * @Description: 将ResultSet中所有的数据转化成List
	 * @param sql
	 *            --SQL语句
	 * @return List<Object[]> --返回值说明
	 * @throws Exception
	 * @author misty:
	 * @date 2014年2月18日 下午12:06:43
	 */
	public List<Object[]> queryForArrayListHandler(StringBuffer sql)
			throws Exception {
		// SQL语句输入Debug日志。
		MyLogger.debug(sql.toString());

		run = new QueryRunner();
		return run.query(this.conn, formartSQL(sql.toString()),
				new ArrayListHandler());
	}

	/**
	 * 数据查询操作.<br>
	 * BeanHandler,将ResultSet中第一行的数据转化成类对象.<br>
	 * 使用举例：<br>
	 * TBL_USER resultBean;<br>
	 * StringBuffer sql;<br>
	 * ResultSetHandler<TBL_USER> rsh = new
	 * BeanHandler<TBL_USER>(TBL_USER.class);<br>
	 * resultBean = queryForBeanHandler(sql, rsh);
	 *
	 * @Method: queryForBeanHandler
	 * @Description: 将ResultSet中第一行的数据转化成类对象.
	 * @param sql
	 *            --SQL语句
	 * @param rsh
	 *            BeanHandler<T>
	 * @return pojo的对象 --返回值说明
	 * @throws Exception
	 * @author misty
	 * @date 2014年2月18日 下午12:08:01
	 */
	public <T> T queryForBeanHandler(StringBuffer sql, ResultSetHandler<T> rsh)
			throws Exception {
		// SQL语句输入Debug日志。
		MyLogger.debug(sql.toString());

		run = new QueryRunner();
		return run.query(this.conn, formartSQL(sql.toString()), rsh);
	}

	/**
	 * 数据查询操作.<br>
	 * BeanListHandler,将ResultSet中所有的数据转化成List，List中存放的是类对象.<br>
	 * 使用举例：<br>
	 * List<TBL_USER> resultBeanList;<br>
	 * StringBuffer sql;<br>
	 * ResultSetHandler<List<TBL_USER>> rsh = new
	 * BeanListHandler<TBL_USER>(TBL_USER.class);<br>
	 * resultBeanList = queryForBeanListHandler(sql, rsh);
	 *
	 * @Method: queryForBeanListHandler
	 * @Description: 将ResultSet中所有的数据转化成List
	 * @param sql      --SQL语句
	 * @param rsh      --BeanListHandler<T>
	 * @return pojo的List集合
	 * @throws Exception
	 * @author misty
	 * @date 2014年2月18日 下午12:10:24
	 */
	public <T> T queryForBeanListHandler(StringBuffer sql,
			ResultSetHandler<T> rsh) throws Exception {
		// SQL语句输入Debug日志。
		MyLogger.debug(sql.toString());

		run = new QueryRunner();
		return run.query(this.conn, formartSQL(sql.toString()), rsh);
	}

	/**
	 * 数据查询操作.<br>
	 * 将ResultSet中一条记录的其中某一列的数据存成String.<br>
	 * 使用举例：<br>
	 * String result;<br>
	 * StringBuffer sql = new
	 * StringBuffer("SELECT USER_NAME FROM TBL_USER WHERE USER_ID='admin'");<br>
	 * result = queryForString(sql);
	 *
	 * @Method: queryForString
	 * @Description: 将ResultSet中一条记录的其中某一列的数据存成String.
	 * @param sql      --SQL语句
	 * @return String  --字符串结果
	 * @throws Exception
	 * @author misty
	 * @date 2014年2月18日 下午12:11:59
	 */
	public String queryForString(StringBuffer sql) throws Exception {
		// SQL语句输入Debug日志。
		MyLogger.debug(sql.toString());

		run = new QueryRunner();
		return run.query(this.conn, formartSQL(sql.toString()),
				new ScalarHandler<String>(1));
	}

	/**
	 * 数据查询操作.<br>
	 * 将ResultSet中一条记录的其中某一列的数据存成Integer.<br>
	 * 使用举例：<br>
	 * int result;<br>
	 * StringBuffer sql = new
	 * StringBuffer("SELECT COUNT(SUER_ID) FROM TBL_USER WHERE USER_LEVLE='1'");<br>
	 * result = queryForInteger(sql);
	 *
	 * @Method: queryForInteger
	 * @Description: 将ResultSet中一条记录的其中某一列的数据存成Integer.
	 * @param sql      --SQL语句
	 * @return int     --整数结果
	 * @throws Exception
	 * @author misty
	 * @date 2014年2月18日 下午12:13:16
	 */
	public int queryForInteger(StringBuffer sql) throws Exception {
		// SQL语句输入Debug日志。
		MyLogger.debug(sql.toString());

		run = new QueryRunner();
		return (run.query(this.conn, formartSQL(sql.toString()),
				new ScalarHandler<Long>(1))).intValue();
	}

	public Double queryForDouble(StringBuffer sql) throws Exception {
        // SQL语句输入Debug日志。
        MyLogger.debug(sql.toString());

        run = new QueryRunner();
        return (run.query(this.conn, formartSQL(sql.toString()),
                new ScalarHandler<Double>(1))).doubleValue();
    }

	public int queryForInteger(String sql) throws Exception {
		// SQL语句输入Debug日志。
		MyLogger.debug(sql);

		run = new QueryRunner();
		return (run.query(this.conn, formartSQL(sql),
				new ScalarHandler<Long>(1))).intValue();
	}

	/**
	 * 数据查询操作.<br>
	 * 将ResultSet中一条记录的其中某一列的数据存成Object.<br>
	 * 使用举例：<br>
	 * Object result;<br>
	 * StringBuffer sql = new
	 * StringBuffer("SELECT COUNT(SUER_ID) FROM TBL_USER WHERE USER_LEVLE='1'");<br>
	 * result = queryForObject(sql);
	 *
	 * @Method: queryForObject
	 * @Description: 将ResultSet中一条记录的其中某一列的数据存成Object
	 * @param sql     --SQL语句
	 * @return Object -- 对象结果
	 * @throws Exception
	 * @author misty
	 * @date 2014年2月18日 下午12:13:16
	 */
	public Object queryForObject(StringBuffer sql) throws Exception {
		// SQL语句输入Debug日志。
		MyLogger.debug(sql.toString());

		run = new QueryRunner();
		return run.query(this.conn, formartSQL(sql.toString()),
				new ScalarHandler<Object>(1));
	}

	/**
	 * @Method: executeSQL
	 * @Description: 执行SQL语句.
	 * @param sql      --SQL语句
	 * @return         --执行完成的数据条数
	 * @throws Exception
	 * @author misty
	 * @date 2014年2月18日 下午12:16:48
	 */
	public int executeSQL(StringBuffer sql) throws Exception {
		// SQL语句输入Debug日志。
		MyLogger.debug(sql.toString());

		run = new QueryRunner();
		return run.update(this.conn, formartSQL(sql.toString()));

	}

	/**
	 * @Method: formartSQL
	 * @Description: 格式化sql语句。
	 * @param sql      --SQL语句
	 * @return String  --字符串
	 * @author misty
	 * @date 2014年2月18日 下午12:17:48
	 */
	private String formartSQL(String sql) {
		return sql;
	}

}
