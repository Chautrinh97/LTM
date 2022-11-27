package com.ctv.model.bo;

import com.ctv.model.dao.ProductDAO;
import com.ctv.model.bean.CartItem;
import com.ctv.model.bean.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartBO {
	private ProductDAO productDAO = new ProductDAO();
	private HttpSession session;

	public void showManageCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	                                                                                                IOException {
		session = request.getSession();
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		if (cart != null) {
			for (CartItem cartItem : cart) {
				cartItem.setProduct(productDAO.get(cartItem.getProduct().getProductId()));
			}

		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/customer/cart/cart.jsp");
		dispatcher.forward(request, response);
	}

	public void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		session = request.getSession();
		int productId = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(productId);
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		CartItem cartItem = new CartItem(product, quantity);
		List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cart");
		// TH1: Cart List đã được khởi tạo
		if (cartItemList != null) {
			// Xác định cart item đã tồn tại trong list chưa, nếu có rồi thì thêm số lượng
			int pos = findItemInCart(cartItemList, productId);
			if (pos >= 0) {
				CartItem item = cartItemList.get(pos);
				item.setQuantity(item.getQuantity() + quantity);
				// chuyển lên đầu
				cartItemList.remove(item);
				cartItemList.add(0, item);

			} else {
				cartItemList.add(0, cartItem);
			}
		}
		// TH2: Cart rỗng
		else {
			cartItemList = new ArrayList<>();
			cartItemList.add(0, cartItem);
			session.setAttribute("cart", cartItemList);
		}
		// Buy-now
		String from = request.getParameter("from");
		session.setAttribute("cartSuccessMessage", "Thêm vào giỏ hàng thành công");
		response.sendRedirect(from);
	}

	private int findItemInCart(List<CartItem> cartItemList, int productId) {
		for (int i = 0; i < cartItemList.size(); i++) {
			if (cartItemList.get(i).getProduct().getProductId() == productId) {
				return i;
			}
		}
		return -1;
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
			cart.remove(findItemInCart(cart, productId));
		}

		response.sendRedirect(request.getContextPath() + request.getServletPath());
	}

	public void initCart(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession();
		List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
		if (cart != null) {
			for (CartItem cartItem : cart) {
				cartItem.setProduct(productDAO.get(cartItem.getProduct().getProductId()));
			}

		}
	}
}
