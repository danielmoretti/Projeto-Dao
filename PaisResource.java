import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.*;
import io.dropwizard.jersey.params.*;
import java.util.*;

@Path("/Pais")
@Produces(MediaType.APPLICATION_JSON)
public class PaisResource {    

    private List<Pais> pais;
    private long proximoId;
    
    public PaisResource() {
        proximoId = 1;
        paises = new ArrayList<>();
        paises.add(new Pais(proximoId++, "Brasil", "América do sul", "209 milhões" ));
        paises.add(new Pais(proximoId++, "Estados Unidos", "América do Norte", "327 milhões"));
        paises.add(new Pais(proximoId++, "China", "Ásia",".6 bilhões" ));
    }
    
    @GET
    public List<Pais> read() {
        return paises;
    }

}