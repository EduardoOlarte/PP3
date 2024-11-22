package control;

import model.AppointmentSystem;
import model.Appointment;
import model.Specialty;
import view.AppointmentPanel;
import view.LoginPanel;
import view.MenuPanel;
import view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Control {
	private View view;
	private AppointmentSystem appointmentSystem;

	public Control(View view, AppointmentSystem appointmentSystem) {
		this.view = view;
		this.appointmentSystem = appointmentSystem;

		// Inicializar acciones para los paneles principales
		initializeLoginActions();

	}

	// Acción para el botón "Menú"
	private void showMenuPanel() {
		view.changePanel(view.getMenuPanel(), 1);
		initializeMenuActions(); // Re-inicializar las acciones del MenuPanel
	}

	// Acción para el botón "Cerrar Sesión"
	private void showLoginPanel() {
		view.changePanel(view.getLoginPanel(), 1);
		initializeLoginActions(); // Re-inicializar las acciones del LoginPanel
	}

	// Cambiar al AppointmentPanel y re-inicializar las acciones
	private void showAppointmentPanel() {
		view.changePanel(view.getAppointmentPanel(), 0);
		initializeActions(); // Re-inicializar las acciones del AppointmentPanel
	}

	// Inicializar acciones de los botones en el MenuPanel
	private void initializeMenuActions() {
		MenuPanel menuPanel = view.getMenuPanel();

		// Acción para el botón "Citas"
		menuPanel.getAppointmentButton().addActionListener(e -> showAppointmentPanel());

		// Acción para el botón "Cerrar Sesión"
		menuPanel.getLogoutButton().addActionListener(e -> showLoginPanel());

		// Acción para el botón "Salir"
		menuPanel.getExitButton().addActionListener(e -> System.exit(0)); // Cerrar la aplicación
	}

	// Inicializar acciones del LoginPanel
	private void initializeLoginActions() {
		LoginPanel loginPanel = view.getLoginPanel();

		// Acción para el botón "Iniciar sesión"
		loginPanel.getLoginButton().addActionListener(e -> {
			String username = loginPanel.getUserField().getText();
			String password = new String(loginPanel.getPasswordField().getPassword());

			// Validación simple de usuario y contraseña
			if ("".equals(username) && "".equals(password)) {
				showMenuPanel(); // Cambiar al MenuPanel si las credenciales son correctas
			} else {
				// Si las credenciales son incorrectas
				JOptionPane.showMessageDialog(view, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		// Acción para el botón "Salir"
		loginPanel.getExitButton().addActionListener(e -> System.exit(0)); // Cerrar la aplicación
	}

	// Inicializar acciones para el AppointmentPanel
	private void initializeActions() {
		// Obtén el AppointmentPanel directamente desde View
		AppointmentPanel appointmentPanel = view.getAppointmentPanel();

		// Agrega lógica de eventos para los botones del AppointmentPanel
		appointmentPanel.getAddAppointmentButton().addActionListener(e -> addAppointment(appointmentPanel));
		appointmentPanel.getModifyAppointmentButton().addActionListener(e -> modifyAppointment(appointmentPanel));
		appointmentPanel.getCancelAppointmentButton().addActionListener(e -> cancelAppointment(appointmentPanel));
		appointmentPanel.getShowAppointmentsButton().addActionListener(e -> showAppointments(appointmentPanel));

		// Acción para el botón "Menú"
		appointmentPanel.getMenuButton().addActionListener(e -> showMenuPanel());

		// Acción para el botón "Cerrar Sesión"
		appointmentPanel.getBackLoginButton().addActionListener(e -> showLoginPanel());
	}

	private void addAppointment(AppointmentPanel panel) {
		try {

			// Ahora, validamos y procesamos la información de los campos
			String id = validateField(panel.getIdAppointmentField().getText().trim(), "ID");
			String patient = validateField(panel.getPatientAppointmentField().getText().trim(), "Paciente");
			String doctor = validateField(panel.getDoctorAppointmentField().getText().trim(), "Doctor");
			String dateString = validateField(panel.getDateAppointmentField().getText().trim(), "Fecha");
			String time = validateField(panel.getTimeField().getText().trim(), "Hora"); // No validamos el formato de la
																						// hora
			String reason = validateField(panel.getReasonField().getText().trim(), "Motivo");
			String specialtyText = validateField(panel.getSpecialtyField().getText().trim(), "Especialidad");

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

			// Intentar registrar la cita en el sistema
			boolean success = appointmentSystem.registerAppointment(id, patient, doctor, date, time, reason, specialty);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Cita agregada exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				clearAppointmentFields(panel); // Limpiar los campos si la cita fue agregada correctamente
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

	// Limpiar los campos de cita (esto solo se llamará si la cita se ha agregado
	// correctamente)
	private void clearAppointmentFields(AppointmentPanel panel) {
		panel.getIdAppointmentField().setText("");
		panel.getPatientAppointmentField().setText("");
		panel.getDoctorAppointmentField().setText("");
		panel.getDateAppointmentField().setText("");
		panel.getTimeField().setText("");
		panel.getReasonField().setText("");
		panel.getSpecialtyField().setText("");
	}

	private void modifyAppointment(AppointmentPanel panel) {
		try {
			String id = panel.getIdAppointmentField().getText().trim();
			String patient = panel.getPatientAppointmentField().getText().trim();
			String doctor = panel.getDoctorAppointmentField().getText().trim();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(panel.getDateAppointmentField().getText().trim());
			String time = panel.getTimeField().getText().trim();
			String reason = panel.getReasonField().getText().trim();
			Specialty specialty = Specialty.valueOf(panel.getSpecialtyField().getText().trim().toUpperCase());

			boolean success = appointmentSystem.modifyAppointment(id, patient, doctor, date, time, reason, specialty);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Cita modificada exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				clearAppointmentFields(panel);
			} else {
				JOptionPane.showMessageDialog(panel, "La cita no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al modificar la cita: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cancelAppointment(AppointmentPanel panel) {
		try {
			String id = panel.getIdAppointmentField().getText().trim();
			boolean success = appointmentSystem.cancelAppointment(id);

			if (success) {
				JOptionPane.showMessageDialog(panel, "Cita cancelada exitosamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				clearAppointmentFields(panel);
			} else {
				JOptionPane.showMessageDialog(panel, "La cita no existe.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(panel, "Error al cancelar la cita: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showAppointments(AppointmentPanel panel) {
		// Obtener el texto del campo specialtyFilter
		String specialtyFilterText = panel.getSpecialtyFilterField().getText().trim();

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
