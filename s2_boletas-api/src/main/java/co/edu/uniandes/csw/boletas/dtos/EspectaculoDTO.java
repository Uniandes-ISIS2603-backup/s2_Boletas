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
    public Date fecha;
    
    public String descripcion;
    
    public String artista;
    
    public EspectaculoDTO()
    {
        
    }
    
    public EspectaculoDTO(EspectaculoEntity espectaculo)
    {
        
    }
    
    public EspectaculoEntity toEntity()
    {
        return null;
    }
    
}
