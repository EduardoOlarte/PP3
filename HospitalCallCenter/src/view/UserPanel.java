package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class UserPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField, textField_2;
    private JPasswordField passwordField_1;
    private JTable UserTable;

    public UserPanel(View view) {
        setLayout(null);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setBackground(new Color(102, 204, 255));
        setBounds(0, 0, 1184, 661);

        // Botones
        JButton addUserButton = new JButton("Agregar Usuario");
        addUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        addUserButton.setBounds(17, 69, 185, 50);
        add(addUserButton);

        JButton modifyUserButton = new JButton("Modificar Usuario");
        modifyUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        modifyUserButton.setBounds(17, 151, 185, 50);
        add(modifyUserButton);

        JButton removeUserButton = new JButton("Eliminar Usuario");
        removeUserButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        removeUserButton.setBounds(17, 232, 185, 57);
        add(removeUserButton);

        JButton showUsersButton = new JButton("Ver Usuarios");
        showUsersButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        showUsersButton.setBounds(17, 314, 185, 50);
        add(showUsersButton);

        JButton UserExitButton = new JButton("Salir");
        UserExitButton.setBounds(116, 548, 87, 29);
        add(UserExitButton);
        UserExitButton.addActionListener(e -> System.exit(0));

        JButton UserMenuButton = new JButton("Menu");
        UserMenuButton.setBounds(17, 548, 87, 29);
        add(UserMenuButton);
        UserMenuButton.addActionListener(e -> {
            // Cambiar al LoginPanel
            view.changePanel(new MenuPanel(view),1);
        });

        JButton UserBackLoginButton = new JButton("Cerrar Sesion");
        UserBackLoginButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        UserBackLoginButton.setBounds(41, 480, 138, 44);
        add(UserBackLoginButton);
        UserBackLoginButton.addActionListener(e -> {
            // Cambiar al LoginPanel
            view.changePanel(new LoginPanel(view),1);
        });

        // Labels y TextFields
        JLabel nameUserLabel = new JLabel("Usuario");
        nameUserLabel.setBounds(219, 168, 152, 22);
        add(nameUserLabel);

        textField = new JTextField();
        textField.setBounds(219, 190, 152, 28);
        add(textField);
        textField.setColumns(10);

        JLabel passwordUserLabel = new JLabel("Contraseña");
        passwordUserLabel.setBounds(219, 230, 152, 22);
        add(passwordUserLabel);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(219, 312, 152, 28);
        add(textField_2);

        JLabel rolUserLabel = new JLabel("Rol");
        rolUserLabel.setBounds(219, 290, 152, 22);
        add(rolUserLabel);

        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(219, 250, 152, 28);
        add(passwordField_1);

        // Tabla de Usuarios
        JScrollPane scrollPaneUser = new JScrollPane();
        scrollPaneUser.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneUser.setBounds(396, 48, 782, 586);
        add(scrollPaneUser);

        UserTable = new JTable();
        UserTable.setShowVerticalLines(true);
        UserTable.setShowHorizontalLines(true);
        UserTable.setModel(new DefaultTableModel(
            new Object[][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String[] {
                "Usuario", "Contraseña", "Rol"
            }
        ));
        scrollPaneUser.setViewportView(UserTable);
    }

    // Getters para los botones y otros componentes
    public JButton getAddUserButton() {
        return (JButton) getComponentAt(17, 69);
    }

    public JButton getModifyUserButton() {
        return (JButton) getComponentAt(17, 151);
    }

    public JButton getRemoveUserButton() {
        return (JButton) getComponentAt(17, 232);
    }

    public JButton getShowUsersButton() {
        return (JButton) getComponentAt(17, 314);
    }

    public JButton getUserExitButton() {
        return (JButton) getComponentAt(116, 548);
    }

    public JButton getUserMenuButton() {
        return (JButton) getComponentAt(17, 548);
    }

    public JButton getUserBackLoginButton() {
        return (JButton) getComponentAt(41, 480);
    }

    public JTextField getUserField() {
        return textField;
    }

    public JPasswordField getPasswordField() {
        return passwordField_1;
    }

    public JTextField getRoleField() {
        return textField_2;
    }

    public JTable getUserTable() {
        return UserTable;
    }
}
