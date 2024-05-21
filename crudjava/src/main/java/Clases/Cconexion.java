
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Cconexion {
    Connection conectar = null;
    String usuario = "root";
    String contraseña = "admin";
    String bd = "cerouno";
    String ip = "localhost";
    String puerto = "3306";
    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;
    
    public Connection validarconexion() {
        try {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer la conexión
            conectar = DriverManager.getConnection(cadena, usuario, contraseña);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse, error: " + e.toString());
        }
        return conectar;
    }
     public void cerrarConexion() {
        try {
            if (conectar != null) {
                conectar.close();
                
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión, error: " + e.toString());
        }
    }
}
