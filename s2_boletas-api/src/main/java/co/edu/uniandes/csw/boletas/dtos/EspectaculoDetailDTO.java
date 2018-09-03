/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian Rodriguez 
 */
public class EspectaculoDetailDTO extends EspectaculoDTO implements Serializable
{
    
    private List<BoletaDTO> boletas;
    
    public EspectaculoDetailDTO()
    {
       
    }
    
    public EspectaculoDetailDTO(EspectaculoEntity espectaculo)
    {
        super(espectaculo);
        if(espectaculo != null)
        {
            if(espectaculo.getBoletas() != null)
            {
                boletas = new ArrayList<>();
                for(BoletaEntity bol: espectaculo.getBoletas())
                {
                    boletas.add(new BoletaDTO(bol));
                }
            }
        }
    }
    
    public List<BoletaDTO> getBoletas()
    {
        return boletas;
    }
    
    public void setBoletas(List<BoletaDTO> boletas)
    {
        this.boletas = boletas;
    }
    
    @Override
    public EspectaculoEntity toEntity() {
        EspectaculoEntity espectaculoEntity = super.toEntity();
        if (boletas != null) {
            List<BoletaEntity> booksEntity = new ArrayList<>();
            for (BoletaDTO dtoBook : boletas) {
                booksEntity.add(dtoBook.toEntity());
            }
            espectaculoEntity.setBoletas(booksEntity);
        }
        return espectaculoEntity;
    }
}
