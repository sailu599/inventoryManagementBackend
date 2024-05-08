package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dataModel.WalletModel;

public class WalletDAO {

	public WalletModel getWallet(int customerId) throws ClassNotFoundException, SQLException
	{
		DB db=new DB();
		Connection connection=db.getConnection();
		
        String query="select amount,creditPoints from wallet where customerId="+customerId;
        
        WalletModel wallet=new WalletModel();
        
      Statement st=connection.createStatement();
      ResultSet rs=st.executeQuery(query);
      if(rs.next())
      {
    	  wallet.setAmount(rs.getInt("amount"));  
    	  wallet.setCreditPoints(rs.getInt("creditPoints"));
    	  
      }
      return wallet;
	}
	
	public boolean updateWallet(int id,int amount)
	{
		String query="update wallet set amount=amount+? where customerId=?";
		DB db=new DB();
		
		try 
		{
			Connection connection=db.getConnection();
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1, amount);
			ps.setInt(2, id);
			System.out.println(ps);
			ps.executeUpdate();
			return true;
			
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean redeemWallet(int id) {
		String query="update wallet set amount=amount+(creditPoints/10),creditPoints=(creditPoints%10) where customerId=?";
		DB db=new DB();
		
		try 
		{
			Connection connection=db.getConnection();
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1,id);
			System.out.println(ps);
			ps.executeUpdate();
			return true;
			
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	
		
	}
	
}
