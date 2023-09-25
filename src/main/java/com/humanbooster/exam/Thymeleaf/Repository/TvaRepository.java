package com.humanbooster.exam.Thymeleaf.Repository;

import com.humanbooster.exam.Thymeleaf.Models.LigneFacture;
import com.humanbooster.exam.Thymeleaf.Models.Tva;
import org.springframework.data.repository.CrudRepository;

public interface TvaRepository extends CrudRepository<Tva, Long> {
    public Tva findByLibelle(String libelle);
}
