package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataModel.UserModel;

public class UserDAO {
	
	
	public ArrayList<UserModel> getUser() throws ClassNotFoundException, SQLException
	{
		DB db=new DB();
		Connection connection=db.getConnection();
		ArrayList<UserModel> users=new ArrayList<>();
		String query="select *from user where status=1 and role=2";
		Statement st=connection.createStatement();
		ResultSet result=st.executeQuery(query);
		while(result.next())
		{
			UserModel user=new UserModel();
			user.setUserId(result.getInt("userId"));
			user.setUserName(result.getString("userName"));;
			user.setEmail(result.getString("email"));;
			user.setPassword(result.getString("password"));;
			user.setRole(result.getInt("role"));
			users.add(user);
		}
		connection.close();
		return users;
	}
	
	public boolean addUser(UserModel user)
	{
		DB db=new DB();
		try {
			Connection connection=db.getConnection();
			String query="insert into user(userName,role,email,password) values(?,?,?,?)";
			PreparedStatement ps=connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUserName());
			ps.setInt(2, user.getRole());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.execute();
			if(user.getRole()==3)
			{
			  ResultSet result=ps.getGeneratedKeys();
			  result.next();
			  int customerId=result.getInt(1);
			  System.out.println(customerId);
			  query="insert into wallet(customerId) value(?)";
			  ps=connection.prepareStatement(query);
			  ps.setInt(1,customerId);
			  ps.execute();
			}
			return true;
			
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return false;
		
	}
	
	
	public boolean updateUser(UserModel user)
	{
		DB db=new DB();
		try {
			Connection connection=db.getConnection();
			String query="update user set userName=?,role=?,email=?,password=? where userId=?";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, user.getUserName());
			ps.setInt(2, user.getRole());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setInt(5, user.getUserId());
			System.out.println(ps);
			ps.execute();
			return true;
			
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean deleteUser(int id) throws ClassNotFoundException, SQLException
	{
		
		DB db=new DB();
	    Connection	connection=db.getConnection();
		String query="update User set status = 0 where userId = "+id;
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

	public UserModel autenticate(String userName, String password) throws ClassNotFoundException, SQLException {

		DB db=new DB();
	    Connection	connection=db.getConnection();
		
        String query="select * from user WHERE userName = ? AND password = ?";
        PreparedStatement ps=connection.prepareStatement(query);
        ps.setString(1, userName);
        ps.setString(2, password);
        
        ResultSet result=ps.executeQuery();
        
        UserModel userModel=new UserModel();
        
        if(result.next())
        {
        	userModel.setRole(result.getInt("role"));
        	userModel.setUserId(result.getInt("userId"));
        	return userModel;
        }
    	System.out.println("false");
		return null;
	}

}
