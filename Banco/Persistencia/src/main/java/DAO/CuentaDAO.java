/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.ConexionBD;
import Entidades.Cuenta;
import Excepcion.PersistenciaExcepcion;
import Interfaces.ICuentaDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public class CuentaDAO implements ICuentaDAO {

    @Override
    public Cuenta insertar(Cuenta cuenta) throws PersistenciaExcepcion {
        String sql = "INSERT INTO cuentas (numero_cuenta, fecha_apertura, saldo, activa, id_cliente) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cuenta.getNumeroCuenta());
            stmt.setDate(2, Date.valueOf(cuenta.getFechaApertura()));
            stmt.setDouble(3, cuenta.getSaldo());
            stmt.setBoolean(4, cuenta.isActiva());
            stmt.setInt(5, cuenta.getIdCliente());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cuenta.setId(rs.getInt(1));
                }
            }
            return cuenta;
        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al insertar cuenta", e);
        }
    }

    @Override
    public void actualizarSaldo(int idCuenta, double nuevoSaldo) throws PersistenciaExcepcion {
        String sql = "UPDATE cuentas SET saldo = ? WHERE id = ?";
        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, nuevoSaldo);
            stmt.setInt(2, idCuenta);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al actualizar el saldo", e);
        }
    }

    @Override
    public void cancelarCuenta(int idCuenta) throws PersistenciaExcepcion {
        String sql = "UPDATE cuentas SET activa = false WHERE id = ?";
        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al cancelar la cuenta", e);
        }
    }

    @Override
    public Cuenta buscarPorNumeroCuenta(String numero) throws PersistenciaExcepcion {
        String sql = "SELECT * FROM cuentas WHERE numero_cuenta = ?";
        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapCuenta(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al buscar cuenta por número", e);
        }
    }

    @Override
    public List<Cuenta> buscarCuentasPorCliente(int idCliente) throws PersistenciaExcepcion {
        String sql = "SELECT * FROM cuentas WHERE id_cliente = ?";
        List<Cuenta> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapCuenta(rs));
            }

        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al buscar cuentas del cliente", e);
        }

        return lista;
    }

    private Cuenta mapCuenta(ResultSet rs) throws SQLException {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(rs.getInt("id"));
        cuenta.setNumeroCuenta(rs.getString("numero_cuenta"));
        cuenta.setFechaApertura(rs.getDate("fecha_apertura").toLocalDate());
        cuenta.setSaldo(rs.getDouble("saldo"));
        cuenta.setActiva(rs.getBoolean("activa"));
        cuenta.setIdCliente(rs.getInt("id_cliente"));
        return cuenta;
    }

}
