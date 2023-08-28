package br.com.alura.hotel.jdbc.controller;

import java.sql.Connection;

import br.com.alura.hotel.jdbc.dao.HospedeDAO;
import br.com.alura.hotel.jdbc.factory.ConnectionFactory;
import br.com.alura.hotel.jdbc.model.Hospede;

public class HospedeController extends Repository<Hospede> {
	
	public HospedeController() {
		Connection connection = new ConnectionFactory().connection();
		super.dao = new HospedeDAO(connection);
	}
}
