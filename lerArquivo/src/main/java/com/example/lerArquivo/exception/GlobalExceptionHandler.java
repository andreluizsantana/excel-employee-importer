package com.example.lerArquivo.exception;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ArquivoInvalidoException.class)
  public ResponseEntity<String> handleArquivoInvalido(ArquivoInvalidoException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Erro de validação, arquivo inválido:  " + e.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleArquivoInvalido(IllegalArgumentException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Erro de validação, formato não suportado: " + e.getMessage());
  }

  @ExceptionHandler(UnsupportedOperationException.class)
  public ResponseEntity<String> handleArquivoInvalido(UnsupportedOperationException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Erro de validação Importação CSV ainda não implementada: " + e.getMessage());
  }

  @ExceptionHandler(NumberFormatException.class)
  public ResponseEntity<String> handleArquivoInvalido(NumberFormatException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Erro de validação: " + e.getMessage());
  }

  @ExceptionHandler(EncryptedDocumentException.class)
  public ResponseEntity<String> handleArquivoInvalido(EncryptedDocumentException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Erro de validação: " + e.getMessage());
  }

  @ExceptionHandler(NotOfficeXmlFileException.class)
  public ResponseEntity<String> handleArquivoInvalido(NotOfficeXmlFileException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body("Erro de validação: " + e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
  }
}
