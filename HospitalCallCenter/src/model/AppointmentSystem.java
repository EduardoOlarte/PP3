package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import structure.AVLTree;
import structure.BinaryTree;
import structure.NodeTree;
import structure.NodeTreeAvl;

public class AppointmentSystem {
	private BinaryTree<Patient> patientTree;
	private BinaryTree<User> userTree;
	private AVLTree<Doctor> doctorTree;
	private AVLTree<Appointment> appointmentTree;
	private DataManager dataManager;

	private String filePatients;
	private String fileUsers;
	private String fileDoctors;
	private String fileAppointments;

	public AppointmentSystem() {
		this.patientTree = new BinaryTree<>(Comparator.comparing(Patient::getId));
		this.userTree = new BinaryTree<>(Comparator.comparing(User::getNameid));
		this.doctorTree = new AVLTree<>(Comparator.comparing(Doctor::getProfessionalId));
		this.appointmentTree = new AVLTree<>(new AppointmentComparator());

		this.dataManager = new DataManager();
		filePatients = "Data/Pacientes.txt";
		fileUsers = "Data/Usuarios.txt";
		fileDoctors = "Data/Doctores.txt";
		fileAppointments = "Data/Citas.txt";
		loadData();
	}

	public void loadData() {
		dataManager.readPatients(patientTree, filePatients);
		dataManager.readUsers(userTree, fileUsers);
		dataManager.readDoctors(doctorTree, fileDoctors);
		dataManager.readAppointments(appointmentTree, fileAppointments);
	}

	public void saveData() {
		dataManager.savePatients(patientTree, filePatients);
		dataManager.saveUsers(userTree, fileUsers);
		dataManager.saveDoctors(doctorTree, fileDoctors);
		dataManager.saveAppointments(appointmentTree, fileAppointments);
	}

