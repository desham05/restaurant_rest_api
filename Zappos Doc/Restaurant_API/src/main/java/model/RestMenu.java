package main.java.model;
// Generated 20 Apr, 2018 1:51:22 PM by Hibernate Tools 3.6.0.Final

/**
 * RestMenu generated by hbm2java
 */
public class RestMenu implements java.io.Serializable {

	private Integer restMenuId;
	private Restaurants restaurants;
	private Menu menu;

	public RestMenu() {
	}

	public RestMenu(Restaurants restaurants, Menu menu) {
		this.restaurants = restaurants;
		this.menu = menu;
	}

	public Integer getRestMenuId() {
		return this.restMenuId;
	}

	public void setRestMenuId(Integer restMenuId) {
		this.restMenuId = restMenuId;
	}

	public Restaurants getRestaurants() {
		return this.restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
