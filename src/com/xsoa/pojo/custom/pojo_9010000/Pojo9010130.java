package com.xsoa.pojo.custom.pojo_9010000;

import com.xsoa.pojo.basetable.Pojo_MENU;

public class Pojo9010130 extends Pojo_MENU {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The value of the simple 是否关联 property. */
	private String MENU_SFGL;

	/** The value of the simple 关联状态 property. */
	private String MENU_GLZT;

	/**
	 * Simple constructor of Abstract BeanPojo_MENU instances.
	 */
	public Pojo9010130() {
	}

	/**
	 * Return the value of the 是否关联 column.
	 * 
	 * @return java.lang.String
	 */
	public String getMENU_SFGL() {
		return this.MENU_SFGL;
	}

	/**
	 * Return the value of the 关联状态 column.
	 * 
	 * @return java.lang.String
	 */
	public String getMENU_GLZT() {
		return this.MENU_GLZT;
	}

	/**
	 * Set the value of the 是否关联 column.
	 * 
	 * @param String
	 *            MENU_SFGL
	 */
	public void setMENU_SFGL(String MENU_SFGL) {
		this.MENU_SFGL = MENU_SFGL;
	}

	/**
	 * Set the value of the 关联状态 column.
	 * 
	 * @param String
	 *            MENU_GLZT
	 */
	public void setMENU_GLZT(String MENU_GLZT) {
		this.MENU_GLZT = MENU_GLZT;
	}
}
