package com.ctv.model.bo;

import com.ctv.model.dao.CategoryDAO;
import com.ctv.model.dao.ImagePathDAO;
import com.ctv.model.dao.ProductDAO;
import com.ctv.model.bean.Category;
import com.ctv.model.bean.Product;
import com.ctv.util.RequestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProductBO {
	private static final String MANAGE_PRODUCTS_SERVLET = "/admin/products";
	private final int NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE = 20;
	final int NUMBER_OF_RECORDS_PER_MANAGE_PRODUCTS_PAGE = 10;
	private ProductDAO productDAO = new ProductDAO();
	private CategoryDAO categoryDAO = new CategoryDAO();
	private ImagePathDAO imagePathDAO = new ImagePathDAO();
	private HttpSession session;

	public void listProductAndCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Product> productList = productDAO.get(0, NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE, null, null);
		int numberOfPages = (productDAO.count() - 1) / NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		List<Category> categoryList = categoryDAO.getAll();
		request.setAttribute("productList", productList);
		request.setAttribute("categoryList", categoryList);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
		requestDispatcher.forward(request, response);
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
		productList = productDAO.get(begin, NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE, keyword, minPrice, maxPrice, sortBy, order);

		int numberOfPages = (productDAO.count(keyword, minPrice, maxPrice) - 1) / NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("search", true);
		request.setAttribute("productList", productList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
		dispatcher.forward(request, response);
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
				Category category = categoryDAO.find(categoryName);
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
			productList = productDAO.get(begin, NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE, categoryId, sortBy, order);

			request.setAttribute("productList", productList);
			List<Category> categoryList = categoryDAO.getAll();
			request.setAttribute("categoryList", categoryList);
			int numberOfPages = (productDAO.count(categoryId) - 1) / NUMBER_OF_RECORDS_PER_CUSTOMER_PRODUCT_PAGE + 1;
			request.setAttribute("numberOfPages", numberOfPages);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/home.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void viewProductDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                                IOException {
		String requestURI = request.getPathInfo(); // chuỗi kq sẽ là như này "/ten-san-pham"
		String productURI = requestURI.substring(1);
		Product product = productDAO.get(productURI);
		request.setAttribute("product", product);
		if (product != null) {
			Category category = product.getCategory();
			Integer categoryId;
			if (category != null) {
				categoryId = category.getCategoryId();
			} else categoryId = null;
			List<Product> similarProducts = productDAO.get(0, 6, categoryId, null, null);
			similarProducts.remove(product);
			request.setAttribute("similarProducts", similarProducts);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/home/product.jsp");
		dispatcher.forward(request, response);
	}
}
