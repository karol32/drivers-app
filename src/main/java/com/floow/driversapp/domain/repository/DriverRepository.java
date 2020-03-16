package com.floow.driversapp.domain.repository;


import com.floow.driversapp.domain.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DriverRepository extends MongoRepository<Driver, String> {

    List<Driver> findAll();

    List<Driver> findByCreationDateAfter(LocalDate date);
}
