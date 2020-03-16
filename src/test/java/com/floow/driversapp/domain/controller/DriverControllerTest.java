package com.floow.driversapp.domain.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.floow.driversapp.domain.dto.DriverDto;
import com.floow.driversapp.domain.model.Driver;
import com.floow.driversapp.domain.repository.DriverRepository;
import com.floow.driversapp.domain.services.DriverServices;
import com.floow.driversapp.util.converter.ModelMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DriverController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class DriverControllerTest {
    private static final ObjectMapper om = new ObjectMapper();
    private static final String SOME_ID = " 5e6f90974d0bb85b4da1857e";
    private static final String SOME_FIRST_NAME = "TestJohn";
    private static final String SOME_LAST_NAME = "TestRambo";
    private static final LocalDate SOME_DATE_OF_BIRTH = LocalDate.of(1988,11,30 );
    private static final LocalDate SOME_DATE_OF_CREATE = LocalDate.of(2020,01,1 );
    private static final Driver SOME_DRIVER_DATA_1 = Driver.builder()
            .firstName(SOME_FIRST_NAME)
            .lastName(SOME_LAST_NAME)
            .dateOfBirth(SOME_DATE_OF_BIRTH)
            .build();
    private static final DriverDto SOME_DRIVER_DATA_DTO = DriverDto.builder()
            .id(SOME_ID)
            .firstName(SOME_FIRST_NAME)
            .lastName(SOME_LAST_NAME)
            .dateOfBirth("2020-01-01")
            .creationDate(SOME_DATE_OF_CREATE)
            .build();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverServices driverServices;

    @MockBean
    private DriverController driverController;

    @MockBean
    ModelMapper<Driver, DriverDto> modelMapper;

    @Test
    void shouldReturnEmptyArrayWitHttpStatus200() throws Exception {

        //given
        RequestBuilder request = MockMvcRequestBuilders.get("/driver")
                .contentType(MediaType.APPLICATION_JSON);

        //when
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();

        //then
        assertAll(
                ()-> assertThat(result.getResponse().getStatus(), is(200)),
                ()-> assertThat(result.getResponse().getContentAsString(), is(Arrays.asList().toString()))
        );
    }


    @Test
    void shouldReturnArrayWitHttpStatus200() throws Exception {
        //given
        RequestBuilder request = MockMvcRequestBuilders.get("/driver")
                .contentType(MediaType.APPLICATION_JSON);

        List<DriverDto> allDrivers = singletonList(SOME_DRIVER_DATA_DTO);

        //when
        given(driverController.getAll()).willReturn(allDrivers);

        //then
        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(SOME_DRIVER_DATA_DTO.getId())))
                .andReturn();
    }

    @Test
    void shouldReturnDriverByDateWitHttpStatus200() throws Exception {

        //given
        RequestBuilder request = MockMvcRequestBuilders.get("/driver/byDate")
                .contentType(MediaType.APPLICATION_JSON)
                .param("date", "2020-01-01");
        List<DriverDto> allDrivers = singletonList(SOME_DRIVER_DATA_DTO);

        //when
        given(driverController.getByDateOfCreated("2020-01-01")).willReturn(allDrivers);
        //then
        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(SOME_DRIVER_DATA_DTO.getId())))
                .andExpect(jsonPath("$[0].firstname", is(SOME_DRIVER_DATA_DTO.getFirstName())))
                .andExpect(jsonPath("$[0].lastname", is(SOME_DRIVER_DATA_DTO.getLastName())))
                .andExpect(jsonPath("$[0].date_of_birth", is(SOME_DRIVER_DATA_DTO.getDateOfBirth())))
                .andExpect(jsonPath("$[0].creationDate", is(SOME_DRIVER_DATA_DTO.getCreationDate().toString())))
                .andReturn();
    }

    @Test
    void shouldSaveDriverAndReturnJsonWitHttpStatus200() throws Exception {

        //given
        String json = "{\"date_of_birth\": \"2009-01-17\",  \"firstname\": \"Karol\", \"lastname\": \"Cecot\"}";


        RequestBuilder request = MockMvcRequestBuilders.post("/driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        //when
        when(driverServices.save(any(Driver.class))).thenReturn(SOME_DRIVER_DATA_1);
        //then
        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }
}