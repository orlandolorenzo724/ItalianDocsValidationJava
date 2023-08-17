package org.kreyzon.italiandocsvalidation.model;

import lombok.*;


@Builder
@AllArgsConstructor
@Data
public class Person {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String birthplace;
    private String birthplaceInitials;
    private String gender;

    public Person(String firstName, String lastName, String dateOfBirth, String birthplace, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.birthplace = birthplace;
        this.gender = gender;
    }
}
