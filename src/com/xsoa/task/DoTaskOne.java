package com.xsoa.task;

import java.text.SimpleDateFormat;
import java.util.TimerTask;

import com.xsoa.pojo.basetable.Pojo_JRFG;
import com.xsoa.pojo.basetable.Pojo_YHXX;
import com.xsoa.service.service1040000.Service1040140;

public class DoTaskOne extends TimerTask {

	private Service1040140 service;

	@Override
	public void run() {
		service = new Service1040140();
		Pojo_JRFG dataBean = new Pojo_JRFG();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Pojo_YHXX pojo = service.getFgid();
			//Pojo_YHXX pojo1 = service.getFgid();
			//String time = sdf.format(new Date());
			//dataBean.setJRFG_FGID(pojo.getYHXX_YHID());
			//dataBean.setJRFG_BYID(pojo1.getYHXX_YHID());
			//dataBean.setJRFG_GXSJ(time);
			//boolean result = service.updateJrfg(dataBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
