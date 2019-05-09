public class Jogo {
    private long id;
    private String nomea;
    private String nomeb;
    private int golsa;
    private int golsb;
    
    public Jogos() {
        
        }
    
    public Jogo(long id, String na, String nb, int ga, int gb) {
        this.id = id;
        nomea = na;
        nomeb = nb;
        golsa = ga
        golsb = gb;
    }
    
    public long getId() { return id; }
    public String getNomea() { return nomea; }
    public String getNomeb() { return nomeb; }
    public int getGolsa() { return golsa; }
    public int getGolsb() { return golsb; }
    
    
    public void setId(long id) { this.id = id; }
    public void setNomea(String na) { nome = na; }
    public void setNomeb(String nb) { nome = nb; }
    public void setGolsa(int ga) { golsa = ga; }
    public void setGolsb(int gb) { golsa = gb; }
}