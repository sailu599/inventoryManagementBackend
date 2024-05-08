package dataModel;

import java.util.ArrayList;
import java.util.HashMap;

public class BillModel {

	int billId;
	String date;
	ArrayList<InventoryModel> products;
	int total;
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<InventoryModel> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<InventoryModel> products) {
		this.products = products;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

}
