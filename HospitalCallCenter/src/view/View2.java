package view;

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
import javax.swing.JTable;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class View2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel LoginPanel;
	private JTextField userField;
	private JPasswordField passwordField;
	private JTextField textField;
	private JTextField textField_2;
	private JPasswordField passwordField_1;
	private JTable UserTable;
	private JTextField idDoctorField;
	private JTextField specialtyDoctorField;
	private JTextField nameDoctorField;
	private JTable table;
	private JTextField idPatientField;
	private JTextField birthDateField;
	private JTextField patientNameField;
	private JTextField contactField;
	private JTextField adressField;
	private JTable table_1;
	private JTextField idAppointmentField;
	private JTextField doctorAppointmentField;
	private JTextField patientApppointmentField;
	private JTextField timeField;
	private JTextField dateAppointmentField;
	private JTextField textField_1;
	private JTextField reasonField;
	private JTextField textField_3;
	private JTable table_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View2 frame = new View2();
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
	public View2() {
		setTitle("CallCenterHospital");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
//		setBounds(100, 100, 233, 438); ventana pequeña
		LoginPanel = new JPanel();
		LoginPanel.setBackground(new Color(102, 204, 255));
		LoginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(LoginPanel);
		LoginPanel.setLayout(null);
		
		JPanel AppointmentsPanel = new JPanel();
		AppointmentsPanel.setLayout(null);
		AppointmentsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		AppointmentsPanel.setBackground(new Color(102, 204, 255));
		AppointmentsPanel.setBounds(0, 0, 1184, 661);
		LoginPanel.add(AppointmentsPanel);
		
		JButton addAppointmentButton = new JButton("Agregar Cita");
		addAppointmentButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addAppointmentButton.setBounds(17, 69, 185, 50);
		AppointmentsPanel.add(addAppointmentButton);
		
		JButton modifyAppointmentButton = new JButton("Modificar Cita");
		modifyAppointmentButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		modifyAppointmentButton.setBounds(17, 151, 185, 50);
		AppointmentsPanel.add(modifyAppointmentButton);
		
		JButton CancelAppointmentButton = new JButton("Eliminar Cita");
		CancelAppointmentButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		CancelAppointmentButton.setBounds(17, 232, 185, 57);
		AppointmentsPanel.add(CancelAppointmentButton);
		
		JButton showAppointmentsButton = new JButton("Ver Citas");
		showAppointmentsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showAppointmentsButton.setBounds(17, 314, 185, 50);
		AppointmentsPanel.add(showAppointmentsButton);
		
		JButton AppointmentExitButton = new JButton("Salir");
		AppointmentExitButton.setBounds(116, 548, 87, 29);
		AppointmentsPanel.add(AppointmentExitButton);
		
		JButton AppointmentMenuButton = new JButton("Menu");
		AppointmentMenuButton.setBounds(17, 548, 87, 29);
		AppointmentsPanel.add(AppointmentMenuButton);
		
		JButton AppointmentBackLoginButton = new JButton("Cerrar Sesion");
		AppointmentBackLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		AppointmentBackLoginButton.setBounds(41, 480, 138, 44);
		AppointmentsPanel.add(AppointmentBackLoginButton);
		
		JLabel idAppointmentLabel = new JLabel("Id Cita");
		idAppointmentLabel.setBounds(219, 75, 152, 22);
		AppointmentsPanel.add(idAppointmentLabel);
		
		idAppointmentField = new JTextField();
		idAppointmentField.setColumns(10);
		idAppointmentField.setBounds(219, 97, 152, 28);
		AppointmentsPanel.add(idAppointmentField);
		
		JLabel patientAppointmentLabel = new JLabel("Nombre Paciente");
		patientAppointmentLabel.setBounds(219, 137, 152, 22);
		AppointmentsPanel.add(patientAppointmentLabel);
		
		doctorAppointmentField = new JTextField();
		doctorAppointmentField.setColumns(10);
		doctorAppointmentField.setBounds(219, 221, 152, 28);
		AppointmentsPanel.add(doctorAppointmentField);
		
		JLabel doctorAppointmentLabel = new JLabel("Doctor");
		doctorAppointmentLabel.setBounds(219, 199, 152, 22);
		AppointmentsPanel.add(doctorAppointmentLabel);
		
		JScrollPane scrollPaneAppointment = new JScrollPane();
		scrollPaneAppointment.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneAppointment.setBounds(396, 48, 782, 586);
		AppointmentsPanel.add(scrollPaneAppointment);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"Id Cita", "Paciente", "Doctor", "Fecha de Cita", "Hora de Cita", "Motivo", "Especialidad"
			}
		));
		scrollPaneAppointment.setViewportView(table_2);
		
		patientApppointmentField = new JTextField();
		patientApppointmentField.setColumns(10);
		patientApppointmentField.setBounds(219, 154, 152, 28);
		AppointmentsPanel.add(patientApppointmentField);
		
		timeField = new JTextField();
		timeField.setColumns(10);
		timeField.setBounds(219, 352, 152, 28);
		AppointmentsPanel.add(timeField);
		
		JLabel timeLabel = new JLabel("Hora Cita");
		timeLabel.setBounds(219, 330, 152, 22);
		AppointmentsPanel.add(timeLabel);
		
		dateAppointmentField = new JTextField();
		dateAppointmentField.setColumns(10);
		dateAppointmentField.setBounds(219, 287, 152, 28);
		AppointmentsPanel.add(dateAppointmentField);
		
		JLabel DateAppointmentLabel = new JLabel("Fecha Cita");
		DateAppointmentLabel.setBounds(219, 270, 152, 22);
		AppointmentsPanel.add(DateAppointmentLabel);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(219, 484, 152, 28);
		AppointmentsPanel.add(textField_1);
		
		JLabel SpecialtyLabel = new JLabel("Especialidad");
		SpecialtyLabel.setBounds(219, 462, 152, 22);
		AppointmentsPanel.add(SpecialtyLabel);
		
		reasonField = new JTextField();
		reasonField.setColumns(10);
		reasonField.setBounds(219, 419, 152, 28);
		AppointmentsPanel.add(reasonField);
		
		JLabel reasonAppointmentLabel = new JLabel("Motivo");
		reasonAppointmentLabel.setBounds(219, 402, 152, 22);
		AppointmentsPanel.add(reasonAppointmentLabel);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(27, 398, 152, 28);
		AppointmentsPanel.add(textField_3);
		
		JLabel SpecialtyFilterLabel = new JLabel("Filtro de Especialidad");
		SpecialtyFilterLabel.setBounds(27, 376, 152, 22);
		AppointmentsPanel.add(SpecialtyFilterLabel);
		
		JPanel PatientsPanel = new JPanel();
		PatientsPanel.setLayout(null);
		PatientsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		PatientsPanel.setBackground(new Color(102, 204, 255));
		PatientsPanel.setBounds(0, 0, 1184, 661);
		LoginPanel.add(PatientsPanel);
		
		JButton addPatientsButton = new JButton("Agregar Paciente");
		addPatientsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addPatientsButton.setBounds(17, 69, 185, 50);
		PatientsPanel.add(addPatientsButton);
		
		JButton modifyPatientButton = new JButton("Modificar Paciente");
		modifyPatientButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		modifyPatientButton.setBounds(17, 151, 185, 50);
		PatientsPanel.add(modifyPatientButton);
		
		JButton deletePatientButton = new JButton("Eliminar Paciente");
		deletePatientButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		deletePatientButton.setBounds(17, 232, 185, 57);
		PatientsPanel.add(deletePatientButton);
		
		JButton showPatientsButton = new JButton("Ver Pacientes");
		showPatientsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showPatientsButton.setBounds(17, 314, 185, 50);
		PatientsPanel.add(showPatientsButton);
		
		JButton PatientExitButton = new JButton("Salir");
		PatientExitButton.setBounds(116, 548, 87, 29);
		PatientsPanel.add(PatientExitButton);
		
		JButton PatientMenuButton = new JButton("Menu");
		PatientMenuButton.setBounds(17, 548, 87, 29);
		PatientsPanel.add(PatientMenuButton);
		
		JButton PatientBackLoginButton = new JButton("Cerrar Sesion");
		PatientBackLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		PatientBackLoginButton.setBounds(41, 480, 138, 44);
		PatientsPanel.add(PatientBackLoginButton);
		
		JLabel idPatientLabel = new JLabel("Cedula");
		idPatientLabel.setBounds(219, 168, 152, 22);
		PatientsPanel.add(idPatientLabel);
		
		idPatientField = new JTextField();
		idPatientField.setColumns(10);
		idPatientField.setBounds(219, 190, 152, 28);
		PatientsPanel.add(idPatientField);
		
		JLabel patientNameLabel = new JLabel("Nombre");
		patientNameLabel.setBounds(219, 230, 152, 22);
		PatientsPanel.add(patientNameLabel);
		
		birthDateField = new JTextField();
		birthDateField.setColumns(10);
		birthDateField.setBounds(219, 312, 152, 28);
		PatientsPanel.add(birthDateField);
		
		JLabel birthDateLabel = new JLabel("Fecha de Nacimiento");
		birthDateLabel.setBounds(219, 290, 152, 22);
		PatientsPanel.add(birthDateLabel);
		
		JScrollPane scrollPanePatient = new JScrollPane();
		scrollPanePatient.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanePatient.setBounds(396, 48, 782, 586);
		PatientsPanel.add(scrollPanePatient);
		
		table_1 = new JTable();
		table_1.setShowVerticalLines(true);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Cedula", "Nombre", "Fecha de Nacimiento", "Direccion", "Contacto"
			}
		));
		scrollPanePatient.setViewportView(table_1);
		
		patientNameField = new JTextField();
		patientNameField.setColumns(10);
		patientNameField.setBounds(219, 247, 152, 28);
		PatientsPanel.add(patientNameField);
		
		contactField = new JTextField();
		contactField.setColumns(10);
		contactField.setBounds(219, 443, 152, 28);
		PatientsPanel.add(contactField);
		
		JLabel contactLabel = new JLabel("Contacto");
		contactLabel.setBounds(219, 421, 152, 22);
		PatientsPanel.add(contactLabel);
		
		adressField = new JTextField();
		adressField.setColumns(10);
		adressField.setBounds(219, 378, 152, 28);
		PatientsPanel.add(adressField);
		
		JLabel patientAdressLabel = new JLabel("Direccion");
		patientAdressLabel.setBounds(219, 361, 152, 22);
		PatientsPanel.add(patientAdressLabel);
		
		JPanel DoctorsPanel = new JPanel();
		DoctorsPanel.setLayout(null);
		DoctorsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		DoctorsPanel.setBackground(new Color(102, 204, 255));
		DoctorsPanel.setBounds(0, 0, 1184, 661);
		LoginPanel.add(DoctorsPanel);
		
		JButton addDoctorButton = new JButton("Agregar Doctor");
		addDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addDoctorButton.setBounds(17, 69, 185, 50);
		DoctorsPanel.add(addDoctorButton);
		
		JButton modifyDoctorButton = new JButton("Modificar Doctor");
		modifyDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		modifyDoctorButton.setBounds(17, 151, 185, 50);
		DoctorsPanel.add(modifyDoctorButton);
		
		JButton removeDoctorButton = new JButton("Eliminar Doctor");
		removeDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		removeDoctorButton.setBounds(17, 232, 185, 57);
		DoctorsPanel.add(removeDoctorButton);
		
		JButton showDoctorsButton = new JButton("Ver Doctores");
		showDoctorsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showDoctorsButton.setBounds(17, 314, 185, 50);
		DoctorsPanel.add(showDoctorsButton);
		
		JButton DoctorExitButton = new JButton("Salir");
		DoctorExitButton.setBounds(116, 548, 87, 29);
		DoctorsPanel.add(DoctorExitButton);
		
		JButton DoctorMenuButton = new JButton("Menu");
		DoctorMenuButton.setBounds(17, 548, 87, 29);
		DoctorsPanel.add(DoctorMenuButton);
		
		JButton DoctorBackLoginButton = new JButton("Cerrar Sesion");
		DoctorBackLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		DoctorBackLoginButton.setBounds(41, 480, 138, 44);
		DoctorsPanel.add(DoctorBackLoginButton);
		
		JLabel idDoctorLabel = new JLabel("Id");
		idDoctorLabel.setBounds(219, 168, 152, 22);
		DoctorsPanel.add(idDoctorLabel);
		
		idDoctorField = new JTextField();
		idDoctorField.setColumns(10);
		idDoctorField.setBounds(219, 190, 152, 28);
		DoctorsPanel.add(idDoctorField);
		
		JLabel doctorNameLabel = new JLabel("Nombre");
		doctorNameLabel.setBounds(219, 230, 152, 22);
		DoctorsPanel.add(doctorNameLabel);
		
		specialtyDoctorField = new JTextField();
		specialtyDoctorField.setColumns(10);
		specialtyDoctorField.setBounds(219, 312, 152, 28);
		DoctorsPanel.add(specialtyDoctorField);
		
		JLabel specialtyDoctorLabel = new JLabel("Especialidad");
		specialtyDoctorLabel.setBounds(219, 290, 152, 22);
		DoctorsPanel.add(specialtyDoctorLabel);
		
		JScrollPane scrollPaneDoctors = new JScrollPane();
		scrollPaneDoctors.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneDoctors.setBounds(396, 48, 782, 586);
		DoctorsPanel.add(scrollPaneDoctors);
		
		table = new JTable();
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Id", "Nombre", "Especialidad"
			}
		));
		scrollPaneDoctors.setViewportView(table);
		
		nameDoctorField = new JTextField();
		nameDoctorField.setColumns(10);
		nameDoctorField.setBounds(219, 247, 152, 28);
		DoctorsPanel.add(nameDoctorField);
		
		JPanel usersPanel = new JPanel();
		usersPanel.setLayout(null);
		usersPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		usersPanel.setBackground(new Color(102, 204, 255));
		usersPanel.setBounds(0, 0, 1184, 661);
		LoginPanel.add(usersPanel);
		
		JButton addUserButton = new JButton("Agregar Usuario");
		addUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addUserButton.setBounds(17, 69, 185, 50);
		usersPanel.add(addUserButton);
		
		JButton modifyUserButton = new JButton("Modificar Usuario");
		modifyUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		modifyUserButton.setBounds(17, 151, 185, 50);
		usersPanel.add(modifyUserButton);
		
		JButton removeUserButton = new JButton("Eliminar Usuario");
		removeUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		removeUserButton.setBounds(17, 232, 185, 57);
		usersPanel.add(removeUserButton);
		
		JButton showUsersButton = new JButton("Ver Usuarios");
		showUsersButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		showUsersButton.setBounds(17, 314, 185, 50);
		usersPanel.add(showUsersButton);
		
		JButton UserExitButton = new JButton("Salir");
		UserExitButton.setBounds(116, 548, 87, 29);
		usersPanel.add(UserExitButton);
		
		JButton UserMenuButton = new JButton("Menu");
		UserMenuButton.setBounds(17, 548, 87, 29);
		usersPanel.add(UserMenuButton);
		
		JButton UserBackLoginButton = new JButton("Cerrar Sesion");
		UserBackLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
		UserBackLoginButton.setBounds(41, 480, 138, 44);
		usersPanel.add(UserBackLoginButton);
		
		JLabel nameUserLabel = new JLabel("Usuario");
		nameUserLabel.setBounds(219, 168, 152, 22);
		usersPanel.add(nameUserLabel);
		
		textField = new JTextField();
		textField.setBounds(219, 190, 152, 28);
		usersPanel.add(textField);
		textField.setColumns(10);
		
		JLabel passwordUserLabel = new JLabel("Contraseña");
		passwordUserLabel.setBounds(219, 230, 152, 22);
		usersPanel.add(passwordUserLabel);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(219, 312, 152, 28);
		usersPanel.add(textField_2);
		
		JLabel rolUserLabel = new JLabel("Rol");
		rolUserLabel.setBounds(219, 290, 152, 22);
		usersPanel.add(rolUserLabel);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(219, 250, 152, 28);
		usersPanel.add(passwordField_1);
		
		JScrollPane scrollPaneUser = new JScrollPane();
		scrollPaneUser.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneUser.setBounds(396, 48, 782, 586);
		usersPanel.add(scrollPaneUser);
		
		UserTable = new JTable();
		UserTable.setShowVerticalLines(true);
		UserTable.setShowHorizontalLines(true);
		UserTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Usuario", "Contrase\u00F1a", "Rol"
			}
		));
		scrollPaneUser.setViewportView(UserTable);
		
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
