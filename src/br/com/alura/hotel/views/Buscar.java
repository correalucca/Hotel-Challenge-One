package br.com.alura.hotel.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.alura.hotel.jdbc.controller.HospedeController;
import br.com.alura.hotel.jdbc.controller.ReservaController;
import br.com.alura.hotel.jdbc.model.Hospede;
import br.com.alura.hotel.jdbc.model.Reserva;

@SuppressWarnings("serial")
public class Buscar extends JFrame {
	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHospedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHospedes;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private ReservaController reservaController;
	private HospedeController hospedeController;
	DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscar frame = new Buscar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Buscar() {
		this.reservaController = new ReservaController();
		this.hospedeController = new HospedeController();

		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagens/logo-hotel-40px.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblTitulo = new JLabel("SISTEMA DE BUSCA");
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblTitulo.setBounds(331, 62, 280, 42);
		contentPane.add(lblTitulo);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.SCROLL_TAB_LAYOUT);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Reservas", new ImageIcon(Buscar.class.getResource("/imagens/reservado.png")), tbReservas);
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Id");
		modelo.addColumn("Data Check In");
		modelo.addColumn("Data Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pagamento");

		JScrollPane scrool1 = new JScrollPane(tbReservas);
		panel.add("Reservas", scrool1);
		preencherReservas();

		tbHospedes = new JTable();
		tbHospedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHospedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Hóspedes", new ImageIcon(Buscar.class.getResource("/imagens/pessoas.png")), tbHospedes);
		modeloHospedes = (DefaultTableModel) tbHospedes.getModel();
		modeloHospedes.addColumn("Id");
		modeloHospedes.addColumn("Nome");
		modeloHospedes.addColumn("Sobrenome");
		modeloHospedes.addColumn("Data de Nascimento");
		modeloHospedes.addColumn("Nacionalidade");
		modeloHospedes.addColumn("Telefone");
		modeloHospedes.addColumn("Reserva");

		JScrollPane scrool2 = new JScrollPane(tbHospedes);
		panel.add("Hospedes", scrool2);
		preencherHospedes();

