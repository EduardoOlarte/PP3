package app;

import control.Control;
import model.AppointmentSystem;
import view.View;

public class App {
	public static void main(String[] args) {
		AppointmentSystem appointmentSystem = new AppointmentSystem();
		View view = new View();
		Control control = new Control(view, appointmentSystem);
		control.init();

	}
}
