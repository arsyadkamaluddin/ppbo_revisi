import org.jdesktop.swingx.JXDatePicker;
import config.DbConnect;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.border.EmptyBorder;
public abstract class BookingBase extends JFrame {
    protected JLabel timeLabel = new JLabel();
    protected JLabel dateLabel = new JLabel();
    protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
    
    public BookingBase() {
        timeLabel.setFont(new Font("Inter", Font.BOLD, 20));
        timeLabel.setBounds(67, 30, 230, 50);
        
        dateLabel.setBounds(67, 80, 230, 50);
        dateLabel.setFont(new Font("Inter", Font.BOLD, 20));
        initTimeAndDateLabels();
    }
    
    protected void updateTime() {
        String currentTime = timeFormat.format(new Date());
        String currentDate = dateFormat.format(new Date());
        timeLabel.setText(currentTime);
        dateLabel.setText(currentDate);
    }
    
    private void initTimeAndDateLabels() {
        updateTime();
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();
    }
}


