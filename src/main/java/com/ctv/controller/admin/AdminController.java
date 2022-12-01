package com.ctv.controller.admin;

import com.ctv.model.bean.Admin;
import com.ctv.model.bo.AdminBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminController", value = "/admin")
public class AdminController
		extends HttpServlet {
	private final AdminBO adminBO = new AdminBO();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin == null) {
			adminBO.showLoginForm(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/admin/products");
		}

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		adminBO.authenticate(request, response);
	}
}