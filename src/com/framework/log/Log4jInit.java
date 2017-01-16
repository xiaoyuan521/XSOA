package com.framework.log;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() {
		/*String strRealPath = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j");
		String flag = getInitParameter("flag");
		String log4jConfigFilePath = strRealPath + file;

		if (log4jConfigFilePath != null) {
			DOMConfigurator.configureAndWatch(log4jConfigFilePath);
			MyLogger.init();
			System.out.println("#####Log4j init OK.["+flag+"]#####");
		}*/
		String file = getInitParameter("log4j");
		String flag = getInitParameter("flag");
		if (file != null) {
			Properties ps = new Properties();
			try {
				ps.load(getServletContext().getResourceAsStream(file));
			} catch (IOException e) {
				System.out.println("log4j load error:" + e.getMessage());
			}
			PropertyConfigurator.configure(ps);
			System.out.println("#####Log4j init OK.["+flag+"]#####");
			MyLogger.init();
			MyLogger.info("★★★★★系统启动成功V1.2★★★★★");
		}
	}

}