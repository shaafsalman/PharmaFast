package Views;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import Controllers.AdminController;
import Helpers.UtilityFunctions;
import Models.Product;

import javax.swing.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ShaafSalman
 */
public class manageProducts extends javax.swing.JFrame {

    /**
     * Creates new form manageProducts
     */

    AdminController adController = new AdminController();
    UtilityFunctions uf = new UtilityFunctions();

    public manageProducts() throws SQLException 
    {
        initComponents();
        adController.initializeProductsTable(tblProducts);

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        pnlTittle = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        picUser = new javax.swing.JLabel();
        lblTittle = new javax.swing.JLabel();
        lblUsernameHeader = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAdd.setBackground(new java.awt.Color(0, 153, 51));
        btnAdd.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");

        btnDelete.setBackground(new java.awt.Color(204, 0, 0));
        btnDelete.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");

        btnModify.setBackground(new java.awt.Color(0, 153, 204));
        btnModify.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnModify.setForeground(new java.awt.Color(255, 255, 255));
        btnModify.setText("Modify");

        pnlTittle.setBackground(new java.awt.Color(102, 102, 102));

        picUser.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/profilepic128.png"))); // NOI18N

        lblTittle.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        lblTittle.setForeground(new java.awt.Color(255, 255, 255));
        lblTittle.setText("Manage Products");

        lblUsernameHeader.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblUsernameHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblUsernameHeader.setText("Shaaf Salman");

        btnBack.setBackground(new java.awt.Color(102, 102, 102));
        btnBack.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/left-chevron.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
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

        tblProducts.setBackground(new java.awt.Color(102, 102, 102));
        tblProducts.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblProducts.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {"Product ID", "Category", "Product", "Cost Price", "Selling Price", "Quantity", "Expiry Date"}
        ) {
            Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblProducts.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblProducts);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlTittle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
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
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                        .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                        .addComponent(btnModify, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        pack();
    }// </editor-fold>



