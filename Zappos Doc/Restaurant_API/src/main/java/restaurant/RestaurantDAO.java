package main.java.restaurant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import main.java.constants.ProjectConstants;
import main.java.model.Item;
import main.java.model.Menu;
import main.java.model.RestMenu;
import main.java.model.Restaurants;
import main.java.utilities.HibernateUtils;
import main.java.utilities.RESTUtility;

public class RestaurantDAO {
	private static final Log log = LogFactory.getLog(RestaurantDAO.class);

	public List<RestMenu> findallRestaurant() throws Exception{
		log.debug("in findallRestaurant() RestaurantDAO");
		Session session = null;
		List<RestMenu> restmenuList = new ArrayList<RestMenu>(); 
		Set<Item> itemList = new HashSet<Item>(); 
		
		try {
			session = HibernateUtils.getSessionFactory().openSession();  

			Criteria c = session.createCriteria(RestMenu.class, "rm");
							c.createAlias("rm.restaurants", "r");
							c.createAlias("rm.menu", "m");
							c.setResultTransformer(Transformers.aliasToBean(RestMenu.class));

			ProjectionList p1=Projections.projectionList();
			p1.add(Projections.property("r.restaurantName"));
			p1.add(Projections.property("r.restaurantAddress")); 
			p1.add(Projections.property("r.restaurantStars")); 
			p1.add(Projections.property("m.menuType")); 
			
			c.setProjection(p1);
			
			restmenuList = c.list();

			log.debug("successfully fetched Restaurant. findallRestaurant() RestaurantDAO");

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not fetch Restaurant. findallRestaurant() RestaurantDAO", e);
		}finally{
			session.close();
		}
		return restmenuList;
	}

