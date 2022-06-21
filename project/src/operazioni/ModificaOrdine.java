package operazioni;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ModificaOrdine {

	public void ModificaOrdineCliente(String nuovoStato, String nuovoTipo, Date nuovaData, int codice) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String usql = "UPDATE ordinecliente SET stato=?, data=?,tipo=?  WHERE codice=?";

			ps = con.prepareStatement(usql);
			ps.setString(1, nuovoStato);
			ps.setDate(2, nuovaData);
			ps.setString(3, nuovoTipo);
			ps.setInt(4, codice);

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

	public void ModificaOrdineFornitore(Date nuovaData, int codice) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String usql = "UPDATE ordinefornitore SET  data=?  WHERE codice=?";

			ps = con.prepareStatement(usql);

			ps.setDate(1, nuovaData);
			ps.setInt(2, codice);

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
