import config.DbConnect;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.border.EmptyBorder;

public class Pembayaran extends JFrame implements WindowBehavior{
    ResultSet dataFromDB = null;
    Statement statement = null;
    DbConnect con = null;
    private ArrayList<RoomClass> daftarKamar = new ArrayList<RoomClass>();
    private JLabel timeLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private JScrollPane contKamar = new JScrollPane();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
    private int nomorKamar;
    private int harga;
    private Date masuk;
    private Date keluar;
    private String namaPemesan;
    private String nik;
    private String telepon;
    private String userId;
    private long jumlahmalam;
    private long totalharga;
    private int ranjang;
    private int ac;

    public Pembayaran(int nomorKamar, int harga, Date masuk, Date keluar, String namaPemesan, String nik, String telepon) {
        try {
            con = new DbConnect();
            statement = con.getConnection().createStatement();
            dataFromDB = statement.executeQuery("SELECT * FROM dataKamar");
            while (dataFromDB.next()) {
                ranjang = dataFromDB.getInt("ranjang");
                ac = dataFromDB.getInt("ac");
                harga = dataFromDB.getInt(6);

            }
            dataFromDB = statement.executeQuery("SELECT userId FROM datauser WHERE username='"+nik+"' ORDER BY userId DESC LIMIT 1");
            dataFromDB.next();
            userId = dataFromDB.getString(1);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        this.nomorKamar = nomorKamar;
        this.harga = harga;
        this.masuk = masuk;
        this.keluar = keluar;
        this.jumlahmalam = (keluar.getTime() - masuk.getTime())/ 1000 / 60 / 60 / 24;
        this.totalharga = jumlahmalam*harga;
        this.namaPemesan = namaPemesan;
        this.nik = nik;
        this.telepon = telepon;
        init();
    }

    public void init() {
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JLabel labelNama = new JLabel("Pembayaran");
        JPanel contButton = new JPanel(new GridLayout(3, 1, 0, 20));
        JButton btnExit = new JButton("Home");
        JPanel contKamar = new JPanel(null);
        JPanel contInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        JLabel labelPenanda = new JLabel("Isi Pembayaran");
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

        labelPenanda.setPreferredSize(new Dimension(290, 70));
        labelPenanda.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
        labelPenanda.setFont(new Font("Inter", Font.BOLD, 32));
        labelPenanda.setBackground(Color.white);
        labelPenanda.setOpaque(true);

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

        contInput.add(labelPenanda);
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
        panelNamaPemesan.add(inputNamaPemesan);
        inputNamaPemesan.disable();
        inputNamaPemesan.setText(namaPemesan);

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
        inputNIK.disable();
        inputNIK.setText(nik);

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
        inputTelepon.disable();
        inputTelepon.setText(telepon);

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
        acGroup.add(acCheckBox);
        checkBoxPanel.add(acCheckBox);

        JCheckBox nonAcCheckBox = new JCheckBox("Non AC");
        nonAcCheckBox.setBackground(new Color(146, 146, 146));
        acGroup.add(nonAcCheckBox);
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

        JPanel panelCetak= new JPanel();
        panelCetak.setLayout(new BoxLayout(panelCetak, BoxLayout.Y_AXIS));
        panelCetak.setBounds(50, 568, 311, 50);

        JButton btnCetak = new JButton("Cetak");
        btnCetak.setFont(new Font("Inter", Font.BOLD, 20));
        btnCetak.setBackground(Color.WHITE);
        btnCetak.setPreferredSize(new Dimension(300, 100));

        panelCetak.add(Box.createVerticalGlue());
        panelCetak.add(btnCetak);
        panelCetak.add(Box.createVerticalGlue());

        contKamar.add(panelCetak);

        contKamar.setVisible(true);

        contKamar.add(panelNamaPemesan);
        contKamar.add(panelNIK);
        contKamar.add(panelTelepon);
        contKamar.add(panelCheckin);
        contKamar.add(panelCheckout);
        contKamar.add(panelJumlahmalam);
        contKamar.add(panelTipeKamar);
        contKamar.add(panelTotalHarga);
        contKamar.add(panelCetak);

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

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home();
                home.setVisible(true);
                dispose();
            }
        });

        btnCetak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                new Invoice(userId,nomorKamar,(int)totalharga,formatter.format(masuk),formatter.format(keluar),namaPemesan);
                JOptionPane.showMessageDialog(null, "Invoice Berhasil Dicetak");
                dispose();
            }
        });

    }

    public void updateTime() {
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
            Pembayaran halo = new Pembayaran(100,600, new Date(), new Date(), "Nama Pemesan", "16 Karakter", "+62");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
