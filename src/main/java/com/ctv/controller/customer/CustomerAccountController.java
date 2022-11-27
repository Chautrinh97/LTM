package com.ctv.controller.customer;


import com.ctv.model.bo.CustomerBO;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerAccountController", value = "/user/account")
public class CustomerAccountController
		extends HttpServlet {

	private final CustomerBO customerBO = new CustomerBO();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tab = request.getParameter("tab");
		if (tab != null) {
			customerBO.showManageInformationPage(request, response);

		} else response.sendRedirect(request.getContextPath() + "/user/account?tab=profile");
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String action = request.getParameter("action");
		switch (action) {
			case "updateProfile":
				customerBO.updateProfile(request, response);
				break;
			case "changePassword":
				customerBO.changePassword(request, response);
				break;

			case "updateAddress":
				customerBO.updateAddress(request, response);
				break;

		}
	}


}




