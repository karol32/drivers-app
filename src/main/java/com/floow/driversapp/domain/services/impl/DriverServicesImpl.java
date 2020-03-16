
package com.floow.driversapp.domain.services.impl;

import com.floow.driversapp.domain.model.Driver;
import com.floow.driversapp.domain.repository.DriverRepository;
import com.floow.driversapp.domain.services.DriverServices;
import com.floow.driversapp.util.converter.DataFormater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
class DriverServicesImpl implements DriverServices {
    private DriverRepository driverRepo;

   @Autowired
   DriverServicesImpl(final DriverRepository driverRepo) {
        this.driverRepo = driverRepo;
    }

    @Override
    public List<Driver> getAll() {
        return this.driverRepo.findAll();
    }

    @Override
    public Driver save(final Driver driver) {
        if(driver.getId() != null && this.driverRepo.findById(driver.getId()).isPresent()) {
            return driver; // TODO should return error
        }
        log.info("Driver successfully save with id number {} ", driver.getId());
        return this.driverRepo.save(driver);
    }

    @Override
    public List<Driver> getByDateAfterCreated(final String dateTime) {
        return this.driverRepo.findByCreationDateAfter(LocalDate.parse(dateTime, DataFormater.formatter));
    }

}