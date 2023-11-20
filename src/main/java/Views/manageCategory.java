package Views;

import Controllers.AdminController;
import Models.Category;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author ShaafSalman
 */
public class manageCategory extends javax.swing.JFrame {

    AdminController adController = new AdminController();

    public manageCategory() throws SQLException
    {

        initComponents();
        adController.initializeCategoryTable(tblCategory);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        pnlTittle = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        picUser = new javax.swing.JLabel();
        lblTittle = new javax.swing.JLabel();
        lblUsernameHeader = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategory = new javax.swing.JTable();
        btnModify = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlTittle.setBackground(new java.awt.Color(102, 102, 102));

        picUser.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/profilepic128.png"))); // NOI18N

        lblTittle.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        lblTittle.setForeground(new java.awt.Color(255, 255, 255));
        lblTittle.setText("Manage Categories");

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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 353, Short.MAX_VALUE)
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

        tblCategory.setBackground(new java.awt.Color(102, 102, 102));
        tblCategory.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblCategory.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                        "Category ID", "Category Name", "Products"
                }
        ));
        jScrollPane2.setViewportView(tblCategory);

        btnModify.setBackground(new java.awt.Color(0, 153, 204));
        btnModify.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnModify.setForeground(new java.awt.Color(255, 255, 255));
        btnModify.setText("Modify");
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

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

        pack();
    }// </editor-fold>


    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        String categoryName = JOptionPane.showInputDialog(this, "Enter Category Name:");

        if (categoryName != null && !categoryName.isEmpty()) {
            Category newCategory = new Category(0, categoryName);
            boolean categoryAdded = adController.addCategory(newCategory);

            if (categoryAdded) {
                JOptionPane.showMessageDialog(this, "Category added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error adding the category. Please try again.");
            }

            adController.initializeCategoryTable(tblCategory);
        }
    }
    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblCategory.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to modify.");
            return;
        }

        int categoryID = 0;
        categoryID = (int) tblCategory.getValueAt(selectedRow, 0);
        String currentName = (String) tblCategory.getValueAt(selectedRow, 1);

        String newName = JOptionPane.showInputDialog(this, "Enter a new name for the category:", currentName);

        if (newName != null && !newName.isEmpty()) {
            Category updatedCategory = new Category(categoryID, newName);
            boolean updated = adController.editCategory(updatedCategory);

            if (updated)
            {
                adController.initializeCategoryTable(tblCategory);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Failed to update the category name.");
            }
        }
    }
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblCategory.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category to delete.");
            return;
        }

        int categoryID = 0;
        String name = (String) tblCategory.getValueAt(selectedRow, 1);
        categoryID = (int) tblCategory.getValueAt(selectedRow, 0);

        if (adController.deleteCategory(categoryID)) {
            adController.initializeCategoryTable(tblCategory);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete " + name + " " + categoryID);
        }
    }



    public static void main(String args[]) {


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new manageCategory().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

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
    private javax.swing.JTable tblCategory;
}
