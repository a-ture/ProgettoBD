package operazioni;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JOptionPane;

public class ModificaCliente {

	public int ModificaDatiCliente(String piva, String nuovanomesocietà, String nuovaemail, String nuovavia,
			int nuovanCivico, String nuovacap) {
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String usql = "UPDATE cliente SET nomesocietà=?, email=?, via=?, ncivico=?, cap=?  WHERE piva=?";

			 ps = con.prepareStatement(usql);
			ps.setString(1, nuovanomesocietà);
			ps.setString(2, nuovaemail);
			ps.setString(3, nuovavia);
			ps.setInt(4, nuovanCivico);
			ps.setString(5, nuovacap);
			ps.setString(6, piva);

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

	public void ModificaDatiNumeroTelefonoCliente(String idCliente, String numerotelefono, String numerotelefonoOld) {
		Connection con = null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String usql1 = "DELETE FROM telefonocliente WHERE numerotelefono=? and idCliente=?";

			 ps = con.prepareStatement(usql1);

			ps.setString(1, numerotelefonoOld);
			ps.setString(2, idCliente);

			ps.executeUpdate();

			con.commit();

			String usql = "INSERT INTO telefonocliente (idcliente,numerotelefono) VALUES (?,?)";

			ps1 = con.prepareStatement(usql);

			ps1.setString(1, idCliente);
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

	public void EliminaTelefono(String idCliente, String numerotelefono) {
		Connection con = null;

		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "SELECT COUNT(*) FROM telefonoCliente WHERE idCliente=?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, idCliente);
			rs = ps.executeQuery();
			rs.next();
			int n = rs.getInt("COUNT(*)");
			if (n > 1) {
				String usql1 = "DELETE FROM telefonocliente WHERE numerotelefono=? and idCliente=?";

				ps = con.prepareStatement(usql1);

				ps.setString(1, numerotelefono);
				ps.setString(2, idCliente);

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