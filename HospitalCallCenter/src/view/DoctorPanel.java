package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DoctorPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField idDoctorField, nameDoctorField, specialtyDoctorField;
    private JTable doctorTable;
    private JButton addDoctorButton, modifyDoctorButton, removeDoctorButton, showDoctorsButton, exitButton, menuButton, backLoginButton;

    public DoctorPanel(View view) {
        setLayout(null);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setBackground(new Color(102, 204, 255));
        setBounds(0, 0, 1184, 661);

        // Botones
        addDoctorButton = new JButton("Agregar Doctor");
        addDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        addDoctorButton.setBounds(17, 69, 185, 50);
        add(addDoctorButton);

        modifyDoctorButton = new JButton("Modificar Doctor");
        modifyDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        modifyDoctorButton.setBounds(17, 151, 185, 50);
        add(modifyDoctorButton);

        removeDoctorButton = new JButton("Eliminar Doctor");
        removeDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        removeDoctorButton.setBounds(17, 232, 185, 57);
        add(removeDoctorButton);

        showDoctorsButton = new JButton("Ver Doctores");
        showDoctorsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        showDoctorsButton.setBounds(17, 314, 185, 50);
        add(showDoctorsButton);

        exitButton = new JButton("Salir");
        exitButton.setBounds(116, 548, 87, 29);
        add(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        menuButton = new JButton("Menu");
        menuButton.setBounds(17, 548, 87, 29);
        add(menuButton);
        menuButton.addActionListener(e -> {
            // Cambiar al LoginPanel
            view.changePanel(new MenuPanel(view),1);
        });

        backLoginButton = new JButton("Cerrar Sesion");
        backLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        backLoginButton.setBounds(41, 480, 138, 44);
        add(backLoginButton);
        backLoginButton.addActionListener(e -> {
            // Cambiar al LoginPanel
            view.changePanel(new LoginPanel(view),1);
        });

        // Etiquetas y campos de texto
        JLabel idDoctorLabel = new JLabel("Id");
        idDoctorLabel.setBounds(219, 168, 152, 22);
        add(idDoctorLabel);

        idDoctorField = new JTextField();
        idDoctorField.setColumns(10);
        idDoctorField.setBounds(219, 190, 152, 28);
        add(idDoctorField);

        JLabel doctorNameLabel = new JLabel("Nombre");
        doctorNameLabel.setBounds(219, 230, 152, 22);
        add(doctorNameLabel);

        nameDoctorField = new JTextField();
        nameDoctorField.setColumns(10);
        nameDoctorField.setBounds(219, 252, 152, 28);
        add(nameDoctorField);

        JLabel specialtyDoctorLabel = new JLabel("Especialidad");
        specialtyDoctorLabel.setBounds(219, 290, 152, 22);
        add(specialtyDoctorLabel);

        specialtyDoctorField = new JTextField();
        specialtyDoctorField.setColumns(10);
        specialtyDoctorField.setBounds(219, 312, 152, 28);
        add(specialtyDoctorField);

        // Tabla
        JScrollPane scrollPaneDoctors = new JScrollPane();
        scrollPaneDoctors.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneDoctors.setBounds(396, 48, 782, 586);
        add(scrollPaneDoctors);

        doctorTable = new JTable();
        doctorTable.setShowVerticalLines(true);
        doctorTable.setShowHorizontalLines(true);
        doctorTable.setModel(new DefaultTableModel(
            new Object[][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
            },
            new String[] {
                "Id", "Nombre", "Especialidad"
            }
        ));
        scrollPaneDoctors.setViewportView(doctorTable);
    }

    // Getters para componentes
    public JButton getAddDoctorButton() {
        return addDoctorButton;
    }

    public JButton getModifyDoctorButton() {
        return modifyDoctorButton;
    }

    public JButton getRemoveDoctorButton() {
        return removeDoctorButton;
    }

    public JButton getShowDoctorsButton() {
        return showDoctorsButton;
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

    public JTextField getIdDoctorField() {
        return idDoctorField;
    }

    public JTextField getNameDoctorField() {
        return nameDoctorField;
    }

    public JTextField getSpecialtyDoctorField() {
        return specialtyDoctorField;
    }

    public JTable getDoctorTable() {
        return doctorTable;
    }
}
