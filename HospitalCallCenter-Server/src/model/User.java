package model;

public class User {
	private String nameid;
	private String password;
	private String rol;

	public User(String nameid, String password, String rol) {
		this.nameid = nameid;
		this.password = password;
		this.rol = rol;
	}

	public void updateInfo(User newInfo) {
		this.nameid = newInfo.nameid;
		this.password = newInfo.password;
		this.rol = newInfo.rol;
	}

	public String getNameid() {
		return nameid;
	}

	public void setNameid(String nameid) {
		this.nameid = nameid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return String.format("NameID: %s, Rol: %s", nameid, rol);
	}

}
