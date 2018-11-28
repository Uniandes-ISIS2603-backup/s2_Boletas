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
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ja.amortegui10
 */
@Entity
public class LugarEntity extends BaseEntity implements Serializable{
    
    private String ubicacion;
    
    private Integer numSillas;
    private String direccion;    
    private String nombre;
    
    @PodamExclude
    @OneToMany(mappedBy="lugar", fetch = FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
    private List<SillaEntity> sillas = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(mappedBy="lugar", fetch = FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
    private List<EspectaculoEntity> espectaculos;
 
    public List<EspectaculoEntity> getEspectaculos() {
        return espectaculos;
    }

    public void setEspectaculos(List<EspectaculoEntity> espectaculos) {
        this.espectaculos = espectaculos;
    }
    
   
    public void setSillas(List<SillaEntity> sillas) {
        this.sillas = sillas;
    }

    public List<SillaEntity> getSillas() {
        return sillas;
    }
    

    public void setNumSillas(Integer numSillas) {
        this.numSillas = numSillas;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumSillas() {
        return numSillas;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    

    
}
