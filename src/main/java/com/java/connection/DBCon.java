package com.java.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCon 
{
	private static Connection con = null;
	
	public static Connection getConnection()
	{
		if(con == null)
		{
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/e_cart","root","1234");
				System.out.println("Connection sucessfully");
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		return con;
	}
}
