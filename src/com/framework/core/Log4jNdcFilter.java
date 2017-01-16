package com.framework.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.NDC;

/**
 * Servlet Filter implementation class Log4jNdcFilter
 */
@WebFilter("/Log4jNdcFilter")
public class Log4jNdcFilter implements Filter {


	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 获得客户的网络地址
	    String address = request.getRemoteAddr();
	    // 把网络地址放入NDC中. 那么在在layout pattern 中通过使用 %x，就可在每条日之中增加网络地址的信息.
	    NDC.push(address);
	    //继续处理其他的filter链.
	    chain.doFilter(request, response);
	     // 从NDC的堆栈中删除网络地址.
	    NDC.pop();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
