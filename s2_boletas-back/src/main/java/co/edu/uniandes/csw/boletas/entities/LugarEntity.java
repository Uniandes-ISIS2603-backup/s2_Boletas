/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author ja.amortegui10
 */
@Entity
public class LugarEntity extends BaseEntity implements Serializable{
    
    public enum Ubicacion{
        ESTADIO,
        TEATRO
    }
    
    @Id
    private Long id;
    private Ubicacion ubicacion;
    private Integer numSillas;
    private String direccion;    
    private String nombre;

    
    public void setId(Long id) {
        this.id = id;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setNumSillas(Integer numSillas) {
        this.numSillas = numSillas;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public Integer getNumSillas() {
        return numSillas;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombre() {
        return nombre;
    }
    
}
