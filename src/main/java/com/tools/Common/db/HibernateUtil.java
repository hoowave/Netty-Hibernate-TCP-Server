package com.tools.Common.db;

import com.tools.Common.db.entity.Account;
import com.tools.Common.db.entity.Game;
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
            configuration.getProperties().put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
            configuration.getProperties().put("hibernate.hbm2ddl.auto", "update");
            configuration.getProperties().put("hibernate.show_sql", "true");
            configuration.getProperties().put("hibernate.format_sql", "true");
            configuration.getProperties().put("hibernate.use_sql_comments", "true");
            configuration.getProperties().put("hibernate.connection.datasource", dataSource);
            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(Game.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("sessionFactory is " + sessionFactory);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}