    JComboBox<String> yearComboBox = uf.createExpiryYearComboBox();
    JComboBox<String> monthComboBox = uf.createMonthComboBox();
    JComboBox<String> dayComboBox = uf.createDayComboBox();
    //////////////////////////////////////////////////////////////////////////////////////////////
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblProducts.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to modify.");
            return;
        }

        int productID = 0;

        productID = (int) tblProducts.getValueAt(selectedRow, 0);
        String productName = (String) tblProducts.getValueAt(selectedRow, 2);
        double costPrice = (double) tblProducts.getValueAt(selectedRow, 3);
        double sellingPrice = (double) tblProducts.getValueAt(selectedRow, 4);
        int quantity = (int) tblProducts.getValueAt(selectedRow, 5);
        String category = (String) tblProducts.getValueAt(selectedRow, 1);
        int categoryID = adController.getCategoryIDByName(category);
        java.sql.Date expiryDate = (java.sql.Date) tblProducts.getValueAt(selectedRow, 6);



        // Prompt the user to update values or skip
        String inputProductName = JOptionPane.showInputDialog(null, "Enter Product Name (Skip to keep existing):");
        double inputCostPrice;
        double inputSellingPrice;
        int inputQuantity;

        // Check if the user skipped the input for each value
        if (inputProductName != null && !inputProductName.isEmpty()) {
            productName = inputProductName;
        }

        String inputCost = JOptionPane.showInputDialog(null, "Enter Cost Price (Skip to keep existing):");
        if (inputCost != null && !inputCost.isEmpty()) {
            inputCostPrice = Double.parseDouble(inputCost);
            costPrice = inputCostPrice;
        }

        String inputSelling = JOptionPane.showInputDialog(null, "Enter Selling Price (Skip to keep existing):");
        if (inputSelling != null && !inputSelling.isEmpty()) {
            inputSellingPrice = Double.parseDouble(inputSelling);
            sellingPrice = inputSellingPrice;
        }

        String inputQuantityStr = JOptionPane.showInputDialog(null, "Enter Quantity (Skip to keep existing):");
        if (inputQuantityStr != null && !inputQuantityStr.isEmpty()) {
            inputQuantity = Integer.parseInt(inputQuantityStr);
            quantity = inputQuantity;
        }

        // Create a Product object with the updated values
        Product updatedProduct = new Product(productID, productName, costPrice, sellingPrice, quantity,categoryID,expiryDate);

        // Pass the Product object to a function that updates the database
        boolean updateResult = adController.updateProduct(updatedProduct);

        if (updateResult) {
            JOptionPane.showMessageDialog(this, "Product " + productName + " (ID: " + productID + ") details updated successfully.");
            // Refresh the products table if update successful
            adController.initializeProductsTable(tblProducts);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update product details for " + productName + " (ID: " + productID + ")");
        }
    }
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        String productName = JOptionPane.showInputDialog(null, "Enter Product Name:");
        double costPrice = 0.0;
        double sellingPrice = 0.0;
        int quantity = 0;

        Map<Integer, String> categoryData = new HashMap<>();
        boolean success = adController.getCategoryData(categoryData);

        if (!success) {
            JOptionPane.showMessageDialog(null, "Failed to retrieve category data. Please try again later.");
            return;
        }

        JComboBox<String> categoryComboBox = new JComboBox<>(categoryData.values().toArray(new String[0]));
        int result = JOptionPane.showConfirmDialog(null, categoryComboBox, "Select a Category", JOptionPane.OK_CANCEL_OPTION);

        int categoryID = -1;
        if (result == JOptionPane.OK_OPTION && categoryComboBox.getSelectedIndex() != -1) {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();

            for (Map.Entry<Integer, String> entry : categoryData.entrySet()) {
                if (entry.getValue().equals(selectedCategory)) {
                    categoryID = entry.getKey();
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Category not selected. Please select a category.");
            return;
        }

        String inputCost = JOptionPane.showInputDialog(null, "Enter Cost Price (Skip to set default as 0.0):");
        if (inputCost != null && !inputCost.isEmpty()) {
            costPrice = Double.parseDouble(inputCost);
        }

        String inputSelling = JOptionPane.showInputDialog(null, "Enter Selling Price (Skip to set default as 0.0):");
        if (inputSelling != null && !inputSelling.isEmpty()) {
            sellingPrice = Double.parseDouble(inputSelling);
        }

        String inputQuantityStr = JOptionPane.showInputDialog(null, "Enter Quantity (Skip to set default as 0):");
        if (inputQuantityStr != null && !inputQuantityStr.isEmpty()) {
            quantity = Integer.parseInt(inputQuantityStr);
        }

        JComboBox<String> yearComboBox = uf.createExpiryYearComboBox();
        JComboBox<String> monthComboBox = uf.createMonthComboBox();
        JComboBox<String> dayComboBox = uf.createDayComboBox();

        JPanel expiryPanel = new JPanel();
        expiryPanel.add(yearComboBox);
        expiryPanel.add(monthComboBox);
        expiryPanel.add(dayComboBox);

        int expiryResult;
        boolean validDate = false;
        String selectedExpiryDate = null;

        while (!validDate) {
            expiryResult = JOptionPane.showConfirmDialog(null, expiryPanel, "Select Expiry Date", JOptionPane.OK_CANCEL_OPTION);

            if (expiryResult == JOptionPane.OK_OPTION) {
                String selectedYear = (String) yearComboBox.getSelectedItem();
                String selectedMonth = (String) monthComboBox.getSelectedItem();
                String selectedDay = (String) dayComboBox.getSelectedItem();

                validDate = uf.isValidDate(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth), Integer.parseInt(selectedDay));

                if (!validDate) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid date.");
                } else {
                    selectedExpiryDate = selectedYear + "-" + selectedMonth + "-" + selectedDay;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Expiry date not selected. Please select a date.");
                return;
            }
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(selectedExpiryDate);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            Product newProduct = new Product(0, productName, costPrice, sellingPrice, quantity,categoryID,sqlDate);

            boolean addResult = adController.addProduct(newProduct);

            if (addResult) {
                JOptionPane.showMessageDialog(this, "Product " + productName + " added successfully.");
                adController.initializeProductsTable(tblProducts);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product " + productName);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error parsing the selected date.");
        }
    }
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblProducts.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to Delete.");
            return;
        }

        int productID = 0;
        String name =(String) tblProducts.getValueAt(selectedRow, 2);
        productID =(int) tblProducts.getValueAt(selectedRow, 0);

        if (adController.deleteProduct(productID))
        {
            JOptionPane.showMessageDialog(this, "Product" + name + ": " +productID);
            adController.initializeProductsTable(tblProducts);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Failed to Delete " + name +" " +productID);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////



    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(manageProducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manageProducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manageProducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manageProducts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new manageProducts().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTittle;
    private javax.swing.JLabel lblUsernameHeader;
    private javax.swing.JLabel picUser;
    private javax.swing.JPanel pnlTittle;
    private javax.swing.JTable tblProducts;
    // End of variables declaration
}
