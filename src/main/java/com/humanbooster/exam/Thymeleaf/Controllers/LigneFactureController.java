package com.humanbooster.exam.Thymeleaf.Controllers;

import com.humanbooster.exam.Thymeleaf.Models.Facture;
import com.humanbooster.exam.Thymeleaf.Models.LigneFacture;
import com.humanbooster.exam.Thymeleaf.Repository.EntrepriseRepository;
import com.humanbooster.exam.Thymeleaf.Repository.LigneFactureRepository;
import com.humanbooster.exam.Thymeleaf.Repository.TvaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LigneFactureController {

    @Autowired
    LigneFactureRepository ligneFactureRepository;

    @Autowired
    EntrepriseRepository entrepriseRepository;

    @Autowired
    TvaRepository tvasRepository;

    @RequestMapping("ligne/{facture}")
    public ModelAndView ligneFacture(@PathVariable(required = false) Facture facture){
        if(facture == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Facture inexistante");
        }

        ModelAndView mv = new ModelAndView("ligne-factures/list.html");
        mv.addObject("facture", facture);


        return mv;
    }

    @RequestMapping(value = "ligne/{facture}/ajout", method = RequestMethod.GET)
    public ModelAndView form(@PathVariable(required = false) Facture facture){
        LigneFacture lf = new LigneFacture();
        lf.setFacture(facture);

        ModelAndView mv = new ModelAndView("ligne-factures/add");
        mv.addObject("ligneFacture", lf);
        mv.addObject("tvas", tvasRepository.findAll());

        return mv;

    }

    @RequestMapping(value = "ligne/{facture}/ajout", method = RequestMethod.POST)
    public String form(@PathVariable(required = false) Facture facture,
                             @Validated LigneFacture ligneFacture,
                             BindingResult bindingResult,
                             Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("tvas", tvasRepository.findAll());
            model.addAttribute("ligneFacture", ligneFacture);
            return "ligne-factures/add";
        } else {
            ligneFacture.setFacture(facture);
            this.ligneFactureRepository.save(ligneFacture);
            return "redirect:/ligne/"+facture.getId();
        }
    }
}
