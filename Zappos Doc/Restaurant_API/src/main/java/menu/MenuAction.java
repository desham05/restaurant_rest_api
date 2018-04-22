package main.java.menu;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

import main.java.constants.ProjectConstants;
import main.java.model.Menu;
import main.java.utilities.RESTUtility;

@Path("/menu")
public class MenuAction {
	private static final Log log = LogFactory.getLog(MenuAction.class);

	@Path("/findallmenus")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MenuDTO> findallMenu(){
		log.debug("in findallMenu() MenuAction");
		MenuDAO  menudao = new MenuDAO();
		List<MenuDTO> menudtoList = new ArrayList();
		try {
			menudtoList = menudao.findallMenu();
			log.debug("successfully fetched Menu.");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not find Menu in findallMenu() MenuAction", e);
		}
		return menudtoList;
	}

	@POST	
	@Path("/createmenu")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String saveMenu(List<Menu> menu){
		log.debug("in saveMenu() MenuAction ");
		MenuDTO menudto = new MenuDTO();
		MenuDAO  menudao = new MenuDAO();
		String response = null;
		try {
			menudto = menudao.saveMenu(menu);
			if(menudto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Menu added successfully";
				log.debug("successfully saved Menu.() MenuAction");
			}else{
				response = "Incomplete data of menu";
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = "Error while adding menu";
			log.error("Could not save menu in saveMenu() MenuAction ", e);
		}
		return response;
	}


	@DELETE	
	@Path("/deletemenu")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteMenu(List<Menu> menu){
		log.debug("in deleteMenu() MenuAction");
		MenuDTO menudto = new MenuDTO();
		MenuDAO  menudao = new MenuDAO();
		String response = null;
		try {
			menudto = menudao.deleteMenu(menu);
			if(menudto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Menu deleted successfully";
				log.debug("successfully deleted deleteMenu.() MenuAction");
			}else{
				response = "Can not delete data for menu";
			}
		}catch (Exception e) {
			// TODO: handle exception
			if(response == null){
				response = "Error while deleting menu";
			}
			log.error("Could not update Menu in deleteMenu() MenuAction", e);
		}
		return response;
	}

	public void listMenuByRestId(){
		log.debug("in listMenuByRestId() MenuAction");
		List<MenuDTO>  menudtoList = new ArrayList<MenuDTO>();
		MenuDAO mendao = new MenuDAO();
		try {
			if(RESTUtility.isDataNotBlank("rest_id")){
				menudtoList = mendao.listMenuByRestId("rest_id");
				for (MenuDTO menudto : menudtoList) {
					if(RESTUtility.isDataNotNull(menudto.getMenuId())){
						menudto.setMenuId(menudto.getMenuId());
					}
					if(RESTUtility.isDataNotNull(menudto.getMenuType())){
						menudto.setMenuType(menudto.getMenuType());
					}

				}
			}
			else{
				log.debug("can not find listMenuByRestId() MenuAction");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not find Menu in listMenuByRestId() MenuAction", e);
		}
	}
	
	@POST	
	@Path("/savemenuitem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String saveMenuItem(List<Menu> menu){
		log.debug("in saveMenuItem() RestaurantAction ");
		MenuDTO menudto = new MenuDTO();
		MenuDAO  menudao = new MenuDAO();
		String response = null;
		try {
			menudto = menudao.saveMenuItem(menu);
			if(menudto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Menu added successfully";
				log.debug("successfully saved saveMenuItem.() MenuAction");
			}else{
				response = "Incomplete data of menu";
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = "Error while adding menu";
			log.error("Could not save Menu in saveMenuItem() MenuAction ", e);
		}
		return response;
	}
}
