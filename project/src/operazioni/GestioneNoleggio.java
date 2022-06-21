package operazioni;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;

import javax.swing.JOptionPane;



public class GestioneNoleggio {

	public void aggiorna() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			con = DBConnectionPool.getConnection();
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -1);
			java.sql.Date date = new Date(c.getTimeInMillis());

			String sql = "SELECT f.idStampanteDigitale,f.quantità FROM forma as f, ordinecliente as o WHERE o.data=? and f.idOrdineCliente=o.codice and (o.tipo='noleggio' or o.tipo='rinnovo noleggio' ) ";

			ps = con.prepareStatement(sql);
			ps.setDate(1, date);

			String stampanti;
			int pezzi;

			rs = ps.executeQuery();
			InserisciOrdine op = new InserisciOrdine();
			while (rs.next()) {
				stampanti = rs.getString("idStampanteDigitale");
				pezzi = rs.getInt("quantità");
				op.aggiornaStampanteDigitaleFornitore(pezzi, stampanti);
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
