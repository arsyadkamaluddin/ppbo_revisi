import javax.swing.*;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import config.DbConnect;
import net.proteanit.sql.DbUtils;

public class InvoiceBayar extends JFrame {
    private JButton btnPrintPDF;
    private JButton btnBack;
    private JLabel lblDate;
    private JLabel lblCustomerID;
    private JLabel lblBillTo;
    private JLabel lblNamaPemesan;
    private JLabel lblAlamat;
    private JLabel lblTelepon;
    private JLabel lblSubTotal;
    private JLabel lblTax;
    private JLabel lblTotal;
    private JLabel lblAdvancePayment;
    private JScrollPane scrollPaneTable;
    private JTable tableInvoice;

    private ResultSet rs = null;
    private Statement statement = null;
    DbConnect con = null;
    private String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    private int nomorKamar;
    private int harga;
    private Date masuk;
    private Date keluar;
    private String namaPemesan;
    private String nik;
    private String telepon;
    private long jumlahmalam;
    private long totalharga;
    private int ranjang;
    private int ac;

    public InvoiceBayar(int nomorKamar, int harga, Date masuk, Date keluar, String namaPemesan, String nik, String telepon) {
        this.nomorKamar = nomorKamar;
        this.harga = harga;
        this.masuk = masuk;
        this.keluar = keluar;
        this.namaPemesan = namaPemesan;
        this.nik = nik;
        this.telepon = telepon;

        initComponents();
        initializeData();
    }

    private void initializeData() {
        con = new DbConnect();
        try {
            statement = con.getConnection().createStatement();
            lblDate.setText(date);
            lblCustomerID.setText(String.valueOf(nomorKamar));

            // Query untuk mendapatkan informasi customer
            String q1 = "SELECT userId FROM datauser WHERE username='"+nik+"' ORDER BY userId DESC LIMIT 1";
            rs = statement.executeQuery(q1);
            if (rs.next()) {
                System.out.println(rs.getString(1));
                lblNamaPemesan.setText(namaPemesan); // Nama
                lblTelepon.setText(telepon); // Telepon
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        btnPrintPDF = new JButton();
        btnBack = new JButton();
        lblDate = new JLabel();
        lblCustomerID = new JLabel();
        lblBillTo = new JLabel();
        lblNamaPemesan = new JLabel();
        lblAlamat = new JLabel();
        lblTelepon = new JLabel();
        lblSubTotal = new JLabel();
        lblTax = new JLabel();
        lblTotal = new JLabel();
        lblAdvancePayment = new JLabel();
        scrollPaneTable = new JScrollPane();
        tableInvoice = new JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("INVOICE");
        setAlwaysOnTop(true);

        btnPrintPDF.setText("Print PDF");
        btnPrintPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintPDFActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblBillTo.setText("Bill To:");

        tableInvoice.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                },
                new String [] {
                        "Room no.", "Name", "no. of days", "price/day", "line total"
                }
        ));
        scrollPaneTable.setViewportView(tableInvoice);

        lblAdvancePayment.setText("Advance payment:");

        JPanel container = new JPanel();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(container);

//        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lblNamaPemesan, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                                                        .addComponent(lblAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblTelepon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblBillTo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(254, 254, 254)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblDate)
                                                        .addComponent(lblCustomerID))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                                        .addComponent(lblCustomerID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(btnPrintPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblDate)
                                        .addComponent(lblBillTo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCustomerID)
                                        .addComponent(lblNamaPemesan))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAlamat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTelepon)
                                .addGap(18, 18, 18)
                                .addComponent(scrollPaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAdvancePayment)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnPrintPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        add(container);
        setVisible(true);
    }

    private void btnPrintPDFActionPerformed(java.awt.event.ActionEvent evt) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Invoice.pdf"));
            document.open();
            document.addTitle("Invoice");
            document.addAuthor("Your Company Name");

            // Header
            document.add(new com.itextpdf.text.Paragraph("Invoice"));
            document.add(new com.itextpdf.text.Paragraph("Date: " + date));
            document.add(new com.itextpdf.text.Paragraph("Customer ID: " + nomorKamar));

            // Bill to
            document.add(new com.itextpdf.text.Paragraph("Bill To:"));
            document.add(new com.itextpdf.text.Paragraph("Name: " + lblNamaPemesan.getText()));
            document.add(new com.itextpdf.text.Paragraph("Address: " + lblAlamat.getText()));
            document.add(new com.itextpdf.text.Paragraph("Phone: " + lblTelepon.getText()));

            // Invoice table
            document.add(new com.itextpdf.text.Paragraph(" "));
            document.add(new com.itextpdf.text.Paragraph("Room no.    Name    No. of days    Price/day    Line total"));
            for (int i = 0; i < tableInvoice.getRowCount(); i++) {
                String row = "";
                for (int j = 0; j < tableInvoice.getColumnCount(); j++) {
                    row += tableInvoice.getModel().getValueAt(i, j).toString() + "    ";
                }
                document.add(new com.itextpdf.text.Paragraph(row));
            }

            // Summary
            document.add(new com.itextpdf.text.Paragraph(" "));
            document.add(new com.itextpdf.text.Paragraph("Sub Total: " + lblSubTotal.getText()));
            document.add(new com.itextpdf.text.Paragraph("Tax: " + lblTax.getText()));
            document.add(new com.itextpdf.text.Paragraph("Advance Payment: " + lblAdvancePayment.getText()));
            document.add(new com.itextpdf.text.Paragraph("Total: " + lblTotal.getText()));

            document.close();
            JOptionPane.showMessageDialog(null, "PDF created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    public static void main(String[] args) {

        new InvoiceBayar(101, 500000, new Date(), new Date(), "John Doe", "1234567890", "081234567890").setVisible(true);

    }
}
