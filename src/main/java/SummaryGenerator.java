/*
File: SummaryGenerator.java
- Dedicated to processing logic for summarization.
- Currently returns "Summary of <input text>".
- Future development:
    • Connect to an AI API endpoint to generate summaries.
    • Maintain modularity—keep input/output handling only in AISummarizerUI.

TO-DO:
- Inside SummaryGenerator.java, replace the placeholder return with
  your summarization logic or API integration.
- Ensure it always returns a string back to AISummarizerUI for display.
*/
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class SummaryGenerator {
    /*
     * Generates a summary string based on the user's input.
     *
     * @param input the raw text entered by the user in the input box.
     * @return a formatted summary string in the form "Summary of <input text>".
     *         If the input is null or blank, returns "[No input provided]".
     *
     * Purpose:
     * This is a placeholder function intended to simulate text summarization.
     * In future development, replace the return logic with actual summary
     * generation—either via NLP algorithms or external AI model integration.
    */
    public static String parseDataFirst(File inputFile) throws IOException {
        String result;

        if (!inputFile.exists()) {
            throw new IOException("File not found!");
        }

        Path path = Path.of(inputFile.getAbsolutePath());
        if (Files.probeContentType(path).contains("pdf")) { //If the file type is a .pdf
            try (PDDocument document = PDDocument.load(inputFile)) {
                PDFTextStripper stripper = new PDFTextStripper();
                result = stripper.getText(document);
            }

            return generateSummary(result);
        }
        else if (Files.probeContentType(path).contains("txt")) {    //If the file type is a .txt
            Scanner fileScanner = new Scanner(inputFile);
            StringBuilder inputText = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                String currWord = fileScanner.next();
                inputText.append(currWord);
            }
            fileScanner.close();
            return generateSummary(inputText.toString());
        }
        else {
            return "Error, invalid file type!";
        }
    }

    //TODO: Integrate OpenAI API (ChatGPT) here to summarize the given document
    public static String generateSummary(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "[No input provided]";
        }
        return "Summary of " + input;
    }
}