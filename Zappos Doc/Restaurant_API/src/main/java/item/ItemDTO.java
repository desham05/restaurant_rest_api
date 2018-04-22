package main.java.item;

import java.io.Serializable;

public class ItemDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int itemId;
	private String itemName;
	private String itemDescription;
	private String itemPrice;
	private String dtoSuccess;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getDtoSuccess() {
		return dtoSuccess;
	}
	public void setDtoSuccess(String dtoSuccess) {
		this.dtoSuccess = dtoSuccess;
	}

}
