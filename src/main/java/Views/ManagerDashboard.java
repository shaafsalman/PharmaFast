package Views;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import Controllers.AdminController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;


/**
 *
 * @author ShaafSalman
 */
public class ManagerDashboard extends javax.swing.JFrame {


AdminController adController = new AdminController();
    /**
     * Creates new form Controllers.ManagerHub
     */
    public ManagerDashboard() throws SQLException {
        initComponents();
    }

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
        lblUserID = new javax.swing.JLabel();
        btnAddNewStock = new javax.swing.JButton();
        btnManageExpiredInventory = new javax.swing.JButton();
        btnGenerateInentoryReport = new javax.swing.JButton();
        btnManageSalesReport = new javax.swing.JButton();
        btnManageCategories = new javax.swing.JButton();
        lblManagerPortal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlWhiteMain.setBackground(java.awt.Color.white);

        pnlSide.setBackground(new java.awt.Color(102, 102, 102));
        pnlSide.setForeground(new java.awt.Color(102, 102, 102));

        picPharmaFast.setIcon(new javax.swing.ImageIcon(("src\\Material\\apple-touch-icon.png"))); // NOI18N

        lblPharmaFast.setBackground(new java.awt.Color(0, 0, 0));
        lblPharmaFast.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        lblPharmaFast.setForeground(new java.awt.Color(255, 255, 255));
        lblPharmaFast.setText("PharmaFast ");

        javax.swing.GroupLayout pnlSideLayout = new javax.swing.GroupLayout(pnlSide);
        pnlSide.setLayout(pnlSideLayout);
        pnlSideLayout.setHorizontalGroup(
                pnlSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlSideLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(picPharmaFast)
                                .addGap(18, 18, 18)
                                .addComponent(lblPharmaFast, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(377, Short.MAX_VALUE))
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
        btnViewProducts.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnViewProducts.setForeground(new java.awt.Color(255, 255, 255));
        btnViewProducts.setText("View Products");
        btnViewProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewProductsActionPerformed(evt);
            }
        });

        pnlUser.setBackground(new java.awt.Color(195, 195, 195));

        picUserProfile.setIcon(new javax.swing.ImageIcon(("src\\Material\\profilepiccus1.png"))); // NOI18N

        lblUserName.setBackground(new java.awt.Color(0, 0, 0));
        lblUserName.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(51, 51, 51));
        lblUserName.setText("Shaaf Salman");

        lblUserRoles.setBackground(new java.awt.Color(0, 0, 0));
        lblUserRoles.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblUserRoles.setForeground(new java.awt.Color(51, 51, 51));
        lblUserRoles.setText("Manager");

        btnEditUserDetails.setBackground(new java.awt.Color(102, 102, 102));
        btnEditUserDetails.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnEditUserDetails.setForeground(new java.awt.Color(255, 255, 255));
        btnEditUserDetails.setText("Edit Details");
        btnEditUserDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUserDetailsActionPerformed(evt);
            }
        });

        btnLogOut.setBackground(new java.awt.Color(102, 102, 102));
        btnLogOut.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnLogOut.setForeground(new java.awt.Color(255, 255, 255));
        btnLogOut.setText("Log Out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        lblUserID.setBackground(new java.awt.Color(0, 0, 0));
        lblUserID.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblUserID.setForeground(new java.awt.Color(51, 51, 51));
        lblUserID.setText("1");

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
                                                        .addComponent(lblUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblUserRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addComponent(btnEditUserDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1))
        );

        btnAddNewStock.setBackground(new java.awt.Color(100, 192, 244));
        btnAddNewStock.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnAddNewStock.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNewStock.setText("Add New Stock");
        btnAddNewStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewStockActionPerformed(evt);
            }
        });

        btnManageExpiredInventory.setBackground(new java.awt.Color(100, 192, 244));
        btnManageExpiredInventory.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnManageExpiredInventory.setForeground(new java.awt.Color(255, 255, 255));
        btnManageExpiredInventory.setText("Manage Expired Inventory ");
        btnManageExpiredInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageExpiredInventoryActionPerformed(evt);
            }
        });

        btnGenerateInentoryReport.setBackground(new java.awt.Color(100, 192, 244));
        btnGenerateInentoryReport.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnGenerateInentoryReport.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerateInentoryReport.setText("Generate Inventory Report");
        btnGenerateInentoryReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateInentoryReportActionPerformed(evt);
            }
        });

        btnManageSalesReport.setBackground(new java.awt.Color(100, 192, 244));
        btnManageSalesReport.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnManageSalesReport.setForeground(new java.awt.Color(255, 255, 255));
        btnManageSalesReport.setText("Generate Sales Report");
        btnManageSalesReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageSalesReportActionPerformed(evt);
            }
        });

        btnManageCategories.setBackground(new java.awt.Color(100, 192, 244));
        btnManageCategories.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnManageCategories.setForeground(new java.awt.Color(255, 255, 255));
        btnManageCategories.setText("Manage Categories");
        btnManageCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageCategoriesActionPerformed(evt);
            }
        });

        lblManagerPortal.setBackground(new java.awt.Color(0, 0, 0));
        lblManagerPortal.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        lblManagerPortal.setForeground(new java.awt.Color(102, 102, 102));
        lblManagerPortal.setText("Manager Portal");

        javax.swing.GroupLayout pnlWhiteMainLayout = new javax.swing.GroupLayout(pnlWhiteMain);
        pnlWhiteMain.setLayout(pnlWhiteMainLayout);
        pnlWhiteMainLayout.setHorizontalGroup(
                pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(btnManageExpiredInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(btnManageSalesReport, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(btnManageCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(pnlWhiteMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(btnAddNewStock, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(btnViewProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(btnGenerateInentoryReport, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(pnlSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(pnlWhiteMainLayout.createSequentialGroup()
                                                .addGap(208, 208, 208)
                                                .addComponent(lblManagerPortal)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
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
                                                .addGap(50, 50, 50))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlWhiteMainLayout.createSequentialGroup()
                                                .addComponent(pnlUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlWhiteMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlWhiteMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>



    private void btnEditUserDetailsActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnViewProductsActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnManageExpiredInventoryActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnAddNewStockActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnGenerateInentoryReportActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnManageSalesReportActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnManageCategoriesActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

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

    // Variables declaration - do not modify
    private javax.swing.JButton btnAddNewStock;
    private javax.swing.JButton btnEditUserDetails;
    private javax.swing.JButton btnGenerateInentoryReport;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnManageCategories;
    private javax.swing.JButton btnManageExpiredInventory;
    private javax.swing.JButton btnManageSalesReport;
    private javax.swing.JButton btnViewProducts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblManagerPortal;
    private javax.swing.JLabel lblPharmaFast;
    private javax.swing.JLabel lblUserID;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserRoles;
    private javax.swing.JLabel picPharmaFast;
    private javax.swing.JLabel picUserProfile;
    private javax.swing.JPanel pnlSide;
    private javax.swing.JPanel pnlUser;
    private javax.swing.JPanel pnlWhiteMain;
    // End of variables declaration
}
