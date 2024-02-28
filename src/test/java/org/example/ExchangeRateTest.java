package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ExchangeRateTest.TestChecker.class)

class ExchangeRateTest {

    private ExchangeRate exchangeRate;
    private static ArrayList<ReportObject> reportObjects = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        exchangeRate = new ExchangeRate();
    }

    @AfterAll
    public static void AfterAllTest() {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                float lineHeight = 18;
                contentStream.showText("line");
                for (ReportObject reportObject : reportObjects) {
                    String line = "Status: " + reportObject.getStatus() +
                            " | Method Name: " + reportObject.getMethodName() +
                            " | Error Message: " + reportObject.getErrorMessage();
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -lineHeight);
                }

                contentStream.endText();
            }
            document.save("src/test/resources/test_report2.pdf");
            System.out.println("PDF generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getExchangeRate() {
        String filePath = "src/test/resources/curency.xlsx";
        try(
                FileInputStream fileInputStream = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fileInputStream)
        ){
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1 ; rowIndex <= sheet.getLastRowNum() ; rowIndex++){
                Row row = sheet.getRow(rowIndex);

                String baseCurrency   = row.getCell(0).getStringCellValue();
                String targetCurrency = row.getCell(1).getStringCellValue();
                double exchangevalue = row.getCell(2).getNumericCellValue();

                assertEquals(exchangevalue, exchangeRate.getExchangeRate(baseCurrency, targetCurrency));

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    void initExchangeRates() {
    }

    public static class TestChecker implements AfterTestExecutionCallback {
        @Override
        public void afterTestExecution(ExtensionContext context) {
            String statusCode;
            String errorMessage;
            ExchangeRateTest exchangeRateInstance = new ExchangeRateTest();
            if (context.getExecutionException().isPresent()) {
                // Test failed
                statusCode = "K.O";
                errorMessage = context.getExecutionException().get().getMessage(); // Get the error message
            } else {
                // Test passed
                statusCode = "OK";
                errorMessage = null;
            }
            String methodName = context.getRequiredTestMethod().getName();

            ReportObject reportObject = new ReportObject(statusCode, errorMessage, methodName);
            reportObjects.add(reportObject);
        }
    }

}
