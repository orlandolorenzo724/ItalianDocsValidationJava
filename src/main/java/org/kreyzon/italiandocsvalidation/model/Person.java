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
}
