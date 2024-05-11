package technikum.at.tourplanner_swen2_team5.models;

import lombok.Getter;

public class TourImage {

    private String tourId;

    private String filename;

    public TourImage(String tourId, String filename){
        this.tourId = tourId;
        this.filename = filename;
    }



}
