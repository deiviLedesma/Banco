/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.ConexionBD;
import Entidades.RetiroSinCuenta;
import Enumss.Estado;
import Excepcion.PersistenciaExcepcion;
import Interfaces.IRetiroSinCuentaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author SDavidLedesma
 */
public class RetiroSinCuentaDAO implements IRetiroSinCuentaDAO {

    @Override
    public RetiroSinCuenta registrarRetiro(RetiroSinCuenta retiro) throws PersistenciaExcepcion {
        String sql = """
            INSERT INTO retiros_sin_cuenta (contrasena, fecha_solicitud, monto, estado, id_cliente, id_operacion)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, retiro.getContrasena());
            stmt.setTimestamp(2, Timestamp.valueOf(retiro.getFechaSolicitud()));
            stmt.setDouble(3, retiro.getMonto());
            stmt.setString(4, retiro.getEstado().name());
            stmt.setInt(5, retiro.getIdCliente());
            stmt.setInt(6, retiro.getIdOperacion());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    retiro.setFolio(rs.getInt(1));
                }
            }
            return retiro;
        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al registrar retiro sin cuenta", e);
        }
    }

    @Override
    public RetiroSinCuenta buscarPorFolioYContrasena(int folio, String contrasena) throws PersistenciaExcepcion {
        String sql = "SELECT * FROM retiros_sin_cuenta WHERE folio = ? AND contrasena = ?";

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, folio);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRetiro(rs);
            }

        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al buscar retiro por folio y contraseña", e);
        }

        return null;
    }

    @Override
    public void actualizarEstado(int folio, String nuevoEstado) throws PersistenciaExcepcion {
        String sql = "UPDATE retiros_sin_cuenta SET estado = ? WHERE folio = ?";

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, folio);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al actualizar estado del retiro", e);
        }
    }

    private RetiroSinCuenta mapRetiro(ResultSet rs) throws SQLException {
        RetiroSinCuenta r = new RetiroSinCuenta();
        r.setFolio(rs.getInt("folio"));
        r.setContrasena(rs.getString("contrasena"));
        r.setFechaSolicitud(rs.getTimestamp("fecha_solicitud").toLocalDateTime());
        r.setMonto(rs.getDouble("monto"));
        r.setEstado(Estado.valueOf(rs.getString("estado")));
        r.setIdCliente(rs.getInt("id_cliente"));
        r.setIdOperacion(rs.getInt("id_operacion"));
        return r;
    }
}
