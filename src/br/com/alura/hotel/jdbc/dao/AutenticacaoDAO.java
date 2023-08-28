package br.com.alura.hotel.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.alura.hotel.jdbc.model.Autenticavel;

public class AutenticacaoDAO {

	private Connection connection;

	public AutenticacaoDAO(Connection connection) {
		this.connection = connection;
	}

	public boolean login(Autenticavel autenticavel) {
		String sql = "SELECT * FROM usuario WHERE user = ? AND password = ?";
		try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
			pstmt.setString(1, autenticavel.getUser());
			pstmt.setString(2, autenticavel.getPassword());
			pstmt.execute();

			return autenticavel.autentica(pstmt);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
