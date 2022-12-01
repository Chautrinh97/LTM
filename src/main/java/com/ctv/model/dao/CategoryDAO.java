package com.ctv.model.dao;

import com.ctv.model.bean.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO
		implements GenericDAO<Category> {

	@Override
	public Category get(int id) {
		String sql = "SELECT * FROM category WHERE category_id=? ";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return map(resultSet);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Category> getAll() {
		List<Category> categoryList = new ArrayList<>();
		String sql = "SELECT * FROM category";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				categoryList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	@Override
	public Category map(ResultSet resultSet) {
		Category category = new Category();
		try {
			int categoryId = resultSet.getInt("category_id");
			String categoryName = resultSet.getString("category_name");
			category.setCategoryId(categoryId);
			category.setCategoryName(categoryName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	public Category find(String categoryName) {
		String sql = "SELECT * FROM category WHERE category_name = ?";
		Category category = null;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			ResultSet resultSet;
			statement.setString(1, categoryName);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				category = map(resultSet);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	public List<Category> get(int begin, int numberOfRec, String keyword,String sortBy, String order) {
		if (order==null) order="ASC";
		List<Category> categoryList = new ArrayList<>();
		String sql =
				"SELECT * FROM category " +
						(keyword != null ? " WHERE category_name" +  " LIKE '%" + keyword + "%' " : "") +
						(sortBy != null ? "ORDER BY " + sortBy +" " + order: "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				categoryList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}
	public Category create(Category category) {
		String sql = "INSERT INTO category(category_name) VALUES(?)";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, category.getCategoryName());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			while (resultSet.next()) {
				int categoryId = resultSet.getInt(1);
				category.setCategoryId(categoryId);
			}
			return category;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Category update(Category category) {
		String categoryName = category.getCategoryName();
		int categoryId = category.getCategoryId();

		String sql = "UPDATE category SET category_name=? WHERE category_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, categoryName);
			statement.setInt(2, categoryId);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}
	public List<Category> getAll(String orderBy, String order) {
		List<Category> categoryList = new ArrayList<>();
		String sql = "SELECT * FROM category ORDER BY " + orderBy +" "+ order;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				categoryList.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}
	public void delete(int id) {
		String sql = "DELETE  FROM category WHERE category_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int count(String keyword, String field){
		int count = 0;
		if (field==null) field = "category_name";
		List<Category> categoryList = new ArrayList<>();
		String sql =
				"SELECT COUNT(category_id) AS no FROM category " +
						(keyword != null ? " WHERE " + field + " LIKE '%" + keyword + "%' " : "") ;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt("no");
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  count;
	}

}
