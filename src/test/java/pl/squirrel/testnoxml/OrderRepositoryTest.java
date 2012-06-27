package pl.squirrel.testnoxml;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import pl.squirrel.testnoxml.entity.Order;
import pl.squirrel.testnoxml.entity.OrderLine;
import pl.squirrel.testnoxml.entity.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(classes = { TestRepositoryConfig.class })
@Transactional
public class OrderRepositoryTest {
	@Autowired
	private OrderRepository repo;

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	public void testPersistOrderWithItems() {
		Session s = sessionFactory.getCurrentSession();

		Product chestnut = new Product("Chestnut", "2.50");
		s.save(chestnut);
		Product hazelnut = new Product("Hazelnut", "5.59");
		s.save(hazelnut);

		Order order = new Order();
		order.addLine(chestnut, 20);
		order.addLine(hazelnut, 150);

		repo.saveOrder(order);
		s.flush();
		
		Order persistent = (Order) s.createCriteria(Order.class).uniqueResult();
		Assert.assertNotSame(0, persistent.getId());
		Assert.assertEquals(new OrderLine(chestnut, 20), persistent
				.getOrderLines().get(0));
		Assert.assertEquals(new OrderLine(hazelnut, 150), persistent
				.getOrderLines().get(1));
	}
}
