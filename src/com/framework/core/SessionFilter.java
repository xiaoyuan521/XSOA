package com.framework.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.framework.session.SessionAttribute;

public class SessionFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		httpResponse.setHeader("Pragma", "No-Cache");
		httpResponse.setHeader("Cache-Control", "No-Cache");
		httpResponse.setDateHeader("Expires", 0);

		String contextPath = httpRequest.getContextPath();
		String uri = httpRequest.getRequestURI();
		String urisub = uri.substring(contextPath.length());
		HttpSession session = httpRequest.getSession();

		// 首页、登录页、系统超时页。不进行session超时校验。
		///
		if (urisub.equals("/") || urisub.equals("/LoginServlet")
				|| urisub.equals("/bin/index.jsp")
				|| urisub.equals("/bin/jsp/login/login.jsp")) {
			chain.doFilter(request, response);
		} else {
			Object user = session.getAttribute(SessionAttribute.LOGIN_USER);
			if (user == null) {
				request.getRequestDispatcher("/bin/jsp/timeout/timeout.jsp")
						.forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
			
		}
		

	}

	public void destroy() {

	}
}