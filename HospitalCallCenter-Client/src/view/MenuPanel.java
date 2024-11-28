package view;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton appointmentButton;
	private JButton patientButton;
	private JButton doctorButton;
	private JButton usersButton;
	private JButton logoutButton;
	private JButton exitButton;

	public MenuPanel(View view) {
		setLayout(null);
		setBackground(new Color(135, 206, 235));
		setBounds(0, 0, 217, 700);

		appointmentButton = new JButton("Citas");
		appointmentButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		appointmentButton.setBounds(17, 48, 185, 29);
		add(appointmentButton);

		patientButton = new JButton("Pacientes");
		patientButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		patientButton.setBounds(17, 115, 185, 29);
		add(patientButton);

		doctorButton = new JButton("Doctores");
		doctorButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		doctorButton.setBounds(17, 176, 185, 29);
		add(doctorButton);

		usersButton = new JButton("Usuarios");
		usersButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		usersButton.setBounds(17, 237, 185, 29);
		add(usersButton);

		logoutButton = new JButton("Cerrar Sesión");
		logoutButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		logoutButton.setBounds(39, 321, 138, 29);
		logoutButton.addActionListener(e -> view.showPanel("loginPanel"));
		add(logoutButton);

		exitButton = new JButton("Salir");
		exitButton.setBounds(124, 364, 87, 29);
		exitButton.addActionListener(e -> System.exit(0));
		add(exitButton);
	}

	public JButton getAppointmentButton() {
		return appointmentButton;
	}

	public JButton getPatientButton() {
		return patientButton;
	}

	public JButton getDoctorButton() {
		return doctorButton;
	}

	public JButton getUsersButton() {
		return usersButton;
	}

	public JButton getLogoutButton() {
		return logoutButton;
	}

	public JButton getExitButton() {
		return exitButton;
	}
}
