# Wallet Management and Automated Testing System

## Introduction

This project is a Maven-based application for currency management, featuring automated testing and reporting. It utilizes JUnit for unit testing, Apache PDFBox for generating PDF reports, and Slack integration for notifications.

## Features

- Currency management with support for EUR, USD, and TND.
- Automated unit testing using JUnit.
- PDF report generation summarizing test results.
- Slack notifications upon test completion.

## Dependencies

- JUnit 5.9.0 for unit testing.
- Apache PDFBox 2.0.27 for PDF generation.
- Apache POI 5.2.0 for Excel file handling.
- jSlack 3.4.2 for Slack integration.

## Usage

1. Clone the repository.
2. Navigate to the project directory.
3. Run tests using Maven: `mvn test`
4. Check the `target` directory for the generated PDF report.
5. A Slack message will be sent upon test completion.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.
