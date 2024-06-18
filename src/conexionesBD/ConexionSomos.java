package conexionesBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexionSomos {

    public static Connection getConnection() {
        Connection con = null;

        String bd = "cuentas_medicas";
        String url = "jdbc:mysql://10.250.2.39/"+bd;
        String user = "sdelgado";
        String password = "Savia.2024*";
        String driver = "com.mysql.cj.jdbc.Driver";     
     

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(ConexionSomos.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return con;
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



