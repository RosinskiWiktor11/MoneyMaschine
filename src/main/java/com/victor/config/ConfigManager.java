package com.victor.config;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ RepositoryConfig.class, ServiceConfig.class, ControllerConfig.class,
		ViewControllerConfig.class })
public class ConfigManager {

	@Bean
	public Session session() {
		File file = new File("configs/hibernate.cfg.xml");
		org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
		configuration.configure(file);
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb
				.build());
		return sessionFactory.openSession();
	}

}
