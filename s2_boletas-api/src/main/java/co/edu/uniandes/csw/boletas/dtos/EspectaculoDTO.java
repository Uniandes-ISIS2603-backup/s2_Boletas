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
<<<<<<< HEAD
 * @author Sebastian Rodriguez
=======
 * @author Sebastian Rodriguez Beltran
>>>>>>> master
 */
public class EspectaculoDTO implements Serializable
{
    
    private Long espectaculoId;
    
    private String nombre;
    
    private Date fecha;
    
    private String descripcion;
    
    private String artista;
    
    /**
     * Espectaculo tiene una asociacion, de modo que tiene un lugar asignado
     */
    
    private LugarDTO lugar;
    
    
    public EspectaculoDTO()
    {
        
    }
    
    public EspectaculoDTO(EspectaculoEntity espectaculo)
    {
        if (espectaculo != null)
        {
            espectaculoId = espectaculo.getId();
            
            nombre = espectaculo.darNombre();
        
            artista = espectaculo.darArtista();
        
            fecha = espectaculo.darFecha();
        
            descripcion = espectaculo.darDescripcion();  
            
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
    
    public EspectaculoEntity toEntity()
    {
        EspectaculoEntity espectaculo = new EspectaculoEntity();
        
        espectaculo.cambiarNombre(this.nombre);
        
        espectaculo.cambiarArtista(this.artista);
        
        espectaculo.cambiarDescripcion(this.descripcion);
        
        espectaculo.cambiarFecha(this.fecha);
        
        if(lugar != null)
        {
            espectaculo.setLugar(lugar.toEntity());
        }
        
        return espectaculo;
    }
    
    public Long getId()
    {
        return espectaculoId;
    }
    
    public void cambiarId(Long id)
    {
        espectaculoId = id;
    }
    
    public String getName()
    {
        return nombre;
    }
    
    public void cambiarNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public Date getDate()
    {
        return fecha;
    }
    
    public void cambiarFecha(Date pFecha)
    {
        this.fecha = pFecha;
    }
    
    public String getDescription()
    {
        return descripcion;
    }
    
    public void cambiarDescripcion(String description)
    {
        this.descripcion = description;
    }
    
    public String getArtist()
    {
        return artista;
    }
    
    public void cambiarArtista(String artist)
    {
        this.artista = artist;
    }
    
    public LugarDTO darLugar()
    {
        return lugar;
    }
    
    public void cambiarLugar(LugarDTO lugar)
    {
        this.lugar = lugar;
    }
    
     @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
