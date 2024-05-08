package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.*;
import dataModel.InventoryModel;



public class InventoryDAO {

	public ArrayList<InventoryModel> getInventory(boolean admin) throws ClassNotFoundException, SQLException
	{
		DB db=new DB();
		Connection connection=db.getConnection();
		ArrayList<InventoryModel> inventories=new ArrayList<>();
		String query;
		if(admin)
		{
			 query="SELECT\r\n"
			 		+ "    inventory.productId,\r\n"
			 		+ "    inventory.productName,\r\n"
			 		+ "    inventory.price,\r\n"
			 		+ "    inventory.quantity,\r\n"
			 		+ "    createdByUser.userName AS createdBy,\r\n"
			 		+ "    modifiedByUser.userName AS editedBy\r\n"
			 		+ "FROM\r\n"
			 		+ "    inventory\r\n"
			 		+ "LEFT JOIN\r\n"
			 		+ "    user AS createdByUser ON inventory.createdBy = createdByUser.userId\r\n"
			 		+ "LEFT JOIN\r\n"
			 		+ "    user AS modifiedByUser ON inventory.modifiedBy = modifiedByUser.userId\r\n"
			 		+ "WHERE\r\n"
			 		+ "    inventory.status = 1\r\n"
			 		+ "ORDER BY\r\n"
			 		+ "    productName;\r\n";
		}
		else
		{
		 query="select *from inventory where status=1";
		}
		 
		Statement st=connection.createStatement();
		ResultSet result=st.executeQuery(query);
		while(result.next())
		{
			InventoryModel inventory=new InventoryModel();
			inventory.setProductId(result.getInt("productId"));
			inventory.setProductName(result.getString("productName"));;
			inventory.setQuantity(Integer.parseInt(result.getString("quantity")));
			inventory.setPrice(Integer.parseInt(result.getString("price")));
			if(admin)
			{
				inventory.setCreatorName(result.getString("createdBy"));
				inventory.setEditedName(result.getString("editedBy"));
			}
			inventories.add(inventory);
		}
		connection.close();
		return inventories;
	}
	
	public boolean addInventory(InventoryModel inventory)
	{
		DB db=new DB();
		try {
			Connection connection=db.getConnection();
			String query="insert into inventory(productName,price,quantity,createdBy) values(?,?,?,?)";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, inventory.getProductName());
			ps.setInt(2, inventory.getPrice());
			ps.setInt(3, inventory.getQuantity());
		    ps.setInt(4, inventory.getCreatedBy());
			ps.execute();
			return true;
			
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean deleteInventory(int id) throws ClassNotFoundException, SQLException
	{
		
		DB db=new DB();
	Connection	connection=db.getConnection();
		String query="update inventory set status = 0 where productId = "+id;
		try 
		{
			Statement st=connection.createStatement();
			st.execute(query);
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return false;
		
	}
	
	public boolean updateInventory(InventoryModel inventory)
	{
		DB db=new DB();
		try {
			Connection connection=db.getConnection();
			String query="update inventory set productName=?,price=?,quantity=quantity+?,modifiedBy=? where productId=?";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, inventory.getProductName());
			ps.setInt(2,  inventory.getPrice());
			ps.setInt(3, inventory.getQuantity());
			ps.setInt(4, inventory.getModifiedBy());
			ps.setInt(5, inventory.getProductId());
			System.out.println(ps);
			ps.execute();
			return true;
			
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return false;
		
	}

	public ArrayList<InventoryModel> getMinInventory() throws ClassNotFoundException, SQLException {
		DB db=new DB();
		Connection connection=db.getConnection();
		ArrayList<InventoryModel> inventories=new ArrayList<>();
		String query="SELECT * FROM inventory WHERE quantity <= 5 AND status = 1 ORDER BY quantity";
		
		Statement st=connection.createStatement();
		ResultSet result=st.executeQuery(query);
		while(result.next())
		{
			InventoryModel inventory=new InventoryModel();
			inventory.setProductId(result.getInt("productId"));
			inventory.setProductName(result.getString("productName"));;
			inventory.setQuantity(Integer.parseInt(result.getString("quantity")));
			inventory.setPrice(Integer.parseInt(result.getString("price")));
			inventories.add(inventory);
		}
		connection.close();
		return inventories;
	}

	public HashMap<String, Integer> getMaxSale() throws ClassNotFoundException, SQLException {
		
		DB db=new DB();
		Connection connection=db.getConnection();
		
		
		HashMap<String,Integer> maxSale=new HashMap<>();
		
	    String query="Select billProduct.productName,sum(billProduct.quantity) from billProduct \r\n"
	    		+ "join bill\r\n"
	    		+ "on bill.billid=billProduct.billId\r\n"
	    		+ "WHERE purchaseDate >= DATE_SUB(CURDATE(), INTERVAL 5 DAY)\r\n"
	    		+ "group by billProduct.productName \r\n"
	    		+ "order by sum(billProduct.quantity) desc\r\n"
	    		+ "limit 5";
		
		Statement st=connection.createStatement();
		ResultSet result=st.executeQuery(query);
		
		while(result.next())
		{
			maxSale.put(result.getString("productName"), result.getInt("sum(billProduct.quantity)"));
		}
		
		return maxSale;
	}
	
	
	
}
