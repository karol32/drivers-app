package com.floow.driversapp.domain.services;

import com.floow.driversapp.domain.model.Driver;

import java.util.List;

public interface DriverServices {
      List<Driver> getAll();
      Driver save(Driver driver);
      List<Driver> getByDateAfterCreated(String dateTime);

}
