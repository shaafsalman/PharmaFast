package Main;

import Views.Login;
public class RunMe {
    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });

    }
}
