/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.boletas.entities.ClienteEntity;

/**
 *
 * @author Vilma Tirado Gomez
 */

public class ClienteDTO implements Serializable {
    
    public String nombre;
     private String usuario;
    public String pago;
    private String cedula;
    
    public ClienteDTO()
    {
        
    }
    
    public ClienteDTO(ClienteEntity entity )
    {
        nombre= entity.getNombre();
        usuario=entity.getUsuario();
        pago= entity.getPago();
        cedula=entity.getCedula();
    }
    
    public ClienteEntity toEntity()
    {
        ClienteEntity entity= new ClienteEntity();
        entity.setPago(this.pago);
        entity.setNombre(nombre);
        entity.setUsuario(usuario);
        entity.setCedula(cedula);
        return entity;
    }
    
}