package control;

import model.Appointment;
import model.AppointmentSystem; // Asegúrate de que esta clase esté disponible
import model.Doctor;
import model.Patient;
import model.Specialty;
import model.User;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ControlServer {
	private ServerSocket serverSocket;
	private AppointmentSystem appointmentSystem;

	public ControlServer(int port, AppointmentSystem appointmentSystem) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.appointmentSystem = appointmentSystem;
		start();
	}

	public void start() {
		System.out.println("Servidor iniciado y esperando conexiones...");
		while (true) {
			try {

				Socket clientSocket = serverSocket.accept();
				int count = 1;
				System.out.println("Cliente conectado " + count);
				count++;
				new Thread(new ClientHandler(clientSocket)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class ClientHandler implements Runnable {
		private Socket clientSocket;

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		@Override
		public void run() {
			try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
				String request;
				while ((request = in.readLine()) != null) {
					processRequest(request, out);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void processRequest(String request, PrintWriter out) {
			String[] parts = request.split(",");
			String command = parts[0];

			if ("LOGIN".equals(command)) {
				String username = parts[1];
				String password = parts[2];
				handleLogin(username, password, out);
			}
			if ("ADD_APPOINTMENT".equals(command)) {

				handleAddAppointment(parts, out);
			}
			if ("MODIFY_APPOINTMENT".equals(command)) {
				modifyAppointment(parts, out);
			}
			if ("CANCEL_APPOINTMENT".equals(command)) {
				handleCancelAppointment(parts, out);
			}
			if ("SHOW_APPOINTMENTS".equals(command)) {
				handleShowAppointments(parts, out);
			}
			if ("ADD_PATIENT".equals(command)) {
				handleAddPatient(parts, out);
			}
			if ("MODIFY_PATIENT".equals(command)) {
				handleModifyPatient(parts, out);
			}
			if ("DELETE_PATIENT".equals(command)) {
				handleDeletePatient(parts, out);
			}
			if ("SHOW_PATIENTS".equals(command)) {
				handleShowPatients(out);
			}
			if ("SHOW_DOCTORS".equals(command)) {
				handleShowDoctors(out);
			}
			if ("ADD_DOCTOR".equals(command)) {
				handleAddDoctor(parts, out);
			}
			if ("MODIFY_DOCTOR".equals(command)) {
				handleModifyDoctor(parts, out);
			}
			if ("DELETE_DOCTOR".equals(command)) {
				handleDeleteDoctor(parts, out);
			}
			if ("SHOW_USERS".equals(request)) {
				handleShowUsers(out);
			}
			if ("ADD_USER".equals(command)) {
				handleAddUser(parts, out);
			}
			if ("MODIFY_USER".equals(command)) {
				handleModifyUser(parts, out);
			}
			if ("DELETE_USER".equals(command)) {
				handleDeleteUser(parts, out);
			}
		}

		private void handleDeleteUser(String[] parts, PrintWriter out) {
			if (parts.length < 2) {
				out.println("INVALID_DATA");
				return;
			}

			String nameid = parts[1];

			boolean success = appointmentSystem.deleteUser(nameid);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("USER_NOT_FOUND");
			}
		}

		private void handleModifyUser(String[] parts, PrintWriter out) {
			if (parts.length < 4) {
				out.println("INVALID_DATA");
				return;
			}

			String nameid = parts[1];
			String newPassword = parts[2];
			String newRol = parts[3];

			boolean success = appointmentSystem.modifyUser(nameid, newPassword, newRol);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("USER_NOT_FOUND");
			}
		}

		private void handleAddUser(String[] parts, PrintWriter out) {
			if (parts.length < 4) {
				out.println("INVALID_DATA");
				return;
			}

			String nameid = parts[1];
			String password = parts[2];
			String rol = parts[3];

			boolean success = appointmentSystem.registerUser(nameid, password, rol);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("USER_ALREADY_EXISTS");
			}
		}

		private void handleShowUsers(PrintWriter out) {
			List<User> users = appointmentSystem.viewAllUsers();

			StringBuilder responseBuilder = new StringBuilder();
			for (User user : users) {
				responseBuilder.append(user.getNameid()).append(",").append(user.getPassword()).append(",")
						.append(user.getRol()).append(";");
			}

			if (responseBuilder.length() > 0) {
				responseBuilder.setLength(responseBuilder.length() - 1);
			}

			out.println(responseBuilder.toString());
		}

		private void handleDeleteDoctor(String[] parts, PrintWriter out) {
			if (parts.length < 2) {
				out.println("INVALID_DATA");
				return;
			}

			String professionalId = parts[1];

			boolean success = appointmentSystem.deleteDoctor(professionalId);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("DOCTOR_NOT_FOUND");
			}
		}

		private void handleModifyDoctor(String[] parts, PrintWriter out) {
			if (parts.length < 4) {
				out.println("INVALID_DATA");
				return;
			}

			String professionalId = parts[1];
			String newName = parts[2];
			String specialtyText = parts[3];

			Specialty newSpecialty;
			try {
				newSpecialty = Specialty.valueOf(specialtyText.toUpperCase());
			} catch (IllegalArgumentException e) {
				out.println("INVALID_SPECIALTY");
				return;
			}

			boolean success = appointmentSystem.modifyDoctor(professionalId, newName, newSpecialty);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("DOCTOR_NOT_FOUND");
			}
		}

		private void handleAddDoctor(String[] parts, PrintWriter out) {
			if (parts.length < 4) {
				out.println("INVALID_DATA");
				return;
			}

			String professionalId = parts[1];
			String name = parts[2];
			String specialtyText = parts[3];

			Specialty specialty;
			try {
				specialty = Specialty.valueOf(specialtyText.toUpperCase());
			} catch (IllegalArgumentException e) {
				out.println("INVALID_SPECIALTY");
				return;
			}

			boolean success = appointmentSystem.registerDoctor(professionalId, name, specialty);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("DOCTOR_EXISTS");
			}
		}

		private void handleShowDoctors(PrintWriter out) {
			List<Doctor> doctors = appointmentSystem.viewAllDoctors();

			doctors.sort(Comparator.comparing(Doctor::getName));

			StringBuilder response = new StringBuilder("DOCTOR_LIST");

			for (Doctor doctor : doctors) {
				response.append(",").append(doctor.getProfessionalId()).append(",").append(doctor.getName()).append(",")
						.append(doctor.getSpecialty());
			}

			out.println(response.toString());
		}

		private void handleShowPatients(PrintWriter out) {
			List<Patient> patients = appointmentSystem.viewAllPatients();

			StringBuilder response = new StringBuilder("PATIENT_LIST");

			for (Patient patient : patients) {
				response.append(",").append(patient.getId()).append(",").append(patient.getName()).append(",")
						.append(new SimpleDateFormat("yyyy-MM-dd").format(patient.getBirthDate())).append(",")
						.append(patient.getAddress()).append(",").append(patient.getContact());
			}

			out.println(response.toString());
		}

		private void handleDeletePatient(String[] parts, PrintWriter out) {
			if (parts.length < 2) {
				out.println("INVALID_DATA");
				return;
			}

			String id = parts[1];

			boolean success = appointmentSystem.deletePatient(id);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("PATIENT_NOT_FOUND");
			}
		}

		private void handleModifyPatient(String[] parts, PrintWriter out) {
			if (parts.length < 6) {
				out.println("INVALID_DATA");
				return;
			}

			String id = parts[1];
			String newName = parts[2];
			String birthDateString = parts[3];
			String newAddress = parts[4];
			String newContact = parts[5];

			Date newBirthDate;
			try {
				newBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
			} catch (ParseException e) {
				out.println("INVALID_DATA");
				return;
			}

			boolean success = appointmentSystem.modifyPatient(id, newName, newBirthDate, newAddress, newContact);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("PATIENT_NOT_FOUND");
			}
		}

		private void handleAddPatient(String[] parts, PrintWriter out) {
			if (parts.length < 6) {
				out.println("INVALID_DATA");
				return;
			}

			String id = parts[1];
			String name = parts[2];
			String birthDateString = parts[3];
			String address = parts[4];
			String contact = parts[5];

			Date birthDate;
			try {
				birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateString);
			} catch (ParseException e) {
				out.println("INVALID_DATA");
				return;
			}

			boolean success = appointmentSystem.registerPatient(id, name, birthDate, address, contact);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("PATIENT_EXISTS");
			}
		}

		private void handleCancelAppointment(String[] parts, PrintWriter out) {
			String id = parts[1];
			String patient = parts[2];
			String doctor = parts[3];
			String dateString = parts[4];
			String time = parts[5];
			String reason = parts[6];
			String specialty = parts[7];

			Date date = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = dateFormat.parse(dateString);
			} catch (ParseException e) {
				out.println("INVALID_DATE");
				return;
			}
			boolean success = appointmentSystem.cancelAppointment(id, patient, doctor, date, time, reason,
					Specialty.valueOf(specialty));
			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("FAILURE");
			}
		}

		private void modifyAppointment(String[] parts, PrintWriter out) {
			if (parts.length != 8) {
				out.println("FAILURE,Formato de solicitud incorrecto.");
				return;
			}

			String id = parts[1];
			String patient = parts[2];
			String doctor = parts[3];
			String dateString = parts[4];
			String time = parts[5];
			String reason = parts[6];
			String specialtyText = parts[7];

			if (!appointmentSystem.patientExist(patient)) {
				out.println("FAILURE,El paciente no existe.");
				return;
			}

			if (!appointmentSystem.doctorExist(doctor)) {
				out.println("FAILURE,El doctor no existe.");
				return;
			}

			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			} catch (ParseException e) {
				out.println("FAILURE,La fecha debe estar en el formato yyyy-MM-dd.");
				return;
			}

			Specialty specialty;
			try {
				specialty = Specialty.valueOf(specialtyText.toUpperCase());
			} catch (IllegalArgumentException e) {
				out.println("FAILURE,La especialidad ingresada no es válida.");
				return;
			}

			Specialty doctorSpecialty = appointmentSystem.getDoctorSpecialty(doctor);
			if (doctorSpecialty == null) {
				out.println("FAILURE,No se pudo determinar la especialidad del doctor.");
				return;
			}

			if (!doctorSpecialty.equals(specialty)) {
				out.println("FAILURE,La especialidad del doctor no coincide con la especialidad de la cita.");
				return;
			}
			String validationMessage = appointmentSystem.validateAppointment(id, patient, doctor, date, time,
					specialty);
			if (validationMessage != null) {
				out.println("FAILURE," + validationMessage);
				return;
			}

			boolean success = appointmentSystem.modifyAppointment(id, patient, doctor, date, time, reason, specialty);
			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("FAILURE,La cita no existe.");
			}
		}

		private void handleLogin(String username, String password, PrintWriter out) {
			if (appointmentSystem.userExists(username, password)) {
				User user = appointmentSystem.getUser(username, password);
				String role = user.getRol();
				out.println("SUCCESS," + role);
			} else {
				out.println("FAILURE");
			}
		}

		private void handleAddAppointment(String[] parts, PrintWriter out) {
			if (parts.length < 8) {
				out.println("INVALID_DATA");
				return;
			}

			String id = parts[1];
			String patient = parts[2];
			String doctor = parts[3];
			String dateString = parts[4];
			String time = parts[5];
			String reason = parts[6];
			String specialtyText = parts[7];

			if (!time.matches("^(0[1-9]|1[0-2]):[0-5][0-9] (AM|PM)$")) {
				out.println("INVALID_TIME_FORMAT");
				return;
			}

			Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			} catch (Exception e) {
				out.println("INVALID_DATE_FORMAT");
				return;
			}

			Specialty specialty;
			try {
				specialty = Specialty.valueOf(specialtyText.toUpperCase());
			} catch (IllegalArgumentException e) {
				out.println("Especialidad Invalida");
				return;
			}

			String validationMessage = appointmentSystem.validateAppointment(id, patient, doctor, date, time,
					specialty);
			if (validationMessage != null) {
				out.println(validationMessage);
				return;
			}

			boolean success = appointmentSystem.registerAppointment(id, patient, doctor, date, time, reason, specialty);

			if (success) {
				out.println("SUCCESS");
			} else {
				out.println("APPOINTMENT_ALREADY_EXISTS");
			}
		}

		private void handleShowAppointments(String[] parts, PrintWriter out) {
			String specialtyFilterText = parts.length > 1 ? parts[1] : "";
			String patientNameFilterText = parts.length > 2 ? parts[2] : "";

			List<Appointment> appointments;

			if (!specialtyFilterText.isEmpty()) {
				try {
					Specialty specialty = Specialty.valueOf(specialtyFilterText.toUpperCase());
					appointments = appointmentSystem.viewAppointmentsBySpecialty(specialty);
				} catch (IllegalArgumentException e) {
					out.println("INVALID_SPECIALTY");
					return;
				}
			} else {
				appointments = appointmentSystem.viewAllAppointments();
			}

			if (!patientNameFilterText.isEmpty()) {
				appointments.removeIf(appointment -> !appointment.getPatient().toLowerCase()
						.contains(patientNameFilterText.toLowerCase()));
			}

			out.println("APPOINTMENTS_LIST," + appointments.size());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			for (Appointment appointment : appointments) {
				String appointmentData = String.format("%s,%s,%s,%s,%s,%s,%s", appointment.getId(),
						appointment.getPatient(), appointment.getDoctor(), dateFormat.format(appointment.getDate()),
						appointment.getTime(), appointment.getReason(), appointment.getSpecialty());
				out.println(appointmentData);
			}
		}
	}

}