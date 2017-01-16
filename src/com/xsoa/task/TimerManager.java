package com.xsoa.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerManager {

	//时间间隔
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

	public TimerManager(Timer timer) {
		Calendar calendar = Calendar.getInstance();

		/*** 定制每日0:00执行方法 ***/
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date date = calendar.getTime();//第一次执行定时任务的时间

		//如果第一次执行定时任务的时间 小于 当前的时间
		//此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行
		if (date.before(new Date())) {
			date = this.addDay(date, 1);
		}

		/* 执行定时更新法官 */
		DoTaskOne taskOne = new DoTaskOne();
		timer.schedule(taskOne, date, PERIOD_DAY);
	}

	public Date addDay(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, num);
		return calendar.getTime();
	}
}
