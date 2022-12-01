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

	public int findItemInCart(List<CartItem> cartItemList, int productId) {
		for (int i = 0; i < cartItemList.size(); i++) {
			if (cartItemList.get(i).getProduct().getProductId() == productId) {
				return i;
			}
		}
		return -1;
	}
}
