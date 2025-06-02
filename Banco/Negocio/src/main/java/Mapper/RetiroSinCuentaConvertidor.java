/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.RetiroSinCuentaDTO;
import Entidades.RetiroSinCuenta;

/**
 *
 * @author SDavidLedesma
 */
public class RetiroSinCuentaConvertidor {

    public static RetiroSinCuentaDTO entidadADTO(RetiroSinCuenta entidad) {
        if (entidad == null) {
            return null;
        }
        RetiroSinCuentaDTO dto = new RetiroSinCuentaDTO();
        dto.setFolio(entidad.getFolio());
        dto.setContrasena(entidad.getContrasena());
        dto.setFechaSolicitud(entidad.getFechaSolicitud());
        dto.setMonto(entidad.getMonto());
        dto.setEstado(entidad.getEstado());
        dto.setIdCliente(entidad.getIdCliente());
        dto.setIdOperacion(entidad.getIdOperacion());
        return dto;
    }

    public static RetiroSinCuenta dtoAEntidad(RetiroSinCuentaDTO dto) {
        if (dto == null) {
            return null;
        }
        RetiroSinCuenta entidad = new RetiroSinCuenta();
        entidad.setFolio(dto.getFolio());
        entidad.setContrasena(dto.getContrasena());
        entidad.setFechaSolicitud(dto.getFechaSolicitud());
        entidad.setMonto(dto.getMonto());
        entidad.setEstado(dto.getEstado());
        entidad.setIdCliente(dto.getIdCliente());
        entidad.setIdOperacion(dto.getIdOperacion());
        return entidad;
    }
}
