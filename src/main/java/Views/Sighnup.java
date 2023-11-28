package Views;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import Controllers.UserController;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

//final

/**
 *
 * @author ShaafSalman
 */
public class Sighnup extends javax.swing.JFrame {

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Sighnup() {
        initComponents();
    }

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String role = (String) cmboRole.getSelectedItem();

        try {
            int adminCode = Integer.parseInt(txtAdminCode.getText().trim());

            User newUser = new User(0, username, password, role);
            boolean registered = UserController.registerUser(newUser, adminCode);
            if (!registered) {
                JOptionPane.showMessageDialog(null, "Please Try Again");
            }

        }
        catch (NumberFormatException e)
        {
        }
    }
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        this.dispose();
        Login loginFrame = new Login();
        loginFrame.setVisible(true);
    }

    public static void main(String[] args) {
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
            java.util.logging.Logger.getLogger(Sighnup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sighnup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sighnup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sighnup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sighnup().setVisible(true);
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        pnlBack = new javax.swing.JPanel();
        pnlFront = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtAdminCode = new javax.swing.JTextField();
        btnRegister = new javax.swing.JToggleButton();
        btnLogin = new javax.swing.JToggleButton();
        cmboRole = new javax.swing.JComboBox<>();
        txtPassword = new javax.swing.JPasswordField();
        doodle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlBack.setBackground(new java.awt.Color(102, 102, 102));

        pnlFront.setBackground(new java.awt.Color(255, 255, 255));

        lblUsername.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(51, 51, 51));
        lblUsername.setText("Username");

        lblLogin.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(51, 51, 51));
        lblLogin.setText("Signup");

        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(0, 0, 0));
        txtUsername.setCaretColor(new java.awt.Color(204, 204, 204));

        lblPassword.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(51, 51, 51));
        lblPassword.setText("Password");

        txtAdminCode.setBackground(new java.awt.Color(255, 255, 255));
        txtAdminCode.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtAdminCode.setForeground(new java.awt.Color(153, 153, 153));
        txtAdminCode.setText("Admin Code");
        txtAdminCode.setCaretColor(new java.awt.Color(204, 204, 204));

        btnRegister.setBackground(new java.awt.Color(0, 153, 204));
        btnRegister.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(0, 153, 204));
        btnLogin.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnLoginActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cmboRole.setBackground(new java.awt.Color(255, 255, 255));
        cmboRole.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cmboRole.setForeground(new java.awt.Color(0, 0, 0));
        cmboRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manager", "Sales Assistant" }));

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlFrontLayout = new javax.swing.GroupLayout(pnlFront);
        pnlFront.setLayout(pnlFrontLayout);
        pnlFrontLayout.setHorizontalGroup(
                pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFrontLayout.createSequentialGroup()
                                .addGroup(pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(pnlFrontLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFrontLayout.createSequentialGroup()
                                                .addGap(68, 68, 68)
                                                .addGroup(pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                                                        .addGroup(pnlFrontLayout.createSequentialGroup()
                                                                .addComponent(cmboRole, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtAdminCode))
                                                        .addGroup(pnlFrontLayout.createSequentialGroup()
                                                                .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(71, Short.MAX_VALUE))
                        .addGroup(pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlFrontLayout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(lblLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(494, Short.MAX_VALUE)))
        );
        pnlFrontLayout.setVerticalGroup(
                pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFrontLayout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addGroup(pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlFrontLayout.createSequentialGroup()
                                                .addComponent(cmboRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                                .addGroup(pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(33, 33, 33))
                                        .addGroup(pnlFrontLayout.createSequentialGroup()
                                                .addComponent(txtAdminCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlFrontLayout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(lblLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(328, Short.MAX_VALUE)))
        );

        doodle.setIcon(new javax.swing.ImageIcon(("src/main/resources/Material/doodle_short.png"))); // NOI18N

        javax.swing.GroupLayout pnlBackLayout = new javax.swing.GroupLayout(pnlBack);
        pnlBack.setLayout(pnlBackLayout);
        pnlBackLayout.setHorizontalGroup(
                pnlBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(doodle)
                                .addGap(18, 18, 18)
                                .addComponent(pnlFront, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        pnlBackLayout.setVerticalGroup(
                pnlBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlBackLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlFront, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(pnlBackLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(doodle)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }// </editor-fold>

    // Variables declaration - do not modify
    private javax.swing.JToggleButton btnLogin;
    private javax.swing.JToggleButton btnRegister;
    private javax.swing.JComboBox<String> cmboRole;
    private javax.swing.JLabel doodle;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlBack;
    private javax.swing.JPanel pnlFront;
    private javax.swing.JTextField txtAdminCode;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration
}
