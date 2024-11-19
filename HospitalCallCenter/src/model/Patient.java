package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient {
	private String id;
	private String name;
	private Date birthDate;
	private String address;
	private String contact;

	public Patient(String id, String name, Date birthDate, String address, String contact) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.address = address;
		this.contact = contact;
	}

	public void updateInfo(Patient newInfo) {
		this.name = newInfo.name;
		this.birthDate = newInfo.birthDate;
		this.address = newInfo.address;
		this.contact = newInfo.contact;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    return String.format(
	        "ID: %s, Nombre: %s, Fecha de Nacimiento: %s, Dirección: %s, Contacto: %s",
	        id,
	        name,
	        birthDate != null ? dateFormat.format(birthDate) : "N/A",
	        address,
	        contact
	    );
	}

}
