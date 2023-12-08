package Views;
import Controllers.AdminController;
import Helpers.CsvReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;

//final
public class AddStock extends javax.swing.JFrame {


    AdminController adminController = new AdminController();
    CsvReader csvReader = new CsvReader();
    public AddStock() throws SQLException {
        initComponents();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String FilePath = csvReader.processCSVFile(selectedFile);

            csvReader.readProductLogFile(tblLogs,FilePath);
            lblStatus.setText("Status: Successful");

            progressBar.setValue(100);
        }

        progressBar.setValue(100);
    }
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        this.dispose();
        ManagerDashboard managerDashboard= new ManagerDashboard();
        managerDashboard.setVisible(true);

    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AddStock().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
///////////////////////////////////////////////////////////////////////////////////////////////////


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {


        pnlTittle = new javax.swing.JPanel();
        picProfile = new javax.swing.JLabel();
        txtTittle = new javax.swing.JLabel();
        txtUserName = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnUpload = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLogs = new javax.swing.JTable();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        pnlTittle.setBackground(new java.awt.Color(102, 102, 102));

        picProfile.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/profilepic128.png"))); // NOI18N

        txtTittle.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        txtTittle.setForeground(new java.awt.Color(255, 255, 255));
        txtTittle.setText("Add New Stock");

        txtUserName.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txtUserName.setForeground(new java.awt.Color(255, 255, 255));
        adminController.setUser(txtUserName);

        btnBack.setBackground(new java.awt.Color(102, 102, 102));
        btnBack.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/left-chevron.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnBackActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        javax.swing.GroupLayout pnlTittleLayout = new javax.swing.GroupLayout(pnlTittle);
        pnlTittle.setLayout(pnlTittleLayout);
        pnlTittleLayout.setHorizontalGroup(
                pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTittleLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTittle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 380, Short.MAX_VALUE)
                                .addComponent(txtUserName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(picProfile)
                                .addGap(9, 9, 9))
        );
        pnlTittleLayout.setVerticalGroup(
                pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtTittle)
                                        .addComponent(btnBack))
                                .addGap(22, 22, 22))
                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                .addGroup(pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(txtUserName))
                                        .addComponent(picProfile))
                                .addContainerGap(22, Short.MAX_VALUE))
        );

        btnUpload.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnUpload.setText("Upload File");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        lblStatus.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblStatus.setText("Status :");

        tblLogs.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Logs"
                }
        ));
        jScrollPane1.setViewportView(tblLogs);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1)
                                        .addComponent(pnlTittle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(progressBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlTittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

    }// </editor-fold>


    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnUpload;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel picProfile;
    private javax.swing.JPanel pnlTittle;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTable tblLogs;
    private javax.swing.JLabel txtTittle;
    private javax.swing.JLabel txtUserName;
}
