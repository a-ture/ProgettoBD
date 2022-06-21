package operazioni;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import javax.swing.JOptionPane;



public class InserisciOrdine {

	public int inserisciOridneCliente(Date data, String tipo, String stato, String id) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO ordinecliente(data,tipo,stato,idcliente)" + "VALUES (?,?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setDate(1, data);
			ps.setString(2, tipo);
			ps.setString(3, stato);
			ps.setString(4, id);

			risposta = ps.executeUpdate();

			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
		return risposta;
	}

	public int torvaCodiceOrdineCliente() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement();

			String sql = "SELECT codice FROM ordineCliente WHERE codice >= ALL (SELECT codice FROM ordinecliente)";

			rs = st.executeQuery(sql);

			rs.next();
			i = rs.getInt("codice");

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
		return i;
	}

	public int inserisciOridneFornitore(Date data, String id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO ordinefornitore(data,idFornitore) " + "VALUES (?,?)";

			ps = con.prepareStatement(sql);

			ps.setDate(1, data);
			ps.setString(2, id);

			risposta = ps.executeUpdate();
			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
		return risposta;
	}

	public int trovaCodiceOridneFornitore() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int i = 0;
		try {
			con = DBConnectionPool.getConnection();

			st = con.createStatement();

			String sql = "SELECT codice  FROM ordinefornitore WHERE codice >= ALL (SELECT codice FROM ordineFornitore)";

			rs = st.executeQuery(sql);
			rs.next();
			i = rs.getInt("codice");

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
		return i;
	}

	public void inserisciCompone(String idProdottoContabile, int codiceOrdineFornitore, int quantità) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO compone(idProdottoContabile,idordineFornitore,Quantità) VALUES (?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, idProdottoContabile);
			ps.setInt(2, codiceOrdineFornitore);
			ps.setInt(3, quantità);

			ps.executeUpdate();

			con.commit();
		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	public void inserisciForma(String idStampanteDigitale, int codiceOrdineCliente, int quantità) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO forma  (idStampanteDigitale,idOrdineCliente,quantità ) " + "VALUES (?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, idStampanteDigitale);
			ps.setInt(2, codiceOrdineCliente);
			ps.setInt(3, quantità);

			ps.executeUpdate();

			con.commit();
		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	public void inserisciSpecifica(String idProdottoContabile, int codiceOrdineCliente, int quantità) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO specifica(idProdottoContabile,idOrdineCliente,Quantità)  VALUES (?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, idProdottoContabile);
			ps.setInt(2, codiceOrdineCliente);
			ps.setInt(3, quantità);

			ps.executeUpdate();

			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	public void inserisciDettaglia(String idStampanteDigitale, int codiceOrdineFornitore, int quantità) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO dettaglia (idStampanteDigitale,idOrdineFornitore,quantità ) " + "VALUES (?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, idStampanteDigitale);
			ps.setInt(2, codiceOrdineFornitore);
			ps.setInt(3, quantità);

			ps.executeUpdate();

			con.commit();
		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	public void aggiornaProdottiContabiliCliente(int npezzi, String modello) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "UPDATE prodottoContabile SET numeropezzi=numeropezzi-? WHERE modello=?";

			ps = con.prepareStatement(sql);
			ps.setInt(1, npezzi);
			ps.setString(2, modello);
			ps.executeUpdate();
			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	public void aggiornaStampanteDigitaleCliente(int npezzi, String modello) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "UPDATE stampanteDigitale SET numeropezzi=numeropezzi-? WHERE modello=?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, npezzi);
			ps.setString(2, modello);
			ps.executeUpdate();

			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	public void aggiornaProdottiContabiliFornitore(int npezzi, String modello) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement();

			String sql = "UPDATE prodottoContabile SET numeropezzi=numeropezzi+? WHERE modello=?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, npezzi);
			ps.setString(2, modello);
			ps.executeUpdate();
			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	public void aggiornaStampanteDigitaleFornitore(int npezzi, String modello) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "UPDATE stampanteDigitale SET numeropezzi=numeropezzi+? WHERE modello=?";

			ps = con.prepareStatement(sql);
			ps.setInt(1, npezzi);
			ps.setString(2, modello);
			ps.executeUpdate();
			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	public int inserisciFatturazioneCliente(int idOrdineCliente) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO fatturazioneCliente (idOrdineCliente) VALUES (?)";

			ps = con.prepareStatement(sql);
			ps.setInt(1, idOrdineCliente);

			risposta = ps.executeUpdate();
			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
		return risposta;
	}

	public int inserisciFatturazioneFornitore(int idOrdineFornitore) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO fatturazioneFornitore (idOrdineFornitore) VALUES (?)";

			ps = con.prepareStatement(sql);
			ps.setInt(1, idOrdineFornitore);

			risposta = ps.executeUpdate();
			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
		return risposta;
	}
}
