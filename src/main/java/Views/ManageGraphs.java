package Views;


import Controllers.AdminController;
import Helpers.GraphGenerator;
import Helpers.UtilityFunctions;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
//final

/**
 *
 * @author ShaafSalman
 */
public class ManageGraphs extends javax.swing.JFrame {


///////////////////////////////////////////////////////////////////////////////////////////////////

    UtilityFunctions uf = new UtilityFunctions();
    JComboBox<String> yearComboBox = uf.createYearComboBox();
    JComboBox<String> monthComboBox = uf.createMonthComboBox();
    JComboBox<String> dayComboBox = uf.createDayComboBox();
    AdminController adminController = new AdminController();


    public ManageGraphs() throws SQLException {
        initComponents();
    }
    private void btnBack1ActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        this.dispose();
        ManagerDashboard managerDashboard= new ManagerDashboard();
        managerDashboard.setVisible(true);

    }
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        // TODO add your handling code here:
        this.dispose();
        ManagerDashboard managerDashboard= new ManagerDashboard();
        managerDashboard.setVisible(true);

    }

    private void btnMonthlyGraphActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedYear = (String) yearComboBox.getSelectedItem();
        String selectedMonth = (String) monthComboBox.getSelectedItem();

        if (selectedYear != null && selectedMonth != null) {
            generateAndDisplayGraph("monthly", selectedYear + "-" + selectedMonth);
        } else {
            showErrorMessage("Please select a year and a month.");
        }
    }
    private void btnYearlyGraphActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedYear = (String) yearComboBox.getSelectedItem();

        if (selectedYear != null) {
            generateAndDisplayGraph("yearly", selectedYear);
        } else {
            showErrorMessage("Please select a year.");
        }
    }
    private void btnDailyGraphActionPerformed(java.awt.event.ActionEvent evt) {
        if (areComboBoxesPopulated(yearComboBox, monthComboBox, dayComboBox)) {
            String selectedYear = (String) yearComboBox.getSelectedItem();
            String selectedMonth = (String) monthComboBox.getSelectedItem();
            String selectedDay = (String) dayComboBox.getSelectedItem();

            if (selectedYear != null && selectedMonth != null && selectedDay != null) {
                generateAndDisplayGraph("daily", selectedYear + "-" + selectedMonth + "-" + selectedDay);
            } else {
                showErrorMessage("Please select a valid year, month, and day.");
            }
        } else {
            showErrorMessage("Please ensure the combo boxes are populated.");
        }
    }
    private void generateAndDisplayGraph(String reportType, String date) {
        Object[] message = {
                "Select a date:",
                "Year:", yearComboBox,
                "Month:", monthComboBox,
                "Day:", dayComboBox
        };

        int option = JOptionPane.showConfirmDialog(null, message, getDialogTitle(reportType), JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String filePath = GraphGenerator.generateGraph(reportType, date);

            if (!filePath.isEmpty()) {
                uf.displayGraph(filePath);
            } else {
                showErrorMessage("Graph generation failed.");
            }
        }
    }
    private String getDialogTitle(String reportType) {
        switch (reportType) {
            case "monthly":
                return "Monthly Graph";
            case "yearly":
                return "Yearly Graph";
            case "daily":
                return "Daily Graph";
            default:
                throw new IllegalArgumentException("Invalid report type: " + reportType);
        }
    }
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    private boolean areComboBoxesPopulated(JComboBox<?>... comboBoxes) {
        for (JComboBox<?> comboBox : comboBoxes) {
            if (comboBox.getItemCount() == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ManageGraphs().setVisible(true);
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
        tblCategory = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        pnlTittle = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        picUser = new javax.swing.JLabel();
        lblTittle = new javax.swing.JLabel();
        lblUsernameHeader = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnDailyGraph = new javax.swing.JButton();
        pnlTittle1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        picUser1 = new javax.swing.JLabel();
        lblTittle1 = new javax.swing.JLabel();
        lblUsernameHeader1 = new javax.swing.JLabel();
        btnBack1 = new javax.swing.JButton();
        btnMonthlyGraph = new javax.swing.JButton();
        btnAnumGraph = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        tblCategory.setBackground(new java.awt.Color(102, 102, 102));
        tblCategory.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblCategory.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Category ID", "Category Name", "Products"
                }
        ));
        jScrollPane2.setViewportView(tblCategory);

        btnDelete.setBackground(new java.awt.Color(204, 0, 0));
        btnDelete.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");

        btnModify.setBackground(new java.awt.Color(0, 153, 204));
        btnModify.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnModify.setForeground(new java.awt.Color(255, 255, 255));
        btnModify.setText("Modify");

        btnAdd.setBackground(new java.awt.Color(0, 153, 51));
        btnAdd.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");

        pnlTittle.setBackground(new java.awt.Color(102, 102, 102));

        picUser.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/profilepic128.png"))); // NOI18N

        lblTittle.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        lblTittle.setForeground(new java.awt.Color(255, 255, 255));
        lblTittle.setText("Manage Categories");

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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 370, Short.MAX_VALUE)
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnDailyGraph.setBackground(new java.awt.Color(0, 153, 204));
        btnDailyGraph.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnDailyGraph.setForeground(new java.awt.Color(255, 255, 255));
        btnDailyGraph.setText("Generate Daily Graph");
        btnDailyGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDailyGraphActionPerformed(evt);
            }
        });

        pnlTittle1.setBackground(new java.awt.Color(102, 102, 102));

        picUser1.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/profilepic128.png"))); // NOI18N

        lblTittle1.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        lblTittle1.setForeground(new java.awt.Color(255, 255, 255));
        lblTittle1.setText("Manage  Graphs");

        lblUsernameHeader1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblUsernameHeader1.setForeground(new java.awt.Color(255, 255, 255));
        lblUsernameHeader1.setText("Shaaf Salman");

        btnBack1.setBackground(new java.awt.Color(102, 102, 102));
        btnBack1.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/left-chevron.png"))); // NOI18N
        btnBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnBack1ActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        javax.swing.GroupLayout pnlTittle1Layout = new javax.swing.GroupLayout(pnlTittle1);
        pnlTittle1.setLayout(pnlTittle1Layout);
        pnlTittle1Layout.setHorizontalGroup(
                pnlTittle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTittle1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnBack1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTittle1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlTittle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlTittle1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(131, 131, 131))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTittle1Layout.createSequentialGroup()
                                                .addComponent(lblUsernameHeader1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addComponent(picUser1)
                                .addGap(9, 9, 9))
        );
        pnlTittle1Layout.setVerticalGroup(
                pnlTittle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlTittle1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlTittle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlTittle1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlTittle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnlTittle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(lblTittle1)
                                                                .addComponent(lblUsernameHeader1))
                                                        .addComponent(btnBack1)))
                                        .addComponent(picUser1))
                                .addContainerGap(26, Short.MAX_VALUE))
        );

        btnMonthlyGraph.setBackground(new java.awt.Color(0, 153, 204));
        btnMonthlyGraph.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnMonthlyGraph.setForeground(new java.awt.Color(255, 255, 255));
        btnMonthlyGraph.setText("Generate Monthly Graph");
        btnMonthlyGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonthlyGraphActionPerformed(evt);
            }
        });

        btnAnumGraph.setBackground(new java.awt.Color(0, 153, 204));
        btnAnumGraph.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnAnumGraph.setForeground(new java.awt.Color(255, 255, 255));
        btnAnumGraph.setText("Generate Yearly Based Graph");
        btnAnumGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYearlyGraphActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/monitor.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlTittle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnDailyGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnMonthlyGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAnumGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlTittle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnDailyGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(32, 32, 32)
                                                .addComponent(btnMonthlyGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(btnAnumGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel1))
                                .addContainerGap(85, Short.MAX_VALUE))
        );

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }// </editor-fold>

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAnumGraph;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnDailyGraph;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnMonthlyGraph;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTittle;
    private javax.swing.JLabel lblTittle1;
    private javax.swing.JLabel lblUsernameHeader;
    private javax.swing.JLabel lblUsernameHeader1;
    private javax.swing.JLabel picUser;
    private javax.swing.JLabel picUser1;
    private javax.swing.JPanel pnlTittle;
    private javax.swing.JPanel pnlTittle1;
    private javax.swing.JTable tblCategory;
}