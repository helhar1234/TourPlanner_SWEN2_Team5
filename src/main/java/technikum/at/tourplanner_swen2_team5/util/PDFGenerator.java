package technikum.at.tourplanner_swen2_team5.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.UnitValue;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.TourLogService;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PDFGenerator {

    private Path getPdfPath(String tourName) {
        String currentWorkingDir = System.getProperty("user.dir");
        System.out.println("Aktuelles Arbeitsverzeichnis: " + currentWorkingDir);

        Path relativePath = Paths.get(currentWorkingDir, "src", "main", "resources", "technikum", "at", "tourplanner_swen2_team5", "pdf", "TourReports");

        String fileName = tourName + ".pdf";
        Path filePath = relativePath.resolve(fileName);

        return filePath.toAbsolutePath().normalize();
    }

    public void generateTourReport(TourModel tour) throws FileNotFoundException {
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

            TourLogService logService = new TourLogService();
            List<TourLogModel> tourLogs = logService.getAllTourLogs();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
