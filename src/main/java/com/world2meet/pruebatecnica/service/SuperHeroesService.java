package com.world2meet.pruebatecnica.service;

import com.world2meet.pruebatecnica.entity.SuperHeroes;
import com.world2meet.pruebatecnica.exception.BusinessException;
import fj.data.Validation;

import java.util.List;

public interface SuperHeroesService {

    List<SuperHeroes> findAll();

    Validation<String, SuperHeroes> findById(String id);

    Validation<String, List<SuperHeroes>> findByTextInName(String text);

    Validation<String, SuperHeroes> insert(SuperHeroes superHeroes);

    Validation<String, BusinessException> delete(String id);

}
