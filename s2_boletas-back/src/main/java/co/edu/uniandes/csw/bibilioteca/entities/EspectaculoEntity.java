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
    private String nombre;
    
    private Date fecha;
    
    private String descripcion;
    
    private String artista;
    
    public void cambiarArtista(String artist)
    {
        artista = artist;
    }
    
    public String darArtista()
    {
        return artista;
    }
}
