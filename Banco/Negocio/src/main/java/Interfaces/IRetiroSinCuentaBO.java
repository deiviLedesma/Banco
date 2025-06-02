/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import DTO.RetiroSinCuentaDTO;
import NegocioException.NegocioException;

/**
 *
 * @author SDavidLedesma
 */
public interface IRetiroSinCuentaBO {

    /**
     * Registra un nuevo retiro sin cuenta.
     *
     * @param retiro DTO con los datos del retiro
     * @return Retiro registrado con folio asignado
     * @throws NegocioException si hay validaciones o errores de persistencia
     */
    RetiroSinCuentaDTO registrarRetiro(RetiroSinCuentaDTO retiro) throws NegocioException;

    /**
     * Cobra un retiro sin cuenta utilizando el folio y contraseña.
     *
     * @param folio Folio del retiro
     * @param contrasenia Contraseña para validar el retiro
     * @throws NegocioException si los datos no coinciden o el retiro no es
     * válido
     */
    void cobrarRetiro(int folio, String contrasenia) throws NegocioException;

}
