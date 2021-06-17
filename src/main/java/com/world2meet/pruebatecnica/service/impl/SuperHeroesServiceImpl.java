package com.world2meet.pruebatecnica.service.impl;

import com.world2meet.pruebatecnica.entity.SuperHeroes;
import com.world2meet.pruebatecnica.exception.BusinessException;
import com.world2meet.pruebatecnica.repository.SuperHeroesRepository;
import com.world2meet.pruebatecnica.service.SuperHeroesService;
import fj.data.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperHeroesServiceImpl implements SuperHeroesService {

    @Autowired
    SuperHeroesRepository superHeroesRepository;

    @Override
    public List<SuperHeroes> findAll() {
        return superHeroesRepository.findAll();
    }

    @Override
    public Validation<String, SuperHeroes> findById(Long id) {
        try {
            SuperHeroes superHeroes = superHeroesRepository.findById(id).get();
            return Validation.success(superHeroes);
        } catch (Exception e) {
            return Validation.fail("Bad Request: " + e.getMessage());
        }
    }

    @Override
    public Validation<String, List<SuperHeroes>> findByTextInName(String text) {
        try {
            List<SuperHeroes> superHeroes = superHeroesRepository.findByTextInName(text);
            if (superHeroes.size() > 0) {
                return Validation.success(superHeroes);
            } else {
                return Validation.fail("No se encontraron elementos");
            }

        } catch (Exception e) {
            return Validation.fail(e.getMessage());
        }
    }

    @Override
    public Validation<String, SuperHeroes> insert(SuperHeroes superHeroes) {
        try {
            SuperHeroes supHer = superHeroesRepository.save(superHeroes);
            return Validation.success(supHer);
        } catch (Exception e) {
            return Validation.fail(e.getMessage());
        }
    }

    @Override
    public Validation<String, BusinessException> delete(Long id) {
        try {
            superHeroesRepository.deleteById(id);
            return Validation.success(new BusinessException("SuperHeroe eliminado satisfactoriamente."));
        } catch (Exception e) {
            return Validation.fail(e.getMessage());
        }
    }


}
