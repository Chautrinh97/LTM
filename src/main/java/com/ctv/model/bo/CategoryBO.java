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
	private CategoryDAO categoryDAO = new CategoryDAO();

	public List<Category> get(int begin, int number_of_records_per_page, String keyword, String sortBy, String order) {
		return categoryDAO.get(begin, number_of_records_per_page,keyword,sortBy, order);
	}
	public Category get(int categoryId){
		return categoryDAO.get(categoryId);
	}
	public int count(String keyword, String field) {
		return categoryDAO.count(keyword, field);
	}

	public Category find(String categoryName) {
		return categoryDAO.find(categoryName);
	}

	public void create(String categoryName) {
		Category category = new Category(categoryName);
		categoryDAO.create(category);
	}
	public void update(int categoryId, String categoryName) {
		Category category = new Category(categoryId, categoryName);
        categoryDAO.update(category);
	}

	public void delete(int categoryId) {
		categoryDAO.delete(categoryId);
	}

	public List<Category> getAll(String orderBy, String order) {
		return categoryDAO.getAll(orderBy, order);
	}

	public List<Category> getAll() {
		return categoryDAO.getAll();
	}
}
