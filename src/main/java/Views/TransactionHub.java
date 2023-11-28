package Views;

import Controllers.AdminController;
import Controllers.CashierController;
import Dao.TransactionDao;
import Dao.TransactionItemDao;
import Helpers.ReportGenerator;
import Helpers.SessionManager;
import Helpers.UtilityFunctions;
import Models.Transaction;
import Models.TransactionItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author ShaafSalman
 */
public class TransactionHub extends javax.swing.JFrame {

//////////////////////////////////////////////////////////////////////////////////////
    CashierController csController = new CashierController();
    ReportGenerator reportGenerator = new ReportGenerator();
    boolean Payment = false;


    public TransactionHub() throws SQLException {
        initComponents();
        optimizTable(tblCart);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                    int quantity = csController.askForQuantity();
                    if (quantity > 0 && csController.isQuantityAvailable(enteredProductID, quantity)) {
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

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"), "handlePayment");
        getRootPane().getActionMap().put("handlePayment", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePayment();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"), "triggerProcessButton");
        getRootPane().getActionMap().put("triggerProcessButton", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnProcessActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
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
            subtotal += (float) rowTotal;
        }
        txtSubTotal.setText(String.valueOf(subtotal));
        txtVat.setText(String.valueOf(csController.VAT));
        float total = subtotal+((csController.VAT/100) * subtotal);
        txtTotalAmount.setText(String.valueOf(total));
    }
    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {
        Process();
    }
    private void btnNewF4ActionPerformed(java.awt.event.ActionEvent evt) {Process();}
    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {dispose();}
    private void btnTenderF3ActionPerformed(java.awt.event.ActionEvent evt) {handlePayment();}
    private void btnVoidF10ActionPerformed(java.awt.event.ActionEvent evt) {clearAll();}
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
                csController.saveTransactions(tblCart,txtTotalAmount.getText());
                Payment = true;
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient payment. The paid amount is less than the total amount.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid numeric amount.");
        }
    }
    private void Process() {

        if (Payment)
        {
            String total = txtTotalAmount.getText();
            String subTotal = txtSubTotal.getText();
            String vat = txtVat.getText();
            String paidAmount = txtAmountTender.getText();
            String change = txtChange.getText();

            String pdfFilePath = reportGenerator.generateReceipt(tblCart, total, subTotal, vat, paidAmount, change);
            UtilityFunctions.displayReport(pdfFilePath);

        }
        else
        {
            JOptionPane.showMessageDialog(this,"Please Pay First");

        }

    }
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


    void optimizTable(JTable table) {
        table.setRowHeight(40);

        table.setOpaque(false);
        table.setShowGrid(false);
        table.setBackground(new Color(255, 255, 255));

        // Set the selection background to light gray
        table.setSelectionBackground(new Color(220, 220, 220));

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

    public static void main(String args[]) {


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TransactionHub().setVisible(true);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        lblVoid = new javax.swing.JLabel();
        lblTender = new javax.swing.JLabel();
        lblNew = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
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
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTittle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(457, 457, 457)
                                .addComponent(txtUserName)
                                .addGap(15, 15, 15)
                                .addComponent(picProfile)
                                .addContainerGap())
        );
        pnlTittleLayout.setVerticalGroup(
                pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTittleLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(pnlTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                                .addComponent(txtUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(16, 16, 16))
                                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                                .addComponent(txtTittle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(8, 8, 8))
                                        .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(22, 22, 22))
                        .addGroup(pnlTittleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(picProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(16, 16, 16))
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
        lblNew.setText("Process");

        lblClose.setBackground(new java.awt.Color(204, 204, 204));
        lblClose.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblClose.setForeground(new java.awt.Color(51, 51, 51));
        lblClose.setText("Close");

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
        btnNewF4.setText("F2");
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
        txtSubTotal.setToolTipText("");
        txtSubTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

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
                        .addGroup(leftPannelLayout.createSequentialGroup()
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addGroup(leftPannelLayout.createSequentialGroup()
                                                                        .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(lblAmountTendered)
                                                                                .addComponent(lblTotalAmount)
                                                                                .addComponent(lblVat)
                                                                                .addComponent(lblChange))
                                                                        .addGap(18, 18, 18)
                                                                        .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addComponent(txtAmountTender, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(txtTotalAmount, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, leftPannelLayout.createSequentialGroup()
                                                                                        .addComponent(txtVat, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addComponent(lblVat1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(txtChange)))
                                                                .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(lblSubTotal)))
                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addComponent(lblTender, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                                                .addGap(53, 53, 53)
                                                                                .addComponent(lblSummary))
                                                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                                                .addGap(36, 36, 36)
                                                                                .addComponent(lblVoid))))
                                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                                .addComponent(btnTenderF3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(btnVoidF10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                                                .addComponent(btnNewF4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                                                .addGap(6, 6, 6)
                                                                                .addComponent(lblNew)
                                                                                .addGap(40, 40, 40)
                                                                                .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                        .addGroup(leftPannelLayout.createSequentialGroup()
                                                .addGap(66, 66, 66)
                                                .addComponent(btnProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(22, 22, 22)))
                                .addGap(12, 12, 12))
        );
        leftPannelLayout.setVerticalGroup(
                leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(leftPannelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnVoidF10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNewF4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTenderF3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(leftPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTender)
                                        .addComponent(lblClose)
                                        .addComponent(lblNew)
                                        .addComponent(lblVoid))
                                .addGap(30, 30, 30)
                                .addComponent(lblSummary)
                                .addGap(26, 26, 26)
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
                                .addGap(39, 39, 39)
                                .addComponent(btnProcess)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                        .addComponent(jScrollPane1)
                                                        .addComponent(txtBarcode)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(165, 165, 165)
                                                                .addComponent(lblTransactionID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(194, 194, 194))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lblBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(509, 509, 509)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(leftPannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())
                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlTittle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(lblTransactionID, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(leftPannel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>



    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnNewF4;
    private javax.swing.JButton btnProcess;

    private javax.swing.JButton btnTenderF3;
    private javax.swing.JButton btnVoidF10;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAmountTendered;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblChange;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblNew;
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
