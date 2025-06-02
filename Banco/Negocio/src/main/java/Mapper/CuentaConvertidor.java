/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.CuentaDTO;
import Entidades.Cuenta;

/**
 *
 * @author SDavidLedesma
 */
public class CuentaConvertidor {

    public static CuentaDTO entidadADTO(Cuenta entidad) {
        if (entidad == null) {
            return null;
        }
        CuentaDTO dto = new CuentaDTO();
        dto.setId(entidad.getId());
        dto.setNumeroCuenta(entidad.getNumeroCuenta());
        dto.setFechaApertura(entidad.getFechaApertura());
        dto.setSaldo(entidad.getSaldo());
        dto.setActiva(entidad.isActiva());
        dto.setIdCliente(entidad.getIdCliente());
        return dto;
    }

    public static Cuenta dtoAEntidad(CuentaDTO dto) {
        if (dto == null) {
            return null;
        }
        Cuenta entidad = new Cuenta();
        entidad.setId(dto.getId());
        entidad.setNumeroCuenta(dto.getNumeroCuenta());
        entidad.setFechaApertura(dto.getFechaApertura());
        entidad.setSaldo(dto.getSaldo());
        entidad.setActiva(dto.isActiva());
        entidad.setIdCliente(dto.getIdCliente());
        return entidad;
    }
}
