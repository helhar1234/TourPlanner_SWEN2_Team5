package technikum.at.tourplanner_swen2_team5.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

            document.add(new Paragraph("Neu"));

            document.close();
            System.out.println("PDF-Dokument erstellt: " + dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
