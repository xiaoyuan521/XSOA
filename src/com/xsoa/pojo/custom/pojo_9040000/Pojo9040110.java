package com.xsoa.pojo.custom.pojo_9040000;

import com.xsoa.pojo.basetable.Pojo_BMXX;

public class Pojo9040110 extends Pojo_BMXX {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 删除标志（0：未删除，1：已删除） property.*/
	private String SCBZ;

	/**
	* Simple constructor of Abstract BeanPojo9010120 instances.
	*/
	public Pojo9040110(){
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
	*Set the value of the 删除标志（0：未删除，1：已删除） column.
	*
	*@param String SCBZ 
	*/
	public void setSCBZ(String SCBZ){
		this.SCBZ=SCBZ;
	}
}