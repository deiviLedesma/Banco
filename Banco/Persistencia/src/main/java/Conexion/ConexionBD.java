/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author SDavidLedesma
 */
public class ConexionBD {

    //atributpos para la conexion
    private static final String URL = "jdbc:mysql://localhost:3306/banco";
    private static final String USER = "root";
    private static final String PASS = "Inunanash1";

    private ConexionBD() {
        //constructor privado para evitar instanciacion
    }

    /**
     * abre una nueva conexion
     *
     * @return conexion activa
     * @throws SQLException
     */
    public static Connection abrirConexino() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Cierra una conexion activa si no es nula
     * @param conexion 
     */
    public static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
