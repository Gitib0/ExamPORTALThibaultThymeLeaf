package com.humanbooster.exam.Thymeleaf;

import com.humanbooster.exam.Thymeleaf.Models.*;
import com.humanbooster.exam.Thymeleaf.Repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.Locale;

@SpringBootApplication
public class DataCreatorApplication {
    private Logger logger = LoggerFactory.getLogger(DataCreatorApplication.class);


    public static void main(String[] args){
        SpringApplication.run(DataCreatorApplication.class);
    }

    @Bean
    public CommandLineRunner dataLoader(
            TvaRepository tvaRepository,
            FactureRepository factureRepository,
            EntrepriseRepository entrepriseRepository,

            LigneFactureRepository ligneFactureRepositor){
        return args -> {
            Facture facture1 = new Facture();
            Facture facture2 = new Facture();

            Tva tva1 = new Tva();
            Tva tva2 = new Tva();
            Tva tva3 = new Tva();

            Entreprise adelorme = new Entreprise();

            Entreprise hbooster = new Entreprise();

            if(entrepriseRepository.findByRaisonSociale("Aurélien Delorme") == null){

                adelorme.setCodePostal("63130");
                adelorme.setRue("Rue Thermale");
                adelorme.setNumeroRue("10");
                adelorme.setSiret("80009876600010");
                adelorme.setVille("Royat");
                adelorme.setRaisonSociale("Aurélien Delorme");
                entrepriseRepository.save(adelorme);
            }

            if(entrepriseRepository.findByRaisonSociale("Human Booster") == null){

                hbooster.setCodePostal("63000");
                hbooster.setRue("Boulevard Gustave Flaubert");
                hbooster.setNumeroRue("222");
                hbooster.setSiret("80009876600010");
                hbooster.setVille("Clermont-Ferrand");
                hbooster.setRaisonSociale("Human Booster");
                entrepriseRepository.save(hbooster);
            }

            if(tvaRepository.findByLibelle("5%") == null){

                tva1.setTaux(0.05);
                tva1.setLibelle("5%");
                tvaRepository.save(tva1);
            }
            if(tvaRepository.findByLibelle("10%") == null){
                tva2.setTaux(0.1);
                tva2.setLibelle("10%");
                tvaRepository.save(tva2);
            }
            if(tvaRepository.findByLibelle("20%") == null){
                tva3.setTaux(0.2);
                tva3.setLibelle("20%");
                tvaRepository.save(tva3);
            }

            if(factureRepository.findByLibelle("hb_mars_2022") == null){
                facture1.setDateFacture(new GregorianCalendar(2022,4,30));
                facture1.setLibelle("hb_mars_2022");
                facture1.setEmeteur(adelorme);
                facture1.setClient(hbooster);
                facture1.setDatePayment(new GregorianCalendar(2022,6,10));
                factureRepository.save(facture1);

                LigneFacture lf11 = new LigneFacture();
                lf11.setDesignation("Formation Symfony");
                lf11.setTva(tva1);
                lf11.setQuantity(BigDecimal.valueOf(3));
                lf11.setPrixHt(BigDecimal.valueOf(350));
                lf11.setFacture(facture1);
                ligneFactureRepositor.save(lf11);

                LigneFacture lf12 = new LigneFacture();
                lf12.setTva(tva2);
                lf12.setQuantity(BigDecimal.valueOf(2));
                lf12.setDesignation("Frais de déplacement (transport)");
                lf12.setPrixHt(BigDecimal.valueOf(113.00));
                lf12.setFacture(facture1);
                ligneFactureRepositor.save(lf12);

                LigneFacture lf13 = new LigneFacture();
                lf13.setTva(tva2);
                lf13.setQuantity(BigDecimal.valueOf(2));
                lf13.setDesignation("Frais de déplacement (nuité)");
                lf13.setPrixHt(BigDecimal.valueOf(80.00));
                lf13.setFacture(facture1);

                ligneFactureRepositor.save(lf13);

            }

            if(factureRepository.findByLibelle("hb_mars_2023") == null){
                facture2.setDateFacture(new GregorianCalendar(2022,4,30));
                facture2.setEmeteur(adelorme);
                facture2.setClient(hbooster);
                facture2.setLibelle("hb_aout_2023");
                facture2.setDateFacture(new GregorianCalendar(2023,10,10));
                factureRepository.save(facture2);

                LigneFacture lf21 = new LigneFacture();
                lf21.setTva(tva3);
                lf21.setQuantity(BigDecimal.valueOf(8));
                lf21.setDesignation("Frais de déplacement (nuité)");
                lf21.setPrixHt(BigDecimal.valueOf(400.50));
                lf21.setFacture(facture2);

            }




        };
    }
}
