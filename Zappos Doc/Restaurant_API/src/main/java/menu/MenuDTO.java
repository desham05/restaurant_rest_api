package main.java.menu;

import java.io.Serializable;

public class MenuDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private int menuId;
	private String menuType;
	private String dtoSuccess;
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getDtoSuccess() {
		return dtoSuccess;
	}
	public void setDtoSuccess(String dtoSuccess) {
		this.dtoSuccess = dtoSuccess;
	}
	
}
