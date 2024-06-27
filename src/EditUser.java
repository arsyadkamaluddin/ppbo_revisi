import org.jdesktop.swingx.JXDatePicker;
import config.DbConnect;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class EditUser extends JFrame implements WindowBehavior{
    ResultSet dataFromDB = null;
    Statement statement = null;
    DbConnect con = new DbConnect();
    private ArrayList<UserClass> daftarUser = new ArrayList<UserClass>();
    private JLabel timeLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private JScrollPane contKamar = new JScrollPane();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextField inputNama;
    private JTextField inputUsername;
    private JTextField inputPassword;
    private JLabel updateStatusLabel = new JLabel();
    private JTextField inputID;

    public EditUser() {
        try {
            statement = con.getConnection().createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        init();
    }    

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

        JTable tableUser;

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

        btnSearchID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = inputID.getText();
                fetchUserData(userID);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = inputID.getText();
                String name = inputNama.getText();
                String username = inputUsername.getText();
                String password = inputPassword.getText();

                updateUser(userID, name, username, password);
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin admin = new Admin();
                admin.setVisible(true);
                dispose();
            }
        });

        inputID.addKeyListener(new KeyListener() {
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

        inputNama.addKeyListener(new KeyListener() {
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

        inputUsername.addKeyListener(new KeyListener() {
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

        inputPassword.addKeyListener(new KeyListener() {
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

        updateTableData();

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public void updateTime() {
        String currentTime = timeFormat.format(new Date());
        String currentDate = dateFormat.format(new Date());
        timeLabel.setText(currentTime);
        dateLabel.setText(currentDate);
    }
    private void resetForm(){
        inputID.setText("Masukkan ID...");
        inputNama.setText("Nama");
        inputUsername.setText("Username");
        inputPassword.setText("Password");
    }

    private void fetchUserData(String userID) {
        System.out.println("Fetching data for userID: " + userID);
        try {
            String query = "SELECT * FROM datauser WHERE userId = ?";
            PreparedStatement preparedStatement = con.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                inputNama.setText(resultSet.getString("name"));
                inputUsername.setText(resultSet.getString("username"));
                inputPassword.setText(resultSet.getString("password"));
            } else {
                JOptionPane.showMessageDialog(this, "User tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching user data.", "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Data user berhasil diperbarui untuk user id " + userID);
                updateStatusLabel.setText("Update Berhasil...");
                updateStatusLabel.setForeground(Color.GREEN);
            } else {
                JOptionPane.showMessageDialog(this, "Data user berhasil diperbarui untuk user id " + userID);
                updateStatusLabel.setText("Tidak Ada Perubahan Data.");
                updateStatusLabel.setForeground(Color.ORANGE);
            }
            updateTableData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating user data: " + e.getMessage());
            updateStatusLabel.setText("Update Gagal.");
            updateStatusLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
        resetForm();
    }

    private void updateTableData() {
        tableModel.setRowCount(0);

        String query = "SELECT * FROM datauser";
        try {
            dataFromDB = statement.executeQuery(query);
            while (dataFromDB.next()) {
                tableModel.addRow(new Object[]{dataFromDB.getString("userId"), 
                    dataFromDB.getString("name"), 
                    dataFromDB.getString("username"), 
                    dataFromDB.getString("password"), 
                    dataFromDB.getString("role")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating table data: " + e.getMessage());
            e.printStackTrace();
        }
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
