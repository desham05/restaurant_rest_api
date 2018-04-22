package main.java.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import main.java.constants.ProjectConstants;
import main.java.model.Item;
import main.java.utilities.HibernateUtils;
import main.java.utilities.RESTUtility;

public class ItemDAO {
	private static final Log log = LogFactory.getLog(ItemDAO.class);

	public List<ItemDTO> findallItem() throws Exception{
		log.debug("in findallItem() ItemDAO");
		Session session = null;
		List<Item>  itemList = new ArrayList<Item>();
		List<ItemDTO> itemdtoList = new ArrayList<ItemDTO>(); 
		try {
			session = HibernateUtils.getSessionFactory().openSession();  

			Query query = session.createQuery("from Items");
			itemList = query.list();
			if(RESTUtility.isDataNotNull(itemList)){
				for (Item item : itemList) {
					ItemDTO itemdto = new ItemDTO();
					if(RESTUtility.isDataNotNull(item.getItemId())){
						itemdto.setItemId(item.getItemId());
					}
					if(RESTUtility.isDataNotNull(item.getItemName())){
						itemdto.setItemName(item.getItemName());
					}
					if(RESTUtility.isDataNotNull(item.getItemDescription())){
						itemdto.setItemDescription(item.getItemDescription());
					}
					if(RESTUtility.isDataNotNull(item.getItemPrice())){
						itemdto.setItemPrice(item.getItemPrice().toString());
					}
					itemdtoList.add(itemdto);
				}
			}

			log.debug("successfully fetched Item. findallItem() ItemDAO");

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not fetch Item. findallItem() ItemDAO", e);
		}finally{
			session.close();
		}
		return itemdtoList;
	}

	public ItemDTO saveItem(List<Item> item) throws Exception{
		log.debug("saveItem() ItemDAO");
		Session session = null;
		Transaction trans = null;
		ItemDTO itemdto = new ItemDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();

			for (Item items : item) {
				if(RESTUtility.isDataNotNull(items.getItemName())
						|| RESTUtility.isDataNotNull(items.getItemDescription()) || RESTUtility.isDataNotNull(items.getItemPrice())){
					Item itemCheck = isItemPresent(items,session);
					if(RESTUtility.isDataNotNull(itemCheck)){
						log.debug("Item is already present. saveItem() ItemDAO");
					}else{
						items.setItemName(items.getItemName().toLowerCase());
						session.save(items);
					}
				}
			}
			trans.commit();
			itemdto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully saved Item. saveItem() ItemDAO");
		} catch (Exception e) {
			// TODO: handle exception
			itemdto.setDtoSuccess(ProjectConstants.FAILURE);
			trans.rollback();
			log.error("Could not save Item. saveItem() ItemDAO", e);
		}finally{
			session.close();
		}
		return itemdto;
	}

	public ItemDTO updateItem(List<Item> itemdtoList) throws Exception{
		log.debug("in updateItem() ItemDAO");
		Session session = null;
		Transaction trans = null;
		ItemDTO itemdto = new ItemDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();
			for (Item items : itemdtoList) {
				items = isItemPresent(items,session);
				if(RESTUtility.isDataNotNull(items)){
					session.update(items);
				}else{
					log.debug("Item is not present. updateItem() ItemDAO");
				}
			}
			trans.commit();
			itemdto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully saved Item. updateItem() ItemDAO");
		} catch (Exception e) {
			// TODO: handle exception
			trans.rollback();
			itemdto.setDtoSuccess(ProjectConstants.FAILURE);
			log.error("Could not update Item. updateItem() ItemDAO", e);
		}finally{
			session.close();
		}
		return itemdto;
	}

	public ItemDTO deleteItem(List<Item> itemdtoList) throws Exception{
		log.debug("in deleteItem() ItemDAO");
		Session session = null;
		Transaction trans = null;
		ItemDTO itemdto = new ItemDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();
			for (Item items : itemdtoList) {
				items = isItemPresent(items,session);
				if(RESTUtility.isDataNotNull(items)){
					session.delete(items);
				}else{
					log.debug("Item is not present. deleteItem() ItemDAO");
				}
			}
			trans.commit();
			itemdto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully deleted Item. deleteItem() ItemDAO");
		} catch (Exception e) {
			// TODO: handle exception
			trans.rollback();
			itemdto.setDtoSuccess(ProjectConstants.FAILURE);
			log.error("Could not delete Item. deleteItem() ItemDAO", e);
		}finally{
			session.close();
		}
		return itemdto;
	}

	public List<ItemDTO> listItemByMenuId(String menu_id) throws Exception{
		log.debug("in listItemByMenuId() ItemDAO");
		Session session = null;
		List<Item>  itList = new ArrayList<Item>();
		List<ItemDTO> itdtoList = new ArrayList<ItemDTO>(); 
		try {
			session = HibernateUtils.getSessionFactory().openSession();  

			Query query = session.createQuery("Select * from Item where menu_id = '"+menu_id+"'");
			itList = query.list();
			if(RESTUtility.isDataNotNull(itList)){
				for (Item item : itList) {
					ItemDTO itdto = new ItemDTO();
					if(RESTUtility.isDataNotNull(item.getItemId())){
						itdto.setItemId(item.getItemId());
					}
					if(RESTUtility.isDataNotNull(item.getItemName())){
						itdto.setItemName(item.getItemName());
					}
					if(RESTUtility.isDataNotNull(item.getItemDescription())){
						itdto.setItemDescription(item.getItemDescription());
					}
					if(RESTUtility.isDataNotNull(item.getItemPrice())){
						itdto.setItemPrice(item.getItemPrice().toString());
					}
					itdtoList.add(itdto);
				}
			}

			log.debug("successfully fetched Item. listMenuByRestId() ItemDAO");

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not fetch Item. listMenuByRestId() ItemDAO", e);
		}finally{
			session.close();
		}
		return itdtoList;
	}

	public Item findItemDataByName(String itemName) throws Exception{
		log.debug("in findItemDataByName() ItemDAO");
		Session session = null;
		Item  item = new Item();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  

			if(RESTUtility.isDataNotNull(itemName)){
				Query query = session.createQuery("from Item where itemName = '"+ itemName.toLowerCase()+"'");
				item = (Item)query.uniqueResult();
				if(!RESTUtility.isDataNotNull(item)){
					log.debug("No record for Item. findItemDataByName() ItemDAO");
				}
			}else{
				log.debug("Please enter Item Name. findItemDataByName() ItemDAO");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not fetch Item. findItemDataByName() ItemDAO", e);
		}finally{
			session.close();
		}
		return item;
	}

	public Item isItemPresent(Item item,Session session) throws Exception{
		log.debug("in isItemPresent() ItemDAO");
		Item items = new Item();
		try {
			Query query = session.createQuery("from Item where itemName = '"+ item.getItemName().toLowerCase()+"'");
			items = (Item)query.uniqueResult();
			if(RESTUtility.isDataNotNull(items)){
				items.setItemName(item.getItemName());
				items.setItemDescription(item.getItemDescription());
				items.setItemPrice(item.getItemPrice());
				log.debug("Item is present. isItemPresent() ItemDAO");
			}else{
				log.debug("Item is not present. isItemPresent() ItemDAO");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(" Error updating Item. isItemPresent() ItemDAO", e);
		}
		return items;
	}
}
