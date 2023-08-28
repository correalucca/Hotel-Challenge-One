package br.com.alura.hotel.jdbc.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public abstract class Pessoa {

	protected String nome;
	protected String sobrenome;
	protected LocalDate dataNascimento;
	protected String nacionalidade;
	protected String telefone;

	public Pessoa() {
	}

	public Pessoa(String nome, String sobrenome, LocalDate dataNascimento, String nacionalidade, String telefone) {
		if (ChronoUnit.YEARS.between(dataNascimento.atStartOfDay(), LocalDate.now().atStartOfDay()) <= 18) {
			throw new IllegalArgumentException("Data de nascimento inválida, a pessoa deve ser maior de 18 anos.");
		}
		if (nome.isEmpty() || sobrenome.isEmpty() || dataNascimento == null || nacionalidade.isEmpty()
				|| telefone.isEmpty()) {
			throw new IllegalArgumentException("Preencha todos os campos");
		}
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.nacionalidade = nacionalidade;
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return this.nome + " " + this.sobrenome + ", " + this.dataNascimento + ", " + this.getIdade()
				+ " anos de idade, " + this.nacionalidade;
	}

	public String getNome() {
		return this.nome;
	}

	public String getSobrenome() {
		return this.sobrenome;
	}

	public LocalDate getDataNascimento() {
		return this.dataNascimento;
	}

	public int getIdade() {
		return Period.between(this.dataNascimento, LocalDate.now()).getYears();
	}

	public String getNacionalidade() {
		return this.nacionalidade;
	}

	public String getTelefone() {
		return this.telefone;
	}


}
