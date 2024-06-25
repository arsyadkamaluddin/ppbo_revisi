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

public class Booking extends BookingBase {
    ResultSet dataFromDB = null;
    Statement statement = null;
    DbConnect con = null;
    private ArrayList<RoomClass> daftarKamar = new ArrayList<RoomClass>();
    private JScrollPane contKamar = new JScrollPane();
    private String nama;
    private int nomorKamar;
    private int harga;
    private Date masuk;
    private Date keluar;
    private long jumlahmalam;
    private long totalharga;
    private int ranjang;
    private int ac;

    public Booking(int nomorKamar,Date masuk,Date keluar) {
       try {
           con = new DbConnect();
           statement = con.getConnection().createStatement();
           dataFromDB = statement.executeQuery("SELECT * FROM dataKamar WHERE nomorKamar='"+nomorKamar+"'");
           while (dataFromDB.next()) {
//                RoomClass baru = new RoomClass(dataFromDB.getString(2), dataFromDB.getInt(3), dataFromDB.getString(4), dataFromDB.getString(5), dataFromDB.getInt(6));
//                daftarKamar.add(baru);
                    ranjang = dataFromDB.getInt("ranjang");
                    ac = dataFromDB.getInt("ac");
                    harga = dataFromDB.getInt(6);
           }
           System.out.println(daftarKamar.size());
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
        this.nomorKamar=nomorKamar;
        this.masuk = masuk;
        this.keluar = keluar;
        this.jumlahmalam = (keluar.getTime() - masuk.getTime())/ 1000 / 60 / 60 / 24;
        this.totalharga = jumlahmalam*harga;
        init();

    }
    
    protected void init() {
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JLabel labelNama = new JLabel("Booking");
        JPanel contButton = new JPanel(new GridLayout(3, 1, 0, 20));
        JButton btnExit = new JButton("Keluar");
        JPanel contKamar = new JPanel(null);
        JPanel contInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        JXDatePicker inputTanggal = new JXDatePicker(new Locale("id", "ID"));
        JLabel labelNoKamar = new JLabel("     "+String.valueOf(nomorKamar));
        JLabel labelHarga = new JLabel("   "+String.valueOf(harga));
        
        

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


        labelNoKamar.setFont(new Font("Inter", Font.BOLD, 20));
        labelNoKamar.setBackground(new Color(146, 146, 146));
        labelNoKamar.setPreferredSize(new Dimension(100, 50));
        labelHarga.setPreferredSize(new Dimension(100, 50));

        labelHarga.setFont(new Font("Inter", Font.BOLD, 20));
        labelHarga.setBackground(new Color(146, 146, 146));
        labelHarga.setPreferredSize(new Dimension(100, 50));

        labelNoKamar.setOpaque(true);
        labelHarga.setOpaque(true);

        

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

        JPanel panelNamaPemesan = new JPanel(null);
        panelNamaPemesan.setLayout(new BoxLayout(panelNamaPemesan,BoxLayout.X_AXIS));
        panelNamaPemesan.setBounds(50, 85, 1020, 38);
        panelNamaPemesan.setBackground(new Color(146, 146, 146));
        panelNamaPemesan.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JLabel labelNamaPemesan = new JLabel("Nama Pemesan");
        labelNamaPemesan.setFont(new Font("Inter", Font.BOLD, 20));
        labelNamaPemesan.setPreferredSize(new Dimension(200,30));
        panelNamaPemesan.add(labelNamaPemesan);

        JTextField inputNamaPemesan = new JTextField();
        inputNamaPemesan.setPreferredSize(new Dimension(700, 34));
        inputNamaPemesan.setText(this.nama);
        panelNamaPemesan.add(inputNamaPemesan);

        JPanel panelNIK = new JPanel(null);
        panelNIK.setLayout(new BoxLayout(panelNIK,BoxLayout.X_AXIS));
        panelNIK.setBounds(50, 130, 1020, 38);
        panelNIK.setBackground(new Color(146, 146, 146));
        panelNIK.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));

        JLabel labelNIK = new JLabel("NIK");
        labelNIK.setFont(new Font("Inter", Font.BOLD, 20));
        labelNIK.setPreferredSize(new Dimension(200,30));
        panelNIK.add(labelNIK);

        JTextField inputNIK = new JTextField();
        inputNIK.setPreferredSize(new Dimension(700, 34));
        panelNIK.add(inputNIK);

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
        inputCheckin.disable();
        inputCheckin.setText(new SimpleDateFormat("yyyy-MM-dd").format(masuk));

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
        inputCheckout.disable();
        inputCheckout.setText(new SimpleDateFormat("yyyy-MM-dd").format(keluar));

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
        inputJumlahmalam.disable();
        inputJumlahmalam.setText(Long.toString(jumlahmalam));


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
        // acGroup.add(acCheckBox);
        checkBoxPanel.add(acCheckBox);

        JCheckBox nonAcCheckBox = new JCheckBox("Non AC");
        nonAcCheckBox.setBackground(new Color(146, 146, 146));
        // acGroup.add(nonAcCheckBox);
        checkBoxPanel.add(nonAcCheckBox);

        acCheckBox.setSelected(ac>0?true:false);
        nonAcCheckBox.setSelected(ac==0?true:false);

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

        singleCheckBox.setSelected(ranjang==1?true:false);
        doubleCheckBox.setSelected(ranjang>1?true:false);

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

        JTextField inputTotalHarga = new JTextField();
        inputTotalHarga.setPreferredSize(new Dimension(700, 34));
        panelTotalHarga.add(inputTotalHarga);
        inputTotalHarga.disable();
        inputTotalHarga.setText(Long.toString(totalharga));

        JPanel panelNext = new JPanel();
        panelNext.setLayout(new BoxLayout(panelNext, BoxLayout.Y_AXIS));
        panelNext.setBounds(50, 568, 311, 50);

        JButton btnNext = new JButton("Next >>");
        btnNext.setFont(new Font("Inter", Font.BOLD, 20));
        btnNext.setBackground(Color.WHITE);
        btnNext.setPreferredSize(new Dimension(300, 100));

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home();
                home.setVisible(true);
                dispose();
            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DetailBooking detail = new DetailBooking();
                    detail.setVisible(true);
                    detail.setLocationRelativeTo(null);
                    dispose();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });


        panelNext.add(Box.createVerticalGlue());
        panelNext.add(btnNext);
        panelNext.add(Box.createVerticalGlue());

        contKamar.add(panelNext);

        contKamar.setVisible(true);

        contKamar.add(panelNamaPemesan);
        contKamar.add(panelNIK);
        contKamar.add(panelTelepon);
        contKamar.add(panelCheckin);
        contKamar.add(panelCheckout);
        contKamar.add(panelJumlahmalam);
        contKamar.add(panelTipeKamar);
        contKamar.add(panelTotalHarga);
        contKamar.add(panelNext);

        super.add(contJam);
        super.add(contDetails);
        super.add(labelNama);
        super.add(contButton);
        super.add(contKamar);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(WindowSize.width, WindowSize.heigth);
        super.setUndecorated(true);
        super.setLayout(null);
        super.getContentPane().setBackground(Color.WHITE);
        super.setVisible(true);
    }


    protected void updateTime() {
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
            Booking halo = new Booking(100, new Date(), new Date());
        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }

}
