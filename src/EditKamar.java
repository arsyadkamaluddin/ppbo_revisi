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

public class EditKamar extends JFrame implements WindowBehavior{
    ResultSet dataFromDB = null;
    Statement statement = null;
    DbConnect con = new DbConnect();
    private JLabel timeLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JRadioButton acButton;
    private JRadioButton nonAcButton;
    private JRadioButton singleButton;
    private JRadioButton doubleButton;
    private JTextField inputHarga;
    private JLabel updateStatusLabel = new JLabel();
    private JTextField inputID;
    private ButtonGroup bedGroup = new ButtonGroup();
    private ButtonGroup acGroup = new ButtonGroup();


    public EditKamar() {
        try {
            statement = con.getConnection().createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        init();
        updateTable();
    }

    private void updateTable() {
        String query = "SELECT * FROM datakamar";
        try {
            dataFromDB = statement.executeQuery(query);
            while (dataFromDB.next()) {
                tableModel.addRow(new Object[]{dataFromDB.getString("nomorKamar"), 
                    dataFromDB.getInt("ranjang") == 2 ? "Double" : "Single", 
                    dataFromDB.getInt("ac") == 0 ? "NON-AC" : "AC", 
                    dataFromDB.getString("status"), 
                    dataFromDB.getInt("harga")});
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private void resetForm(){
        inputID.setText("Masukkan nomor kamar...");
        bedGroup.clearSelection();
        acGroup.clearSelection();
        inputHarga.setText("Harga");
        
    }

    public void init() {
        JPanel contJam = new JPanel(null);
        JPanel contDetails = new JPanel(null);
        JLabel labelNama = new JLabel("Edit Kamar");
        JLabel labelKmrSearch = new JLabel("Nomor Kamar");
        inputID = new JTextField("Masukkan nomor kamar...");
        JButton btnSearchNoKmr = new JButton(">>");
        JPanel contButton = new JPanel(new GridLayout(9, 1, 0, 20));
        JPanel contAC = new JPanel(new GridLayout(1, 2, 20, 0));
        JPanel contBed = new JPanel(new GridLayout(1, 2, 20, 0));
        inputHarga = new JTextField("Harga");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");
        JButton btnExit = new JButton("Kembali");

        JPanel contKamar = new JPanel(null);

        JTable tabelKamar;

        contJam.setBounds(0, 0, 350, 160);
        contJam.setBackground(new Color(0xD9D9D9));

        contDetails.setBounds(WindowSize.width - 350, 0, 350, 160);
        contDetails.setBackground(new Color(0xD9D9D9));

        contButton.setBounds(WindowSize.width - 350, 200, 350, 650);
        contButton.setBackground(Color.WHITE);
        contButton.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 67));

        contAC.setBounds(WindowSize.width - 350, 200, 350, 50);
        contAC.setBackground(new Color(0xD9D9D9));
        contAC.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        contBed.setBounds(WindowSize.width - 350, 200, 350, 50);
        contBed.setBackground(new Color(0xD9D9D9));
        contBed.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        contKamar.setBounds(67, 200, 1120, 650);

        labelNama.setBounds(350, 0, WindowSize.width - 700, 160);
        labelNama.setFont(new Font("Inter", Font.BOLD, 72));
        labelNama.setHorizontalAlignment(JLabel.CENTER);
        labelKmrSearch.setFont(new Font("Inter", Font.BOLD, 30));
        labelKmrSearch.setBounds(10, 25, 240, 50);
        inputID.setFont(new Font("Inter", Font.ITALIC, 20));
        inputID.setBounds(7, 80, 240, 50);
        btnSearchNoKmr.setFont(new Font("Inter", Font.BOLD, 32));
        btnSearchNoKmr.setBounds(250, 80, 80, 50);
        inputHarga.setFont(new Font("Inter", Font.ITALIC, 20));
        btnDelete.setFont(new Font("Inter", Font.BOLD, 32));
        btnUpdate.setFont(new Font("Inter", Font.BOLD, 32));
        btnExit.setFont(new Font("Inter", Font.BOLD, 32));

        acButton = new JRadioButton("AC");
        nonAcButton = new JRadioButton("Non-AC");
        acGroup.add(acButton);
        acGroup.add(nonAcButton);
        

        singleButton = new JRadioButton("Single");
        doubleButton = new JRadioButton("Double");
        bedGroup.add(singleButton);
        bedGroup.add(doubleButton);

        acButton.setFont(new Font("Inter", Font.ITALIC, 20));
        nonAcButton.setFont(new Font("Inter", Font.ITALIC, 20));
        singleButton.setFont(new Font("Inter", Font.ITALIC, 20));
        doubleButton.setFont(new Font("Inter", Font.ITALIC, 20));

        acButton.setBounds(10, 10, 100, 30);
        nonAcButton.setBounds(120, 10, 100, 30);
        singleButton.setBounds(10, 10, 100, 30);
        doubleButton.setBounds(120, 10, 100, 30);
        
        acButton.setSelected(false);
        nonAcButton.setSelected(false);
        singleButton.setSelected(false);
        doubleButton.setSelected(false);

        btnSearchNoKmr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomNumber = inputID.getText();
                fetchRoomData(roomNumber);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomorKamar = inputID.getText(); 
                int ranjang = singleButton.isSelected() ? 1 : 2; 
                int ac = acButton.isSelected() ? 1 : 0; 
                int harga = Integer.parseInt(inputHarga.getText()); 
        
                updateUser(nomorKamar, ranjang, ac, harga);
                resetForm();
            }
        });    
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomorKamar = inputID.getText();
                deleteKamar(nomorKamar);
            }
        });       

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin admin = new Admin();
                admin.setVisible(true);
                dispose();
            }
        });

        inputHarga.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputHarga.getText().equals("Harga")) {
                    inputHarga.setText("");
                } else if (inputHarga.getText().equals("")) {
                    inputHarga.setText("Harga");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        inputID.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputID.getText().equals("Masukkan nomor kamar...")) {
                    inputID.setText("");
                } else if (inputID.getText().equals("")) {
                    inputID.setText("Masukkan nomor kamar...");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        timeLabel.setFont(new Font("Inter", Font.BOLD, 20));
        timeLabel.setBounds(67, 30, 230, 50);

        dateLabel.setFont(new Font("Inter", Font.BOLD, 20));
        dateLabel.setBounds(67, 80, 230, 50);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        updateTime();

        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Nomor", "Ranjang", "AC", "Status", "Harga"});
        dataTable = new JTable(tableModel);
        dataTable.disable();
        dataTable.setRowHeight(30);
        dataTable.setFont(new Font("Inter", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(0, 0, 1120, 650);
        contKamar.add(scrollPane);

        contJam.add(dateLabel);
        contJam.add(timeLabel);
        contDetails.add(labelKmrSearch);
        contDetails.add(inputID);
        contDetails.add(btnSearchNoKmr);
        contButton.add(new JLabel());
        contAC.add(acButton);
        contAC.add(nonAcButton);
        contBed.add(singleButton);
        contBed.add(doubleButton);
        contButton.add(contAC);
        contButton.add(contBed);
        contButton.add(inputHarga);
        contButton.add(new JLabel());
        contButton.add(new JLabel());
        contButton.add(btnDelete);
        contButton.add(btnUpdate);
        contButton.add(btnExit);

        add(contJam);
        add(contDetails);
        add(labelNama);
        add(contButton);
        add(contKamar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WindowSize.width, WindowSize.heigth);
        setUndecorated(true);

        updateTableData();

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
        }

        public void updateTime() {
            String currentTime = timeFormat.format(new Date());
            String currentDate = dateFormat.format(new Date());
            timeLabel.setText(currentTime);
            dateLabel.setText(currentDate);
        }
    
        private void fetchRoomData(String roomNumber) {
            try {
                String query = "SELECT * FROM datakamar WHERE nomorKamar = ?";
                PreparedStatement preparedStatement = con.getConnection().prepareStatement(query);
                preparedStatement.setString(1, roomNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    inputHarga.setText(String.valueOf(resultSet.getInt("harga")));
    
                    int ranjang = resultSet.getInt("ranjang");
                    if (ranjang == 1) {
                        singleButton.setSelected(true);
                    } else if (ranjang == 2) {
                        doubleButton.setSelected(true);
                    }
    
                    int ac = resultSet.getInt("ac");
                    if (ac == 1) {
                        acButton.setSelected(true);
                    } else {
                        nonAcButton.setSelected(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Kamar tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error fetching room data.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    
        private void updateUser(String nomorKamar, int ranjang, int ac, int harga) {
            try {
                String query = "UPDATE datakamar SET ranjang = ?, ac = ?, harga = ? WHERE nomorKamar = ?";
                PreparedStatement preparedStatement = con.getConnection().prepareStatement(query);
                preparedStatement.setInt(1, ranjang);
                preparedStatement.setInt(2, ac);
                preparedStatement.setInt(3, harga);
                preparedStatement.setString(4, nomorKamar);
        
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Data kamar berhasil diperbarui untuk nomor kamar " + nomorKamar);
                    updateStatusLabel.setText("Update Berhasil...");
                    updateStatusLabel.setForeground(Color.GREEN);
                } else {
                    JOptionPane.showMessageDialog(this, "Tidak ada perubahan data untuk nomor kamar " + nomorKamar);
                    updateStatusLabel.setText("Tidak Ada Perubahan Data.");
                    updateStatusLabel.setForeground(Color.ORANGE);
                }
                updateTableData();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating room data: " + e.getMessage());
                updateStatusLabel.setText("Update Gagal.");
                updateStatusLabel.setForeground(Color.RED);
                e.printStackTrace();
            }
            resetForm();
        }

        private void updateTableData() {
            tableModel.setRowCount(0);
        
            String query = "SELECT * FROM datakamar";
            try {
                dataFromDB = statement.executeQuery(query);
                while (dataFromDB.next()) {
                    tableModel.addRow(new Object[]{
                        dataFromDB.getString("nomorKamar"),
                        dataFromDB.getInt("ranjang") == 2 ? "Double" : "Single",
                        dataFromDB.getInt("ac") == 1 ? "AC" : "Non-AC",
                        dataFromDB.getString("status"),
                        dataFromDB.getInt("harga")
                    });
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating table data: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        private void deleteKamar(String nomorKamar) {
            try {
                String getStatusQuery = "SELECT status FROM datakamar WHERE nomorKamar = ?";
                PreparedStatement getStatusStatement = con.getConnection().prepareStatement(getStatusQuery);
                getStatusStatement.setString(1, nomorKamar);
                ResultSet statusResult = getStatusStatement.executeQuery();
                
                if (statusResult.next()) {
                    String status = statusResult.getString("status");
                    
                    if ("available".equalsIgnoreCase(status)) {
                        String deleteQuery = "DELETE FROM datakamar WHERE nomorKamar = ?";
                        PreparedStatement preparedStatement = con.getConnection().prepareStatement(deleteQuery);
                        preparedStatement.setString(1, nomorKamar);
                
                        int rowsDeleted = preparedStatement.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(this, "Data kamar dengan nomor " + nomorKamar + " berhasil dihapus.");
                            updateStatusLabel.setText("Data berhasil dihapus.");
                            updateStatusLabel.setForeground(Color.GREEN);
                        } else {
                            JOptionPane.showMessageDialog(this, "Tidak ada data kamar dengan nomor " + nomorKamar + ".");
                            updateStatusLabel.setText("Data tidak ditemukan.");
                            updateStatusLabel.setForeground(Color.ORANGE);
                        }
                        updateTableData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Kamar dengan nomor " + nomorKamar + " tidak dapat dihapus karena kamar tidak tersedia.");
                        updateStatusLabel.setText("Kamar tidak dapat dihapus.");
                        updateStatusLabel.setForeground(Color.RED);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Kamar dengan nomor " + nomorKamar + " tidak ditemukan.");
                    updateStatusLabel.setText("Data tidak ditemukan.");
                    updateStatusLabel.setForeground(Color.ORANGE);
                }
        
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting room data: " + ex.getMessage());
                updateStatusLabel.setText("Gagal menghapus data.");
                updateStatusLabel.setForeground(Color.RED);
                ex.printStackTrace();
            }
            resetForm();
        }
        
        
        
        public static void main(String[] args) {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
    
            SwingUtilities.invokeLater(() -> {
                new EditKamar();
            });
        }
    }
    