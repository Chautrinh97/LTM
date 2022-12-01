package com.ctv.controller.admin;

import com.ctv.model.bo.CategoryBO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ManageCategoriesController", value = "/admin/categories/*")
public class ManageCategoriesController
		extends HttpServlet {
	private final CategoryBO categoryBO = new CategoryBO();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		categoryBO.listCategory(request, response);
	}


	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		switch (action) {
			case "create":
				categoryBO.create(request, response);
				break;
			case "update":
				categoryBO.update(request, response);
				break;
			case "delete":
				categoryBO.delete(request, response);
		}
	}

}