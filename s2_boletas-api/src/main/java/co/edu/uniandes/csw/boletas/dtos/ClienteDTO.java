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


    private Long id;
    private String nombre;
    private String usuario;
    private String pago;
    private String cedula;
    
    public ClienteDTO()
    {
        
    }
    
    public ClienteDTO(ClienteEntity entity )
    {
        if(entity!=null)
        {
       id=entity.getId();
        nombre= entity.getNombre();
        usuario=entity.getUsuario();
        pago= entity.getPago();
        cedula=entity.getCedula();
        }
    }
    
    public ClienteEntity toEntity()
    {
        ClienteEntity entity= new ClienteEntity();
        entity.setId(this.id);
        entity.setPago(this.getPago());
        entity.setNombre(getNombre());
        entity.setUsuario(getUsuario());
        entity.setCedula(getCedula());
        return entity;
    }
    
        /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the pago
     */
    public String getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(String pago) {
        this.pago = pago;
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id=id;
    }
    
    
    
}