package Views;


import Controllers.AdminController;
import Helpers.UtilityFunctions;

import javax.swing.*;
import java.sql.SQLException;

/**
 *
 * @author ShaafSalman
 */
public class orderStock extends javax.swing.JFrame {

    AdminController adminController = new AdminController();
    UtilityFunctions uFunctions = new UtilityFunctions();

    public orderStock() throws SQLException {

        initComponents();
        adminController.initializeStockTable(tblStock);
        adminController.initializeDeadStockTable(tblDeadStock);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblDeadStock = new javax.swing.JTable();
        btnReStock = new javax.swing.JButton();
        pnlTittle = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        picUser = new javax.swing.JLabel();
        lblTittle = new javax.swing.JLabel();
        lblUsernameHeader = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        lblAlert = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblDeadStock.setBackground(new java.awt.Color(102, 102, 102));
        tblDeadStock.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblDeadStock.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        tblDeadStock.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Product", "Category ", "Stock"
                }
        ));
        jScrollPane2.setViewportView(tblDeadStock);

        btnReStock.setBackground(new java.awt.Color(0, 153, 51));
        btnReStock.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnReStock.setForeground(new java.awt.Color(255, 255, 255));
        btnReStock.setText("reStock");

        pnlTittle.setBackground(new java.awt.Color(102, 102, 102));

        picUser.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/profilepic128.png"))); // NOI18N

        lblTittle.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        lblTittle.setForeground(new java.awt.Color(255, 255, 255));
        lblTittle.setText("Manage Stock");

        lblUsernameHeader.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblUsernameHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblUsernameHeader.setText("Shaaf Salman");

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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 463, Short.MAX_VALUE)
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

        tblStock.setBackground(new java.awt.Color(102, 102, 102));
        tblStock.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblStock.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        tblStock.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Product", "Category ", "Stock"
                }
        ));
        jScrollPane3.setViewportView(tblStock);

        lblAlert.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblAlert.setText("Alerts");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlTittle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(lblAlert, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(270, 270, 270)
                                                .addComponent(btnReStock, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane3))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlTittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblAlert)
                                        .addComponent(btnReStock, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                .addGap(22, 22, 22))
        );
        btnReStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReStockActionPerformed(evt);
            }
        });
        pack();
    }// </editor-fold>

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        this.dispose();
        ManagerDashboard managerDashboard= new ManagerDashboard();
        managerDashboard.setVisible(true);
    }

    private void btnReStockActionPerformed(java.awt.event.ActionEvent evt) {
        JTable selectedTable = null;

        if (tblStock.getSelectedRow() != -1) {
            selectedTable = tblStock;
        } else if (tblDeadStock.getSelectedRow() != -1) {
            selectedTable = tblDeadStock;
        } else
        {
            return;
        }
        int[] selectedRows = selectedTable.getSelectedRows();

        for (int selectedRow : selectedRows) {
            String productName = selectedTable.getValueAt(selectedRow, 0).toString();
            String category = selectedTable.getValueAt(selectedRow, 1).toString();
            int currentQuantity = Integer.parseInt(selectedTable.getValueAt(selectedRow, 2).toString());

            String quantityInput = JOptionPane.showInputDialog("Enter the quantity to order for " + productName + " in " + category + ":");

            if (quantityInput != null && !quantityInput.isEmpty()) {
                try {
                    int newQuantity = Integer.parseInt(quantityInput);
                    if(UtilityFunctions.placeRestockOrder(productName, category, currentQuantity, newQuantity))
                    {
                        JOptionPane.showMessageDialog(this,"Order Placed Successfully");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new orderStock().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnReStock;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAlert;
    private javax.swing.JLabel lblTittle;
    private javax.swing.JLabel lblUsernameHeader;
    private javax.swing.JLabel picUser;
    private javax.swing.JPanel pnlTittle;
    private javax.swing.JTable tblDeadStock;
    private javax.swing.JTable tblStock;
}

