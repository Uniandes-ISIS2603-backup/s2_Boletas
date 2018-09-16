/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Vilma Tirado Gomez
 */
@Entity
public class OrganizadorEntity extends BaseEntity implements Serializable{
    
    @OneToMany(mappedBy= "organizador", cascade=CascadeType.PERSIST, orphanRemoval=true)
    private List<EspectaculoEntity> espectaculos= new ArrayList<>();
    
   
    private String usuario;
    private String nombre;
    private Integer  presupuesto;
    private Integer ganancia;
    
    
    public String getUsuario()
    {
        return usuario;
    }
    public String getNombre()
    {
        return nombre;
    }
    public Integer getPresupuesto()
    {
        return presupuesto;
    }
    
    public Integer  getGanancia()
    {
        return ganancia;
    }
    
    public List<EspectaculoEntity> getEspectaculos()
    {
        return espectaculos;
    }
    
    public void setUsuario(String usuario)
    {
        this.usuario=usuario;
    }
    
    public void setNombre(String nom)
    {
        this.nombre=nom;
    }
    
    public void setGanancia(Integer ganancia)
    {
        this.ganancia= ganancia;
    }
    public void setPresupuesto (Integer presup)
    {
        this.presupuesto=presup;
    }
    
    public void setEspectaculos (List<EspectaculoEntity> espectaculos)
    {
        this.espectaculos=espectaculos;
    }
    
}