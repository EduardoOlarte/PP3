package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JPasswordField;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel LoginPanel;
	private JTextField userField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public View() {
		setTitle("CallCenterHospital");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 233, 438);
//		setBounds(100, 100, 233, 438); ventana pequeña
		LoginPanel = new JPanel();
		LoginPanel.setBackground(new Color(102, 204, 255));
		LoginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(LoginPanel);
		LoginPanel.setLayout(null);
		
		JPanel usersPanel = new JPanel();
		usersPanel.setLayout(null);
		usersPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		usersPanel.setBackground(new Color(102, 204, 255));
		usersPanel.setBounds(0, 0, 217, 399);
		LoginPanel.add(usersPanel);
		
		JButton UserExitButton = new JButton("Salir");
		UserExitButton.setBounds(124, 364, 87, 29);
		usersPanel.add(UserExitButton);
		
		JButton addUserButton = new JButton("Agregar Usuario");
		addUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addUserButton.setBounds(17, 48, 185, 29);
		usersPanel.add(addUserButton);
		
		JButton modifyUserButton = new JButton("Modificar Usuario");
		modifyUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		modifyUserButton.setBounds(17, 97, 185, 29);
		usersPanel.add(modifyUserButton);
		
		JButton removeUserButton = new JButton("Eliminar Usuario");
		removeUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		removeUserButton.setBounds(17, 147, 185, 29);
		usersPanel.add(removeUserButton);
		
		JButton showUsersButton = new JButton("Ver Usuarios");
		showUsersButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showUsersButton.setBounds(17, 196, 185, 29);
		usersPanel.add(showUsersButton);
		
		JButton UserBackLoginButton = new JButton("Cerrar Sesion");
		UserBackLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		UserBackLoginButton.setBounds(39, 307, 138, 29);
		usersPanel.add(UserBackLoginButton);
		
		JButton showUserButton = new JButton("Ver Usuario");
		showUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showUserButton.setBounds(17, 245, 185, 29);
		usersPanel.add(showUserButton);
		
		JPanel DoctorsPanel = new JPanel();
		DoctorsPanel.setLayout(null);
		DoctorsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		DoctorsPanel.setBackground(new Color(102, 204, 255));
		DoctorsPanel.setBounds(0, 0, 217, 399);
		LoginPanel.add(DoctorsPanel);
		
		JButton doctorsExitButton = new JButton("Salir");
		doctorsExitButton.setBounds(124, 364, 87, 29);
		DoctorsPanel.add(doctorsExitButton);
		
		JButton addDoctorButton = new JButton("Agregar Doctor");
		addDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addDoctorButton.setBounds(17, 48, 185, 29);
		DoctorsPanel.add(addDoctorButton);
		
		JButton modifyDoctorButton = new JButton("Modificar Doctor");
		modifyDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		modifyDoctorButton.setBounds(17, 97, 185, 29);
		DoctorsPanel.add(modifyDoctorButton);
		
		JButton removeDoctorButton = new JButton("Eliminar Doctor");
		removeDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		removeDoctorButton.setBounds(17, 147, 185, 29);
		DoctorsPanel.add(removeDoctorButton);
		
		JButton showDoctorsButton = new JButton("Ver Doctores");
		showDoctorsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showDoctorsButton.setBounds(17, 196, 185, 29);
		DoctorsPanel.add(showDoctorsButton);
		
		JButton doctorBackLoginButton = new JButton("Cerrar Sesion");
		doctorBackLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		doctorBackLoginButton.setBounds(39, 307, 138, 29);
		DoctorsPanel.add(doctorBackLoginButton);
		
		JButton showDoctorButton = new JButton("Ver Doctor");
		showDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showDoctorButton.setBounds(17, 245, 185, 29);
		DoctorsPanel.add(showDoctorButton);
		
		JPanel AppointmentPanel = new JPanel();
		AppointmentPanel.setLayout(null);
		AppointmentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		AppointmentPanel.setBackground(new Color(102, 204, 255));
		AppointmentPanel.setBounds(0, 0, 217, 399);
		LoginPanel.add(AppointmentPanel);
		
		JButton exitAppointmentButton = new JButton("Salir");
		exitAppointmentButton.setBounds(124, 364, 87, 29);
		AppointmentPanel.add(exitAppointmentButton);
		
		JButton addAppointmentButton = new JButton("Agendar Cita");
		addAppointmentButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addAppointmentButton.setBounds(17, 48, 185, 29);
		AppointmentPanel.add(addAppointmentButton);
		
		JButton modifyAppointmentButton = new JButton("Modificar Cita");
		modifyAppointmentButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		modifyAppointmentButton.setBounds(17, 97, 185, 29);
		AppointmentPanel.add(modifyAppointmentButton);
		
		JButton removeAppointmentButton = new JButton("Cancelar Cita");
		removeAppointmentButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		removeAppointmentButton.setBounds(17, 147, 185, 29);
		AppointmentPanel.add(removeAppointmentButton);
		
		JButton showAppointmentsButton = new JButton("Ver Citas");
		showAppointmentsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showAppointmentsButton.setBounds(17, 196, 185, 29);
		AppointmentPanel.add(showAppointmentsButton);
		
		JButton backLoginAppointmentButton = new JButton("Cerrar Sesion");
		backLoginAppointmentButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		backLoginAppointmentButton.setBounds(39, 307, 138, 29);
		AppointmentPanel.add(backLoginAppointmentButton);
		
		JButton showAppointmentButton = new JButton("Ver Cita");
		showAppointmentButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showAppointmentButton.setBounds(17, 245, 185, 29);
		AppointmentPanel.add(showAppointmentButton);
		
		JPanel PatientPanel = new JPanel();
		PatientPanel.setLayout(null);
		PatientPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		PatientPanel.setBackground(new Color(102, 204, 255));
		PatientPanel.setBounds(0, 0, 217, 399);
		LoginPanel.add(PatientPanel);
		
		JButton patietnExitButton = new JButton("Salir");
		patietnExitButton.setBounds(124, 364, 87, 29);
		PatientPanel.add(patietnExitButton);
		
		JButton addPatientButton = new JButton("Agregar Paciente");
		addPatientButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addPatientButton.setBounds(17, 48, 185, 29);
		PatientPanel.add(addPatientButton);
		
		JButton modifyPatientButton = new JButton("Modificar Paciente");
		modifyPatientButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		modifyPatientButton.setBounds(17, 97, 185, 29);
		PatientPanel.add(modifyPatientButton);
		
		JButton removePatientButton = new JButton("Eliminar Paciente");
		removePatientButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		removePatientButton.setBounds(17, 147, 185, 29);
		PatientPanel.add(removePatientButton);
		
		JButton showPatientsButton = new JButton("Ver Pacientes");
		showPatientsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showPatientsButton.setBounds(17, 196, 185, 29);
		PatientPanel.add(showPatientsButton);
		
		JButton PatietntBackLoginButton = new JButton("Cerrar Sesion");
		PatietntBackLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		PatietntBackLoginButton.setBounds(39, 307, 138, 29);
		PatientPanel.add(PatietntBackLoginButton);
		
		JButton showPatientButton = new JButton("Ver Paciente");
		showPatientButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showPatientButton.setBounds(17, 245, 185, 29);
		PatientPanel.add(showPatientButton);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		menuPanel.setBackground(new Color(102, 204, 255));
		menuPanel.setBounds(0, 0, 217, 399);
		LoginPanel.add(menuPanel);
		
		JButton exitButton_1 = new JButton("Salir");
		exitButton_1.setBounds(124, 364, 87, 29);
		menuPanel.add(exitButton_1);
		
		JButton appointmentButton = new JButton("Citas");
		appointmentButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		appointmentButton.setBounds(17, 48, 185, 29);
		menuPanel.add(appointmentButton);
		
		JButton patientButton = new JButton("Pacientes");
		patientButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		patientButton.setBounds(17, 115, 185, 29);
		menuPanel.add(patientButton);
		
		JButton doctorButton = new JButton("Doctores");
		doctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		doctorButton.setBounds(17, 176, 185, 29);
		menuPanel.add(doctorButton);
		
		JButton usersButton = new JButton("Usuarios");
		usersButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		usersButton.setBounds(17, 237, 185, 29);
		menuPanel.add(usersButton);
		
		JButton backLoginButton = new JButton("Cerrar Sesion");
		backLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		backLoginButton.setBounds(39, 321, 138, 29);
		menuPanel.add(backLoginButton);
		
		userField = new JTextField();
		userField.setBounds(17, 110, 185, 37);
		LoginPanel.add(userField);
		userField.setColumns(10);
		
		JLabel LoginLabel = new JLabel("Inicio de Sesion");
		LoginLabel.setBackground(new Color(255, 0, 0));
		LoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LoginLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LoginLabel.setBounds(17, 26, 185, 29);
		LoginPanel.add(LoginLabel);
		
		JLabel userLabel = new JLabel("Usuario");
		userLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userLabel.setBounds(17, 79, 185, 29);
		LoginPanel.add(userLabel);
		
		JLabel passwordLabel = new JLabel("Contraseña");
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordLabel.setBounds(17, 158, 185, 29);
		LoginPanel.add(passwordLabel);
		
		JButton loginButton = new JButton("Iniciar");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		loginButton.setBounds(17, 250, 185, 29);
		LoginPanel.add(loginButton);
		
		JButton exitButton = new JButton("Salir");
		exitButton.setBounds(115, 347, 87, 29);
		LoginPanel.add(exitButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(17, 183, 185, 37);
		LoginPanel.add(passwordField);
	}
}
