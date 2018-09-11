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
 * @author ja.amortegui10
 */
public class SillaDTO implements Serializable{
    private Long id;
    private String numero;
    private String tipo;

    
    /**
     * Constructor vacío
     */
    public SillaDTO()
    {
        
    }
    
    /**
     * Constructor con parámetros.
     * @param entity 
     */
    public SillaDTO(SillaEntity entity)
    {
        this.id = entity.getId();
        this.numero = entity.getNumero();
        this.tipo = entity.getTipo();
    }
    
    /**
     * Transformar a entity.
     * @return 
     */
    public SillaEntity toEntity()
    {
        SillaEntity sillaEntity = new SillaEntity();
        sillaEntity.setId(this.id);
        sillaEntity.setNumero(this.numero);
        sillaEntity.setTipo(this.tipo);;
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
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
    
}
