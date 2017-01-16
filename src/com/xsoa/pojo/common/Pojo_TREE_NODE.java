package com.xsoa.pojo.common;

import java.io.Serializable;

/**
 * @ClassName: Pojo_TREE_NODE
 * @Package:com.xsoa.pojo.common
 * @Description: 树结点Pojo
 * @author ljg
 * @date 2015年2月10日 下午5:27:27
 * @update
 */
public class Pojo_TREE_NODE implements Serializable {
	private static final long serialVersionUID = 1L;
	/** The value of the simple 节点id property. */
	private String id;

	/** The value of the simple 父节点id property. */
	private String pId;

	/** The value of the simple 节点类型 property. */
	private String type;

	/** The value of the simple 节点显示名 property. */
	private String name;

	/** The value of the simple 提示 property. */
	private String t;
	
	/** The value of the simple 提示 property. */
	private String isParent;


	/**
	 * Return the value of the 节点id column.
	 *
	 * @return java.lang.String
	 */
	public String getid() {
		return this.id;
	}

	/**
	 * Return the value of the 父节点id column.
	 *
	 * @return java.lang.String
	 */
	public String getpId() {
		return this.pId;
	}

	/**
	 * Return the value of the 节点类型 column.
	 *
	 * @return java.lang.String
	 */
	public String gettype() {
		return this.type;
	}

	/**
	 * Return the value of the 节点显示名 column.
	 *
	 * @return java.lang.String
	 */
	public String getname() {
		return this.name;
	}

	/**
	 * Return the value of the 提示 column.
	 *
	 * @return java.lang.String
	 */
	public String gett() {
		return this.t;
	}
	
	/**
	 * Return the value of the 是否父节点 column.
	 *
	 * @return java.lang.String
	 */
	public String getisParent() {
		return this.isParent;
	}

	/**
	 * Set the value of the 节点id column.
	 *
	 * @param String
	 *            ID
	 */
	public void setid(String ID) {
		this.id = ID;
	}

	/**
	 * Set the value of the 父节点id column.
	 *
	 * @param String
	 *            PID
	 */
	public void setpId(String PID) {
		this.pId = PID;
	}

	/**
	 * Set the value of the 节点类型 column.
	 *
	 * @param String
	 *            TYPE
	 */
	public void settype(String TYPE) {
		this.type = TYPE;
	}

	/**
	 * Set the value of the 节点显示名 column.
	 *
	 * @param String
	 *            NAME
	 */
	public void setname(String NAME) {
		this.name = NAME;
	}

	/**
	 * Set the value of the 提示 column.
	 *
	 * @param String
	 *            T
	 */
	public void sett(String T) {
		this.t = T;
	}
	
	/**
	 * Set the value of the 是否父节点 column.
	 *
	 * @param String
	 *            IsParent
	 */
	public void setisParent(String IsParent) {
		this.isParent = IsParent;
	}

}
