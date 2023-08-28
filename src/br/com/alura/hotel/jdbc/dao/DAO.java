package br.com.alura.hotel.jdbc.dao;

import java.util.List;

public interface DAO<T> {

	List<T> listar();

	void salvar(T t);

	List<T> buscar(String busca);

	void deletar(Integer id);

	void alterar(T t);
}
