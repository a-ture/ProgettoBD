 package operazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ModificaDatiFornitore {

	public void ModificaFornitore(String piva, String nomeFornitore, String via, int nCivico, String cap) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "UPDATE fornitore SET nomefornitore=?, via=?, nCivico=?, cap=? WHERE piva=?";

			ps = con.prepareStatement(sql);

			ps.setString(1, nomeFornitore);
			ps.setString(2, via);
			ps.setInt(3, nCivico);
			ps.setString(4, cap);
			ps.setString(5, piva);

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

	public void ModificaDatiNumeroTelefonoFornitore(String idFornitore, String numerotelefono,
			String numerotelefonoOld) {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String usql1 = "DELETE FROM telefonofornitore WHERE numerotelefono=? and idFornitore=?";

			ps = con.prepareStatement(usql1);

			ps.setString(1, numerotelefonoOld);
			ps.setString(2, idFornitore);

			ps.executeUpdate();

			con.commit();

			String usql = "INSERT INTO telefonofornitore (idfornitore,numerotelefono) VALUES (?,?)";

			ps1 = con.prepareStatement(usql);

			ps1.setString(1, idFornitore);
			ps1.setString(2, numerotelefono);

			ps1.executeUpdate();

			con.commit();

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (ps1 != null)
					ps1.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	public void EliminaNumeroTelefonoFornitore(String idFornitore, String numerotelefono) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "SELECT COUNT(*) FROM telefonoFornitore WHERE idFornitore=?";
			ps = con.prepareStatement(sql);

			ps.setString(1, idFornitore);
			rs = ps.executeQuery();
			rs.next();
			int n = rs.getInt("COUNT(*)");
			if (n > 1) {

				String usql1 = "DELETE FROM telefonofornitore WHERE numerotelefono=? and idFornitore=?";

				ps = con.prepareStatement(usql1);

				ps.setString(1, numerotelefono);
				ps.setString(2, idFornitore);

				ps.executeUpdate();

				con.commit();
			} else {
				JOptionPane.showMessageDialog(null, "Non puoi eliminare l'unico numero di telefono");
			}

		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null, s);
		} finally {
			try {
				if (rs != null)
					rs.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}

	}

}
