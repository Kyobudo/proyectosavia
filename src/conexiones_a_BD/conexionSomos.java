package conexiones_a_BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class conexionSomos {

    public static Connection getConnection() {
        Connection conexionSomos = null;

        String bd = "system_pruebas01";
        String url = "jdbc:mysql://10.250.2.39/cuentas_medicas";
        String user = "sdelgado";
        String password = "Savia.2024*";
        String driver = "com.mysql.cj.jdbc.Driver";
        
        try {
            Class.forName(driver);
            conexionSomos = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(conexionSomos.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return conexionSomos;
    }
     public static void main(String[] args) {
        Connection con = getConnection();
        if (con != null) {
            System.out.println("Conexión exitosa a la base de datos.");
        } else {
            System.out.println("Error al conectar a la base de datos.");
        }
    }
}



