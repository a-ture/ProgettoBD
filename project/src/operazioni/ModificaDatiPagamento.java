package operazioni;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JOptionPane;

public class ModificaDatiPagamento {

	public void  modificaPagamento(Date data, String modalità, double importo, int id) {
		Connection con = null;
		PreparedStatement ps=null; 
		ResultSet rs = null;
		try {
			con = DBConnectionPool.getConnection();

			String sql = "UPDATE pagamento SET data=?,modalità=?,importo=?  WHERE id=?";

			 ps = con.prepareStatement(sql);

			ps.setDate(1, data);
			ps.setString(2, modalità);
			ps.setDouble(3, importo);
			ps.setInt(4, id);

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
