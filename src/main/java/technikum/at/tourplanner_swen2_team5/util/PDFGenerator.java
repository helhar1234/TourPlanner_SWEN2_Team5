package technikum.at.tourplanner_swen2_team5.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.UnitValue;
import javafx.collections.transformation.FilteredList;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.TourLogService;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class PDFGenerator {

    public void generateTourReport(TourModel tour) {
        try {
            writeTourReport(tour);
            Path pdfPath = getPdfPath(tour.getName());
            byte[] pdfContent = Files.readAllBytes(pdfPath);
            downloadAndShowPDF(pdfContent, tour);
            log.info("Successfully generated tour report");
        } catch (IOException e) {
            log.error("Failed to generate tour report of tour with id {}", tour.getId(), e);
        }
    }

    private Path getPdfPath(String tourName) {
        String currentWorkingDir = System.getProperty("user.dir");
        System.out.println("Aktuelles Arbeitsverzeichnis: " + currentWorkingDir);

        Path relativePath = Paths.get(currentWorkingDir, "src", "main", "resources", "technikum", "at", "tourplanner_swen2_team5", "pdf", "TourReports");

        String fileName = tourName + ".pdf";
        Path filePath = relativePath.resolve(fileName);
        Path absolutePath = filePath.toAbsolutePath().normalize();
        if(Files.exists(absolutePath)) {
            return filePath.toAbsolutePath().normalize();
        } else {
            log.error("Failed to resolve path for tour report");
            throw new RuntimeException("Failed to resolve path for tour report");
        }
    }

    private void writeTourReport(TourModel tour) throws IOException {
        String dest = getPdfPath(tour.getName()).toString();

        try {

            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.setMargins(20, 20, 20, 20);

            document.add(new Paragraph("Tour Report").setBold().setFontSize(20));
            document.add(new Paragraph("Name: " + tour.getName()));
            document.add(new Paragraph("Description: " + tour.getDescription()));
            document.add(new Paragraph("Start Location: " + tour.getStart()));
            document.add(new Paragraph("End Location: " + tour.getDestination()));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Tour Logs:").setBold().setFontSize(16));

            TourLogViewModel logViewModel = new TourLogViewModel();
            FilteredList<TourLogModel> tourLogs = new FilteredList<>(logViewModel.getTourLogs());
            tourLogs.setPredicate(tourLog -> tourLog.getTour().getId().equals(tour.getId()));
            for (TourLogModel log : tourLogs) {
                document.add(new Paragraph("Date: " + log.getDate()));
                document.add(new Paragraph("Comment: " + log.getComment()));
                document.add(new Paragraph("Distance: " + log.getDistance() + " km"));

                LineSeparator ls = new LineSeparator(new com.itextpdf.kernel.pdf.canvas.draw.SolidLine());
                ls.setWidth(UnitValue.createPercentValue(100));
                document.add(ls);

                document.add(new Paragraph(" ")); // Leerzeile
            }

            document.close();
        } catch (FileNotFoundException e) {
            log.error("Failed to write pdf to tour report", e);
        }
    }

    private void downloadAndShowPDF(byte[] pdfContent, TourModel tour) {
        // FileChooser erstellen, um den Speicherort für das heruntergeladene PDF auszuwählen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(tour.getName() + " Report.pdf");
        File file = fileChooser.showSaveDialog(null);

        // Wenn der Benutzer einen Speicherort ausgewählt hat
        if (file != null) {
            try {
                // PDF-Inhalt in die Datei schreiben
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(pdfContent);
                fos.close();

                // PDF anzeigen
                showPDF(file);
            } catch (IOException e) {
                log.error("Failed to save tour report for tour id {}", tour.getId(), e);
            }
        }
    }

    // PDF anzeigen
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
