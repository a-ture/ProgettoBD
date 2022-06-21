package operazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JOptionPane;

public class RimuoviProdotto {

	public void EliminaProdottoContabile(String modello) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String usql = "DELETE FROM prodottoContabile WHERE modello=?";

			ps = con.prepareStatement(usql);

			ps.setString(1, modello);

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

	public void EliminaStampante(String modello) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String usql = "DELETE FROM stampantedigitale WHERE modello=?";

			ps = con.prepareStatement(usql);

			ps.setString(1, modello);

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
