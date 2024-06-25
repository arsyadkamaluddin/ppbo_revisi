import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;

public class UserClass {
    private String userId;
    private String name;
    private String username;
    private String password;
    private String role;

    public UserClass(String userId, String name, String username, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public JPanel createUserCard() {
        JPanel container = new JPanel();
        JPanel contLeft = new JPanel();
        JLabel userIdLabel = new JLabel("ID: " + this.userId);
        JLabel nameLabel = new JLabel("Name: " + this.name);
        JLabel usernameLabel = new JLabel("Username: " + this.username);
        JLabel roleLabel = new JLabel("Role: " + this.role);

        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        contLeft.setLayout(new BoxLayout(contLeft, BoxLayout.Y_AXIS));
        contLeft.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 60));
        contLeft.setBackground(new Color(0, 0, 0, 0));
        contLeft.setOpaque(true);
        container.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        userIdLabel.setSize(150, 40);
        userIdLabel.setFont(new Font("Inter", Font.BOLD, 18));

        nameLabel.setSize(150, 40);
        nameLabel.setFont(new Font("Inter", Font.BOLD, 18));

        usernameLabel.setSize(150, 40);
        usernameLabel.setFont(new Font("Inter", Font.BOLD, 18));

        roleLabel.setSize(150, 40);
        roleLabel.setFont(new Font("Inter", Font.BOLD, 18));

        container.setPreferredSize(new Dimension(400, 90));
        container.setBackground(Color.LIGHT_GRAY);

        contLeft.add(userIdLabel);
        contLeft.add(nameLabel);
        contLeft.add(usernameLabel);
        contLeft.add(roleLabel);
        container.add(contLeft);

        container.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                System.out.println(userId);
            }

            public void mouseEntered(MouseEvent me) {
                container.setBackground(Color.darkGray);
            }

            public void mouseExited(MouseEvent me) {
                container.setBackground(Color.LIGHT_GRAY);
            }
        });

        return container;
    }

    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/yourdatabase";
        String user = "yourusername";
        String password = "yourpassword";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM datauser")) {

            JFrame frame = new JFrame("User List");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(0, 1));

            while (rs.next()) {
                String userId = rs.getString("userId");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String userPassword = rs.getString("password");
                String role = rs.getString("role");

                UserClass userClass = new UserClass(userId, name, username, userPassword, role);
                frame.add(userClass.createUserCard());
            }

            frame.pack();
            frame.setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
