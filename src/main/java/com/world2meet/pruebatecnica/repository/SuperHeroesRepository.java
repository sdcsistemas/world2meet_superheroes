package com.world2meet.pruebatecnica.repository;

import com.world2meet.pruebatecnica.entity.SuperHeroes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperHeroesRepository extends JpaRepository<SuperHeroes, Long> {

    @Query(value="select sh from SuperHeroes sh where sh.name like %?1%")
    List<SuperHeroes> findByTextInName(String text);
}
