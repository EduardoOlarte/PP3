package view;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private LoginPanel loginPanel;
    private AppointmentPanel appointmentPanel;
    private MenuPanel menuPanel;
    private PatientPanel patientPanel;
    private DoctorPanel doctorPanel;
    private UserPanel userPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public View() {
        // Configuración de la ventana
        setTitle("Gestión del Sistema");
        setBounds(100, 100, 233, 438); // Dimensiones iniciales
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        // Inicializar el CardLayout y el panel principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Inicializar los paneles
        loginPanel = new LoginPanel(this);
        appointmentPanel = new AppointmentPanel(this);
        menuPanel = new MenuPanel(this);
        patientPanel = new PatientPanel(this);
        doctorPanel = new DoctorPanel(this);
        userPanel = new UserPanel(this);

        // Agregar los paneles al CardLayout
        mainPanel.add(loginPanel, "loginPanel");
        mainPanel.add(menuPanel, "menuPanel");
        mainPanel.add(appointmentPanel, "appointmentPanel");
        mainPanel.add(patientPanel, "patientPanel");
        mainPanel.add(doctorPanel, "doctorPanel");
        mainPanel.add(userPanel, "userPanel");

        // Agregar el panel principal al JFrame
        add(mainPanel);
        setVisible(true);

        // Mostrar inicialmente el LoginPanel
        showPanel("loginPanel");
    }

    // Método para mostrar un panel específico y redimensionar la ventana
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
        setLocationRelativeTo(null); // Centrar la ventana
    }

    // Getters para los paneles
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