/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.dtos.ClienteDTO;
import co.edu.uniandes.csw.boletas.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.ClienteLogic;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Vilma Tirado Gomez
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource 
{
   
    private static final String RECURSO = "El Recurso /clientes/ ";
    private static final String EXISTE = " /no existe";
    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());
    
    //Representacion de la clase ClienteLogic. Es una injeccion de dependencias
    
    @Inject
    ClienteLogic logica;
    
    @POST
    public ClienteDTO createCliente(ClienteDTO cliente) throws BusinessLogicException
    { 
        
        LOGGER.log(Level.INFO,"ClienteResourse createCliente: input: {0}" , cliente);
        
        //Lo primero que se hace es pasar el DTO  a entity ya que la logica solo conoce entities
        ClienteEntity entity=cliente.toEntity();
        //Despues se le pasa el entity a la logica la cual lo guarda en persistencia 
        //y persistencia le da un id 
        ClienteEntity nuevoCliente= logica.createCliente(entity);
        
        //Una vez creada la entity en la aplicacion esta se puede pasar nuevamente a DTO 
        return new ClienteDTO(nuevoCliente);
          
    }
    
    @PUT
    @Path("{clienteId : \\d+}")
    public ClienteDetailDTO updateCliente(@PathParam("clienteId") Long clienteId, ClienteDetailDTO cliente)
    {
           LOGGER.log(Level.INFO, "Proceso de actualizar un cliente: PUT");
           LOGGER.log(Level.INFO, "ClienteResource  updateCliente: input: id:{0} , cliente: {1}", new Object[]{clienteId, cliente});
        cliente.setId(clienteId);
         if(logica.getCliente(clienteId)==null)
         {
              throw new WebApplicationException(RECURSO + clienteId +EXISTE,404);
         }
         ClienteEntity actualizado= logica.updateCliente(clienteId,cliente.toEntity());
         return new ClienteDetailDTO(actualizado);
        
    }
    
    @GET
    @Path("{clienteId : \\d+}")
    public ClienteDetailDTO getCliente(@PathParam("clienteId") Long clienteId) 
    {
        //Se busca el entity que se quiere modificar
        ClienteEntity entity = logica.getCliente(clienteId);
        //Si no existe se manda excepcion
        if(entity==null)
        {
            throw new WebApplicationException(RECURSO+ clienteId+ EXISTE,404);
        }
        //Si existe se modifica y se vuelve DTO 
        return new ClienteDetailDTO(entity);
        
        
                
        
    }
    
    @GET 
    public List<ClienteDetailDTO> getClientes()
    {
         return listEntity2DetailDTO(logica.getClientes());
        
    }
    
     /* * Este método conecta la ruta de /clientes con las rutas de /compras que
     * dependen del cliente, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las compras  de un cliente.
     *
     * @param clientesId El ID de la clientes con respecto a la cual se
     * accede al servicio.
     * @return El servicio de compras para este cliente en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @Path("{clienteId: \\d+}/compras")
    public Class<ClienteCompraResource> getClienteCompraResource(@PathParam("clienteId") Long clienteId) {
        if (logica.getCliente(clienteId) == null) {
            throw new WebApplicationException(RECURSO + clienteId + EXISTE, 404);
        }
        return ClienteCompraResource.class;
        
    }
    
         /* * Este método conecta la ruta de /clientes con las rutas de /comentarios que
     * dependen del cliente, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los comentarios  de un cliente.
     *
     * @param clientesId El ID de la clientes con respecto a la cual se
     * accede al servicio.
     * @return El servicio de comentarios para este cliente en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la cliente.
     */
    @Path("{clienteId: \\d+}/comentarios")
    public Class<ClienteComentariosResource> getClienteComentariosResource(@PathParam("clienteId") Long clienteId) {
        if (logica.getCliente(clienteId) == null) {
            throw new WebApplicationException(RECURSO + clienteId + EXISTE, 404);
        }
        return ClienteComentariosResource.class;
        
    }
    
    @DELETE
    @Path("{clienteId: \\d+}")
    public void deleteCliente(@PathParam("clienteId") Long clienteId) 
    { 
        logica.delete(clienteId);
    }
    
        private List<ClienteDetailDTO> listEntity2DetailDTO(List<ClienteEntity> entityList) {
       List<ClienteDetailDTO> list = new ArrayList<>();
       for (ClienteEntity entity : entityList) {
           list.add(new ClienteDetailDTO(entity));
       }
       return list;
    }
}