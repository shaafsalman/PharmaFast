package Main;

import Views.Login;
import Helpers.ConnectionFile;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class RunMe {
    public static void main(String[] args) {
        if (isDatabaseActive()) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new Login().setVisible(true);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Error: Unable to connect to the database.", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isDatabaseActive() {
        try (Connection connection = ConnectionFile.getConnection()) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
