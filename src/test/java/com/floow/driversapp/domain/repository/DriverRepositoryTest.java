package com.floow.driversapp.domain.repository;

import com.floow.driversapp.domain.model.Driver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6BDDAssertions.then;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@TestPropertySource(locations = "classpath:application-test.properties")
class DriverRepositoryTest {

    private static final String SOME_FIRST_NAME = "TestJohn";
    private static final String SOME_LAST_NAME = "TestRambo";
    private static final LocalDate SOME_DATE_OF_BIRTH = LocalDate.of(1988,11,30 );
    private static final LocalDate SOME_DATE_OF_CREATE = LocalDate.of(2020,1,1 );
    private static final Driver SOME_DRIVER_DATA_1 = Driver.builder()
            .firstName(SOME_FIRST_NAME)
            .lastName(SOME_LAST_NAME)
            .dateOfBirth(SOME_DATE_OF_BIRTH)
            .build();
    private static final Driver SOME_DRIVER_DATA_2 = Driver.builder()
            .firstName(SOME_FIRST_NAME)
            .lastName(SOME_LAST_NAME)
            .dateOfBirth(SOME_DATE_OF_BIRTH)
            .build();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DriverRepository driverRepository;

    @AfterEach
    void cleanUpDatabase() {
        mongoTemplate.dropCollection(Driver.class);
    }

    @Test
    void shouldSaveDriverToRepository() {
        // when
        final Driver savedDriverInDb = driverRepository.save(SOME_DRIVER_DATA_1);

        // then
        Optional<Driver> response = driverRepository.findById(savedDriverInDb.getId());
        assertAll(
                ()-> assertNotNull(response.get().getId()),
                ()-> assertThat(response.get(), is(savedDriverInDb))
        );
    }

    @Test
    void shouldGetAllDrivers() {
        //given
        final Driver driver_1 = driverRepository.save(SOME_DRIVER_DATA_1);
        final Driver driver_2 = driverRepository.save(SOME_DRIVER_DATA_2);

        // when
        final List<Driver> driverListFromDb = driverRepository.findAll();

        //then
        assertAll(
                ()-> assertNotNull(driverListFromDb),
                ()-> then(driverListFromDb).contains(driver_1, driver_2)
        );
    }

    @Test
    void shouldGetAllDriversByDataParam(){
        //given
        final Driver driver_1 = driverRepository.save(SOME_DRIVER_DATA_1);
        final Driver driver_2 = driverRepository.save(SOME_DRIVER_DATA_2);

        // when
        final List<Driver> driverListFromDb = driverRepository.findByCreationDateAfter(SOME_DATE_OF_CREATE);

        //then
        assertAll(
                ()-> assertNotNull(driverListFromDb),
                ()-> then(driverListFromDb).contains(driver_1, driver_2)
        );

    }


}