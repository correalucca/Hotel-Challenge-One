package br.com.alura.hotel.views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

import br.com.alura.hotel.jdbc.controller.ReservaController;
import br.com.alura.hotel.jdbc.model.Reserva;

@SuppressWarnings("serial")
public class ReservasView extends JFrame {
	private JPanel contentPane;
	public static JTextField txtValor;
	public static JDateChooser txtDataE;
	public static JDateChooser txtDataS;
	public static JComboBox<String> txtFormaPagamento;
	int xMouse, yMouse;
	private JLabel labelExit;
	private JLabel lblValorSimbolo;
	private JLabel labelAtras;
	private ReservaController reservaController;
	DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservasView frame = new ReservasView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ReservasView() {
		super("Reserva");

		this.reservaController = new ReservaController();

		setIconImage(Toolkit.getDefaultToolkit().getImage(ReservasView.class.getResource("/imagens/logo-hotel-40px.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 560);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 910, 560);
		contentPane.add(panel);
		panel.setLayout(null);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(SystemColor.textHighlight);
		separator_1_2.setBounds(68, 195, 289, 2);
		separator_1_2.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_2);

		JSeparator separator_1_3 = new JSeparator();
		separator_1_3.setForeground(SystemColor.textHighlight);
		separator_1_3.setBackground(SystemColor.textHighlight);
		separator_1_3.setBounds(68, 453, 289, 2);
		panel.add(separator_1_3);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(SystemColor.textHighlight);
		separator_1_1.setBounds(68, 281, 289, 11);
		separator_1_1.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_1);

		txtDataE = new JDateChooser();
		txtDataE.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtDataE.getCalendarButton()
				.setIcon(new ImageIcon(ReservasView.class.getResource("/imagens/icon-reservas.png")));
		txtDataE.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
		txtDataE.setBounds(68, 161, 289, 35);
		txtDataE.getCalendarButton().setBounds(268, 0, 21, 33);
		txtDataE.setBackground(Color.WHITE);
		txtDataE.setBorder(new LineBorder(SystemColor.window));
		txtDataE.setDateFormatString("yyyy-MM-dd");
		txtDataE.setFont(new Font("Roboto", Font.PLAIN, 18));
		panel.add(txtDataE);
		
		txtDataE.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (txtDataE.getDate() != null && txtDataS.getDate() != null) {
					try {
						valorDiaria();
					} catch (IllegalArgumentException e) {
						txtValor.setText("");
						JOptionPane.showMessageDialog(null, e.getMessage());
					} catch (NullPointerException ex) {
						txtValor.setText("");
						JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
					}
				}
			}
		});

		lblValorSimbolo = new JLabel("R$");
		lblValorSimbolo.setVisible(false);
		lblValorSimbolo.setBounds(121, 332, 17, 25);
		lblValorSimbolo.setForeground(SystemColor.textHighlight);
		lblValorSimbolo.setFont(new Font("Roboto", Font.BOLD, 17));
		panel.add(lblValorSimbolo);

		JLabel lblCheckIn = new JLabel("DATA DE CHECK IN");
		lblCheckIn.setForeground(SystemColor.textInactiveText);
		lblCheckIn.setBounds(68, 136, 169, 14);
		lblCheckIn.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblCheckIn);

		JLabel lblCheckOut = new JLabel("DATA DE CHECK OUT");
		lblCheckOut.setForeground(SystemColor.textInactiveText);
		lblCheckOut.setBounds(68, 221, 200, 14);
		lblCheckOut.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblCheckOut);

		txtDataS = new JDateChooser();
		txtDataS.getCalendarButton()
				.setIcon(new ImageIcon(ReservasView.class.getResource("/imagens/icon-reservas.png")));
		txtDataS.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 11));
		txtDataS.setBounds(68, 246, 289, 35);
		txtDataS.getCalendarButton().setBounds(267, 1, 21, 31);
		txtDataS.setBackground(Color.WHITE);
		txtDataS.setFont(new Font("Roboto", Font.PLAIN, 18));
		txtDataS.setDateFormatString("yyyy-MM-dd");
		txtDataS.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtDataS.setBorder(new LineBorder(new Color(255, 255, 255), 0));
		panel.add(txtDataS);

		txtValor = new JTextField();
		txtValor.setBackground(SystemColor.text);
		txtValor.setHorizontalAlignment(SwingConstants.CENTER);
		txtValor.setForeground(Color.BLACK);
		txtValor.setBounds(55, 328, 200, 33);
		txtValor.setEditable(false);
		txtValor.setFont(new Font("Roboto Black", Font.BOLD, 17));
		txtValor.setBorder(BorderFactory.createEmptyBorder());
		panel.add(txtValor);
		txtValor.setColumns(20);

		txtDataS.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (txtDataE.getDate() != null && txtDataS.getDate() != null) {
					try {
						valorDiaria();
					} catch (IllegalArgumentException e) {
						txtValor.setText("");
						JOptionPane.showMessageDialog(null, e.getMessage());
					} catch (NullPointerException ex) {
						txtValor.setText("");
						JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
					}
				}
			}
		});

		JLabel lblValor = new JLabel("VALOR DA RESERVA");
		lblValor.setForeground(SystemColor.textInactiveText);
		lblValor.setBounds(72, 303, 196, 14);
		lblValor.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblValor);

		txtFormaPagamento = new JComboBox<String>();
		txtFormaPagamento.setBounds(68, 417, 289, 38);
		txtFormaPagamento.setBackground(SystemColor.text);
		txtFormaPagamento.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		txtFormaPagamento.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtFormaPagamento.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Cartão de Crédito", "Cartão de Débito", "Dinheiro" }));
		panel.add(txtFormaPagamento);

		JLabel lblFormaPago = new JLabel("FORMA DE PAGAMENTO");
		lblFormaPago.setForeground(SystemColor.textInactiveText);
		lblFormaPago.setBounds(68, 382, 213, 24);
		lblFormaPago.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblFormaPago);

		JLabel lblTitulo = new JLabel("SISTEMA DE RESERVAS");
		lblTitulo.setBounds(109, 60, 219, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto", Font.BOLD, 20));
		panel.add(lblTitulo);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(428, 0, 482, 560);
		panel_1.setBackground(new Color(12, 138, 199));
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel logo = new JLabel("");
		logo.setBounds(197, 68, 104, 107);
		panel_1.add(logo);
		logo.setIcon(new ImageIcon(ReservasView.class.getResource("/imagens/logo-hotel-100px.png")));

		JLabel imagenFundo = new JLabel("");
		imagenFundo.setBounds(0, 140, 500, 409);
		panel_1.add(imagenFundo);
		imagenFundo.setBackground(Color.WHITE);
		imagenFundo.setIcon(new ImageIcon(ReservasView.class.getResource("/imagens/reservas-img-3.png")));

		JPanel btnExit = new JPanel();
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuPrincipal principal = new MenuPrincipal();
				principal.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setBackground(new Color(12, 138, 199));
				labelExit.setForeground(Color.white);
			}
		});
		btnExit.setLayout(null);
		btnExit.setBackground(new Color(12, 138, 199));
		btnExit.setBounds(429, 0, 53, 36);
		panel_1.add(btnExit);

		labelExit = new JLabel("X");
		labelExit.setForeground(Color.WHITE);
		labelExit.setBounds(0, 0, 53, 36);
		btnExit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
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
		panel.add(header);

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
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.textHighlight);
		separator_1.setBounds(68, 362, 289, 2);
		separator_1.setBackground(SystemColor.textHighlight);
		panel.add(separator_1);

		JPanel btnProximo = new JPanel();
		btnProximo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Reserva reserva = salvar();
					RegistroHospede registro = new RegistroHospede(reserva);
					registro.setVisible(true);
					dispose();
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
				}
			}
		});
		btnProximo.setLayout(null);
		btnProximo.setBackground(SystemColor.textHighlight);
		btnProximo.setBounds(238, 493, 122, 35);
		panel.add(btnProximo);
		btnProximo.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JLabel lblSeguinte = new JLabel("PRÓXIMO");
		lblSeguinte.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeguinte.setForeground(Color.WHITE);
		lblSeguinte.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblSeguinte.setBounds(0, 0, 122, 35);
		btnProximo.add(lblSeguinte);
	}

	private Reserva salvar() {
		LocalDate dataE = txtDataE.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dataS = txtDataS.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Reserva reserva = new Reserva(dataE, dataS, Double.parseDouble(txtValor.getText()),
				(String) txtFormaPagamento.getSelectedItem());
		reservaController.salvar(reserva);
		return reserva;
	}

	private void valorDiaria() {
		LocalDate dataE = txtDataE.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dataS = txtDataS.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		txtValor.setText(String.valueOf(Reserva.valor(dataE, dataS)));
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