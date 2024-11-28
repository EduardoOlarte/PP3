package view;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	private LoginPanel loginPanel;
	private AppointmentPanel appointmentPanel;
	private MenuPanel menuPanel;
	private PatientPanel patientPanel;
	private DoctorPanel doctorPanel;
	private UserPanel userPanel;
	private CardLayout cardLayout;
	private JPanel mainPanel;

	public View() {
		setTitle("Gestión del Sistema");
		setBounds(100, 100, 233, 438);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		loginPanel = new LoginPanel(this);
		appointmentPanel = new AppointmentPanel(this);
		menuPanel = new MenuPanel(this);
		patientPanel = new PatientPanel(this);
		doctorPanel = new DoctorPanel(this);
		userPanel = new UserPanel(this);

		mainPanel.add(loginPanel, "loginPanel");
		mainPanel.add(menuPanel, "menuPanel");
		mainPanel.add(appointmentPanel, "appointmentPanel");
		mainPanel.add(patientPanel, "patientPanel");
		mainPanel.add(doctorPanel, "doctorPanel");
		mainPanel.add(userPanel, "userPanel");

		add(mainPanel);
		setVisible(true);

		showPanel("loginPanel");
	}

	public void showPanel(String panelName) {
		cardLayout.show(mainPanel, panelName);
		switch (panelName) {
		case "loginPanel":
			setSize(233, 438);
			break;
		case "menuPanel":
			setSize(233, 438);
			break;
		case "appointmentPanel":
			setSize(1200, 720);
			break;
		case "patientPanel":
			setSize(1200, 720);
			break;
		case "doctorPanel":
			setSize(1200, 720);
			break;
		case "userPanel":
			setSize(1200, 720);
			break;
		default:
			setSize(1200, 720);
			break;
		}
		setLocationRelativeTo(null);
	}

	public LoginPanel getLoginPanel() {
		return loginPanel;
	}

	public AppointmentPanel getAppointmentPanel() {
		return appointmentPanel;
	}

	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	public PatientPanel getPatientPanel() {
		return patientPanel;
	}

	public DoctorPanel getDoctorPanel() {
		return doctorPanel;
	}

	public UserPanel getUserPanel() {
		return userPanel;
	}
}