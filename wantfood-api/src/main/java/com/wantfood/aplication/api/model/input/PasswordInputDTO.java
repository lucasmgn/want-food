package com.wantfood.aplication.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordInputDTO {
	
	@NotBlank
    private String currentPassword;
    
    @NotBlank
    private String newPassword;
}
