import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditUser extends EditBase {

    private JTextField inputNama;
    private JTextField inputUsername;
    private JTextField inputPassword;

    public EditUser() {
        super(); // Calls the constructor of EditBase
    }

    @Override
    public void init() {
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JLabel labelNama = new JLabel("Edit User");
        JLabel labelIDSearch = new JLabel("ID Admin/Customer");
        inputID = new JTextField("Masukkan ID...");
        JButton btnSearchID = new JButton(">>");
        JPanel contButton = new JPanel(new GridLayout(10, 1, 0, 20));
        inputNama = new JTextField("Nama");
        inputUsername = new JTextField("Username");
        inputPassword = new JTextField("Password");
        JButton btnUpdate = new JButton("Update");
        JButton btnExit = new JButton("Kembali");

        JPanel contKamar = new JPanel(null);

        contJam.setBounds(0, 0, 350, 160);
        contJam.setBackground(new Color(0xD9D9D9));

        contDetails.setBounds(WindowSize.width - 350, 0, 350, 160);
        contDetails.setBackground(new Color(0xD9D9D9));

        contButton.setBounds(WindowSize.width - 350, 200, 350, 650);
        contButton.setOpaque(true);
        contButton.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 67));
        contButton.setBackground(Color.WHITE);

        contKamar.setBounds(67, 200, 1120, 650);

        labelNama.setBounds(350, 0, WindowSize.width - 700, 160);
        labelNama.setFont(new Font("Inter", Font.BOLD, 72));
        labelNama.setHorizontalAlignment(JLabel.CENTER);
        labelIDSearch.setFont(new Font("Inter", Font.BOLD, 30));
        labelIDSearch.setBounds(10, 25, 320, 50);
        inputID.setFont(new Font("Inter", Font.ITALIC, 20));
        inputID.setBounds(7, 80, 240, 50);
        btnSearchID.setFont(new Font("Inter", Font.BOLD, 32));
        btnSearchID.setBounds(250, 80, 80, 50);
        inputNama.setFont(new Font("Inter", Font.ITALIC, 20));
        inputUsername.setFont(new Font("Inter", Font.ITALIC, 20));
        inputPassword.setFont(new Font("Inter", Font.ITALIC, 20));
        btnUpdate.setFont(new Font("Inter", Font.BOLD, 32));
        updateStatusLabel.setFont(new Font("Inter", Font.BOLD, 20));
        updateStatusLabel.setForeground(Color.GREEN);

        btnExit.setFont(new Font("Inter", Font.BOLD, 32));

        addActionListener(btnSearchID, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = inputID.getText();
                fetchUserData(userID);
            }
        });

        addActionListener(btnUpdate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = inputID.getText();
                String name = inputNama.getText();
                String username = inputUsername.getText();
                String password = inputPassword.getText();

                updateUser(userID, name, username, password);
            }
        });

        addActionListener(btnExit, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin admin = new Admin();
                admin.setVisible(true);
                dispose();
            }
        });

        addKeyListener(inputID, new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputID.getText().equals("Masukkan ID...")) {
                    inputID.setText("");
                } else if (inputID.getText().equals("")) {
                    inputID.setText("Masukkan ID...");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        addKeyListener(inputNama, new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputNama.getText().equals("Name")) {
                    inputNama.setText("");
                } else if (inputNama.getText().equals("")) {
                    inputNama.setText("Name");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        addKeyListener(inputUsername, new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputUsername.getText().equals("Username")) {
                    inputUsername.setText("");
                } else if (inputUsername.getText().equals("")) {
                    inputUsername.setText("Username");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        addKeyListener(inputPassword, new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputPassword.getText().equals("Password")) {
                    inputPassword.setText("");
                } else if (inputPassword.getText().equals("")) {
                    inputPassword.setText("Password");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        timeLabel.setFont(new Font("Inter", Font.BOLD, 20));
        timeLabel.setBounds(67, 30, 230, 50);

        dateLabel.setFont(new Font("Inter", Font.BOLD, 20));
        dateLabel.setBounds(67, 80, 230, 50);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        updateTime();

        tableModel = new DefaultTableModel(new Object[][]{},new String[]{"User ID", "Name","Username","Password","Role"});
        dataTable = new JTable(tableModel);
        dataTable.disable();
        dataTable.setRowHeight(30);
        dataTable.setFont(new Font("Inter",Font.PLAIN,15));
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(0, 0, 1120, 650);
        contKamar.add(scrollPane);

        contJam.add(dateLabel);
        contJam.add(timeLabel);
        contDetails.add(labelIDSearch);
        contDetails.add(inputID);
        contDetails.add(btnSearchID);
        contButton.add(new JLabel());
        contButton.add(inputNama);
        contButton.add(inputUsername);
        contButton.add(inputPassword);
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(btnUpdate);
        contButton.add(btnExit);

        add(contJam);
        add(contDetails);
        add(labelNama);
        add(contButton);
        add(contKamar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WindowSize.width, WindowSize.heigth);
        setUndecorated(true);

        updateTableData("SELECT * FROM datauser", new String[]{"userId", "name", "username", "password", "role"});

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    protected void resetForm() {
        super.resetForm(); // Call the superclass resetForm method to reset inputID field
        inputNama.setText("Nama");
        inputUsername.setText("Username");
        inputPassword.setText("Password");
    }

    private void fetchUserData(String userID) {
        try {
            String query = "SELECT * FROM datauser WHERE userId = ?";
            PreparedStatement preparedStatement = con.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                UserClass user = new UserClass(userID, name, username, password, role);

                inputNama.setText(user.getName());
                inputUsername.setText(user.getUsername());
                inputPassword.setText(user.getPassword());
            } else {
                showMessageDialog("User tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                resetForm();
            }
        } catch (SQLException e) {
            showMessageDialog("Error fetching user data.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            resetForm();
        }
        
    }

    private void updateUser(String userID, String name, String username, String password) {
        try {
            String query = "UPDATE dataUser SET name = ?, username = ?, password = ? WHERE userId = ?";
            PreparedStatement preparedStatement = con.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, userID);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                showMessageDialog("Data user berhasil diperbarui untuk user id " + userID, "Success", JOptionPane.INFORMATION_MESSAGE);
                updateStatusLabel.setText("Update Berhasil...");
                updateStatusLabel.setForeground(Color.GREEN);
            } else {
                showMessageDialog("Tidak Ada Perubahan Data.", "Info", JOptionPane.WARNING_MESSAGE);
                updateStatusLabel.setText("Tidak Ada Perubahan Data.");
                updateStatusLabel.setForeground(Color.ORANGE);
            }
            updateTableData("SELECT * FROM datauser", new String[]{"userId", "name", "username", "password", "role"});
        } catch (SQLException e) {
            showMessageDialog("Error updating user data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            updateStatusLabel.setText("Update Gagal.");
            updateStatusLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
        resetForm();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        SwingUtilities.invokeLater(() -> {
            new EditUser();
        });
    }
}
