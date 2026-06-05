package output;

import usecase.UserActivityReportResponse;

public interface PdfGeneratorPort {
    byte[] generatePdf(UserActivityReportResponse userActivityReportResponse);
}
