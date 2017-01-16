package com.xsoa.pojo.custom.pojo_1050000;

import com.xsoa.pojo.basetable.Pojo_RBMX;

public class Pojo1050111 extends Pojo_RBMX {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 操作人姓名 property.*/
	private String RBMX_CZRXM;
	
	/**The value of the simple 操作方式 property.*/
	private String RBMX_CZFS;
	
	/**The value of the simple 日报状态 property.*/
	private String ZTMC;

	/**
	* Simple constructor of Abstract BeanPojo1050111 instances.
	*/
	public Pojo1050111(){
	}

	/**
	*Return the value of the 操作人姓名 column.
	*
	*@return java.lang.String
	*/
	public String getRBMX_CZRXM(){
		return this.RBMX_CZRXM;
	}

	/**
	*Return the value of the 操作方式 column.
	*
	*@return java.lang.String
	*/
	public String getRBMX_CZFS(){
		return this.RBMX_CZFS;
	}
	
	/**
	*Return the value of the 日报状态 column.
	*
	*@return java.lang.String
	*/
	public String getZTMC(){
		return this.ZTMC;
	}
	
	/**
	*Set the value of the 操作人姓名 column.
	*
	*@param String RBMX_CZRXM 
	*/
	public void setRBMX_CZRXM(String RBMX_CZRXM){
		this.RBMX_CZRXM=RBMX_CZRXM;
	}

	/**
	*Set the value of the 操作方式 column.
	*
	*@param String RBMX_CZFS 
	*/
	public void setRBMX_CZFS(String RBMX_CZFS){
		this.RBMX_CZFS=RBMX_CZFS;
	}
	
	/**
	*Set the value of the 日报状态 column.
	*
	*@param String ZTMC 
	*/
	public void setZTMC(String ZTMC){
		this.ZTMC=ZTMC;
	}
}