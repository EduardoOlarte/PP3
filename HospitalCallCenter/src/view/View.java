package view;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private LoginPanel loginPanel;
    private AppointmentPanel appointmentPanel;
    private MenuPanel menuPanel;

    public View() {
        // Configuración de la ventana
        setTitle("Gestión del Sistema");
        setBounds(100, 100, 233, 438); // Dimensiones iniciales
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        // Inicializar los paneles
        loginPanel = new LoginPanel(this);
        appointmentPanel = new AppointmentPanel(this);
        menuPanel = new MenuPanel(this);

        // Mostrar inicialmente el LoginPanel
        add(loginPanel); // Esto agrega el LoginPanel a la ventana inicialmente
        setVisible(true);
    }

    // Método para cambiar el panel visible
    public void changePanel(JPanel panel, int size) {
        getContentPane().removeAll(); // Limpiar el contenido actual
        add(panel); // Agregar el nuevo panel
        if (size == 0) {
            setBounds(100, 100, 1200, 700); // Dimensiones para el panel principal
        } else {
            setBounds(100, 100, 233, 438); // Dimensiones para el panel de inicio de sesión
        }
        setLocationRelativeTo(null);
        revalidate();
        repaint();
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
}
