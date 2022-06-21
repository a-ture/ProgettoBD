package operazioni;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ModificaAssistenza {

	public void ModificaDatiAssistenza(int numeroChiamata, Date data, String descrizione, String tipologia) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String usql = "UPDATE assistenza SET data=?, descrizione=?, tipo=?  WHERE numerochiamata=?";

			ps = con.prepareStatement(usql);

			ps.setDate(1, data);
			ps.setString(2, descrizione);
			ps.setString(3, tipologia);
			ps.setInt(4, numeroChiamata);
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
