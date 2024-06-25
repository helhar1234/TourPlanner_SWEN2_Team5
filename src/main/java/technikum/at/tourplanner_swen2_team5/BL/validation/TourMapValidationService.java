package technikum.at.tourplanner_swen2_team5.BL.validation;

import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.IOException;

@Slf4j
public class TourMapValidationService {

    public static boolean isValidLocation(String location) {
        try {
            return MapRequester.geocode(location) != null;
        } catch (IOException e) {
            log.error("Location {} could not be validated", location, e);
            return false;
        }
    }


}
