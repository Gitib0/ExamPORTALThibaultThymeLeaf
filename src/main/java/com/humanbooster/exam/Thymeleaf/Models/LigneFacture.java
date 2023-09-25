package com.humanbooster.exam.Thymeleaf.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


@Entity

public class LigneFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Veuillez saisir une quantité")
    @Digits(integer = 4, fraction = 2, message = "Veuillez saisir un nombre (2 décimales autorisées)")
    private BigDecimal quantity;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    @NotNull(message = "Veuillez saisir un prix")
    @Digits(integer = 4, fraction = 2, message = "Veuillez saisir un nombre (2 décimales autorisées)")
    private BigDecimal prixHt;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Veuillez saisir une TVA")
    private Tva tva;


    @ManyToOne()
    private Facture facture;

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrixHt() {
        return prixHt;
    }

    public void setPrixHt(BigDecimal prixHt) {
        this.prixHt = prixHt;
    }

    public Tva getTva() {
        return tva;
    }

    public void setTva(Tva tva) {
        this.tva = tva;
    }
}
