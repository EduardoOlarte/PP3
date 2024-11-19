package model;

public class Doctor {
	private String professionalId;
	private String name;
	private Specialty specialty;

	public Doctor(String professionalId, String name, Specialty specialty) {
		this.professionalId = professionalId;
		this.name = name;
		this.specialty = specialty;
	}

	public void updateInfo(Doctor newInfo) {
		this.professionalId = newInfo.professionalId;
		this.name = newInfo.name;
		this.specialty = newInfo.specialty;
	}

	// Getters y Setters
	public String getProfessionalId() {
		return professionalId;
	}

	public void setProfessionalId(String professionalId) {
		this.professionalId = professionalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	@Override
	public String toString() {
		return String.format("ID Profesional: %s, Nombre: %s, Especialidad: %s", professionalId, name, specialty);
	}

}
