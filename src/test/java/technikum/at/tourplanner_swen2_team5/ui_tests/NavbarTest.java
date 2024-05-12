package technikum.at.tourplanner_swen2_team5.ui_tests;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class NavbarTest extends Application {

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
        /*Test
        void testLoginButtonEnabled() {
            verifyThat("#logInButton", isEnabled());
        }

        @Test
        void testLoginWithValidCredentials() {
            clickOn("#username").write("wermann");
            clickOn("#pwd").write("123");
            clickOn("#logInButton");

            verifyThat("#logOutButton", isVisible());
            verifyThat("#closeButton", isVisible());
        }

        @Test
        void testLogoutButtonLogsOutUser() {
            testLoginWithValidCredentials();

            clickOn("#logOutButton");
            verifyThat("#logInButton", isVisible());
        }

        @Test
        void testLoginWithInvalidCredentials() {
            clickOn("#username").write("invalid");
            clickOn("#pwd").write("invalid");
            clickOn("#logInButton");

            verifyThat("#warningLabel", hasText("Wrong username or password"));
        }*/
}
