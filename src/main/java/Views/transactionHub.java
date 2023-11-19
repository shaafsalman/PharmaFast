package Views;

import Controllers.AdminController;
import Controllers.CashierController;
import Helpers.ReportGenerator;
import Helpers.UtilityFunctions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 *
 * @author ShaafSalman
 */
public class transactionHub extends javax.swing.JFrame {


    CashierController csController = new CashierController();
    AdminController adController = new AdminController();
    UtilityFunctions uFunctions = new UtilityFunctions();

    ReportGenerator reportGenerator = new ReportGenerator();



    public transactionHub() throws SQLException {
        initComponents();
        optimizTable(tblCart);

        txtSubTotal.setEditable(false);
        txtVat.setEditable(false);
        txtTotalAmount.setEditable(false);
        txtChange.setEditable(false);
        txtAmountTender.setEditable(false);
        txtBarcode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredProductID = txtBarcode.getText();
                if (csController.productExists(enteredProductID)) {
                    int quantity = askForQuantity();
                    if (quantity > 0 && isQuantityAvailable(enteredProductID, quantity)) {
                        addRowToTable(enteredProductID, quantity);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid quantity or not enough stock");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Product not found in the database");
                }
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F3"), "handlePayment");
        getRootPane().getActionMap().put("handlePayment", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePayment();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F10"), "clearAll");
        getRootPane().getActionMap().put("clearAll", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F2"), "performAction");
        getRootPane().getActionMap().put("performAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnProcessActionPerformed(e);
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "closeForm");
        getRootPane().getActionMap().put("closeForm", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    private int askForQuantity() {
        String input = JOptionPane.showInputDialog("Enter quantity:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    private boolean isQuantityAvailable(String productID, int quantity)
    {
        return csController.isQuantityAvailable(productID, quantity);
    }
    private void addRowToTable(String productID, int quantity) {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        boolean isAlreadyInCart = false;

        for (int i = 0; i < model.getRowCount(); i++) {
            String cartProductID = model.getValueAt(i, 0).toString();
            if (cartProductID.equals(productID)) {
                int existingQuantity = (int) model.getValueAt(i, 4);
                int totalQuantity = existingQuantity + quantity;

                int availableQuantity = csController.getAvailableQuantity(productID);

                if (totalQuantity <= availableQuantity) {
                    model.setValueAt(totalQuantity, i, 4);
                    double price = (double) model.getValueAt(i, 3);
                    double totalPrice = price * totalQuantity;
                    model.setValueAt(totalPrice, i, 5);
                } else {
                    JOptionPane.showMessageDialog(null, "Available quantity is " + availableQuantity);
                }
                isAlreadyInCart = true;
                break;
            }
        }

        if (!isAlreadyInCart) {
            int availableQuantity = csController.getAvailableQuantity(productID);
            if (quantity <= availableQuantity) {
                String productName = csController.getProductName(productID);
                String categoryName = csController.getCategoryName(productID);
                double price = csController.getPrice(productID);
                double totalPrice = price * quantity;

                model.addRow(new Object[]{productID, productName, categoryName, price, quantity, totalPrice});
            } else {
                JOptionPane.showMessageDialog(null, "Available quantity is " + availableQuantity);
            }
        }


        float subtotal = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            double rowTotal = (double) model.getValueAt(i, 5);
            subtotal += rowTotal;
        }
        txtSubTotal.setText(String.valueOf(subtotal));


        txtVat.setText(String.valueOf(csController.VAT));

        float total = subtotal+((csController.VAT/100) * subtotal);
        txtTotalAmount.setText(String.valueOf(total));

    }
    private void txtBarcodeActionPerformed(java.awt.event.ActionEvent evt) {
    }
    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {
        String total = txtTotalAmount.getText();
        String subTotal = txtSubTotal.getText();
        String vat = txtVat.getText();
        String paidAmount = txtAmountTender.getText();
        String change = txtChange.getText();

        String pdfFilePath = reportGenerator.generateReceipt(tblCart, total, subTotal, vat, paidAmount, change);
        uFunctions.displayReport(pdfFilePath);
    }



    private void btnVoidF10ActionPerformed(java.awt.event.ActionEvent evt) {
        clearAll();
    }
    private void btnQtyF2ActionPerformed(java.awt.event.ActionEvent evt) {

    }
    private void btnNewF4ActionPerformed(java.awt.event.ActionEvent evt) {

    }
    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }
    private void btnTenderF3ActionPerformed(java.awt.event.ActionEvent evt) {
        handlePayment();
    }
    private void handlePayment() {
        double totalAmount = Double.parseDouble(txtTotalAmount.getText());

        String input = JOptionPane.showInputDialog("Total amount: " + totalAmount + "\nEnter the paid amount:");

        try {
            double paidAmount = Double.parseDouble(input);
            if (paidAmount >= totalAmount) {
                double change = paidAmount - totalAmount;
                JOptionPane.showMessageDialog(null, "Payment successful. Change: " + change);
                txtChange.setText(String.valueOf(change));
                txtAmountTender.setText(String.valueOf(paidAmount));
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient payment. The paid amount is less than the total amount.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid numeric amount.");
        }
    }
    void optimizTable(JTable table) {
        table.setRowHeight(40);

        table.setOpaque(false);
        table.setShowGrid(false);
        table.setBackground(new Color(255, 255, 255));
        table.setSelectionBackground(new Color(52, 52, 52));

        class HeaderRenderer extends DefaultTableCellRenderer {
            public HeaderRenderer() {
                setHorizontalAlignment(SwingConstants.CENTER);
            }
        }

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        header.setBackground(new Color(47, 47, 47));
        header.setForeground(Color.darkGray);
        header.setBorder(new EmptyBorder(20, 10, 20, 10));

        TableCellRenderer headerRenderer = new HeaderRenderer();
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        table.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        lblTransactionID = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        pnlTittle = new javax.swing.JPanel();
        picProfile = new javax.swing.JLabel();
        txtTittle = new javax.swing.JLabel();
        txtUserName = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        txtBarcode = new javax.swing.JTextField();
        lblBarcode = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        leftPannel = new javax.swing.JPanel();
        btnProcess = new javax.swing.JButton();
        btnVoidF10 = new javax.swing.JButton();
        btnQtyF2 = new javax.swing.JButton();
        lblVoid = new javax.swing.JLabel();
        lblTender = new javax.swing.JLabel();
        lblNew = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        lblQTY = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        lblSummary = new javax.swing.JLabel();
        lblVat = new javax.swing.JLabel();
        lblTotalAmount = new javax.swing.JLabel();
        lblAmountTendered = new javax.swing.JLabel();
        btnNewF4 = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        lblChange = new javax.swing.JLabel();
        txtVat = new javax.swing.JTextField();
        txtSubTotal = new javax.swing.JTextField();
        txtTotalAmount = new javax.swing.JTextField();
        txtAmountTender = new javax.swing.JTextField();
        txtChange = new javax.swing.JTextField();
        lblVat1 = new javax.swing.JLabel();
        btnTenderF3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTransactionID.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        lblTransactionID.setText("Transaction #");

        pnlTittle.setBackground(new java.awt.Color(102, 102, 102));

        picProfile.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/profilepic128.png"))); // NOI18N

        txtTittle.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        txtTittle.setForeground(new java.awt.Color(255, 255, 255));
        txtTittle.setText("Transaction Portal");

        txtUserName.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txtUserName.setForeground(new java.awt.Color(255, 255, 255));
        txtUserName.setText("Shaaf Salman");

        btnBack.setBackground(new java.awt.Color(102, 102, 102));
        btnBack.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/left-chevron.png"))); // NOI18N

        javax.swing.GroupLayout pnlTittleLayout = new javax.swing.GroupLayout(pnlTittle);
        pnlTittle.setLayout(pnlTittleLayout);
        pnlTittleLayout.setHorizontalGroup(
                pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTittleLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTittle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 492, Short.MAX_VALUE)
                                .addComponent(txtUserName)
                                .addGap(15, 15, 15)
                                .addComponent(picProfile)
                                .addContainerGap())
        );
        pnlTittleLayout.setVerticalGroup(
                pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTittleLayout.createSequentialGroup()
                                .addContainerGap(25, Short.MAX_VALUE)
                                .addGroup(pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtUserName)
                                        .addComponent(txtTittle)
                                        .addComponent(btnBack))
                                .addGap(22, 22, 22))
                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(picProfile)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtBarcode.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        lblBarcode.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblBarcode.setText("Barcode");

        tblCart.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tblCart.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Barcode", "Product", "Category", "Price", "Quantity", "Total"
                }
        ));
        jScrollPane1.setViewportView(tblCart);

