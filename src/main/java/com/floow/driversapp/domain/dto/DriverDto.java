package com.floow.driversapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class DriverDto {

    @ApiModelProperty(hidden = true)
    private String id;

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "John", required = true)
    private String firstName;

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "Rambo", required = true)
    private String lastName;

    @NotNull
    @NotEmpty
    @ApiModelProperty(example = "2019-01-17", required = true)
    private String dateOfBirth;

    @ApiModelProperty(value = "Driver creation time ", hidden = true)
    private LocalDate creationDate;

    @JsonCreator
    @Builder
    public DriverDto(
            final String id,
            @JsonProperty("firstname") final String firstName,
            @JsonProperty("lastname")  final String lastName,
            @JsonProperty("date_of_birth") final String dateOfBirth,
            final LocalDate creationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.creationDate = creationDate;
    }
}
