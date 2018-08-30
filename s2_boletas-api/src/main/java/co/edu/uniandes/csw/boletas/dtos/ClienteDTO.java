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
    public String password;
    public String pago;
    public Long clienteId;
    
    public ClienteDTO()
    {
        
    }
    
    public ClienteDTO(ClienteEntity entity )
    {
        nombre= entity.darNombre();
        password= entity.darPassword();
        pago= entity.darPago();
        clienteId=entity.darId();
    }
    
    public ClienteEntity toEntity()
    {
        ClienteEntity entity= new ClienteEntity();
        entity.setPago(this.pago);
        entity.setNombre(nombre);
        entity.setPassword(password);
        return entity;
    }
    
    public Long darId()
    {
        return clienteId;
    }
    
}