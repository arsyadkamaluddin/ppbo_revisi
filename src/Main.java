import javax.swing.*;
public class Main {
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
            JFrame f = new JFrame();
            f.dispose();
            Login halo = new Login();
            halo.setVisible(true);
            halo.setLocationRelativeTo(null);
            return;
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
