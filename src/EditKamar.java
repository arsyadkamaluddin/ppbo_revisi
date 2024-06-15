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

public class EditKamar extends JFrame {
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
    private JRadioButton acButton;
    private JRadioButton nonAcButton;
    private JRadioButton singleButton;
    private JRadioButton doubleButton;

    public EditKamar() {
        try {
            con = new DbConnect();
            statement = con.getConnection().createStatement();
            dataFromDB = statement.executeQuery("SELECT * FROM dataKamar");
            while (dataFromDB.next()) {
                RoomClass baru = new RoomClass(dataFromDB.getString(1),dataFromDB.getInt(2),dataFromDB.getInt(3),dataFromDB.getInt(4));
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
        JLabel labelNama = new JLabel("Edit Kamar");
        JLabel labelKmrSearch = new JLabel("Nomor Kamar");
        JTextField inputID = new JTextField("Masukkan nomor kamar...");
        JButton btnSearchNoKmr = new JButton(">>");
        JPanel contButton = new JPanel(new GridLayout(9, 1, 0, 20));
        JButton btnUpdate = new JButton("Update");
        JButton btnLogout = new JButton("Admin");
        JButton btnExit = new JButton("Keluar");

        JPanel contKamar = new JPanel(null);
        contJam.setBounds(0, 0, 350, 160);
        contJam.setBackground(new Color(0xD9D9D9));

        contDetails.setBounds(WindowSize.width - 350, 0, 350, 160);
        contDetails.setBackground(new Color(0xD9D9D9));

        contButton.setBounds(WindowSize.width - 350, 200, 350, 650);
        contButton.setBackground(Color.WHITE);
        contButton.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 67));

        contKamar.setBounds(67, 200, 1120, 650);

        labelNama.setBounds(350, 0, WindowSize.width - 700, 160);
        labelNama.setFont(new Font("Inter", Font.BOLD, 72));
        labelNama.setHorizontalAlignment(JLabel.CENTER);
        labelKmrSearch.setFont(new Font("Inter", Font.BOLD, 30));
        labelKmrSearch.setBounds(10, 25, 240, 50);
        inputID.setFont(new Font("Inter", Font.ITALIC, 20));
        inputID.setBounds(7, 80, 240, 50);
        btnSearchNoKmr.setFont(new Font("Inter", Font.BOLD, 32));
        btnSearchNoKmr.setBounds(250, 80, 80, 50);
        btnUpdate.setFont(new Font("Inter", Font.BOLD, 32));

        btnLogout.setFont(new Font("Inter", Font.BOLD, 32));
        btnExit.setFont(new Font("Inter", Font.BOLD, 32));

        acButton = new JRadioButton("AC");
        nonAcButton = new JRadioButton("Non-AC");
        ButtonGroup acGroup = new ButtonGroup();
        acButton.setBounds(10, 130, 100, 30);
        nonAcButton.setBounds(120, 130, 100, 30);
        acGroup.add(acButton);
        acGroup.add(nonAcButton);

        singleButton = new JRadioButton("Single");
        doubleButton = new JRadioButton("Double");
        ButtonGroup bedGroup = new ButtonGroup();
        singleButton.setBounds(10, 170, 100, 30);
        doubleButton.setBounds(120, 170, 100, 30);
        bedGroup.add(singleButton);
        bedGroup.add(doubleButton);

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

        btnSearchNoKmr.addActionListener(new ActionListener() {
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

        // Adding action listeners
        acButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // handle AC selected
            }
        });

        nonAcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // handle Non-AC selected
            }
        });

        singleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // handle Single bed selected
            }
        });

        doubleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // handle Double bed selected
            }
        });

        inputID.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputID.getText().equals("Masukkan nomor kamar...")) {
                    inputID.setText("");
                } else if (inputID.getText().equals("")) {
                    inputID.setText("Masukkan nomor kamar...");
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
        tableModel.addColumn("Nomor kamar");
        tableModel.addColumn("AC/Non-AC");
        tableModel.addColumn("Tipe Bed");
        tableModel.addColumn("Status");

        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(0, 0, 1120, 650);
        contKamar.add(scrollPane);

        tableModel.addRow(new Object[]{"nfdkjfjd", "fdfd"});

        contJam.add(dateLabel);
        contJam.add(timeLabel);
        contDetails.add(labelKmrSearch);
        contDetails.add(inputID);
        contDetails.add(btnSearchNoKmr);
        contButton.add(acButton);
        contButton.add(nonAcButton);
        contButton.add(singleButton);
        contButton.add(doubleButton);
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
            EditKamar halo = new EditKamar();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}