		JLabel lblNewLabel2 = new JLabel("");
		lblNewLabel2.setIcon(new ImageIcon(Buscar.class.getResource("/imagens/logo-hotel-100px.png")));
		lblNewLabel2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnExit = new JPanel();
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnExit.setLayout(null);
		btnExit.setBackground(Color.WHITE);
		btnExit.setBounds(857, 0, 53, 36);
		header.add(btnExit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnExit.add(labelExit);

		JSeparator jSeparator_1_2 = new JSeparator();
		jSeparator_1_2.setForeground(new Color(12, 138, 199));
		jSeparator_1_2.setBackground(new Color(12, 138, 199));
		jSeparator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(jSeparator_1_2);

		JPanel btnBuscar = new JPanel();
		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtBuscar.getText().equals("")) {
					preencherReservas();
					preencherHospedes();
				} else {
					pesquisarReservas(txtBuscar.getText());
					pesquisarHospedes(txtBuscar.getText());
					txtBuscar.setText("");
				}
			}
		});
		btnBuscar.setLayout(null);
		btnBuscar.setBackground(new Color(12, 138, 199));
		btnBuscar.setBounds(748, 125, 122, 35);
		btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnBuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnBuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int reservasSelecionadas = tbReservas.getSelectedRow();
				int hoespedesSelecionados = tbHospedes.getSelectedRow();

				if (reservasSelecionadas >= 0) {
					try {
						editarReserva();
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} finally {
						preencherReservas();
					}
				} else if (hoespedesSelecionados >= 0) {
					try {
						editarHospede();
					} catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					} finally {
						preencherHospedes();
					}
				}
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnDeletar = new JPanel();
		btnDeletar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int reservasSelecionadas = tbReservas.getSelectedRow();
				int hospedesSelecionados = tbHospedes.getSelectedRow();

				if (reservasSelecionadas >= 0) {
					int confirmar = JOptionPane.showConfirmDialog(null, "Deseja mesmo deletar os dados?");
					if (confirmar == JOptionPane.YES_OPTION) {
						Object selecionado = (Object) modelo.getValueAt(tbReservas.getSelectedRow(),
								tbReservas.getSelectedColumn());
						Integer id = (Integer) selecionado;
						try {
							reservaController.deletar(id);
							JOptionPane.showMessageDialog(null, "Item excluído com sucesso!");
							preencherReservas();
						}catch(Exception ex) {
							JOptionPane.showMessageDialog(
									null, "Você possui um hospede cadastrado para essa reserva. Se deseja continuar, deverá excluí-lo primeiro.");
						}
					}
				}else if(hospedesSelecionados >= 0) {
					Object selecionado = (Object) modeloHospedes.getValueAt(
							tbHospedes.getSelectedRow(), tbHospedes.getSelectedColumn());
					if(selecionado instanceof Integer) {
						Integer id = (Integer) selecionado;
						hospedeController.deletar(id);
						JOptionPane.showMessageDialog(null, "Item excluído com sucesso!");
						preencherHospedes();
					}else {
						JOptionPane.showMessageDialog(null, "Por favor, selecionar o ID");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Por favor, selecionar o ID");
				}
			}
		});
		btnDeletar.setLayout(null);
		btnDeletar.setBackground(new Color(12, 138, 199));
		btnDeletar.setBounds(767, 508, 122, 35);
		btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnDeletar);

		JLabel lblExcluir = new JLabel("DELETAR");
		lblExcluir.setHorizontalAlignment(SwingConstants.CENTER);
		lblExcluir.setForeground(Color.WHITE);
		lblExcluir.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblExcluir.setBounds(0, 0, 122, 35);
		btnDeletar.add(lblExcluir);
		setResizable(false);
	}

	private void limparTabelaReservas() {
		modelo.getDataVector().clear();
		tbReservas.updateUI();
	}

	private void limparTabelaHospedes() {
		modeloHospedes.getDataVector().clear();
		tbHospedes.updateUI();
	}

	private void preencherReservas() {
		try {
			limparTabelaReservas();
			List<Reserva> reservas = listaReservas();
			for (Reserva reserva : reservas) {
				modelo.addRow(new Object[] { reserva.getId(), dtf1.format(reserva.getDataEntrada()),
						dtf1.format(reserva.getDataSaida()), reserva.getValor(getLocale()),
						reserva.getFormaDePagamento() });
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<Reserva> listaReservas() {
		return this.reservaController.listar();
	}

	private void editarReserva() {
		Object selecionado = (Object) modelo.getValueAt(tbReservas.getSelectedRow(), 0);
		if (selecionado instanceof Integer) {
			String dataEntrada = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 1);
			String dataSaida = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 2);
			String formaPagamento = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
			Double valor = Reserva.valor(LocalDate.parse(dataEntrada, dtf1), LocalDate.parse(dataSaida, dtf1));
			Reserva reserva = new Reserva((Integer) selecionado, LocalDate.parse(dataEntrada, dtf1),
					LocalDate.parse(dataSaida, dtf1), valor, formaPagamento);
			this.reservaController.alterar(reserva);
			JOptionPane.showMessageDialog(this, "Item editado com sucesso!");
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
		}
	}

	private void pesquisarReservas(String pesquisa) {
		try {
			limparTabelaReservas();
			List<Reserva> reservas = this.reservaController.pesquisar(pesquisa);
			reservas.forEach(reserva -> {
				modelo.addRow(new Object[] { reserva.getId(), reserva.getDataEntrada(), reserva.getDataSaida(),
						reserva.getValor(), reserva.getFormaDePagamento() });
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void preencherHospedes() {
		try {
			limparTabelaHospedes();
			List<Hospede> hospedes = listaHospedes();
			hospedes.forEach(hospede -> {
				modeloHospedes.addRow(new Object[] { hospede.getId(), hospede.getNome(), hospede.getSobrenome(),
						dtf1.format(hospede.getDataNascimento()), hospede.getNacionalidade(), hospede.getTelefone(),
						hospede.getIdReserva() });
			});
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private List<Hospede> listaHospedes() {
		return this.hospedeController.listar();
	}

	private void editarHospede() {
		Object selecionado = (Object) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 0);
		if (selecionado instanceof Integer) {
			String nome = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 1);
			String sobrenome = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 2);
			String dataNascimento = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 3);
			String nacionalidade = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 4);
			String telefone = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 5);
			Integer idReserva = (Integer) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 6);
			Hospede hospede = new Hospede((Integer) selecionado, nome, sobrenome, LocalDate.parse(dataNascimento, dtf1),
					nacionalidade, telefone, idReserva);

			this.hospedeController.alterar(hospede);
			JOptionPane.showMessageDialog(this, "Item editado com sucesso!");
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID");
		}
	}

	private void pesquisarHospedes(String pesquisa) {
		try {
			limparTabelaHospedes();
			List<Hospede> hospedes = this.hospedeController.pesquisar(pesquisa);
			hospedes.forEach(hospede -> {
				modeloHospedes.addRow(new Object[] { hospede.getId(), hospede.getNome(), hospede.getSobrenome(),
						hospede.getDataNascimento(), hospede.getNacionalidade(), hospede.getTelefone(),
						hospede.getIdReserva() });
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void headerMousePressed(MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}
