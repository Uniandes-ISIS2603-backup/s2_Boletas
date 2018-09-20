/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
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
    /**
     * Relacion con el objeto BoletaDTO, espectaculo contiene una lista de 
     * boletas
     * 
     */
    private List<BoletaDTO> boletas;
    
    
    /**
     * Relacion de espectaculo con ComentarioDTO, espectaculo tiene una lista
     * de comentarios respecto a este
     */
    private List<ComentarioDTO> comentarios;
    
    
    /**
     * Constructor vacio de la clase
     */
    public EspectaculoDetailDTO()
    {
       
    }
    
    
    /**
     * Constructor del EspectaculoDetailDTO
     * Primero hace un super de su superclase EspectaculoDTO, pasandole el mismo
     * objeto como parametro
     * @param espectaculo Un espectaculoEntity, con toda la informacion necesaria
     * para hacer el objeto DTO
     */
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
            if(espectaculo.getComentarios() != null)
            {
                comentarios = new ArrayList<>();
                for(ComentarioEntity bol: espectaculo.getComentarios())
                {
                    comentarios.add(new ComentarioDTO(bol));
                }
            }
        }
    }
    
    
    /**
     * Devulve la lista de boletas asociadas a el espectaculo
     * @return 
     */
    public List<BoletaDTO> getBoletas()
    {
        return boletas;
    }
    
    /**
     * Establece la lista de boletas asociadas a un espectaculo
     * @param boletas, una lista con objetos de tipo BoletaDTO
     */
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
        if(comentarios != null )
        {
            List<ComentarioEntity> comentarioEntity = new ArrayList<>();
            for (ComentarioDTO dtoComentario : comentarios) {
                comentarioEntity.add(dtoComentario.toEntity());
            }
            espectaculoEntity.setComentarios(comentarioEntity);
        }
        return espectaculoEntity;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }
    
    /**
     * Establece un conjunto de comentarios hacia el espectaculo
     * @param comentarios 
     */
    public void setComentarios(List<ComentarioDTO> comentarios)
    {
        this.comentarios = comentarios;
    }
}
