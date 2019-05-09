import java.sql.*;
import java.util.*;

public class JogosDao {
    private final static String sqlCreateTable = "CREATE TABLE jogos " 
        + "(id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
        + "nomea VARCHAR(100) NOT NULL,"
		+ "nomeb VARCHAR(100) NOT NULL,"
        + "golsa INT NOT NULL,"
		+ "golsb INT NOT NULL,"
        + "PRIMARY KEY (id))";
	private final String sqlC = "INSERT INTO jogos (nomea, nomeb, golsa, golsb) VALUES (?,?,?,?)";
	private final String sqlR = "SELECT * FROM jogos";
	private final String sqlU = "UPDATE jogos SET nomea=?, nomeb=?, golsa=?, golsb=? WHERE id=?";
	private final String sqlD = "DELETE FROM jogos WHERE id=?";	
	private final String sqlRById = "SELECT * FROM jogos WHERE id=?";
	private PreparedStatement stmC;
	private PreparedStatement stmR;
	private PreparedStatement stmU;
	private PreparedStatement stmD;
	private PreparedStatement stmRById;
	public JogosDao(ConexaoJavaDb conexao) throws DaoException, ConexaoException {
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
    
	public long create(Jogo p) throws DaoException {
		long id = 0;
		try {
			stmC.setString(1, p.getNomea());
			stmC.setString(2, p.getNomeb());
			stmC.setInt(3, p.getGolsa());
			stmC.setInt(4, p.getGolsb());
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
	public List<Jogo> read() throws DaoException {
		List<Jogo> jogos = new ArrayList<>();
		try {
			ResultSet rs = stmR.executeQuery();
			while(rs.next()) {
				long id = rs.getLong("id");
				String nomea = rs.getString("nome");
				String nomeb = rs.getString("nome");
				int golsa = rs.getInt("matricula");
				int golsb = rs.getInt("matricula");
				Jogo p = new Professo(id, nomea, nomeb,golsa, golsb);
				jogos.add(p);
			}
			rs.close();
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao ler registros: " + ex.getMessage());
		}
		return jogos;
	}
	public void update(Jogo p) throws DaoException {
		try {
			stmU.setString(1, p.getNomea());
			stmU.setString(2, p.getNomeb());
			stmU.setInt(3, p.getGolsa());
			stmU.setInt(4, p.getGolsb());
			stmU.setLong(5, p.getId());
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

	public Jogo readById(long id) throws DaoException {
		Jogo p = null;

		try {
			stmRById.setLong(1, id);
			ResultSet rs = stmRById.executeQuery();
			if (rs.next()) {
				int golsa = rs.getInt("matricula");
				int golsb = rs.getInt("matricula");
				String nomea = rs.getString("nome");
				String nomeb = rs.getString("nome");
				p = new Jogo(id,nomea,nomeb,golsa,golsb);
			}
		} catch(SQLException ex) {
            ex.printStackTrace();
			throw new DaoException("Falha ao buscar pelo id: " + ex.getMessage());
		}
		return p;
	}

}