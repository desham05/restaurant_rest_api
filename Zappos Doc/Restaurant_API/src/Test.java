import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import main.java.model.RestMenu;
import main.java.utilities.HibernateUtils;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Session session = null;
		List<RestMenu> restmenuList = new ArrayList<RestMenu>(); 
		try {
			session = HibernateUtils.getSessionFactory().openSession();  
			Query query = session.createQuery("r.restaurantName,r.restaurantAddress,r.restaurantStars,"
					+ "m.menuType,i.itemName,i.itemDescription,i.itemPrice "
					+ "FROM RestMenu rm "
					+ "join restaurants r on r.restaurantId = rm.restaurantId "
					+ "join Menu m on m.menuId = rm.menuId "
					+ "join Item i on i.menuId = m.menuId");
			restmenuList = query.list();
			System.out.println(restmenuList);

		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			session.close();
		}

	}

}
