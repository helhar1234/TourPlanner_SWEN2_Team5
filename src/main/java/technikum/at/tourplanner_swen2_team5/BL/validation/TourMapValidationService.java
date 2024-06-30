package technikum.at.tourplanner_swen2_team5.BL.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.IOException;

@Slf4j
@Service
public class TourMapValidationService {
    private MapRequester mapRequester;

    @Autowired
    public TourMapValidationService(MapRequester mapRequester) {
        this.mapRequester = mapRequester;
    }

    public boolean isValidLocation(String location) {
        try {
            return mapRequester.geocode(location) != null;
        } catch (IOException e) {
            log.error("Location {} could not be validated", location, e);
            return false;
        }
    }
}
