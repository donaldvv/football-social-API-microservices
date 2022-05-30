package com.don.usersservice.dto.request;

import com.don.usersservice.model.enums.EGender;
import com.don.usersservice.model.enums.ERole;
import com.don.usersservice.validator.EnumValidator;
import com.don.usersservice.validator.RoleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Donald Veizi
 */
@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 18, message = "Password must be at between 6 and 18 characters long!")
    private String password;

    @NotNull
    @NotEmpty
    private Collection< @RoleEnum(enumClass = ERole.class, ignoreCase = true) String> roles;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @EnumValidator(enumClass = EGender.class, ignoreCase = true)
    private String gender;

    @NotNull(message = "The departure date is required.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank
    private String address;
}

