package com.ctv.filter;

import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "CustomerFilter", urlPatterns = "/*")
public class CustomerFilter
		implements Filter {
	public static final String[] loginRequiredURLs = {
			"/user/account",
			"/user/purchase",
			"/checkout"
	};

	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(
			ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();
		String requestURL = httpServletRequest.getRequestURL().toString();
		String queryString = httpServletRequest.getQueryString();
		String servletPath = httpServletRequest.getServletPath();
		boolean isAdminRequest = servletPath.startsWith("/admin");
		boolean isLoggedIn = httpServletRequest.getSession().getAttribute("customer") != null;
		boolean isLoginRequest = servletPath.equals("/login");
		boolean isLoginPage = requestURL.endsWith("login.jsp");
		boolean isResourceRequest =
				((requestURL.endsWith(".css")) || (requestURL.endsWith(".js")) || (requestURL.endsWith(".jpg") || (requestURL.endsWith("favicon.ico"))));

		if ((!isAdminRequest) && isLogInRequired(servletPath) && (!isLoggedIn) && (!isLoginRequest) && (!isLoginPage) && (!isResourceRequest)) {
			String method = httpServletRequest.getMethod();
			if (method.equals("POST")) {
				session.setAttribute("postData", IOUtils.toString(request.getReader()));
				httpServletResponse.setStatus(307);
				httpServletResponse.setHeader("Location",
						httpServletRequest.getContextPath() + "/login?from=" + requestURL + (queryString == null ? "" : "?" + queryString));
			}
			else{
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login?from=" + requestURL + (queryString == null ? "" : "?" + queryString));
			}
		} else {
			httpServletRequest.setAttribute("uri", requestURL + (queryString==null?"":"?"+queryString));
			chain.doFilter(request, response);
		}

	}

	private boolean isLogInRequired(String servletPath) {
		for (String s : loginRequiredURLs) {
			if (servletPath.equals(s)) return true;
		}
		return false;
	}

	public void destroy() {
	}
}
