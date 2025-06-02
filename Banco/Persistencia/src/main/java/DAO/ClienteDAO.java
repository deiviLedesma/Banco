/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.ConexionBD;
import Entidades.Cliente;
import Excepcion.PersistenciaExcepcion;
import Interfaces.IClienteDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SDavidLedesma
 */
public class ClienteDAO implements IClienteDAO {

    @Override
    public Cliente insertar(Cliente cliente) throws PersistenciaExcepcion {
        String sql = "INSERT INTO clientes (nombre_completo, domicilio, fecha_nacimiento, edad, contrasena) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNombreCompleto());
            stmt.setString(2, cliente.getDomicilio());
            stmt.setDate(3, Date.valueOf(cliente.getFechaNacimiento()));
            stmt.setInt(4, cliente.getEdad());
            stmt.setString(5, cliente.getContrasenia());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }
            return cliente;
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al insertar cliente en la base de datos", ex);
        }
    }

    @Override
    public Cliente actualizar(Cliente cliente) throws PersistenciaExcepcion {
        String sql = "UPDATE clientes SET nombre_completo = ?, domicilio = ?, fecha_nacimiento = ?, edad = ?, contrasena = ? WHERE id = ?";

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombreCompleto());
            stmt.setString(2, cliente.getDomicilio());
            stmt.setDate(3, Date.valueOf(cliente.getFechaNacimiento()));
            stmt.setInt(4, cliente.getEdad());
            stmt.setString(5, cliente.getContrasenia());
            stmt.setInt(6, cliente.getId());

            stmt.executeUpdate();
            return cliente;
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al actualizar el cliente en la base de datos", ex);
        }
    }

    @Override
    public Cliente buscarPorId(int id) throws PersistenciaExcepcion {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapCliente(rs);
            }
            return null;
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al buscarr por id", ex);
        }
    }

    @Override
    public Cliente buscarPorNombre(String nombre) throws PersistenciaExcepcion {
        String sql = "SELECT * FROM clientes WHERE nombre_completo = ?";
        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapCliente(rs);
            }
            return null;
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al buscar por nombre", ex);
        }
    }

    @Override
    public List<Cliente> buscarTodos() throws PersistenciaExcepcion {
        String sql = "SELECT * FROM clientes";
        List<Cliente> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapCliente(rs));
            }
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Eror al buscar todos los clientes en la base de datos", ex);
        }
        return lista;
    }

    @Override
    public boolean existeClienteConNombre(String nombre) throws PersistenciaExcepcion {
        String sql = "SELECT COUNT(*) FROM clientes WHERE nombre_completo = ?";
        try (Connection conn = ConexionBD.abrirConexino(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException ex) {
            throw new PersistenciaExcepcion("Error al validar el cliente en la base de datos ", ex);
        }
    }

    private Cliente mapCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNombreCompleto(rs.getString("nombre_completo"));
        cliente.setDomicilio(rs.getString("domicilio"));
        cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        cliente.setEdad(rs.getInt("edad"));
        cliente.setContrasenia(rs.getString("contrasena"));
        return cliente;
    }
}
