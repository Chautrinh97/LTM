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
	private CustomerDAO customerDAO = new CustomerDAO();
	private ShippingAddressDAO shippingAddressDAO = new ShippingAddressDAO();

	public Customer validate(Customer customer) {
		return customerDAO.validate(customer);
	}
	public Customer update(Customer customer, String fullName, Customer.Gender gender, LocalDate dateOfBirth) {
		customer.setFullName(fullName);
		customer.setGender(gender);
		customer.setDateOfBirth(dateOfBirth);
		return customerDAO.update(customer);
	}
	public void update(Customer customer, String newPassword) {
		customer.setPassword(newPassword);
        customerDAO.update(customer);
	}
	public ShippingAddress update(ShippingAddress updatedShippingAddress) {
		return shippingAddressDAO.update(updatedShippingAddress);
	}
}
