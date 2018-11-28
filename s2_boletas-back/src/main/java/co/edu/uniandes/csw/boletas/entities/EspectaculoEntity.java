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
    
    private String imagen;

    
    /**
     * Relacion de uno a muchos con boletas, un espectaculo tiene muchas boletas
     */
    @PodamExclude
    @OneToMany(mappedBy = "espectaculo",cascade= CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "espectaculo")
    private List<BoletaEntity> boletas = new ArrayList<>(); 

    /**
     * Relacion de muchos a uno, un lugar puede tener muchos espectaculos
     * y un espectaculo tiene un solo lugar
     */
    @PodamExclude
    @ManyToOne()
    private LugarEntity lugar;
    
    
    /**
     * Relacion con comentarios, un espectaculo va a tener muchos comentarios
     * y cada comentario es sobre solo un espectaculo
     * Se define el cascade persist, en caso de eliminar el espectaculo sus 
     * comentarios tambien se eliminan
     */
    @PodamExclude
    @OneToMany(mappedBy = "espectaculo",cascade= CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "espectaculo")
    private List<ComentarioEntity> comentarios = new ArrayList<>();
    
    
    /**
     * Relacion de muchos a uno, un organizador tiene muchos espectaculos
     * y un espectaculo es hecho por un organizador
     */
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
    
    public LugarEntity getLugar()
    {
        return lugar;
    }
    
    public void setLugar(LugarEntity lugar)
    {
        this.lugar = lugar;
    }
    
    /**
    * Retorna 
    * @return la url de la imagen
    */
    public String getImagen()
    {
        return imagen;
    }
    
    public void setImagen(String imagen)
    {
        this.imagen = imagen;
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
