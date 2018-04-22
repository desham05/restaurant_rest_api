package main.java.restaurant;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import main.java.constants.ProjectConstants;
import main.java.model.RestMenu;
import main.java.model.Restaurants;
import main.java.utilities.RESTUtility;

@Path("/restaurants")
public class RestaurantAction {

	private static final Log log = LogFactory.getLog(RestaurantAction.class);

	@Path("/findallrestaurants")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RestMenu> findallRestaurant(){
		log.debug("in findallRestaurant() RestaurantAction");
		RestaurantDAO  restdao = new RestaurantDAO();
		List<RestMenu> restmenuList = new ArrayList();
		try {
			restmenuList = restdao.findallRestaurant();
			log.debug("successfully fetched Restaurant.");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not find Restaurant in findallRestaurant() RestaurantAction", e);
		}
		return restmenuList;
	}

	@POST	
	@Path("/createrestaurant")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String saveRestaurant(List<Restaurants> restaurant){
		log.debug("in saveRestaurant() RestaurantAction ");
		RestaurantDTO restdto = new RestaurantDTO();
		RestaurantDAO  restdao = new RestaurantDAO();
		String response = null;
		try {
			restdto = restdao.saveRestaurant(restaurant);
			if(restdto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Restaurant added successfully";
				log.debug("successfully saved Restaurant.() RestaurantAction");
			}else{
				response = "Incomplete data of restaurant";
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = "Error while adding restaurant";
			log.error("Could not save Restaurant in saveRestaurant() RestaurantAction ", e);
		}
		return response;
	}

	@PUT	
	@Path("/updaterestaurant")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateRestaurant(List<Restaurants> restaurant){
		log.debug("in updateRestaurant() RestaurantAction");
		RestaurantDTO restdto = new RestaurantDTO();
		RestaurantDAO  restdao = new RestaurantDAO();
		String response = null;
		try {
			restdto = restdao.updateRestaurant(restaurant);
			if(restdto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Restaurants updated successfully";
				log.debug("successfully updated Restaurants.() RestaurantAction");
			}else{
				response = "Incomplete data of restaurant";
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = "Error while adding restaurant";
			log.error("Could not update Restaurant in updateRestaurant() RestaurantAction", e);
		}
		return response;
	}

	@DELETE	
	@Path("/deleterestaurant")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteRestaurant(List<Restaurants> restaurant){
		log.debug("in deleteRestaurant() RestaurantAction");
		RestaurantDTO restdto = new RestaurantDTO();
		RestaurantDAO  restdao = new RestaurantDAO();
		String response = null;
		try {
			restdto = restdao.deleteRestaurant(restaurant);
			if(restdto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Restaurants deleted successfully";
				log.debug("successfully deleted deleteRestaurant.() RestaurantAction");
			}else{
				response = "No data to delete restaurant";
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = "Error while deleting restaurant";
			log.error("Could not update Restaurant in deleteRestaurant() RestaurantAction", e);
		}
		return response;
	}

	@POST	
	@Path("/findrestaurantbyname")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurants findRestaurantDataByName(String restName){
		log.debug("in findRestaurantDataByName() RestaurantAction");
		Restaurants rest = new Restaurants();
		RestaurantDAO  restdao = new RestaurantDAO();
		try {
			if(RESTUtility.isDataNotNull(restName) && RESTUtility.isDataNotBlank(restName)){
				rest = restdao.findRestaurantDataByName(restName);
			}

			if(!RESTUtility.isDataNotNull(rest)){
				log.debug("can not find findRestaurantDataByName() RestaurantAction");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not find Restaurant in findRestaurantDataByName() RestaurantAction", e);
		}
		return rest;
	}
	
	@POST	
	@Path("/saverestaurantmenuitem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String saveRestaurantMenuItem(List<RestMenu> restmenu){
		log.debug("in saveRestaurantMenuItem() RestaurantAction ");
		RestaurantDTO restdto = new RestaurantDTO();
		RestaurantDAO  restdao = new RestaurantDAO();
		String response = null;
		try {
			restdto = restdao.saveRestaurantMenuItem(restmenu);
			if(restdto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Restaurant added successfully";
				log.debug("successfully saved saveRestaurantMenuItem.() RestaurantAction");
			}else{
				response = "Incomplete data of restaurant";
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = "Error while adding restaurant";
			log.error("Could not save Restaurant in saveRestaurantMenuItem() RestaurantAction ", e);
		}
		return response;
	}
}
