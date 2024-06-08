package technikum.at.tourplanner_swen2_team5.util;

import org.json.JSONArray;
import org.json.JSONObject;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MapRequester {

    private static final String USER_DIR = System.getProperty("user.home") + "/TourPlanner";
    private static final File MAP_DIR = new File(USER_DIR, "maps");
    private static final File DIRECTIONS_FILE = new File(USER_DIR, "directions.js");
    private static final File LEAFLET_HTML_FILE = new File(USER_DIR, "leaflet.html");

    static {
        if (!MAP_DIR.exists()) {
            MAP_DIR.mkdirs();
        }
        if (!LEAFLET_HTML_FILE.exists()) {
            try (InputStream inputStream = MainTourPlaner.class.getResourceAsStream("/leaflet.html")) {
                if (inputStream != null) {
                    Files.copy(inputStream, LEAFLET_HTML_FILE.toPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String fetchMapImage(TourModel tour) throws IOException {
        String startCoords = geocode(tour.getStart());
        String destinationCoords = geocode(tour.getDestination());

        if (startCoords == null || destinationCoords == null) {
            throw new IOException("Invalid start or destination coordinates");
        }

        // Build the API URL for the static map image request
        String url = String.format("https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/pin-s+000(%s),pin-s+f44(%s)/auto/600x300?access_token=%s",
                startCoords, destinationCoords, ApplicationContext.API_KEY_MB);

        // Fetch the map image from the URL
        BufferedImage image = fetchImageFromUrl(url);

        // Save the image to the file system
        String filename = tour.getId() + "_map.png";
        File outputFile = new File(MAP_DIR, filename);
        ImageIO.write(image, "png", outputFile);

        return filename;
    }

    private static BufferedImage fetchImageFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() == 200) {
            return ImageIO.read(connection.getInputStream());
        } else {
            throw new IOException("Failed to fetch image from URL: " + urlString);
        }
    }

    public static void openMapInBrowser(String start, String destination) {
        try {
            String startCoords = geocode(start);
            String destinationCoords = geocode(destination);

            if (startCoords == null || destinationCoords == null) {
                System.out.println("Invalid start or destination coordinates");
                return;
            }

            String routeData = fetchRouteData(startCoords, destinationCoords);
            writeDirectionsToFile(routeData);
            MainTourPlaner.openMapInBrowser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fetchRouteData(String startCoords, String destinationCoords) throws IOException {
        String url = String.format("https://api.openrouteservice.org/v2/directions/driving-car?api_key=%s&start=%s&end=%s",
                ApplicationContext.API_KEY_ORS, startCoords, destinationCoords);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() == 200) {
            return new String(connection.getInputStream().readAllBytes());
        } else {
            throw new IOException("Failed to fetch route data");
        }
    }

    private static void writeDirectionsToFile(String routeData) throws IOException {
        try (FileWriter fileWriter = new FileWriter(DIRECTIONS_FILE)) {
            fileWriter.write("var directions = " + routeData + ";");
        }
    }

    public static String geocode(String location) throws IOException {
        String url = String.format("https://api.openrouteservice.org/geocode/search?api_key=%s&text=%s",
                ApplicationContext.API_KEY_ORS, URLEncoder.encode(location, StandardCharsets.UTF_8));

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() == 200) {
            String response = new String(connection.getInputStream().readAllBytes());
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray features = jsonResponse.getJSONArray("features");

            if (features.length() > 0) {
                JSONObject geometry = features.getJSONObject(0).getJSONObject("geometry");
                JSONArray coordinates = geometry.getJSONArray("coordinates");
                return coordinates.getDouble(0) + "," + coordinates.getDouble(1);
            }
        }

        return null;
    }

    public static double getDistance(String start, String destination) throws IOException {
        String startCoords = geocode(start);
        String destinationCoords = geocode(destination);

        if (startCoords == null || destinationCoords == null) {
            throw new IOException("Invalid start or destination coordinates");
        }

        String url = String.format("https://api.openrouteservice.org/v2/directions/driving-car?api_key=%s&start=%s&end=%s",
                ApplicationContext.API_KEY_ORS, startCoords, destinationCoords);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() == 200) {
            String response = new String(connection.getInputStream().readAllBytes());
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray routes = jsonResponse.getJSONArray("features");
            if (routes.length() > 0) {
                JSONObject route = routes.getJSONObject(0).getJSONObject("properties");
                JSONObject summary = route.getJSONObject("summary");

                double distance = summary.getDouble("distance");
                return distance / 1000; // Convert meters to kilometers
            }
        } else {
            throw new IOException("Failed to fetch route data");
        }
        return 0;
    }

    public static int getTimeByTransportation(String transportationType, double distance) {
        double speed = 0;

        switch (transportationType) {
            case "Bike":
                speed = 15; // km/h
                break;
            case "Hike":
                speed = 4; // km/h
                break;
            case "Running":
                speed = 10; // km/h
                break;
            case "Vacation":
                speed = 60; // km/h
                break;
            default:
                throw new IllegalArgumentException("Invalid transportation type: " + transportationType);
        }

        if (speed > 0) {
            return (int) ((distance / speed) * 60); // Convert hours to minutes
        }
        return 0;
    }
}