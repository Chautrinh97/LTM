package com.ctv.model.dao;

import com.ctv.model.bean.ImagePath;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImagePathDAO
		implements GenericDAO<ImagePath> {
	
	@Override
	public ImagePath get(int id) {
	return  null;

	}

	@Override
	public List<ImagePath> getAll() {
		return null;
	}

	public ImagePath create(ImagePath imagePath) {
		String sql = "INSERT INTO image VALUES(?,?)";
		try(Connection connection = DataSourceHelper.getDataSource().getConnection();
		    PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1,imagePath.getProductId());
			statement.setString(2,imagePath.getPath());
			statement.execute();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return imagePath;
	}
	@Override
	public ImagePath map(ResultSet resultSet) {
		return null;
	}

	public List<ImagePath> getGroup(int productId) {
		List<ImagePath> imagePathList = new ArrayList<>();
		String sql = "SELECT * FROM image WHERE product_id=? ";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, productId);
			ResultSet resultSet = statement.executeQuery();
			// loop the result set
			while (resultSet.next()) {
				int id=resultSet.getInt("product_id");
				String path = resultSet.getString("image_path");
				imagePathList.add(new ImagePath(id, path));

			}

		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return imagePathList;
	}
	public void delete(int productId) {
		String sql = "DELETE FROM image WHERE product_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, productId);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
