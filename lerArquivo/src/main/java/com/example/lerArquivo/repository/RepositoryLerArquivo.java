package com.example.lerArquivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lerArquivo.entity.Funcionario;

public interface RepositoryLerArquivo extends JpaRepository<Funcionario, Long> {}
