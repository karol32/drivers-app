package com.floow.driversapp.domain.controller;

import com.floow.driversapp.domain.dto.DriverDto;
import com.floow.driversapp.domain.model.Driver;
import com.floow.driversapp.domain.services.DriverServices;

import com.floow.driversapp.util.converter.ModelMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/driver")
@Api(tags = "Drivers API", produces = "application/json", description = "Available API endpoints")
class DriverController {

   private DriverServices driverServices;
   private ModelMapper<Driver, DriverDto> modelMapper;

    @Autowired
    public DriverController(final DriverServices driverServices, final ModelMapper<Driver, DriverDto> modelMapper) {
        this.driverServices = driverServices;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    @ApiOperation(value = "An endpoint which returns a list of all existing drivers in JSON format", response = Driver.class, produces = "application/json")
    public List<DriverDto> getAll(){
        return driverServices.getAll()
                .stream()
                .map(modelMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/byDate")
    @ApiOperation(value = "An endpoint which returns a list of all drivers created after the specified date in JSON format", response = Driver.class, produces = "application/json")
    @ApiParam(value = "Created after the specified date.",
            required = true,
            example = "1980-05-01")
    public List<DriverDto> getByDateOfCreated(@Valid @RequestParam String date){
        return driverServices
                .getByDateAfterCreated(date)
                .stream()
                .map(modelMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation(value = "An endpoint to allow new drivers to be created and stored.", response = Driver.class, produces = "application/json")
    public DriverDto post(@Valid @RequestBody DriverDto dto){
        return Optional.of(
                dto)
                .map(modelMapper::dtoToModel)
                .map(driverServices::save)
                .map(modelMapper::modelToDto).get();
    }

}
