package com.world2meet.pruebatecnica.controller;

import com.world2meet.pruebatecnica.config.TrackExecutionTime;
import com.world2meet.pruebatecnica.dto.SuperHeroesDto;
import com.world2meet.pruebatecnica.entity.SuperHeroes;
import com.world2meet.pruebatecnica.exception.BusinessException;
import com.world2meet.pruebatecnica.service.SuperHeroesService;
import fj.data.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@Validated
@RequestMapping("/superheroes")
public class SuperHeroesController {

    private final static Logger log = LoggerFactory.getLogger(SuperHeroesController.class);

    CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS)
            .noTransform()
            .mustRevalidate();

    @Autowired
    private SuperHeroesService superHeroesService;

    @GetMapping("")
    @TrackExecutionTime
    public ResponseEntity<List<SuperHeroes>> findAll() {
        try {
            List<SuperHeroes> superHeroesList = new ArrayList<>();
            superHeroesService.findAll().forEach(superHeroesList::add);

            if (superHeroesList.isEmpty()) {
                return ResponseEntity.noContent()
                        .cacheControl(cacheControl).build();
            }
            return ResponseEntity.ok()
                    .cacheControl(cacheControl)
                    .body(superHeroesList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .cacheControl(cacheControl)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    @TrackExecutionTime
    public ResponseEntity<?> findById(@PathVariable("id") @NotNull(message = "id: must not be null") Long id) {
        Validation<String, SuperHeroes> response = superHeroesService.findById(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok()
                    .cacheControl(cacheControl)
                    .body(response.success());
        } else {
            return ResponseEntity.badRequest()
                    .cacheControl(cacheControl)
                    .body(new BusinessException(response.fail()));
        }

    }

    @GetMapping("/findByTextInName/{text}")
    @TrackExecutionTime
    public ResponseEntity<List<?>> findByTextInName(@PathVariable("text") @NotEmpty(message = "text: must not be empty") String text) {
        Validation<String, List<SuperHeroes>> response = superHeroesService.findByTextInName(text);
        if (response.isSuccess()) {
            return ResponseEntity.ok()
                    .cacheControl(cacheControl)
                    .body(response.success());
        } else {
            List<BusinessException> list = new ArrayList<>();
            list.add(new BusinessException(response.fail()));
            return ResponseEntity.badRequest()
                    .cacheControl(cacheControl)
                    .body(list);
        }
    }


    @PostMapping("/save")
    @TrackExecutionTime
    public ResponseEntity<?> insert(@Valid @RequestBody SuperHeroesDto superHeroesDto) {
        Validation<String, SuperHeroes> response = superHeroesService.insert(
                new SuperHeroes(superHeroesDto.getName(), superHeroesDto.getDescriptionRowers()));
        if (response.isSuccess()) {
            return ResponseEntity.ok()
                    .cacheControl(cacheControl)
                    .body(response.success());
        } else {
            return ResponseEntity.internalServerError()
                    .cacheControl(cacheControl)
                    .body(new BusinessException(response.fail()));
        }
    }

    @PostMapping("/update/{id}")
    @TrackExecutionTime
    public ResponseEntity<?> update(@PathVariable("id") @NotNull(message = "id: must not be null") Long id, @Valid @RequestBody SuperHeroesDto superHeroesDto) {
        Validation<String, SuperHeroes> superHeroeAux = superHeroesService.findById(id);

        if (superHeroeAux.isSuccess()) {
            SuperHeroes supHer = superHeroeAux.success();
            supHer.setName(superHeroesDto.getName());
            supHer.setDescriptionRowers(superHeroesDto.getDescriptionRowers());

            Validation<String, SuperHeroes> response = superHeroesService.insert(supHer);
            if (response.isSuccess()) {
                return ResponseEntity.ok()
                        .cacheControl(cacheControl)
                        .body(response.success());
            } else {
                return ResponseEntity.internalServerError()
                        .cacheControl(cacheControl)
                        .body(new BusinessException(response.fail()));
            }

        } else {
            return ResponseEntity.badRequest()
                    .cacheControl(cacheControl)
                    .body(new BusinessException(superHeroeAux.fail()));
        }

    }

    @DeleteMapping("/delete/{id}")
    @TrackExecutionTime
    public ResponseEntity<?> delete(@PathVariable("id") @NotNull(message = "id: must not be null") Long id) {
        Validation<String, BusinessException> response = superHeroesService.delete(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok()
                    .cacheControl(cacheControl)
                    .body(response.success());
        } else {
            return ResponseEntity.internalServerError()
                    .cacheControl(cacheControl)
                    .body(new BusinessException(response.fail()));
        }
    }

}
