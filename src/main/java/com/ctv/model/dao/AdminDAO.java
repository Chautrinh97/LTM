package com.ctv.model.dao;

import com.ctv.model.bean.Admin;
import com.ctv.model.dao.GenericDAO;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class 	AdminDAO
		implements GenericDAO<Admin> {

	@Override
	public Admin get(int id){
		Admin admin = new Admin();
		String sql = "SELECT * FROM admin WHERE user_id=? ";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			// loop the result set
			while (resultSet.next()) {
				return map(resultSet);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Admin map(ResultSet resultSet){
		try {
			int id = resultSet.getInt("user_id");
			String username = resultSet.getString("username");
			String email = resultSet.getString("email");
			String phoneNumber = resultSet.getString("phone_number");
			String address = resultSet.getString("address");
			String password = resultSet.getString("password");
			String fullName = resultSet.getString("fullname");
			String role = resultSet.getString("role");
			return new Admin(id, username, email, password, fullName, phoneNumber, address,role);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Admin> getAll() {
		return null;
	}

	public Admin create(Admin admin) {
		String sql = "INSERT INTO admin(username, password, fullname, phone_number, address, email, role)  VALUES(?," +
				"?, ?, " +
				"?, ?,?,?)";
		try (Connection	connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){

			statement.setString(1, admin.getUsername());
			statement.setString(2,admin.getPassword());
			statement.setString(3, admin.getFullName());
			statement.setString(4, admin.getPhoneNumber());
			statement.setString(5, admin.getAddress());
			statement.setString(6, admin.getEmail());
			statement.setString(7, admin.getRole());
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();
			admin.setUserId(resultSet.getInt(1));
			resultSet.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return admin;
	}

	public Admin update(Admin admin) {
		String fullName = admin.getFullName();
		String username = admin.getUsername();
		String email = admin.getEmail();
		String password = admin.getPassword();
		int id = admin.getUserId();

		String sql = "UPDATE admin SET fullname=?, username=?,email=?, password=? WHERE user_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, fullName);
			statement.setString(2, username);
			statement.setString(3, email);
			statement.setString(4, password);
			statement.setInt(5, id);
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return admin;
	}
	public void updateRole(String role, int id){

		String sql = "UPDATE admin SET role=? WHERE user_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setString(1, role);
			statement.setInt(2, id);

			statement.execute();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	public List<Admin> getAdminList() {
		List<Admin> adminList = new ArrayList<>();
		String sql = "SELECT * FROM admin";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); Statement statement = connection.createStatement();) {
			ResultSet resultSet = statement.executeQuery(sql);
			// loop the result set
			while (resultSet.next()) {
				adminList.add(map(resultSet));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return adminList;
	}




	public void delete(int id) {
		String sql = "DELETE FROM admin WHERE user_id=?";
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.execute();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public Admin findByUsername(String username) {
		String sql = "SELECT * FROM admin WHERE username=?";
		Admin admin = null;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql); ) {
			ResultSet resultSet;
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			while (resultSet.next()){
				admin = map(resultSet);
			}
			resultSet.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return admin;
	}

	public Admin findByEmail(String email) {
		String sql = "SELECT * FROM admin WHERE email=?";
		Admin admin = null;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql); ) {
			ResultSet resultSet;
			statement.setString(1, email);
			resultSet = statement.executeQuery();
			while (resultSet.next()){
				admin = map(resultSet);
			}
			resultSet.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return admin;
	}

	public List<Admin> get(int begin, int numberOfRec, String keyword, String sortBy, String order) {
		List<Admin> adminList = new ArrayList<>();
		String sql = "SELECT * FROM admin " +
				(keyword != null ? " WHERE ( username LIKE '%" + keyword + "%' " +
						"OR fullname LIKE '%" + keyword + "%' " +
						"OR email LIKE '%" + keyword + "%' ) "
						: "") +
				(sortBy != null ? "ORDER BY " + sortBy +" " + order: "") +
				" LIMIT " + begin + "," + numberOfRec;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				adminList.add(map(resultSet));
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adminList;
	}

	public int count(String keyword){
		int count = 0;
		String sql =
				"SELECT COUNT(user_id) AS no FROM admin " +
						(keyword != null ? " WHERE username LIKE '%" + keyword + "%' "
								+ "OR fullname LIKE '%" + keyword + "%' "
								+ "OR email LIKE '%" + keyword + "%'": "");
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

	public Admin findByPhoneNumber(String phoneNumber) {
		String sql = "SELECT * FROM admin WHERE phone_number=?";
		Admin admin = null;
		try (Connection connection = DataSourceHelper.getDataSource().getConnection(); PreparedStatement statement =
				connection.prepareStatement(sql); ) {
			ResultSet resultSet;
			statement.setString(1, phoneNumber);
			resultSet = statement.executeQuery();
			while (resultSet.next()){
				admin = map(resultSet);
			}
			resultSet.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return admin;
	}

	public Admin validate(Admin admin)  {
		// Tạo connection
		String usernameToAuthenticate = admin.getUsername();
		String emailToAuthenticate = admin.getEmail();
		String password = admin.getPassword();
		String sql = "SELECT * FROM admin WHERE ((username=?) or email=? ) LIMIT 1";

		try (Connection connection = DataSourceHelper.getDataSource().getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, usernameToAuthenticate);
			statement.setString(2, emailToAuthenticate);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String validPassword = resultSet.getString("password");
				if (password.equals(validPassword))
					return map(resultSet);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
