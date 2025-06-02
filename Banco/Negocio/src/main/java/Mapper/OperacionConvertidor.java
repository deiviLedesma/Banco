/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.OperacionDTO;
import Entidades.Operacion;

/**
 *
 * @author SDavidLedesma
 */
public class OperacionConvertidor {

    public static OperacionDTO entidadADTO(Operacion entidad) {
        if (entidad == null) {
            return null;
        }
        OperacionDTO dto = new OperacionDTO();
        dto.setId(entidad.getId());
        dto.setTipoOperacion(entidad.getTipoOperacion());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setMonto(entidad.getMonto());
        dto.setIdCuentaOrigen(entidad.getIdCuentaOrigen());
        dto.setIdCuentaDestino(entidad.getIdCuentaDestino());
        return dto;
    }

    public static Operacion dtoAEntidad(OperacionDTO dto) {
        if (dto == null) {
            return null;
        }
        Operacion entidad = new Operacion();
        entidad.setId(dto.getId());
        entidad.setTipoOperacion(dto.getTipoOperacion());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setMonto(dto.getMonto());
        entidad.setIdCuentaOrigen(dto.getIdCuentaOrigen());
        entidad.setIdCuentaDestino(dto.getIdCuentaDestino());
        return entidad;
    }
}
