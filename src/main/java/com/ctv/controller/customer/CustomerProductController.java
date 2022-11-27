package com.ctv.controller.customer;

import com.ctv.model.bo.CartBO;
import com.ctv.model.bo.ProductBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CustomerProductController", value = "/products/*")
public class CustomerProductController
		extends HttpServlet {
	private final ProductBO productBO = new ProductBO();
	private CartBO cartBO;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		cartBO = new CartBO();
		cartBO.initCart(request, response);
		// Search
		if (request.getRequestURI().equals(request.getContextPath() + "/products/search")) productBO.search(request, response);
		else {
			productBO.viewProducts(request, response);
		}

	}

}
