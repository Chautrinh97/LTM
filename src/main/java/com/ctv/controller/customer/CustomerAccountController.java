package com.ctv.controller.customer;


import com.ctv.model.bean.Customer;
import com.ctv.model.bean.ShippingAddress;
import com.ctv.model.bo.CustomerBO;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "CustomerAccountController", value = "/user/account")
public class CustomerAccountController
		extends HttpServlet {
	private final String CUSTOMER_LOGIN_PAGE = "/customer/login/login.jsp";
	private HttpSession session;

	private final CustomerBO customerBO = new CustomerBO();


	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tab = request.getParameter("tab");
		if (tab != null) {
			showManageInformationPage(request, response);

		} else response.sendRedirect(request.getContextPath() + "/user/account?tab=profile");
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String action = request.getParameter("action");
		switch (action) {
			case "updateProfile":
				updateProfile(request, response);
				break;
			case "changePassword":
				changePassword(request, response);
				break;

			case "updateAddress":
				updateAddress(request, response);
				break;

		}
	}
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
		customer = customerBO.update(customer,fullName,gender,date_of_birth);
		session.setAttribute("customer", customer);
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
			customerBO.update(customer,newPassword);
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
			updatedShippingAddress = customerBO.update(updatedShippingAddress);
			customer.setAddress(updatedShippingAddress);
			//Đặt lời nhắn thành công
			request.setAttribute("successMessage", "Cập nhật thành công");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/account/manage-account.jsp");
			dispatcher.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




