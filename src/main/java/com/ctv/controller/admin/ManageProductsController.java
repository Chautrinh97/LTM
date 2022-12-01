package com.ctv.controller.admin;

import com.ctv.model.bean.Category;
import com.ctv.model.bean.Product;
import com.ctv.model.bo.CategoryBO;
import com.ctv.model.bo.ProductBO;
import com.ctv.util.CaseUtils;
import com.ctv.util.PropertiesUtil;
import com.ctv.util.RequestUtils;
import com.ctv.util.UniqueStringUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "ManageProductsController", value = "/admin/products/*")
public class ManageProductsController
		extends HttpServlet {
	private static final String MANAGE_PRODUCTS_SERVLET = "/admin/products";
	final int NUMBER_OF_RECORDS_PER_MANAGE_PRODUCTS_PAGE = 10;
	private HttpSession session;

	private final ProductBO productBO = new ProductBO();
	private final CategoryBO categoryBO = new CategoryBO();

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null) {
			String path = "";
			List<Category> categoryList = categoryBO.getAll("category_name", "ASC");
			request.setAttribute("categoryList", categoryList);
			switch (action) {
				case "create":
					path = "/admin/manage/product/addForm.jsp";
					break;
				case "view":
					int id = Integer.parseInt(request.getParameter("id"));
					Product product = productBO.get(id);
					request.setAttribute("product", product);
					request.setAttribute("categoryList", categoryList);
					path = "/admin/manage/product/view_editForm.jsp";
					break;
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		} else {
			listProducts(request, response);
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				break;
		}
	}
	public void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String keyword = request.getParameter("keyword");
		String sortBy = getSortByForAdmin(request);
		List<Product> productList;
		int begin = RequestUtils.getBegin(request, NUMBER_OF_RECORDS_PER_MANAGE_PRODUCTS_PAGE);
		productList = productBO.get(begin, NUMBER_OF_RECORDS_PER_MANAGE_PRODUCTS_PAGE, keyword, sortBy, null);
		int numberOfPages = (productBO.count(keyword, null) - 1) / NUMBER_OF_RECORDS_PER_MANAGE_PRODUCTS_PAGE + 1;
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("tab", "products");
		request.setAttribute("list", productList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage/home.jsp");
		dispatcher.forward(request, response);
	}
	private String getSortByForAdmin(HttpServletRequest request) {
		String sortBy = request.getParameter("sortBy");
		if (sortBy != null) {
			switch (sortBy) {
				case "default":
					sortBy = null;
					break;
				case "name":
					sortBy = "product_name";
					break;
			}
		}
		return sortBy;
	}

	public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// Lấy danh sách tham số và chuyển về đối  tượng
		String name = request.getParameter("productName");
		String description = request.getParameter("description");
		String dimension = request.getParameter("dimension");
		String material = request.getParameter("material");
		int price = Integer.parseInt(request.getParameter("price"));
		String uri = CaseUtils.convert2KebabCase(name) + "-" + UniqueStringUtils.randomUUID(16);
		int warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));
		Category category = null;
		if (!Objects.equals(request.getParameter("categoryId"), "")) {
			category = categoryBO.get(Integer.parseInt(request.getParameter("categoryId")));
		}
		int productId = productBO.create(name, warrantyPeriod, description, dimension, material, price, category,
				uri).getProductId();

		String imageFolder = "images/products";
		for (Part part : request.getParts()) {
			if (part.getName().equals("images") && !Objects.equals(part.getSubmittedFileName(), "")) {
				String uniqueId = UUID.randomUUID().toString();
				String submittedFileName = part.getSubmittedFileName();
				String baseName = FilenameUtils.getBaseName(submittedFileName);
				String extensionName = FilenameUtils.getExtension(submittedFileName);
				String fileName = baseName + uniqueId + "." + extensionName;
				if (fileName.matches("[0-9]+.*")) fileName = "a" + fileName;
				part.write(PropertiesUtil.get("config.properties").getProperty("sourceImageFolder") + "\\" + fileName);
				part.write(PropertiesUtil.get("config.properties").getProperty("targetImageFolder") + "\\" + fileName);
				productBO.create(productId, imageFolder + "/" + fileName);
			}
		}
		request.getSession().setAttribute("successMessage", "Sản phẩm đã thêm thành công");
		response.sendRedirect(request.getContextPath() + "/admin/products");
	}
	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		session = request.getSession();
		int productId = Integer.parseInt(request.getParameter("productId"));

		Product product = productBO.get(productId);
		String name = request.getParameter("productName");
		int warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));
		String description = request.getParameter("description");
		Category category = null;
		if (!Objects.equals(request.getParameter("categoryId"), "")) {
			category = categoryBO.get(Integer.parseInt(request.getParameter("categoryId")));
		}
		String material = request.getParameter("material");
		String dimension = request.getParameter("dimension");
		int price = Integer.parseInt(request.getParameter("price"));

		productBO.update(product,category,name,warrantyPeriod,description,material,dimension,price);

		String imageFolder = "images/products";
		//xóa các ảnh cũ của productId;
		productBO.deleteImgPath(productId);

		for (Part part : request.getParts()) {
			if (part.getName().equals("images") && !Objects.equals(part.getSubmittedFileName(), "")) {
				String uniqueId = UUID.randomUUID().toString();
				String submittedFileName = part.getSubmittedFileName();
				String baseName = FilenameUtils.getBaseName(submittedFileName);
				String extensionName = FilenameUtils.getExtension(submittedFileName);
				String fileName = baseName + uniqueId + "." + extensionName;
				if (fileName.matches("[0-9]+.*")) fileName = "a" + fileName;
				part.write( PropertiesUtil.get("config.properties").getProperty("sourceImageFolder") + "\\" + fileName);
				part.write(PropertiesUtil.get("config.properties").getProperty("targetImageFolder")  + "\\" + fileName);
				productBO.create(productId, imageFolder + "/" + fileName);
			}
		}
		session.setAttribute("successMessage", "Sản phẩm đã được sửa thành công");
		response.sendRedirect(request.getContextPath() + MANAGE_PRODUCTS_SERVLET);
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		int productId = Integer.parseInt(request.getParameter("productId"));

		productBO.deleteImgPath(productId);
		productBO.delete(productId);
		session = request.getSession();
		session.setAttribute("successMessage", "Xóa sản phẩm thành công");
		try {
			response.sendRedirect(request.getContextPath() + MANAGE_PRODUCTS_SERVLET);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}