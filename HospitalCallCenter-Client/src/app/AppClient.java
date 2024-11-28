package app;

import java.io.IOException;

import control.ControlClient;
import view.View;

public class AppClient {
	public static void main(String[] args) throws IOException {
		View view = new View();
		ControlClient controlClient = new ControlClient("Localhost", 5000, view);
		controlClient.init();
	}
}
