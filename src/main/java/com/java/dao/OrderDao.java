package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java.model.Order;
import com.java.model.Product;

public class OrderDao
{
	private Connection con;
	private String query;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public OrderDao(Connection con)
	{
		this.con=con;
	}
	public boolean insertOrder(Order model)
	{
		boolean result = false;
		try
		{
			query = "insert into orders (p_id, u_id, o_quantity, o_date) values(?,?,?,?)";
			ps = this.con.prepareStatement(query);
			ps.setInt(1, model.getId());
			ps.setInt(2, model.getuId());
			ps.setInt(3, model.getQuantity());
			ps.setString(4, model.getDate());
			ps.executeUpdate();
			result = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Order> userOrders(int id)
	{
		List<Order> list = new ArrayList<>();
		try
		{
			query = "select * from orders where u_id=? order by orders.o_id desc";
			ps = this.con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				Order order = new Order();
				ProductDao productDao = new ProductDao(this.con);
				int pId = rs.getInt("p_id");
				
				Product product = productDao.getSingleProduct(pId);
				order.setOrderId(rs.getInt("o_id"));
				order.setId(pId);
				order.setName(product.getName());
				order.setCategory(product.getCategory());
				order.setPrice(product.getPrice()*rs.getInt("o_quantity"));
				order.setQuantity(rs.getInt("o_quantity"));
				order.setDate(rs.getString("o_date"));
				list.add(order);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	public void cancelOrder(int id)
	{
		try
		{
			query = "delete from orders where o_id=?";
			ps = this.con.prepareStatement(query);
			ps.setInt(1, id);
			ps.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
