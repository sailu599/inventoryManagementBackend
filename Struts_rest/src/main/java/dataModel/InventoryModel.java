package dataModel;

public class InventoryModel {
	private int productId ;
	private String productName; 
	private int quantity; 
	private int price;
	private int max;
	private int createdBy;
	private int modifiedBy;
	private String creatorName;
	private String editedName;
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getEditedName() {
		return editedName;
	}
	public void setEditedName(String editedName) {
		this.editedName = editedName;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Override
	public String toString() {
		return "InventoryModel [productId=" + productId + ", productName=" + productName + ", quantity=" + quantity
				+ ", price=" + price + ", max=" + max + "]";
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	} 

}
