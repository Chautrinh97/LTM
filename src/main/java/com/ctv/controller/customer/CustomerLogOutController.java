package com.ctv.controller.customer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerLogOutController", value = "/logout")
public class CustomerLogOutController extends HttpServlet {
    private HttpSession session;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session= request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/");
    }

}
