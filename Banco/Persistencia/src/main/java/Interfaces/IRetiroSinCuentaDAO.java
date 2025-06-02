/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Entidades.RetiroSinCuenta;
import Excepcion.PersistenciaExcepcion;

/**
 *
 * @author SDavidLedesma
 */
public interface IRetiroSinCuentaDAO {

    RetiroSinCuenta registrarRetiro(RetiroSinCuenta retiro) throws PersistenciaExcepcion;

    RetiroSinCuenta buscarPorFolioYContrasena(int folio, String contrasena) throws PersistenciaExcepcion;

    void actualizarEstado(int folio, String nuevoEstado) throws PersistenciaExcepcion;

}
