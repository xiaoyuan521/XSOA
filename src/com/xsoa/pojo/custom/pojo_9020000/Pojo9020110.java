package com.xsoa.pojo.custom.pojo_9020000;

import com.xsoa.pojo.basetable.Pojo_DLRZ;

public class Pojo9020110 extends Pojo_DLRZ {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 开始时间 property.*/
	private String DLRZ_KSSJ;
	
	/**The value of the simple 结束时间 property.*/
	private String DLRZ_JSSJ;

	/**
	* Simple constructor of Abstract BeanPojo9020110 instances.
	*/
	public Pojo9020110(){
	}

	/**
	*Return the value of the 开始时间 column.
	*
	*@return java.lang.String
	*/
	public String getDLRZ_KSSJ(){
		return this.DLRZ_KSSJ;
	}

	/**
	*Return the value of the 结束时间 column.
	*
	*@return java.lang.String
	*/
	public String getDLRZ_JSSJ(){
		return this.DLRZ_JSSJ;
	}
	
	/**
	*Set the value of the 开始时间 column.
	*
	*@param String DLRZ_KSSJ 
	*/
	public void setDLRZ_KSSJ(String DLRZ_KSSJ){
		this.DLRZ_KSSJ=DLRZ_KSSJ;
	}
	
	/**
	*Set the value of the 结束时间 column.
	*
	*@param String DLRZ_JSSJ 
	*/
	public void setDLRZ_JSSJ(String DLRZ_JSSJ){
		this.DLRZ_JSSJ=DLRZ_JSSJ;
	}
}