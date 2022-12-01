package com.ctv.controller.customer;

import com.ctv.model.bean.CartItem;
import com.ctv.model.bean.Category;
import com.ctv.model.bean.Product;
import com.ctv.model.bo.CartBO;
import com.ctv.model.bo.CategoryBO;
import com.ctv.model.bo.ProductBO;
import com.ctv.util.RequestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerProductController", value = "/products/*")
public class CustomerProductController
		extends HttpServlet {
	private final int NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE = 20;
	private HttpSession session;
	private final ProductBO productBO = new ProductBO();
	private final CategoryBO categoryBO = new CategoryBO();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initCart(request, response);
		// Search
		if (request.getRequestURI().equals(request.getContextPath() + "/products/search")) search(request, response);
		else {
			viewProducts(request, response);
		}

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
	public void viewProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String pageParam = request.getParameter("page");
		String categoryName = request.getParameter("category");
		//
		if (pageParam == null && categoryName == null) {
			if (request.getRequestURI().equals(request.getContextPath() + "/products")) {
				response.sendRedirect(request.getContextPath());

			} else {
				viewProductDetail(request, response);
			}
		} else {
			Integer categoryId;
			if (categoryName != null) {
				Category category = categoryBO.find(categoryName);
				if (category != null)
					categoryId = category.getCategoryId();
				else categoryId = null;
			} else categoryId = null;
			List<Product> productList;
			// Lấy sortBy, order
			String sortBy = getSortBy(request);
			String order = request.getParameter("order");
			// Lấy page (lấy phần tử bắt đầu)
			int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE);
			productList = productBO.get(begin, NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE, categoryId, sortBy, order);

			request.setAttribute("productList", productList);
			List<Category> categoryList = categoryBO.getAll();
			request.setAttribute("categoryList", categoryList);
			int numberOfPages = (productBO.count(categoryId) - 1) / NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE + 1;
			request.setAttribute("numberOfPages", numberOfPages);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
			dispatcher.forward(request, response);
		}
	}
	private String getSortBy(HttpServletRequest request) {
		String sortBy = request.getParameter("sortBy");
		if (sortBy != null) {
			switch (sortBy) {
				case "price":
					sortBy = "price";
					break;
				default:
					sortBy = null;
					break;
			}
		}
		return sortBy;
	}
	private void viewProductDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String requestURI = request.getPathInfo(); // chuỗi kq sẽ là như này "/ten-san-pham"
		String productURI = requestURI.substring(1);
		Product product = productBO.get(productURI);
		request.setAttribute("product", product);
		if (product != null) {
			Category category = product.getCategory();
			Integer categoryId;
			if (category != null) {
				categoryId = category.getCategoryId();
			} else categoryId = null;
			List<Product> similarProducts = productBO.get(0, 6, categoryId, null, null);
			similarProducts.remove(product);
			request.setAttribute("similarProducts", similarProducts);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/product.jsp");
		dispatcher.forward(request, response);
	}
	public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String sortBy = getSortBy(request);
		String order = request.getParameter("order");
		int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE);
		int minPrice;
		int maxPrice;
		if (request.getParameter("minPrice") != null) minPrice = Integer.parseInt(request.getParameter("minPrice"));
		else minPrice = 0;
		if (request.getParameter("maxPrice") != null) maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
		else maxPrice = Integer.MAX_VALUE;

		List<Product> productList;
		productList = productBO.get(begin, NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE, keyword, minPrice, maxPrice, sortBy, order);

		int numberOfPages = (productBO.count(keyword, minPrice, maxPrice) - 1) / NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("search", true);
		request.setAttribute("productList", productList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
		dispatcher.forward(request, response);
	}
}
