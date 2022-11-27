package com.ctv.model.dao;

import com.ctv.model.bean.Customer;
import com.ctv.model.bean.ShippingAddress;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO
		implements GenericDAO<Customer> {


	@Override
	public Customer get(int id) {
		Customer customer = new Customer();
		String sql = "SELECT * FROM customer WHERE user_id=? ";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			// loop the result set
			while (resultSet.next()) {
				return map(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public List<Customer> getAll() {
		return null;
	}

	public Customer update(Customer customer) {
		String sql = "UPDATE customer  SET phonenumber =?, email=?, password=?, fullname=?, date_of_birth =?, " +
				"gender=?, status=? WHERE user_id =?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, customer.getPhoneNumber());
			statement.setString(2, customer.getEmail());
			statement.setString(3, customer.getPassword());
			statement.setString(4, customer.getFullName());
			statement.setDate(5, Date.valueOf(customer.getDateOfBirth()));
			statement.setInt(6, customer.getGender().getValue());
			statement.setBoolean(7, customer.isActive());
			statement.setInt(8, customer.getUserId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;

	}

	@Override
	public Customer map(ResultSet resultSet) {
		try {
			int userId = resultSet.getInt("user_id");
			String fullName = resultSet.getString("fullname");
			String password = resultSet.getString("password");
			String pNumber = resultSet.getString("phoneNumber");
			String email = resultSet.getString("email");
			Customer.Gender gender = Customer.Gender.values()[resultSet.getByte("gender")];
			LocalDate dateOfBirth = resultSet.getDate("date_of_birth").toLocalDate();
			ShippingAddressDAO addressDAO = new ShippingAddressDAO();
			ShippingAddress address = addressDAO.get(userId);
			boolean status = resultSet.getBoolean("status");
			return new Customer(userId, password, email, fullName, pNumber, gender, dateOfBirth,
					address, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Customer> get(int begin, int numberOfRec, String keyword) {
		List<Customer> customerList = new ArrayList<>();
		String sql =
				"SELECT * FROM customer " +
						(keyword != null ? " WHERE fullname LIKE '%" + keyword + "%' " : "") +
						" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				customerList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	public Customer validate(Customer customer) {
		boolean isPhoneNumber = (customer.getPhoneNumber() != null);
		String account = isPhoneNumber ? customer.getPhoneNumber() : customer.getEmail();
		String password = customer.getPassword();
		String sql = "SELECT * FROM customer WHERE " +
				(isPhoneNumber ? "phonenumber=?" : "email=?") +
				" LIMIT 1";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, account);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String validPassword = resultSet.getString("password");
				if (customer.getPassword().equals(validPassword))  return map(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
