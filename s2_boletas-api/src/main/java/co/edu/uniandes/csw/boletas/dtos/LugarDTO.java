/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.bibilioteca.entities.LugarEntity;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Jhonatan Amórtegui
 */
 
public class LugarDTO implements Serializable {
    private Long id;
    private Integer numSillas; 
    private String direccion;
    private String nombre;
    
    public LugarDTO(LugarEntity entity)
    {
        this.id = entity.getId();
        //Falta Ubicación
        this.numSillas = entity.getNumSillas();
        this.direccion = entity.getDireccion();
        this.nombre = entity.getNombre();
    }
    
    public LugarEntity toEntity()
    {
        LugarEntity lugarEntity = new LugarEntity();
        lugarEntity.setId(this.id);
        //Falta Ubicación
        lugarEntity.setNumSillas(this.numSillas);
        lugarEntity.setDireccion(this.direccion);
        lugarEntity.setNombre(this.nombre);
        return lugarEntity;
    }
}
