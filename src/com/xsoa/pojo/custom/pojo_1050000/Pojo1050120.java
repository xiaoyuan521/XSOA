package com.xsoa.pojo.custom.pojo_1050000;

import com.xsoa.pojo.basetable.Pojo_RBGL;

public class Pojo1050120 extends Pojo_RBGL {
	private static final long serialVersionUID = 1L;

	/**The value of the simple 申请人 property.*/
	private String SQR;
	
	/**The value of the simple 申请部门 property.*/
	private String SQBM;

	/**The value of the simple 审批人 property.*/
	private String SPR;
	
	/**The value of the simple 审批部门 property.*/
	private String SPBM;
	
	/**The value of the simple 日报状态 property.*/
	private String RBZT;

	/**
	* Simple constructor of Abstract BeanPojo1050120 instances.
	*/
	public Pojo1050120(){
	}

	/**
	*Return the value of the 申请人 column.
	*
	*@return java.lang.String
	*/
	public String getSQR(){
		return this.SQR;
	}

	/**
	*Return the value of the 申请部门 column.
	*
	*@return java.lang.String
	*/
	public String getSQBM(){
		return this.SQBM;
	}
	
	/**
	*Return the value of the 审批人 column.
	*
	*@return java.lang.String
	*/
	public String getSPR(){
		return this.SPR;
	}

	/**
	*Return the value of the 审批部门 column.
	*
	*@return java.lang.String
	*/
	public String getSPBM(){
		return this.SPBM;
	}
	
	/**
	*Return the value of the 日报状态 column.
	*
	*@return java.lang.String
	*/
	public String getRBZT(){
		return this.RBZT;
	}
	
	/**
	*Set the value of the 申请人 column.
	*
	*@param String SQR 
	*/
	public void setSQR(String SQR){
		this.SQR=SQR;
	}

	/**
	*Set the value of the 申请部门 column.
	*
	*@param String SQBM 
	*/
	public void setSQBM(String SQBM){
		this.SQBM=SQBM;
	}
	
	/**
	*Set the value of the 审批人 column.
	*
	*@param String SPR 
	*/
	public void setSPR(String SPR){
		this.SPR=SPR;
	}

	/**
	*Set the value of the 审批部门 column.
	*
	*@param String SPBM 
	*/
	public void setSPBM(String SPBM){
		this.SPBM=SPBM;
	}
	
	/**
	*Set the value of the 日报状态 column.
	*
	*@param String RBZT 
	*/
	public void setRBZT(String RBZT){
		this.RBZT=RBZT;
	}
	
}