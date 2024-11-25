package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AppointmentPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField idAppointmentField, doctorAppointmentField, patientAppointmentField, timeField,
            dateAppointmentField, specialtyField, reasonField, specialtyFilterField, patientNameFilterField;
    private JTable appointmentTable;
    private JButton addAppointmentButton, modifyAppointmentButton, cancelAppointmentButton, showAppointmentsButton,
            exitButton, menuButton, backLoginButton;

    public AppointmentPanel(View view) {
        setLayout(null);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setBackground(new Color(135, 206, 235));
        setBounds(0, 0, 1184, 661);

        // Botones
        addAppointmentButton = new JButton("Agregar Cita");
        addAppointmentButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        addAppointmentButton.setBounds(17, 69, 185, 50);
        add(addAppointmentButton);

        modifyAppointmentButton = new JButton("Modificar Cita");
        modifyAppointmentButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        modifyAppointmentButton.setBounds(17, 151, 185, 50);
        add(modifyAppointmentButton);

        cancelAppointmentButton = new JButton("Eliminar Cita");
        cancelAppointmentButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        cancelAppointmentButton.setBounds(17, 232, 185, 57);
        add(cancelAppointmentButton);

        showAppointmentsButton = new JButton("Ver Citas");
        showAppointmentsButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        showAppointmentsButton.setBounds(17, 314, 185, 50);
        add(showAppointmentsButton);

        exitButton = new JButton("Salir");
        exitButton.setBounds(116, 548, 87, 29);
        add(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        menuButton = new JButton("Menu");
        menuButton.setBounds(17, 548, 87, 29);
        menuButton.addActionListener(e -> view.showPanel("menuPanel")); // Acción para mostrar el LoginPanel
        add(menuButton);

        backLoginButton = new JButton("Cerrar Sesion");
        backLoginButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        backLoginButton.setBounds(41, 480, 138, 44);
        backLoginButton.addActionListener(e -> view.showPanel("loginPanel")); // Acción para mostrar el LoginPanel
        add(backLoginButton);

        // Etiquetas y campos de texto
        JLabel idAppointmentLabel = new JLabel("Id Cita");
        idAppointmentLabel.setBounds(219, 75, 152, 22);
        add(idAppointmentLabel);

        idAppointmentField = new JTextField();
        idAppointmentField.setColumns(10);
        idAppointmentField.setBounds(219, 97, 152, 28);
        add(idAppointmentField);

        JLabel patientAppointmentLabel = new JLabel("Nombre Paciente");
        patientAppointmentLabel.setBounds(219, 137, 152, 22);
        add(patientAppointmentLabel);

        patientAppointmentField = new JTextField();
        patientAppointmentField.setColumns(10);
        patientAppointmentField.setBounds(219, 160, 152, 28);
        add(patientAppointmentField);

        JLabel doctorAppointmentLabel = new JLabel("Doctor");
        doctorAppointmentLabel.setBounds(219, 199, 152, 22);
        add(doctorAppointmentLabel);

        doctorAppointmentField = new JTextField();
        doctorAppointmentField.setColumns(10);
        doctorAppointmentField.setBounds(219, 221, 152, 28);
        add(doctorAppointmentField);

        JLabel dateAppointmentLabel = new JLabel("Fecha Cita");
        dateAppointmentLabel.setBounds(219, 270, 152, 22);
        add(dateAppointmentLabel);

        dateAppointmentField = new JTextField();
        dateAppointmentField.setColumns(10);
        dateAppointmentField.setBounds(219, 292, 152, 28);
        add(dateAppointmentField);

        JLabel timeLabel = new JLabel("Hora Cita");
        timeLabel.setBounds(219, 330, 152, 22);
        add(timeLabel);

        timeField = new JTextField();
        timeField.setColumns(10);
        timeField.setBounds(219, 352, 152, 28);
        add(timeField);

        JLabel reasonLabel = new JLabel("Motivo");
        reasonLabel.setBounds(219, 402, 152, 22);
        add(reasonLabel);

        reasonField = new JTextField();
        reasonField.setColumns(10);
        reasonField.setBounds(219, 425, 152, 28);
        add(reasonField);

        JLabel specialtyLabel = new JLabel("Especialidad");
        specialtyLabel.setBounds(219, 462, 152, 22);
        add(specialtyLabel);

        specialtyField = new JTextField();
        specialtyField.setColumns(10);
        specialtyField.setBounds(219, 484, 152, 28);
        add(specialtyField);

        JLabel specialtyFilterLabel = new JLabel("Filtro de Especialidad");
        specialtyFilterLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        specialtyFilterLabel.setBounds(27, 376, 152, 22);
        add(specialtyFilterLabel);

        specialtyFilterField = new JTextField();
        specialtyFilterField.setColumns(10);
        specialtyFilterField.setBounds(27, 398, 152, 28);
        add(specialtyFilterField);

        JLabel patientNameFilterLabel = new JLabel("Nombre de Paciente");
        patientNameFilterLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        patientNameFilterLabel.setBounds(27, 420, 200, 22);
        add(patientNameFilterLabel);

        patientNameFilterField = new JTextField();
        patientNameFilterField.setColumns(10);
        patientNameFilterField.setBounds(27, 440, 152, 28);
        add(patientNameFilterField);

        // Tabla
        JScrollPane scrollPaneAppointment = new JScrollPane();
        scrollPaneAppointment.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneAppointment.setBounds(396, 48, 782, 586);
        add(scrollPaneAppointment);

        appointmentTable = new JTable();
        appointmentTable.setModel(new DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null },
                },
                new String[] {
                        "Id Cita", "Paciente", "Doctor", "Fecha de Cita", "Hora de Cita", "Motivo", "Especialidad"
                }));
        scrollPaneAppointment.setViewportView(appointmentTable);
    }

    // Getters para componentes
    public JButton getAddAppointmentButton() {
        return addAppointmentButton;
    }

    public JButton getModifyAppointmentButton() {
        return modifyAppointmentButton;
    }

    public JButton getCancelAppointmentButton() {
        return cancelAppointmentButton;
    }

    public JButton getShowAppointmentsButton() {
        return showAppointmentsButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getMenuButton() {
        return menuButton;
    }

    public JButton getBackLoginButton() {
        return backLoginButton;
    }

    public JTextField getIdAppointmentField() {
        return idAppointmentField;
    }

    public JTextField getDoctorAppointmentField() {
        return doctorAppointmentField;
    }

    public JTextField getPatientAppointmentField() {
        return patientAppointmentField;
    }

    public JTextField getTimeField() {
        return timeField;
    }

    public JTextField getDateAppointmentField() {
        return dateAppointmentField;
    }

    public JTextField getSpecialtyField() {
        return specialtyField;
    }

    public JTextField getReasonField() {
        return reasonField;
    }

    public JTextField getSpecialtyFilterField() {
        return specialtyFilterField;
    }

    public JTable getAppointmentTable() {
        return appointmentTable;
    }
    public JTextField getPatientNameFilterField() {
        return patientNameFilterField;
    }
}
