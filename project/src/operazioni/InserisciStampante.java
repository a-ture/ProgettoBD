package operazioni;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class InserisciStampante {

	public void inserisciNuovaStampante(String modello, double prezzofisso, int numeropezzi, String formatodistampamax,
			int annoproduzione, String aziendaproduttrice) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO stampantedigitale(modello,canonefisso,numeropezzi,formatodistampamax,annodiproduzione, aziendaproduttrice)"
					+ "VALUES (?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);

			ps.setString(1, modello);
			ps.setDouble(2, prezzofisso);
			ps.setInt(3, numeropezzi);
			ps.setString(4, formatodistampamax);
			ps.setInt(5, annoproduzione);
			ps.setString(6, aziendaproduttrice);

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