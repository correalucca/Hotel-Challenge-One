package br.com.alura.hotel.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MenuPrincipal extends JFrame {

	private JPanel contentPane;
	private JLabel labelExit;
	int xMouse, yMouse;

	public MenuPrincipal() {
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagens/logo-hotel-40px.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 910, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);

		Panel panel = new Panel();
		panel.setBackground(SystemColor.window);
		panel.setBounds(0, 0, 910, 537);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel imagenFundo = new JLabel("");
		imagenFundo.setBounds(-50, 0, 732, 501);
		imagenFundo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/menu-img.png")));
		panel.add(imagenFundo);

		JLabel logo = new JLabel("");
		logo.setBounds(722, 80, 150, 156);
		logo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/hotel-150px.png")));
		panel.add(logo);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 500, 910, 37);
		panel_1.setBackground(new Color(12, 138, 199));
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblCopyR = new JLabel("Desenvolvido por Lucas Corrêa © 2023");
		lblCopyR.setBounds(315, 11, 301, 19);
		lblCopyR.setForeground(new Color(240, 248, 255));
		lblCopyR.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel_1.add(lblCopyR);

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

		JPanel btnExit = new JPanel();
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int sair = JOptionPane.showConfirmDialog(null, "Deseja mesmo sair?", "Selecione uma das opções",
						JOptionPane.YES_NO_OPTION);
				if(sair == 0)
					System.exit(0);
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
		btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnExit.setBackground(Color.WHITE);
		btnExit.setBounds(857, 0, 53, 36);
		header.add(btnExit);

		labelExit = new JLabel("X");
		labelExit.setBounds(0, 0, 53, 36);
		btnExit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnLogin = new JPanel();
		btnLogin.setBounds(754, 300, 83, 70);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		btnLogin.setLayout(null);
		btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnLogin.setBackground(SystemColor.window);
		panel.add(btnLogin);

		JLabel imageLogin = new JLabel("");
		imageLogin.setBounds(0, 0, 80, 70);
		btnLogin.add(imageLogin);
		imageLogin.setHorizontalAlignment(SwingConstants.CENTER);
		imageLogin.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/login.png")));

		JLabel lblTitulo = new JLabel("LOGIN");
		lblTitulo.setBounds(754, 265, 83, 24);
		lblTitulo.setBackground(SystemColor.window);
		panel.add(lblTitulo);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setForeground(SystemColor.textHighlight);
		lblTitulo.setFont(new Font("Roboto Light", Font.PLAIN, 20));
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
