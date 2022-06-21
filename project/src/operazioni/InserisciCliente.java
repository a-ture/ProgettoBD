package operazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JOptionPane;

public class InserisciCliente {

	public int inserisciNuovoCliente(String piva, String nomesocietà, String email, String via, int nCivico, String cap) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO cliente(piva,nomesocietà,email,via,nCivico,cap)" + "VALUES (?,?,?,?,?,?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, piva);
			ps.setString(2, nomesocietà);
			ps.setString(3, email);
			ps.setString(4, via);
			ps.setInt(5, nCivico);
			ps.setString(6, cap);

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

	public int inserisciNuovoNumeroTelefonoCliente(String idcliente, String numerotelefono) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int risposta = 0;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO telefonoCliente(idcliente,numerotelefono)" + "VALUES (?,?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, idcliente);
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