package com.agosh.account.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity()
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "FirstName is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank
    @Column(unique=true)
    @Email(message = "Please enter a valid e-mail address")
    private String email;

    @NotNull(message = "please enter valid date")
    @Past
    private Date dob;

}