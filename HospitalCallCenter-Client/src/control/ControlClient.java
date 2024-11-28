package control;

import view.AppointmentPanel;
import view.DoctorPanel;
import view.LoginPanel;
import view.MenuPanel;
import view.PatientPanel;
import view.UserPanel;
import view.View; // Asegúrate de que esta clase esté disponible
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlClient {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private View view;

	public ControlClient(String host, int port, View view) throws IOException {
		this.socket = new Socket(host, port);
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.view = view;
		view.setVisible(true);
	}

	public void init() {
		initializeLoginActions();
		initializeMenuActions();
		initilizedAppointmentActions();
		initializeDoctorActions();
		initializeUserActions();
		initializePatientActions();
	}

	private void initializeLoginActions() {
		LoginPanel loginPanel = view.getLoginPanel();
		loginPanel.getLoginButton().addActionListener(e -> loginAction(loginPanel));
	}

	private void loginAction(LoginPanel loginPanel) {
		String username = getUsername(loginPanel);
		String password = getPassword(loginPanel);

		sendLoginRequest(username, password);
	}

	private String getUsername(LoginPanel loginPanel) {
		return loginPanel.getUserField().getText().trim();
	}

	private String getPassword(LoginPanel loginPanel) {
		String password = new String(loginPanel.getPasswordField().getPassword()).trim();
		loginPanel.getPasswordField().setText("");
		return password;
	}

	private void sendLoginRequest(String username, String password) {
		String request = "LOGIN," + username + "," + password;
		out.println(request);

		try {
			String response = in.readLine();
			processLoginResponse(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processLoginResponse(String response) {
		String[] parts = response.split(",");
		String status = parts[0];

		if ("SUCCESS".equals(status)) {
			String role = parts[1];
			handleRole(role);
			view.showPanel("menuPanel");
		} else {
			showLoginError();
		}
	}

	private void handleRole(String role) {
		MenuPanel menuPanel = view.getMenuPanel();
		if ("admin".equals(role)) {
			menuPanel.getUsersButton().setVisible(true);
			enableDoctorAndPatientButtons(true);
		} else if ("user".equals(role)) {
			menuPanel.getUsersButton().setVisible(false);
			enableDoctorAndPatientButtons(false);
		}
	}

	private void showLoginError() {
		JOptionPane.showMessageDialog(view, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void initializeMenuActions() {
		MenuPanel menuPanel = view.getMenuPanel();

		menuPanel.getAppointmentButton().addActionListener(e -> view.showPanel("appointmentPanel"));
		menuPanel.getPatientButton().addActionListener(e -> view.showPanel("patientPanel"));
		menuPanel.getDoctorButton().addActionListener(e -> view.showPanel("doctorPanel"));
		menuPanel.getUsersButton().addActionListener(e -> view.showPanel("userPanel"));
		menuPanel.getLogoutButton().addActionListener(e -> view.showPanel("loginPanel"));
	}

	private void enableDoctorAndPatientButtons(boolean enable) {
		DoctorPanel doctorPanel = view.getDoctorPanel();
		PatientPanel patientPanel = view.getPatientPanel();

		doctorPanel.getAddDoctorButton().setEnabled(enable);
		doctorPanel.getModifyDoctorButton().setEnabled(enable);
		doctorPanel.getRemoveDoctorButton().setEnabled(enable);

		patientPanel.getAddPatientsButton().setEnabled(enable);
		patientPanel.getModifyPatientButton().setEnabled(enable);
		patientPanel.getDeletePatientButton().setEnabled(enable);
	}

	private void initilizedAppointmentActions() {
		AppointmentPanel appointmentPanel = view.getAppointmentPanel();

		appointmentPanel.getAddAppointmentButton().addActionListener(e -> addAppointment(appointmentPanel));
		appointmentPanel.getModifyAppointmentButton().addActionListener(e -> modifyAppointment(appointmentPanel));
		appointmentPanel.getCancelAppointmentButton().addActionListener(e -> cancelAppointment(appointmentPanel));
		appointmentPanel.getShowAppointmentsButton().addActionListener(e -> showAppointments(appointmentPanel));

	}

	private void initializePatientActions() {
		PatientPanel patientPanel = view.getPatientPanel();

		patientPanel.getAddPatientsButton().addActionListener(e -> addPatient(patientPanel));
		patientPanel.getModifyPatientButton().addActionListener(e -> modifyPatient(patientPanel));
		patientPanel.getDeletePatientButton().addActionListener(e -> deletePatient(patientPanel));
		patientPanel.getShowPatientsButton().addActionListener(e -> {
			try {
				showPatients(patientPanel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

	}

	private void initializeDoctorActions() {
		DoctorPanel doctorPanel = view.getDoctorPanel();

		doctorPanel.getAddDoctorButton().addActionListener(e -> addDoctor(doctorPanel));
		doctorPanel.getModifyDoctorButton().addActionListener(e -> modifyDoctor(doctorPanel));
		doctorPanel.getRemoveDoctorButton().addActionListener(e -> deleteDoctor(doctorPanel));
		doctorPanel.getShowDoctorsButton().addActionListener(e -> {
			try {
				showDoctors(doctorPanel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

	}

	private void initializeUserActions() {
		UserPanel userPanel = view.getUserPanel();

		userPanel.getAddUserButton().addActionListener(e -> addUser(userPanel));
		userPanel.getModifyUserButton().addActionListener(e -> modifyUser(userPanel));
		userPanel.getRemoveUserButton().addActionListener(e -> deleteUser(userPanel));
		userPanel.getShowUsersButton().addActionListener(e -> {
			try {
				showUsers(userPanel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

	}

	private void addAppointment(AppointmentPanel panel) {
		try {
			String id = validateField(panel.getIdAppointmentField().getText().trim(), "ID");
			String patient = validateField(panel.getPatientAppointmentField().getText().trim(), "Paciente");
			String doctor = validateField(panel.getDoctorAppointmentField().getText().trim(), "Doctor");
			String dateString = validateField(panel.getDateAppointmentField().getText().trim(), "Fecha");
			String time = validateField(panel.getTimeField().getText().trim(), "Hora");
			String reason = validateField(panel.getReasonField().getText().trim(), "Motivo");
			String specialtyText = validateField(panel.getSpecialtyField().getText().trim(), "Especialidad");

			if (!time.matches("^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$")) {
				throw new IllegalArgumentException("La hora debe estar en el formato hh:mm AM/PM.");
			}

			Date date = parseDate(dateString);

			sendAppointmentRequest(id, patient, doctor, date, time, reason, specialtyText);

		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al agregar la cita: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void sendAppointmentRequest(String id, String patient, String doctor, Date date, String time, String reason,
			String specialtyText) {
		String request = String.format("ADD_APPOINTMENT,%s,%s,%s,%s,%s,%s,%s", id, patient, doctor,
				new SimpleDateFormat("yyyy-MM-dd").format(date), time, reason, specialtyText);
		out.println(request);

		try {
			String response = in.readLine();
			processAppointmentResponse(response, view.getAppointmentPanel());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Date parseDate(String dateString) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		} catch (Exception e) {
			throw new IllegalArgumentException("La fecha debe estar en el formato yyyy-MM-dd.");
		}
	}

	private void processAppointmentResponse(String response, AppointmentPanel panel) throws IOException {
		String[] parts = response.split(",", 2);
		String status = parts[0];

		if ("SUCCESS".equals(status)) {
			JOptionPane.showMessageDialog(panel, "Cita agregada exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			String errorMessage = response;

			JOptionPane.showMessageDialog(panel, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private String validateField(String value, String fieldName) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException("El campo " + fieldName + " es obligatorio.");
		}
		return value;
	}

	private void modifyAppointment(AppointmentPanel panel) {
		try {
			String id = validateField(panel.getIdAppointmentField().getText().trim(), "ID");
			String patient = validateField(panel.getPatientAppointmentField().getText().trim(), "Paciente");
			String doctor = validateField(panel.getDoctorAppointmentField().getText().trim(), "Doctor");
			String dateString = validateField(panel.getDateAppointmentField().getText().trim(), "Fecha");
			String time = validateField(panel.getTimeField().getText().trim(), "Hora");
			String reason = validateField(panel.getReasonField().getText().trim(), "Motivo");
			String specialtyText = validateField(panel.getSpecialtyField().getText().trim(), "Especialidad");

			if (!time.matches("^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$")) {
				JOptionPane.showMessageDialog(panel, "La hora debe estar en el formato hh:mm AM/PM.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				System.out.println("Fecha parseada: " + date);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(panel, "La fecha debe estar en el formato yyyy-MM-dd.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			sendModifyAppointmentRequest(id, patient, doctor, date, time, reason, specialtyText);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al modificar la cita: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void sendModifyAppointmentRequest(String id, String patient, String doctor, Date date, String time,
			String reason, String specialtyText) {
		String request = String.format("MODIFY_APPOINTMENT,%s,%s,%s,%s,%s,%s,%s", id, patient, doctor,
				new SimpleDateFormat("yyyy-MM-dd").format(date), time, reason, specialtyText.toUpperCase());
		out.println(request);

		try {
			String response = in.readLine();
			processModifyAppointmentResponse(response, view.getAppointmentPanel());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(view, "Error al comunicarse con el servidor: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void processModifyAppointmentResponse(String response, AppointmentPanel panel) {
		String[] parts = response.split(",", 2);
		String status = parts[0];

		if ("SUCCESS".equals(status)) {
			JOptionPane.showMessageDialog(panel, "Cita modificada exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			String errorMessage = parts.length > 1 ? parts[1] : "Error desconocido.";
			JOptionPane.showMessageDialog(panel, "Error al modificar la cita: " + errorMessage, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cancelAppointment(AppointmentPanel panel) {
		try {
			String id = validateField(panel.getIdAppointmentField().getText().trim(), "ID");
			String patient = validateField(panel.getPatientAppointmentField().getText().trim(), "Paciente");
			String doctor = validateField(panel.getDoctorAppointmentField().getText().trim(), "Doctor");
			String dateString = validateField(panel.getDateAppointmentField().getText().trim(), "Fecha");
			String time = validateField(panel.getTimeField().getText().trim(), "Hora");
			String reason = validateField(panel.getReasonField().getText().trim(), "Motivo");
			String specialtyText = validateField(panel.getSpecialtyField().getText().trim(), "Especialidad");

			if (!time.matches("^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$")) {
				throw new IllegalArgumentException("La hora debe estar en el formato hh:mm AM/PM.");
			}

			sendRequest("CANCEL_APPOINTMENT," + id + "," + patient + "," + doctor + "," + dateString + "," + time + ","
					+ reason + "," + specialtyText.toUpperCase());
			String response = getResponse();

			if ("SUCCESS".equals(response)) {
				JOptionPane.showMessageDialog(panel, "Cita cancelada exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "La cita no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al cancelar la cita: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void sendRequest(String request) {
		out.println(request);
	}

	private String getResponse() throws IOException {
		return in.readLine();
	}

	private void showAppointments(AppointmentPanel panel) {
		String specialtyFilterText = panel.getSpecialtyFilterField().getText().trim();
		String patientNameFilterText = panel.getPatientNameFilterField().getText().trim();

		sendRequest("SHOW_APPOINTMENTS," + specialtyFilterText + "," + patientNameFilterText);

		try {
			String response = getResponse();
			if (response.startsWith("APPOINTMENTS_LIST")) {
				int appointmentCount = Integer.parseInt(response.split(",")[1]);
				DefaultTableModel tableModel = (DefaultTableModel) panel.getAppointmentTable().getModel();
				tableModel.setRowCount(0);

				for (int i = 0; i < appointmentCount; i++) {
					String appointmentData = getResponse();
					String[] appointmentParts = appointmentData.split(",");
					tableModel.addRow(new Object[] { appointmentParts[0], // ID
							appointmentParts[1], appointmentParts[2], appointmentParts[3], appointmentParts[4],
							appointmentParts[5], appointmentParts[6] });
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(panel, "Error al obtener las citas: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addPatient(PatientPanel panel) {
		try {
			String id = validateField(panel.getIdPatientField().getText().trim(), "Cedula");
			String name = validateField(panel.getNamePatientField().getText().trim(), "Nombre");
			String birthDateString = validateField(panel.getBirthDateField().getText().trim(), "Fecha de Nacimiento");
			String address = validateField(panel.getAddressField().getText().trim(), "Direccion");
			String contact = validateField(panel.getContactField().getText().trim(), "Contacto");

			sendRequest("ADD_PATIENT," + id + "," + name + "," + birthDateString + "," + address + "," + contact);

			String response = getResponse();
			handleServerResponse(response, panel);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al agregar el paciente: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleServerResponse(String response, PatientPanel panel) {
		if ("SUCCESS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "Paciente agregado exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if ("PATIENT_EXISTS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "El paciente ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
		} else if ("INVALID_DATA".equals(response)) {
			JOptionPane.showMessageDialog(panel, "La fecha debe estar en formato yyyy-mm-dd ", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modifyPatient(PatientPanel panel) {
		try {
			String id = validateField(panel.getIdPatientField().getText().trim(), "Cedula");
			String newName = validateField(panel.getNamePatientField().getText().trim(), "Nombre");
			String birthDateString = validateField(panel.getBirthDateField().getText().trim(), "Fecha de Nacimiento");
			String newAddress = validateField(panel.getAddressField().getText().trim(), "Direccion");
			String newContact = validateField(panel.getContactField().getText().trim(), "Contacto");

			sendRequest("MODIFY_PATIENT," + id + "," + newName + "," + birthDateString + "," + newAddress + ","
					+ newContact);

			String response = getResponse();
			responseModifyPatient(response, panel);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al modificar el paciente: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void responseModifyPatient(String response, PatientPanel panel) {
		if ("SUCCESS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "Paciente modificado exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if ("PATIENT_NOT_FOUND".equals(response)) {
			JOptionPane.showMessageDialog(panel, "El paciente no existe.", "Error", JOptionPane.ERROR_MESSAGE);
		} else if ("INVALID_DATA".equals(response)) {
			JOptionPane.showMessageDialog(panel, "Datos inválidos. Por favor, verifique la información.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deletePatient(PatientPanel panel) {
		try {
			String id = validateField(panel.getIdPatientField().getText().trim(), "Cedula");

			sendRequest("DELETE_PATIENT," + id);

			String response = getResponse();
			responseModifyPatient(response, panel);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al eliminar el paciente: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showPatients(PatientPanel panel) throws IOException {
		sendRequest("SHOW_PATIENTS");

		String response = getResponse();
		responsePatientList(response, panel);
	}

	private void responsePatientList(String response, PatientPanel panel) {
		if (response.startsWith("PATIENT_LIST")) {
			String[] patientData = response.split(",");
			updatePatientTable(patientData, panel);
		} else {
			JOptionPane.showMessageDialog(panel, "Error al obtener la lista de pacientes: " + response, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updatePatientTable(String[] patientData, PatientPanel panel) {
		DefaultTableModel tableModel = (DefaultTableModel) panel.getPatientTable().getModel();
		tableModel.setRowCount(0);

		for (int i = 1; i < patientData.length; i += 5) {
			String id = patientData[i];
			String name = patientData[i + 1];
			String birthDate = patientData[i + 2];
			String address = patientData[i + 3];
			String contact = patientData[i + 4];

			tableModel.addRow(new Object[] { id, name, birthDate, address, contact });
		}
	}

	private void showDoctors(DoctorPanel panel) throws IOException {
		sendRequest("SHOW_DOCTORS");

		String response = getResponse();
		responseDoctorList(response, panel);
	}

	private void responseDoctorList(String response, DoctorPanel panel) {
		if (response.startsWith("DOCTOR_LIST")) {
			String[] doctorData = response.split(",");
			updateDoctorTable(doctorData, panel);
		} else {
			JOptionPane.showMessageDialog(panel, "Error al obtener la lista de doctores: " + response, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateDoctorTable(String[] doctorData, DoctorPanel panel) {
		DefaultTableModel tableModel = (DefaultTableModel) panel.getDoctorTable().getModel();
		tableModel.setRowCount(0);

		for (int i = 1; i < doctorData.length; i += 3) {
			String professionalId = doctorData[i];
			String name = doctorData[i + 1];
			String specialty = doctorData[i + 2];

			tableModel.addRow(new Object[] { professionalId, name, specialty });
		}
	}

	private void addDoctor(DoctorPanel panel) {
		try {
			String professionalId = validateField(panel.getIdDoctorField().getText().trim(), "ID Profesional");
			String name = validateField(panel.getNameDoctorField().getText().trim(), "Nombre");
			String specialtyText = validateField(panel.getSpecialtyDoctorField().getText().trim(), "Especialidad");

			sendRequest("ADD_DOCTOR," + professionalId + "," + name + "," + specialtyText);

			String response = getResponse();
			responseAddDoctor(response, panel);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al agregar el doctor: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void responseAddDoctor(String response, DoctorPanel panel) {
		if ("SUCCESS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "Doctor agregado exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if ("DOCTOR_EXISTS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "El doctor ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
		} else if ("INVALID_SPECIALTY".equals(response)) {
			JOptionPane.showMessageDialog(panel, "La especialidad ingresada no es válida.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(panel, "Error desconocido: " + response, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modifyDoctor(DoctorPanel panel) {
		try {
			String professionalId = validateField(panel.getIdDoctorField().getText().trim(), "ID Profesional");
			String newName = validateField(panel.getNameDoctorField().getText().trim(), "Nombre");
			String specialtyText = validateField(panel.getSpecialtyDoctorField().getText().trim(), "Especialidad");

			sendRequest("MODIFY_DOCTOR," + professionalId + "," + newName + "," + specialtyText);

			String response = getResponse();
			responseModifyDoc(response, panel);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al modificar el doctor: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void responseModifyDoc(String response, DoctorPanel panel) {
		if ("SUCCESS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "Doctor modificado exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if ("DOCTOR_NOT_FOUND".equals(response)) {
			JOptionPane.showMessageDialog(panel, "El doctor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
		} else if ("INVALID_SPECIALTY".equals(response)) {
			JOptionPane.showMessageDialog(panel, "La especialidad ingresada no es válida.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(panel, "Error desconocido: " + response, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteDoctor(DoctorPanel panel) {
		try {
			String professionalId = validateField(panel.getIdDoctorField().getText().trim(), "ID Profesional");

			sendRequest("DELETE_DOCTOR," + professionalId);

			String response = getResponse();
			responseDeletDoc(response, panel);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al eliminar el doctor: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void responseDeletDoc(String response, DoctorPanel panel) {
		if ("SUCCESS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "Doctor eliminado exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if ("DOCTOR_NOT_FOUND".equals(response)) {
			JOptionPane.showMessageDialog(panel, "El doctor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(panel, "Error desconocido: " + response, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showUsers(UserPanel panel) throws IOException {
		sendRequest("SHOW_USERS");

		String response = getResponse();
		responseUserList(response, panel);
	}

	private void responseUserList(String response, UserPanel panel) {
		String[] userEntries = response.split(";");

		DefaultTableModel tableModel = (DefaultTableModel) panel.getUserTable().getModel();
		tableModel.setRowCount(0);

		for (String entry : userEntries) {
			String[] userData = entry.split(",");
			if (userData.length == 3) {
				tableModel.addRow(new Object[] { userData[0], userData[1], userData[2] });
			}
		}
	}

	private void addUser(UserPanel panel) {
		try {
			String nameid = validateField(panel.getUserField().getText().trim(), "ID de Usuario");
			String password = validateField(new String(panel.getPasswordField().getPassword()).trim(), "Contraseña");
			String rol = validateField(panel.getRolField().getText().trim(), "Rol");

			if (!rol.equals("admin") && !rol.equals("user")) {
				JOptionPane.showMessageDialog(panel, "Error de validación: el rol debe ser 'admin' o 'user'.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			sendRequest("ADD_USER," + nameid + "," + password + "," + rol);

			String response = getResponse();
			responseAddUser(response, panel);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al agregar el usuario: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void responseAddUser(String response, UserPanel panel) {
		if ("SUCCESS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "Usuario agregado exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if ("USER_ALREADY_EXISTS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(panel, "Error desconocido: " + response, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modifyUser(UserPanel panel) {
		try {
			String nameid = validateField(panel.getUserField().getText().trim(), "ID de Usuario");
			String newPassword = validateField(new String(panel.getPasswordField().getPassword()).trim(),
					"Nueva Contraseña");
			String newRol = validateField(panel.getRolField().getText().trim(), "Nuevo Rol");

			if (!newRol.equals("admin") && !newRol.equals("user")) {
				JOptionPane.showMessageDialog(panel, "Error de validación: el rol debe ser 'admin' o 'user'.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			sendRequest("MODIFY_USER," + nameid + "," + newPassword + "," + newRol.toUpperCase());

			String response = getResponse();
			responseModifyUser(response, panel);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al modificar el usuario: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void responseModifyUser(String response, UserPanel panel) {
		if ("SUCCESS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "Usuario modificado exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if ("USER_NOT_FOUND".equals(response)) {
			JOptionPane.showMessageDialog(panel, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(panel, "Error desconocido: " + response, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteUser(UserPanel panel) {
		try {
			String nameid = validateField(panel.getUserField().getText().trim(), "ID de Usuario");

			sendRequest("DELETE_USER," + nameid);

			String response = getResponse();
			responseDeletUser(response, panel);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al eliminar el usuario: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void responseDeletUser(String response, UserPanel panel) {
		if ("SUCCESS".equals(response)) {
			JOptionPane.showMessageDialog(panel, "Usuario eliminado exitosamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} else if ("USER_NOT_FOUND".equals(response)) {
			JOptionPane.showMessageDialog(panel, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(panel, "Error desconocido: " + response, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}