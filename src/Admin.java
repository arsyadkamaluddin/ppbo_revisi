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
import java.util.Date;
import java.util.Locale;

public class Admin extends JFrame implements WindowBehavior{
    Color warna = Color.WHITE;
    DbConnect con = new DbConnect();
    Statement statement = null;
    ResultSet dataFromDB = null;
    private JCheckBox cekAc = new JCheckBox("AC");
    private JCheckBox cekDouble = new JCheckBox("Double Bed");
    private JLabel timeLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
    private DefaultTableModel dataTblKamar;
    private DefaultTableModel dataTblUser;
    private JLabel dataKamar = new JLabel();
    private JLabel dataPengunjung = new JLabel();
    private JTextField inputNomor = new JTextField("Nomor Kamar ");
    private JTextField inputHarga = new JTextField("Harga ");
    
    public Admin(){
        try {
            statement = con.getConnection().createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        init();
        updateTable();

    }    

    private void updateTable(){
        try{
            statement.executeQuery("CALL update_stat('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"')");
            String query = "SELECT * FROM dataKamar ORDER BY nomorKamar";
            dataFromDB = statement.executeQuery(query);
            dataTblKamar.setRowCount(0);
            while(dataFromDB.next()){
                dataTblKamar.addRow(new Object[]{dataFromDB.getString(2),dataFromDB.getInt(3)==2?"Double":"Single",dataFromDB.getInt(4)==0?"NON-AC":"AC",dataFromDB.getString(5),dataFromDB.getInt(6)});
            }
            query = "SELECT * FROM dataUser ORDER BY name";
            dataFromDB = statement.executeQuery(query);
            dataTblUser.setRowCount(0);
            while(dataFromDB.next()){
                dataTblUser.addRow(new Object[]{dataFromDB.getString(2),dataFromDB.getString(3),dataFromDB.getString(4)});
            }
            query = "SELECT COUNT(nomorKamar) FROM dataKamar WHERE status!='Used'";
            dataFromDB = statement.executeQuery(query);
            while(dataFromDB.next()){
                dataKamar.setText(Integer.toString(dataFromDB.getInt(1)));
            }
            query = "SELECT COUNT(nomorKamar) FROM dataKamar WHERE status='Used'";
            dataFromDB = statement.executeQuery(query);
            while(dataFromDB.next()){
                dataPengunjung.setText(Integer.toString(dataFromDB.getInt(1)));
            }
            
        }catch (SQLException e){
            System.out.println(e.toString());
        }
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

    public void init(){
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JPanel contMain = new JPanel(null);
        JPanel contButton = new JPanel(new GridLayout(9,1,0,20));
        JPanel contOpsi = new JPanel();

        
        JTable tabelKamar;
        JTable tabelUser;
        
        
        JScrollPane scrKamar = new JScrollPane();
        JScrollPane scrUser = new JScrollPane();

        JLabel labelNama = new JLabel("Admin Hotel Disyfa");
        JLabel labelKamar = new JLabel("Sisa Kamar");
        JLabel labelPengunjung = new JLabel("Pengunjung");

        JButton btnUser = new JButton("Edit User");
        JButton btnKamar = new JButton("Edit Kamar");
        JButton btnTambah = new JButton("Tambah");
        JButton btnRiwayat = new JButton("Transaksi");
        JButton btnExit = new JButton("Keluar");

        

        dataTblKamar = new DefaultTableModel(new Object[][]{},new String[]{"Nomor","Ranjang","AC","Status","Harga"});
        dataTblUser = new DefaultTableModel(new Object[][]{},new String[]{"Nama","NIK","No Telepon"});
        tabelKamar = new JTable(dataTblKamar);
        tabelKamar.disable();
        tabelKamar.setRowHeight(30);
        tabelKamar.setFont(new Font("Inter",Font.PLAIN,15));
        
        tabelUser = new JTable(dataTblUser);
        tabelUser.disable();
        tabelUser.setRowHeight(30);
        tabelUser.setFont(new Font("Inter",Font.PLAIN,15));        

        scrKamar.setBounds(0, 0, 1120, 325);
        scrKamar.setViewportView(tabelKamar);
        scrUser.setBounds(0, 326, 1120, 325);
        scrUser.setViewportView(tabelUser);


        contJam.setBounds(0,0,350,160);
        

        contDetails.setBounds(WindowSize.width-350,0,350,160);

        contButton.setBounds(WindowSize.width-350,200,350,650);
        contButton.setBackground(warna);
        contButton.setBorder(BorderFactory.createEmptyBorder(0,30,0,67));
        
        contMain.setBounds(67,200,1120,650);

        contOpsi.setPreferredSize(new Dimension(290,60));
        // contOpsi.setLayout(new GridLayout(1,2));
        contOpsi.setBorder(BorderFactory.createEmptyBorder(7, 5, 5, 5));
       
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

        btnUser.setFont(new Font("Inter", Font.BOLD,32));
        btnTambah.setFont(new Font("Inter", Font.BOLD,32));
        btnKamar.setFont(new Font("Inter", Font.BOLD,32));
        btnExit.setFont(new Font("Inter", Font.BOLD,32));
        btnRiwayat.setFont(new Font("Inter", Font.BOLD,32));
        inputNomor.setFont(new Font("Inter", Font.ITALIC,20));
        inputHarga.setFont(new Font("Inter", Font.ITALIC,20));

        cekAc.setIcon(new CustomCheckBoxIcon(20));
        cekDouble.setIcon(new CustomCheckBoxIcon(20));
        cekAc.setFont(new Font("Inter", Font.ITALIC,20));
        cekDouble.setFont(new Font("Inter", Font.ITALIC,20));


        btnUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    EditUser halo = new EditUser();
                    halo.setVisible(true);
                    halo.setLocationRelativeTo(null);
                    dispose();
                }catch (Exception err){

                }

            }
        });
        btnKamar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    EditKamar halo = new EditKamar();
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
                Home home = new Home();
                home.setVisible(true);
                dispose();
            }
        });
        btnRiwayat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RiwayatTransaksi home = new RiwayatTransaksi();
                home.setVisible(true);
                dispose();
            }
        });

        inputNomor.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(Character.isDigit(e.getKeyChar())){
                    if(inputNomor.getText().equals("Nomor Kamar ")){
                        inputNomor.setText("");
                    }else if(inputNomor.getText().equals("")){
                        inputNomor.setText("Nomor Kamar ");
                    }
                }                
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                if(!Character.isDigit(e.getKeyChar())){
                    inputNomor.setText("Nomor Kamar ");
                }
            }
        });
        inputHarga.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(Character.isDigit(e.getKeyChar())){
                    if(inputHarga.getText().equals("Harga ")){
                        inputHarga.setText("");
                    }else if(inputHarga.getText().equals("")){
                        inputHarga.setText("Harga ");
                    }
                }                
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                if(!Character.isDigit(e.getKeyChar())){
                    inputHarga.setText("Harga ");
                }
            }
        });
        btnTambah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(inputHarga.getText()=="Harga "&&inputNomor.getText()=="Nomor Kamar "){
                    JOptionPane.showMessageDialog(null, "Isikan data yang valid");
                }else{
                    if(addRoom(Integer.valueOf(inputNomor.getText()),Integer.valueOf(inputHarga.getText()),cekAc.isSelected(),cekDouble.isSelected())){
                        JOptionPane.showMessageDialog(null, "Kamar "+inputNomor.getText()+" berhasil ditambahkan");
                    }
                }
                resetForm();
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

        contOpsi.add(cekAc);
        contOpsi.add(cekDouble);

        contDetails.add(labelKamar);
        contDetails.add(labelPengunjung);
        contDetails.add(dataKamar);
        contDetails.add(dataPengunjung);

        contMain.add(scrKamar);
        contMain.add(scrUser);

        contButton.add(btnUser);
        contButton.add(btnKamar);
        contButton.add(new JLabel());
        contButton.add(inputNomor);
        contButton.add(inputHarga);
        contButton.add(contOpsi);
        contButton.add(btnTambah);
        contButton.add(btnRiwayat);
        contButton.add(btnExit);


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

    private boolean addRoom(int roomNumber,int price,boolean ac,boolean bed){
        int ranjang = bed?2:1;
        int ase = ac?1:0;
        try {
            String query = "INSERT INTO dataKamar (nomorKamar,ranjang,ac,status,harga) VALUES ('"+Integer.toString(roomNumber)+"','"+ranjang+"','"+ase+"','Available','"+price+"')";
            statement.executeUpdate(query);
            updateTable();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Kamar dengan nomor "+Integer.toString(roomNumber)+" telah ada");
            return false;
        }
    }

    private void resetForm(){
        cekAc.setSelected(false);
        cekDouble.setSelected(false);
        inputHarga.setText("Harga ");
        inputNomor.setText("Nomor Kamar ");
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
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        try{
            Admin halo = new Admin();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}

