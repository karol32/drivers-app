
package com.floow.driversapp.util.converter.impl;

import com.floow.driversapp.domain.dto.DriverDto;
import com.floow.driversapp.domain.model.Driver;
import com.floow.driversapp.util.converter.DataFormater;
import com.floow.driversapp.util.converter.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;



@Component
class DriverModelMapper implements ModelMapper<Driver, DriverDto> {

    @Override
    public Driver dtoToModel(DriverDto driverDto) {
        return Driver
                .builder()
                .firstName(driverDto.getFirstName())
                .lastName(driverDto.getLastName())
                .dateOfBirth(LocalDate.parse(driverDto.getDateOfBirth(), DataFormater.formatter))
                .build();
    }

    @Override
    public DriverDto modelToDto(Driver driver) {
        return DriverDto
                .builder()
                .id(driver.getId())
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .dateOfBirth(driver.getDateOfBirth().format(DataFormater.formatter))
                .creationDate(driver.getCreationDate())
                .build();
    }
}

