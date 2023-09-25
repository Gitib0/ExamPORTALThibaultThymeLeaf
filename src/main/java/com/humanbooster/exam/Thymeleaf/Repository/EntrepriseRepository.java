package com.humanbooster.exam.Thymeleaf.Repository;

import com.humanbooster.exam.Thymeleaf.Models.Entreprise;
import org.springframework.data.repository.CrudRepository;

public interface EntrepriseRepository extends CrudRepository<Entreprise, Long> {
    public Entreprise findByRaisonSociale(String raisonSociale);
}
