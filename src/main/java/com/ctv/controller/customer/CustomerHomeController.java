package com.ctv.controller.customer;

import com.ctv.model.bean.Customer;
import com.ctv.model.bo.CartBO;
import com.ctv.model.bo.ProductBO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerHomeController", value = "")
public class CustomerHomeController
		extends HttpServlet {

	private final ProductBO productBO = new ProductBO();
	private CartBO cartBO;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cartBO = new CartBO();
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {

			session.setAttribute("customer", session.getAttribute("substituteCustomer"));
		}
		session.removeAttribute("substituteCustomer");
		session.removeAttribute("oldCustomer");
		cartBO.initCart(request, response);
		productBO.listProductAndCategory(request, response);
	}

}
