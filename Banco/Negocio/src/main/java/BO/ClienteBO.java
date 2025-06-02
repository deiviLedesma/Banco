/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.ClienteDAO;
import DTO.ClienteDTO;
import Entidades.Cliente;
import Excepcion.PersistenciaExcepcion;
import Interfaces.IClienteBO;
import Interfaces.IClienteDAO;
import Mapper.ClienteConvertidor;
import NegocioException.NegocioException;
import Utilerias.Encriptador;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SDavidLedesma
 */
public class ClienteBO implements IClienteBO {

    private final IClienteDAO clienteDAO;

    public ClienteBO() {
        this.clienteDAO = new ClienteDAO();
    }

    @Override
    public ClienteDTO registrarCliente(ClienteDTO cliente) throws NegocioException {
        // Validaciones lógicas
        if (cliente.getNombreCompleto() == null || cliente.getContrasenia() == null) {
            try {
                throw new Exception("Nombre o contraseña no pueden estar vacíos.");
            } catch (Exception ex) {
                Logger.getLogger(ClienteBO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Encriptar contraseña antes de guardar
        cliente.setContrasenia(Encriptador.encriptar(cliente.getContrasenia()));
        Cliente entidad = ClienteConvertidor.dtoAEntidad(cliente);
        try {
            clienteDAO.insertar(entidad);
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioException("Error al registrar el cliente", ex);
        }
        return cliente;
    }

    @Override
    public ClienteDTO actualizarCliente(ClienteDTO cliente) throws NegocioException {
        Cliente entidad = ClienteConvertidor.dtoAEntidad(cliente);
        try {
            clienteDAO.actualizar(entidad);
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioException("Error al actualizar el cliente", ex);
        }
        return cliente;
    }

    @Override
    public ClienteDTO buscarClientePorId(int id) throws NegocioException {
        try {
            Cliente entidad = clienteDAO.buscarPorId(id);
            return ClienteConvertidor.entidadADTO(entidad);
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioException("Error al buscar el cliente por el id", ex);
        }
    }

    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() throws NegocioException {
        try {
            List<Cliente> entidades = clienteDAO.buscarTodos();
            List<ClienteDTO> dtos = new ArrayList<>();
            for (Cliente entidad : entidades) {
                dtos.add(ClienteConvertidor.entidadADTO(entidad));
            }
            return dtos;
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioException("Error al buscar todos los clientes", ex);
        }
    }
}
