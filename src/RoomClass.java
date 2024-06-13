import javax.swing.*;
public class RoomClass {
    private String roomNumber;
    private boolean doubleBed;
    private boolean ac;
    private boolean available;
    private int price;

    public RoomClass(String roomNumber,int bed,String ac,String available,int price){
        this.roomNumber = roomNumber;
        this.doubleBed = bed==2;
        this.ac = ac.equals("true");
        this.available = available.equals("available");
        this.price = price;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public boolean isAc() {
        return ac;
    }
    public boolean isDoubleBed() {
        return doubleBed;
    }
    public boolean isAvailable() {
        return available;
    }
    public int getPrice() {
        return price;
    }
//    public JPanel createCard(){
//        JPanel container = new JPanel;
//
//        return container;
//    }
}
