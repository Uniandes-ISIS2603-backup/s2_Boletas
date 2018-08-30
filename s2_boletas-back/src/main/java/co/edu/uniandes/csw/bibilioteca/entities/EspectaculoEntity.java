/*                                              
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bibilioteca.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author Sebastian Rodriguez
 */
@Entity
public class EspectaculoEntity extends BaseEntity implements Serializable
{
    
    private Long id;
    
    private String nombre;
    
    private Date fecha;
    
    private String descripcion;
    
    private String artista;
    
    public Long darId()
    {
        return id;
    }
    
    public void cambiarId(Long nuevo)
    {
        id = nuevo;
    }
    
    public String darNombre()
    {
        return nombre;
    }
    
    public void cambiarNombre(String pNombre)
    {
        this.nombre = pNombre;
    }
    
    public Date darFecha()
    {
        return fecha;
    }
    
    public void cambiarFecha(Date date)
    {
        fecha = date;
    }
    
    public String darDescripcion()
    {
        return descripcion;
    }
    
    public void cambiarDescripcion(String descrip)
    {
        this.descripcion = descrip;
    }
    
    public void cambiarArtista(String artist)
    {
        artista = artist;
    }
    
    public String darArtista()
    {
        return artista;
    }
}
