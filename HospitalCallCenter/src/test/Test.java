package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.*;
import structure.AVLTree;
import structure.BinaryTree;

public class Test {
	public static void main(String[] args) {
		AppointmentSystem system = new AppointmentSystem();

		// Cargar los datos desde los archivos
		system.loadData();
		System.out.println("Datos cargados exitosamente.");

		// Mostrar todos los datos cargados
		showAllData(system);

		// Pruebas de registro, modificación y eliminación
		testAppointmentMethods(system);
		testDoctorMethods(system);
		testPatientMethods(system);
		testUserMethods(system);

		// Guardar los datos después de realizar cambios
		system.saveData();
		System.out.println("Datos guardados exitosamente.");
	}

	private static void showAllData(AppointmentSystem system) {
		System.out.println("\n*** Pacientes ***");
		for (Patient patient : system.viewAllPatients()) {
			System.out.println(patient);
		}

		System.out.println("\n*** Doctores ***");
		for (Doctor doctor : system.viewAllDoctors()) {
			System.out.println(doctor);
		}

		System.out.println("\n*** Usuarios ***");
		for (User user : system.viewAllUsers()) {
			System.out.println(user);
		}

		System.out.println("\n*** Citas ***");
		for (Appointment appointment : system.viewAllAppointments()) { // Muestra todas las citas actuales
			System.out.println(appointment);
		}
	}

	private static void testAppointmentMethods(AppointmentSystem system) {
		System.out.println("\n=== Pruebas para Citas ===");
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date appointmentDate = dateFormat.parse("2024-11-25");

			// Registrar una nueva cita
			boolean registered = system.registerAppointment("A003", "Carlos Fernández", "Dr. Pedro Martínez",
					appointmentDate, "15:00", "Consulta de seguimiento", Specialty.PEDIATRICS);
			System.out.println(registered ? "Cita registrada exitosamente." : "Error al registrar la cita.");

			// Modificar una cita existente
			boolean modified = system.modifyAppointment("A003", "Carlos Fernández", "Dr. Pedro Martínez",
					appointmentDate, "16:00", "Cambio de horario", Specialty.PEDIATRICS);
			System.out.println(modified ? "Cita modificada exitosamente." : "Error al modificar la cita.");

			// Cancelar una cita existente
			boolean canceled = system.cancelAppointment("A003");
			System.out.println(canceled ? "Cita cancelada exitosamente." : "Error al cancelar la cita.");
		} catch (Exception e) {
			System.out.println("Error en pruebas de citas: " + e.getMessage());
		}
	}

	private static void testDoctorMethods(AppointmentSystem system) {
		System.out.println("\n=== Pruebas para Doctores ===");

		// Registrar un nuevo doctor
		boolean registered = system.registerDoctor("D003", "Dra. Claudia Rivera", Specialty.DERMATOLOGY);
		System.out.println(registered ? "Doctor registrado exitosamente." : "Error al registrar el doctor.");

		// Modificar un doctor existente
		boolean modified = system.modifyDoctor("D003", "Dra. Claudia R. Gómez", Specialty.CARDIOLOGY);
		System.out.println(modified ? "Doctor modificado exitosamente." : "Error al modificar el doctor.");

		// Eliminar un doctor existente
		boolean deleted = system.deleteDoctor("D003");
		System.out.println(deleted ? "Doctor eliminado exitosamente." : "Error al eliminar el doctor.");
	}

	private static void testPatientMethods(AppointmentSystem system) {
		System.out.println("\n=== Pruebas para Pacientes ===");

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDate = dateFormat.parse("2000-01-15");

			// Registrar un nuevo paciente
			boolean registered = system.registerPatient("P004", "Laura Castillo", birthDate, "Calle Luna 123",
					"555-8765");
			System.out.println(registered ? "Paciente registrado exitosamente." : "Error al registrar el paciente.");

			// Modificar un paciente existente
			boolean modified = system.modifyPatient("P004", "Laura C. Martínez", birthDate, "Calle Estrella 456",
					"555-4321");
			System.out.println(modified ? "Paciente modificado exitosamente." : "Error al modificar el paciente.");

			// Eliminar un paciente existente
			boolean deleted = system.deletePatient("P004");
			System.out.println(deleted ? "Paciente eliminado exitosamente." : "Error al eliminar el paciente.");
		} catch (Exception e) {
			System.out.println("Error en pruebas de pacientes: " + e.getMessage());
		}
	}

	private static void testUserMethods(AppointmentSystem system) {
		System.out.println("\n=== Pruebas para Usuarios ===");

		// Registrar un nuevo usuario
		boolean registered = system.registerUser("user03", "securepassword", "Doctor");
		System.out.println(registered ? "Usuario registrado exitosamente." : "Error al registrar el usuario.");

		// Modificar un usuario existente
		boolean modified = system.modifyUser("user03", "newpassword123", "Administrator");
		System.out.println(modified ? "Usuario modificado exitosamente." : "Error al modificar el usuario.");

		// Eliminar un usuario existente
		boolean deleted = system.deleteUser("user03");
		System.out.println(deleted ? "Usuario eliminado exitosamente." : "Error al eliminar el usuario.");
	}
}
