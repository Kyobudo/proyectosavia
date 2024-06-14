
package conexionesBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class ConexionIntegra {
    public static Connection getConnection() {
        Connection conexion_integra = null;

        String bd = "DATOSINTEGRA";
        String url = "jdbc:mysql://10.250.5.39/DATOSINTEGRA";
        String user = "sdelgado";
        String password = "2uJO?0/G6;]w";
        String driver = "com.mysql.cj.jdbc.Driver";
        
        try {
            Class.forName(driver);
            conexion_integra = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(ConexionSomos.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return conexion_integra;
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
