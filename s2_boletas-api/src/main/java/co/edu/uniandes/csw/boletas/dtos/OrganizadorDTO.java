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

   
    private Long id;
    private String usuario;
    private String nombre;
    private Integer presupuesto;
    private Integer ganancia;
    
    public OrganizadorDTO()
    {
        
    }
    
    public OrganizadorDTO(OrganizadorEntity organizador )
    {
        if(organizador != null)
        {
            this.usuario= organizador.getUsuario();
            this.nombre= organizador.getNombre();
            this.ganancia=organizador.getGanancia();
            this.presupuesto=organizador.getPresupuesto();
            this.id = organizador.getId();
        }
        
    }
    
    public OrganizadorEntity toEntity()
    {
        OrganizadorEntity entity= new OrganizadorEntity();
        entity.setUsuario(usuario);
        entity.setNombre(nombre);
        entity.setGanancia(ganancia);
        entity.setPresupuesto(presupuesto);
        entity.setId(id);
        return entity;
        
    }
    
   
    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getPresupuesto() {
        return presupuesto;
    }

    public Integer getGanancia() {
        return ganancia;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPresupuesto(Integer presupuesto) {
        this.presupuesto = presupuesto;
    }

    public void setGanancia(Integer ganancia) {
        this.ganancia = ganancia;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
}