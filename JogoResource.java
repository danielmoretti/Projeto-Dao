import javax.ws.rs.*;
import javax.ws.rs.core.*;
import io.dropwizard.jersey.*;
import io.dropwizard.jersey.params.*;
import java.util.*;

@Path("/jogos")
@Produces(MediaType.APPLICATION_JSON)
public class JogoResource {    

    private List<Jogo> jogos;
    private long proximoId;
    
    public JogoResource() {
        proximoId = 1;
        jogos = new ArrayList<>();
        jogos.add(new Jogo(proximoId++, "São Paulo", "Corinthians", 1, 2));
        jogos.add(new Jogo(proximoId++, "Santos", "Fluminense", 4, 1));
        jogos.add(new Jogo(proximoId++, "Flamengo", "Palmeiras", 2, 3));
    }
    
    @GET
    public List<Jogo> read() {
        return jogos;
    }

}