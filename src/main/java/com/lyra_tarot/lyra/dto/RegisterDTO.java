package com.lyra_tarot.lyra.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.lyra_tarot.lyra.model.UserRole;

public record RegisterDTO (
    @NotBlank String nome,
    @NotBlank @Email String email,
    @NotBlank String senha,
    @NotNull UserRole role,
    @NotBlank String estado,
    @NotBlank String cidade,
    @NotNull LocalDate dataNascimento,
    @NotNull LocalTime horaNascimento
){

}
