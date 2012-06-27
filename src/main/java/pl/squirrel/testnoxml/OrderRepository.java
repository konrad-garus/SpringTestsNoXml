package pl.squirrel.testnoxml;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.squirrel.testnoxml.entity.Order;

@Transactional
@Repository
public class OrderRepository {
	@Autowired
	private SessionFactory sessionFactory;

	public void saveOrder(Order order) {
		sessionFactory.getCurrentSession().save(order);
	}

}
