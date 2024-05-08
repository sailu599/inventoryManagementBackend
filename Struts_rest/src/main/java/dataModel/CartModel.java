package dataModel;

import java.util.ArrayList;

public class CartModel {
	private int c_id;
	private ArrayList<InventoryModel> cartItems;
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public ArrayList<InventoryModel> getCartItems() {
		return cartItems;
	}
	public void setCartItems(ArrayList<InventoryModel> cartItems) {
		this.cartItems = cartItems;
	}
	

}
