package br.com.alura.hotel.jdbc.controller;

import java.sql.Connection;

import br.com.alura.hotel.jdbc.dao.ReservaDAO;
import br.com.alura.hotel.jdbc.factory.ConnectionFactory;
import br.com.alura.hotel.jdbc.model.Reserva;

public class ReservaController extends Repository<Reserva> {
	
	public ReservaController() {
		Connection connection = new ConnectionFactory().connection();
		super.dao = new ReservaDAO(connection);
	}
}