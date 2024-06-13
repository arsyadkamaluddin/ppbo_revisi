
import config.DbConnect;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;

public class AdminLogin extends JFrame{
    ResultSet dataLoginFromDB = null;
    Statement statement = null;
    DbConnect con = null;
    private JPasswordField inputPassword;
    private JTextField inputUsername;


    public AdminLogin() throws SQLException {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        initComponents();
        con = new DbConnect();
        statement = con.getConnection().createStatement();
    }

    private void resetForm(){
        inputUsername.setText(null);
        inputPassword.setText(null);
        inputUsername.requestFocus();
    }

    private void initComponents() {
        JButton tblExit = new JButton("Keluar");
        JLabel labelJudul = new JLabel("Admin Sistem Management Hotel");
        JLabel labelUsername= new JLabel("username");
        JLabel labelPassword = new JLabel("password");
        JPanel formLogin = new JPanel();
        JPanel panelButton = new JPanel();
        inputUsername = new JTextField();
        inputPassword = new JPasswordField();


        setFont(new Font("Agency FB", 0, 18));

        labelJudul.setFont(new Font("Tahoma", 1, 31));

        inputUsername.setFont(new Font("Tahoma",0, 12));
        inputUsername.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        labelUsername.setFont(new Font("Tahoma", 0, 18));
        labelUsername.setForeground(new Color(0, 153, 204));

        labelPassword.setFont(new Font("Tahoma", 0, 18));
        labelPassword.setForeground(new Color(0, 153, 204));

        inputPassword.setFont(new Font("Tahoma", 1, 12));
        inputPassword.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        inputPassword.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)loginValidation();

            }
        });

        tblExit.setBackground(new Color(0, 0, 0));
        tblExit.setFont(new Font("Tahoma", 1, 14));
        tblExit.setForeground(new Color(0, 204, 204));
        tblExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tblExitActionPerformed(evt);
            }
        });

//        GroupLayout layout = new GroupLayout(getContentPane());
        GridLayout layout = new GridLayout(3,1,0,20);
        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(50,50,0,50));
        container.setLayout(layout);
        formLogin.setLayout(new GridLayout(2,2,0,30));
        formLogin.add(labelUsername,0);
        formLogin.add(inputUsername,1);
        formLogin.add(labelPassword,2);
        formLogin.add(inputPassword,3);
        panelButton.add(tblExit,0);
        container.add(labelJudul);
        container.add(formLogin);
        container.add(panelButton);
        add(container);
        pack();
    }

    private void loginValidation() {
        try {
            Statement db = con.getConnection().createStatement();
            String query = "SELECT * FROM dataUser WHERE username='" + inputUsername.getText() + "' AND role='admin'";
            dataLoginFromDB = db.executeQuery(query);

            if (dataLoginFromDB.next()) {
                if (dataLoginFromDB.getString(2).equals(inputPassword.getText())) {
                    Home o = new Home();
                    o.setVisible(true);
                    o.setLocationRelativeTo(null);
                    this.dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this,"Username atau Password salah!","TIDAK VALID",JOptionPane.WARNING_MESSAGE);
            resetForm();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    private void tblExitActionPerformed(ActionEvent evt) {
        new Home();
        dispose();
    }
}
