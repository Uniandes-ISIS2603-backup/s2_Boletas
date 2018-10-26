/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Sebastian Rodriguez Beltran
 */
public class EspectaculoDTO implements Serializable
{

    //Atributos
    
    /**
     * Atributo que da el id de un espectaculo, este lo define la base de datos
     * cuando se persiste
     */
    private Long espectaculoId;
    
    private String nombre;
    
    private Date fecha;
    
    private String descripcion;
    
    private String artista;
    
    private String tipo;
    
    private String imagen;
    
    /**
     * Espectaculo tiene una asociacion, de modo que tiene un lugar asignado
     */
    
    private LugarDTO lugar;
    
    /**
     * Constructor vacio de la clase
     */
    public EspectaculoDTO()
    {
        
    }
    
    
    /**
     * Constructor de la clase, que le entra un objeto EspectaculoEntity
     * y pasa todos sus datos al DTO
     * @param espectaculo EspectaculoEntity, contiene los valores de los atributos
     */
    public EspectaculoDTO(EspectaculoEntity espectaculo)
    {
        if (espectaculo != null)
        {
            espectaculoId = espectaculo.getId();
            
            nombre = espectaculo.getNombre();
        
            artista = espectaculo.getArtista();
        
            fecha = espectaculo.getFecha();
        
            descripcion = espectaculo.getDescripcion();
            
            tipo = espectaculo.getTipo();
            
            imagen = espectaculo.getImagen();
            
            if(espectaculo.darLugar() != null)
            {
                this.lugar = new LugarDTO(espectaculo.darLugar());
            }
            else 
            {
                lugar = null;
            }
              
            
        }
        
    }
    
    
    /**
     * Metodo para convertir de un EspectaculoDTO a un espectaculoEntity
     * Lo que hace es pasar todos los valores a un nuevo objeto de tipo 
     * EspectaculoEntity
     * @return Un objeto EspectaculoEntity
     */
    public EspectaculoEntity toEntity()
    {
        EspectaculoEntity espectaculo = new EspectaculoEntity();
        
        espectaculo.setId(this.espectaculoId);
        
        espectaculo.setNombre(this.nombre);
        
        espectaculo.setArtista(this.artista);
        
        espectaculo.setDescripcion(this.descripcion);
        
        espectaculo.setFecha(this.fecha);
        
        espectaculo.setTipo(this.tipo);
        
        espectaculo.setImagen(this.imagen);
        
        if(lugar != null)
        {
            espectaculo.setLugar(lugar.toEntity());
        }
        else
            espectaculo.setLugar(null);
        
        return espectaculo;
    }
    
    
    /**
     * El id del espectaculo
     * @return 
     */
    public Long getId()
    {
        return espectaculoId;
    }
    
    
    /**
     * Establece cual es el id del DTO, este no es establecido por el usuario
     * sino que cuando se persiste, se agrega
     * @param id 
     */
    public void setId(Long id)
    {
        espectaculoId = id;
    }
    
    
    /**
     * @return Retorna un string con el nombre del espectaculo
     */
    public String getNombre()
    {
        return nombre;
    }
    
    
    /**
     * 
     * @param nombre, Retorna el nombre del espectaculo
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    
    /**
     * @return fecha, Retorna un objeto de tipo Date, con la fecha, 
     * y hora en la que se va a hacer el espectaculo
     */
    public Date getFecha()
    {
        return fecha;
    }
    
    
    /**
     * 
     * @param pFecha, Se define la fecha en la que va a suceder el espectaculo
     */
    public void setFecha(Date pFecha)
    {
        this.fecha = pFecha;
    }
    
    
    /**
     * @return Retorna un mensaje con la informacion del espectaculo, su descripcion
     */
    public String getDescripcion()
    {
        return descripcion;
    }
    
    /**
     * @param description Establece cual es la descripcion sobre un espectaculo
     */
    public void setDescripcion(String description)
    {
        this.descripcion = description;
    }
    
    
    /**
     * @return Artista, el nombre del artista o grupo
     */
    public String getArtista()
    {
        return artista;
    }
    
    
    /**
     * @param artist Un string con el nombre del artista
     */
    public void setArtista(String artist)
    {
        this.artista = artist;
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
    
     @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the lugar
     */
    public LugarDTO getLugar() {
        return lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(LugarDTO lugar) {
        this.lugar = lugar;
    }
    
    /**
     * @return Metodo para dar la url de la imagen 
     */
    public String getImagen()
    {
        return imagen;
    }
    
    /**
     * Metodo que inserta la url de la imagen al espectaculo
     * @param imagen 
     */
    public void setImagen(String imagen)
    {
        this.imagen = imagen;
    }
}
