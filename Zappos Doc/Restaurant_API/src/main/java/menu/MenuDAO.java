package main.java.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import main.java.constants.ProjectConstants;
import main.java.model.Item;
import main.java.model.Menu;
import main.java.utilities.HibernateUtils;
import main.java.utilities.RESTUtility;

public class MenuDAO {
	private static final Log log = LogFactory.getLog(MenuDAO.class);

	public List<MenuDTO> findallMenu() throws Exception{
		log.debug("in findallMenu() MenuDAO");
		Session session = null;
		List<Menu>  menuList = new ArrayList<Menu>();
		List<MenuDTO> menudtoList = new ArrayList<MenuDTO>(); 
		try {
			session = HibernateUtils.getSessionFactory().openSession();  

			Query query = session.createQuery("from Menu");
			menuList = query.list();
			if(RESTUtility.isDataNotNull(menuList)){
				for (Menu menu : menuList) {
					MenuDTO menudto = new MenuDTO();
					if(RESTUtility.isDataNotNull(menu.getMenuId())){
						menudto.setMenuId(menu.getMenuId());
					}
					if(RESTUtility.isDataNotNull(menu.getMenuType())){
						menudto.setMenuType(menu.getMenuType());
					}
					menudtoList.add(menudto);
				}
			}

			log.debug("successfully fetched Menu. findallMenu() MenuDAO");

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not fetch Menu. findallMenu() MenuDAO", e);
		}finally{
			session.close();
		}
		return menudtoList;
	}

	public MenuDTO saveMenu(List<Menu> menu) throws Exception{
		log.debug("saveMenu() MenuDAO");
		Session session = null;
		Transaction trans = null;
		MenuDTO menudto = new MenuDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();

			for (Menu me : menu) {
				if(RESTUtility.isDataNotNull(me.getMenuType())){
					Menu men = isMenuPresent(me,session);
					if(RESTUtility.isDataNotNull(men)){
						log.debug("Menu is already present. saveMenu() MenuDAO");
					}else{
						me.setMenuType(me.getMenuType().toLowerCase());
						session.save(me);
					}
				}
			}
			trans.commit();
			menudto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully saved Menu. saveMenu() MenuDAO");
		} catch (Exception e) {
			// TODO: handle exception
			menudto.setDtoSuccess(ProjectConstants.FAILURE);
			trans.rollback();
			log.error("Could not save Menu. saveMenu() MenuDAO", e);
		}finally{
			session.close();
		}
		return menudto;
	}

	public MenuDTO deleteMenu(List<Menu> menuList) throws Exception{
		log.debug("in deleteMenu() MenuDAO");
		Session session = null;
		Transaction trans = null;
		MenuDTO menudto = new MenuDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();
			for (Menu menu : menuList) {
				menu = isMenuPresent(menu,session);
				if(RESTUtility.isDataNotNull(menu)){
					session.delete(menu);
				}else{
					log.debug("Menu is not present. deleteMenu() MenuDAO");
				}
			}
			trans.commit();
			menudto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully deleted Menu. deleteMenu() MenuDAO");
		} catch(ConstraintViolationException c){
			menudto.setDtoSuccess(ProjectConstants.FAILURE);
			log.debug("Can not delet menu as used by restaurant");
		}catch (Exception e) {
			// TODO: handle exception
			trans.rollback();
			menudto.setDtoSuccess(ProjectConstants.FAILURE);
			log.error("Could not delete Menu. deleteMenu() MenuDAO", e);
		}finally{
			session.close();
		}
		return menudto;
	}

	public List<MenuDTO> listMenuByRestId(String rest_id) throws Exception{
		log.debug("in listMenuByRestId() MenuDAO");
		Session session = null;
		List<Menu>  menuList = new ArrayList<Menu>();
		List<MenuDTO> menudtoList = new ArrayList<MenuDTO>(); 
		try {
			session = HibernateUtils.getSessionFactory().openSession();  

			Query query = session.createQuery("Select r.menu_id,r.menu_type from Menu where rest_menu rm"
					+ "join menu m on m.menu_id = rm.menu_id"
					+ "where rest_id = '"+rest_id+"'");
			menuList = query.list();
			if(RESTUtility.isDataNotNull(menuList)){
				for (Menu menu : menuList) {
					MenuDTO menudto = new MenuDTO();
					if(RESTUtility.isDataNotNull(menu.getMenuId())){
						menudto.setMenuId(menu.getMenuId());
					}
					if(RESTUtility.isDataNotNull(menu.getMenuType())){
						menudto.setMenuType(menu.getMenuType());
					}
					menudtoList.add(menudto);
				}
			}

			log.debug("successfully fetched Menu. listMenuByRestId() MenuDAO");

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not fetch Menu. listMenuByRestId() MenuDAO", e);
		}finally{
			session.close();
		}
		return menudtoList;
	}

	public Menu isMenuPresent(Menu menu,Session session) throws Exception{
		log.debug("in isMenuPresent() MenuDAO");
		Menu menus = new Menu();
		try {
			Query query = session.createQuery("from Menu where menuType = '"+ menu.getMenuType().toLowerCase()+"'");
			menus = (Menu)query.uniqueResult();
			if(RESTUtility.isDataNotNull(menus)){
				menus.setMenuType(menu.getMenuType());
				log.debug("Menu is present. isMenuPresent() MenuDAO");
			}else{
				log.debug("Menu is not present. isMenuPresent() MenuDAO");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(" Error updating Menu. isMenuPresent() MenuDAO", e);
		}
		return menus;
	}

	public MenuDTO saveMenuItem(List<Menu> menuList) throws Exception{
		log.debug("saveMenuItem() MenuDAO");
		Session session = null;
		Transaction trans = null;
		MenuDTO menudto = new MenuDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();

			for (Menu menuitems : menuList) {
				if(RESTUtility.isDataNotNull(menuitems.getMenuType())){
					Menu menudata = isMenuPresent(menuitems,session);
					if(RESTUtility.isDataNotNull(menudata)){
						log.debug("Menu is already present. saveMenuItem() MenuDAO");
					}else{
						Menu menu = new Menu();
						if(RESTUtility.isDataNotNull(menuitems.getMenuType())){
							menu.setMenuType(menuitems.getMenuType());
						}
						session.save(menu);
						trans.commit();

						if(trans.wasCommitted()){
							trans = session.beginTransaction();
							if(RESTUtility.isDataNotNull(menuitems.getItems())){
								Set<Item> itemset = menuitems.getItems();
								Iterator<Item> it = itemset.iterator();
								while(it.hasNext()){
									Item item = it.next();
									Item newItem = new Item();
									if(RESTUtility.isDataNotNull(menu.getMenuId())){
										Menu men = new Menu();
										men.setMenuId(menu.getMenuId());
										newItem.setMenu(menu);
									}
									if(RESTUtility.isDataNotNull(item.getItemName())){
										newItem.setItemName(item.getItemName());
									}
									if(RESTUtility.isDataNotNull(item.getItemDescription())){
										newItem.setItemDescription(item.getItemDescription());
									}
									if(RESTUtility.isDataNotNull(item.getItemPrice())){
										newItem.setItemPrice(item.getItemPrice());
									}
									session.save(newItem);
								}
							}
						}
					}

				}
			}
			trans.commit();
			menudto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully saved Menu. saveMenuItem() MenuDAO");
		} catch (Exception e) {
			// TODO: handle exception
			menudto.setDtoSuccess(ProjectConstants.FAILURE);
			trans.rollback();
			log.error("Could not save Menu. saveMenuItem() MenuDAO", e);
		}finally{
			session.close();
		}
		return menudto;
	}
}
