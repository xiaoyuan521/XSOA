package com.xsoa.service.service9010000;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.framework.util.MyStringUtils;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.pojo.common.Pojo_TREE_NODE;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010120;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010130;
import com.xsoa.pojo.custom.pojo_9010000.Pojo9010131;

public class Service9010130 extends BaseService {

	private DBManager db;

	public Service9010130() {
		db = new DBManager();
	}
	public ArrayList<Object> getArrayList(List<Pojo_TREE_NODE> listObj) throws Exception {
		ArrayList<Object> result = null;
		if (listObj != null && listObj.size() > 0) {
            result = new ArrayList<Object>();
            for (int i = 0, len = listObj.size(); i < len; i++) {
            	Pojo_TREE_NODE bmRecord = listObj.get(i);
            	result.add(bmRecord);
            }
		}
		return result;
	}
	/**
	 * @FunctionName: getBMList
	 * @Description: 取得部门信息列表
	 * @return
	 * @throws Exception
	 * @return List<Pojo_TREE_NODE>
	 * @author czl
	 * @date 2015-3-20
	 */
	public ArrayList<Object> getBMList() throws Exception {
		ArrayList<Object> result = null;

		try {
			db.openConnection();

			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.BMXX_BMID AS id,");
			sql.append("        A.BMXX_BMMC AS name,");
			sql.append("        '0' AS pId,");
			sql.append("        'CMD_BM' AS type,");
			sql.append("        'true' AS isParent,");
			sql.append("        '' AS t");
			sql.append("   FROM BMXX A ");
			sql.append("  ORDER BY A.BMXX_BMID ");

			ResultSetHandler<List<Pojo_TREE_NODE>> rs = new BeanListHandler<Pojo_TREE_NODE>(Pojo_TREE_NODE.class);
			List<Pojo_TREE_NODE> bminfo = db.queryForBeanListHandler(sql, rs);
			result = getArrayList(bminfo);

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return result;
	}
	/**
	 * @FunctionName: getJSList
	 * @Description: 根据部门id取得角色信息
	 * @param BMID-部门ID
	 * @return
	 * @throws Exception
	 * @return ArrayList<Object>
	 * @author ljg
	 * @date 2015年2月11日 下午1:16:15
	 */
	public ArrayList<Object> getJSList(String BMID) throws Exception {
		ArrayList<Object> result = null;

		try {
			db.openConnection();

			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.YHJS_JSID AS id,");
			sql.append("        A.YHJS_JSMC AS name,");
			sql.append("        '").append(BMID).append("' AS pId,");
			sql.append("        'CMD_JS' AS type,");
			sql.append("        'false' AS isParent,");
			sql.append("        '' AS t");
			sql.append("   FROM YHJS A ");
			sql.append("  WHERE A.YHJS_BMID = '").append(BMID).append("'");
			sql.append("  ORDER BY A.YHJS_JSID ");

			ResultSetHandler<List<Pojo_TREE_NODE>> rs = new BeanListHandler<Pojo_TREE_NODE>(Pojo_TREE_NODE.class);
			List<Pojo_TREE_NODE> bminfo = db.queryForBeanListHandler(sql, rs);
			result = getArrayList(bminfo);

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
	 * @FunctionName: getJsxxDataCount
	 * @Description: 获取角色信息数据个数
	 * @return
	 * @throws Exception
	 * @return int
	 * @author ztz
	 * @date 2015年2月2日 上午9:55:35
	 */
	public int getJsxxDataCount() throws Exception {
		int count = 0;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         COUNT(A.YHJS_JSID)  ");//角色信息个数
			strbuf.append(" FROM ");
			strbuf.append("         YHJS A ");

			count = db.queryForInteger(strbuf);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return count;
	}
	/**
	 *
	 * @FunctionName: getJsxxData
	 * @Description: 获取角色信息数据列表
	 * @param page
	 * @param limit
	 * @param sort
	 * @return
	 * @throws Exception
	 * @return List<Pojo9010120>
	 * @author ztz
	 * @date 2015年2月2日 上午9:55:52
	 */
	public List<Pojo9010120> getJsxxData(int page, int limit, String sort) throws Exception {
		List<Pojo9010120> jsxxList = null;

		try {
			db.openConnection();

			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("     A.YHJS_JSID, ");//角色ID
			strbuf.append("     A.YHJS_JSMC, ");//角色名称
			strbuf.append("     A.YHJS_BMID, ");//部门ID
			strbuf.append("     C.BMXX_BMMC, ");//部门名称
			strbuf.append("     A.YHJS_JSMS, ");//角色描述
			strbuf.append("     B.YHXX_YHMC AS YHJS_CJR, ");//创建人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.YHJS_CJSJ, '%Y%m%d'), '%Y-%m-%d') AS YHJS_CJSJ,  ");//创建时间
			strbuf.append("     B.YHXX_YHMC AS YHJS_GXR, ");//更新人
			strbuf.append("     DATE_FORMAT(STR_TO_DATE(A.YHJS_CJSJ, '%Y%m%d'), '%Y-%m-%d') AS YHJS_GXSJ,  ");//更新时间
			strbuf.append("     A.YHJS_SCBZ, ");//删除标志（0：未删除，1：已删除）
			strbuf.append("     CASE WHEN A.YHJS_SCBZ = '0' THEN '未删除' ELSE '已删除' END AS SCBZ ");//删除标志显示名称
			strbuf.append(" FROM ");
			strbuf.append("     YHJS A, YHXX B, BMXX C ");
			strbuf.append(" WHERE  ");
			strbuf.append("     A.YHJS_CJR = B.YHXX_YHID ");
			strbuf.append(" AND A.YHJS_GXR = B.YHXX_YHID ");
			strbuf.append(" AND A.YHJS_BMID = C.BMXX_BMID ");
			strbuf.append(" ORDER BY ");
			strbuf.append("     A.YHJS_CJSJ ");

			StringBuffer execSql = this.getPageSqL(strbuf.toString(), page, limit,
					sort);

			ResultSetHandler<List<Pojo9010120>> rsh = new BeanListHandler<Pojo9010120>(
					Pojo9010120.class);

			jsxxList = db.queryForBeanListHandler(execSql, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return jsxxList;
	}
	/**
	 *
	 * @FunctionName: getMenuList
	 * @Description: 获取菜单数据列表
	 * @param
	 * @throws Exception
	 * @return List<Pojo9010130>
	 * @author czl
	 * @date 2015-3-20
	 */
	public List<Pojo9010130> getMenuList() throws Exception {
		List<Pojo9010130> menuList = null;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         A.MENU_CDID, ");//菜单ID
			strbuf.append("         A.MENU_CDMC, ");//菜单名称
			strbuf.append("         A.MENU_CDCJ");//菜单层级（0-父节点 1-子节点）
			strbuf.append(" FROM ");
			strbuf.append("         MENU A");
			strbuf.append(" WHERE  ");
			strbuf.append(" A.MENU_SCBZ = '0' ");
			strbuf.append(" ORDER BY ");
			strbuf.append("         A.MENU_CDID ");

			ResultSetHandler<List<Pojo9010130>> rsh = new BeanListHandler<Pojo9010130>(
					Pojo9010130.class);
			menuList = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return menuList;
	}
	/**
	 *
	 * @FunctionName: getJsMenuList
	 * @Description: 获取角色菜单数据列表
	 * @param
	 * @throws Exception
	 * @return List<Pojo9010131>
	 * @author czl
	 * @date 2015-3-20
	 */
	public List<Pojo9010131> getJsMenuList(String jsid) throws Exception {
		List<Pojo9010131> menuList = null;
		try {
			db.openConnection();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(" SELECT ");
			strbuf.append("         A.JSQX_CDID ");//菜单ID
			strbuf.append(" FROM ");
			strbuf.append("         JSQX A,MENU B");
			strbuf.append(" WHERE  ");
			strbuf.append(" A.JSQX_CDID = B.MENU_CDID");
			strbuf.append(" AND A.JSQX_SCBZ = '0' AND B.MENU_SCBZ = '0'");
			strbuf.append(" AND A.JSQX_JSID = '").append(jsid).append("'");
			strbuf.append(" ORDER BY ");
			strbuf.append("         A.JSQX_JSID,A.JSQX_CDID ");

			ResultSetHandler<List<Pojo9010131>> rsh = new BeanListHandler<Pojo9010131>(
					Pojo9010131.class);
			menuList = db.queryForBeanListHandler(strbuf, rsh);
		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}
		return menuList;
	}
	/**
	 *
	 * @FunctionName: relationMenu
	 * @Description: 角色关联菜单
	 * @param jsid,cdids,beanUser
	 * @throws Exception
	 * @return int
	 * @author czl
	 * @date 2015-3-20
	 */
	public int relationMenu(String jsid, String cdids, Pojo_YHXX beanUser) throws Exception {
		int result = 0;
		try {
			db.openConnection();
			db.beginTran();
			/* 删除JSQX表数据Start */
			StringBuffer strbufDel = new StringBuffer();
			strbufDel.append(" DELETE FROM ");
			strbufDel.append("     JSQX ");
			strbufDel.append(" WHERE 1=1");
			strbufDel.append(" AND JSQX_JSID = '").append(jsid).append("'");
			result = db.executeSQL(strbufDel);
			/* 删除JSQX表数据End */
			if (MyStringUtils.isNotBlank(cdids)) {
				String[] cdid = cdids.split(",");
				for(int i = 0; i < cdid.length; i++) {
					StringBuffer strbuf = new StringBuffer();
					strbuf.append(" INSERT INTO ");
					strbuf.append("         JSQX ");
					strbuf.append(" ( ");
					strbuf.append("         JSQX_JSID, ");//角色ID
					strbuf.append("         JSQX_CDID, ");//菜单ID
					strbuf.append("         JSQX_SCBZ, ");//删除标志（0-未删除 1-已删除）
					strbuf.append("         JSQX_CJR, ");//创建人
					strbuf.append("         JSQX_CJSJ, ");//创建时间
					strbuf.append("         JSQX_GXR, ");//更新人
					strbuf.append("         JSQX_GXSJ  ");//更新时间
					strbuf.append(" ) ");
					strbuf.append(" VALUES ");
					strbuf.append(" ( ");
					strbuf.append("         '"+jsid+"', ");//角色ID
					strbuf.append("         '"+cdid[i]+"', ");//菜单ID
					strbuf.append("         '0', ");//删除标志（0-未删除 1-已删除）
					strbuf.append("         '"+beanUser.getYHXX_YHID()+"', ");//创建人
					strbuf.append("         DATE_FORMAT(now(),'%Y%m%d %H:%i:%s'), ");//创建时间
					strbuf.append("         '"+beanUser.getYHXX_YHID()+"', ");//更新人
					strbuf.append("         DATE_FORMAT(now(),'%Y%m%d %H:%i:%s')  ");//更新时间
					strbuf.append(" ) ");
					int iCont = db.executeSQL(strbuf);
					result = result + iCont;
				}
			}
			if(result>0){
				db.commit();
			}else{
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
}