	public boolean registerAppointment(String id, String patient, String doctor, Date date, String time, String reason,
			Specialty specialty) {
		if (date == null || time == null || time.isEmpty()) {
			throw new IllegalArgumentException("La cita debe tener fecha y hora válidas.");
		}

		// Proceder con la lógica para registrar la cita
		Appointment newAppointment = new Appointment(id, patient, doctor, date, time, reason, specialty);

		// Verifica si una cita con el mismo ID ya existe
		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getId().equals(id)) {
				return false; // La cita con el mismo ID ya existe
			}
		}

		// Verifica si una cita para el mismo paciente con la misma fecha y hora ya
		// existe
		iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getPatient().equals(patient) &&
					appointment.getDate().equals(date) &&
					appointment.getTime().equals(time)) {
				return false; // El paciente ya tiene una cita en la misma fecha y hora
			}
		}

		// Verifica si el paciente ya tiene una cita con la misma especialidad
		iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getPatient().equals(patient) &&
					appointment.getSpecialty().equals(specialty)) {
				return false; // El paciente ya tiene una cita con la misma especialidad
			}
		}

		// Si no existe, agregar la cita al árbol
		appointmentTree.add(newAppointment);
		return true; // Registro exitoso
	}

	public boolean modifyAppointment(String id, String patient, String doctor, Date date, String time, String reason,
			Specialty specialty) {
		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getId().equals(id) && appointment.getPatient().equals(patient)) {
				appointment.setDoctor(doctor);
				appointment.setDate(date);
				appointment.setTime(time);
				appointment.setReason(reason);
				appointment.setSpecialty(specialty);
				return true; // Modificación exitosa
			}
		}
		return false; // La cita no existe
	}

	// Cancelar una cita existente
	public boolean cancelAppointment(String id, String patient, String doctor, Date date, String time, String reason,
			Specialty specialty) {
		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getId().equals(id) &&
					appointment.getPatient().equals(patient) &&
					appointment.getDoctor().equals(doctor) &&
					appointment.getDate().equals(date) &&
					appointment.getTime().equals(time) &&
					appointment.getReason().equals(reason) &&
					appointment.getSpecialty().equals(specialty)) {
				appointmentTree.remove(appointment);
				return true; // Cancelación exitosa
			}
		}
		return false; // La cita no existe
	}

	public List<Appointment> viewAllAppointments() {
		List<Appointment> appointmentList = new ArrayList<>();

		// Recorrer el árbol en orden
		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			appointmentList.add(iterator.next());
		}

		return appointmentList; // Lista siempre ordenada por el recorrido en orden del árbol
	}

	public List<Appointment> viewAppointmentsBySpecialty(Specialty specialty) {
		List<Appointment> appointmentList = new ArrayList<>();

		// Recorrer el árbol en orden
		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();

			// Filtrar por especialidad
			if (appointment.getSpecialty() == specialty) {
				appointmentList.add(appointment);
			}
		}

		return appointmentList; // Lista ordenada y filtrada
	}

	// Mostrar una cita por su id
	public Appointment viewAppointmentById(String id) {
		Appointment target = new Appointment(id, null, null, null, null, null, null);
		NodeTreeAvl<Appointment> node = appointmentTree.search(target);

		return (node != null) ? node.getData() : null; // Retorna la cita si la encuentra, de lo contrario null
	}

	// Registrar un nuevo doctor
	public boolean registerDoctor(String professionalId, String name, Specialty specialty) {
		Doctor newDoctor = new Doctor(professionalId, name, specialty);
		if (doctorTree.contains(newDoctor)) {
			return false; // Ya existe un doctor con este ID
		}
		return doctorTree.add(newDoctor);
	}

	// Modificar un doctor existente
	public boolean modifyDoctor(String professionalId, String newName, Specialty newSpecialty) {
		Doctor target = new Doctor(professionalId, null, null);
		NodeTreeAvl<Doctor> node = doctorTree.search(target);

		if (node == null) {
			return false; // No se encontró el doctor
		}

		Doctor existingDoctor = node.getData();
		Doctor updatedDoctor = new Doctor(professionalId, newName, newSpecialty);
		existingDoctor.updateInfo(updatedDoctor);
		return true;
	}

	// Eliminar un doctor
	public boolean deleteDoctor(String professionalId) {
		Doctor target = new Doctor(professionalId, null, null);
		return doctorTree.remove(target);
	}

	public List<Doctor> viewAllDoctors() {
		List<Doctor> doctorList = new ArrayList<>();
		Iterator<Doctor> iterator = doctorTree.iterator();

		while (iterator.hasNext()) {
			doctorList.add(iterator.next());
		}

		// Ordenar la lista de doctores por nombre
		doctorList.sort(Comparator.comparing(Doctor::getName));

		return doctorList;
	}

	// Mostrar un doctor por su professionalId
	public Doctor viewDoctorById(String professionalId) {
		Doctor target = new Doctor(professionalId, null, null);
		NodeTreeAvl<Doctor> node = doctorTree.search(target);

		return (node != null) ? node.getData() : null; // Retorna el doctor si lo encuentra, de lo contrario null
	}

	// Registrar un nuevo paciente
	public boolean registerPatient(String id, String name, Date birthDate, String address, String contact) {
		Patient newPatient = new Patient(id, name, birthDate, address, contact);
		if (patientTree.contains(newPatient)) {
			return false; // Ya existe un paciente con este ID
		}
		return patientTree.add(newPatient);
	}

	// Modificar un paciente existente
	public boolean modifyPatient(String id, String newName, Date newBirthDate, String newAddress, String newContact) {
		Patient target = new Patient(id, null, null, null, null);
		NodeTree<Patient> node = patientTree.search(target);

		if (node == null) {
			return false; // No se encontró el paciente
		}

		Patient existingPatient = node.getData();
		existingPatient.setName(newName);
		existingPatient.setBirthDate(newBirthDate);
		existingPatient.setAddress(newAddress);
		existingPatient.setContact(newContact);
		return true; // Modificación exitosa
	}

	// Eliminar un paciente
	public boolean deletePatient(String id) {
		Patient target = new Patient(id, null, null, null, null);
		return patientTree.remove(target);
	}

	// Mostrar todos los pacientes
	public List<Patient> viewAllPatients() {
		List<Patient> patientList = new ArrayList<>();
		Iterator<Patient> iterator = patientTree.iterator();

		while (iterator.hasNext()) {
			patientList.add(iterator.next());
		}

		// Ordenar la lista de pacientes por la primera letra de su nombre
		patientList.sort(Comparator.comparing(Patient::getName));

		return patientList;
	}

	// Buscar un paciente por ID
	public Patient findPatientById(String id) {
		Patient target = new Patient(id, null, null, null, null);
		NodeTree<Patient> node = patientTree.search(target);

		return (node != null) ? node.getData() : null;
	}

	// Registrar un nuevo usuario
	public boolean registerUser(String nameid, String password, String rol) {
		User newUser = new User(nameid, password, rol);
		if (userTree.contains(newUser)) {
			return false; // Ya existe un usuario con este nameid
		}
		return userTree.add(newUser);
	}

	// Modificar un usuario existente
	public boolean modifyUser(String nameid, String newPassword, String newRol) {
		User target = new User(nameid, null, null);
		NodeTree<User> node = userTree.search(target);

		if (node == null) {
			return false; // No se encontró el usuario
		}

		User existingUser = node.getData();
		existingUser.setPassword(newPassword);
		existingUser.setRol(newRol);
		return true; // Modificación exitosa
	}

	// Eliminar un usuario
	public boolean deleteUser(String nameid) {
		User target = new User(nameid, null, null);
		return userTree.remove(target);
	}

	// Mostrar todos los usuarios
	public List<User> viewAllUsers() {
		List<User> userList = new ArrayList<>();
		Iterator<User> iterator = userTree.iterator();

		while (iterator.hasNext()) {
			userList.add(iterator.next());
		}

		// Ordenar la lista de usuarios por ID
		userList.sort(Comparator.comparing(User::getNameid));

		return userList;
	}

	// Buscar un usuario por nameid
	public User findUserByNameId(String nameid) {
		User target = new User(nameid, null, null);
		NodeTree<User> node = userTree.search(target);

		return (node != null) ? node.getData() : null;
	}

	// Método auxiliar para validar la cita
	public String validateAppointment(String id, String patientName, String doctorName, Date date, String time,
			Specialty specialty) {
		if (!patientExist(patientName)) {
			return "El paciente no existe.";
		}

		// Verificar que el doctor exista
		if (!doctorExist(doctorName)) {
			return "El doctor no existe.";
		}

		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getId().equals(id)) {
				return "La cita con el mismo ID ya existe.";
			}
			if (appointment.getPatient().equals(patientName) &&
					appointment.getDate().equals(date) &&
					appointment.getTime().equals(time)) {
				return "El paciente ya tiene una cita en la misma fecha y hora.";
			}
			if (appointment.getDate().equals(date) &&
					appointment.getTime().equals(time) &&
					appointment.getDoctor().equals(doctorName)) {
				return "El Doctor ya tiene una cita en la misma fecha y hora.";
			}
			if (appointment.getPatient().equals(patientName) &&
					appointment.getSpecialty().equals(specialty)) {
				return "El paciente ya tiene una cita con la misma especialidad.";
			}
		}
		return null; // No hay conflictos
	}

	// Método para verificar si un paciente existe
	public boolean patientExist(String patientName) {
		Iterator<Patient> patientIterator = patientTree.iterator();
		while (patientIterator.hasNext()) {
			Patient patient = patientIterator.next();
			if (patient.getName().equals(patientName)) {
				return true;
			}
		}
		return false;
	}

	public boolean patientExists(String patientId) {
		Iterator<Patient> iterator = patientTree.iterator();
		while (iterator.hasNext()) {
			Patient patient = iterator.next();
			if (patient.getId().equals(patientId)) {
				return true;
			}
		}
		return false;
	}

	public Patient getPatientById(String patientId) {
		Iterator<Patient> iterator = patientTree.iterator();
		while (iterator.hasNext()) {
			Patient patient = iterator.next();
			if (patient.getId().equals(patientId)) {
				return patient;
			}
		}
		return null;
	}

	// Método para verificar si un doctor existe
	public boolean doctorExist(String doctorName) {
		Iterator<Doctor> doctorIterator = doctorTree.iterator();
		while (doctorIterator.hasNext()) {
			Doctor doctor = doctorIterator.next();
			if (doctor.getName().equals(doctorName)) {
				return true;
			}
		}
		return false;
	}

	public boolean userExists(String nameid, String password) {
		Iterator<User> iterator = userTree.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getNameid().equals(nameid) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	public User getUser(String nameid, String password) {
		Iterator<User> iterator = userTree.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getNameid().equals(nameid) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

}
