package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;

import java.util.List;

public interface ITourLogService {
    //boolean checkValidTourLog();
    List<TourLogModel> getAllTourLogs();
    void addTourLog(TourLogModel tourLog);
    void deleteTourLog(TourLogModel tourLogModel);
}
