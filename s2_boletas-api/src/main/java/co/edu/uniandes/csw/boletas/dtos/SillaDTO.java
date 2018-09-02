/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import java.io.Serializable;



/**
 *
 * @author Jhonatan Amórtegui
 */
public class SillaDTO implements Serializable{
    private Long id;
    private String numero;
    
    public SillaDTO()
    {
        
    }
    
    public SillaDTO(SillaEntity entity)
    {
        this.id = entity.getId();
        this.numero = entity.getNumero();
        //Me falta implementar lo de tipo
    }
    
    public SillaEntity toEntity()
    {
        SillaEntity sillaEntity = new SillaEntity();
        sillaEntity.setId(id);
        sillaEntity.setNumero(numero);
        //Me falta el tipo
        return sillaEntity;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    
}
