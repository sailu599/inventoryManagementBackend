package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import DAO.WalletDAO;
import DAO.WalletDAO;
import dataModel.WalletModel;

public class wallletController {

	
	public void getWallet() throws IOException, ClassNotFoundException, SQLException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		System.out.println("Walletcontroller");
		int customerId=Integer.parseInt(request.getParameter("customerId"));
	
		WalletDAO walletDAO=new WalletDAO();
		String status;
		System.out.println("Walletcontroller");
		WalletModel wallet=walletDAO.getWallet(customerId);
		 Gson gson = new Gson();
         String json = gson.toJson(wallet);
         System.out.println(json);
         response.getWriter().write(json); 	
	}
	
	
	public void updateWallet() throws IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		
		int id=Integer.parseInt(request.getParameter("customerId"));
		int amount=Integer.parseInt(request.getParameter("amount"));
		
		WalletDAO walletDAO=new WalletDAO();
		
		String status;
		if(walletDAO.updateWallet(id,amount))
		{
			status="success";
		}
		else
		{
			status="failure";
		}
		
		response.getWriter().write(status);
		
	}
	
	public void redeemWallet() throws IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		

		int id=Integer.parseInt(request.getParameter("customerId"));
		

		WalletDAO walletDAO=new WalletDAO();
		
		String status;
		if(walletDAO.redeemWallet(id))
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
