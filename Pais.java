public class Pais {
    private long id;
    private String nome;
    private String Continente;
    private String populacao;
    
    public Pais() {}
    
    public Pais (long id, String n, String c, String p) {
        this.id = id;
        nome = n;
        Continente = c;
        populacao = p;
    }
    
    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getContinente() { return Continente
    public String getPopulacao() { return populacao; }
    
    public void setId(long id) { this.id = id; }
    public void setNome(String n) { nome = n; }
    public void setContinente(String C) {Continente = c;}
    public void setPopulacao(String p) { populacao = p; }
}