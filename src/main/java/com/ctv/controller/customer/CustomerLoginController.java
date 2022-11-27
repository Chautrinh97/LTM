package com.ctv.controller.customer;

import com.ctv.model.bo.CustomerBO;

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
	private final CustomerBO customerBO = new CustomerBO();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.removeAttribute("postData");
		customerBO.showLoginForm(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		if (account != null) {
			customerBO.authenticate(request, response);
		} else {
			customerBO.showLoginForm(request, response);
		}
	}

}
