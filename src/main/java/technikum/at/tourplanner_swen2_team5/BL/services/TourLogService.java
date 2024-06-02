package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TourLogDAO;

import java.util.List;
import java.util.UUID;

public class TourLogService implements ITourLogService {
    private final TourLogDAO tourLogDAO;

    public TourLogService() {
        this.tourLogDAO = new TourLogDAO();
    }

    @Override
    public List<TourLogModel> getAllTourLogs() {
        return tourLogDAO.findAll();
    }

    @Override
    public void addTourLog(TourLogModel tourLog) {
        tourLog.setId(UUID.randomUUID().toString());
        tourLogDAO.save(tourLog);
    }

    @Override
    public void deleteTourLog(TourLogModel tourLog) {
        tourLogDAO.delete(tourLog);
    }

    public TourLogModel getTourLogById(String id) {
        return tourLogDAO.findById(id);
    }

    public void updateTourLog(TourLogModel tourLog) {
        tourLogDAO.update(tourLog);
    }
}
