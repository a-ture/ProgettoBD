package operazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JOptionPane;

public class EliminaCliente {

	public void EliminaDatiCliente(String piva) {
		Connection con = null;
		PreparedStatement ps=null; 
		PreparedStatement ps1=null; 
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
	

			String usql = "DELETE FROM cliente WHERE piva=?";

	        ps = con.prepareStatement(usql);

			ps.setString(1, piva);

			int n = ps.executeUpdate();

			if (n == 1) {

				String usql1 = "DELETE FROM telefonocliente WHERE idCliente=?";

			    ps1 = con.prepareStatement(usql1);

				ps1.setString(1, piva);

				ps1.executeUpdate();
				
				con.commit();

			} else {

				JOptionPane.showMessageDialog(null,"La rimozione non ha avuto succceso, riprova!");
			}
		} catch (SQLException s) {
			JOptionPane.showMessageDialog(null,s); 
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
				JOptionPane.showMessageDialog(null,s); }
		}
	}
}
