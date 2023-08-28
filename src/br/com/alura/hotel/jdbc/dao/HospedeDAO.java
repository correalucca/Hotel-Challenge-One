package br.com.alura.hotel.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.hotel.jdbc.model.Hospede;

public class HospedeDAO implements DAO<Hospede> {

	private Connection connection;

	public HospedeDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Hospede> listar() {
		List<Hospede> hospedes = new ArrayList<>();
		String sql = "SELECT * FROM hospedes";
		try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
			pstmt.execute();
			return converteResult(hospedes, pstmt);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void salvar(Hospede hospede) {
		String sql = "INSERT INTO hospedes (nome, sobrenome, data_nascimento, nacionalidade, telefone, id_reserva) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, hospede.getNome());
			pstmt.setString(2, hospede.getSobrenome());
			pstmt.setString(3, hospede.getDataNascimento().toString());
			pstmt.setString(4, hospede.getNacionalidade());
			pstmt.setString(5, hospede.getTelefone());
			pstmt.setInt(6, hospede.getIdReserva());
			pstmt.execute();
			try (ResultSet rst = pstmt.getGeneratedKeys()) {
				while (rst.next()) {
					hospede.setId(rst.getInt(1));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Hospede> buscar(String busca) {
		try {
			String sql = "SELECT * FROM hospedes WHERE id_reserva = ? OR sobrenome = ?";
			List<Hospede> hospedes = new ArrayList<>();
			try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
				pstmt.setString(1, busca);
				pstmt.setString(2, busca);
				pstmt.execute();
				converteResult(hospedes, pstmt);
			}
			return hospedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deletar(Integer id) {
		try {
			String sql = "DELETE FROM hospedes WHERE id = ?";
			try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
				pstmt.setInt(1, id);
				pstmt.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void alterar(Hospede hospede) {
		try {
			String sql = "UPDATE hospedes SET nome = ?, sobrenome = ?, data_nascimento = ?, nacionalidade = ?, telefone = ? WHERE id = ?";
			try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
				pstmt.setString(1, hospede.getNome());
				pstmt.setString(2, hospede.getSobrenome());
				pstmt.setString(3, hospede.getDataNascimento().toString());
				pstmt.setString(4, hospede.getNacionalidade());
				pstmt.setString(5, hospede.getTelefone());
				pstmt.setInt(6, hospede.getId());
				pstmt.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Hospede> converteResult(List<Hospede> hospedesList, PreparedStatement pstmt) {
		try (ResultSet rst = pstmt.getResultSet()) {
			while (rst.next()) {
				Hospede hospede = new Hospede(
						rst.getInt(1), rst.getString(2), rst.getString(3),
						LocalDate.parse(rst.getString(4)), rst.getString(5),
						rst.getString(6), rst.getInt(7));
				hospedesList.add(hospede);
			}
			return hospedesList;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
