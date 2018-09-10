/*                                              
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Sebastian Rodriguez
 */
@Entity
public class EspectaculoEntity extends BaseEntity implements Serializable {
    
    

    private String nombre;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    private String descripcion;

    private String artista;
    
    private String tipo;

    
//    @PodamExclude
//    @OneToMany()
//    private Collection<BoletaEntity> boletas = new ArrayList<BoletaEntity>(); 
//
//    
//    @PodamExclude
//    @OneToOne(fetch = FetchType.LAZY)
//    private LugarEntity lugar;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date date) {
        fecha = date;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descrip) {
        this.descripcion = descrip;
    }

    public void setArtista(String artist) {
        artista = artist;
    }

    public String getArtista() {
        return artista;
    }
    
//    public List<BoletaEntity> getBoletas()
//    {
//        return boletas;
//    }
//    
//    public void setBoletas(List<BoletaEntity> boletas)
//    {
//        this.boletas = boletas;
//    }
    
//    public LugarEntity darLugar()
//    {
//        return lugar;
//    }
//    
//    public void setLugar(LugarEntity pLugar)
//    {
//        this.lugar = pLugar;
//    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
