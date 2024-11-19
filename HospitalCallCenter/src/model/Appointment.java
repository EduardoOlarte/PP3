package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
	private String id;
	private String patientName;
	private String doctorName;
	private Date date;
	private String time;
	private String reason;
	private Specialty specialty;

	public Appointment(String id, String patient, String doctor, Date date, String time, String reason,
			Specialty specialty) {
		this.id = id;
		this.patientName = patient;
		this.doctorName = doctor;
		this.date = date;
		this.time = time;
		this.reason = reason;
		this.specialty = specialty;
	}

	public void modifyAppointment(Appointment newAppointment) {
		this.patientName = newAppointment.patientName;
		this.doctorName = newAppointment.doctorName;
		this.date = newAppointment.date;
		this.time = newAppointment.time;
		this.reason = newAppointment.reason;
		this.specialty = newAppointment.specialty;
	}

	// Getters y Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatient() {
		return patientName;
	}

	public void setPatient(String patient) {
		this.patientName = patient;
	}

	public String getDoctor() {
		return doctorName;
	}

	public void setDoctor(String doctor) {
		this.doctorName = doctor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return String.format("ID: %s, Paciente: %s, Doctor: %s, Fecha: %s, Hora: %s, Motivo: %s, Especialidad: %s", id,
				patientName, doctorName, date != null ? dateFormat.format(date) : "N/A", time, reason, specialty);
	}

}
