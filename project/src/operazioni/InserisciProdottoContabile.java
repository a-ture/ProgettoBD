package operazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class InserisciProdottoContabile {
	
	public void inserisciNuovoProdottoContabile(String modello, String marca, int anno, int numeroPezzi, double prezzo, String tipologia)
	{
		Connection con = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "INSERT INTO prodottoContabile (modello,aziendaProduttrice,tipologia, prezzo,numeropezzi,annodiproduzione)" 
			                + "VALUES (?,?,?,?,?,?)";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1,modello);
			ps.setString(2,marca);
			ps.setString(3, tipologia);
			ps.setDouble(4, prezzo);
			ps.setInt(5,numeroPezzi);
			ps.setInt(6, anno);
		
			ps.executeUpdate();

			con.commit();

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
