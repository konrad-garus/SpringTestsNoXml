package pl.squirrel.testnoxml;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
public class TestRepositoryConfig {
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.setName("Nuts").build();
	}

	@Bean
	public LocalSessionFactoryBean sessionFactoryBean() {
		LocalSessionFactoryBean result = new LocalSessionFactoryBean();
		result.setDataSource(dataSource());
		result.setPackagesToScan(new String[] { "pl.squirrel.testnoxml.entity" });

		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		result.setHibernateProperties(properties);
		return result;
	}

	@Bean
	public SessionFactory sessionFactory() {
		return sessionFactoryBean().getObject();
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager man = new HibernateTransactionManager();
		man.setSessionFactory(sessionFactory());
		return man;
	}

	@Bean
	public OrderRepository orderRepo() {
		return new OrderRepository();
	}
}
