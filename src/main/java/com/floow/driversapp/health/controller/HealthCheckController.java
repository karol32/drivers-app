package com.floow.driversapp.health.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Api(tags = "Health Check", produces = "application/json", description = "Service health verification.")
class HealthCheckController {

    @ApiOperation(value = "Verify Health of Service", response = String.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/health", produces = "application/json")
    public ResponseEntity<Object> health() {
        return ResponseEntity.ok("");
    }
}