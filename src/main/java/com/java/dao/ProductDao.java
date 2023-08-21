package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java.model.*;

public class ProductDao 
{
	private Connection con;
	private String query;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public ProductDao(Connection con) 
	{
		this.con = con;
	}
	public List<Product> getAllProducts()
	{
		List<Product> products = new ArrayList<Product>();
		try
		{
			query = "select * from products";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next())
			{
				Product row = new Product();
				row.setId(rs.getInt("id"));
				row.setName(rs.getString("name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getDouble("price"));
				row.setImage(rs.getString("image"));
				
				products.add(row);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return products;
		
	}
	public List<Cart> getCartProducts(ArrayList<Cart> cartList)
	{
		List<Cart> products = new ArrayList<Cart>();
		try
		{
			if(cartList.size()>0)
			{
				for(Cart item :cartList)
				{
					query = "select * from products where id=?";
					ps = this.con.prepareStatement(query);
					ps.setInt(1, item.getId());
					rs = ps.executeQuery();
					while(rs.next())
					{
						Cart row = new Cart();
						row.setId(rs.getInt("id"));
						row.setName(rs.getString("name"));
						row.setCategory(rs.getString("category"));
						row.setPrice(rs.getDouble("price")*item.getQuantity());
						row.setQuantity(item.getQuantity());
						products.add(row);
						
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return products;
		
	}
	public double getTotalCartPrice(ArrayList<Cart> cartList)
	{
		double sum=0;
		try
		{
			if(cartList.size()>0)
			{
				for(Cart item:cartList)
				{
					query ="select price from products where id=?";
					ps = this.con.prepareStatement(query);
					ps.setInt(1, item.getId());
					rs = ps.executeQuery();
					
					while(rs.next())
					{
						sum+=rs.getDouble("price")*item.getQuantity();
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sum;
		
	}
	public Product getSingleProduct(int id) 
	{
		Product row = null;
		try
		{
			query = "select * from products where id=?";
			ps = this.con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				row = new Product();
				row.setId(rs.getInt("id"));
				row.setName(rs.getString("name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getDouble("price"));
				row.setImage(rs.getString("image"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return row;
	}
}
