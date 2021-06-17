package com.world2meet.pruebatecnica.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.world2meet.pruebatecnica.controller.SuperHeroesController;
import com.world2meet.pruebatecnica.entity.SuperHeroes;
import com.world2meet.pruebatecnica.service.SuperHeroesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SuperHeroesController.class)
@WithMockUser
public class SuperHeroesUnitTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperHeroesService superHeroesService;

    List<SuperHeroes> list = new ArrayList<>();
    SuperHeroes mockSuperHeroes = new SuperHeroes(1L, "Superman", "Fuerza del sol");


    String expected = "[{\"name\":\"Superman\",\"descriptionRowers\":\"Fuerza del sol\"}]";

    @Test
    public void whenFindAll_thenOK() throws Exception {
        list.add(mockSuperHeroes);
        Mockito.when(superHeroesService.findAll()).thenReturn(list);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/superheroes").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String actualResponseBody = result.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
                objectMapper.writeValueAsString(list));
    }


    @Test
    public void whenFindByTextInName_thenBadRequestException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/superheroes/findByTextInName/", "azz")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}
