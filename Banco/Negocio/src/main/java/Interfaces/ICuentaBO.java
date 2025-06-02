/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.CuentaDTO;
import NegocioException.NegocioException;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public interface ICuentaBO {

    CuentaDTO registrarCuenta(CuentaDTO cuenta) throws NegocioException;

    void actualizarSaldo(int idCuenta, double nuevoSaldo) throws NegocioException;

    void cancelarCuenta(int idCuenta) throws NegocioException;

    List<CuentaDTO> obtenerCuentasPorCliente(int idCliente) throws NegocioException;

}
