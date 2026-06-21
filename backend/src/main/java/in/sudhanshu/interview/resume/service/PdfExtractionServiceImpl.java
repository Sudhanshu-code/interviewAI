package in.sudhanshu.interview.resume.service;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

@Service
public class PdfExtractionServiceImpl implements PdfExtractionService {

    @Override
    public String extractText(String filePath) {

        try {
            PDDocument document = Loader.loadPDF(Path.of(filePath).toFile());

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to extract PDF text");
        }
    }

}
