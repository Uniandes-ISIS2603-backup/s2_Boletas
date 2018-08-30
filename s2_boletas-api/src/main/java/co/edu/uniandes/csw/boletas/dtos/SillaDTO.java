/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.bibilioteca.entities.SillaEntity;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**
 *
 * @author Jhonatan Amórtegui
 */
@Path("sillas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SillaDTO implements Serializable{
    private Long id;
    private String numero;
    private String tipo;
    
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
}
