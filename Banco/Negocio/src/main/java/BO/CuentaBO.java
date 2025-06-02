/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.CuentaDAO;
import DTO.CuentaDTO;
import Entidades.Cuenta;
import Excepcion.PersistenciaExcepcion;
import Interfaces.ICuentaBO;
import Interfaces.ICuentaDAO;
import Mapper.CuentaConvertidor;
import NegocioException.NegocioException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SDavidLedesma
 */
public class CuentaBO implements ICuentaBO {

    private final ICuentaDAO cuentaDAO;

    public CuentaBO() {
        this.cuentaDAO = new CuentaDAO();
    }

    //constructor adicional para pruebas
    public CuentaBO(ICuentaDAO cuentaDAO) {
        this.cuentaDAO = cuentaDAO;
    }

    @Override
    public CuentaDTO registrarCuenta(CuentaDTO cuenta) throws NegocioException {
        validarCuenta(cuenta);

        cuenta.setNumeroCuenta(generarNumeroCuentaUnico());
        cuenta.setFechaApertura(LocalDate.now());
        cuenta.setActiva(true);

        try {
            Cuenta entidad = CuentaConvertidor.dtoAEntidad(cuenta);
            cuentaDAO.insertar(entidad);
            cuenta.setId(entidad.getId());
            return cuenta;
        } catch (PersistenciaExcepcion e) {
            throw new NegocioException("Error al registrar la cuenta.", e);
        }
    }

    @Override
    public void actualizarSaldo(int idCuenta, double nuevoSaldo) throws NegocioException {
        if (nuevoSaldo < 0) {
            throw new NegocioException("El saldo no puede ser negativo.");
        }

        try {
            cuentaDAO.actualizarSaldo(idCuenta, nuevoSaldo);
        } catch (PersistenciaExcepcion e) {
            throw new NegocioException("Error al actualizar el saldo.", e);
        }
    }

    @Override
    public void cancelarCuenta(int idCuenta) throws NegocioException {
        try {
            cuentaDAO.cancelarCuenta(idCuenta);
        } catch (PersistenciaExcepcion e) {
            throw new NegocioException("Error al cancelar la cuenta.", e);
        }
    }

    @Override
    public List<CuentaDTO> obtenerCuentasPorCliente(int idCliente) throws NegocioException {
        try {
            List<Cuenta> entidades = cuentaDAO.buscarCuentasPorCliente(idCliente);
            List<CuentaDTO> dtos = new ArrayList<>();
            for (Cuenta entidad : entidades) {
                dtos.add(CuentaConvertidor.entidadADTO(entidad));
            }
            return dtos;
        } catch (PersistenciaExcepcion ex) {
            throw new NegocioException("Error al obtener cuentas por cliente", ex);
        }
    }

    /**
     * Valida los datos de la cuenta antes de su registro.
     */
    private void validarCuenta(CuentaDTO cuenta) throws NegocioException {
        if (cuenta.getIdCliente() <= 0) {
            throw new NegocioException("ID de cliente inválido.");
        }
        if (cuenta.getSaldo() < 0) {
            throw new NegocioException("El saldo inicial no puede ser negativo.");
        }
        if (cuenta.getNumeroCuenta() != null) {
            throw new NegocioException("El número de cuenta será generado automáticamente.");
        }
    }

    /**
     * Genera un número de cuenta aleatorio y único.
     */
    private String generarNumeroCuentaUnico() throws NegocioException {
        int intentos = 0;
        while (intentos < 10) {
            String numero = String.format("%010d", (int) (Math.random() * 1_000_000_000));
            try {
                if (cuentaDAO.buscarPorNumeroCuenta(numero) == null) {
                    return numero;
                }
            } catch (PersistenciaExcepcion e) {
                throw new NegocioException("Error al verificar número de cuenta único.", e);
            }
            intentos++;
        }
        throw new NegocioException("No se pudo generar un número de cuenta único.");
    }
}
