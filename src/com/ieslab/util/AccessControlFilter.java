package com.ieslab.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;


/**
 *访问控制Filter，实现对于基于URL的各种访问权限的控制
 * @author lihao
 * @version 1.0
 */
public class AccessControlFilter implements Filter {
	
  public AccessControlFilter() {
  }

  public void init(FilterConfig filterConfig){
  }

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String context = ((HttpServletRequest) request).getContextPath();
		String url = ((HttpServletRequest) request).getRequestURI().substring(context.length());

		HttpServletResponse hsr = (HttpServletResponse) response;
		hsr.setHeader("pragma", "no-cache");  
		hsr.setHeader("cache-control", "no-cache");  
		
		String pageStyle = null;
		Cookie[] cookiestr = ((HttpServletRequest) request).getCookies();
		if(cookiestr!=null){
			for(Cookie cookie : cookiestr){
				if(cookie.getName().equals("pageStyle")){
					pageStyle = cookie.getValue();
				}
			}
		}
		if(pageStyle!=null && !pageStyle.equals("theme_dark")){
			if(!url.equals("/view.do") && url.indexOf("theme_dark") > 0 ){
				url = url.replaceAll("theme_dark", pageStyle);
				File t_file = new File(GlobalPathUtil.path + url);
				if(t_file.exists()){
					hsr.sendRedirect(context + url);
					return;
				}
			}
		}

		if (url.equals("/") || url.indexOf("login") >= 0 || url.indexOf("index") >= 0 ) {
			chain.doFilter(request, response);
			return;
		}else{
			if(!SecurityUtils.getSubject().isAuthenticated() && url.indexOf("view") >= 0){
				hsr.sendRedirect(context+"/login.jsp");
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

  public void destroy() {
  }
}
