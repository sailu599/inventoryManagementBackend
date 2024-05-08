package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import DAO.CartDAO;
import dataModel.CartModel;

public class CartController {
	
	
	public void getCart() throws IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		
		int id=Integer.parseInt(request.getParameter("customerId"));
		
		CartModel cart=new CartModel();
	    CartDAO cartDAO=new CartDAO();
	    
	    cart=cartDAO.getCart(id);
	    
	    Gson gson = new Gson();
	    String json=gson.toJson(cart);
	    response.getWriter().write(json);
	}
	
	
	public void addCart() throws IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		
		int customerId=Integer.parseInt(request.getParameter("customerId"));
		int productId=Integer.parseInt(request.getParameter("productId"));
		int quantity=Integer.parseInt(request.getParameter("quantity"));
		String status;
		
		CartDAO cartDAO=new CartDAO();
		
		if(cartDAO.addCart(customerId, productId, quantity))
		{
			status="success";
		}
		else
		{
			status="failure";
		}
		
		response.getWriter().write(status);
	}
	
	
	public void deleteCart() throws IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		
		int id=Integer.parseInt(request.getParameter("cartId"));
		CartDAO cartDAO=new CartDAO();
		
		String status;
		if(cartDAO.deleteCart(id))
		{
			status="success";
		}
		else
		{
			status="failure";
		}
		
		response.getWriter().write(status);
		
	}
	
	
	public void updateCart() throws IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		
		int id=Integer.parseInt(request.getParameter("cartId"));
		int quantity=Integer.parseInt(request.getParameter("quantity"));
		
		CartDAO cartDAO=new CartDAO();
		
		String status;
		if(cartDAO.updateCart(id,quantity))
		{
			status="success";
		}
		else
		{
			status="failure";
		}
		
		response.getWriter().write(status);
		
	}

}
