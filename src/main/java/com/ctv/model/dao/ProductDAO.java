package com.ctv.model.dao;

import com.ctv.model.bean.Category;
import com.ctv.model.bean.ImagePath;
import com.ctv.model.bean.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO
		implements GenericDAO<Product> {
	private final ImagePathDAO imagePathDAO;
	private final CategoryDAO categoryDAO;

	public ProductDAO() {
		categoryDAO = new CategoryDAO();
		imagePathDAO = new ImagePathDAO();
	}

	@Override
	public Product get(int id) {
		String sql = "SELECT * FROM product WHERE product_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getAll() {

		List<Product> productList = new ArrayList<>();
		String sql = "SELECT * FROM product ";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				productList.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}
	public List<Product> getAll(String sortBy, String order) {

		List<Product> productList = new ArrayList<>();
		String sql = "SELECT * FROM product " +
				(sortBy != null ? " ORDER BY " + sortBy + " " + order : "");
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				productList.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public Product map(ResultSet resultSet) {
		try {
			int productId = resultSet.getInt("product_id");
			String productName = resultSet.getString("product_name");
			int warrantyPeriod = resultSet.getInt("warranty_period");
			String material = resultSet.getString("material");
			String dimension = resultSet.getString("dimension");
			String description = resultSet.getString("description");
			int price = resultSet.getInt("price");
			int categoryId = resultSet.getInt("category_id");
			Category category = categoryDAO.get(categoryId);
			List<ImagePath> imagePathList = imagePathDAO.getGroup(productId);
			String uri = resultSet.getString("uri");
			return new Product(productId, productName, warrantyPeriod, material, dimension, description,
					price, category, imagePathList, uri);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public int count(String keyword, String field) {
		int count = 0;
		if (field == null) field = "product_name";
		List<Product> productList = new ArrayList<>();
		String sql =
				"SELECT COUNT(product_id) AS no FROM "+ (keyword!=null? "product p JOIN category c on p.category_id = c.category_id":
						"product")+
						(keyword != null ? " WHERE " + field + " LIKE '%" + keyword +
								"%' OR category_name LIKE '%"+keyword+"%'"
								: "");
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("no");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public List<Product> get(
			int begin, int numberOfRec, String keyword, String sortBy,
			String order) {
		if (order == null) order = "ASC";
		List<Product> productList = new ArrayList<>();
		String sql =
				"SELECT product.* FROM "+(keyword!=null? "product JOIN category c ON product.category_id = c.category_id":
						"product")+
						(keyword != null ?
								" WHERE (product_name  LIKE '%" + keyword +
										"%' OR description  LIKE '%" + keyword +
										"%' OR category_name LIKE '%" + keyword+
										"%' OR material  LIKE '%" + keyword + "' ) "
								: "") +
						(sortBy != null ? " ORDER BY " + sortBy + " " + order : "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				productList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}
	public List<Product> get(
			int begin, int numberOfRec, String keyword, int minPrice, int maxPrice, String sortBy,
			String order) {
		if (order == null) order = "ASC";
		List<Product> productList = new ArrayList<>();
		String sql =
				"SELECT * FROM product " +
						(keyword != null ?
								" WHERE (product_name  LIKE '%" + keyword +
										"%' OR description  LIKE '%" + keyword +
										"%' OR material  LIKE '%" + keyword + "' ) " +
										" AND (price BETWEEN " + minPrice + " AND " + maxPrice + ")"
								: "") +
						(sortBy != null ? " ORDER BY " + sortBy + " " + order : "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				productList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public int count(String keyword, int minPrice, int maxPrice) {
		int count = 0;
		String sql =
				"SELECT COUNT(product_id) AS no FROM product " +
						(keyword != null ?
								" WHERE (product_name  LIKE '%" + keyword +
										"%' OR description  LIKE '%" + keyword +
										"%' OR material  LIKE '%" + keyword + "' ) " +
										" AND (price BETWEEN " + minPrice + " AND " + maxPrice + ")"
								: "");
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("no");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Product findByName(String name) {
		String sql = "SELECT * FROM product WHERE product_name = ?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql);) {
			ResultSet resultSet;
			statement.setString(1, name);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Product> get(int begin, int numberOfRecords, String sortBy, String order) {
		List<Product> productList = new ArrayList<>();
		String sql =
				"SELECT * FROM product LIMIT " + begin + ", " + numberOfRecords +
						(sortBy != null ? " ORDER BY " + sortBy + " " + order : "");
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				productList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public int count() {
		int count = 0;
		String sql =
				"SELECT COUNT(product_id) AS no FROM product ";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("no");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Product> get(int begin, int numberOfRecs, Integer categoryId, String sortBy, String order) {
		if (order == null) order = "ASC";
		List<Product> productList = new ArrayList<>();
		String sql =
				"SELECT * FROM product " +
						(categoryId != null ? " WHERE category_id = " + categoryId : "") +
						(sortBy != null ? " ORDER BY " + sortBy + " " + order : "") +
						" LIMIT " + begin + "," + numberOfRecs;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				productList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public int count(Integer categoryId) {
		int count = 0;
		String sql =
				"SELECT COUNT(product_id) AS no FROM product " + (categoryId != null ?
						" WHERE category_id = " + categoryId : "");
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("no");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public Product get(String productURI) {
		String sql = "SELECT * FROM product WHERE uri=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, productURI);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return map(resultSet);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
