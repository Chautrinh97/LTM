package com.ctv.model.bo;

import com.ctv.model.bean.Admin;
import com.ctv.model.dao.AdminDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class AdminBO {
	private AdminDAO adminDAO = new AdminDAO();
	public Admin validate(String usernameOrEmail, String password) {
		Admin admin = new Admin(usernameOrEmail,password);
		return adminDAO.validate(admin);
	}
}
