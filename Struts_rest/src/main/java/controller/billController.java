package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import DAO.BillDAO;
import dataModel.BillModel;

public class billController {
	
	
	public void addBill() throws ClassNotFoundException, SQLException, IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		
		int customerId=Integer.parseInt(request.getParameter("customerId"));
	
		BillDAO billDAO=new BillDAO();
		String status;
		if(billDAO.addBill(customerId))
			status="success";
		else 
			status="failure";
		response.getWriter().write(status);
		
		
	}
	
	public void getBill() throws ClassNotFoundException, SQLException, IOException
	{
		
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		
		int customerId=Integer.parseInt(request.getParameter("customerId"));
	
		BillDAO billDAO=new BillDAO();
		String status;
		System.out.println("Billcontroller");
		ArrayList<BillModel> bill=billDAO.getBill(customerId);
		 Gson gson = new Gson();
         String json = gson.toJson(bill);
         System.out.println(json);
         response.getWriter().write(json); 
		
	}

}
