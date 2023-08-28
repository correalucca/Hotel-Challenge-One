package br.com.alura.hotel.jdbc.model;

import java.sql.PreparedStatement;

public interface Autenticavel {

	String getUser();

	String getPassword();

	boolean autentica(PreparedStatement pstmt);
}
