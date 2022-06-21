package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.InserisciOrdine;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class Operazione19 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione19 frame = new Operazione19();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Operazione19() {
		setTitle("Operazione 19 ");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 886, 624);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 78, 852, 499);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Elenco prodotti disponibili");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				Statement st = null;

				try {
					con = DBConnectionPool.getConnection();
					st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					String sql = "SELECT  p.modello, p.NumeroPezzi"
							+ " FROM  stampantedigitale  AS s, prodottoContabile AS p " + "where p.NumeroPezzi>0"
							+ " GROUP BY p.modello " + " UNION SELECT  s.modello, s.NumeroPezzi"
							+ " FROM  stampantedigitale  AS s, prodottoContabile AS p " + "where s.disponibile=true"
							+ " GROUP BY s.modello ";
					ResultSet rs = st.executeQuery(sql);
					table.setModel(DbUtils.resultSetToTableModel(rs));
					rs.relative(-1);

					while (rs.next()) {
						table.setModel(DbUtils.resultSetToTableModel(rs));
					}

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

			}

		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(468, 25, 243, 43);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("Aggiorna giacenza");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				Statement st = null;
				ResultSet rs = null;

				try {
					con = DBConnectionPool.getConnection();

					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, -1);
					java.sql.Date date = new Date(c.getTimeInMillis());

					System.out.print(date); 
					String sql = "SELECT f.idStampanteDigitale,f.quantità FROM forma as f, ordinecliente as o WHERE o.data=? and f.idOrdineCliente=o.codice and (o.tipo='noleggio' or o.tipo='rinnovo noleggio' ) ";

					PreparedStatement ps = con.prepareStatement(sql);
					ps.setDate(1, date);

					String stampanti = null;
					int pezzi=0;
					System.out.println(stampanti + pezzi);
					rs = ps.executeQuery();
					
					InserisciOrdine op = new InserisciOrdine();
					while (rs.next()) {
						stampanti = rs.getString("idStampanteDigitale");
						pezzi = rs.getInt("quantità");
						System.out.println(stampanti + pezzi);
						op.aggiornaStampanteDigitaleFornitore(pezzi, stampanti);
					}

				} catch (SQLException s) {
					JOptionPane.showMessageDialog(null, s);
				} finally {
					try {
						if (rs != null)
							rs.close();
						if (st != null)
							st.close();
						DBConnectionPool.releaseConnection(con);
					} catch (SQLException s) {
						JOptionPane.showMessageDialog(null, s);
					}
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setBounds(97, 25, 239, 43);
		contentPane.add(btnNewButton_1);
	}
}
