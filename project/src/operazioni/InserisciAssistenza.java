package operazioni;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import javax.swing.JOptionPane;

public class InserisciAssistenza {

	public int inserisciNuovaAssistenza(Date data, String descrizione, String tipologia, String idCliente) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int risposta = 0;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO assistenza(data,descrizione,tipo,idCliente)" + "VALUES (?,?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setDate(1, data);
			ps.setString(2, descrizione);
			ps.setString(3, tipologia);
			ps.setString(4, idCliente);

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

	public int trovaIdAssistenza() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int id = 0;
		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement();

			String sql = "SELECT numerochiamata FROM assistenza WHERE numerochiamata >= ALL (SELECT numerochiamata FROM assistenza)";

			rs = st.executeQuery(sql);
			rs.next();

			id = rs.getInt("numerochiamata");
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

	public int inserisciFatturazioneAssistenza(int numeroChiamata) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO fatturazioneAssistenza (idAssistenza)" + "VALUES (?)";

			ps = con.prepareStatement(sql);
			ps.setInt(1, numeroChiamata);

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