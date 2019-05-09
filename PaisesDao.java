import java.sql.*;
import java.util.*;

public class PaisesDao {
    private final static String sqlCreateTable = "CREATE TABLE paises " 
        + "(id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
        + "nome VARCHAR(100) NOT NULL,"
		+ "continente VARCHAR(100) NOT NULL,"
        + "populacao VARCHAR(100) NOT NULL,"
        + "PRIMARY KEY (id))";
	private final String sqlC = "INSERT INTO paises (nome, continente, populacao) VALUES (?,?,?)";
	private final String sqlR = "SELECT * FROM paises";
	private final String sqlU = "UPDATE paises SET nome=?, continente=? , populacao=? WHERE id=?";
	private final String sqlD = "DELETE FROM paises WHERE id=?";	
	private final String sqlRById = "SELECT * FROM paises WHERE id=?";
	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmU;
	private PreparedStatement stmD;
	private PreparedStatement stmRById;
	public PaisesDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
		try {
			Connection con = conexao.getConnection();
            try {
                Statement stm = con.createStatement();
                stm.execute(sqlCreateTable);
                System.out.println("Tabela criada com sucesso!");
            } catch( SQLException ex ) {
                System.out.println("Tabela já existe!");
            }           
			stmC = con.prepareStatement(sqlC, Statement.RETURN_GENERATED_KEYS);
			stmR = con.prepareStatement(sqlR);
			stmU = con.prepareStatement(sqlU);
			stmD = con.prepareStatement(sqlD);
			stmRById = con.prepareStatement(sqlRById);
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao preparar statement: " + ex.getMessage());
		}
	}
    
	public long create(Pais p) throws DaoException {
		long id = 0;
		try {
			stmC.setString(1, p.getNome());
			stmC.setString(2, p.getContinente());
			stmC.setString(3, p.getpopulacao());
			int r = stmC.executeUpdate();
			ResultSet rs = stmC.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao criar registro: " + ex.getMessage());
		}
		return id;
	}
	public List<Pais> read() throws DaoException {
		List<Pais> paisess = new ArrayList<>();
		try {
			ResultSet rs = stmR.executeQuery();
			while(rs.next()) {
				long id = rs.getLong("id");
				String nome = rs.getString("nome");
				String continente = rs.getString("continente");
				String populacao = rs.getString("populacao");
				Pais p = new Pais(id, nome, continente, populacao);
				paises.add(p);
			}
			rs.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao ler registros: " + ex.getMessage());
		}
		return paisess;
	}
	public void update(Pais p) throws DaoException {
		try {
			stmU.setString(1, p.getNome());
			stmU.setString(2, p.getContinente());
			stmU.setString(3, p.getPopulacao());
			stmU.setLong(4, p.getId());
			int r = stmU.executeUpdate();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao atualizar registro: " + ex.getMessage());
		}
	}
	public void delete(long id) throws DaoException {
		try {
			stmD.setLong(1, id);
			int r = stmD.executeUpdate();
		} catch(SQLException ex) {
			throw new DaoException("Falha ao apagar registro: " + ex.getMessage());
		}
	}
	public void close() throws DaoException {
		try {
			stmC.close();
			stmR.close();
			stmU.close();
			stmD.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao fechar DAO: " + ex.getMessage());
		}
	}	 

	public Pais readById(long id) throws DaoException {
		Pais p = null;

		try {
			stmRById.setLong(1, id);
			ResultSet rs = stmRById.executeQuery();
			if (rs.next()) {
				String populacao = rs.getString("populacao");
				String continente = rs.getString("continente");
				String nome = rs.getString("nome");
				p = new Professor(id,nome,continente, populacao);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return p;
	}

}