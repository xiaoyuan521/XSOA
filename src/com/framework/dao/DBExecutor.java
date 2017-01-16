package com.framework.dao;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import com.framework.log.MyLogger;

public class DBExecutor {

	public DBExecutor() {

	}

	/**
	 * Call the given procedure statement, which have some input/output
	 * parameter.
	 * 
	 * @param procedureName
	 *            an procedure name.
	 * @param returnValueType
	 *            an function return value type.
	 * @param inputParameterValueList
	 *            an function input parameter list.
	 * @return HashMap<String,Object> procedure output parameters.
	 *         eg.{outParam1=xxx,outParam2=xxx,......}
	 * @exception Exception
	 *                if a database access error occurs.
	 * 
	 * @author ljg
	 */
	public HashMap<String, Object> callProcedure(String procedureName,
			List<Object> inputParameterValueList,
			List<Integer> outParameterTypeList) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		DBManager db = null;
		Connection conn = null;
		CallableStatement cstmt = null;

		try {

			// Create data base connection.
			db = new DBManager();
			conn = db.openConnection();

			// Create sql.
			StringBuffer strbuf = new StringBuffer();

			strbuf.append("{call " + procedureName + "(");

			if (inputParameterValueList != null
					&& inputParameterValueList.size() > 0) {
				for (int i = 0; i < inputParameterValueList.size(); i++) {
					strbuf.append("?,");
				}
			}
			if (outParameterTypeList != null && outParameterTypeList.size() > 0) {
				for (int i = 0; i < outParameterTypeList.size(); i++) {
					strbuf.append("?,");
				}
			}

			if (strbuf.lastIndexOf(",") != -1) {
				strbuf.deleteCharAt(strbuf.lastIndexOf(","));
			}
			strbuf.append(")}");

			// Create CallableStatement.
			cstmt = conn.prepareCall(strbuf.toString());

			if (inputParameterValueList != null
					&& inputParameterValueList.size() > 0) {
				for (int i = 0; i < inputParameterValueList.size(); i++) {
					cstmt.setObject(i + 1, inputParameterValueList.get(i));
				}
			}

			if (outParameterTypeList != null && outParameterTypeList.size() > 0) {
				for (int i = 0; i < outParameterTypeList.size(); i++) {
					cstmt.registerOutParameter(
							i + inputParameterValueList.size() + 1,
							outParameterTypeList.get(i));
				}
			}

			cstmt.execute();

			// Get out parameter.
			if (outParameterTypeList != null && outParameterTypeList.size() > 0) {
				for (int i = 0; i < outParameterTypeList.size(); i++) {
					result.put(
							"outParam" + (i + 1),
							cstmt.getObject(inputParameterValueList.size() + i
									+ 1));
				}
			}

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}

	/**
	 * Call the given procedure statement, which have some input/output
	 * parameter.
	 * 
	 * @param procedureName
	 *            an procedure name.
	 * @param returnValueType
	 *            an function return value type.
	 * @param inputParameterValueList
	 *            an function input parameter list.
	 * @return HashMap<String,Object> procedure output parameters.
	 *         eg.{outParam1=xxx,outParam2=xxx,......}
	 * @exception Exception
	 *                if a database access error occurs.
	 * 
	 * @author ljg
	 */
	public HashMap<String, Object> callProcedureClob(String procedureName,
			List<Object> inputParameterValueList,StringReader sr,
			List<Integer> outParameterTypeList) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		DBManager db = null;
		Connection conn = null;
		CallableStatement cstmt = null;

		try {

			// Create data base connection.
			db = new DBManager();
			conn = db.openConnection();

			// Create sql.
			StringBuffer strbuf = new StringBuffer();

			strbuf.append("{call " + procedureName + "(");

			if (inputParameterValueList != null
					&& inputParameterValueList.size() > 0) {
				for (int i = 0; i < inputParameterValueList.size(); i++) {
					strbuf.append("?,");
				}
			}
			if(sr !=null){//CLOB
				strbuf.append("?,");
			}
			if (outParameterTypeList != null && outParameterTypeList.size() > 0) {
				for (int i = 0; i < outParameterTypeList.size(); i++) {
					strbuf.append("?,");
				}
			}

			if (strbuf.lastIndexOf(",") != -1) {
				strbuf.deleteCharAt(strbuf.lastIndexOf(","));
			}
			strbuf.append(")}");

			// Create CallableStatement.
			cstmt = conn.prepareCall(strbuf.toString());

			if (inputParameterValueList != null
					&& inputParameterValueList.size() > 0) {
				for (int i = 0; i < inputParameterValueList.size(); i++) {
					cstmt.setObject(i + 1, inputParameterValueList.get(i));
				}
			}
			
			if(sr !=null){
				//cstmt.setClob(inputParameterValueList.size() + 1, sr);
				cstmt.setCharacterStream(inputParameterValueList.size() + 1, sr);
			}

			if (outParameterTypeList != null && outParameterTypeList.size() > 0) {
				for (int i = 0; i < outParameterTypeList.size(); i++) {
					cstmt.registerOutParameter(
							i + inputParameterValueList.size() + 2,
							outParameterTypeList.get(i));
				}
			}

			cstmt.execute();

			// Get out parameter.
			if (outParameterTypeList != null && outParameterTypeList.size() > 0) {
				for (int i = 0; i < outParameterTypeList.size(); i++) {
					result.put(
							"outParam" + (i + 1),
							cstmt.getObject(inputParameterValueList.size() + i
									+ 1));
				}
			}

		} catch (Exception e) {
			MyLogger.error(this.getClass().getName(), e);
			throw e;
		} finally {
			db.closeConnection();
		}

		return result;
	}
}
