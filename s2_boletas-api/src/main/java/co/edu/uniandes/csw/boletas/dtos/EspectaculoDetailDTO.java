/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.bibilioteca.entities.EspectaculoEntity;
import java.io.Serializable;

/**
 *
 * @author Sebastian Rodriguez 
 */
public class EspectaculoDetailDTO extends EspectaculoDTO implements Serializable
{
    public EspectaculoDetailDTO()
    {
       
    }
    
    public EspectaculoDetailDTO(EspectaculoEntity espectaculo)
    {
        super(espectaculo);
    }
}
