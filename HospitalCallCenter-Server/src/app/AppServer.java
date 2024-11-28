package app;

import java.io.IOException;

import control.ControlServer;
import model.AppointmentSystem;

public class AppServer {
	public static void main(String[] args) throws IOException {
		AppointmentSystem apponymentSystem = new AppointmentSystem();
		ControlServer controlServer = new ControlServer(5000, apponymentSystem);
		controlServer.start();
	}
}
