package operazioni;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class StatisticheDenaro {

	public double CalcolaUscite(Date dataInizio, Date dataFine) {
		Connection con = null;
		ResultSet rs = null;
		double uscite = -1;
		try {
			con = DBConnectionPool.getConnection();
			String sql = "SELECT importo FROM pagamento WHERE data between ? and ? and id IN (SELECT idPagamento FROM FatturazioneFornitore)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1, dataInizio);
			ps.setDate(2, dataFine);
			rs = ps.executeQuery();

			double totaleImponibile = 0;
			while (rs.next()) {
				totaleImponibile = rs.getDouble("importo");
				uscite += totaleImponibile;

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
		return uscite;
	}

	public double CalcolaEntrate(Date dataInizio, Date dataFine) {
		Connection con = null;

		ResultSet rs = null;
		double entrate = -1;

		try {
			con = DBConnectionPool.getConnection();
			String sql = "SELECT importo FROM pagamento WHERE data between ? and ? and (id IN (SELECT idPagamento FROM FatturazioneCliente) OR"
					+ " id IN (SELECT idPagamento FROM FatturazioneAssistenza) )";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDate(1, dataInizio);
			ps.setDate(2, dataFine);
			rs = ps.executeQuery();

			while (rs.next()) {

				double totaleImponibile = rs.getDouble("importo");
				entrate += totaleImponibile;
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
		return entrate;
	}

}
