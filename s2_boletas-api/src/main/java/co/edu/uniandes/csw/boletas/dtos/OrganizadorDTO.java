/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.bibilioteca.entities.OrganizadorEntity;

/**
 *
 * @author estudiante
 */
public class OrganizadorDTO implements Serializable  {
    
    public Long organizadorId;
    public String nombre;
    public String password;
    public int presupuesto;
    public int ganancia;
    
    public OrganizadorDTO()
    {
        
    }
    
    public OrganizadorDTO(OrganizadorEntity organizador )
    {
        nombre= organizador.darNombre();
        password= organizador.darPassword();
        ganancia=organizador.darGanancia();
        presupuesto=organizador.darPresupuesto();
    }
    
    public OrganizadorEntity toEntity()
    {
        OrganizadorEntity entity= new OrganizadorEntity();
        entity.cambiarGanancia(this.ganancia);
        entity.cambiarPresupuesto(this.presupuesto);
        return entity;
        
    }
    
    public Long darId()
    {
        return organizadorId;
    }
    
    public String darNombre()
    {
        return nombre;
    }
    
    public String darPassword()
    {
        return password;
    }
    
    public int darPresupueto()
    {
        return presupuesto;
    }
    
    public int darGanancia()
    {
       return ganancia;
    }
    
    
}