package com.ctv.controller.customer;

import com.ctv.model.bo.CartBO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerCartController", value = "/user/cart")
public class CustomerCartController
		extends HttpServlet {
	private final CartBO cartBO = new CartBO();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cartBO.showManageCartPage(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
				case "add":
					cartBO.addToCart(request, response);
					break;
				case "update":
					cartBO.updateCartItem(request, response);
					break;
				case "delete":
					cartBO.deleteCartItem(request, response);
					break;
			}
		}
	}

}
