package com.ctv.model.bo;

import com.ctv.model.dao.CategoryDAO;
import com.ctv.model.bean.Category;
import com.ctv.model.bean.Category;
import com.ctv.model.dao.CategoryDAO;
import com.ctv.util.RequestUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CategoryBO {
	private final int NUMBER_OF_RECORDS_PER_PAGE = 10;
	private CategoryDAO categoryDAO = new CategoryDAO();
	private HttpSession session;

	public void listCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String sortBy = getOrder(request);
		List<Category> categoryList;
		int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_PAGE);
		categoryList = categoryDAO.get(begin, NUMBER_OF_RECORDS_PER_PAGE, keyword, sortBy, null);
		int numberOfPages = (categoryDAO.count(keyword, null) - 1) / NUMBER_OF_RECORDS_PER_PAGE + 1;
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

		if (categoryDAO.find(categoryName) == null) {
			Category category = new Category(categoryName);
			categoryDAO.create(category);
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

		if (categoryDAO.find(categoryName) == null) {
			Category category = new Category(categoryId, categoryName);
			categoryDAO.update(category);
			session.setAttribute("successMessage", "Sửa doanh mục thành công");
		} else if (categoryDAO.find(categoryName).getCategoryId() != categoryId) {
			session.setAttribute("errorMessage", "Tên doanh mục đã tồn tại");
		} else {
			Category category = new Category(categoryId, categoryName);
			categoryDAO.update(category);
		}
		try {
			response.sendRedirect(request.getContextPath() + "/admin/categories");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));

		categoryDAO.delete(categoryId);
		session = request.getSession();
		session.setAttribute("successMessage", "Xóa doanh mục thành công");
		try {
			response.sendRedirect(request.getContextPath() + "/admin/categories");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
