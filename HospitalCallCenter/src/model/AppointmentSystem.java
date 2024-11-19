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
		this.appointmentTree = new AVLTree<>(Comparator.comparing(Appointment::getId));
		this.dataManager = new DataManager();
		filePatients = "Data/Pacientes.txt";
		fileUsers = "Data/Usuarios.txt";
		fileDoctors = "Data/Doctores.txt";
		fileAppointments = "Data/Citas.txt";

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

	// Registrar una nueva cita
	public boolean registerAppointment(String id, String patientName, String doctorName, Date date, String time,
			String reason, Specialty specialty) {
		if (appointmentTree.contains(new Appointment(id, null, null, null, null, null, null))) {
			return false; // Ya existe una cita con este ID
		}

		Appointment newAppointment = new Appointment(id, patientName, doctorName, date, time, reason, specialty);
		return appointmentTree.add(newAppointment);
	}

	// Modificar una cita existente
	public boolean modifyAppointment(String id, String newPatientName, String newDoctorName, Date newDate,
			String newTime, String newReason, Specialty newSpecialty) {
		Appointment target = new Appointment(id, null, null, null, null, null, null);
		NodeTreeAvl<Appointment> node = appointmentTree.search(target);

		if (node == null) {
			return false; // No existe la cita
		}

		Appointment existingAppointment = node.getData();
		Appointment updatedAppointment = new Appointment(id, newPatientName, newDoctorName, newDate, newTime, newReason,
				newSpecialty);
		existingAppointment.modifyAppointment(updatedAppointment);
		return true;
	}

	// Cancelar una cita existente
	public boolean cancelAppointment(String id) {
		Appointment target = new Appointment(id, null, null, null, null, null, null);
		return appointmentTree.remove(target);
	}

	public List<Appointment> viewAllAppointments() {
		List<Appointment> appointmentList = new ArrayList<>();
		Iterator<Appointment> iterator = appointmentTree.iterator();

		while (iterator.hasNext()) {
			appointmentList.add(iterator.next());
		}

		return appointmentList;
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

	// Mostrar todos los doctores
	public List<Doctor> viewAllDoctors() {
		List<Doctor> doctorList = new ArrayList<>();
		Iterator<Doctor> iterator = doctorTree.iterator();

		while (iterator.hasNext()) {
			doctorList.add(iterator.next());
		}

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
		Patient updatedPatient = new Patient(id, newName, newBirthDate, newAddress, newContact);
		existingPatient.updateInfo(updatedPatient);
		return true;
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
		User updatedUser = new User(nameid, newPassword, newRol);
		existingUser.updateInfo(updatedUser);
		return true;
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

		return userList;
	}

	// Buscar un usuario por nameid
	public User findUserByNameId(String nameid) {
		User target = new User(nameid, null, null);
		NodeTree<User> node = userTree.search(target);

		return (node != null) ? node.getData() : null;
	}

}
