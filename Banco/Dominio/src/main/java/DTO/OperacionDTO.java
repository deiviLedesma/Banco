/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import ENUM.TipoOperacion;
import java.time.LocalDateTime;

/**
 *
 * @author SDavidLedesma
 */
public class OperacionDTO {

    //Atributos
    private int id;
    private TipoOperacion tipoOperacion; // "Transferencia" o "RetiroSinCuenta"
    private LocalDateTime fechaHora;
    private double monto;
    private int idCuentaOrigen;
    private int idCuentaDestino;

    /**
     * constructor por omisino
     */
    public OperacionDTO() {
    }

    /**
     * constructor que inicializa los atributos
     *
     * @param id
     * @param tipoOperacion
     * @param fechaHora
     * @param monto
     * @param idCuentaOrigen
     * @param idCuentaDestino
     */
    public OperacionDTO(int id, TipoOperacion tipoOperacion, LocalDateTime fechaHora, double monto, int idCuentaOrigen, int idCuentaDestino) {
        this.id = id;
        this.tipoOperacion = tipoOperacion;
        this.fechaHora = fechaHora;
        this.monto = monto;
        this.idCuentaOrigen = idCuentaOrigen;
        this.idCuentaDestino = idCuentaDestino;
    }

    //getter y setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdCuentaOrigen() {
        return idCuentaOrigen;
    }

    public void setIdCuentaOrigen(int idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
    }

    public int getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(int idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    @Override
    public String toString() {
        return "OperacionDTO{" + "id=" + id + ", tipoOperacion=" + tipoOperacion + ", fechaHora=" + fechaHora + ", monto=" + monto + ", idCuentaOrigen=" + idCuentaOrigen + ", idCuentaDestino=" + idCuentaDestino + '}';
    }

}
