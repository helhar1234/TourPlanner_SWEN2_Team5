package technikum.at.tourplanner_swen2_team5.util;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class PDFGenerator {

    private static final DeviceRgb BRIGHT_GREEN = new DeviceRgb(164, 214, 94);
    private static final DeviceRgb DARK_GREEN = new DeviceRgb(57, 92, 55);
    private static final DeviceRgb CREME_WHITE = new DeviceRgb(245, 245, 245);
    private static final float ROUNDED_CORNER = 5f;

    public void generateTourReport(TourModel tour) {
        try {
            File selectedFile = promptUserForSaveLocation(tour.getName());
            if (selectedFile != null) {
                writeTourReport(tour, selectedFile.toPath());
                byte[] pdfContent = Files.readAllBytes(selectedFile.toPath());
                downloadAndShowPDF(pdfContent, selectedFile);
                log.info("Successfully generated tour report for tour with id {}", tour.getId());
            }
        } catch (IOException e) {
            log.error("Failed to generate tour report of tour with id {}", tour.getId(), e);
        }
    }

    private File promptUserForSaveLocation(String defaultFileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(defaultFileName + " Report.pdf");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            return file;
        } else {
            log.warn("User did not select a save location for the PDF");
            return null;
        }
    }

    private void writeTourReport(TourModel tour, Path filePath) throws IOException {
        try {
            PdfWriter writer = new PdfWriter(filePath.toString());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            addHeader(document, tour);
            addMapImage(document, tour);
            addTourDataSection(document, tour);
            addSectionSeparator(document);
            addTourLogsSection(document, tour);

            document.close();
        } catch (FileNotFoundException e) {
            log.error("Failed to write pdf to tour report", e);
        }
    }

    // Header mit Logo und Titel
    private void addHeader(Document document, TourModel tour) throws IOException {
        // Tabelle mit zwei Spalten, ungleiche Aufteilung
        Table table = new Table(UnitValue.createPercentArray(new float[]{50, 5}))
                .useAllAvailableWidth();
        String imageName = "img/logos/BikerLogoMave.png";
        URL resource = MainTourPlaner.class.getResource(imageName);
        Image logo = new Image(ImageDataFactory.create(resource.toString()));
        logo.setWidth(40);
        logo.setHeight(40);

        // Titelzelle
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(new Date());
        Cell titleCell = new Cell().add(new Paragraph(currentDate + " - " + tour.getName() + " Report"))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setFontColor(DARK_GREEN)
                .setFontSize(14);
        table.addCell(titleCell);

        Cell logoCell = new Cell().add(logo)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);
        table.addCell(logoCell);

        document.add(table);
        document.add(new Paragraph("Tour Report").setBold().setFontSize(20).setFontColor(BRIGHT_GREEN));
    }


    private void addMapImage(Document document, TourModel tour) throws IOException {
        String filename = tour.getId() + "_map.png";
        File mapFile = new File(System.getProperty("user.home") + "/TourPlanner/maps", filename);

        if (!mapFile.exists()) {
            filename = MapRequester.fetchMapImage(tour);
            mapFile = new File(System.getProperty("user.home") + "/TourPlanner/maps", filename);
        }

        Image map = new Image(ImageDataFactory.create(mapFile.toURI().toString()))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setMaxHeight(200)
                .setAutoScale(true);
        document.add(map);
    }

    private void addTourDataSection(Document document, TourModel tour) {
        // Tour Name
        Paragraph tourName = new Paragraph(tour.getName())
                .setFontColor(DARK_GREEN)
                .setFontSize(14)
                .setBold();

        // From Start to Destination
        Paragraph routeDetails = new Paragraph()
                .add(new Text("From ").setFontColor(DARK_GREEN))
                .add(new Text(tour.getStart()).setFontColor(DARK_GREEN).setBold())
                .add(new Text(" to ").setFontColor(DARK_GREEN))
                .add(new Text(tour.getDestination()).setFontColor(DARK_GREEN).setBold())
                .setFontSize(12);

        Formatter formatter = new Formatter();
        String estimatedTime = formatter.formatTime(0, tour.getTime());
        Paragraph distanceAndTime = new Paragraph()
                .add(new Text(tour.getDistance() + " km | " + estimatedTime).setFontColor(DARK_GREEN))
                .setFontSize(12);

        Div tourDataContainer = new Div()
                .add(tourName)
                .add(routeDetails)
                .add(distanceAndTime)
                .setBackgroundColor(CREME_WHITE)
                .setFontColor(DARK_GREEN)
                .setBorderRadius(new BorderRadius(ROUNDED_CORNER));

        document.add(tourDataContainer);
    }

    private void addSectionSeparator(Document document) {
        document.add(new LineSeparator(new SolidLine()).setMarginTop(10).setMarginBottom(10));
    }

    private void addTourLogsSection(Document document, TourModel tour) throws MalformedURLException {
        document.add(new Paragraph("Tour Logs").setBold().setFontSize(16).setFontColor(BRIGHT_GREEN));

        TourLogViewModel logViewModel = new TourLogViewModel();
        List<TourLogModel> logs = logViewModel.getTourLogsForTour(tour.getId());
        for (TourLogModel log : logs) {
            Div logEntryDiv = new Div();
            logEntryDiv.setBackgroundColor(CREME_WHITE);
            logEntryDiv.setBorder(Border.NO_BORDER);
            logEntryDiv.setPadding(5);
            logEntryDiv.setBorderRadius(new BorderRadius(ROUNDED_CORNER));

            Table logTable = new Table(UnitValue.createPercentArray(new float[]{70, 5})).useAllAvailableWidth();
            logTable.setBorder(Border.NO_BORDER);  // Keine Umrandung der gesamten Tabelle

            Cell dateCell = new Cell().add(new Paragraph(log.getDate())
                            .setFontColor(DARK_GREEN)
                            .setFontSize(12)
                            .setBold())
                    .setBorder(Border.NO_BORDER);
            logTable.addCell(dateCell);

            if (log.getTransportType() != null) {
                URL resource = MainTourPlaner.class.getResource("img/icons/" + log.getTransportType().getName().toLowerCase() + "-icon.png");
                if (resource != null) {
                    Image transportIcon = new Image(ImageDataFactory.create(resource.toString()))
                            .setWidth(20)
                            .setHeight(20);
                    Cell iconCell = new Cell().add(transportIcon).setBorder(Border.NO_BORDER);
                    logTable.addCell(iconCell);
                } else {
                    logTable.addCell(new Cell().add(new Paragraph("No image available")).setBorder(Border.NO_BORDER));
                }
            } else {
                logTable.addCell(new Cell().add(new Paragraph("No transport type")).setBorder(Border.NO_BORDER));
            }

            Cell infoCell = new Cell(1, 2)  // Spanning both columns
                    .add(new Paragraph("Started at " + log.getTimeHours() + ":" + log.getTimeMinutes() + "m").setBold())
                    .add(new Paragraph(log.getTotalTime() + " | " + log.getDistance() + " km | " + log.getDifficulty().getDifficulty()))
                    .add(new Paragraph(log.getComment()))
                    .setBorder(Border.NO_BORDER);
            logTable.addCell(infoCell);

            Cell ratingCell = new Cell(1, 2)  // Spanning both columns
                    .add(new Paragraph(log.getRating() + "/10")
                            .setFontColor(log.getRating() >= 6 ? BRIGHT_GREEN : log.getRating() >= 3 ? new DeviceRgb(255, 165, 0) : new DeviceRgb(255, 0, 0))
                            .setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER);
            logTable.addCell(ratingCell);

            logEntryDiv.add(logTable);

            document.add(logEntryDiv);
        }
    }

    private void downloadAndShowPDF(byte[] pdfContent, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(pdfContent);
            fos.close();

            showPDF(file);
        } catch (IOException e) {
            log.error("Failed to show tour report for tour id {}", file.getName(), e);
        }
    }

    private void showPDF(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                log.warn("Desktop is not supported, unable to open the PDF automatically.");
            }
        } catch (IOException e) {
            log.error("Failed to open the PDF automatically", e);
        }
    }
}
