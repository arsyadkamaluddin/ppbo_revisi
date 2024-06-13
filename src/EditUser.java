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

public class EditUser extends JFrame {
    ResultSet dataFromDB = null;
    Statement statement = null;
    DbConnect con = null;
    private ArrayList<RoomClass> daftarKamar = new ArrayList<RoomClass>();
    private JLabel timeLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private JScrollPane contKamar = new JScrollPane();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public EditUser() {
        try {
            con = new DbConnect();
            statement = con.getConnection().createStatement();
            dataFromDB = statement.executeQuery("SELECT * FROM dataUser");
            while (dataFromDB.next()) {
                RoomClass baru = new RoomClass(dataFromDB.getString(2), dataFromDB.getInt(3), dataFromDB.getString(4), dataFromDB.getString(5), dataFromDB.getInt(6));
                daftarKamar.add(baru);
            }
            System.out.println(daftarKamar.size());
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        init();
    }

    private void init() {
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JLabel labelNama = new JLabel("Edit User");
        JLabel labelIDSearch = new JLabel("ID Admin/Customer");
        JTextField inputID = new JTextField("Masukkan ID...");
        JButton btnSearchID = new JButton(">>");
        JPanel contButton = new JPanel(new GridLayout(9, 1, 0, 20));
        JTextField inputEmail = new JTextField("Email/No. Telepone");
        JTextField inputUsername = new JTextField("Username");
        JTextField inputPassword = new JTextField("Password");
        JButton btnUpdate = new JButton("Update");
        JButton btnLogout = new JButton("Logout");
        JButton btnExit = new JButton("Keluar");
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
        labelNama.setFont(new Font("Inter", Font.BOLD, 48));
        labelNama.setHorizontalAlignment(JLabel.CENTER);
        labelIDSearch.setFont(new Font("Inter", Font.BOLD, 20));
        labelIDSearch.setBounds(10, 25, 240, 50);
        inputID.setFont(new Font("Inter", Font.ITALIC, 20));
        inputID.setBounds(7, 80, 240, 50);
        btnSearchID.setFont(new Font("Inter", Font.BOLD, 32));
        btnSearchID.setBounds(250, 80, 80, 50);
        inputEmail.setFont(new Font("Inter", Font.ITALIC, 20));
        inputUsername.setFont(new Font("Inter", Font.ITALIC, 20));
        inputPassword.setFont(new Font("Inter", Font.ITALIC, 20));
        btnUpdate.setFont(new Font("Inter", Font.BOLD, 32));

        btnLogout.setFont(new Font("Inter", Font.BOLD, 32));
        btnExit.setFont(new Font("Inter", Font.BOLD, 32));
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login halo = new Login();
                    halo.setVisible(true);
                    halo.setLocationRelativeTo(null);
                    dispose();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        btnSearchID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AdminLogin halo = new AdminLogin();
                    halo.setVisible(true);
                    halo.setLocationRelativeTo(null);
                    dispose();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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

        inputEmail.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputEmail.getText().equals("Email/No. Telepone")) {
                    inputEmail.setText("");
                } else if (inputEmail.getText().equals("")) {
                    inputEmail.setText("Email/No. Telepone");
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

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Account");
        tableModel.addColumn("Email/Telepone");
        tableModel.addColumn("Username");
        tableModel.addColumn("Password");

        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(0, 0, 1120, 650);
        contKamar.add(scrollPane);

        tableModel.addRow(new Object[]{"nfdkjfjd","fdfd"});

        contJam.add(dateLabel);
        contJam.add(timeLabel);
        contDetails.add(labelIDSearch);
        contDetails.add(inputID);
        contDetails.add(btnSearchID);
        contButton.add(new JLabel());
        contButton.add(inputEmail);
        contButton.add(inputUsername);
        contButton.add(inputPassword);
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(btnUpdate);
        contButton.add(btnLogout);
        contButton.add(btnExit);

        add(contJam);
        add(contDetails);
        add(labelNama);
        add(contButton);
        add(contKamar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WindowSize.width, WindowSize.heigth);
        setUndecorated(true);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void updateTime() {
        String currentTime = timeFormat.format(new Date());
        String currentDate = dateFormat.format(new Date());
        timeLabel.setText(currentTime);
        dateLabel.setText(currentDate);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                System.out.println(info.getName());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            EditUser halo = new EditUser();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
