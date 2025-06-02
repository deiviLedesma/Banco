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

/**
 *
 * @author SDavidLedesma
 */
public class ClienteBO implements IClienteBO {

    final IClienteDAO clienteDAO;

    public ClienteBO() {
        this.clienteDAO = new ClienteDAO();
    }

    // Constructor adicional para pruebas
    public ClienteBO(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public ClienteDTO registrarCliente(ClienteDTO cliente) throws NegocioException {
        validarCliente(cliente);

        // Encriptar contraseña antes de guardar
        cliente.setContrasenia(Encriptador.encriptar(cliente.getContrasenia()));

        Cliente entidad = ClienteConvertidor.dtoAEntidad(cliente);
        try {
            clienteDAO.insertar(entidad);
            cliente.setId(entidad.getId()); // devolver con ID actualizado
            return cliente;
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioException("Error al registrar el cliente", ex);
        }
    }

    @Override
    public ClienteDTO actualizarCliente(ClienteDTO cliente) throws NegocioException {
        Cliente entidad = ClienteConvertidor.dtoAEntidad(cliente);
        try {
            clienteDAO.actualizar(entidad);
            return cliente;
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioException("Error al actualizar el cliente", ex);
        }
    }

    @Override
    public ClienteDTO buscarClientePorId(int id) throws NegocioException {
        try {
            Cliente entidad = clienteDAO.buscarPorId(id);
            return ClienteConvertidor.entidadADTO(entidad);
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioException("Error al buscar el cliente por ID", ex);
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
            throw new NegocioException("Error al obtener todos los clientes", ex);
        }
    }

    /**
     * Valida los datos del cliente.
     */
    private void validarCliente(ClienteDTO cliente) throws NegocioException {
        if (cliente.getNombreCompleto() == null || cliente.getNombreCompleto().isBlank()) {
            throw new NegocioException("El nombre completo es obligatorio.");
        }
        if (cliente.getContrasenia() == null || cliente.getContrasenia().isBlank()) {
            throw new NegocioException("La contraseña es obligatoria.");
        }
        if (cliente.getEdad() < 18) {
            throw new NegocioException("El cliente debe ser mayor de edad.");
        }
    }

    /**
     * Verifica las credenciales de un cliente por nombre y contraseña.
     *
     * @param nombreCompleto Nombre completo del cliente
     * @param contrasena Texto plano ingresado
     * @return ClienteDTO si las credenciales son correctas
     * @throws NegocioException si las credenciales son inválidas o hay un error
     */
    public ClienteDTO loginCliente(String nombreCompleto, String contrasena) throws NegocioException {
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new NegocioException("El nombre no puede estar vacío.");
        }

        if (contrasena == null || contrasena.isBlank()) {
            throw new NegocioException("La contraseña no puede estar vacía.");
        }

        try {
            Cliente cliente = clienteDAO.buscarPorNombre(nombreCompleto);
            if (cliente == null) {
                throw new NegocioException("Cliente no encontrado.");
            }

            if (!Encriptador.verificar(contrasena, cliente.getContrasenia())) {
                throw new NegocioException("Contraseña incorrecta.");
            }

            return ClienteConvertidor.entidadADTO(cliente);

        } catch (PersistenciaExcepcion e) {
            throw new NegocioException("Error al iniciar sesión", e);
        }
    }

}
