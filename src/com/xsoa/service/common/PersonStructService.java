package com.xsoa.service.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.framework.core.BaseService;
import com.framework.dao.DBManager;
import com.framework.log.MyLogger;
import com.xsoa.pojo.common.Pojo_TREE_NODE;

/**	
 * @ClassName: PersonStructService	
 * @Package:com.xsoa.service.common	
 * @Description: 员工结构Service	
 * @author ljg	
 * @date 2015年2月11日 上午9:15:20	
 * @update	
 */
public class PersonStructService extends BaseService {
	private DBManager db = null;

	public PersonStructService() {
		db = new DBManager();
	}
	
	/**	
	 * @FunctionName: getBMList	
	 * @Description: 取得部门信息列表	
	 * @return
	 * @throws Exception	
	 * @return List<Pojo_TREE_NODE>	
	 * @author ljg	
	 * @date 2015年2月11日 上午9:20:22	
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
			sql.append("        'true' AS isParent,");
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
	
	public ArrayList<Object> getZWList(String BMID,String JSID) throws Exception {
		ArrayList<Object> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.ZWXX_ZWID AS id,");
			sql.append("        A.ZWXX_ZWMC AS name,");
			sql.append("        '").append(JSID).append("' AS pId,");
			sql.append("        'CMD_ZW' AS type,");
			sql.append("        'true' AS isParent,");
			sql.append("        '' AS t");
			sql.append("   FROM ZWXX A ");
			sql.append("  WHERE A.ZWXX_BMID = '").append(BMID).append("'");
			sql.append("    AND A.ZWXX_JSID = '").append(JSID).append("'");
			sql.append("  ORDER BY A.ZWXX_ZWID ");
			
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
	
	public ArrayList<Object> getYGList(String ZWID) throws Exception {
		ArrayList<Object> result = null;
		
		try {
			db.openConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT A.YGXX_YGID AS id,");
			sql.append("        A.YGXX_XM AS name,");
			sql.append("        '").append(ZWID).append("' AS pId,");
			sql.append("        'CMD_YG' AS type,");
			sql.append("        'false' AS isParent,");
			sql.append("        '' AS t");
			sql.append("   FROM YGXX A ");
			sql.append("   INNER JOIN YGZW AS B ON A.YGXX_YGID = B.YGZW_YGID ");
			sql.append("  WHERE B.YGZW_ZWID = '").append(ZWID).append("'");
			sql.append("  ORDER BY A.YGXX_YGID ");
			
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
	
	
	
}
