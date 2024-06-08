package technikum.at.tourplanner_swen2_team5.BL.validation;

import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.IOException;

public class TourMapValidationService {

    public static boolean isValidLocation(String location) {
        try {
            return MapRequester.geocode(location) != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean validateStartAndDestination(String start, String destination) {
        return isValidLocation(start) && isValidLocation(destination);
    }
}
