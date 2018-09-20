/*                                              
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import co.edu.uniandes.csw.boletas.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Sebastian Rodriguez
 */
@Entity
public class EspectaculoEntity extends BaseEntity implements Serializable {
    
    

    private String nombre;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;

    private String descripcion;

    private String artista;
    
    private String tipo;

    
    @PodamExclude
    @OneToMany(mappedBy = "espectaculo",cascade= CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "espectaculo")
    private List<BoletaEntity> boletas = new ArrayList<>(); 

    
    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private LugarEntity lugar;
    
    @PodamExclude
    @OneToMany(mappedBy = "espectaculo",cascade= CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "espectaculo")
    private List<ComentarioEntity> comentarios = new ArrayList<>();
    
    @PodamExclude
    @javax.persistence.ManyToOne()
    private OrganizadorEntity organizador;
    
    
    /**
     * Metodo que devuelve el nombre del espectaculo
     * @return El nombre del espectaculo
     */
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
    
    public List<BoletaEntity> getBoletas()
    {
        return boletas;
    }
    
    public void setBoletas(List<BoletaEntity> boletas)
    {
        this.boletas = boletas;
    }
    
    public LugarEntity darLugar()
    {
        return lugar;
    }
    
    public void setLugar(LugarEntity pLugar)
    {
        this.lugar = pLugar;
    }

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

    /**
     * @return the organizador
     */
    public OrganizadorEntity getOrganizador() {
        return organizador;
    }

    /**
     * @param organizador the organizador to set
     */
    public void setOrganizador(OrganizadorEntity organizador) {
        this.organizador = organizador;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }
    
}
