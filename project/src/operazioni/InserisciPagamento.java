package operazioni;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class InserisciPagamento {

	public int inserisciPagamento(Date data, String modalità, double importo) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO pagamento (data, modalità, importo)" + "VALUES (?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setDate(1, data);
			ps.setString(2, modalità);
			ps.setDouble(3, importo);

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

	public int trovaIdPagamento() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int id = 0;
		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement();

			String sql = "SELECT ID FROM pagamento WHERE ID >= ALL (SELECT ID FROM pagamento)";

			rs = st.executeQuery(sql);
			rs.next();

			id = rs.getInt("id");
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
		return id;
	}

	public void inserisciFatturazioneCliente(int idOrdineCliente, int idPagamento) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "UPDATE FatturazioneCliente SET idPagamento=? WHERE idOrdineCliente=?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, idPagamento);
			ps.setInt(2, idOrdineCliente);

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

	public void inserisciFatturazioneFornitore(int idOrdineFornitore, int idPagamento) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "UPDATE fatturazioneFornitore SET idPagamento=? WHERE idOrdineFornitore=?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, idPagamento);
			ps.setInt(2, idOrdineFornitore);

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

	public void inserisciFatturazioneAssistenza(int idAssistenza, int idPagamento) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "UPDATE FatturazioneAssistenza SET idPagamento=? WHERE idAssistenza=?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, idPagamento);
			ps.setInt(2, idAssistenza);

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

}
