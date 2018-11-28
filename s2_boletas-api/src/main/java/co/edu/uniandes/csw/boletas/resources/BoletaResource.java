/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.BoletaDTO;
import co.edu.uniandes.csw.boletas.ejb.BoletaLogic;
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;



/**
 * Clase que representa el recurso boleta
 * @author Diego Camacho
 */
@Path("boletas")
@Produces("application/json")
@Consumes("application/json")
public class BoletaResource {
    
    /**
     * Dependencia de la lógica, conexión con la persistencia
     */
    @Inject 
    private BoletaLogic boletaLogic;
    
    private static final String RECURSO = "El Recurso /boletas/ ";
    private static final String EXISTE = " no existe";
    
    private static final Logger LOGGER = Logger.getLogger(BoletaResource.class.getName());
    
    /**
     * Crea una nueva boleta con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param boleta {@link BoletaDTO} - La boleta que se desea guardar.
     * @return JSON {@link BoletaTO} - La boleta guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la boleta 
     */
    @POST
    public BoletaDTO postBoleta(BoletaDTO boleta) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "BoletaResource postBoleta: input: {0}", boleta);
        BoletaEntity boletaEntity = boleta.toEntity();
        BoletaEntity nuevaBoletaEntity = boletaLogic.createBoleta(boletaEntity);
        BoletaDTO nuevaBoletaDto =new BoletaDTO(nuevaBoletaEntity);
        LOGGER.log(Level.INFO, "BoletaResource createBoleta: output: {0}", nuevaBoletaDto);
        return nuevaBoletaDto;
    }
    
    /**
     * Busca y devuelve todas las boletas que existen en la aplicacion.
     *
     * @return JSONArray {@link BoletaDTO} - Las boletas encontradas en la
     * aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<BoletaDTO> getBoletas() {
        LOGGER.info("BoletaResource getBoletas: input: void");
        List<BoletaDTO> listaBoletas = listEntity2DTO(boletaLogic.getBoletas());
        LOGGER.log(Level.INFO, "BoletaResource getBoletas: output: {0}", listaBoletas);
        return listaBoletas;
    }
    
    /**
     * Busca la boleta con el id asociado recibido en la URL y lo devuelve.
     *
     * @param boletasId Identificador de la boleta que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BoletaDTO} - La boleta buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     */
    @GET
    @Path("{boletasId : \\d+}")
    public BoletaDTO getBoleta(@PathParam("boletasId") Long boletasId) 
    {
        LOGGER.log(Level.INFO,"BoletaResource getBoleta: input: {0}", boletasId);
        BoletaEntity boletaEntity = boletaLogic.getBoleta(boletasId);
        if(boletaEntity == null)
        {
            throw new WebApplicationException(RECURSO+boletasId+EXISTE, 404);
        }
        BoletaDTO boletaDTO = new BoletaDTO(boletaEntity);
        LOGGER.log(Level.INFO, "BoletaResource getBoleta: output: {0}", boletasId);
        return boletaDTO;
    }
    
    /**
     * Actualiza la boleta con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param boletasId Identificador de la boleta que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param boleta {@link BoletaDTO} La boleta que se desea guardar.
     * @return JSON {@link BoletaDTO} - La boleta guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar la boleta.
     */
    @PUT
    @Path("{boletasId: \\d+}")
    public BoletaDTO updateBoleta(@PathParam("boletasId") Long boletasId, BoletaDTO boleta)
    {
        LOGGER.log(Level.INFO, "BoletaResource updateBoleta: input: id:{0} , boleta: {1}", new Object[]{boletasId, boleta});
        boleta.setId(boletasId);
        if (boletaLogic.getBoleta(boletasId) == null) {
            throw new WebApplicationException(RECURSO + boletasId + EXISTE, 404);
        }
        BoletaDTO boletaDTO = new BoletaDTO(boletaLogic.updateBoleta(boletasId, boleta.toEntity()));
        LOGGER.log(Level.INFO, "BoletaResource updateBoleta: output: {0}", boletaDTO);
        return boletaDTO;
    }
    
    /**
     * Borra la boleta con el id asociado recibido en la URL.
     *
     * @param boletasId Identificador de la boleta que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     */
    @DELETE
    @Path("{boletasId: \\d+}")
    public void deleteBoleta(@PathParam("boletasId") Long boletasId) 
    { 
        LOGGER.log(Level.INFO, "BoletaResource deleteBoleta: input: {0}", boletasId);
        if (boletaLogic.getBoleta(boletasId) == null) {
            throw new WebApplicationException(RECURSO + boletasId + EXISTE, 404);
        }
        boletaLogic.deleteBoleta(boletasId);
        LOGGER.info("BoletaResource deleteBoleta: output: void");
    }
    
    
    
      /**
     * Conexión con el servicio de compra para una boleta.
     * {@link BoletaCompraResource}
     *
     * Este método conecta la ruta de /boletas con las rutas de /compras que
     * dependen de la boleta, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de la compra de una boleta.
     *
     * @param boletaId El ID de la boleta con respecto a la cual se
     * accede al servicio.
     * @return El servicio de compra para esta boleta en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     */
    @Path("{boletasId: \\d+}/compras")
    public Class<BoletaCompraResource> getBoletaCompraResource(@PathParam("boletasId") Long boletasId) {
        if (boletaLogic.getBoleta(boletasId) == null) {
            throw new WebApplicationException(RECURSO + boletasId + EXISTE, 404);
        }
        return BoletaCompraResource.class;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos BoletaEntity a una lista de
     * objetos BoletaDTO (json)
     *
     * @param entityList corresponde a la lista de libros de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de boletas en forma DTO (json)
     */
    private List<BoletaDTO> listEntity2DTO(List<BoletaEntity> entityList) {
        List<BoletaDTO> list = new ArrayList<>();
        for (BoletaEntity entity : entityList) {
            list.add(new BoletaDTO(entity));
        }
        return list;
    }
    
}
