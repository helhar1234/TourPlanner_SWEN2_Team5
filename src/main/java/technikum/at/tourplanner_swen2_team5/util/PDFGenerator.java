package technikum.at.tourplanner_swen2_team5.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.UnitValue;
import javafx.collections.transformation.FilteredList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class PDFGenerator {

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

                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (FileNotFoundException e) {
            log.error("Failed to write pdf to tour report", e);
        }
    }

    private void downloadAndShowPDF(byte[] pdfContent, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(pdfContent);
            fos.close();

            showPDF(file);
        } catch (IOException e) {
            log.error("Failed to save tour report for tour id {}", file.getName(), e);
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
