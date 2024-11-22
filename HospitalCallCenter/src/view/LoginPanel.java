package view;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;

    public LoginPanel(View view) {
        setBackground(new Color(102, 204, 255));
        setLayout(null);
        setBounds(0, 0, 1200, 700);

        JLabel loginLabel = new JLabel("Inicio de Sesion");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        loginLabel.setBounds(17, 26, 185, 29);
        add(loginLabel);

        JLabel userLabel = new JLabel("Usuario");
        userLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        userLabel.setBounds(17, 79, 185, 29);
        add(userLabel);

        userField = new JTextField();
        userField.setBounds(17, 110, 185, 37);
        add(userField);
        userField.setColumns(10);

        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        passwordLabel.setBounds(17, 158, 185, 29);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(17, 183, 185, 37);
        add(passwordField);

        loginButton = new JButton("Iniciar");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        loginButton.setBounds(17, 250, 185, 29);
        add(loginButton);

        exitButton = new JButton("Salir");
        exitButton.setBounds(115, 347, 87, 29);
        exitButton.addActionListener(e -> System.exit(0)); // Cierra la aplicación
        add(exitButton);

        // Acción para el botón "Iniciar"
    }

    // Getters para los campos y botones
    public JTextField getUserField() {
        return userField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
