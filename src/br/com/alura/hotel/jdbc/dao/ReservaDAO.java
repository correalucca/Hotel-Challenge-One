package br.com.alura.hotel.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.hotel.jdbc.model.Reserva;

public class ReservaDAO implements DAO<Reserva> {

	private Connection connection;

	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Reserva> listar() {
		try {
			List<Reserva> reservas = new ArrayList<>();
			String sql = "SELECT * FROM reservas";
			try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
				pstmt.execute();
				converteResult(reservas, pstmt);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void salvar(Reserva reserva) {
		String sql = "INSERT INTO reservas(data_entrada, data_saida, valor, forma_pagamento) VALUES (?, ?, ?, ?)";

		try(PreparedStatement pstmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, reserva.getDataEntrada().toString());
			pstmt.setString(2, reserva.getDataSaida().toString());
			pstmt.setDouble(3, reserva.getValor());
			pstmt.setString(4, reserva.getFormaDePagamento());
			pstmt.execute();

			try (ResultSet resultSet = pstmt.getGeneratedKeys()) {
				while (resultSet.next()) {
					reserva.setId(resultSet.getInt(1));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Reserva> buscar(String busca) {
		try {
			String sql = "SELECT * FROM reservas WHERE id=?";
			List<Reserva> reservas = new ArrayList<>();
			try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
				pstmt.setString(1, busca);
				pstmt.execute();
				converteResult(reservas, pstmt);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deletar(Integer id) {
		try {
			String sql = "DELETE FROM reservas WHERE id=?";
			try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
				pstmt.setInt(1, id);
				pstmt.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void alterar(Reserva reserva) {
		try {
			String sql = "UPDATE reservas SET data_entrada = ?, data_saida = ?, valor = ?, forma_pagamento = ? WHERE ID = ?";

			try(PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
				pstmt.setString(1, reserva.getDataEntrada().toString());
				pstmt.setString(2, reserva.getDataSaida().toString());
				pstmt.setDouble(3, reserva.getValor());
				pstmt.setString(4, reserva.getFormaDePagamento());
				pstmt.setInt(5, reserva.getId());
				pstmt.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reserva> converteResult(List<Reserva> reservas, PreparedStatement pstmt) {
		try (ResultSet rst = pstmt.getResultSet()) {
			while (rst.next()) {
				Reserva reserva = new Reserva(
						rst.getInt(1),
						LocalDate.parse(rst.getString(2)),
						LocalDate.parse(rst.getString(3)),
						rst.getDouble(4),
						rst.getString(5)
				);
				reservas.add(reserva);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
