import org.jdesktop.swingx.JXDatePicker;
import config.DbConnect;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RiwayatTransaksi extends JFrame implements WindowBehavior{
    Color warna = Color.WHITE;
    DbConnect con = new DbConnect();
    Statement statement = null;
    ResultSet dataFromDB = null;
    private JLabel timeLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
    private DefaultTableModel dataTblKamar;
    private JLabel dataKamar = new JLabel();
    private JLabel dataPengunjung = new JLabel();
    
    public RiwayatTransaksi(){
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
            String tahun = Integer.toString(new Date().getYear()+1900);
            String bulan = String.format("%02d", new Date().getMonth()-1);
            String query = "SELECT u.name,r.nomorKamar,r.harga,r.checkIn,r.checkOut FROM riwayat r JOIN datauser u ON r.userId=u.userId WHERE checkIn > '"+tahun+"-"+bulan+"-01' ORDER BY checkIn";
            dataFromDB = statement.executeQuery(query);
            dataTblKamar.setRowCount(0);
            while(dataFromDB.next()){
                dataTblKamar.addRow(new Object[]{dataFromDB.getString(1),dataFromDB.getString(2),Integer.toString(dataFromDB.getInt(3)),dataFromDB.getString(4),dataFromDB.getString(5)});
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
    public void init(){
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JPanel contMain = new JPanel(null);
        JPanel contButton = new JPanel(new GridLayout(9,1,0,20));
        
        JTable tabelKamar;        
        
        JScrollPane scrKamar = new JScrollPane();

        JLabel labelNama = new JLabel("RiwayatTransaksi Hotel Disyfa");
        JLabel labelKamar = new JLabel("Sisa Kamar");
        JLabel labelPengunjung = new JLabel("Pengunjung");
        JLabel labelPenghasilan = new JLabel("Bulan ini :");

        JButton btnExit = new JButton("Keluar");        

        dataTblKamar = new DefaultTableModel(new Object[][]{},new String[]{"Nama","Kamar","Harga","Masuk","Keluar"});
        tabelKamar = new JTable(dataTblKamar);
        tabelKamar.disable();
        tabelKamar.setRowHeight(30);
        tabelKamar.setFont(new Font("Inter",Font.PLAIN,15));           

        scrKamar.setBounds(0, 0, 1120, 650);
        scrKamar.setViewportView(tabelKamar);

        contJam.setBounds(0,0,350,160);        

        contDetails.setBounds(WindowSize.width-350,0,350,160);

        contButton.setBounds(WindowSize.width-350,200,350,650);
        contButton.setBackground(warna);
        contButton.setBorder(BorderFactory.createEmptyBorder(0,30,0,67));
        
        contMain.setBounds(67,200,1120,650);
       
        labelNama.setBounds(350,0, WindowSize.width-700,160);
        labelNama.setFont(new Font("Inter", Font.BOLD,48));
        labelNama.setHorizontalAlignment(JLabel.CENTER);

        labelKamar.setFont(new Font("Inter", Font.BOLD,20));
        labelKamar.setBounds(67,30,180,50);
        
        labelPenghasilan.setFont(new Font("Inter", Font.BOLD,20));
        labelPengunjung.setFont(new Font("Inter", Font.BOLD,20));
        labelPengunjung.setBounds(67,80,180,50);
        
        dataPengunjung.setFont(new Font("Inter", Font.BOLD,20));
        dataPengunjung.setBounds(247,80,230,50);

        dataKamar.setFont(new Font("Inter", Font.BOLD,20));
        dataKamar.setBounds(247,30,230,50);

        btnExit.setFont(new Font("Inter", Font.BOLD,32));

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin home = new Admin();
                home.setVisible(true);
                dispose();
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
        try {
            dataFromDB = statement.executeQuery("SELECT SUM(harga) FROM riwayat");
            dataFromDB.next();
        NumberFormat formatter = NumberFormat.getInstance(new Locale("id", "ID"));
        String formatted = formatter.format(dataFromDB.getInt(1));
            labelPenghasilan.setText("Total Rp "+formatted);            
        } catch (Exception e) {
            System.out.println(e);
        }
        timer.start();

        updateTime();
        contJam.add(dateLabel);
        contJam.add(timeLabel);

        contDetails.add(labelKamar);
        contDetails.add(labelPengunjung);
        contDetails.add(dataKamar);
        contDetails.add(dataPengunjung);

        contMain.add(scrKamar);

        contButton.add(labelPenghasilan);
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(new JLabel());
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
            RiwayatTransaksi halo = new RiwayatTransaksi();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}

