package de.hslu.infomac.notenverwaltung.helper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		Configuration configuration = configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
		return factory;
	}

	private static Configuration configure() {
		Configuration configuration = new Configuration().configure("/hibernate.cfg.xml")

				// Generiertes SQL auf Console ausgeben
				// .setProperty("use_sql_comments", "false")
				// .setProperty("hibernate.show_sql", "false")
				// .setProperty("hibernate.generate_statistics", "false")
				.setProperty("hibernate.current_session_context_class", "thread");

		return configuration;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
