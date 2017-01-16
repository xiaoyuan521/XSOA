package com.xsoa.pojo.basetable;

import java.io.Serializable;

public class Pojo_LTJY implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 用户ID property. */
    private String LTJY_YHID;

	/** The value of the simple 经验值 property. */
	private Integer LTJY_JYZ;


	/** The value of the simple 删除标志（0：未删除，1：已删除） property. */
	private String LTJY_SCBZ;

	/**
	 * Simple constructor of Abstract BeanPojo_LTJY instances.
	 */
	public Pojo_LTJY() {
	}

    public String getLTJY_YHID() {
        return LTJY_YHID;
    }

    public void setLTJY_YHID(String lTJY_YHID) {
        LTJY_YHID = lTJY_YHID;
    }

    public Integer getLTJY_JYZ() {
        return LTJY_JYZ;
    }

    public void setLTJY_JYZ(Integer lTJY_JYZ) {
        LTJY_JYZ = lTJY_JYZ;
    }

    public String getLTJY_SCBZ() {
        return LTJY_SCBZ;
    }

    public void setLTJY_SCBZ(String lTJY_SCBZ) {
        LTJY_SCBZ = lTJY_SCBZ;
    }

}
