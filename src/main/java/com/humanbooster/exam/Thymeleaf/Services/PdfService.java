package com.humanbooster.exam.Thymeleaf.Services;

import com.humanbooster.exam.Thymeleaf.Models.Facture;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PdfService {
    public String output = "src/main/resources/static/pdf/facture.pdf";

    @Autowired
    TemplateEngine templateEngine;

    public String parseThymeleafTemplate(Facture facture, String formattedTotalHT, String formattedTotalTTC,
                                         List<String> formattedLigneTotalHTList, List<String> formattedLigneTotalTTCList) {
        Context context = new Context();
        context.setVariable("facture", facture);
        context.setVariable("formattedTotalHT", formattedTotalHT);
        context.setVariable("formattedTotalTTC", formattedTotalTTC);
        context.setVariable("formattedLigneTotalHTList", formattedLigneTotalHTList);
        context.setVariable("formattedLigneTotalTTCList", formattedLigneTotalTTCList);
        return templateEngine.process("pdf/facture", context);
    }

    public void generatePdfFromHtml(Facture facture, String formattedTotalHT, String formattedTotalTTC,
                                    List<String> formattedLigneTotalHTList, List<String> formattedLigneTotalTTCList)
            throws IOException, DocumentException {
        String html = this.parseThymeleafTemplate(facture, formattedTotalHT, formattedTotalTTC,
                formattedLigneTotalHTList, formattedLigneTotalTTCList);
        OutputStream os = new FileOutputStream(output);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(os);
        os.close();
    }
}
