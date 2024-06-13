import org.jdesktop.swingx.JXDatePicker;
import config.DbConnect;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Home extends JFrame{
    ResultSet dataFromDB = null;
    Statement statement = null;
    DbConnect con = null;
    private ArrayList<RoomClass> daftarKamar = new ArrayList<RoomClass>();
    private JLabel timeLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private  JScrollPane contKamar = new JScrollPane();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));

    public Home(){
        try{
            con = new DbConnect();
            statement = con.getConnection().createStatement();
            dataFromDB = statement.executeQuery("SELECT * FROM dataKamar");
            while(dataFromDB.next()){
                RoomClass baru = new RoomClass(dataFromDB.getString(2),dataFromDB.getInt(3),dataFromDB.getString(4),dataFromDB.getString(5),dataFromDB.getInt(6));
                daftarKamar.add(baru);
            }
            System.out.println(daftarKamar.size());
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        init();

    }
    private void init(){
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JLabel labelNama = new JLabel("Hotel Disyfa");
        JLabel labelKamar = new JLabel("Sisa Kamar");
        JLabel labelPengunjung = new JLabel("Pengunjung");
        JLabel dataKamar = new JLabel("10");
        JLabel dataPengunjung = new JLabel("20");
        JPanel contButton = new JPanel(new GridLayout(9,1,0,20));
        JButton btnAdmin = new JButton("Admin");
        JButton btnCustomers = new JButton("Customers");
        JButton btnRooms = new JButton("Rooms");
        JTextField inputIn = new JTextField("Check IN : ");
        JTextField inputOut = new JTextField("Check OUT : ");
        JButton btnLogout = new JButton("Logout");
        JButton btnExit = new JButton("Keluar");
        JPanel contKamar = new JPanel(null);
        JPanel contInput = new JPanel();
        JXDatePicker inputTanggal = new JXDatePicker(new Locale("id","ID"));
        contJam.setBounds(0,0,350,160);
        contJam.setBackground(Color.ORANGE);

        contDetails.setBounds(WindowSize.width-350,0,350,160);
        contDetails.setBackground(Color.ORANGE);

        contButton.setBounds(WindowSize.width-350,200,350,650);
        contButton.setBackground(new Color(0,0,0,0));
        contButton.setOpaque(true);
        contButton.setBorder(BorderFactory.createEmptyBorder(0,30,0,67));

        contKamar.setBounds(67,200,1120,650);

        contInput.setLayout(new BoxLayout(contInput,BoxLayout.X_AXIS));
        contInput.setBounds(0,0,1120,100);
        contInput.setBackground(Color.BLUE);
        labelNama.setBounds(350,0,WindowSize.width-700,160);
        labelNama.setFont(new Font("Inter", Font.BOLD,48));
        labelNama.setHorizontalAlignment(JLabel.CENTER);

        labelKamar.setFont(new Font("Inter", Font.BOLD,20));
        labelKamar.setBounds(67,30,180,50);

        labelPengunjung.setFont(new Font("Inter", Font.BOLD,20));
        labelPengunjung.setBounds(67,80,180,50);

        dataPengunjung.setFont(new Font("Inter", Font.BOLD,20));
        dataPengunjung.setBounds(247,80,230,50);

        dataKamar.setFont(new Font("Inter", Font.BOLD,20));
        dataKamar.setBounds(247,30,230,50);

        btnAdmin.setFont(new Font("Inter", Font.BOLD,32));
        btnRooms.setFont(new Font("Inter", Font.BOLD,32));
        btnCustomers.setFont(new Font("Inter", Font.BOLD,32));
        btnExit.setFont(new Font("Inter", Font.BOLD,32));
        btnLogout.setFont(new Font("Inter", Font.BOLD,32));
        inputIn.setFont(new Font("Inter", Font.ITALIC,20));
        inputOut.setFont(new Font("Inter", Font.ITALIC,20));

        inputTanggal.setBounds(0,0,300,100);
        inputTanggal.getEditor().setEditable(false);
        inputTanggal.getEditor().setText(null);
        inputTanggal.setDate(new Date());
        inputTanggal.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));
        inputTanggal.setBackground(Color.RED);



        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Login halo = new Login();
                    halo.setVisible(true);
                    halo.setLocationRelativeTo(null);
                    dispose();
                }catch (Exception err){

                }

            }
        });

        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    AdminLogin halo = new AdminLogin();
                    halo.setVisible(true);
                    halo.setLocationRelativeTo(null);
                    dispose();
                }catch (Exception err){

                }

            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        inputIn.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(inputIn.getText().equals("Check IN : ")){
                    inputIn.setText("");
                }else if(inputIn.getText().equals("")){
                    inputIn.setText("Check IN : ");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        inputOut.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(inputOut.getText().equals("Check OUT : ")){
                    inputOut.setText("");
                }else if(inputOut.getText().equals("")){
                    inputOut.setText("Check OUT : ");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        timeLabel.setFont(new Font("Inter", Font.BOLD,20));
        timeLabel.setBounds(67,30,230,50);

        dateLabel.setFont(new Font("Inter", Font.BOLD,20));
        dateLabel.setBounds(67,80,230,50);

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
        contDetails.add(labelKamar);
        contDetails.add(labelPengunjung);
        contDetails.add(dataKamar);
        contDetails.add(dataPengunjung);
        contButton.add(btnAdmin);
        contButton.add(btnCustomers);
        contButton.add(btnRooms);
        contButton.add(new JLabel());
        contButton.add(inputIn);
        contButton.add(inputOut);
        contButton.add(new JLabel());
        contButton.add(btnLogout);
        contButton.add(btnExit);
        contKamar.add(contInput);
        contInput.add(inputTanggal);
        add(contJam);
        add(contDetails);
        add(labelNama);
        add(contButton);
        add(contKamar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WindowSize.width,WindowSize.heigth);
        setUndecorated(true);
        setLayout(null);
        getContentPane().setBackground(Color.RED);
        setVisible(true);
    }
    private void updateKamar(){
        for(RoomClass kamar:daftarKamar){
            JLabel nama = new JLabel(kamar.getRoomNumber());

        }
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
        }catch (Exception e){
            System.out.println(e.toString());
        }
        try{
            Home halo = new Home();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
