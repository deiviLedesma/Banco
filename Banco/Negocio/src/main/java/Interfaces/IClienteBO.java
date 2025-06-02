/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Cliente;
import NegocioException.NegocioException;

/**
 *
 * @author SDavidLedesma
 */
public interface IClienteBO {

    Cliente registrarCliente(Cliente cliente) throws NegocioException;

}
