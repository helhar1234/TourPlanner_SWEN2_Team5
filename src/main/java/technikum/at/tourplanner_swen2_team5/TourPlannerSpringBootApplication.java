package technikum.at.tourplanner_swen2_team5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TourPlannerSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TourPlannerSpringBootApplication.class, args);
    }
}
