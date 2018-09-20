/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.ejb.OrganizadorEspectaculoLogic;
import co.edu.uniandes.csw.boletas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.boletas.dtos.EspectaculoDTO;
import co.edu.uniandes.csw.boletas.dtos.EspectaculoDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el subrecurso /organizadores/{id}/espectaculos
 * @author Vilma Tirado y Sebastian Rodriguez 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrganizadorEspectaculoResourse 
{
    private static final Logger LOGGER = Logger.getLogger(OrganizadorEspectaculoResourse.class.getName());

   @Inject
   private OrganizadorEspectaculoLogic organizadorEspectaculoLogic;

    @Inject
    private EspectaculoLogic espectaculoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private OrganizadorLogic comentarioLogic;
    
    
    
    @POST
    @Path("{espectaculosId: \\d+}")
    public EspectaculoDTO addEspectaculo(@PathParam("organizadorId") Long organizadoresId, @PathParam("espectaculoId") Long espectaculosId)
    {
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResourse addEspectaculo: input: organizadorId: "+ organizadoresId +", espectaculosId: " + espectaculosId, new Object[]{organizadoresId, espectaculosId});
        if (espectaculoLogic.getEspectaculo(espectaculosId) == null) {
            throw new WebApplicationException("El recurso /espectaculos" + espectaculosId + " no existe.", 404);
        }
        EspectaculoDTO espectaculoDTO = new EspectaculoDTO(organizadorEspectaculoLogic.addEspectaculo(espectaculosId, organizadoresId));
//        ComentarioDTO comentarioDTO = new ComentarioDTO(espectaculoComentarioLogic.addComentario(comentariosId, espectaculosId));
//        LOGGER.log(Level.INFO, "EspectaculoComentarioResourse agregarComentario: output: {0}", comentarioDTO.toString());
        return new EspectaculoDTO();
    }
    
    @GET
    @Path("{espectaculosId: \\+d}")
    public EspectaculoDetailDTO getEspectaculo (@PathParam("espectaculosid") Long espectaculosId, @PathParam("organizadoresId") Long organizadoresId) throws BusinessLogicException      
    {
        if (espectaculoLogic.getEspectaculo(espectaculosId)==null)
            throw new WebApplicationException("El recurso organizadores/"+ organizadoresId+ "/espectaculos / "+ espectaculosId + "No existe",404);
        EspectaculoDetailDTO espectaculoDetailDto= new EspectaculoDetailDTO(organizadorEspectaculoLogic.getEspectaculo(organizadoresId, espectaculosId));
        
        return espectaculoDetailDto;
    }
    
    
        /**
     * Busca y devuelve todos los espectaculos que estan asociados al organizador.
     *
     * @param organizadorId Identificador del organizador que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link EspectaculoDetailDTO} - Los espectaculos asociados al
     * organizador. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET
    public List<EspectaculoDetailDTO> getEspectaculos (@PathParam("espectaculosId") Long espectaculosId)
    {
        List<EspectaculoDetailDTO> listaDetailDTO= espectaculosListEntity2DTO(organizadorEspectaculoLogic.getEspectaculos(espectaculosId));
        return listaDetailDTO;
        
    }
    
        /**
     * Convierte una lista de espectaculoEntity  a una lista de EspectaculoDetailDTO.
     *
     * @param entityList Lista de espectaculoEntity a convertir.
     * @return Lista de espectaculoDTO convertida.
     */
    
    public List<EspectaculoDetailDTO> espectaculosListEntity2DTO(List<EspectaculoEntity> entityList)
    {
        List<EspectaculoDetailDTO> lista = new ArrayList();
        for (EspectaculoEntity entity: entityList)
        {
            lista.add(new EspectaculoDetailDTO(entity));
        }
        return lista;
    }
    
    
    
        /**
     * Convierte una lista de EspectaculoDetailDTO a una lista de EspectaculoEntity.
     *
     * @param dtos Lista de EspectaculoDetailDTO a convertir.
     * @return Lista de EspectaculoEntity convertida.
     */
    
    public List<EspectaculoEntity> espectaculoListDTO2Entity( List<EspectaculoDetailDTO> dtos)
    {
        List<EspectaculoEntity> lista= new ArrayList();
        for (EspectaculoDetailDTO dto: dtos)
        {
            lista.add(dto.toEntity());
        }
        return lista;
        
    }
    
    
    
}
