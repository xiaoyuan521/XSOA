package com.xsoa.pojo.custom.pojo_9010000;

import com.xsoa.pojo.basetable.Pojo_YHJS;

public class Pojo9010120 extends Pojo_YHJS {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 部门名称 property.*/
	private String BMXX_BMMC;

	/**The value of the simple 删除标志（0：未删除，1：已删除） property.*/
	private String SCBZ;

	/**
	* Simple constructor of Abstract BeanPojo9010120 instances.
	*/
	public Pojo9010120(){
	}

	/**
	*Return the value of the 部门名称 column.
	*
	*@return java.lang.String
	*/
	public String getBMXX_BMMC(){
		return this.BMXX_BMMC;
	}

	/**
	*Return the value of the 删除标志（0：未删除，1：已删除） column.
	*
	*@return java.lang.String
	*/
	public String getSCBZ(){
		return this.SCBZ;
	}

	/**
	*Set the value of the 部门名称 column.
	*
	*@param String BMXX_BMMC 
	*/
	public void setBMXX_BMMC(String BMXX_BMMC){
		this.BMXX_BMMC=BMXX_BMMC;
	}

	/**
	*Set the value of the 删除标志（0：未删除，1：已删除） column.
	*
	*@param String SCBZ 
	*/
	public void setSCBZ(String SCBZ){
		this.SCBZ=SCBZ;
	}
}