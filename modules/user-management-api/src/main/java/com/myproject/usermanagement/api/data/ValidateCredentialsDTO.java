package com.myproject.usermanagement.api.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter @Setter
public class ValidateCredentialsDTO {

    @NotBlank(message = "{username.not.blank}")
    private String username;

    @NotBlank(message = "{password.not.blank}")
    @Size(min = 8, message = "{password.min}")
    private String password;
}
