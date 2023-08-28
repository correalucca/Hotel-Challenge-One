package br.com.alura.hotel.jdbc.model;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;

public class Reserva {

	private Integer id;
	private LocalDate dataEntrada;
	private LocalDate dataSaida;
	private Double valor;
	private String formaDePagamento;
	private static final double DIARIA = 150.0;

	public Reserva(LocalDate dataEntrada, LocalDate dataSaida, Double valor, String formaDePagamento) {
		if (dataSaida.isBefore(dataEntrada)) {
			throw new IllegalArgumentException("A data de Check-out não pode ser anterior data de Check-in.");
		}
		if (dataEntrada == null || dataSaida == null || valor == null || formaDePagamento == null) {
			throw new IllegalArgumentException("Preencha todos os campos.");
		}
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.valor = valor;
		this.formaDePagamento = formaDePagamento;
	}

	public Reserva(Integer id, LocalDate dataEntrada, LocalDate dataSaida, Double valor, String formaDePagamento) {
		this(dataEntrada, dataSaida, valor, formaDePagamento);
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataEntrada() {
		return this.dataEntrada;
	}

	public LocalDate getDataSaida() {
		return this.dataSaida;
	}

	public Double getValor() {
		return this.valor;
	}

	public String getValor(Locale locale) {
		return NumberFormat.getCurrencyInstance(locale).format(this.valor);
	}

	public String getFormaDePagamento() {
		return this.formaDePagamento;
	}

	public static double valor(LocalDate dataEntrada, LocalDate dataSaida) {
		if (dataSaida.isBefore(dataEntrada)) {
			throw new IllegalArgumentException("A data de Check-out não pode ser anterior data de Check-in.");
		}
		if (dataSaida.isEqual(dataEntrada)) {
			throw new IllegalArgumentException("Selecione uma data de Check-Out posterior.");
		}
		LocalDate now = LocalDate.now();
		if (dataEntrada.isBefore(now) || dataSaida.isBefore(now)) {
			throw new IllegalArgumentException("As datas de Check-in e Check-out devem ser posteriores a data atual.");
		}
		return Reserva.DIARIA * Duration.between(dataEntrada.atStartOfDay(), dataSaida.atStartOfDay()).toDays();
	}

	@Override
	public String toString() {
		return this.id + ", " + this.dataEntrada + ", " + this.valor + ", " + this.dataSaida + ", "
				+ this.formaDePagamento;
	}
}
