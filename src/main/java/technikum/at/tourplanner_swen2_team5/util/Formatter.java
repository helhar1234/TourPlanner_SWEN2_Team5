package technikum.at.tourplanner_swen2_team5.util;

public class Formatter {
    public String formatTime(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        return String.format("%dh %02dmin", hours, remainingMinutes);
    }

    public static String formatDistance(double distance) {
        if (distance < 1.0) {
            // Umrechnung von Kilometern in Meter, wenn die Distanz kleiner als ein Kilometer ist
            int meters = (int) (distance * 1000);
            return String.format("%d m", meters);
        } else {
            // Runde auf zwei Dezimalstellen, wenn die Distanz mindestens ein Kilometer ist
            return String.format("%.2f km", distance);
        }
    }
}
