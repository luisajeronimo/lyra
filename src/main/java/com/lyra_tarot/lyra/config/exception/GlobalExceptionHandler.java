package com.lyra_tarot.lyra.config.exception;

import com.lyra_tarot.lyra.dto.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. ERRO 404: Recurso não encontrado
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> tratarErro404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO("Recurso não encontrado.", 404));
    }

    // 2. ERRO 400: Validação de dados
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    // 3. ERRO 400: Regras de negócio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> tratarErroRegraDeNegocio(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO(ex.getMessage(), 400));
    }

    // 4. ERRO 400: JSON mal formado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> tratarErroDeLeituraJSON(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO("Erro no formato dos dados enviados. Verifique a formatação.", 400));
    }

    // 5. ERRO 409: Conflito de dados
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> tratarErroBancoDeDados(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO("Este dado já está cadastrado no sistema (Conflito).", 409));
    }

    // 6. ERRO 401: Login errado
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> tratarErroLogin() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO("E-mail ou senha inválidos.", 401));
    }

    // 7. ERRO 403: Sem permissão
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> tratarAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseDTO("Você não tem permissão para acessar este recurso.", 403));
    }

    // 8. ERRO 500: Erro geral
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> tratarErro500(Exception ex) {
        System.out.println("Erro crítico capturado: " + ex.getMessage()); 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Erro interno no servidor.", 500));
    }

    // 9. ERRO 503: API do Gemini sobrecarregada ou fora do ar
    @ExceptionHandler(IntegracaoGeminiException.class)
    public ResponseEntity<ErrorResponseDTO> tratarErroGemini(IntegracaoGeminiException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponseDTO(ex.getMessage(), 503));
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}