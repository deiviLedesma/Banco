/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.OperacionDTO;
import NegocioException.NegocioException;

/**
 *
 * @author SDavidLedesma
 */
public interface IOperacionBO {

    OperacionDTO registrarTransferencia(OperacionDTO operacion) throws NegocioException;

    void validarTransferencia(OperacionDTO operacion) throws NegocioException;

}
