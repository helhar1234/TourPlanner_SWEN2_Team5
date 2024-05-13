package technikum.at.tourplanner_swen2_team5.ui_tests;

import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class NavbarTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainTourPlaner().start(stage);
    }

    @BeforeEach
    void setUp() throws Exception {
        FxToolkit.setupStage(Stage::show);
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.cleanupStages();
    }

    @Test
    void testVisibilityOfNavbarButtons() {
        verifyThat("#logo", isVisible());
        verifyThat("#mapButton", isVisible());
        verifyThat("#plusButton", isVisible());
        verifyThat("#searchBar", isVisible());
        verifyThat("#searchButton", isVisible());
        verifyThat("#settingsButton", isVisible());
    }
}
