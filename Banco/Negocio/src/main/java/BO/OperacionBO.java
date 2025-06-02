/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.OperacionDAO;
import DTO.OperacionDTO;
import Entidades.Operacion;
import Enumss.TipoOperacion;
import Excepcion.PersistenciaExcepcion;
import Interfaces.IOperacionBO;
import Interfaces.IOperacionDAO;
import Mapper.OperacionConvertidor;
import NegocioException.NegocioException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SDavidLedesma
 */
public class OperacionBO implements IOperacionBO {

    private final IOperacionDAO operacionDAO;

    public OperacionBO() {
        this.operacionDAO = new OperacionDAO();
    }

    //constructor adicional para pruebas
    public OperacionBO(IOperacionDAO operacionDAO) {
        this.operacionDAO = operacionDAO;
    }

    @Override
    public OperacionDTO registrarTransferencia(OperacionDTO operacion) throws NegocioException {
        validarTransferencia(operacion);

        operacion.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
        operacion.setFechaHora(LocalDateTime.now());

        try {
            Operacion entidad = OperacionConvertidor.dtoAEntidad(operacion);
            operacionDAO.registrarOperacion(entidad);
            operacion.setId(entidad.getId());
            return operacion;
        } catch (PersistenciaExcepcion e) {
            throw new NegocioException("Error al registrar la transferencia", e);
        }
    }

    @Override
    public void validarTransferencia(OperacionDTO operacion) throws NegocioException {
        if (operacion.getIdCuentaOrigen() == 0 || operacion.getIdCuentaDestino() == 0) {
            throw new NegocioException("Se deben indicar ambas cuentas.");
        }
        if (operacion.getIdCuentaOrigen() == operacion.getIdCuentaDestino()) {
            throw new NegocioException("La cuenta origen y destino no pueden ser iguales.");
        }
        if (operacion.getMonto() <= 0) {
            throw new NegocioException("El monto debe ser mayor a cero.");
        }
    }

}
