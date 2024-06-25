import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
public class RoomClass {
    private String roomNumber;
    private boolean doubleBed;
    private boolean ac;
    private boolean available;
    private int price;

    public RoomClass(String roomNumber,int bed,int ac,int price){
        this.roomNumber = roomNumber;
        this.doubleBed = bed==2;
        this.ac = ac==1;
        this.price = price;
    }
   public JPanel createCard(){
       JPanel container = new JPanel();
       JPanel contLeft = new JPanel();
       JLabel nomorKamar = new JLabel(this.roomNumber);
       JLabel acLabel = new JLabel(this.ac?"AC":"NON-AC");
       JLabel doubleLabel = new JLabel(this.doubleBed?"Double Bed":"Single Bed");
       JLabel priceLabel = new JLabel(Integer.toString(this.price));

       container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));
       contLeft.setLayout(new BoxLayout(contLeft,BoxLayout.Y_AXIS));
       contLeft.setBorder(BorderFactory.createEmptyBorder(0,00,0,60));
       contLeft.setBackground(new Color(0,0,0,0));
       contLeft.setOpaque(true);
       container.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
       
       nomorKamar.setSize(150,40);
       nomorKamar.setFont(new Font("Inter", Font.BOLD,24));
       
       priceLabel.setSize(120,40);
       priceLabel.setFont(new Font("Inter", Font.BOLD,24));
       
       acLabel.setSize(120,40);
       acLabel.setFont(new Font("Inter", Font.BOLD,18));
       
       doubleLabel.setSize(120,40);
       doubleLabel.setFont(new Font("Inter", Font.BOLD,18));
       
       container.setPreferredSize(new Dimension(300,90));;
       container.setBackground(Color.LIGHT_GRAY);

       contLeft.add(nomorKamar);
       contLeft.add(acLabel);
       contLeft.add(doubleLabel);
       container.add(contLeft);
       container.add(priceLabel);

       container.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent me){
            System.out.println(roomNumber);
        }
        public void mouseEntered(MouseEvent me){
            container.setBackground(Color.darkGray);
        }
        public void mouseExited(MouseEvent me){
            container.setBackground(Color.LIGHT_GRAY);
        }
       });


       return container;
   }
}
