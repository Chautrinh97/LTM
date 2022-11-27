package com.ctv.model.dao;

import com.ctv.model.bean.ShippingAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShippingAddressDAO implements GenericDAO<ShippingAddress>{

	public ShippingAddress get(int userId) {
		ShippingAddress shippingAddress = null;
		String sql = "SELECT * FROM shipping_address WHERE user_id=? LIMIT 1";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql);){
			statement.setInt(1, userId);
			ResultSet resultSet;
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				shippingAddress = map(resultSet);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return shippingAddress;
	}

	@Override
	public List<ShippingAddress> getAll() {
		return null;
	}

	public ShippingAddress update(ShippingAddress shippingAddress) {
		String recipient_name = shippingAddress.getRecipientName();
		String address = shippingAddress.getAddress();
		int id = shippingAddress.getCustomerId();
		String sql = "UPDATE shipping_address SET recipient_name=?,  address=? WHERE user_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, recipient_name);
			statement.setString(2, address);
			statement.setInt(3, id);
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return shippingAddress;
	}

	@Override
	public ShippingAddress map(ResultSet resultSet) {
		try {
			int customerId = resultSet.getInt("user_id");
			String recipientName = resultSet.getString("recipient_name");
			String phoneNumber = resultSet.getString("phone_number");
			String address = resultSet.getString("address");
			 return  new ShippingAddress(customerId, recipientName, phoneNumber, address);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public ShippingAddress create(ShippingAddress address) {
		String sql = "INSERT INTO shipping_address VALUES (?,?,?,?)";
		try(Connection connection = DataSourceHelper.getDataSource().getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, address.getCustomerId());
			preparedStatement.setString(2, address.getRecipientName());
			preparedStatement.setString(3, address.getPhoneNumber());
			preparedStatement.setString(4, address.getAddress());
			preparedStatement.executeUpdate();
			return address;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
