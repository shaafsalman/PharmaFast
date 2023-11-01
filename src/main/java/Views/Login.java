package Views;

public class Login extends javax.swing.JFrame {


    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        pnlBack = new javax.swing.JPanel();
        pnlFront = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        btnRegister = new javax.swing.JToggleButton();
        btnLogin = new javax.swing.JToggleButton();
        doodle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlBack.setBackground(new java.awt.Color(102, 102, 102));

        pnlFront.setBackground(new java.awt.Color(255, 255, 255));

        lblUsername.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(51, 51, 51));
        lblUsername.setText("Username");

        lblLogin.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(51, 51, 51));
        lblLogin.setText("Login");

        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(0, 0, 0));
        txtUsername.setCaretColor(new java.awt.Color(204, 204, 204));

        lblPassword.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(51, 51, 51));
        lblPassword.setText("Password");

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(0, 0, 0));
        txtPassword.setCaretColor(new java.awt.Color(204, 204, 204));

        btnRegister.setBackground(new java.awt.Color(0, 153, 204));
        btnRegister.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("Register");

        btnLogin.setBackground(new java.awt.Color(0, 153, 204));
        btnLogin.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");

        javax.swing.GroupLayout pnlFrontLayout = new javax.swing.GroupLayout(pnlFront);
        pnlFront.setLayout(pnlFrontLayout);
        pnlFrontLayout.setHorizontalGroup(
                pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFrontLayout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(56, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFrontLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46))
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
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addGroup(pnlFrontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addContainerGap()
                                .addComponent(doodle)
                                .addGap(18, 18, 18)
                                .addComponent(pnlFront, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBackLayout.setVerticalGroup(
                pnlBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlBackLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlFront, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(pnlBackLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(doodle)
                                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
    }// </editor-fold>



    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:

        Sighnup newForm = new Sighnup();
        newForm.setVisible(true);
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JToggleButton btnLogin;
    private javax.swing.JToggleButton btnRegister;
    private javax.swing.JLabel doodle;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlBack;
    private javax.swing.JPanel pnlFront;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration
}
