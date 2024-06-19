package technikum.at.tourplanner_swen2_team5.DAL;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import technikum.at.tourplanner_swen2_team5.BL.models.*;

@Slf4j
public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try{
            //Konfig laden und sicherstellen, dass die Datei gefunden wird
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            //Entitäten explizit hinzufügen
            configuration.addAnnotatedClass(TourModel.class);
            configuration.addAnnotatedClass(TourLogModel.class);
            configuration.addAnnotatedClass(TourMapModel.class);
            configuration.addAnnotatedClass(DifficultyModel.class);
            configuration.addAnnotatedClass(TransportTypeModel.class);

            sessionFactory = configuration.buildSessionFactory(builder.build());
            log.info("Hibernate SessionFactory created successfully");
        } catch (Exception e) {
            log.error("Initial SessionFactory creation failed", e);
            throw new RuntimeException(e);
        }
    }
}
