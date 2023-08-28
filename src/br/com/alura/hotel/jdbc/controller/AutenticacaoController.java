package br.com.alura.hotel.jdbc.controller;

import java.sql.Connection;

import br.com.alura.hotel.jdbc.dao.AutenticacaoDAO;
import br.com.alura.hotel.jdbc.factory.ConnectionFactory;
import br.com.alura.hotel.jdbc.model.Autenticavel;

public class AutenticacaoController {

	private AutenticacaoDAO autenticacaoDAO;
	private Autenticavel autenticavel;

	public AutenticacaoController(Autenticavel autenticavel) {
		Connection connection = new ConnectionFactory().connection();

		this.autenticacaoDAO = new AutenticacaoDAO(connection);
		this.autenticavel = autenticavel;
	}

	public boolean login() {
		return this.autenticacaoDAO.login(this.autenticavel);
	}
}
