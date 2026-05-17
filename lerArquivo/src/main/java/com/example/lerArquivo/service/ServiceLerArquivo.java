package com.example.lerArquivo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lerArquivo.entity.Funcionario;
import com.example.lerArquivo.exception.ArquivoInvalidoException;
import com.example.lerArquivo.repository.RepositoryLerArquivo;

@Service
@Transactional
public class ServiceLerArquivo {

  private final RepositoryLerArquivo repository;

  public ServiceLerArquivo(RepositoryLerArquivo repository) {
    this.repository = repository;
  }

  public void importarPlanilha(Workbook workbook) {
    List<Funcionario> funcionarios = new ArrayList<>();
    Sheet aba = workbook.getSheetAt(0);
    DataFormatter formatter = new DataFormatter();

    for (Row linha : aba) {
      if (linha.getRowNum() == 0 || isLinhaVazia(linha)) {
        continue;
      }

      try {
        Funcionario f = new Funcionario();
        f.setCpf(getCellValueAsString(linha.getCell(1), formatter));
        f.setNome(getCellValueAsString(linha.getCell(2), formatter));
        f.setTelefone(getCellValueAsString(linha.getCell(3), formatter));

        String salarioStr =
            getCellValueAsString(linha.getCell(4), formatter).replace(",", ".").trim();

        if (!salarioStr.isEmpty()) {
          f.setSalario(new BigDecimal(salarioStr));
        }

        funcionarios.add(f);
      } catch (NumberFormatException e) {
        throw new ArquivoInvalidoException(
            "Erro ao converter salário na linha " + (linha.getRowNum() + 1), e);
      } catch (Exception e) {
        throw new ArquivoInvalidoException("Erro ao processar linha " + (linha.getRowNum() + 1), e);
      }
    }

    if (!funcionarios.isEmpty()) {
      repository.saveAll(funcionarios);
    }
  }

  private String getCellValueAsString(Cell cell, DataFormatter formatter) {
    if (cell == null) return "";
    return formatter.formatCellValue(cell).trim();
  }

  private boolean isLinhaVazia(Row row) {
    for (Cell cell : row) {
      if (cell != null && !getCellValueAsString(cell, new DataFormatter()).isEmpty()) {
        return false;
      }
    }
    return true;
  }
}
