package main.java.item;

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
import main.java.model.Item;
import main.java.utilities.RESTUtility;
@Path("/items")
public class ItemAction {
	private static final Log log = LogFactory.getLog(ItemAction.class);

	@Path("/findallitems")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemDTO> findallItem(){
		log.debug("in findallItem() ItemAction");
		ItemDAO  itemdao = new ItemDAO();
		List<ItemDTO> itemdtoList = new ArrayList();
		try {
			itemdtoList = itemdao.findallItem();
			log.debug("successfully fetched item.");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not find Item in findallItem() ItemAction", e);
		}
		return itemdtoList;
	}

	@POST	
	@Path("/createitem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String saveItem(List<Item> item){
		log.debug("in saveItem() ItemAction ");
		ItemDTO itemdto = new ItemDTO();
		ItemDAO  itemdao = new ItemDAO();
		String response = null;
		try {
			itemdto = itemdao.saveItem(item);
			if(itemdto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Item added successfully";
				log.debug("successfully saved saveItem.() ItemAction");
			}else{
				response = "Incomplete data of item";
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = "Error while adding item";
			log.error("Could not save Item in saveItem() ItemAction ", e);
		}
		return response;
	}

	@PUT	
	@Path("/updateitem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(List<Item> item){
		log.debug("in updateItem() ItemAction");
		ItemDTO itemdto = new ItemDTO();
		ItemDAO  itemdao = new ItemDAO();
		String response = null;
		try {
			itemdto = itemdao.updateItem(item);
			if(itemdto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Item updated successfully";
				log.debug("successfully updated updateItem.() ItemAction");
			}else{
				response = "Incomplete data of item";
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = "Error while adding item";
			log.error("Could not update Item in updateItem() ItemAction", e);
		}
		return response;
	}

	@DELETE	
	@Path("/deleteitem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(List<Item> item){
		log.debug("in deleteItem() ItemAction");
		ItemDTO itemdto = new ItemDTO();
		ItemDAO  itemdao = new ItemDAO();
		String response = null;
		try {
			itemdto = itemdao.deleteItem(item);
			if(itemdto.getDtoSuccess() == ProjectConstants.SUCCESS){
				response = "Restaurants deleted successfully";
				log.debug("successfully deleted deleteItem.() ItemAction");
			}else{
				response = "No data to delete item";
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = "Error while deleting item";
			log.error("Could not update item in deleteItem() ItemAction", e);
		}
		return response;
	}

	@POST	
	@Path("/finditembyname")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Item findItemDataByName(String itemName){
		log.debug("in findItemDataByName() ItemAction");
		Item item = new Item();
		ItemDAO  itemdao = new ItemDAO();
		try {
			if(RESTUtility.isDataNotNull(itemName) && RESTUtility.isDataNotBlank(itemName)){
				item = itemdao.findItemDataByName(itemName);
			}

			if(!RESTUtility.isDataNotNull(item)){
				log.debug("can not find findItemDataByName() ItemAction");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not find item in findItemDataByName() ItemAction", e);
		}
		return item;
	}

	public void listItemByMenuId(){
		log.debug("in listItemByMenuId() ItemAction");
		List<ItemDTO>  itemDTOList = new ArrayList<ItemDTO>();
		ItemDAO itdao = new ItemDAO();
		try {
			if(RESTUtility.isDataNotBlank("menu_id")){
				itemDTOList = itdao.listItemByMenuId("menu_id");
				for (ItemDTO itemDTO : itemDTOList) {
					if(RESTUtility.isDataNotNull(itemDTO.getItemId())){
						itemDTO.setItemId(itemDTO.getItemId());
					}
					if(RESTUtility.isDataNotNull(itemDTO.getItemName())){
						itemDTO.setItemName(itemDTO.getItemName());
					}
					if(RESTUtility.isDataNotNull(itemDTO.getItemDescription())){
						itemDTO.setItemDescription(itemDTO.getItemDescription());
					}
					if(RESTUtility.isDataNotNull(itemDTO.getItemPrice())){
						itemDTO.setItemPrice(itemDTO.getItemPrice());
					}
				}
			}
			else{
				log.debug("can not find listItemByMenuId() ItemAction");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not find Item in listItemByMenuId() ItemAction", e);
		}
	}
}
