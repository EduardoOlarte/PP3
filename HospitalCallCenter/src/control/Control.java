package control;

import model.AppointmentSystem;
import model.Doctor;
import model.Patient;
import model.Appointment;
import model.Specialty;
import model.User;
import view.AppointmentPanel;
import view.DoctorPanel;
import view.LoginPanel;
import view.MenuPanel;
import view.PatientPanel;
import view.UserPanel;
import view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Control {
	private View view;
	private AppointmentSystem appointmentSystem;

	public Control(View view, AppointmentSystem appointmentSystem) {
		this.view = view;
		this.appointmentSystem = appointmentSystem;

	}

	public void init() {
		initializeLoginActions();
		initializeMenuActions();
		initializeDoctorActions();
		initializeUserActions();
		initilizedAppointmentActions();
		initializePatientActions();
	}

	private void initializeLoginActions() {
		LoginPanel loginPanel = view.getLoginPanel();
		loginPanel.getLoginButton().addActionListener(e -> loginAction(loginPanel));
	}

    private void loginAction(LoginPanel loginPanel) {
        String username = loginPanel.getUserField().getText().trim();
        String password = new String(loginPanel.getPasswordField().getPassword()).trim();

        // Limpiar el campo de contraseña
        loginPanel.getPasswordField().setText("");

        // Verificar usuario y contraseña
        if (appointmentSystem.userExists(username, password)) {
            User user = appointmentSystem.getUser(username, password); // Obtener el usuario
            MenuPanel menuPanel = view.getMenuPanel();

            if ("admin".equals(user.getRol())) {
                menuPanel.getUsersButton().setVisible(true);
                enableDoctorAndPatientButtons(true);
            } else if ("user".equals(user.getRol())) {
                menuPanel.getUsersButton().setVisible(false);
                enableDoctorAndPatientButtons(false);
            }

            view.showPanel("menuPanel");
        } else {
            JOptionPane.showMessageDialog(view, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

	// Inicializar acciones para el UserPanel
	private void initializeUserActions() {
		// Obtén el UserPanel directamente desde View
		UserPanel userPanel = view.getUserPanel();

		// Agrega lógica de eventos para los botones del UserPanel
		userPanel.getAddUserButton().addActionListener(e -> addUser(userPanel));
		userPanel.getModifyUserButton().addActionListener(e -> modifyUser(userPanel));
		userPanel.getRemoveUserButton().addActionListener(e -> deleteUser(userPanel));
		userPanel.getShowUsersButton().addActionListener(e -> showUsers(userPanel));

	}

	private void addUser(UserPanel panel) {
		try {
			// Validar y procesar la información de los campos
			String nameid = validateField(panel.getUserField().getText().trim(), "ID de Usuario");
			String password = validateField(new String(panel.getPasswordField().getPassword()).trim(), "Contraseña");
			String rol = validateField(panel.getRolField().getText().trim(), "Rol");

			// Validar el rol
			if (!rol.equalsIgnoreCase("ADMIN") && !rol.equalsIgnoreCase("USER")) {
				throw new IllegalArgumentException("El rol debe ser 'ADMIN' o 'USER'.");
			}

			// Intentar registrar el usuario en el sistema
			boolean success = appointmentSystem.registerUser(nameid, password, rol.toUpperCase());

			if (success) {
				JOptionPane.showMessageDialog(panel, "Usuario agregado exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al agregar el usuario: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modifyUser(UserPanel panel) {
		try {
			// Validar y procesar la información de los campos
			String nameid = validateField(panel.getUserField().getText().trim(), "ID de Usuario");
			String newPassword = validateField(new String(panel.getPasswordField().getPassword()).trim(),
					"Nueva Contraseña");
			String newRol = validateField(panel.getRolField().getText().trim(), "Nuevo Rol");

			// Validar el rol
			if (!newRol.equalsIgnoreCase("ADMIN") && !newRol.equalsIgnoreCase("USER")) {
				throw new IllegalArgumentException("El rol debe ser 'ADMIN' o 'USER'.");
			}

			// Intentar modificar el usuario en el sistema
			boolean success = appointmentSystem.modifyUser(nameid, newPassword, newRol.toUpperCase());

			if (success) {
				JOptionPane.showMessageDialog(panel, "Usuario modificado exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al modificar el usuario: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteUser(UserPanel panel) {
		try {
			// Validar y procesar la información del campo
			String nameid = validateField(panel.getUserField().getText().trim(), "ID de Usuario");

			// Intentar eliminar el usuario en el sistema
			boolean success = appointmentSystem.deleteUser(nameid);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Usuario eliminado exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al eliminar el usuario: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showUsers(UserPanel panel) {
		// Obtener la lista de usuarios del sistema de citas
		List<User> users = appointmentSystem.viewAllUsers();

		// Ordenar la lista de usuarios por ID
		users.sort(Comparator.comparing(User::getNameid));

		// Obtener el modelo de la tabla
		DefaultTableModel tableModel = (DefaultTableModel) panel.getUserTable().getModel();
		tableModel.setRowCount(0); // Limpiar la tabla

		// Agregar los usuarios al modelo de la tabla
		for (User user : users) {
			tableModel.addRow(new Object[] {
					user.getNameid(),
					user.getPassword(),
					user.getRol()
			});
		}
	}

	// Inicializar acciones para el AppointmentPanel
	private void initilizedAppointmentActions() {
		// Obtén el AppointmentPanel directamente desde View
		AppointmentPanel appointmentPanel = view.getAppointmentPanel();

		// Agrega lógica de eventos para los botones del AppointmentPanel
		appointmentPanel.getAddAppointmentButton().addActionListener(e -> addAppointment(appointmentPanel));
		appointmentPanel.getModifyAppointmentButton().addActionListener(e -> modifyAppointment(appointmentPanel));
		appointmentPanel.getCancelAppointmentButton().addActionListener(e -> cancelAppointment(appointmentPanel));
		appointmentPanel.getShowAppointmentsButton().addActionListener(e -> showAppointments(appointmentPanel));

	}

	// Inicializar acciones para el PatientPanel
	private void initializePatientActions() {
		// Obtén el PatientPanel directamente desde View
		PatientPanel patientPanel = view.getPatientPanel();

		// Agrega lógica de eventos para los botones del PatientPanel
		patientPanel.getAddPatientsButton().addActionListener(e -> addPatient(patientPanel));
		patientPanel.getModifyPatientButton().addActionListener(e -> modifyPatient(patientPanel));
		patientPanel.getDeletePatientButton().addActionListener(e -> deletePatient(patientPanel));
		patientPanel.getShowPatientsButton().addActionListener(e -> showPatients(patientPanel));

	}

	// Inicializar acciones para el DoctorPanel
	private void initializeDoctorActions() {
		// Obtén el DoctorPanel directamente desde View
		DoctorPanel doctorPanel = view.getDoctorPanel();

		// Agrega lógica de eventos para los botones del DoctorPanel
		doctorPanel.getAddDoctorButton().addActionListener(e -> addDoctor(doctorPanel));
		doctorPanel.getModifyDoctorButton().addActionListener(e -> modifyDoctor(doctorPanel));
		doctorPanel.getRemoveDoctorButton().addActionListener(e -> deleteDoctor(doctorPanel));
		doctorPanel.getShowDoctorsButton().addActionListener(e -> showDoctors(doctorPanel));

	}

	private void addDoctor(DoctorPanel panel) {
		try {
			// Validar y procesar la información de los campos
			String professionalId = validateField(panel.getIdDoctorField().getText().trim(), "ID Profesional");
			String name = validateField(panel.getNameDoctorField().getText().trim(), "Nombre");
			String specialtyText = validateField(panel.getSpecialtyDoctorField().getText().trim(), "Especialidad");

			// Validar la especialidad
			Specialty specialty;
			try {
				specialty = Specialty.valueOf(specialtyText.toUpperCase());
				System.out.println("Especialidad parseada: " + specialty); // Depuración: Imprimir la especialidad
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("La especialidad ingresada no es válida.");
			}

			// Intentar registrar el doctor en el sistema
			boolean success = appointmentSystem.registerDoctor(professionalId, name, specialty);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Doctor agregado exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "El doctor ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al agregar el doctor: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modifyDoctor(DoctorPanel panel) {
		try {
			// Validar y procesar la información de los campos
			String professionalId = validateField(panel.getIdDoctorField().getText().trim(), "ID Profesional");
			String newName = validateField(panel.getNameDoctorField().getText().trim(), "Nombre");
			String specialtyText = validateField(panel.getSpecialtyDoctorField().getText().trim(), "Especialidad");

			// Validar la especialidad
			Specialty newSpecialty;
			try {
				newSpecialty = Specialty.valueOf(specialtyText.toUpperCase());
				System.out.println("Especialidad parseada: " + newSpecialty); // Depuración: Imprimir la especialidad
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("La especialidad ingresada no es válida.");
			}

			// Intentar modificar el doctor en el sistema
			boolean success = appointmentSystem.modifyDoctor(professionalId, newName, newSpecialty);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Doctor modificado exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "El doctor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al modificar el doctor: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteDoctor(DoctorPanel panel) {
		try {
			// Validar y procesar la información del campo
			String professionalId = validateField(panel.getIdDoctorField().getText().trim(), "ID Profesional");

			// Intentar eliminar el doctor en el sistema
			boolean success = appointmentSystem.deleteDoctor(professionalId);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Doctor eliminado exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "El doctor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al eliminar el doctor: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showDoctors(DoctorPanel panel) {
		// Obtener la lista de doctores del sistema de citas
		List<Doctor> doctors = appointmentSystem.viewAllDoctors();

		// Ordenar la lista de doctores por nombre
		doctors.sort(Comparator.comparing(Doctor::getName));

		// Obtener el modelo de la tabla
		DefaultTableModel tableModel = (DefaultTableModel) panel.getDoctorTable().getModel();
		tableModel.setRowCount(0); // Limpiar la tabla

		// Agregar los doctores al modelo de la tabla
		for (Doctor doctor : doctors) {
			tableModel.addRow(new Object[] {
					doctor.getProfessionalId(),
					doctor.getName(),
					doctor.getSpecialty()
			});
		}
	}

	private void addPatient(PatientPanel panel) {
		try {
			// Validar y procesar la información de los campos
			String id = validateField(panel.getIdPatientField().getText().trim(), "Cedula");
			String name = validateField(panel.getNamePatientField().getText().trim(), "Nombre");
			String birthDateString = validateField(panel.getBirthDateField().getText().trim(), "Fecha de Nacimiento");
			String address = validateField(panel.getAddressField().getText().trim(), "Direccion");
			String contact = validateField(panel.getContactField().getText().trim(), "Contacto");

			// Validar y parsear la fecha de nacimiento
			Date birthDate;
			try {
				birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
				System.out.println("Fecha de nacimiento parseada: " + birthDate); // Depuración: Imprimir la fecha
			} catch (Exception e) {
				throw new IllegalArgumentException("La fecha de nacimiento debe estar en el formato yyyy-MM-dd.");
			}

			// Intentar registrar el paciente en el sistema
			boolean success = appointmentSystem.registerPatient(id, name, birthDate, address, contact);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Paciente agregado exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "El paciente ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al agregar el paciente: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modifyPatient(PatientPanel panel) {
		try {
			// Validar y procesar la información de los campos
			String id = validateField(panel.getIdPatientField().getText().trim(), "Cedula");
			String newName = validateField(panel.getNamePatientField().getText().trim(), "Nombre");
			String birthDateString = validateField(panel.getBirthDateField().getText().trim(), "Fecha de Nacimiento");
			String newAddress = validateField(panel.getAddressField().getText().trim(), "Direccion");
			String newContact = validateField(panel.getContactField().getText().trim(), "Contacto");

			// Validar y parsear la fecha de nacimiento
			Date newBirthDate;
			try {
				newBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
				System.out.println("Fecha de nacimiento parseada: " + newBirthDate); // Depuración: Imprimir la fecha
			} catch (Exception e) {
				throw new IllegalArgumentException("La fecha de nacimiento debe estar en el formato yyyy-MM-dd.");
			}

			// Intentar modificar el paciente en el sistema
			boolean success = appointmentSystem.modifyPatient(id, newName, newBirthDate, newAddress, newContact);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Paciente modificado exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "El paciente no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al modificar el paciente: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deletePatient(PatientPanel panel) {
		try {
			// Validar y procesar la información del campo
			String id = validateField(panel.getIdPatientField().getText().trim(), "Cedula");

			// Intentar eliminar el paciente en el sistema
			boolean success = appointmentSystem.deletePatient(id);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Paciente eliminado exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "El paciente no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al eliminar el paciente: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showPatients(PatientPanel panel) {
		// Obtener la lista de pacientes del sistema de citas
		List<Patient> patients = appointmentSystem.viewAllPatients();

		// Obtener el modelo de la tabla
		DefaultTableModel tableModel = (DefaultTableModel) panel.getPatientTable().getModel();
		tableModel.setRowCount(0); // Limpiar la tabla

		// Formatear la fecha para que se muestre en formato "yyyy-MM-dd"
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// Agregar los pacientes al modelo de la tabla
		for (Patient patient : patients) {
			tableModel.addRow(new Object[] {
					patient.getId(),
					patient.getName(),
					dateFormat.format(patient.getBirthDate()),
					patient.getAddress(),
					patient.getContact()
			});
		}
	}

	private void addAppointment(AppointmentPanel panel) {
		try {
			// Ahora, validamos y procesamos la información de los campos
			String id = validateField(panel.getIdAppointmentField().getText().trim(), "ID");
			String patient = validateField(panel.getPatientAppointmentField().getText().trim(), "Paciente");
			String doctor = validateField(panel.getDoctorAppointmentField().getText().trim(), "Doctor");
			String dateString = validateField(panel.getDateAppointmentField().getText().trim(), "Fecha");
			String time = validateField(panel.getTimeField().getText().trim(), "Hora");
			String reason = validateField(panel.getReasonField().getText().trim(), "Motivo");
			String specialtyText = validateField(panel.getSpecialtyField().getText().trim(), "Especialidad");

			// Validar el formato de la hora
			if (!time.matches("^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$")) {
				throw new IllegalArgumentException("La hora debe estar en el formato hh:mm AM/PM.");
			}

			// Validar y parsear la fecha
			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				System.out.println("Fecha parseada: " + date); // Depuración: Imprimir la fecha
			} catch (Exception e) {
				throw new IllegalArgumentException("La fecha debe estar en el formato yyyy-MM-dd.");
			}

			// Validar la especialidad
			Specialty specialty;
			try {
				specialty = Specialty.valueOf(specialtyText.toUpperCase());
				System.out.println("Especialidad parseada: " + specialty); // Depuración: Imprimir la especialidad
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("La especialidad ingresada no es válida.");
			}

			// Validar la cita
			String validationMessage = appointmentSystem.validateAppointment(id, patient, doctor, date, time,
					specialty);
			if (validationMessage != null) {
				throw new IllegalArgumentException(validationMessage);
			}

			// Intentar registrar la cita en el sistema
			boolean success = appointmentSystem.registerAppointment(id, patient, doctor, date, time, reason, specialty);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Cita agregada exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "La cita ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al agregar la cita: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Método auxiliar para validar campos obligatorios
	private String validateField(String value, String fieldName) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException("El campo " + fieldName + " es obligatorio.");
		}
		return value;
	}

	private void modifyAppointment(AppointmentPanel panel) {
		try {
			// Ahora, validamos y procesamos la información de los campos
			String id = validateField(panel.getIdAppointmentField().getText().trim(), "ID");
			String patient = validateField(panel.getPatientAppointmentField().getText().trim(), "Paciente");
			String doctor = validateField(panel.getDoctorAppointmentField().getText().trim(), "Doctor");
			String dateString = validateField(panel.getDateAppointmentField().getText().trim(), "Fecha");
			String time = validateField(panel.getTimeField().getText().trim(), "Hora");
			String reason = validateField(panel.getReasonField().getText().trim(), "Motivo");
			String specialtyText = validateField(panel.getSpecialtyField().getText().trim(), "Especialidad");

			if (!appointmentSystem.patientExist(patient)) {
				JOptionPane.showMessageDialog(view, "El paciente no existe", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Verificar que el doctor exista
			if (!appointmentSystem.doctorExist(doctor)) {
				JOptionPane.showMessageDialog(view, "El doctor no existe", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Validar el formato de la hora
			if (!time.matches("^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$")) {
				throw new IllegalArgumentException("La hora debe estar en el formato hh:mm AM/PM.");
			}

			// Validar y parsear la fecha
			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				System.out.println("Fecha parseada: " + date); // Depuración: Imprimir la fecha
			} catch (Exception e) {
				throw new IllegalArgumentException("La fecha debe estar en el formato yyyy-MM-dd.");
			}

			// Validar la especialidad
			Specialty specialty;
			try {
				specialty = Specialty.valueOf(specialtyText.toUpperCase());
				System.out.println("Especialidad parseada: " + specialty); // Depuración: Imprimir la especialidad
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("La especialidad ingresada no es válida.");
			}

			// Intentar modificar la cita en el sistema
			boolean success = appointmentSystem.modifyAppointment(id, patient, doctor, date, time, reason, specialty);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Cita modificada exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "La cita no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al modificar la cita: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cancelAppointment(AppointmentPanel panel) {
		try {
			// Ahora, validamos y procesamos la información de los campos
			String id = validateField(panel.getIdAppointmentField().getText().trim(), "ID");
			String patient = validateField(panel.getPatientAppointmentField().getText().trim(), "Paciente");
			String doctor = validateField(panel.getDoctorAppointmentField().getText().trim(), "Doctor");
			String dateString = validateField(panel.getDateAppointmentField().getText().trim(), "Fecha");
			String time = validateField(panel.getTimeField().getText().trim(), "Hora");
			String reason = validateField(panel.getReasonField().getText().trim(), "Motivo");
			String specialtyText = validateField(panel.getSpecialtyField().getText().trim(), "Especialidad");

			// Validar el formato de la hora
			if (!time.matches("^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$")) {
				throw new IllegalArgumentException("La hora debe estar en el formato hh:mm AM/PM.");
			}

			// Validar y parsear la fecha
			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				System.out.println("Fecha parseada: " + date); // Depuración: Imprimir la fecha
			} catch (Exception e) {
				throw new IllegalArgumentException("La fecha debe estar en el formato yyyy-MM-dd.");
			}

			// Validar la especialidad
			Specialty specialty;
			try {
				specialty = Specialty.valueOf(specialtyText.toUpperCase());
				System.out.println("Especialidad parseada: " + specialty); // Depuración: Imprimir la especialidad
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("La especialidad ingresada no es válida.");
			}

			// Intentar cancelar la cita en el sistema
			boolean success = appointmentSystem.cancelAppointment(id, patient, doctor, date, time, reason, specialty);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Cita cancelada exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(panel, "La cita no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IllegalArgumentException ex) {
			// Manejo de errores específicos de validación
			JOptionPane.showMessageDialog(panel, "Error de validación: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			// Otros errores inesperados: manejar solo una vez
			JOptionPane.showMessageDialog(panel, "Error al cancelar la cita: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showAppointments(AppointmentPanel panel) {
		// Obtener el texto de los campos specialtyFilter y patientNameFilter
		String specialtyFilterText = panel.getSpecialtyFilterField().getText().trim();
		String patientNameFilterText = panel.getPatientNameFilterField().getText().trim();
	
		List<Appointment> appointments = new ArrayList<>();
	
		// Si hay algo en el campo specialtyFilter, filtramos por especialidad
		if (!specialtyFilterText.isEmpty()) {
			try {
				// Intentamos convertir el texto del campo en una especialidad válida
				Specialty specialty = Specialty.valueOf(specialtyFilterText.toUpperCase());
	
				// Si la especialidad es válida, obtenemos las citas de esa especialidad
				appointments = appointmentSystem.viewAppointmentsBySpecialty(specialty);
			} catch (IllegalArgumentException e) {
				// Si la especialidad no es válida, mostrar un mensaje de error
				JOptionPane.showMessageDialog(panel, "Especialidad no válida. Intente de nuevo.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return; // No continuamos si el valor no es una especialidad válida
			}
		} else {
			// Si no hay filtro, obtenemos todas las citas
			appointments = appointmentSystem.viewAllAppointments();
		}
	
		// Filtrar por nombre del paciente si el campo no está vacío
		if (!patientNameFilterText.isEmpty()) {
			appointments.removeIf(appointment -> !appointment.getPatient().toLowerCase().contains(patientNameFilterText.toLowerCase()));
		}
	
		// Obtener el modelo de la tabla
		DefaultTableModel tableModel = (DefaultTableModel) panel.getAppointmentTable().getModel();
		tableModel.setRowCount(0); // Limpiar la tabla
	
		// Formatear la fecha para que se muestre en formato "yyyy-MM-dd"
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
		// Agregar las citas al modelo de la tabla
		for (Appointment appointment : appointments) {
			tableModel.addRow(new Object[] { appointment.getId(), appointment.getPatient(), appointment.getDoctor(),
					dateFormat.format(appointment.getDate()), appointment.getTime(), appointment.getReason(),
					appointment.getSpecialty() });
		}
	}

}
