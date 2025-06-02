/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.ClienteDTO;
import NegocioException.NegocioException;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public interface IClienteBO {

    ClienteDTO registrarCliente(ClienteDTO cliente) throws NegocioException;

    ClienteDTO actualizarCliente(ClienteDTO cliente) throws NegocioException;

    ClienteDTO buscarClientePorId(int id) throws NegocioException;

    List<ClienteDTO> obtenerTodosLosClientes() throws NegocioException;

    public ClienteDTO loginCliente(String nombreCompleto, String contrasena) throws NegocioException;

}
