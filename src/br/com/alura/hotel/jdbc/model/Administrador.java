package br.com.alura.hotel.jdbc.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Administrador extends Pessoa implements Autenticavel {
	private String user;
	private String password;

	public Administrador(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public Administrador(String nome, String sobrenome, LocalDate dataNascimento,
						 String nacionalidade, String telefone) {
		super(nome, sobrenome, dataNascimento, nacionalidade, telefone);
	}

	@Override
	public String getUser() {
		return this.user;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean autentica(PreparedStatement pstmt) {
		try (ResultSet rst = pstmt.getResultSet()) {
			return rst.next();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
