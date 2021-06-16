package com.world2meet.pruebatecnica.controller;

import com.world2meet.pruebatecnica.entity.SuperHeroes;
import com.world2meet.pruebatecnica.service.SuperHeroesService;
import fj.data.Validation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(value = SuperHeroesController.class)
@WithMockUser
public class SuperHeroesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperHeroesService superHeroesService;

    SuperHeroes mockSuperHeroes = new SuperHeroes("1", "Superman", "Fuerza del sol");

    String expected = "{\"id\":\"1\",\"name\":\"Superman\",\"descriptionRowers\":\"Fuerza del sol\"}";

    @Test
    public void retrieveDetailsForSuperHeroes() throws Exception {

        Validation<String, SuperHeroes> response = superHeroesService.findById(mockSuperHeroes.getId());
        Mockito.when(response.success()).thenReturn(mockSuperHeroes);

       /* Mockito.when(
                superHeroesService.findById(mockSuperHeroes.getId())).thenReturn(mockSuperHeroes);*/

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/superheroes/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());


        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

}