package com.ctv.controller.customer;

import com.ctv.model.bean.Customer;
import com.ctv.model.bo.CustomerBO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CustomerController", value = "/login")

public class CustomerLoginController
		extends HttpServlet {
	private final String CUSTOMER_LOGIN_PAGE = "/customer/login/login.jsp";
	private final CustomerBO customerBO = new CustomerBO();
	private HttpSession session;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		session = request.getSession();
		session.removeAttribute("postData");
		showLoginForm(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		if (account != null) {
			authenticate(request, response);
		} else {
			showLoginForm(request, response);
		}
	}
	public void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		request.setAttribute("headerAction", "Đăng nhập");
		RequestDispatcher dispatcher = request.getRequestDispatcher(CUSTOMER_LOGIN_PAGE);
		dispatcher.forward(request, response);
	}
	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		session = request.getSession();
		String from = request.getParameter("from");
		if (from.equals("")) {
			from = request.getContextPath();
		}
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		Customer customer = new Customer();
		boolean isPhoneNumber = (account.indexOf('@') == -1);
		if (isPhoneNumber) {
			customer.setPhoneNumber(account);
		} else {
			customer.setEmail(account);
		}
		customer.setPassword(password);

		Customer authenticatedCustomer;
		// TH1: tài khoản tồn tại
		authenticatedCustomer = customerBO.validate(customer);
		if (authenticatedCustomer != null) {
			session.setAttribute("customer", authenticatedCustomer);
			response.sendRedirect(from);
		} else {
			request.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
			showLoginForm(request, response);
		}
	}
}
