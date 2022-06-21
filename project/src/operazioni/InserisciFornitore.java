package operazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JOptionPane;

public class InserisciFornitore {

	public int inserisciNuovoFornitore(String piva, String nomeFornitore, String via, int nCivico, String cap) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO fornitore (piva,nomefornitore,via,nCivico,cap)" + "VALUES (?,?,?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, piva);
			ps.setString(2, nomeFornitore);
			ps.setString(3, via);
			ps.setInt(4, nCivico);
			ps.setString(5, cap);

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

	public int inserisciNuovoNumeroTelefonoCliente(String idFornitore, String numerotelefono) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO telefonoFornitore (idFornitore,numerotelefono)" + "VALUES (?,?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, idFornitore);
			ps.setString(2, numerotelefono);

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
