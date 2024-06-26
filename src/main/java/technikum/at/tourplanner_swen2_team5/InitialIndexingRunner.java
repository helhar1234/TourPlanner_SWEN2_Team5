package technikum.at.tourplanner_swen2_team5;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Slf4j
@Component
public class InitialIndexingRunner implements CommandLineRunner {

    private final EntityManager entityManager;

    @Autowired
    public InitialIndexingRunner(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        // Ensure the directory exists
        String userHome = System.getProperty("user.home");
        File indexDir = new File(userHome, "TourPlanner/SearchIndexes");
        if (!indexDir.exists()) {
            if (indexDir.mkdirs()) {
                log.info("Created directory: {}", indexDir.getAbsolutePath());
            } else {
                log.error("Failed to create directory: {}", indexDir.getAbsolutePath());
            }
        }

        SearchSession searchSession = Search.session(entityManager);
        searchSession.massIndexer().startAndWait();
        log.info("Indexing for search complete.");
    }
}
