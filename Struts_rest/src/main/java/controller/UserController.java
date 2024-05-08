package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import DAO.UserDAO;
import dataModel.UserModel;

public class UserController {
   
   
  
	public void authenticate() throws ClassNotFoundException, SQLException, IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		
		HashMap<String,Integer>data=new HashMap<>();
		
		String userName=request.getParameter("userName");	
		String password=request.getParameter("password");
		
		 UserDAO userDAO=new UserDAO();
		 UserModel user=userDAO.autenticate(userName,password);
		 if(user!=null)
		 {
			 data.put("role", user.getRole());
			 data.put("userId", user.getUserId());
			 Gson gson = new Gson();
		     String json = gson.toJson(data);
			 response.getWriter().write(json);
			 response.setStatus(HttpServletResponse.SC_OK);
		 }
		 else
		 {
			 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		 }
		
	}

    public void fetchUsers() throws ClassNotFoundException, SQLException, IOException
    {
    	ArrayList<UserModel> users=new ArrayList<>();
    	UserDAO userDAO=new UserDAO();
    	users=userDAO.getUser();
    	HttpServletResponse response = ServletActionContext.getResponse();	
    	Gson gson = new Gson();
        String json = gson.toJson(users);
        response.getWriter().write(json);    
    }

	public void addUser() throws IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		 String status;
		 
		 UserModel user=new UserModel();
		 user.setUserName(request.getParameter("userName"));
		 user.setEmail(request.getParameter("email"));
		 user.setRole(Integer.parseInt(request.getParameter("role")));
		 user.setPassword(request.getParameter("password"));
		 
		 UserDAO userDAO=new UserDAO();
		 if(userDAO.addUser(user))
		 {
			 status = "success";
		 }
		 else
		 {
			  status ="failure";
		 }
		 response.getWriter().write(status);
	}
	
	
	public void updateUser() throws IOException 
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		 String status;
		 
		 UserModel user=new UserModel();
		 user.setUserName(request.getParameter("userName"));
		 user.setEmail(request.getParameter("email"));
		 user.setRole(Integer.parseInt(request.getParameter("role")));
		 user.setPassword(request.getParameter("password"));
		 user.setUserId(Integer.parseInt(request.getParameter("userId")));
		 UserDAO userDAO=new UserDAO();
		 if(userDAO.updateUser(user))
		 {
			 status = "success";
		 }
		 else
		 {
			  status ="failure";
		 }
		 response.getWriter().write(status);
	}
	
	
	public void deleteUser() throws ClassNotFoundException, SQLException, IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		 String status;
		 
		int userId=Integer.parseInt(request.getParameter("userId"));
		 UserDAO userDAO=new UserDAO();
		 if(userDAO.deleteUser(userId))
		 {
			 status = "success";
		 }
		 else
		 {
			  status ="failure";
		 }
		 response.getWriter().write(status);
		
	}
	
	

}
	

