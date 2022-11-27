package com.ctv.model.bo;

import com.ctv.model.dao.CustomerDAO;
import com.ctv.model.dao.ShippingAddressDAO;
import com.ctv.model.bean.Customer;
import com.ctv.model.bean.ShippingAddress;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

public class CustomerBO {
	private final String CUSTOMER_LOGIN_PAGE = "/customer/login/login.jsp";
	private CustomerDAO customerDAO = new CustomerDAO();
	private ShippingAddressDAO shippingAddressDAO = new ShippingAddressDAO();
	private HttpSession session;

	public void showManageInformationPage(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tab = request.getParameter("tab");
		if (tab.equals("profile") || tab.equals("address") || tab.equals("password")) {
			request.setAttribute("tab", tab);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
			dispatcher.forward(request, response);
		} else
			response.sendRedirect(request.getContextPath() + "/user/account?tab=profile");
	}

	public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		request.setAttribute("tab", "profile");
		session = request.getSession();

		String fullName = request.getParameter("fullName");
		Customer.Gender gender = Customer.Gender.valueOf(request.getParameter("gender"));
		LocalDate date_of_birth = LocalDate.parse(request.getParameter("dateOfBirth"));

		Customer customer = (Customer) session.getAttribute("customer");
		Customer updatedCustomer = new Customer(customer);
		updatedCustomer.setFullName(fullName);
		updatedCustomer.setGender(gender);
		updatedCustomer.setDateOfBirth(date_of_birth);

		updatedCustomer = customerDAO.update(updatedCustomer);
		session.setAttribute("customer", updatedCustomer);
		request.setAttribute("successMessage", "Cập nhật thành công");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                            IOException {
		request.setAttribute("tab", "password");
		session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("password");
		if (customer.getPassword().equals(oldPassword)){
			//đổi mật khẩu trong database
			customer.setPassword(newPassword);
			customerDAO.update(customer);
			//đổi mật khẩu cho session hien tai
			request.setAttribute("successMessage", "Đổi mật khẩu thành công");
		}
		else {
			request.setAttribute("wrongOldPasswordMessage", "Sai mật khẩu cũ");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
		dispatcher.forward(request, response);
	}

	public void updateAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		request.setAttribute("tab", "address");
		session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		ShippingAddress shippingAddress = customer.getAddress();

		// Lấy dữ liệu từ form
		String recipient_name = request.getParameter("recipientName");
		String address = request.getParameter("address");

		// Tạo 1 bản sao của shippingAddress (session)
		ShippingAddress updatedShippingAddress = new ShippingAddress(shippingAddress);
		updatedShippingAddress.setRecipientName(recipient_name);
		updatedShippingAddress.setAddress(address);

		try {
			updatedShippingAddress = shippingAddressDAO.update(updatedShippingAddress);
			customer.setAddress(updatedShippingAddress);
			//Đặt lời nhắn thành công
			request.setAttribute("successMessage", "Cập nhật thành công");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
			dispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException,
	                                                                                          ServletException {
		session = request.getSession();
		String from = request.getParameter("from");
		if (from.equals("")) {
			from = request.getContextPath();
		}
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		Customer customer = new Customer();
		boolean isPhoneNumber = (account.indexOf('@') == -1);
		if (isPhoneNumber) {
			customer.setPhoneNumber(account);
		} else {
			customer.setEmail(account);
		}
		customer.setPassword(password);

		Customer authenticatedCustomer;
		// TH1: tài khoản tồn tại
		authenticatedCustomer = customerDAO.validate(customer);
		if (authenticatedCustomer != null) {
			session.setAttribute("customer", authenticatedCustomer);
			response.sendRedirect(from);
		} else {
			request.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
			showLoginForm(request, response);
		}
	}

	public void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                           IOException {
		request.setAttribute("headerAction", "Đăng nhập");
		RequestDispatcher dispatcher = request.getRequestDispatcher(CUSTOMER_LOGIN_PAGE);
		dispatcher.forward(request, response);
	}

}
