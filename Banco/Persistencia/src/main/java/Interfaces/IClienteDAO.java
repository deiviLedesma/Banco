/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Cliente;
import Excepcion.PersistenciaExcepcion;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public interface IClienteDAO {

    Cliente insertar(Cliente cliente) throws PersistenciaExcepcion;

    Cliente actualizar(Cliente cliente) throws PersistenciaExcepcion;

    Cliente buscarPorId(int id) throws PersistenciaExcepcion;

    Cliente buscarPorNombre(String nombre) throws PersistenciaExcepcion;

    List<Cliente> buscarTodos() throws PersistenciaExcepcion;

    boolean existeClienteConNombre(String nombre) throws PersistenciaExcepcion;
}
