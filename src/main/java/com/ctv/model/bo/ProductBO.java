package com.ctv.model.bo;

import com.ctv.model.bean.ImagePath;
import com.ctv.model.dao.CategoryDAO;
import com.ctv.model.dao.ImagePathDAO;
import com.ctv.model.dao.ProductDAO;
import com.ctv.model.bean.Category;
import com.ctv.model.bean.Product;
import com.ctv.util.CaseUtils;
import com.ctv.util.PropertiesUtil;
import com.ctv.util.RequestUtils;
import com.ctv.util.UniqueStringUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProductBO {
	private ProductDAO productDAO = new ProductDAO();
	private CategoryDAO categoryDAO = new CategoryDAO();
	private ImagePathDAO imagePathDAO = new ImagePathDAO();

	public Product get(int productId) {
		return productDAO.get(productId);
	}
	public List<Product> get(int begin, int numberOfRec, String keyword, String sortBy, String order){
		return productDAO.get(begin,numberOfRec,keyword,sortBy,order);
	}
	public List<Product> get(int begin, int numberOfRec, Integer categoryId, String sortBy, String order){
		return productDAO.get(begin,numberOfRec,categoryId,sortBy,order);
	}
	public Product get(String productURI) {
		return productDAO.get(productURI);
	}
	public List<Product> get(int begin, int numberOfRec, String keyword, int minPrice, int maxPrice, String sortBy, String order) {
		return productDAO.get(begin, numberOfRec, keyword, minPrice, maxPrice, sortBy, order);
	}
	public List<Product> get(int begin, int numberOfRecords, String sortBy, String order) {
		return productDAO.get(begin, numberOfRecords, sortBy, order);
	}
	public int count(String keyword, String field) {
		return productDAO.count(keyword, field);
	}
	public int count() {
		return productDAO.count();
	}
	public int count(Integer categoryId) {
		return productDAO.count(categoryId);
	}
	public int count(String keyword, int minPrice, int maxPrice) {
		return productDAO.count(keyword, minPrice, maxPrice);
	}
	public void create(int productId, String path) {
		ImagePath imagePath = new ImagePath(productId,path);
		imagePathDAO.create(imagePath);
	}
	public Product create(String name, int warrantyPeriod, String description, String dimension, String material, int price, Category category, String uri) {
		Product product = new Product(name, warrantyPeriod, description, dimension, material, price, category, uri);
		return productDAO.create(product);
	}

	public void update(Product product, Category category, String name, int warrantyPeriod, String description, String material, String dimension, int price) {
		product.setCategory(category);
		product.setName(name);
		product.setWarrantyPeriod(warrantyPeriod);
		product.setDescription(description);
		product.setMaterial(material);
		product.setDimension(dimension);
		product.setPrice(price);
		product.setUri(CaseUtils.convert2KebabCase(name) + "-" + UniqueStringUtils.randomUUID(16));
		productDAO.update(product);
	}
	public void deleteImgPath(int productId) {
		imagePathDAO.delete(productId);
	}
	public void delete(int productId) {
		productDAO.delete(productId);
	}
}
