/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import DAO.RetiroSinCuentaDAO;
import DTO.RetiroSinCuentaDTO;
import Entidades.RetiroSinCuenta;
import Enumss.Estado;
import Excepcion.PersistenciaExcepcion;
import Interfaces.IRetiroSinCuentaBO;
import Interfaces.IRetiroSinCuentaDAO;
import Mapper.RetiroSinCuentaConvertidor;
import NegocioException.NegocioException;
import java.time.LocalDateTime;

/**
 *
 * @author SDavidLedesma
 */
public class RetiroSinCuentaBO implements IRetiroSinCuentaBO {

    private final IRetiroSinCuentaDAO retiroDAO;

    public RetiroSinCuentaBO() {
        this.retiroDAO = new RetiroSinCuentaDAO();
    }
    
     // Constructor adicional para pruebas
    public RetiroSinCuentaBO(IRetiroSinCuentaDAO retiroDAO) {
        this.retiroDAO = retiroDAO;
    }

    @Override
    public RetiroSinCuentaDTO registrarRetiro(RetiroSinCuentaDTO retiroDTO) throws NegocioException {
        if (retiroDTO.getMonto() <= 0) {
            throw new NegocioException("El monto debe ser mayor a cero.");
        }
        if (retiroDTO.getContrasena() == null || retiroDTO.getContrasena().isBlank()) {
            throw new NegocioException("La contraseña es obligatoria.");
        }

        retiroDTO.setFechaSolicitud(LocalDateTime.now());
        retiroDTO.setEstado(Estado.PENDIENTE);

        try {
            RetiroSinCuenta entidad = RetiroSinCuentaConvertidor.dtoAEntidad(retiroDTO);
            retiroDAO.registrarRetiro(entidad);
            retiroDTO.setFolio(entidad.getFolio());
            return retiroDTO;
        } catch (PersistenciaExcepcion e) {
            throw new NegocioException("Error al registrar retiro sin cuenta", e);
        }
    }

    @Override
    public void cobrarRetiro(int folio, String contrasena) throws NegocioException {
        try {
            RetiroSinCuenta retiro = retiroDAO.buscarPorFolioYContrasena(folio, contrasena);
            if (retiro == null) {
                throw new NegocioException("Folio o contraseña incorrectos.");
            }
            if (!retiro.getEstado().equals(Estado.PENDIENTE)) {
                throw new NegocioException("Este retiro ya fue cobrado o no está disponible.");
            }

            retiroDAO.actualizarEstado(folio, "COBRADO");
        } catch (PersistenciaExcepcion e) {
            throw new NegocioException("Error al cobrar el retiro", e);
        }
    }
}
