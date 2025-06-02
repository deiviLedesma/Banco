/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDate;

/**
 * Entidad Cuenta que representa una cueneta en el sistema bancario
 *
 * @author SDavidLedesma
 */
public class Cuenta {

    //atributos
    private int id;
    private String numeroCuenta;
    private LocalDate fechaApertura;
    private double saldo;
    private boolean activa;
    private int idCliente;

    /**
     * constructor por omision
     */
    public Cuenta() {
    }

    /**
     * constructor que inicializa los atributos
     *
     * @param id
     * @param numeroCuenta
     * @param fechaApertura
     * @param saldo
     * @param activa
     * @param idCliente
     */
    public Cuenta(int id, String numeroCuenta, LocalDate fechaApertura, double saldo, boolean activa, int idCliente) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.activa = activa;
        this.idCliente = idCliente;
    }

    public Cuenta(String numeroCuenta, LocalDate fechaApertura, double saldo, boolean activa, int idCliente) {
        this.numeroCuenta = numeroCuenta;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.activa = activa;
        this.idCliente = idCliente;
    }

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "CuentaDTO{" + "id=" + id + ", numeroCuenta=" + numeroCuenta + ", fechaApertura=" + fechaApertura + ", saldo=" + saldo + ", activa=" + activa + ", idCliente=" + idCliente + '}';
    }

}
