package com.ctv.controller.customer;

import com.ctv.model.bean.CartItem;
import com.ctv.model.bean.Product;
import com.ctv.model.bo.CartBO;
import com.ctv.model.bo.ProductBO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CustomerCartController", value = "/user/cart")
public class CustomerCartController
		extends HttpServlet {
	private HttpSession session;
	private final CartBO cartBO = new CartBO();
	private final ProductBO productBO = new ProductBO();
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showManageCartPage(request, response);
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
				case "add":
					addToCart(request, response);
					break;
				case "update":
					updateCartItem(request, response);
					break;
				case "delete":
					deleteCartItem(request, response);
					break;
			}
		}
	}
	public void showManageCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		session = request.getSession();
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		if (cart != null) {
			for (CartItem cartItem : cart) {
				cartItem.setProduct(productBO.get(cartItem.getProduct().getProductId()));
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/cart/cart.jsp");
		dispatcher.forward(request, response);
	}
	public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		session = request.getSession();
		int productId = Integer.parseInt(request.getParameter("id"));
		Product product = productBO.get(productId);
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		CartItem cartItem = new CartItem(product, quantity);
		List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cart");
		// TH1: Cart List ???? ???????c kh???i t???o
		if (cartItemList != null) {
			// X??c ?????nh cart item ???? t???n t???i trong list ch??a, n???u c?? r???i th?? th??m s??? l?????ng
			int pos = cartBO.findItemInCart(cartItemList, productId);
			if (pos >= 0) {
				CartItem item = cartItemList.get(pos);
				item.setQuantity(item.getQuantity() + quantity);
				// chuy???n l??n ?????u
				cartItemList.remove(item);
				cartItemList.add(0, item);

			} else {
				cartItemList.add(0, cartItem);
			}
		}
		// TH2: Cart r???ng
		else {
			cartItemList = new ArrayList<>();
			cartItemList.add(0, cartItem);
			session.setAttribute("cart", cartItemList);
		}
		// Buy-now
		String from = request.getParameter("from");
		session.setAttribute("cartSuccessMessage", "Th??m v??o gi??? h??ng th??nh c??ng");
		response.sendRedirect(from);
	}
	public void updateCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));

		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		for (CartItem cartItem : cart) {
			if (cartItem.getProduct().getProductId() == id) {
				cartItem.setQuantity(newQuantity);
				break;
			}
		}

		response.sendRedirect(request.getContextPath() + request.getServletPath());
	}

	public void deleteCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String[] idParams = request.getParameterValues("id");
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		for (String idParam : idParams) {
			int productId = Integer.parseInt(idParam);
			cart.remove(cartBO.findItemInCart(cart, productId));
		}

		response.sendRedirect(request.getContextPath() + request.getServletPath());
	}
}
