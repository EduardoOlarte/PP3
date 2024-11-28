package model;

import structure.AVLTree;
import structure.BinaryTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class DataManager {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public void readPatients(BinaryTree<Patient> patientTree, String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				Patient patient = parsePatient(line);
				if (patient != null) {
					patientTree.add(patient);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Patient parsePatient(String line) {
		String[] parts = line.split(",");
		if (parts.length != 5) {
			return null;
		}

		try {
			String id = parts[0].trim();
			String name = parts[1].trim();
			Date birthDate = DATE_FORMAT.parse(parts[2].trim());
			String address = parts[3].trim();
			String contact = parts[4].trim();

			return new Patient(id, name, birthDate, address, contact);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void savePatients(BinaryTree<Patient> patientTree, String filePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			Iterator<Patient> iterator = patientTree.iterator();
			while (iterator.hasNext()) {
				Patient patient = iterator.next();
				bw.write(formatPatient(patient));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String formatPatient(Patient patient) {
		return String.join(",", patient.getId(), patient.getName(), DATE_FORMAT.format(patient.getBirthDate()),
				patient.getAddress(), patient.getContact());
	}

	public void readUsers(BinaryTree<User> userTree, String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				User user = parseUser(line);
				if (user != null) {
					userTree.add(user);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private User parseUser(String line) {
		String[] parts = line.split(",");
		if (parts.length != 3) {
			return null;
		}

		String nameid = parts[0].trim();
		String password = parts[1].trim();
		String rol = parts[2].trim();

		return new User(nameid, password, rol);
	}

	public void saveUsers(BinaryTree<User> userTree, String filePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			Iterator<User> iterator = userTree.iterator();
			while (iterator.hasNext()) {
				User user = iterator.next();
				bw.write(formatUser(user));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String formatUser(User user) {
		return String.join(",", user.getNameid(), user.getPassword(), user.getRol());
	}

	public void readDoctors(AVLTree<Doctor> doctorTree, String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				Doctor doctor = parseDoctor(line);
				if (doctor != null) {
					doctorTree.add(doctor);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Doctor parseDoctor(String line) {
		String[] parts = line.split(",");
		if (parts.length != 3) {
			return null;
		}

		String professionalId = parts[0].trim();
		String name = parts[1].trim();
		Specialty specialty;

		try {
			specialty = Specialty.valueOf(parts[2].trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}

		return new Doctor(professionalId, name, specialty);
	}

	public void saveDoctors(AVLTree<Doctor> doctorTree, String filePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			Iterator<Doctor> iterator = doctorTree.iterator();
			while (iterator.hasNext()) {
				Doctor doctor = iterator.next();
				bw.write(formatDoctor(doctor));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String formatDoctor(Doctor doctor) {
		return String.join(",", doctor.getProfessionalId(), doctor.getName(), doctor.getSpecialty().toString());
	}

	public void readAppointments(AVLTree<Appointment> appointmentTree, String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				Appointment appointment = parseAppointment(line);
				if (appointment != null) {
					appointmentTree.add(appointment);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Appointment parseAppointment(String line) {
		String[] parts = line.split(",");
		if (parts.length != 7) {
			return null;
		}

		String id = parts[0].trim();
		String patientName = parts[1].trim();
		String doctorName = parts[2].trim();
		Date date;
		try {
			date = DATE_FORMAT.parse(parts[3].trim());
		} catch (ParseException e) {
			return null;
		}
		String time = parts[4].trim();
		String reason = parts[5].trim();
		Specialty specialty;
		try {
			specialty = Specialty.valueOf(parts[6].trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}

		return new Appointment(id, patientName, doctorName, date, time, reason, specialty);
	}

	public void saveAppointments(AVLTree<Appointment> appointmentTree, String filePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			Iterator<Appointment> iterator = appointmentTree.iterator();
			while (iterator.hasNext()) {
				Appointment appointment = iterator.next();
				bw.write(formatAppointment(appointment));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String formatAppointment(Appointment appointment) {
		return String.join(",", appointment.getId(), appointment.getPatient(), appointment.getDoctor(),
				DATE_FORMAT.format(appointment.getDate()), appointment.getTime(), appointment.getReason(),
				appointment.getSpecialty().toString());
	}
}
