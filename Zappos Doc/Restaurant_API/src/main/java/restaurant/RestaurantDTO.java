package main.java.restaurant;

import java.io.Serializable;

public class RestaurantDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int restaurantId;
	private String restaurantName;
	private String restaurantAddress;
	private String restaurantStars;
	private String dtoSuccess;

	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
	public String getRestaurantStars() {
		return restaurantStars;
	}
	public void setRestaurantStars(String restaurantStars) {
		this.restaurantStars = restaurantStars;
	}
	public String getDtoSuccess() {
		return dtoSuccess;
	}
	public void setDtoSuccess(String dtoSuccess) {
		this.dtoSuccess = dtoSuccess;
	}

}
