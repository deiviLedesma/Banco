/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.ClienteDTO;
import Entidades.Cliente;

/**
 *
 * @author SDavidLedesma
 */
public class ClienteConvertidor {

    public static ClienteDTO entidadADTO(Cliente entidad) {
        return new ClienteDTO(
                entidad.getId(),
                entidad.getNombreCompleto(),
                entidad.getDomicilio(),
                entidad.getFechaNacimiento(),
                entidad.getEdad(),
                entidad.getContrasenia()
        );
    }

    public static Cliente dtoAEntidad(ClienteDTO dto) {
        Cliente entidad = new Cliente();
        entidad.setId(dto.getId());
        entidad.setNombreCompleto(dto.getNombreCompleto());
        entidad.setEdad(dto.getEdad());
        entidad.setFechaNacimiento(dto.getFechaNacimiento());
        entidad.setDomicilio(dto.getDomicilio());
        entidad.setContrasenia(dto.getContrasenia());
        return entidad;
    }
}
