package com.tools.Common.db;

import com.tools.Common.db.entity.Account;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.sql.DataSource;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mariadb://localhost:3306/d_toolbox");
            config.setDriverClassName("org.mariadb.jdbc.Driver");
            config.setUsername("hoowave");
            config.setPassword("");
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setAutoCommit(false);

            DataSource dataSource = new HikariDataSource(config);

            Configuration configuration = new Configuration();
            configuration
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
                    .setProperty("hibernate.show_sql", "true")
                    .addAnnotatedClass(Account.class)
                    .addAnnotatedClass(Character.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.datasource", dataSource)
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}