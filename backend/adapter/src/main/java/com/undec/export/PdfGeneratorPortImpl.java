package com.undec.export;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import output.PdfGeneratorPort;
import usecase.UserActivityReportResponse;

import java.io.ByteArrayOutputStream;

@Component
public class PdfGeneratorPortImpl implements PdfGeneratorPort {

    @Override
    public byte[] generatePdf(UserActivityReportResponse data) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, out);
            document.open();


            document.add(new Paragraph("Reporte de Actividad del Usuario"));
            document.add(new Paragraph("Email: " + data.getEmailUser()));
            document.add(new Paragraph("Estado: " + data.getUserStatus()));
            document.add(new Paragraph(" ")); // Espacio


            PdfPTable table = new PdfPTable(4); //
            table.addCell("ID");
            table.addCell("Fecha");
            table.addCell("Monto");
            table.addCell("Estado");


            data.getOrders().forEach(order -> {
                table.addCell(order.getOrderId().toString());
                table.addCell(order.getCreatedOrderAt().toString());
                table.addCell("$" + order.getAmount().toString());
                table.addCell(order.getStatus().toString());
            });

            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("TOTAL APROBADO: $" + data.getTotalAmount()));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generator PDF", e);
        }

        return out.toByteArray(); // Devolvemos el byte[] solicitado por el C.U.
    }

}

