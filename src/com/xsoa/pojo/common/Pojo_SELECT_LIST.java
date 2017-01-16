package com.xsoa.pojo.common;

import java.io.Serializable;

/**
 * @ClassName: SELECT_LIST
 * @Package:com.blank.pojo.common
 * @Description: 下拉列表键值对共通类
 * @author ljg
 * @date 2014年7月21日 上午9:12:55
 * @update
 */
public class Pojo_SELECT_LIST implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value of the simple 代码 property. */
	private String CODE;

	/** The value of the simple 名称 property. */
	private String NAME;

	/**
	 * Return the value of the 代码 column.
	 * 
	 * @return java.lang.String
	 */
	public String getCODE() {
		return this.CODE;
	}

	/**
	 * Return the value of the 名称 column.
	 * 
	 * @return java.lang.String
	 */
	public String getNAME() {
		return this.NAME;
	}

	/**
	 * Set the value of the 代码 column.
	 * 
	 * @param String
	 *            CODE
	 */
	public void setCODE(String CODE) {
		this.CODE = CODE;
	}

	/**
	 * Set the value of the 名称 column.
	 * 
	 * @param String
	 *            NAME
	 */
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

}
