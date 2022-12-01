package com.ctv.controller.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AdminLogOutController", value = "/admin/logout")
public class AdminLogOutController
		extends HttpServlet {
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/admin");
	}

}