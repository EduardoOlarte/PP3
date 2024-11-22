package view;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JButton appointmentButton;
    private JButton patientButton;
    private JButton doctorButton;
    private JButton usersButton;
    private JButton logoutButton;
    private JButton exitButton;

    public MenuPanel(View view) {
        setLayout(null);
        setBackground(new Color(102, 204, 255));
        setBounds(0, 0, 217, 700);

        // Botón "Citas"
        appointmentButton = new JButton("Citas");
        appointmentButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        appointmentButton.setBounds(17, 48, 185, 29);
        add(appointmentButton);

        // Botón "Pacientes"
        patientButton = new JButton("Pacientes");
        patientButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        patientButton.setBounds(17, 115, 185, 29);
        add(patientButton);

        // Botón "Doctores"
        doctorButton = new JButton("Doctores");
        doctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        doctorButton.setBounds(17, 176, 185, 29);
        add(doctorButton);

        // Botón "Usuarios"
        usersButton = new JButton("Usuarios");
        usersButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        usersButton.setBounds(17, 237, 185, 29);
        add(usersButton);

        // Botón "Cerrar Sesión"
        logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        logoutButton.setBounds(39, 321, 138, 29);
        add(logoutButton);

        // Botón "Salir"
        exitButton = new JButton("Salir");
        exitButton.setBounds(124, 364, 87, 29);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    // Getters para los botones
    public JButton getAppointmentButton() {
        return appointmentButton;
    }

    public JButton getPatientButton() {
        return patientButton;
    }

    public JButton getDoctorButton() {
        return doctorButton;
    }

    public JButton getUsersButton() {
        return usersButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
