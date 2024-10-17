package com.humanbooster.exam.Thymeleaf.Controllers;

import com.humanbooster.exam.Thymeleaf.Models.Facture;
import com.humanbooster.exam.Thymeleaf.Models.LigneFacture;
import com.humanbooster.exam.Thymeleaf.Repository.FactureRepository;
import com.humanbooster.exam.Thymeleaf.Services.PdfService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
public class FactureController {

    @Autowired
    PdfService pdfService;

    @Autowired
    FactureRepository factureRepository;

    @RequestMapping("")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("home");

        List<Facture> factures = factureRepository.findAll();

        mv.addObject("factures", factures);

        return  mv;
    }

    @RequestMapping("detail/{facture}")
    public ModelAndView detail(@PathVariable(required = false) Facture facture) {
        if (facture == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Facture inexistante");
        }

        ModelAndView mv = new ModelAndView("detail");
        mv.addObject("facture", facture);
        calculateAndFormatTotals(facture, mv);

        return mv;
    }

    @RequestMapping("pdf/{facture}")
    public void pdf(@PathVariable(required = false) Facture facture, HttpServletResponse httpServletResponse) throws DocumentException, IOException {

        if (facture == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Facture inexistante");
        }

        ModelAndView mv = new ModelAndView("pdf/facture");
        mv.addObject("facture", facture);
        calculateAndFormatTotals(facture, mv);

        this.pdfService.generatePdfFromHtml(facture);

        InputStream inputStream = new FileInputStream(
                new File("src/main/resources/static/pdf/facture.pdf")
        );

        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());

        String headerValue = "attachment; filename=" + facture.getLibelle() + ".pdf";
        httpServletResponse.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        httpServletResponse.setHeader(headerKey, headerValue);
        httpServletResponse.flushBuffer();
    }


    private void calculateAndFormatTotals(Facture facture, ModelAndView mv) {
        double totalHT = 0.0;
        double totalTTC = 0.0;

        DecimalFormat df = new DecimalFormat("#0.00");

        List<String> formattedLigneTotalHTList = new ArrayList<>();
        List<String> formattedLigneTotalTTCList = new ArrayList<>();

        for (LigneFacture ligne : facture.getLignes()) {
            double ligneTotalHT = ligne.getPrixHt().doubleValue() * ligne.getQuantity().doubleValue();
            double tvaAmount = ligne.getTva() != null ? (ligne.getTva().getTaux() / 100.0) * ligneTotalHT : 0.0;

            totalHT += ligneTotalHT;
            totalTTC += ligneTotalHT + tvaAmount;

            formattedLigneTotalHTList.add(df.format(ligneTotalHT));
            formattedLigneTotalTTCList.add(df.format(ligneTotalHT + tvaAmount));
        }

        mv.addObject("formattedLigneTotalHTList", formattedLigneTotalHTList);
        mv.addObject("formattedLigneTotalTTCList", formattedLigneTotalTTCList);

        mv.addObject("formattedTotalHT", df.format(totalHT));
        mv.addObject("formattedTotalTTC", df.format(totalTTC));
    }
}
