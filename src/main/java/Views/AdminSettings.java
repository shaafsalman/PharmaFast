package Views;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import Controllers.AdminController;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 *
 * @author ShaafSalman
 */

//final
public class AdminSettings extends javax.swing.JFrame {

    AdminController adminController = new AdminController();
    /**
     * Creates new form AdminSettings
     */
    public AdminSettings() throws SQLException {
        initComponents();
        adminController.initializeUsersTable(tblUsers);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

///////////////////////////////////////////////////////////////////////////////////////////////////
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        adminController.showAddUserDialog();
        adminController.initializeUsersTable(tblUsers);
    }
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblUsers.getSelectedRow();

        if (selectedRow != -1) {
            adminController.showDeleteUserDialog(this,tblUsers,selectedRow);
            adminController.initializeUsersTable(tblUsers);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to modify.");
        }
    }
    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblUsers.getSelectedRow();

        if (selectedRow != -1) {
            int userId = (int) tblUsers.getValueAt(selectedRow, 0);

            adminController.showModifyUserDialog(userId);
            adminController.initializeUsersTable(tblUsers);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to modify.");
        }
    }
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        this.dispose();
        ManagerDashboard managerDashboard= new ManagerDashboard();
        managerDashboard.setVisible(true);

    }
    private void btnEditAdminCodeActionPerformed(java.awt.event.ActionEvent evt) {
        String newAdminCodeStr = JOptionPane.showInputDialog(this, "Enter new Admin Code:");

        if (newAdminCodeStr != null && !newAdminCodeStr.isEmpty()) {
            int newAdminCode = Integer.parseInt(newAdminCodeStr);
            AdminController.setAdminCode(newAdminCode);
            lblAdminCode.setText("AdminCode: " + newAdminCode);
        }
    }
    private void btnEditVatActionPerformed(java.awt.event.ActionEvent evt) {
        String newVatRateStr = JOptionPane.showInputDialog(this, "Enter new VAT Rate:");

        if (newVatRateStr != null && !newVatRateStr.isEmpty()) {
            float newVatRate = Float.parseFloat(newVatRateStr);
            AdminController.setVatRate(newVatRate);
            lblVat.setText("VAT: " + newVatRate + "%");
        }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AdminSettings().setVisible(true);
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




        jScrollPane2 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        pnlTittle = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        picUser = new javax.swing.JLabel();
        lblTittle = new javax.swing.JLabel();
        lblUsernameHeader = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        lblAdminCode = new javax.swing.JLabel();
        btnEditAdminCode = new javax.swing.JToggleButton();
        lblVat = new javax.swing.JLabel();
        btnEditVat = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Auto maximize the frame

        tblUsers.setBackground(new java.awt.Color(102, 102, 102));
        tblUsers.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblUsers.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "User ID", "UserName", "Email", "Role"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblUsers.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblUsers);
        if (tblUsers.getColumnModel().getColumnCount() > 0) {
            tblUsers.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        btnAdd.setBackground(new java.awt.Color(0, 153, 51));
        btnAdd.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 0, 0));
        btnDelete.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnModify.setBackground(new java.awt.Color(0, 153, 204));
        btnModify.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnModify.setForeground(new java.awt.Color(255, 255, 255));
        btnModify.setText("Modify");
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        pnlTittle.setBackground(new java.awt.Color(102, 102, 102));

        picUser.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/profilepic128.png"))); // NOI18N

        lblTittle.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        lblTittle.setForeground(new java.awt.Color(255, 255, 255));
        lblTittle.setText("Admin Settings");

        lblUsernameHeader.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblUsernameHeader.setForeground(new java.awt.Color(255, 255, 255));
        adminController.setUser(lblUsernameHeader);

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
                                .addGap(18, 18, 18)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTittle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 465, Short.MAX_VALUE)
                                .addGroup(pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(131, 131, 131))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTittleLayout.createSequentialGroup()
                                                .addComponent(lblUsernameHeader)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addComponent(picUser)
                                .addGap(9, 9, 9))
        );
        pnlTittleLayout.setVerticalGroup(
                pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(lblTittle)
                                                                .addComponent(lblUsernameHeader))
                                                        .addComponent(btnBack)))
                                        .addComponent(picUser))
                                .addContainerGap(26, Short.MAX_VALUE))
        );

        lblAdminCode.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblAdminCode.setText("AdminCode: " + AdminController.getAdminCode());

        btnEditAdminCode.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btnEditAdminCode.setText("Edit");
        btnEditAdminCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditAdminCodeActionPerformed(evt);
            }
        });

        lblVat.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblVat.setText("VAT: " + adminController.getVatRate() + "%");

        btnEditVat.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btnEditVat.setText("Edit");
        btnEditVat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditVatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlTittle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(39, 39, 39)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblAdminCode)
                                                        .addComponent(lblVat))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnEditAdminCode)
                                                        .addComponent(btnEditVat))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(7, 7, 7)
                                                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlTittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(87, 87, 87)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                                        .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                                        .addComponent(btnModify, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblAdminCode)
                                                        .addComponent(btnEditAdminCode))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblVat)
                                                        .addComponent(btnEditVat))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

    }// </editor-fold>


    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JToggleButton btnEditAdminCode;
    private javax.swing.JToggleButton btnEditVat;
    private javax.swing.JButton btnModify;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAdminCode;
    private javax.swing.JLabel lblTittle;
    private javax.swing.JLabel lblUsernameHeader;
    private javax.swing.JLabel lblVat;
    private javax.swing.JLabel picUser;
    private javax.swing.JPanel pnlTittle;
    private javax.swing.JTable tblUsers;
    // End of variables declaration
}
