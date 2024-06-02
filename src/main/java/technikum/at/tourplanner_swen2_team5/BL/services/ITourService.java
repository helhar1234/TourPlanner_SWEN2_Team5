package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;

import java.util.List;

public interface ITourService {
    //boolean checkValidTour();
    List<TourModel> getAllTours();
    void addTour(TourModel tour);
    void deleteTour(TourModel tourModel);
}
