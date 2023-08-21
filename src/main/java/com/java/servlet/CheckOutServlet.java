package com.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.connection.DBCon;
import com.java.dao.OrderDao;
import com.java.model.Cart;
import com.java.model.Order;
import com.java.model.User;

@WebServlet("/check-out-servlet")
public class CheckOutServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try(PrintWriter out =response.getWriter())
		{
			SimpleDateFormat format = new SimpleDateFormat();
			Date date = new Date();
			
			//retrive all cart product
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			
			User ok = (User) request.getSession().getAttribute("ok");
			
			//check ok and cart list
			if(cart_list != null && ok != null)
			{
				for(Cart c:cart_list)
				{
					Order order = new Order();
					order.setId(c.getId());
					order.setuId(ok.getId());
					order.setQuantity(c.getQuantity());
					order.setDate(format.format(date));
					
					OrderDao oDao = new OrderDao(DBCon.getConnection());
					boolean result = oDao.insertOrder(order);
					if(!result) break;
				}
				cart_list.clear();
				response.sendRedirect("orders.jsp");
			}
			else
			{
				if(ok == null) response.sendRedirect("login.jsp");
				response.sendRedirect("cart.jsp");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
