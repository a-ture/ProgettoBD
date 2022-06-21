package operazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class EliminaOrdine {

	public void EliminaOrdineCliente(int codice) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			con = DBConnectionPool.getConnection();

			String usql = "DELETE FROM ordinecliente WHERE codice=?";

			ps = con.prepareStatement(usql);

			ps.setInt(1, codice);
			con.commit();

			int n = ps.executeUpdate();

			if (n == 1) {
				JOptionPane.showMessageDialog(null, "La rimozione ha avuto successo");
			} else {
				JOptionPane.showMessageDialog(null, "La rimozione non ha avuto succceso, riprova!");

			}
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

	public void EliminaOrdineFornitore(int codice) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			con = DBConnectionPool.getConnection();

			String usql = "DELETE FROM ordinefornitore WHERE codice=?";

			ps = con.prepareStatement(usql);

			ps.setInt(1, codice);

			int n = ps.executeUpdate();

			con.commit();

			if (n == 1) {
				JOptionPane.showMessageDialog(null, "La rimozione ha avuto successo");
			} else {
				JOptionPane.showMessageDialog(null, "La rimozione non ha avuto succceso, riprova!");

			}
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
