/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.bibilioteca.entities.EspectaculoEntity;
import java.io.Serializable;
import java.util.Date;

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
        artista = espectaculo.darArtista();
    }
    
    public EspectaculoEntity toEntity()
    {
        EspectaculoEntity espectaculo = new EspectaculoEntity();
        
        espectaculo.cambiarArtista(this.artista);
        
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
    
}
