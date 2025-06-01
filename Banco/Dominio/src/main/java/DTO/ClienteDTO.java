/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;

/**
 * DTO para la entidad cliente
 *
 * @author SDavidLedesma
 */
public class ClienteDTO {

    //Atributos
    private int id;
    private String nombreCompleto;
    private String domicilio;
    private LocalDate fechaNacimiento;
    private int edad;
    private String contrasenia;

    /**
     * constructor por omision
     */
    public ClienteDTO() {
    }

    /**
     * constructor  que incializa los atributos
     * @param id
     * @param nombreCompleto
     * @param domicilio
     * @param fechaNacimiento
     * @param edad
     * @param contrasenia 
     */
    public ClienteDTO(int id, String nombreCompleto, String domicilio, LocalDate fechaNacimiento, int edad, String contrasenia) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.domicilio = domicilio;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.contrasenia = contrasenia;
    }

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" + "id=" + id + ", nombreCompleto=" + nombreCompleto + ", domicilio=" + domicilio + ", fechaNacimiento=" + fechaNacimiento + ", edad=" + edad + ", contrasenia=" + contrasenia + '}';
    }

}
