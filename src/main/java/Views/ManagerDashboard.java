package Views;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import Controllers.AdminController;

import java.awt.*;
import java.sql.SQLException;

//final

/**
 *
 * @author ShaafSalman
 */
public class ManagerDashboard extends javax.swing.JFrame {

 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    AdminController adminController = new AdminController();
    public ManagerDashboard() throws SQLException {
        initComponents();
    }

    private void btnEditUserDetailsActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        this.dispose();
        AdminSettings adminSettings = new AdminSettings();
        adminSettings.setVisible(true);
    }
    private void btnViewProductsActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        this.dispose();
        ManageProducts adminSettings = new ManageProducts();
        adminSettings.setVisible(true);
    }
    private void btnManageExpiredInventoryActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        OrderStock x = new OrderStock();
        x.setVisible(true);

    }
    private void btnAddNewStockActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        AddStock x = new AddStock();
        x.setVisible(true);
    }
    private void btnGenerateInentoryReportActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        this.dispose();
        OrderStock x = new OrderStock();
        x.setVisible(true);
    }
    private void btnManageSalesReportActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        ManageReports x = new ManageReports();
        x.setVisible(true);

    }
    private void btnManageCategoriesActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        this.dispose();
         ManageCategory x = new ManageCategory();
        x.setVisible(true);
    }
    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        this.dispose();
        Login x = new Login();
        x.setVisible(true);
    }
    private void btnAddStockActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        this.dispose();
        AddStock x = new AddStock();
        x.setVisible(true);

    }
    private void btnManageGraphsActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
                this.dispose();
        ManageGraphs x = new ManageGraphs();
        x.setVisible(true);

    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManagerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ManagerDashboard().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        pnlWhiteMain = new javax.swing.JPanel();
        pnlSide = new javax.swing.JPanel();
        picPharmaFast = new javax.swing.JLabel();
        lblPharmaFast = new javax.swing.JLabel();
        btnViewProducts = new javax.swing.JButton();
        pnlUser = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        picUserProfile = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        lblUserRoles = new javax.swing.JLabel();
        btnEditUserDetails = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        btnAddNewStock = new javax.swing.JButton();
        btnManageExpiredInventory = new javax.swing.JButton();
        btnGenerateInentoryReport = new javax.swing.JButton();
        btnManageSalesReport = new javax.swing.JButton();
        btnManageCategories = new javax.swing.JButton();
        lblManagerPortal = new javax.swing.JLabel();
        btnAddStock = new javax.swing.JButton();
        btnManageGraphs = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlWhiteMain.setBackground(java.awt.Color.white);

        pnlSide.setBackground(new java.awt.Color(102, 102, 102));
        pnlSide.setForeground(new java.awt.Color(102, 102, 102));

        picPharmaFast.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/apple-touch-icon.png"))); // NOI18N

        lblPharmaFast.setBackground(new java.awt.Color(0, 0, 0));
        lblPharmaFast.setFont(new java.awt.Font("Century Gothic", 1, 48)); // NOI18N
        lblPharmaFast.setForeground(new java.awt.Color(255, 255, 255));
        lblPharmaFast.setText("PharmaFast ");

        javax.swing.GroupLayout pnlSideLayout = new javax.swing.GroupLayout(pnlSide);
        pnlSide.setLayout(pnlSideLayout);
        pnlSideLayout.setHorizontalGroup(
                pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlSideLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(picPharmaFast)
                                .addGap(222, 222, 222)
                                .addComponent(lblPharmaFast, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(261, Short.MAX_VALUE))
        );
        pnlSideLayout.setVerticalGroup(
                pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlSideLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(picPharmaFast)
                                        .addComponent(lblPharmaFast))
                                .addContainerGap(37, Short.MAX_VALUE))
        );

        btnViewProducts.setBackground(new java.awt.Color(100, 192, 244));
        btnViewProducts.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnViewProducts.setForeground(new java.awt.Color(255, 255, 255));
        btnViewProducts.setText("View Products");
        btnViewProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnViewProductsActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pnlUser.setBackground(new java.awt.Color(195, 195, 195));

        picUserProfile.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/profilepiccus1.png"))); // NOI18N

        lblUserName.setBackground(new java.awt.Color(0, 0, 0));
        lblUserName.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(51, 51, 51));
        adminController.setUser(lblUserName);

        lblUserRoles.setBackground(new java.awt.Color(0, 0, 0));
        lblUserRoles.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblUserRoles.setForeground(new java.awt.Color(51, 51, 51));
        adminController.setRole(lblUserRoles);

        btnEditUserDetails.setBackground(new java.awt.Color(102, 102, 102));
        btnEditUserDetails.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnEditUserDetails.setForeground(new java.awt.Color(255, 255, 255));
        btnEditUserDetails.setText("Admin Settings");
        btnEditUserDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnEditUserDetailsActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnLogOut.setBackground(new java.awt.Color(102, 102, 102));
        btnLogOut.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnLogOut.setForeground(new java.awt.Color(255, 255, 255));
        btnLogOut.setText("Log Out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnLogOutActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        javax.swing.GroupLayout pnlUserLayout = new javax.swing.GroupLayout(pnlUser);
        pnlUser.setLayout(pnlUserLayout);
        pnlUserLayout.setHorizontalGroup(
                pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUserLayout.createSequentialGroup()
                                .addContainerGap(18, Short.MAX_VALUE)
                                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUserLayout.createSequentialGroup()
                                                        .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(lblUserName)
                                                                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel1)
                                                                        .addComponent(picUserProfile)))
                                                        .addGap(25, 25, 25))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUserLayout.createSequentialGroup()
                                                        .addComponent(lblUserRoles)
                                                        .addGap(63, 63, 63))
                                                .addGroup(pnlUserLayout.createSequentialGroup()
                                                        .addComponent(btnEditUserDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addContainerGap()))))
        );
        pnlUserLayout.setVerticalGroup(
                pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlUserLayout.createSequentialGroup()
                                .addComponent(picUserProfile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblUserRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(btnEditUserDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1))
        );

        btnAddNewStock.setBackground(new java.awt.Color(100, 192, 244));
        btnAddNewStock.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnAddNewStock.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNewStock.setText("Add New Stock");
        btnAddNewStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnAddNewStockActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnManageExpiredInventory.setBackground(new java.awt.Color(100, 192, 244));
        btnManageExpiredInventory.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnManageExpiredInventory.setForeground(new java.awt.Color(255, 255, 255));
        btnManageExpiredInventory.setText("Manage Expired Inventory ");
        btnManageExpiredInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnManageExpiredInventoryActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnGenerateInentoryReport.setBackground(new java.awt.Color(100, 192, 244));
        btnGenerateInentoryReport.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnGenerateInentoryReport.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerateInentoryReport.setText("Generate Inventory Report");
        btnGenerateInentoryReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnGenerateInentoryReportActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnManageSalesReport.setBackground(new java.awt.Color(100, 192, 244));
        btnManageSalesReport.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnManageSalesReport.setForeground(new java.awt.Color(255, 255, 255));
        btnManageSalesReport.setText("Generate Sales Report");
        btnManageSalesReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnManageSalesReportActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnManageCategories.setBackground(new java.awt.Color(100, 192, 244));
        btnManageCategories.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnManageCategories.setForeground(new java.awt.Color(255, 255, 255));
        btnManageCategories.setText("Manage Categories");
        btnManageCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnManageCategoriesActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        lblManagerPortal.setBackground(new java.awt.Color(0, 0, 0));
        lblManagerPortal.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        lblManagerPortal.setForeground(new java.awt.Color(102, 102, 102));
        lblManagerPortal.setText("Manager Portal");

        btnAddStock.setBackground(new java.awt.Color(100, 192, 244));
        btnAddStock.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnAddStock.setForeground(new java.awt.Color(255, 255, 255));
        btnAddStock.setText("Add Stock");
        btnAddStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnAddStockActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnManageGraphs.setBackground(new java.awt.Color(100, 192, 244));
        btnManageGraphs.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnManageGraphs.setForeground(new java.awt.Color(255, 255, 255));
        btnManageGraphs.setText("View Graphical Stats");
        btnManageGraphs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnManageGraphsActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        javax.swing.GroupLayout pnlWhiteMainLayout = new javax.swing.GroupLayout(pnlWhiteMain);
        pnlWhiteMain.setLayout(pnlWhiteMainLayout);
        pnlWhiteMainLayout.setHorizontalGroup(
                pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btnManageExpiredInventory, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                                                        .addComponent(btnManageSalesReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnAddStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnManageCategories, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(btnAddNewStock, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(btnViewProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(btnGenerateInentoryReport, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                                                                        .addComponent(btnManageGraphs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addGap(48, 48, 48))))
                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(pnlSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                                .addGap(288, 288, 288)
                                                                .addComponent(lblManagerPortal)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(pnlUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        pnlWhiteMainLayout.setVerticalGroup(
                pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                .addComponent(pnlSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblManagerPortal)
                                                .addGap(33, 33, 33)
                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnManageCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnViewProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(10, 10, 10)
                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnManageExpiredInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnAddNewStock, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnManageSalesReport, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnGenerateInentoryReport, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnManageGraphs, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 33, Short.MAX_VALUE))
                                        .addComponent(pnlUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlWhiteMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlWhiteMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }// </editor-fold>


    // Variables declaration - do not modify
    private javax.swing.JButton btnAddNewStock;
    private javax.swing.JButton btnAddStock;
    private javax.swing.JButton btnEditUserDetails;
    private javax.swing.JButton btnGenerateInentoryReport;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnManageCategories;
    private javax.swing.JButton btnManageExpiredInventory;
    private javax.swing.JButton btnManageSalesReport;
    private javax.swing.JButton btnManageGraphs;
    private javax.swing.JButton btnViewProducts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblManagerPortal;
    private javax.swing.JLabel lblPharmaFast;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserRoles;
    private javax.swing.JLabel picPharmaFast;
    private javax.swing.JLabel picUserProfile;
    private javax.swing.JPanel pnlSide;
    private javax.swing.JPanel pnlUser;
    private javax.swing.JPanel pnlWhiteMain;
    // End of variables declaration
}
