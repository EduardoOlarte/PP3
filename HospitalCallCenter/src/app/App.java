package app;

import control.Control;
import model.AppointmentSystem;
import view.View;

public class App {
	public static void main(String[] args) {
		AppointmentSystem appointmentSystem = new AppointmentSystem();
		appointmentSystem.loadData();
		View view = new View();
		Control control = new Control(view, appointmentSystem);

	}
}
