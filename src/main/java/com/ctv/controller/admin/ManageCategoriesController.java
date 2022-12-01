package com.ctv.controller.admin;

import com.ctv.model.bean.Category;
import com.ctv.model.bo.CategoryBO;
import com.ctv.model.dao.CategoryDAO;
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

@WebServlet(name = "ManageCategoriesController", value = "/admin/categories/*")
public class ManageCategoriesController
		extends HttpServlet {
	private final CategoryBO categoryBO = new CategoryBO();
	private final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	private HttpSession session;
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		listCategory(request, response);
	}
	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				create(request, response);
				break;
			case "update":
				update(request, response);
				break;
			case "delete":
				delete(request, response);
		}
	}
	public void listCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String sortBy = getOrder(request);
		List<Category> categoryList;
		int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_PAGE);
		categoryList = categoryBO.get(begin, NUMBER_OF_RECORDS_PER_PAGE, keyword, sortBy, null);
		int numberOfPages = (categoryBO.count(keyword, null) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("list", categoryList);
		request.setAttribute("requestURI", request.getRequestURI());
		request.setAttribute("tab", "categories");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}
	private String getOrder(HttpServletRequest request) {
		String sortBy = request.getParameter("sortBy");
		if (sortBy != null) {
			switch (sortBy) {
				case "default":
					sortBy = null;
					break;
				case "name":
					sortBy = "category_name";
					break;
			}
		}
		return sortBy;
	}
	public void create(HttpServletRequest request, HttpServletResponse response) {
		String categoryName = request.getParameter("categoryName");
		session = request.getSession();

		if (categoryBO.find(categoryName) == null) {
			categoryBO.create(categoryName);
			session.setAttribute("successMessage", "Thêm doanh mục thành công");

		} else {
			session.setAttribute("errorMessage", "Tên doanh mục đã tồn tại");
		}
		try {
			response.sendRedirect(request.getContextPath() + "/admin/categories");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void update(HttpServletRequest request, HttpServletResponse response) {
		String categoryName = request.getParameter("categoryName");
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		session = request.getSession();

		if (categoryBO.find(categoryName) == null) {
			Category category = new Category(categoryId, categoryName);
			categoryBO.update(categoryId,categoryName);
			session.setAttribute("successMessage", "Sửa doanh mục thành công");
		} else if (categoryBO.find(categoryName).getCategoryId() != categoryId) {
			session.setAttribute("errorMessage", "Tên doanh mục đã tồn tại");
		} else {
			categoryBO.update(categoryId, categoryName);
		}
		try {
			response.sendRedirect(request.getContextPath() + "/admin/categories");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));

		categoryBO.delete(categoryId);
		session = request.getSession();
		session.setAttribute("successMessage", "Xóa doanh mục thành công");
		try {
			response.sendRedirect(request.getContextPath() + "/admin/categories");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}