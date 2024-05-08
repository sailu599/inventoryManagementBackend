package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataModel.CartModel;
import dataModel.InventoryModel;

public class CartDAO {
	
	public CartModel getCart(int id)
	{
		DB db=new DB();
		CartModel cart=new CartModel();
		try {
			Connection connection=db.getConnection();
			
			
			String query="SELECT cart.cartId,inventory.quantity as max,inventory.productName,inventory.price,cart.quantity FROM cart "
					    + "JOIN inventory ON inventory.productId=cart.productId "
					    + "WHERE inventory.status = 1 AND cart.customerId="+id; //forntend
			Statement st=connection.createStatement();
			
			ResultSet result=st.executeQuery(query);
			ArrayList<InventoryModel> cartItems=new ArrayList<>();
			while(result.next())
			{
				InventoryModel item=new InventoryModel();
				item.setProductId(result.getInt("cartId"));
				item.setProductName(result.getString("productName"));
				item.setPrice(result.getInt("price"));
				item.setQuantity(result.getInt("quantity"));
				item.setMax(result.getInt("max"));
				cartItems.add(item);
			}
			
			cart.setCartItems(cartItems);
			cart.setC_id(id);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return cart;
		
	}
	
	
	public boolean addCart(int customerId,int productId,int quantity)
	{
		DB db=new DB();
		try {
			Connection connection=db.getConnection();
			String query="insert into cart(customerId,productId,quantity) values("
					     +customerId+","
					     +productId+","
					     +quantity+")";
			Statement st=connection.createStatement();
			System.out.println(query);
			st.execute(query);
            return true;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean deleteCart(int id)
	{
		String query="delete from cart where cartId ="+id;
		DB db=new DB();
		
		try
		{
			Connection connection=db.getConnection();
			Statement st=connection.createStatement();
			st.execute(query);
            return true;
			
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean updateCart(int id,int quantity)
	{
		String query="update cart set quantity=? where cartId=?";
		DB db=new DB();
		
		try 
		{
			Connection connection=db.getConnection();
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1, quantity);
			ps.setInt(2, id);
			
			ps.executeUpdate();
			return true;
			
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
