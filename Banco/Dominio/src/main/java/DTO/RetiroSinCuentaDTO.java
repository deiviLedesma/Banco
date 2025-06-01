/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import ENUM.Estado;
import java.time.LocalDateTime;

/**
 *
 * @author SDavidLedesma
 */
public class RetiroSinCuentaDTO {

    //atributos
    private int folio;
    private String contrasena;
    private LocalDateTime fechaSolicitud;
    private double monto;
    private Estado estado; // "Pendiente", "Cobrado", "NoCobrado"
    private int idCliente;
    private int idOperacion;

    /**
     * constructor por omision
     */
    public RetiroSinCuentaDTO() {
    }

    /**
     * constructor que incializa los atributos
     *
     * @param folio
     * @param contrasena
     * @param fechaSolicitud
     * @param monto
     * @param estado
     * @param idCliente
     * @param idOperacion
     */
    public RetiroSinCuentaDTO(int folio, String contrasena, LocalDateTime fechaSolicitud, double monto, Estado estado, int idCliente, int idOperacion) {
        this.folio = folio;
        this.contrasena = contrasena;
        this.fechaSolicitud = fechaSolicitud;
        this.monto = monto;
        this.estado = estado;
        this.idCliente = idCliente;
        this.idOperacion = idOperacion;
    }

    //getters y setters
    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    @Override
    public String toString() {
        return "RetiroSinCuentaDTO{" + "folio=" + folio + ", contrasena=" + contrasena + ", fechaSolicitud=" + fechaSolicitud + ", monto=" + monto + ", estado=" + estado + ", idCliente=" + idCliente + ", idOperacion=" + idOperacion + '}';
    }

}
