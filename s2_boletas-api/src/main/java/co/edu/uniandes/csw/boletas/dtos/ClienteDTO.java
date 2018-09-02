/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.bibilioteca.entities.ClienteEntity;

/**
 *
 * @author estudiante
 */

public class ClienteDTO implements Serializable {
    
    public String nombre;
     private String usuario;
    public String password;
    public String pago;
    private int cedula;
    
    public Long clienteId;
    
    public ClienteDTO()
    {
        
    }
    
    public ClienteDTO(ClienteEntity entity )
    {
        nombre= entity.getNombre();
        usuario=entity.getUsuario();
        password= entity.getPassword();
        pago= entity.getPago();
        cedula=entity.getCedula();
        clienteId=entity.getId();
    }
    
    public ClienteEntity toEntity()
    {
        ClienteEntity entity= new ClienteEntity();
        entity.setPago(this.pago);
        entity.setNombre(nombre);
        entity.setPassword(password);
        entity.setUsuario(usuario);
        entity.setCedula(cedula);
        return entity;
    }
    
    public Long darId()
    {
        return clienteId;
    }
    
}