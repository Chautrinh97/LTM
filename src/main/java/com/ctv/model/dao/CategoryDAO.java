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

}
