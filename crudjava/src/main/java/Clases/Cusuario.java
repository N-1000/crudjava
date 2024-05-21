package Clases;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Cusuario {

    public Clases.Cconexion objetoConexion; // Declaración de variable para la conexión a la base de datos

    // Método para mostrar una lista de productos en un JComboBox
    public void MostrarlistaP(JComboBox comboLista) {
        objetoConexion = new Clases.Cconexion(); // Inicialización de la conexión a la base de datos

        String consultaL = "select * from Productos"; // Consulta SQL para seleccionar todos los productos
        Statement st;
       

        try {
            st = objetoConexion.validarconexion().createStatement(); // Creación de un Statement para ejecutar la consulta SQL
            ResultSet rs = st.executeQuery(consultaL); // Ejecución de la consulta y obtención de un ResultSet con los resultados
            comboLista.removeAllItems(); // Limpia todos los elementos existentes en el JComboBox

            // Itera sobre los resultados del ResultSet
            while (rs.next()) {
                String nombreproducto = rs.getString("nombre"); // Obtiene el nombre del producto de la columna "nombre"
                comboLista.addItem(nombreproducto); // Agrega el nombre del producto al JComboBox
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar la lista: " + e.getMessage()); // Muestra un mensaje de error en caso de excepción SQL
        } finally {
            objetoConexion.cerrarConexion(); // Cierra la conexión a la base de datos en el bloque finally para asegurar que siempre se cierre
        }
    }

    public void MostrarDatosT(JTable tablaproductos) {
        String consultaT = "select * from productos";
        Statement stdos;

        try {
            stdos = objetoConexion.validarconexion().createStatement();
            ResultSet rs = stdos.executeQuery(consultaT);

            DefaultTableModel model = new DefaultTableModel();

            // Agrega columnas al modelo de tabla
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Precio");

            // Agrega datos al modelo de tabla
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                BigDecimal precio = rs.getBigDecimal("precio");

                // Crea una matriz de fila con los datos
                Object[] row = {id, nombre, precio};

                // Agrega la fila al modelo de tabla
                model.addRow(row);
            }

            // Establece el modelo de tabla para la JTable
            tablaproductos.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
        } finally {
            // Cierra la conexión a la base de datos
            objetoConexion.cerrarConexion();
        }
    }

    public void InsertarDatos(String nombre, BigDecimal precio) {

        String InsertarD = "insert into Productos (nombre, precio) values (?,?)";
        PreparedStatement ps;

        try {
            // Preparar la conexión y la consulta SQL
            ps = objetoConexion.validarconexion().prepareStatement(InsertarD);
            // Asignar el valor del nombre al primer marcador de posición (?)
            ps.setString(1, nombre);

            // Asignar el valor del precio al segundo marcador de posición (?)
            ps.setBigDecimal(2, precio);

            // Ejecutar la consulta de inserción
            ps.executeUpdate();
            

        } catch (SQLException e) {
            // Manejar cualquier excepción SQL que ocurra y mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al insertar el producto: " + e.getMessage());
        } finally {
            // Cerrar la conexión a la base de datos, ya sea que la inserción haya tenido éxito o no
            objetoConexion.cerrarConexion();
        }
    }
    
    public void EliminarD (int id){
    String EliminarSQL = "delete from Productos where id = ?";
    PreparedStatement psdos;
    
    String reiniciarSQL = "ALTER TABLE Productos AUTO_INCREMENT = 1"; // Reiniciar el contador de ID
    PreparedStatement pstres;
        try {
            psdos = objetoConexion.validarconexion().prepareStatement(EliminarSQL);
            pstres = objetoConexion.validarconexion().prepareStatement(reiniciarSQL);
            psdos.setInt(1, id);
            psdos.executeUpdate();
            pstres.executeUpdate();
            
        } catch (Exception e) {
            // Manejar cualquier excepción SQL que ocurra y mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al insertar el producto: " + e.getMessage());
        } finally {
            // Cerrar la conexión a la base de datos, ya sea que la inserción haya tenido éxito o no
            objetoConexion.cerrarConexion();
        }
    }
}
