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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(WalletTest.TestChecker.class)

class WalletTest {
    private Wallet wallet;
    private Currency c = Currency.USD;
    private static ArrayList<ReportObject> reportObjects = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        wallet = new Wallet("Ref1", c);
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
            document.save("src/test/resources/test_report1.pdf");
            System.out.println("PDF generated successfully.");
            SlackIntegration.sendMessage("Test finished successfully !",document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getWalletRef() {
    }

    @Test
    void addAmount() {

    }

    @Test
    void getAmount() {
        String filePath = "src/test/resources/wallet.xlsx";
        try(
                FileInputStream fileInputStream = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fileInputStream)
        ){
            Sheet sheet = workbook.getSheetAt(0);
            ExchangeRate E = new ExchangeRate();
            wallet.addAmount(1, Currency.USD, E);
            for (int rowIndex = 1 ; rowIndex <= sheet.getLastRowNum() ; rowIndex++){
                Row row = sheet.getRow(rowIndex);

                String currency   = row.getCell(0).getStringCellValue();
                double amount = row.getCell(1).getNumericCellValue();

                assertEquals(amount, wallet.getAmount(Currency.valueOf(currency), E));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class TestChecker implements AfterTestExecutionCallback {
        @Override
        public void afterTestExecution(ExtensionContext context) {
            String statusCode;
            String errorMessage;
            WalletTest walletInstance = new WalletTest();
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