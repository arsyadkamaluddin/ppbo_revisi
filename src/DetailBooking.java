import org.jdesktop.swingx.JXDatePicker;
import config.DbConnect;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.border.EmptyBorder;

public class DetailBooking extends JFrame {
    ResultSet dataFromDB = null;
    Statement statement = null;
    DbConnect con = null;
    private ArrayList<RoomClass> daftarKamar = new ArrayList<RoomClass>();
    private JLabel timeLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private JScrollPane contKamar = new JScrollPane();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));

    public DetailBooking() {
        try {
            con = new DbConnect();
            statement = con.getConnection().createStatement();
            dataFromDB = statement.executeQuery("SELECT * FROM dataKamar");
<<<<<<< HEAD
            while (dataFromDB.next()) {
//                RoomClass baru = new RoomClass(dataFromDB.getString(2), dataFromDB.getInt(3), dataFromDB.getString(4), dataFromDB.getString(5), dataFromDB.getInt(6));
//               daftarKamar.add(baru);
            }
=======
            // while (dataFromDB.next()) {
            //     RoomClass baru = new RoomClass(dataFromDB.getString(2), dataFromDB.getInt(3), dataFromDB.getString(4), dataFromDB.getString(5), dataFromDB.getInt(6));
            //     daftarKamar.add(baru);
            // }
>>>>>>> aa122b8c7ff172cb9e567711c8357f9b8c3e0cb4
            System.out.println(daftarKamar.size());
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        init();
    }

    private void init() {
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JLabel labelNama = new JLabel("Detail Booking");
        JPanel contButton = new JPanel(new GridLayout(3, 1, 0, 20));
        JButton btnExit = new JButton("Keluar");
        JPanel contKamar = new JPanel(null);
        JPanel contInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        JXDatePicker inputTanggal = new JXDatePicker(new Locale("id", "ID"));
        JLabel labelNoKamar = new JLabel("    Kamar");
        JLabel labelHarga = new JLabel("    Harga");

        contJam.setBounds(0, 0, 350, 160);
        contJam.setBackground(new Color(214, 217, 223));

        contDetails.setBounds(WindowSize.width - 350, 0, 350, 160);
        contDetails.setBackground(new Color(214, 217, 223));

        contButton.setBounds(WindowSize.width - 350, 525, 390, 325);
        contButton.setBackground(new Color(0, 0, 0, 0));
        contButton.setOpaque(true);
        contButton.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 67));

        contKamar.setBounds(67, 200, 1120, 650);

        contInput.setBounds(0, 0, 1120, 75);
        contInput.setBackground(new Color(214, 217, 223));

        labelNama.setBounds(350, 0, WindowSize.width - 700, 160);
        labelNama.setFont(new Font("Inter", Font.BOLD, 48));
        labelNama.setHorizontalAlignment(JLabel.CENTER);

        inputTanggal.setPreferredSize(new Dimension(300, 75));
        inputTanggal.getEditor().setEditable(false);
        inputTanggal.getEditor().setText(null);
        inputTanggal.setDate(new Date());
        inputTanggal.setBorder(BorderFactory.createEmptyBorder(10, 29, 10, 10));
        inputTanggal.setBackground(Color.RED);

        btnExit.setFont(new Font("Inter", Font.BOLD, 32));

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        labelNoKamar.setFont(new Font("Inter", Font.BOLD, 20));
        labelNoKamar.setBackground(new Color(146, 146, 146));
        labelNoKamar.setPreferredSize(new Dimension(100, 50));
        labelHarga.setPreferredSize(new Dimension(100, 50));

        labelHarga.setFont(new Font("Inter", Font.BOLD, 20));
        labelHarga.setBackground(new Color(146, 146, 146));
        labelHarga.setPreferredSize(new Dimension(100, 50));

        labelNoKamar.setOpaque(true);
        labelHarga.setOpaque(true);

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

        contJam.add(dateLabel);
        contJam.add(timeLabel);
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(btnExit);
        contKamar.add(contInput);

        contInput.add(inputTanggal);
        contInput.add(Box.createRigidArea(new Dimension(20, 0))); // Spacing between components
        contInput.add(labelNoKamar);
        contInput.add(Box.createRigidArea(new Dimension(20, 0))); // Spacing between components
        contInput.add(labelHarga);
        contKamar.add(contInput);

        JPanel panelIdCustomer = new JPanel(null);
        panelIdCustomer.setLayout(new BoxLayout(panelIdCustomer,BoxLayout.X_AXIS));
        panelIdCustomer.setBounds(50, 85, 1020, 38);
        panelIdCustomer.setBackground(new Color(146, 146, 146));
        panelIdCustomer.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JLabel labelIdCustomer = new JLabel("ID Customer");
        labelIdCustomer.setFont(new Font("Inter", Font.BOLD, 20));
        labelIdCustomer.setPreferredSize(new Dimension(200,30));
        panelIdCustomer.add(labelIdCustomer);

        JTextField inputIdCustomer = new JTextField();
        inputIdCustomer.setPreferredSize(new Dimension(700, 34));
        panelIdCustomer.add(inputIdCustomer);

        JPanel panelNamaPemesan = new JPanel(null);
        panelNamaPemesan.setLayout(new BoxLayout(panelNamaPemesan,BoxLayout.X_AXIS));
        panelNamaPemesan.setBounds(50, 130, 1020, 38);
        panelNamaPemesan.setBackground(new Color(146, 146, 146));
        panelNamaPemesan.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JLabel labelNamaPemesan = new JLabel("Nama Pemesan");
        labelNamaPemesan.setFont(new Font("Inter", Font.BOLD, 20));
        labelNamaPemesan.setPreferredSize(new Dimension(200,30));
        panelNamaPemesan.add(labelNamaPemesan);

        JTextField inputNamaPemesan = new JTextField();
        inputNamaPemesan.setPreferredSize(new Dimension(700, 34));
        panelNamaPemesan.add(inputNamaPemesan);

        JPanel panelTelepon = new JPanel(null);
        panelTelepon.setLayout(new BoxLayout(panelTelepon,BoxLayout.X_AXIS));
        panelTelepon.setBounds(50, 175, 1020, 38);
        panelTelepon.setBackground(new Color(146, 146, 146));
        panelTelepon.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JLabel labelTelepon = new JLabel("Telepon");
        labelTelepon.setFont(new Font("Inter", Font.BOLD, 20));
        labelTelepon.setPreferredSize(new Dimension(200,30));
        panelTelepon.add(labelTelepon);

        JTextField inputTelepon = new JTextField();
        inputTelepon.setPreferredSize(new Dimension(700, 34));
        panelTelepon.add(inputTelepon);

        JPanel panelCheckin = new JPanel(null);
        panelCheckin.setLayout(new BoxLayout(panelCheckin,BoxLayout.X_AXIS));
        panelCheckin.setBounds(50, 220, 1020, 38);
        panelCheckin.setBackground(new Color(146, 146, 146));
        panelCheckin.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JLabel labelCheckin = new JLabel("Checkin");
        labelCheckin.setFont(new Font("Inter", Font.BOLD, 20));
        labelCheckin.setPreferredSize(new Dimension(200,30));
        panelCheckin.add(labelCheckin);

        JTextField inputCheckin = new JTextField();
        inputCheckin.setPreferredSize(new Dimension(700, 34));
        panelCheckin.add(inputCheckin);

        JPanel panelCheckout = new JPanel(null);
        panelCheckout.setLayout(new BoxLayout(panelCheckout,BoxLayout.X_AXIS));
        panelCheckout.setBounds(50, 265, 1020, 38);
        panelCheckout.setBackground(new Color(146, 146, 146));
        panelCheckout.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JLabel labelCheckout = new JLabel("Checkout");
        labelCheckout.setFont(new Font("Inter", Font.BOLD, 20));
        labelCheckout.setPreferredSize(new Dimension(200,30));
        panelCheckout.add(labelCheckout);

        JTextField inputCheckout = new JTextField();
        inputCheckout.setPreferredSize(new Dimension(700, 34));
        panelCheckout.add(inputCheckout);

        JPanel panelJumlahmalam = new JPanel(null);
        panelJumlahmalam.setLayout(new BoxLayout(panelJumlahmalam,BoxLayout.X_AXIS));
        panelJumlahmalam.setBounds(50, 310, 1020, 38);
        panelJumlahmalam.setBackground(new Color(146, 146, 146));
        panelJumlahmalam.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JLabel labelJumlahmalam = new JLabel("Jumlah Malam");
        labelJumlahmalam.setFont(new Font("Inter", Font.BOLD, 20));
        labelJumlahmalam.setPreferredSize(new Dimension(200,30));
        panelJumlahmalam.add(labelJumlahmalam);

        JTextField inputJumlahmalam = new JTextField();
        inputJumlahmalam.setPreferredSize(new Dimension(700, 34));
        panelJumlahmalam.add(inputJumlahmalam);

        JPanel panelTipeKamar = new JPanel();
        panelTipeKamar.setLayout(new BoxLayout(panelTipeKamar, BoxLayout.Y_AXIS));
        panelTipeKamar.setBounds(50, 355, 1020, 84);
        panelTipeKamar.setBackground(new Color(146, 146, 146));

        JPanel labelAndCheckBoxPanel = new JPanel();
        labelAndCheckBoxPanel.setLayout(new BoxLayout(labelAndCheckBoxPanel, BoxLayout.X_AXIS));
        labelAndCheckBoxPanel.setBackground(new Color(146, 146, 146));

        JLabel labelTipeKamar = new JLabel("Tipe Kamar");
        labelTipeKamar.setFont(new Font("Inter", Font.BOLD, 20));
        labelTipeKamar.setPreferredSize(new Dimension(200, 30));
        labelTipeKamar.setAlignmentY(Component.CENTER_ALIGNMENT);
        labelAndCheckBoxPanel.add(labelTipeKamar);
        labelTipeKamar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setBackground(new Color(146, 146, 146));

        checkBoxPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));

        ButtonGroup acGroup = new ButtonGroup();
        JCheckBox acCheckBox = new JCheckBox("AC");
        acCheckBox.setBackground(new Color(146, 146, 146));
        acGroup.add(acCheckBox);
        checkBoxPanel.add(acCheckBox);

        JCheckBox nonAcCheckBox = new JCheckBox("Non AC");
        nonAcCheckBox.setBackground(new Color(146, 146, 146));
        acGroup.add(nonAcCheckBox);
        checkBoxPanel.add(nonAcCheckBox);

        labelAndCheckBoxPanel.add(checkBoxPanel);

        labelAndCheckBoxPanel.add(Box.createHorizontalStrut(300));

        JPanel checkBoxPanel2 = new JPanel();
        checkBoxPanel2.setLayout(new BoxLayout(checkBoxPanel2, BoxLayout.Y_AXIS));
        checkBoxPanel2.setBackground(new Color(146, 146, 146));

        checkBoxPanel2.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));

        ButtonGroup bedGroup = new ButtonGroup();
        JCheckBox singleCheckBox = new JCheckBox("Single");
        singleCheckBox.setBackground(new Color(146, 146, 146));
        bedGroup.add(singleCheckBox);
        checkBoxPanel2.add(singleCheckBox);

        JCheckBox doubleCheckBox = new JCheckBox("Double");
        doubleCheckBox.setBackground(new Color(146, 146, 146));
        bedGroup.add(doubleCheckBox);
        checkBoxPanel2.add(doubleCheckBox);

        labelAndCheckBoxPanel.add(checkBoxPanel2);

        panelTipeKamar.add(labelAndCheckBoxPanel);
        labelAndCheckBoxPanel.setBorder(new EmptyBorder(10, 0, 10, 335));

        contKamar.add(panelTipeKamar);
        contKamar.setVisible(true);

        JPanel panelTotalHarga = new JPanel(null);
        panelTotalHarga.setLayout(new BoxLayout(panelTotalHarga,BoxLayout.X_AXIS));
        panelTotalHarga.setBounds(50, 448, 1020, 38);
        panelTotalHarga.setBackground(new Color(146, 146, 146));
        panelTotalHarga.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JLabel labelTotalHarga = new JLabel("Total Harga");
        labelTotalHarga.setFont(new Font("Inter", Font.BOLD, 20));
        labelTotalHarga.setPreferredSize(new Dimension(200,30));
        panelTotalHarga.add(labelTotalHarga);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
        panelButtons.setBackground(new Color(146, 146, 146));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnCetak = new JButton("Cetak");
        btnCetak.setFont(new Font("Inter", Font.BOLD, 20));
        btnCetak.setBackground(Color.WHITE);
        btnCetak.setPreferredSize(new Dimension(300, 50));
        panelButtons.add(btnCetak);

        panelButtons.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnBack = new JButton("Back <<");
        btnBack.setFont(new Font("Inter", Font.BOLD, 20));
        btnBack.setBackground(Color.WHITE);
        btnBack.setPreferredSize(new Dimension(300, 50));
        panelButtons.add(btnBack);

        contKamar.add(panelButtons);

        contKamar.setVisible(true);

        contKamar.add(panelIdCustomer);
        contKamar.add(panelNamaPemesan);
        contKamar.add(panelTelepon);
        contKamar.add(panelCheckin);
        contKamar.add(panelCheckout);
        contKamar.add(panelJumlahmalam);
        contKamar.add(panelTipeKamar);
        contKamar.add(panelTotalHarga);
        contKamar.add(panelButtons);

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
            DetailBooking halo = new DetailBooking();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
