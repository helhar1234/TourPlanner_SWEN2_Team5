package technikum.at.tourplanner_swen2_team5.models;

import javafx.beans.property.*;
import lombok.Getter;

public class TourLogModel {
    @Getter
    private String id;
    private final StringProperty date = new SimpleStringProperty();
    private final StringProperty comment = new SimpleStringProperty();
    private final StringProperty difficulty = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty totalTime = new SimpleStringProperty();
    private final IntegerProperty rating = new SimpleIntegerProperty();
    private final StringProperty transportType = new SimpleStringProperty();

    public TourLogModel() {
    }

    public TourLogModel(String id, String date, String comment, String difficulty, Double distance, String totalTime, Integer rating, String transportType) {
        this.date.set(date);
        this.comment.set(comment);
        this.difficulty.set(difficulty);
        this.distance.set(distance);
        this.totalTime.set(totalTime);
        this.rating.set(rating);
        this.transportType.set(transportType);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public StringProperty difficultyProperty() {
        return difficulty;
    }

    public DoubleProperty distanceProperty() {
        return distance;
    }

    public StringProperty totalTimeProperty() {
        return totalTime;
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    // Getter und Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public String getDifficulty() {
        return difficulty.get();
    }

    public void setDifficulty(String difficulty) {
        this.difficulty.set(difficulty);
    }

    public double getDistance() {
        return distance.get();
    }

    public void setDistance(double distance) {
        this.distance.set(distance);
    }

    public String getTotalTime() {
        return totalTime.get();
    }

    public void setTotalTime(String totalTime) {
        this.totalTime.set(totalTime);
    }

    public int getRating() {
        return rating.get();
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public String getTransportType() {
        return transportType.get();
    }

    public void setTransportType(String transportType) {
        this.transportType.set(transportType);
    }
}
