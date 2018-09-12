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
    
    private String tipo;
    
    /**
     * Espectaculo tiene una asociacion, de modo que tiene un lugar asignado
     */
    
 //   private LugarDTO lugar;
    
    
    public EspectaculoDTO()
    {
        
    }
    
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
            
//            if(espectaculo.darLugar() != null)
//            {
//                this.lugar = new LugarDTO(espectaculo.darLugar());
//            }
//            else 
//            {
//                lugar = null;
//            }
              
            
        }
        
    }
    
    public EspectaculoEntity toEntity()
    {
        EspectaculoEntity espectaculo = new EspectaculoEntity();
        
        espectaculo.setNombre(this.nombre);
        
        espectaculo.setArtista(this.artista);
        
        espectaculo.setDescripcion(this.descripcion);
        
        espectaculo.setFecha(this.fecha);
        
        espectaculo.setTipo(tipo);
        
//        if(lugar != null)
//        {
//            espectaculo.setLugar(lugar.toEntity());
//        }
        
        return espectaculo;
    }
    
    public Long getId()
    {
        return espectaculoId;
    }
    
    public void setId(Long id)
    {
        espectaculoId = id;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public Date getFecha()
    {
        return fecha;
    }
    
    public void setFecha(Date pFecha)
    {
        this.fecha = pFecha;
    }
    
    public String getDescripcion()
    {
        return descripcion;
    }
    
    public void setDescripcion(String description)
    {
        this.descripcion = description;
    }
    
    public String getArtista()
    {
        return artista;
    }
    
    public void setArtista(String artist)
    {
        this.artista = artist;
    }
    
//    public LugarDTO darLugar()
//    {
//        return lugar;
//    }
//    
//    public void cambiarLugar(LugarDTO lugar)
//    {
//        this.lugar = lugar;
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
    
     @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
