package com.example.lerArquivo.controller;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.lerArquivo.exception.ArquivoInvalidoException;
import com.example.lerArquivo.service.ServiceLerArquivo;

@RestController
@RequestMapping("/api/importar")
public class ControllerLerArquivo {

  private final ServiceLerArquivo service;

  public ControllerLerArquivo(ServiceLerArquivo service) {
    this.service = service;
  }

  @PostMapping("/arquivoexcel")
  public ResponseEntity<String> importarArquivo(@RequestParam("file") MultipartFile arquivo)
      throws ArquivoInvalidoException {

    if (arquivo.isEmpty()) {
      throw new ArquivoInvalidoException("Arquivo vazio");
    }

    String nomeArquivo = arquivo.getOriginalFilename();
    if (nomeArquivo == null) {
      throw new ArquivoInvalidoException("Arquivo inválido");
    }

    String extensao = getExtensao(nomeArquivo);

    try (Workbook workbook = abrirPlanilha(arquivo, extensao)) {
      service.importarPlanilha(workbook);
      return ResponseEntity.ok("Arquivo importado com sucesso.");
    } catch (IOException e) {
      throw new ArquivoInvalidoException("Erro ao ler o arquivo: " + e.getMessage(), e);
    }
  }

  private Workbook abrirPlanilha(MultipartFile arquivo, String tipo) throws IOException {
    String tipoMinusculo = tipo.toLowerCase();
    if (tipoMinusculo.equals("xlsx")) {
      return new XSSFWorkbook(arquivo.getInputStream());
    } else if (tipoMinusculo.equals("xls")) {
      return new HSSFWorkbook(arquivo.getInputStream());
    } else if (tipoMinusculo.equals("csv")) {
      throw new UnsupportedOperationException("Importação CSV ainda não implementada");
    } else {
      throw new IllegalArgumentException("Formato não suportado: " + tipo);
    }
  }

  private String getExtensao(String nomeArquivo) {
    if (nomeArquivo.endsWith(".xlsx")) {
      return "xlsx";
    } else if (nomeArquivo.endsWith(".xls")) {
      return "xls";
    } else if (nomeArquivo.endsWith(".csv")) {
      return "csv";
    } else {
      throw new IllegalArgumentException("Formato inválido");
    }
  }
}
