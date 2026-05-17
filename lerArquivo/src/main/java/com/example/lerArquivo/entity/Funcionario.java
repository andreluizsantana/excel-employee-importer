package com.example.lerArquivo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Funcionario {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "func_seq")
  @SequenceGenerator(
      name = "func_seq",
      sequenceName = "func_sequence",
      initialValue = 1,
      allocationSize = 1)
  private long id;

  private String cpf;

  private String nome;

  private String telefone;

  private BigDecimal salario;

  public Funcionario(String cpf, String nome, String telefone, BigDecimal salario) {
    this.cpf = cpf;
    this.nome = nome;
    this.telefone = telefone;
    this.salario = salario;
  }

  public Funcionario() {}

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public BigDecimal getSalario() {
    return salario;
  }

  public void setSalario(BigDecimal salario) {
    this.salario = salario;
  }

  public long getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Funcionario [id="
        + id
        + ", cpf="
        + cpf
        + ", nome="
        + nome
        + ", telefone="
        + telefone
        + ", salario="
        + salario
        + "]";
  }
}
