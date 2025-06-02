/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.ConexionBD;
import Entidades.Operacion;
import Enumss.TipoOperacion;
import Excepcion.PersistenciaExcepcion;
import Interfaces.IOperacionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public class OperacionDAO implements IOperacionDAO {

    @Override
    public Operacion registrarOperacion(Operacion operacion) throws PersistenciaExcepcion {
        String sql = "INSERT INTO operaciones (tipo_operacion, fecha_hora, monto, id_cuenta_origen, id_cuenta_destino) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, operacion.getTipoOperacion().toString());
            stmt.setTimestamp(2, Timestamp.valueOf(operacion.getFechaHora()));
            stmt.setDouble(3, operacion.getMonto());
            stmt.setObject(4, operacion.getIdCuentaOrigen(), Types.INTEGER);
            stmt.setObject(5, operacion.getIdCuentaDestino(), Types.INTEGER);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    operacion.setId(rs.getInt(1));
                }
            }
            return operacion;
        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al registrar operación", e);
        }
    }

    @Override
    public List<Operacion> buscarOperacionesPorCuenta(int idCuenta) throws PersistenciaExcepcion {
        String sql = """
            SELECT * FROM operaciones 
            WHERE id_cuenta_origen = ? OR id_cuenta_destino = ?
            ORDER BY fecha_hora DESC
            """;

        List<Operacion> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            stmt.setInt(2, idCuenta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapOperacion(rs));
            }

        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al buscar operaciones por cuenta", e);
        }

        return lista;
    }

    @Override
    public List<Operacion> buscarOperacionesPorTipoYFechas(int idCuenta, TipoOperacion tipo, LocalDateTime inicio, LocalDateTime fin) throws PersistenciaExcepcion {
        String sql = """
            SELECT * FROM operaciones 
            WHERE (id_cuenta_origen = ? OR id_cuenta_destino = ?)
              AND tipo_operacion = ?
              AND fecha_hora BETWEEN ? AND ?
            ORDER BY fecha_hora DESC
            """;

        List<Operacion> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            stmt.setInt(2, idCuenta);
            stmt.setString(3, tipo.name());
            stmt.setTimestamp(4, Timestamp.valueOf(inicio));
            stmt.setTimestamp(5, Timestamp.valueOf(fin));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapOperacion(rs));
            }

        } catch (SQLException e) {
            throw new PersistenciaExcepcion("Error al buscar operaciones por tipo y fechas", e);
        }

        return lista;
    }

    private Operacion mapOperacion(ResultSet rs) throws SQLException {
        Operacion op = new Operacion();
        op.setId(rs.getInt("id"));
        op.setTipoOperacion(TipoOperacion.valueOf(rs.getString("tipo_operacion")));
        op.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        op.setMonto(rs.getDouble("monto"));
        op.setIdCuentaOrigen((Integer) rs.getObject("id_cuenta_origen"));
        op.setIdCuentaDestino((Integer) rs.getObject("id_cuenta_destino"));
        return op;
    }
}
