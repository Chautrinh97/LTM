package com.ctv.controller.admin;

import com.ctv.model.bean.Admin;
import com.ctv.model.bo.AdminBO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "AdminController", value = "/admin")
public class AdminController
		extends HttpServlet {
	private final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	private final String ADMIN_HOME_SERVLET = "/admin";
	private final String MANAGE_ADMINS_SERVLET = "/admin/admins";
	private final AdminBO adminBO = new AdminBO();
	private HttpSession session;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin == null) {
			showLoginForm(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath() + "/admin/products");
		}

	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		authenticate(request, response);
	}
	public void showLoginForm(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Đặt headerAction là đăng nhập
		request.setAttribute("headerAction", "Đăng nhập");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login/login.jsp");
		dispatcher.forward(request, response);
	}
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		session = request.getSession();
		String from = request.getParameter("from");
		if (Objects.equals(from, "")) {
			from = request.getContextPath() + ADMIN_HOME_SERVLET;
		}
		String usernameOrEmail = request.getParameter("usernameOrEmail");
		String password = request.getParameter("password");

		// TH1: validate thành công
		Admin authenticatedAdmin;
		authenticatedAdmin = adminBO.validate(usernameOrEmail,password);
		if (authenticatedAdmin != null) {
			session.setAttribute("admin", authenticatedAdmin);
			// Chuyển về lại trang home (/admin)
			try {
				response.sendRedirect(from); // contextPath: link web
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			request.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/login/login.jsp");
				dispatcher.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}