        leftPannel.setBackground(new java.awt.Color(204, 204, 204));

        btnProcess.setBackground(new java.awt.Color(0, 153, 204));
        btnProcess.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnProcess.setText("Process");
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });

        btnVoidF10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnVoidF10.setText("F10");
        btnVoidF10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoidF10ActionPerformed(evt);
            }
        });

        btnQtyF2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnQtyF2.setText("F2");
        btnQtyF2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQtyF2ActionPerformed(evt);
            }
        });

        lblVoid.setBackground(new java.awt.Color(204, 204, 204));
        lblVoid.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblVoid.setForeground(new java.awt.Color(51, 51, 51));
        lblVoid.setText("Void");

        lblTender.setBackground(new java.awt.Color(204, 204, 204));
        lblTender.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblTender.setForeground(new java.awt.Color(51, 51, 51));
        lblTender.setText("Tender");

        lblNew.setBackground(new java.awt.Color(204, 204, 204));
        lblNew.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblNew.setForeground(new java.awt.Color(51, 51, 51));
        lblNew.setText("New");

        lblClose.setBackground(new java.awt.Color(204, 204, 204));
        lblClose.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblClose.setForeground(new java.awt.Color(51, 51, 51));
        lblClose.setText("Close");

        lblQTY.setBackground(new java.awt.Color(204, 204, 204));
        lblQTY.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblQTY.setForeground(new java.awt.Color(51, 51, 51));
        lblQTY.setText("Qty");

        lblSubTotal.setBackground(new java.awt.Color(204, 204, 204));
        lblSubTotal.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblSubTotal.setForeground(new java.awt.Color(51, 51, 51));
        lblSubTotal.setText("SubTotal");

        lblSummary.setBackground(new java.awt.Color(204, 204, 204));
        lblSummary.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblSummary.setForeground(new java.awt.Color(51, 51, 51));
        lblSummary.setText("Summary");

        lblVat.setBackground(new java.awt.Color(204, 204, 204));
        lblVat.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblVat.setForeground(new java.awt.Color(51, 51, 51));
        lblVat.setText("Less Vat");

        lblTotalAmount.setBackground(new java.awt.Color(204, 204, 204));
        lblTotalAmount.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblTotalAmount.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalAmount.setText("Total Amount");

        lblAmountTendered.setBackground(new java.awt.Color(204, 204, 204));
        lblAmountTendered.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblAmountTendered.setForeground(new java.awt.Color(51, 51, 51));
        lblAmountTendered.setText("Amount Tendered");

        btnNewF4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnNewF4.setText("F4");
        btnNewF4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewF4ActionPerformed(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnClose.setText("X");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblChange.setBackground(new java.awt.Color(204, 204, 204));
        lblChange.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblChange.setForeground(new java.awt.Color(51, 51, 51));
        lblChange.setText("Change");

        txtVat.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        txtSubTotal.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        txtTotalAmount.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        txtAmountTender.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        txtChange.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        lblVat1.setBackground(new java.awt.Color(204, 204, 204));
        lblVat1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblVat1.setForeground(new java.awt.Color(51, 51, 51));
        lblVat1.setText("%");

        btnTenderF3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnTenderF3.setText("F3");
        btnTenderF3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTenderF3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPannelLayout = new javax.swing.GroupLayout(leftPannel);
        leftPannel.setLayout(leftPannelLayout);
        leftPannelLayout.setHorizontalGroup(
                leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPannelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblSummary)
                                .addGap(125, 125, 125))
                        .addGroup(leftPannelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                .addComponent(lblSubTotal)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblAmountTendered)
                                                        .addComponent(lblTotalAmount)
                                                        .addComponent(lblVat)
                                                        .addComponent(lblChange))
                                                .addGap(18, 18, 18)
                                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtChange)
                                                        .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(txtTotalAmount, javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(txtAmountTender, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, leftPannelLayout.createSequentialGroup()
                                                                        .addComponent(txtVat, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lblVat1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPannelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))
                        .addGroup(leftPannelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnTenderF3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnVoidF10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnQtyF2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNewF4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPannelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTender, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblVoid)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblQTY)
                                .addGap(44, 44, 44)
                                .addComponent(lblNew)
                                .addGap(38, 38, 38)
                                .addComponent(lblClose)
                                .addGap(24, 24, 24))
        );
        leftPannelLayout.setVerticalGroup(
                leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftPannelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnVoidF10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnQtyF2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNewF4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTenderF3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTender)
                                        .addComponent(lblClose)
                                        .addComponent(lblNew)
                                        .addComponent(lblQTY)
                                        .addComponent(lblVoid))
                                .addGap(18, 18, 18)
                                .addComponent(lblSummary)
                                .addGap(38, 38, 38)
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblSubTotal)
                                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblVat)
                                        .addComponent(txtVat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblVat1))
                                .addGap(23, 23, 23)
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTotalAmount)
                                        .addComponent(txtTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblAmountTendered)
                                        .addComponent(txtAmountTender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblChange)
                                        .addComponent(txtChange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(btnProcess)
                                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlTittle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                                                                .addComponent(txtBarcode)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(165, 165, 165)
                                                                        .addComponent(lblTransactionID, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(lblBarcode))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(leftPannel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlTittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(lblTransactionID, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblBarcode)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(leftPannel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        txtBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBarcodeActionPerformed(evt);
            }
        });


        pack();
    }// </editor-fold>


    public static void main(String args[]) {


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new transactionHub().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void clearAll() {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        model.setRowCount(0);

        txtSubTotal.setText("");
        txtVat.setText("");
        txtTotalAmount.setText("");
        txtChange.setText("");
        txtAmountTender.setText("");
        txtBarcode.setText("");
    }

    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnNewF4;
    private javax.swing.JButton btnProcess;
    private javax.swing.JButton btnQtyF2;
    private javax.swing.JButton btnTenderF3;
    private javax.swing.JButton btnVoidF10;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAmountTendered;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblChange;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblNew;
    private javax.swing.JLabel lblQTY;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblSummary;
    private javax.swing.JLabel lblTender;
    private javax.swing.JLabel lblTotalAmount;
    private javax.swing.JLabel lblTransactionID;
    private javax.swing.JLabel lblVat;
    private javax.swing.JLabel lblVat1;
    private javax.swing.JLabel lblVoid;
    private javax.swing.JPanel leftPannel;
    private javax.swing.JLabel picProfile;
    private javax.swing.JPanel pnlTittle;
    private javax.swing.JTable tblCart;
    private javax.swing.JTextField txtAmountTender;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtChange;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JLabel txtTittle;
    private javax.swing.JTextField txtTotalAmount;
    private javax.swing.JLabel txtUserName;
    private javax.swing.JTextField txtVat;
}
