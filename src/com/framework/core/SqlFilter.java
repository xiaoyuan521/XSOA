package com.framework.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

public class SqlFilter implements Filter {

   private String inj_str;

   @Override
   public void init(FilterConfig arg0) throws ServletException {
      inj_str = "--|'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|; |or|-|+|,";
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse res = (HttpServletResponse) response;

      Iterator<String[]> values = req.getParameterMap().values().iterator();// 获取所有的表单参数
      while (values.hasNext()) {
         String[] value = (String[]) values.next();
         for (int i = 0, len = value.length; i < len; i++) {
            if (sql_inj(value[i])) {
               System.out.println("发现sql注入"+value[i]);
               ArrayList<Object> arrResult = new ArrayList<Object>();
               arrResult.add("SQL_INJ");
               res.getWriter().print(JSONArray.fromObject(arrResult).toString());
               return; 
            }
         }
      }
      chain.doFilter(request, response);
   }

   @Override
   public void destroy() {
      inj_str = null;
   }

   private boolean sql_inj(String str) {
      String[] inj_stra = inj_str.split("\\|");

      for (int i = 0, len = inj_stra.length; i < len; i++) {
         if (str.indexOf(" " + inj_stra[i] + " ") >= 0) {
            return true;
         }
      }
      return false;
   }
}