	public RestaurantDTO saveRestaurant(List<Restaurants> rest) throws Exception{
		log.debug("saveRestaurant() RestaurantDAO");
		Session session = null;
		Transaction trans = null;
		RestaurantDTO restdto = new RestaurantDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();

			for (Restaurants restaurants : rest) {
				if(RESTUtility.isDataNotNull(restaurants.getRestaurantName())
						|| RESTUtility.isDataNotNull(restaurants.getRestaurantAddress()) || RESTUtility.isDataNotNull(restaurants.getRestaurantStars())){
					restaurants = isRestaurantPresent(restaurants,session);
					if(RESTUtility.isDataNotNull(restaurants)){
						log.debug("Restaurant is already present. updateRestaurant() RestaurantDAO");
					}else{
						restaurants.setRestaurantName(restaurants.getRestaurantName().toLowerCase());
						session.save(restaurants);
					}
				}
			}
			trans.commit();
			restdto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully saved Restaurant. saveRestaurant() RestaurantDAO");
		} catch (Exception e) {
			// TODO: handle exception
			restdto.setDtoSuccess(ProjectConstants.FAILURE);
			trans.rollback();
			log.error("Could not save Restaurant. saveRestaurant() RestaurantDAO", e);
		}finally{
			session.close();
		}
		return restdto;
	}

	public RestaurantDTO updateRestaurant(List<Restaurants> restdtoList) throws Exception{
		log.debug("in updateRestaurant() RestaurantDAO");
		Session session = null;
		Transaction trans = null;
		RestaurantDTO restdto = new RestaurantDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();
			for (Restaurants restaurants : restdtoList) {
				restaurants = isRestaurantPresent(restaurants,session);
				if(RESTUtility.isDataNotNull(restaurants)){
					session.update(restaurants);
				}else{
					log.debug("Restaurant is not present. updateRestaurant() RestaurantDAO");
				}
			}
			trans.commit();
			restdto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully saved Restaurant. updateRestaurant() RestaurantDAO");
		} catch (Exception e) {
			// TODO: handle exception
			trans.rollback();
			restdto.setDtoSuccess(ProjectConstants.FAILURE);
			log.error("Could not update Restaurant. updateRestaurant() RestaurantDAO", e);
		}finally{
			session.close();
		}
		return restdto;
	}

	public RestaurantDTO deleteRestaurant(List<Restaurants> restdtoList) throws Exception{
		log.debug("in deleteRestaurant() RestaurantDAO");
		Session session = null;
		Transaction trans = null;
		RestaurantDTO restdto = new RestaurantDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();
			for (Restaurants restaurants : restdtoList) {
				restaurants = isRestaurantPresent(restaurants,session);
				if(RESTUtility.isDataNotNull(restaurants)){
					session.delete(restaurants);
				}else{
					log.debug("Restaurant is not present. deleteRestaurant() RestaurantDAO");
				}
			}
			trans.commit();
			restdto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully deleted Restaurant. deleteRestaurant() RestaurantDAO");
		} catch (Exception e) {
			// TODO: handle exception
			trans.rollback();
			restdto.setDtoSuccess(ProjectConstants.FAILURE);
			log.error("Could not delete Restaurant. deleteRestaurant() RestaurantDAO", e);
		}finally{
			session.close();
		}
		return restdto;
	}

	public Restaurants findRestaurantDataByName(String restName) throws Exception{
		log.debug("in findRestaurantDataByName() RestaurantDAO");
		Session session = null;
		Restaurants  rest = new Restaurants();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  

			if(RESTUtility.isDataNotNull(restName)){
				Query query = session.createQuery("from Restaurants where restaurant_name = '"+ restName.toLowerCase()+"'");
				rest = (Restaurants)query.uniqueResult();
				if(!RESTUtility.isDataNotNull(rest)){
					log.debug("No record for Restaurant. findRestaurantDataByName() RestaurantDAO");
				}
			}else{
				log.debug("Please enter Restaurant Name. findRestaurantDataByName() RestaurantDAO");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Could not fetch Restaurant. findRestaurantDataByName() RestaurantDAO", e);
		}finally{
			session.close();
		}
		return rest;
	}

	public Restaurants isRestaurantPresent(Restaurants rest,Session session) throws Exception{
		log.debug("in isRestaurantPresent() RestaurantDAO");
		Restaurants restaurant = new Restaurants();
		try {
			Query query = session.createQuery("from Restaurants where restaurantName = '"+ rest.getRestaurantName().toLowerCase()+"'");
			restaurant = (Restaurants)query.uniqueResult();
			if(RESTUtility.isDataNotNull(restaurant)){
				restaurant.setRestaurantName(rest.getRestaurantName());
				restaurant.setRestaurantAddress(rest.getRestaurantAddress());
				restaurant.setRestaurantStars(rest.getRestaurantStars());
				log.debug("Restaurant is present. isRestaurantPresent() RestaurantDAO");
			}else{
				log.debug("Restaurant is not present. isRestaurantPresent() RestaurantDAO");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(" Error updating Restaurant. isRestaurantPresent() RestaurantDAO", e);
		}
		return restaurant;
	}
	
	public RestaurantDTO saveRestaurantMenuItem(List<RestMenu> restmenu) throws Exception{
		log.debug("saveRestaurantMenuItem() RestaurantDAO");
		Session session = null;
		Transaction trans = null;
		RestaurantDTO restdto = new RestaurantDTO();
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			trans = session.beginTransaction();

			for (RestMenu restmenuitems : restmenu) {
				
				if(RESTUtility.isDataNotNull(restmenuitems.getRestaurants())){
					Restaurants restdata = isRestaurantPresent(restmenuitems.getRestaurants(),session);
					if(RESTUtility.isDataNotNull(restdata)){
						log.debug("Restaurant is already present. saveRestaurantMenuItem() RestaurantDAO");
					}else{
						Restaurants rest = new Restaurants();
						if(RESTUtility.isDataNotNull(restmenuitems.getRestaurants().getRestaurantName())){
							rest.setRestaurantName(restmenuitems.getRestaurants().getRestaurantName());
						}
						if(RESTUtility.isDataNotNull(restmenuitems.getRestaurants().getRestaurantAddress())){
							rest.setRestaurantAddress(restmenuitems.getRestaurants().getRestaurantAddress());
						}
						if(RESTUtility.isDataNotNull(restmenuitems.getRestaurants().getRestaurantStars())){
							rest.setRestaurantStars(restmenuitems.getRestaurants().getRestaurantStars());
						}
						session.save(rest);
						
						if(RESTUtility.isDataNotNull(restmenuitems.getMenu())){
							Menu menu = new Menu();
							if(RESTUtility.isDataNotNull(restmenuitems.getMenu().getMenuType())){
								menu.setMenuType(restmenuitems.getMenu().getMenuType());
							}
							session.save(menu);
							trans.commit();
							
							if(trans.wasCommitted()){
								trans = session.beginTransaction();
								if(RESTUtility.isDataNotNull(restmenuitems.getMenu().getItems())){
									Set<Item> itemset = restmenuitems.getMenu().getItems();
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
								
								if(RESTUtility.isDataNotNull(rest.getRestaurantId())){
									Restaurants  restFinal = new Restaurants();
									restFinal.setRestaurantId(rest.getRestaurantId());
									restmenuitems.setRestaurants(restFinal);
								}
								if(RESTUtility.isDataNotNull(menu.getMenuId())){
									Menu menuFinal = new Menu();
									menuFinal.setMenuId(menu.getMenuId());
									restmenuitems.setMenu(menuFinal);
								}
								session.save(restmenuitems);
							}
						}
					}

				}
			}
			trans.commit();
			restdto.setDtoSuccess(ProjectConstants.SUCCESS);
			log.debug("successfully saved Restaurant. saveRestaurant() RestaurantDAO");
		} catch (Exception e) {
			// TODO: handle exception
			restdto.setDtoSuccess(ProjectConstants.FAILURE);
			trans.rollback();
			log.error("Could not save Restaurant. saveRestaurant() RestaurantDAO", e);
		}finally{
			session.close();
		}
		return restdto;
	}

}
