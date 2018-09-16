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
    
    private Long organizadorId;
    private String usuario;
    private String nombre;
    private Integer presupuesto;
    public Integer ganancia;
    
    public OrganizadorDTO()
    {
        
    }
    
    public OrganizadorDTO(OrganizadorEntity organizador )
    {
        this.usuario= organizador.getUsuario();
        this.nombre= organizador.getNombre();
        this.ganancia=organizador.getGanancia();
        presupuesto=organizador.getPresupuesto();
    }
    
    public OrganizadorEntity toEntity()
    {
        OrganizadorEntity entity= new OrganizadorEntity();
        entity.setUsuario(usuario);
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