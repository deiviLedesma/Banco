/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Cuenta;
import Excepcion.PersistenciaExcepcion;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public interface ICuentaDAO {

    Cuenta insertar(Cuenta cuenta) throws PersistenciaExcepcion;

    void actualizarSaldo(int idCuenta, double nuevoSaldo) throws PersistenciaExcepcion;

    void cancelarCuenta(int idCuenta) throws PersistenciaExcepcion;

    Cuenta buscarPorNumeroCuenta(String numero) throws PersistenciaExcepcion;

    List<Cuenta> buscarCuentasPorCliente(int idCliente) throws PersistenciaExcepcion;

}
