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
		filePatients = "data/Pacientes.txt";
		fileUsers = "data/Usuarios.txt";
		fileDoctors = "data/Doctores.txt";
		fileAppointments = "data/Citas.txt";
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

		Appointment newAppointment = new Appointment(id, patient, doctor, date, time, reason, specialty);

		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getId().equals(id)) {
				return false;
			}
		}

		iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getPatient().equals(patient) && appointment.getDate().equals(date)
					&& appointment.getTime().equals(time)) {
				return false;
			}
		}

		iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getPatient().equals(patient) && appointment.getSpecialty().equals(specialty)) {
				return false;
			}
		}

		appointmentTree.add(newAppointment);
		return true;
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
				return true;
			}
		}
		return false;
	}

	public boolean cancelAppointment(String id, String patient, String doctor, Date date, String time, String reason,
			Specialty specialty) {
		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getId().equals(id) && appointment.getPatient().equals(patient)
					&& appointment.getDoctor().equals(doctor) && appointment.getDate().equals(date)
					&& appointment.getTime().equals(time) && appointment.getReason().equals(reason)
					&& appointment.getSpecialty().equals(specialty)) {
				appointmentTree.remove(appointment);
				return true;
			}
		}
		return false;
	}

	public List<Appointment> viewAllAppointments() {
		List<Appointment> appointmentList = new ArrayList<>();

		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			appointmentList.add(iterator.next());
		}

		return appointmentList;
	}

	public List<Appointment> viewAppointmentsBySpecialty(Specialty specialty) {
		List<Appointment> appointmentList = new ArrayList<>();

		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();

			if (appointment.getSpecialty() == specialty) {
				appointmentList.add(appointment);
			}
		}

		return appointmentList;
	}

	public Appointment viewAppointmentById(String id) {
		Appointment target = new Appointment(id, null, null, null, null, null, null);
		NodeTreeAvl<Appointment> node = appointmentTree.search(target);

		return (node != null) ? node.getData() : null;
	}

	public boolean registerDoctor(String professionalId, String name, Specialty specialty) {
		Doctor newDoctor = new Doctor(professionalId, name, specialty);
		if (doctorTree.contains(newDoctor)) {
			return false;
		}
		return doctorTree.add(newDoctor);
	}

	public boolean modifyDoctor(String professionalId, String newName, Specialty newSpecialty) {
		Doctor target = new Doctor(professionalId, null, null);
		NodeTreeAvl<Doctor> node = doctorTree.search(target);

		if (node == null) {
			return false;
		}

		Doctor existingDoctor = node.getData();
		Doctor updatedDoctor = new Doctor(professionalId, newName, newSpecialty);
		existingDoctor.updateInfo(updatedDoctor);
		return true;
	}

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

		doctorList.sort(Comparator.comparing(Doctor::getName));

		return doctorList;
	}

	public Doctor viewDoctorById(String professionalId) {
		Doctor target = new Doctor(professionalId, null, null);
		NodeTreeAvl<Doctor> node = doctorTree.search(target);

		return (node != null) ? node.getData() : null;
	}

	public boolean registerPatient(String id, String name, Date birthDate, String address, String contact) {
		Patient newPatient = new Patient(id, name, birthDate, address, contact);
		if (patientTree.contains(newPatient)) {
			return false;
		}
		return patientTree.add(newPatient);
	}

	public boolean modifyPatient(String id, String newName, Date newBirthDate, String newAddress, String newContact) {
		Patient target = new Patient(id, null, null, null, null);
		NodeTree<Patient> node = patientTree.search(target);

		if (node == null) {
			return false;
		}

		Patient existingPatient = node.getData();
		existingPatient.setName(newName);
		existingPatient.setBirthDate(newBirthDate);
		existingPatient.setAddress(newAddress);
		existingPatient.setContact(newContact);
		return true;
	}

	public boolean deletePatient(String id) {
		Patient target = new Patient(id, null, null, null, null);
		return patientTree.remove(target);
	}

	public List<Patient> viewAllPatients() {
		List<Patient> patientList = new ArrayList<>();
		Iterator<Patient> iterator = patientTree.iterator();

		while (iterator.hasNext()) {
			patientList.add(iterator.next());
		}

		patientList.sort(Comparator.comparing(Patient::getName));

		return patientList;
	}

	public Patient findPatientById(String id) {
		Patient target = new Patient(id, null, null, null, null);
		NodeTree<Patient> node = patientTree.search(target);

		return (node != null) ? node.getData() : null;
	}

	public boolean registerUser(String nameid, String password, String rol) {
		User newUser = new User(nameid, password, rol);
		if (userTree.contains(newUser)) {
			return false;
		}
		return userTree.add(newUser);
	}

	public boolean modifyUser(String nameid, String newPassword, String newRol) {
		User target = new User(nameid, null, null);
		NodeTree<User> node = userTree.search(target);

		if (node == null) {
			return false;
		}

		User existingUser = node.getData();
		existingUser.setPassword(newPassword);
		existingUser.setRol(newRol);
		return true;
	}

	public boolean deleteUser(String nameid) {
		User target = new User(nameid, null, null);
		return userTree.remove(target);
	}

	public List<User> viewAllUsers() {
		List<User> userList = new ArrayList<>();
		Iterator<User> iterator = userTree.iterator();

		while (iterator.hasNext()) {
			userList.add(iterator.next());
		}

		userList.sort(Comparator.comparing(User::getNameid));

		return userList;
	}

	public User findUserByNameId(String nameid) {
		User target = new User(nameid, null, null);
		NodeTree<User> node = userTree.search(target);

		return (node != null) ? node.getData() : null;
	}

	public String validateAppointment(String id, String patientName, String doctorName, Date date, String time,
			Specialty specialty) {
		if (!patientExist(patientName)) {
			return "El paciente no existe.";
		}

		if (!doctorExist(doctorName)) {
			return "El doctor no existe.";
		}

		Specialty doctorSpecialty = getDoctorSpecialty(doctorName);
		if (doctorSpecialty == null) {
			return "No se pudo determinar la especialidad del doctor.";
		}

		if (!doctorSpecialty.equals(specialty)) {
			return "La especialidad del doctor no coincide con la especialidad de la cita.";
		}

		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if (appointment.getId().equals(id)) {
				return "La cita con el mismo ID ya existe.";
			}
			if (appointment.getPatient().equals(patientName) && appointment.getDate().equals(date)
					&& appointment.getTime().equals(time)) {
				return "El paciente ya tiene una cita en la misma fecha y hora.";
			}
			if (appointment.getDate().equals(date) && appointment.getTime().equals(time)
					&& appointment.getDoctor().equals(doctorName)) {
				return "El Doctor ya tiene una cita en la misma fecha y hora.";
			}
			if (appointment.getPatient().equals(patientName) && appointment.getSpecialty().equals(specialty)) {
				return "El paciente ya tiene una cita con la misma especialidad.";
			}
		}
		return null;
	}

	public String validateModify(String id, String patientName, String doctorName, Date date, String time,
			Specialty specialty) {

		Iterator<Appointment> iterator = appointmentTree.iterator();
		while (iterator.hasNext()) {
			Appointment appointment = iterator.next();

			if (appointment.getPatient().equals(patientName) && appointment.getDate().equals(date)
					&& appointment.getTime().equals(time)) {
				return "El paciente ya tiene una cita en la misma fecha y hora.";
			}
			if (appointment.getDate().equals(date) && appointment.getTime().equals(time)
					&& appointment.getDoctor().equals(doctorName)) {
				return "El Doctor ya tiene una cita en la misma fecha y hora.";
			}
		}
		return null;
	}

	public Specialty getDoctorSpecialty(String doctorName) {
		Iterator<Doctor> doctorIterator = doctorTree.iterator();
		while (doctorIterator.hasNext()) {
			Doctor doctor = doctorIterator.next();
			if (doctor.getName().equals(doctorName)) {
				return doctor.getSpecialty();
			}
		}
		return null;
	}

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
