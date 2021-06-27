package com.world2meet.pruebatecnica.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SuperHeroesDto {

    @NotEmpty(message = "name: must not be empty")
    private String name;

    @NotEmpty(message = "descriptionRowers: must not be empty")
    private String descriptionRowers;
}
