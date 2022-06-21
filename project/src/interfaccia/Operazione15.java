package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class Operazione15 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	JComboBox<String> comboBoxFornitore;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione15 frame = new Operazione15();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}

	private void fillComboBoxFornitore() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement();
			String sql = "SELECT nomeFornitore FROM fornitore";
			rs = st.executeQuery(sql);

			while (rs.next()) {

				comboBoxFornitore.addItem(rs.getString("nomeFornitore"));
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

	private void refreshTable() {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM Fornitore";
			rs = st.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));
			rs.relative(-1);
			while (rs.next()) {

				table.setModel(DbUtils.resultSetToTableModel(rs));
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

	/**
	 * Create the frame.
	 */
	public Operazione15() {
		setTitle("Operazione 15");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1144, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(296, 78, 760, 554);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnCaricaRisultato = new JButton("Visualizza Risultato");
		btnCaricaRisultato.addActionListener(new ActionListener() {
			@SuppressWarnings("resource")
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				Statement st = null;
				ResultSet rs = null;

				try { 
					con = DBConnectionPool.getConnection();

			

					con = DBConnectionPool.getConnection();

					String sql = "SELECT PIVA FROM fornitore WHERE nomefornitore=  ?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, comboBoxFornitore.getSelectedItem().toString());
					rs = ps.executeQuery();

					rs.next();

					String piva=rs.getString("PIVA");

					String usql = "SELECT * FROM ordinefornitore WHERE idFornitore=  ?";
					ps = con.prepareStatement(usql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setString(1, piva);

					rs = ps.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					rs.relative(-1);

			
					while (rs.next()) 

						table.setModel(DbUtils.resultSetToTableModel(rs));
					

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
		btnCaricaRisultato.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCaricaRisultato.setBounds(344, 24, 264, 44);
		contentPane.add(btnCaricaRisultato);

		comboBoxFornitore = new JComboBox<String>();

		comboBoxFornitore.setBounds(75, 255, 164, 21);
		contentPane.add(comboBoxFornitore);
		fillComboBoxFornitore();
		JLabel lblNewLabel = new JLabel("Seleziona il fornitore");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(75, 176, 164, 38);
		contentPane.add(lblNewLabel);

		JButton btnVisuallizzaElencoFornitori = new JButton("Visuallizza elenco fornitori");
		btnVisuallizzaElencoFornitori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTable();
			}
		});
		btnVisuallizzaElencoFornitori.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnVisuallizzaElencoFornitori.setBounds(724, 24, 264, 44);
		contentPane.add(btnVisuallizzaElencoFornitori);
	}
}
