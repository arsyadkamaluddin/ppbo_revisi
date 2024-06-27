import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import config.DbConnect;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class EditBase extends JFrame implements WindowBehavior {
    protected ResultSet dataFromDB = null;
    protected Statement statement = null;
    protected DbConnect con = new DbConnect();
    protected JLabel timeLabel = new JLabel();
    protected JLabel dateLabel = new JLabel();
    protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
    protected JTable dataTable;
    protected DefaultTableModel tableModel;
    protected JLabel updateStatusLabel = new JLabel();
    protected JTextField inputID;

    public EditBase() {
        try {
            statement = con.getConnection().createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        init();
    }

    public abstract void init();

    public void updateTime() {
        String currentTime = timeFormat.format(new Date());
        String currentDate = dateFormat.format(new Date());
        timeLabel.setText(currentTime);
        dateLabel.setText(currentDate);
    }

    protected void resetForm() {
        inputID.setText("Masukkan ID...");
    }

    protected void updateTableData(String query, String[] columnNames) {
        tableModel.setRowCount(0);
        try {
            dataFromDB = statement.executeQuery(query);
            while (dataFromDB.next()) {
                Object[] rowData = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    rowData[i] = dataFromDB.getString(columnNames[i]);
                }
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating table data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void showMessageDialog(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    protected void addActionListener(AbstractButton button, ActionListener listener) {
        button.addActionListener(listener);
    }

    protected void addKeyListener(Component component, KeyListener listener) {
        if (component instanceof JTextField) {
            ((JTextField) component).addKeyListener(listener);
        }
    }
}
