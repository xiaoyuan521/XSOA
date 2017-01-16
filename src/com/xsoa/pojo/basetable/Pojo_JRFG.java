package com.xsoa.pojo.basetable;

import java.io.Serializable;

public class Pojo_JRFG implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value of the simple 法官ID property. */
	private String JRFG_FGID;

	/** The value of the simple 备用法官ID property. */
    private String JRFG_BYID;

	/** The value of the simple 更新时间 property. */
	private String JRFG_GXSJ;

    public String getJRFG_FGID() {
        return JRFG_FGID;
    }

    public void setJRFG_FGID(String jRFG_FGID) {
        JRFG_FGID = jRFG_FGID;
    }

    public String getJRFG_BYID() {
        return JRFG_BYID;
    }

    public void setJRFG_BYID(String jRFG_BYID) {
        JRFG_BYID = jRFG_BYID;
    }

    public String getJRFG_GXSJ() {
        return JRFG_GXSJ;
    }

    public void setJRFG_GXSJ(String jRFG_GXSJ) {
        JRFG_GXSJ = jRFG_GXSJ;
    }

}
