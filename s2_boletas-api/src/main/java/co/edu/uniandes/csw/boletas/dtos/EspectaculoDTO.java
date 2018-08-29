/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.bibilioteca.entities.EspectaculoEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class EspectaculoDTO implements Serializable
{
    
    public Long espectaculoId;
    
    public String nombre;
    
    public Date fecha;
    
    public String descripcion;
    
    public String artista;
    
    public EspectaculoDTO()
    {
        
    }
    
    public EspectaculoDTO(EspectaculoEntity espectaculo)
    {
        if (espectaculo != null)
        {
            espectaculoId = espectaculo.darId();
            
            nombre = espectaculo.darNombre();
        
            artista = espectaculo.darArtista();
        
            fecha = espectaculo.darFecha();
        
            descripcion = espectaculo.darDescripcion();  
        }
        
    }
    
    public EspectaculoEntity toEntity()
    {
        EspectaculoEntity espectaculo = new EspectaculoEntity();
        
        espectaculo.cambiarNombre(this.nombre);
        
        espectaculo.cambiarArtista(this.artista);
        
        espectaculo.cambiarDescripcion(this.descripcion);
        
        espectaculo.cambiarFecha(this.fecha);
        
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
    
     @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
