/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import java.io.Serializable;


/**
 *
 * @author ja.amortegui10
 */
 
public class LugarDTO implements Serializable {
    private Long id;
    private Integer numSillas; 
    private String direccion;
    private String nombre;
    private String ubicacion;

   
    /**
     * Constructor vacío
     */
    public LugarDTO()
    {
        
    }
    
    /**
     * Constructor con parámetros
     * @param entity 
     */
    public LugarDTO(LugarEntity entity)
    {
        this.id = entity.getId();
        this.numSillas = entity.getNumSillas();
        this.direccion = entity.getDireccion();
        this.nombre = entity.getNombre();
        this.ubicacion = entity.getUbicacion();
    }
    
    /**
     * Convertir de dto a entity.
     * @return 
     */
    public LugarEntity toEntity()
    {
        LugarEntity lugarEntity = new LugarEntity();
        lugarEntity.setId(this.id);
        lugarEntity.setNumSillas(this.numSillas);
        lugarEntity.setDireccion(this.direccion);
        lugarEntity.setNombre(this.nombre);
        lugarEntity.setUbicacion(this.ubicacion);
        return lugarEntity;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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
    
     public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    
}
