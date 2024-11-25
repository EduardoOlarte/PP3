package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PatientPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField idPatientField, namePatientField, birthDateField, contactField, addressField;
	private JTable patientTable;
	private JButton addPatientButton, modifyPatientButton, deletePatientButton, showPatientsButton, exitButton,
			menuButton, backLoginButton;

	public PatientPanel(View view) {
		setLayout(null);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(new Color(135, 206, 235));
		setBounds(0, 0, 1184, 661);

		// Botones
		addPatientButton = new JButton("Agregar Paciente");
		addPatientButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		addPatientButton.setBounds(17, 69, 185, 50);
		add(addPatientButton);

		modifyPatientButton = new JButton("Modificar Paciente");
		modifyPatientButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		modifyPatientButton.setBounds(17, 151, 185, 50);
		add(modifyPatientButton);

		deletePatientButton = new JButton("Eliminar Paciente");
		deletePatientButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		deletePatientButton.setBounds(17, 232, 185, 57);
		add(deletePatientButton);

		showPatientsButton = new JButton("Ver Pacientes");
		showPatientsButton.setFont(new Font("SansSerif", Font.BOLD, 16));
		showPatientsButton.setBounds(17, 314, 185, 50);
		add(showPatientsButton);

		exitButton = new JButton("Salir");
		exitButton.setBounds(116, 548, 87, 29);
		add(exitButton);
		exitButton.addActionListener(e -> System.exit(0));

		menuButton = new JButton("Menu");
		menuButton.setBounds(17, 548, 87, 29);
		menuButton.addActionListener(e -> view.showPanel("menuPanel")); // Acción para mostrar el LoginPanel
		add(menuButton);

		backLoginButton = new JButton("Cerrar Sesion");
		backLoginButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		backLoginButton.setBounds(41, 480, 138, 44);
		backLoginButton.addActionListener(e -> view.showPanel("loginPanel")); // Acción para mostrar el LoginPanel
		add(backLoginButton);

		// Etiquetas y campos de texto
		JLabel idPatientLabel = new JLabel("Cedula");
		idPatientLabel.setBounds(219, 68, 152, 22);
		add(idPatientLabel);

		idPatientField = new JTextField();
		idPatientField.setColumns(10);
		idPatientField.setBounds(219, 90, 152, 28);
		add(idPatientField);

		JLabel patientNameLabel = new JLabel("Nombre");
		patientNameLabel.setBounds(219, 130, 152, 22);
		add(patientNameLabel);

		namePatientField = new JTextField();
		namePatientField.setColumns(10);
		namePatientField.setBounds(219, 153, 152, 28);
		add(namePatientField);

		JLabel birthDateLabel = new JLabel("Fecha de Nacimiento");
		birthDateLabel.setBounds(219, 190, 152, 22);
		add(birthDateLabel);

		birthDateField = new JTextField();
		birthDateField.setColumns(10);
		birthDateField.setBounds(219, 212, 152, 28);
		add(birthDateField);

		JLabel addressLabel = new JLabel("Direccion");
		addressLabel.setBounds(219, 261, 152, 22);
		add(addressLabel);

		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(219, 282, 152, 28);
		add(addressField);

		JLabel contactLabel = new JLabel("Contacto");
		contactLabel.setBounds(219, 321, 152, 22);
		add(contactLabel);

		contactField = new JTextField();
		contactField.setColumns(10);
		contactField.setBounds(219, 343, 152, 28);
		add(contactField);

		// Tabla
		JScrollPane scrollPanePatient = new JScrollPane();
		scrollPanePatient.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanePatient.setBounds(396, 48, 782, 586);
		add(scrollPanePatient);

		patientTable = new JTable();
		patientTable.setShowVerticalLines(true);
		patientTable.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null }, { null, null, null, null, null },
						{ null, null, null, null, null }, { null, null, null, null, null }, },
				new String[] { "Cedula", "Nombre", "Fecha de Nacimiento", "Direccion", "Contacto" }));
		scrollPanePatient.setViewportView(patientTable);
	}

	// Getters para los componentes
	public JButton getAddPatientsButton() {
		return addPatientButton;
	}

	public JButton getModifyPatientButton() {
		return modifyPatientButton;
	}

	public JButton getDeletePatientButton() {
		return deletePatientButton;
	}

	public JButton getShowPatientsButton() {
		return showPatientsButton;
	}

	public JButton getExitButton() {
		return exitButton;
	}

	public JButton getMenuButton() {
		return menuButton;
	}

	public JButton getBackLoginButton() {
		return backLoginButton;
	}

	public JTextField getIdPatientField() {
		return idPatientField;
	}

	public JTextField getNamePatientField() {
		return namePatientField;
	}

	public JTextField getBirthDateField() {
		return birthDateField;
	}

	public JTextField getContactField() {
		return contactField;
	}

	public JTextField getAddressField() {
		return addressField;
	}

	public JTable getPatientTable() {
		return patientTable;
	}
}
