package com.ctv.controller.customer;

import com.ctv.model.bean.CartItem;
import com.ctv.model.bean.Category;
import com.ctv.model.bean.Customer;
import com.ctv.model.bean.Product;
import com.ctv.model.bo.CartBO;
import com.ctv.model.bo.CategoryBO;
import com.ctv.model.bo.ProductBO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerHomeController", value = "")
public class CustomerHomeController
		extends HttpServlet {
	private HttpSession session;
	private final int NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE = 20;
	private final ProductBO productBO = new ProductBO();
	private final CategoryBO categoryBO = new CategoryBO();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {

			session.setAttribute("customer", session.getAttribute("substituteCustomer"));
		}
		session.removeAttribute("substituteCustomer");
		session.removeAttribute("oldCustomer");
		initCart(request, response);
		listProductAndCategory(request, response);
	}
	public void initCart(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession();
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		if (cart != null) {
			for (CartItem cartItem : cart) {
				cartItem.setProduct(productBO.get(cartItem.getProduct().getProductId()));
			}

		}
	}
	public void listProductAndCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Product> productList = productBO.get(0, NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE, null, null);
		int numberOfPages = (productBO.count() - 1) / NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		List<Category> categoryList = categoryBO.getAll();
		request.setAttribute("productList", productList);
		request.setAttribute("categoryList", categoryList);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
		requestDispatcher.forward(request, response);
	}
}
