package com.ctv.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	public static int getBegin(HttpServletRequest request, int numberOfRecordsPerPage) {
		String pageParam = request.getParameter("page");
		int page;
		if (pageParam == null) {
			page = 1;
		} else {
			page = Integer.parseInt(pageParam);
		}
		return numberOfRecordsPerPage * (page - 1);
	}
}
