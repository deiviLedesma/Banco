/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Operacion;
import Enumss.TipoOperacion;
import Excepcion.PersistenciaExcepcion;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public interface IOperacionDAO {

    Operacion registrarOperacion(Operacion operacion) throws PersistenciaExcepcion;

    List<Operacion> buscarOperacionesPorCuenta(int idCuenta) throws PersistenciaExcepcion;

    List<Operacion> buscarOperacionesPorTipoYFechas(int idCuenta, TipoOperacion tipo, LocalDateTime inicio, LocalDateTime fin) throws PersistenciaExcepcion;

}
