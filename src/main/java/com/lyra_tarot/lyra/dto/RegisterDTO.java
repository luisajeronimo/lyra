package com.lyra_tarot.lyra.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

public record RegisterDTO (
    @NotBlank String nome,
    @NotBlank @Email String email,
    @NotBlank String senha,
    @NotBlank String estado,
    @NotBlank String cidade,
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past(message = "A data de nascimento deve ser uma data anterior à data atual.")
    @NotNull LocalDate dataNascimento,
    
    @JsonFormat(pattern = "HH:mm")
    @NotNull LocalTime horaNascimento
){

}
