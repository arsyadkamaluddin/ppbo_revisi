import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Invoice extends JFrame{
    public Invoice(String userId,int nomorKamar,int harga, String in,String out,String nama){
        JPanel header = new JPanel();
        JPanel container = new JPanel();
        JLabel labelHeader = new JLabel("Invoice Reservasi Hotel Disyfa");
        JLabel labelBooking = new JLabel("Kode Booking");
        JLabel labelNomor = new JLabel("Nomor Kamar");
        JLabel labelHarga = new JLabel("Total Harga");
        JLabel labelIn = new JLabel("Check IN");
        JLabel labelOut = new JLabel("Check Out");
        JLabel labelNama = new JLabel("Nama");

        JLabel dataBooking = new JLabel(userId);
        JLabel dataNomor = new JLabel(Integer.toString(nomorKamar));
        JLabel dataHarga = new JLabel(Integer.toString(harga));
        JLabel dataIn = new JLabel(in);
        JLabel dataOut = new JLabel(out);
        JLabel dataNama = new JLabel(nama);
        
        

        container.setBounds(0,120,WindowSize.width/2,WindowSize.heigth-120);
        container.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 20));
        header.setBounds(0,0,WindowSize.width/2,120);
        header.setBackground(Color.BLUE);
        container.setBackground(Color.WHITE);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // labelHeader.setBounds();
        labelHeader.setFont(new Font("Inter",Font.BOLD,45));
        labelHeader.setForeground(Color.white);
        labelBooking.setFont(new Font("Inter",Font.BOLD,35));
        labelNomor.setFont(new Font("Inter",Font.BOLD,35));
        labelHarga.setFont(new Font("Inter",Font.BOLD,35));
        labelIn.setFont(new Font("Inter",Font.BOLD,35));
        labelOut.setFont(new Font("Inter",Font.BOLD,35));
        labelNama.setFont(new Font("Inter",Font.BOLD,35));
        dataBooking.setFont(new Font("Inter",Font.PLAIN,20));
        dataNomor.setFont(new Font("Inter",Font.PLAIN,20));
        dataHarga.setFont(new Font("Inter",Font.PLAIN,20));
        dataIn.setFont(new Font("Inter",Font.PLAIN,20));
        dataOut.setFont(new Font("Inter",Font.PLAIN,20));
        dataNama.setFont(new Font("Inter",Font.PLAIN,20));
        labelHeader.setAlignmentX(CENTER_ALIGNMENT);
        header.add(labelHeader);

        container.add(labelBooking);
        container.add(dataBooking);
        container.add(labelNama);
        container.add(dataNama);
        container.add(labelNomor);
        container.add(dataNomor);
        container.add(labelHarga);
        container.add(dataHarga);
        container.add(labelIn);
        container.add(dataIn);
        container.add(labelOut);
        container.add(dataOut);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WindowSize.width/2, WindowSize.heigth);
        setUndecorated(true);
        JLabel label = new JLabel("Hello, PNG!");
        getContentPane().setLayout(null);
        add(label);
        add(header);
        add(container);
        setVisible(true);
        saveFrameToPNG(this, "invoice/"+userId+".png");
        dispose();
        
    }  
    

    public static void saveFrameToPNG(JFrame frame, String filePath) {
        try {
            BufferedImage bufferedImage = new BufferedImage(
                    frame.getWidth(),
                    frame.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            frame.paint(g2d);
            g2d.dispose();
            ImageIO.write(bufferedImage, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Invoice("C031",204,200000,"2024-06-26","2024-06-26","Arsyady");
    }
}
