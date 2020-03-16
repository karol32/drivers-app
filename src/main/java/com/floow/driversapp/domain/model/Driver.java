package com.floow.driversapp.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(collection = "Driver")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Driver {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private LocalDate creationDate;

    @PersistenceConstructor
    @Builder
    Driver(final String firstName, final String lastName, final LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.creationDate = LocalDate.now();
    }

}
