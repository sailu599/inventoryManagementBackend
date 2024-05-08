package DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataModel.BillModel;
import dataModel.CartModel;
import dataModel.InventoryModel;

public class BillDAO {
	
	
	public boolean addBill(int id) throws ClassNotFoundException, SQLException
	{
		
		DB db=new DB();
		Connection connection =db.getConnection();
		connection.setAutoCommit(false);
		 
		CartDAO cartDAO=new CartDAO();
		CartModel cart=cartDAO.getCart(id);
		ArrayList<InventoryModel> cartItems=cart.getCartItems();
		try {
		
			
		     String insertQuery="insert into bill(customerId) value(?)";
		     PreparedStatement billQuery=connection.prepareStatement(insertQuery,PreparedStatement.RETURN_GENERATED_KEYS);
		     billQuery.setInt(1, id);
		     billQuery.execute();
		     ResultSet keySet=billQuery.getGeneratedKeys();
		     keySet.next();
		     int billId= keySet.getInt(1);
		int total=0;
		for(int i=0;i<cartItems.size();i++)
		{

;
		     String query="update inventory set quantity=quantity-? where productName=?";
			PreparedStatement ps=connection.prepareStatement(query);
		     ps.setInt(1, cartItems.get(i).getQuantity());
		     ps.setString(2,cartItems.get(i).getProductName());
		     System.out.println(ps);
		     ps.execute();
		     ps.clearParameters();
		     ps.close();
		     
		     query="insert into billProduct(billId,price,quantity,productName) values(?,?,?,?)";
		     ps=connection.prepareStatement(query);
		     ps.setInt(1, billId);
		     ps.setInt(2, cartItems.get(i).getPrice());
		     ps.setInt(3, cartItems.get(i).getQuantity());
		     ps.setString(4,cartItems.get(i).getProductName());
		     ps.execute();
		     
		     total+=cartItems.get(i).getPrice()*cartItems.get(i).getQuantity();
		     ps.clearParameters();
		     ps.close();
		     
		     
		     
			
		}
		
		String query="update bill set total="+total+","+"purchaseDate =  current_date() where billId = "+billId;
		Statement st=connection.createStatement();
		st.execute(query);
		
		query="update wallet set amount=amount-"+total+","+"creditPoints=creditPoints+"+(total/10);
		st.execute(query);
		
		query="delete from cart where customerId="+id;
		st.execute(query);
		connection.commit();
		connection.setAutoCommit(true);
		connection.close();
		return true;
		}
		catch(Exception e)
		{
			connection.rollback();
			System.out.println(e.getMessage());
		}
		
	
		return false;
		
	}
	

	public ArrayList<BillModel> getBill(int id) throws ClassNotFoundException, SQLException
	{
		
		System.out.println("billdao");
		
		ArrayList<BillModel> bill=new ArrayList<>();

		DB db=new DB();
		Connection connection =db.getConnection();
		String query=" SELECT bill.billId,billProduct.productName,billProduct.price,billProduct.quantity ,bill.total,bill.purchaseDate from billProduct\r\n"
				+ "    join bill on bill.billId=billProduct.billId AND bill.customerId=?\r\n"
				+ "    order by bill.billId DESC";
		PreparedStatement ps=connection.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet res=ps.executeQuery();
		int prev=0;
		
		ArrayList<InventoryModel> items=new ArrayList<>();
		BillModel billProduct=new BillModel();
		int billId=0,total=0;
		String date=null;
		if(res.next())
		{  
			total=res.getInt("total");
			prev=res.getInt("billId");
			date = res.getDate("purchaseDate").toString();
		 do
		{
			billId=res.getInt("billId");
			InventoryModel item=new InventoryModel();
			item.setPrice(res.getInt("price"));
			item.setProductName(res.getString("productName"));
			item.setQuantity(res.getInt("quantity"));
			if(billId!=prev)
			{
				billProduct.setTotal(total);
				billProduct.setDate(date);
				billProduct.setProducts(items);
				billProduct.setBillId(prev);
				bill.add(billProduct);
				billProduct=new BillModel();
				total=res.getInt("total");
				prev=billId;
				items=new ArrayList<>();
			}
		     date=res.getDate("purchaseDate").toString();
			items.add(item);
		}while(res.next());
		 
		}
		if(billId!=0)
		{
			billProduct.setTotal(total);
			billProduct.setDate(date);
			billProduct.setProducts(items);
			billProduct.setBillId(prev);
			bill.add(billProduct);
			billProduct=new BillModel();
		}
		 System.out.println("hiiii");
		return bill;
	}

}
