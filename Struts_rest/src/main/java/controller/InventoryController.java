package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import DAO.InventoryDAO;
import dataModel.InventoryModel;

public class InventoryController {
	
	public void fetchInventory() throws ClassNotFoundException, SQLException, IOException
    {
    	ArrayList<InventoryModel> inventories=new ArrayList<>();
    	InventoryDAO inventoryDAO=new InventoryDAO();
    	HttpServletRequest request = ServletActionContext.getRequest();
    	int role=Integer.parseInt(request.getParameter("role"));
    	boolean isAdmin=false;
    	if(role==1)
    	     isAdmin=true;
    	inventories=inventoryDAO.getInventory(isAdmin);
    	HttpServletResponse response = ServletActionContext.getResponse();	//status
    	
    	 Gson gson = new Gson();
         String json = gson.toJson(inventories);
         response.getWriter().write(json);    
    }
	
	public void minInventory() throws ClassNotFoundException, SQLException, IOException
	{
		ArrayList<InventoryModel> inventories=new ArrayList<>();
    	InventoryDAO inventoryDAO=new InventoryDAO();
    	
    	inventories=inventoryDAO.getMinInventory();
    	System.out.println(inventories.size());
    	HttpServletResponse response=ServletActionContext.getResponse();
    	 Gson gson = new Gson();
         String json = gson.toJson(inventories);
         response.getWriter().write(json);
    	
	}

	public void addInventory() throws IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		 String status;
		 
		 InventoryModel inventory=new InventoryModel();
		 inventory.setProductName(request.getParameter("productName"));
		 inventory.setPrice(Integer.parseInt(request.getParameter("price")));
		 inventory.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		 inventory.setCreatedBy(Integer.parseInt(request.getParameter("createdBy")));
		 
		 InventoryDAO inventoryDAO=new InventoryDAO();
		 if(inventoryDAO.addInventory(inventory))
		 {
			 status = "success";
		 }
		 else
		 {
			  status ="failure";
		 }
		 response.getWriter().write(status);
	}
	
	
	public void updateInventory() throws IOException 
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		 String status;
		 
		 InventoryModel inventory=new InventoryModel();
		 inventory.setProductName(request.getParameter("productName"));
		 inventory.setPrice(Integer.parseInt(request.getParameter("price")));
		 inventory.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		 inventory.setProductId(Integer.parseInt(request.getParameter("productId")));
		 inventory.setModifiedBy(Integer.parseInt(request.getParameter("modifiedBy")));
		 InventoryDAO inventoryDAO=new InventoryDAO();
		 if(inventoryDAO.updateInventory(inventory))
		 {
			 status = "success";
		 }
		 else
		 {
			  status ="failure";
		 }
		 response.getWriter().write(status);
	}
	

	public void deleteInventory() throws ClassNotFoundException, SQLException, IOException
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		 String status;
		 
		int inventoryId=Integer.parseInt(request.getParameter("productId"));
		 InventoryDAO inventoryDAO=new InventoryDAO();
		 if(inventoryDAO.deleteInventory(inventoryId))
		 {
			 status = "success";
		 }
		 else
		 {
			  status ="failure";
		 }
		 response.getWriter().write(status);
		
	}
	
	public void topSale() throws ClassNotFoundException, SQLException, IOException
	{
		
		HttpServletResponse response=ServletActionContext.getResponse();
		 InventoryDAO inventoryDAO=new InventoryDAO();
		 
		HashMap<String,Integer> topSale=inventoryDAO.getMaxSale();
		
		 Gson gson = new Gson();
         String json = gson.toJson(topSale);
         response.getWriter().write(json);
		
	}

}
