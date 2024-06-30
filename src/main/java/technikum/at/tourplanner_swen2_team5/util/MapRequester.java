package technikum.at.tourplanner_swen2_team5.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.MainTourPlanner;

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

@Slf4j
@Component
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
            try (InputStream inputStream = MainTourPlanner.class.getResourceAsStream("/leaflet.html")) {
                if (inputStream != null) {
                    Files.copy(inputStream, LEAFLET_HTML_FILE.toPath());
                }
            } catch (IOException e) {
                log.error("Failed to retrieve leaflet.html file", e);
            }
        }
    }

    public String fetchMapImage(TourModel tour) throws IOException {
        String startCoords = geocode(tour.getStart());
        String destinationCoords = geocode(tour.getDestination());

        if (startCoords == null || destinationCoords == null) {
            log.warn("Failed to find start/destination coordinates");
            throw new IOException("Invalid start or destination coordinates");
        }

        try {
            // Build the API URL for the static map image request
            String url = String.format("https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/pin-s+000(%s),pin-s+f44(%s)/auto/600x300?access_token=%s",
                    startCoords, destinationCoords, ApplicationContext.getApiKeyMb());

            // Fetch the map image from the URL
            BufferedImage image = fetchImageFromUrl(url);

            // Save the image to the file system
            String filename = tour.getId() + "_map.png";
            File outputFile = new File(MAP_DIR, filename);
            ImageIO.write(image, "png", outputFile);

            log.info("Successfully loaded map image and saved it to the file system");
            return filename;
        } catch (IOException e) {
            log.error("Failed to fetch map image and save it to the file system", e);
            return null;
        }
    }

    private static BufferedImage fetchImageFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() == 200) {
            log.info("Successfully retrieved map image from {}", urlString);
            return ImageIO.read(connection.getInputStream());
        } else {
            log.error("Failed to fetch image from URL: {}", urlString);
            throw new IOException("Failed to fetch image from URL: " + urlString);
        }
    }

    public void openMapInBrowser(String start, String destination) {
        try {
            String startCoords = geocode(start);
            String destinationCoords = geocode(destination);

            if (startCoords == null || destinationCoords == null) {
                log.warn("Failed to find start/destination coordinates");
                return;
            }

            String routeData = fetchRouteData(startCoords, destinationCoords);
            writeDirectionsToFile(routeData);
            MainTourPlanner.openMapInBrowser();
            log.info("Successfully opened map in browser");
        } catch (Exception e) {
            log.error("Failed to open Map in Browser", e);
        }
    }

    private String fetchRouteData(String startCoords, String destinationCoords) throws IOException {
        String url = String.format("https://api.openrouteservice.org/v2/directions/driving-car?api_key=%s&start=%s&end=%s",
                ApplicationContext.getApiKeyOrs(), startCoords, destinationCoords);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if (connection.getResponseCode() == 200) {
            log.info("Successfully retrieved route data");
            return new String(connection.getInputStream().readAllBytes());
        } else {
            log.error("Failed to fetch route data from URL: {}", url);
            throw new IOException("Failed to fetch route data");
        }
    }

    private static void writeDirectionsToFile(String routeData) throws IOException {
        try (FileWriter fileWriter = new FileWriter(DIRECTIONS_FILE)) {
            fileWriter.write("var directions = " + routeData + ";");
            log.info("Successfully wrote directions to file");
        } catch (IOException e) {
            log.error("Failed to write directions to file", e);
        }
    }

    public String geocode(String location) throws IOException {
        String url = String.format(
                "https://api.openrouteservice.org/geocode/search?api_key=%s&text=%s",
                ApplicationContext.getApiKeyOrs(), URLEncoder.encode(location, StandardCharsets.UTF_8));

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
                double lon = coordinates.getDouble(0);
                double lat = coordinates.getDouble(1);

                // Überprüfen, ob die Koordinaten innerhalb der Grenzen Europas liegen
                if (isWithinEurope(lat, lon)) {
                    log.info("Successfully retrieved geocode from {} with coordinates {}", url, coordinates);
                    return lon + "," + lat;
                }
            }
        }
        log.error("Failed to fetch geocode from URL: {}", url);
        return null;
    }

    // Hilfsmethode, um zu überprüfen, ob die Koordinaten innerhalb Europas liegen
    private static boolean isWithinEurope(double lat, double lon) {
        // Europa's ungefähre Bounding Box
        double westLon = -31.266001; // Westlichste Länge
        double southLat = 27.636311; // Südlichste Breite
        double eastLon = 39.869301;  // Östlichste Länge
        double northLat = 81.008797; // Nördlichste Breite

        return lon >= westLon && lon <= eastLon && lat >= southLat && lat <= northLat;
    }


    public double getDistance(String start, String destination) throws IOException {
        String startCoords = geocode(start);
        String destinationCoords = geocode(destination);

        if (startCoords == null || destinationCoords == null) {
            throw new IOException("Invalid start or destination coordinates");
        }

        String url = String.format("https://api.openrouteservice.org/v2/directions/driving-car?api_key=%s&start=%s&end=%s",
                ApplicationContext.getApiKeyOrs(), startCoords, destinationCoords);

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
                log.info("Successfully retrieved distance from {} with coordinates {}", url, route);
                return distance / 1000; // Convert meters to kilometers
            }
        } else {
            log.error("Failed to fetch distance from URL: {}", url);
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
                log.error("Invalid transportation type: {}", transportationType);
                throw new IllegalArgumentException("Invalid transportation type: " + transportationType);
        }

        if (speed > 0) {
            log.info("Successfully fetched time for transportation {}", transportationType);
            return (int) ((distance / speed) * 60); // Convert hours to minutes
        }
        log.error("Failed to fetch time from URL: {}", transportationType);
        return 0;
    }
}
