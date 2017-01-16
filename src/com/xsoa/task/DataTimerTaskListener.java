package com.xsoa.task;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class DataTimerTaskListener implements ServletContextListener {

	private Timer timer = null;
	@Override
	public void contextDestroyed(ServletContextEvent event) {

		timer.cancel();
		event.getServletContext().log("停止定时器");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		event.getServletContext().log("启动定时器");
		timer = new Timer();
		new TimerManager(timer);
	}

}
