package com.humanbooster.exam.Thymeleaf.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private GregorianCalendar dateFacture;


    @Temporal(TemporalType.DATE)
    private GregorianCalendar datePayment;

    @Column(unique = true)
    private String libelle;

    @ManyToOne(fetch = FetchType.EAGER)
    private Entreprise client;

    @ManyToOne(fetch = FetchType.EAGER)
    private Entreprise emeteur;

    @OneToMany(mappedBy = "facture", fetch = FetchType.EAGER)
    private List<LigneFacture> lignes;

    public Facture() {
        this.lignes = new ArrayList<LigneFacture>();
    }

    public List<LigneFacture> getLignes(){
        return  this.lignes;
    }

    public void addLigne(LigneFacture ligneFacture){
        this.lignes.add(ligneFacture);
    }

    public void removeLigne(LigneFacture ligneFacture){
        this.lignes.remove(ligneFacture);
    }

    public Entreprise getClient() {
        return client;
    }

    public void setClient(Entreprise client) {
        this.client = client;
    }

    public Entreprise getEmeteur() {
        return emeteur;
    }

    public void setEmeteur(Entreprise emeteur) {
        this.emeteur = emeteur;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GregorianCalendar getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(GregorianCalendar dateFacture) {
        this.dateFacture = dateFacture;
    }

    public GregorianCalendar getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(GregorianCalendar datePayment) {
        this.datePayment = datePayment;
    }
}
