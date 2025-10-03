package com.michel.first_spring_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "jogadores")
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @NotBlank
    private String posicao;

    @NotNull
    private Integer numero;

    @NotNull
    private Double salario;

    @NotNull
    @Column(name = "valor_mercado")
    private Double valorMercado;

    @NotNull
    @Column(name = "valor_contrato")
    private Double valorContrato;

    @NotNull
    @Column(name = "tempo_contrato")
    private Integer tempoContrato;

    @NotBlank
    private String categoria; // principal, sub-23, feminino

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nacionalidade_iso2", nullable = false)
    private Nacionalidade nacionalidade;

    public Jogador() {}

    public Jogador(String nome, LocalDate dataNascimento, Nacionalidade nacionalidade, String posicao,
                   Integer numero, Double salario, Double valorMercado,
                   Double valorContrato, Integer tempoContrato, String categoria) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
        this.posicao = posicao;
        this.numero = numero;
        this.salario = salario;
        this.valorMercado = valorMercado;
        this.valorContrato = valorContrato;
        this.tempoContrato = tempoContrato;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Nacionalidade getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Nacionalidade nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(Double valorMercado) {
        this.valorMercado = valorMercado;
    }

    public Double getValorContrato() {
        return valorContrato;
    }

    public void setValorContrato(Double valorContrato) {
        this.valorContrato = valorContrato;
    }

    public Integer getTempoContrato() {
        return tempoContrato;
    }

    public void setTempoContrato(Integer tempoContrato) {
        this.tempoContrato = tempoContrato;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}


