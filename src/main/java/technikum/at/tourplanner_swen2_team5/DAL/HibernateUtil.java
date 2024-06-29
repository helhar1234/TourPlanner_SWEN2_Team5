package technikum.at.tourplanner_swen2_team5.DAL;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import technikum.at.tourplanner_swen2_team5.BL.models.*;
import technikum.at.tourplanner_swen2_team5.util.ConfigHandler;

import java.util.Arrays;

@Slf4j
public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            ConfigHandler configHandler = new ConfigHandler();
            if (!configHandler.checkConfig("/database.properties", Arrays.asList("DB_URL", "DB_USERNAME", "DB_PASSWORD"))) {
                throw new Exception("Database configuration is missing required entries.");
            }

            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml"); // Load defaults from hibernate.cfg.xml

            // Set properties dynamically
            configuration.setProperty("hibernate.connection.url", configHandler.getConfigValue("DB_URL"));
            configuration.setProperty("hibernate.connection.username", configHandler.getConfigValue("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", configHandler.getConfigValue("DB_PASSWORD"));

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

            // Adding annotated classes
            configuration.addAnnotatedClass(TourModel.class);
            configuration.addAnnotatedClass(TourLogModel.class);
            configuration.addAnnotatedClass(TourMapModel.class);
            configuration.addAnnotatedClass(DifficultyModel.class);
            configuration.addAnnotatedClass(TransportTypeModel.class);

            sessionFactory = configuration.buildSessionFactory(builder.build());
            log.info("Hibernate SessionFactory created successfully");
        } catch (Exception e) {
            log.error("Initial SessionFactory creation failed", e);
            throw new RuntimeException("Failed to create session factory", e);
        }
    }
}
