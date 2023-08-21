package com.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.connection.DBCon;
import com.java.dao.UserDao;
import com.java.model.User;

@WebServlet("/user-login")
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendRedirect("login.jsp");
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		try
		{
			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");
			try
			{
				UserDao udao = new UserDao(DBCon.getConnection());
				User user = udao.userLogin(email, password);
				
				if(user!=null)
				{
					request.getSession().setAttribute("ok", user);
					response.sendRedirect("index.jsp");
				}
				else
				{
					pw.println("User-Login Failed");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
