/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;

/**
 *
 * @author Vilma Tirado Gomez
 */
public class OrganizadorDTO implements Serializable  {
    
    public Long organizadorId;
    public String nombre;
    public String password;
    public Integer presupuesto;
    public Integer ganancia;
    
    public OrganizadorDTO()
    {
        
    }
    
    public OrganizadorDTO(OrganizadorEntity organizador )
    {
        nombre= organizador.getNombre();
        ganancia=organizador.getGanancia();
        presupuesto=organizador.getPresupuesto();
    }
    
    public OrganizadorEntity toEntity()
    {
        OrganizadorEntity entity= new OrganizadorEntity();
        entity.setNombre(nombre);
        entity.setGanancia(ganancia);
        entity.setPresupuesto(this.presupuesto);
        entity.setId(organizadorId);
        return entity;
        
    }
    
    
    
    
    
    
    
    public Long darId()
    {
        return organizadorId;
    }
    
    
    
}