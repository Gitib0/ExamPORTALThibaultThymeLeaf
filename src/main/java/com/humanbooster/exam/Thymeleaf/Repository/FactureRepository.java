package com.humanbooster.exam.Thymeleaf.Repository;

import com.humanbooster.exam.Thymeleaf.Models.Facture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends CrudRepository<Facture, Long> {
    public Facture findByLibelle(String libelle);


    public List<Facture> findAll();
}
