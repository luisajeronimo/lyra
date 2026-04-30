package com.lyra_tarot.lyra.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO (@NotBlank String email, @NotBlank String senha){

}
