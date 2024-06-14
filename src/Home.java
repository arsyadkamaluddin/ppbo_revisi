import org.jdesktop.swingx.JXDatePicker;
import config.DbConnect;
import javax.sound.sampled.BooleanControl;
import javax.swing.*;
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
import java.util.TimerTask;

public class Home extends JFrame{
    Color warna = Color.RED;
    DbConnect con = new DbConnect();
    Statement statement = null;
    ResultSet dataFromDB = null;
    private JXDatePicker inputTanggal = new JXDatePicker(new Locale("id","ID"));
    private JCheckBox cekAc = new JCheckBox("AC");
    private JCheckBox cekDouble = new JCheckBox("Double Bed");
    private ArrayList<RoomClass> daftarKamar = new ArrayList<RoomClass>();
    private JLabel timeLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private  JScrollPane contMain = new JScrollPane();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));

    public Home(){
        try {
            statement = con.getConnection().createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        updateKamar();
        init();

    }
    private void updateKamar(){
        String query = "CALL get_available_rooms('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00','1','0')";
        try{
            dataFromDB = statement.executeQuery(query);
            while(dataFromDB.next()){
                RoomClass baru = new RoomClass(dataFromDB.getString(1),dataFromDB.getInt(2),dataFromDB.getInt(3),dataFromDB.getInt(4));
                daftarKamar.add(baru);
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }
    private void updateKamar(JPanel cont){
        cont.removeAll();
        daftarKamar.clear();
        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(inputTanggal.getDate());
        String acC = cekAc.isSelected()?"1":"0";
        String doubleBe = cekDouble.isSelected()?"2":"1";
        String query = "CALL get_available_rooms('"+tanggal+" 00:00:00','"+doubleBe+"','"+acC+"')";
        try{
            dataFromDB = statement.executeQuery(query);
            while(dataFromDB.next()){
                RoomClass baru = new RoomClass(dataFromDB.getString(1),dataFromDB.getInt(2),dataFromDB.getInt(3),dataFromDB.getInt(4));
                daftarKamar.add(baru);
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        Timer t = new Timer(100, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                cont.removeAll();
                for(RoomClass kamar:daftarKamar){
                    cont.add(kamar.createCard());
                }          
            }
        });
        t.setRepeats(false);
        t.start();
    }
    public class CustomCheckBoxIcon implements Icon {
        private int size;
        

        public CustomCheckBoxIcon(int size) {
            this.size = size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            JCheckBox cb = (JCheckBox) c;
            if (cb.isSelected()) {
                g.setColor(Color.BLACK);
                g.fillArc(x,y,size-1,size-1,0,360);
            } else {
                g.setColor(Color.WHITE);
                g.fillArc(x,y,size-1,size-1,0,360);
//                g.drawRect(x, y, size - 1, size - 1);
            }
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }

    private void init(){
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JPanel contMain = new JPanel(null);
        JPanel contInput = new JPanel();
        JPanel contButton = new JPanel(new GridLayout(9,1,0,20));
        JPanel contKamar = new JPanel(null);
        // JPanel contKamar = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));

        JLabel labelNama = new JLabel("Hotel Disyfa");
        JLabel labelKamar = new JLabel("Sisa Kamar");
        JLabel labelPengunjung = new JLabel("Pengunjung");
        JLabel dataKamar = new JLabel("10");
        JLabel dataPengunjung = new JLabel("20");

        JButton btnAdmin = new JButton("GATAU");
        JButton btnCustomers = new JButton("Customers");
        JButton btnRooms = new JButton("Rooms");
        JButton btnLogout = new JButton("Logout");
        JButton btnExit = new JButton("Keluar");
        JTextField inputIn = new JTextField("Check IN : ");
        JTextField inputOut = new JTextField("Check OUT : ");


        contJam.setBounds(0,0,350,160);
        contJam.setBackground(Color.ORANGE);
        

        contDetails.setBounds(WindowSize.width-350,0,350,160);
        contDetails.setBackground(Color.ORANGE);

        contButton.setBounds(WindowSize.width-350,200,350,650);
        contButton.setBackground(warna);
        contButton.setBorder(BorderFactory.createEmptyBorder(0,30,0,67));
        
        contMain.setBounds(67,200,1120,650);
        contKamar.setBounds(0,100,1120, 1000);
        contKamar.setLayout(new FlowLayout(FlowLayout.LEADING,20,20));
        contKamar.setBorder(BorderFactory.createEmptyBorder(30, 80, 0, 80));

        contInput.setLayout(new GridLayout(1,3,0,0));
        contInput.setBorder(BorderFactory.createEmptyBorder(10,50,10,350));
        contInput.setBounds(0,0,1120,100);
        labelNama.setBounds(350,0, WindowSize.width-700,160);
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

        cekAc.setFont(new Font("Inter", Font.ITALIC,30));
        cekAc.setIcon(new CustomCheckBoxIcon(30));

        cekDouble.setFont(new Font("Inter", Font.ITALIC,30));
        cekDouble.setIcon(new CustomCheckBoxIcon(30));


        inputTanggal.setBounds(0,0,300,100);
        inputTanggal.getEditor().setEditable(false);
        // inputTanggal.getEditor().setText(null);
        inputTanggal.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));
        inputTanggal.setBackground(Color.RED);
        inputTanggal.setFont(new Font("Inter", Font.ITALIC,20));
        inputTanggal.setDate(new Date());

        for(RoomClass kamar:daftarKamar){
            contKamar.add(kamar.createCard());
        }

        contKamar.setBackground(Color.GREEN);
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
        cekAc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateKamar(contKamar);
            }
        });
        cekDouble.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateKamar(contKamar);
            }
        });
        inputTanggal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateKamar(contKamar);
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

        contMain.add(contInput);
        contMain.add(contKamar);

        contInput.add(inputTanggal);
        contInput.add(cekAc);
        contInput.add(cekDouble);

        add(contJam);
        add(contDetails);
        add(labelNama);
        add(contButton);
        add(contMain);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WindowSize.width, WindowSize.heigth);
        setUndecorated(true);
        setLayout(null);
        getContentPane().setBackground(warna);
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

