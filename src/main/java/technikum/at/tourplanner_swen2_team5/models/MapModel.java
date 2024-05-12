package technikum.at.tourplanner_swen2_team5.models;

public class MapModel {

    private final String tourId;

    private final String filename;

    public MapModel(String tourId, String filename) {
        this.tourId = tourId;
        this.filename = filename;
    }


    public String getTourId() {
        return tourId;
    }

    public String getFilename() {
        return filename;
    }
}
