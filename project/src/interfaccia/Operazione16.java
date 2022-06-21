package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class Operazione16 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione16 frame = new Operazione16();
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
	public Operazione16() {
		setTitle("Operazione 16");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 676, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 119, 628, 326);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Carica");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				Statement st = null;
				ResultSet rs = null;

				try {
					
					con = DBConnectionPool.getConnection();
					st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

					Calendar c1 = Calendar.getInstance();
					c1.add(Calendar.MONTH, -1);
					java.sql.Date data = new Date (c1.getTimeInMillis());
					
					
					
					String sql = "SELECT c.nomesocietà, c.piva, t.numerotelefono FROM cliente as c, telefonocliente as t, ordinecliente as o WHERE (o.tipo='rinnovo' or o.tipo='noleggio') and t.idcliente=c.piva AND o.idcliente=c.piva AND o.data = ?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setDate(1, data);
					rs = ps.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					rs.relative(-1);
					

					while (rs.next()) {
						table.setModel(DbUtils.resultSetToTableModel(rs));
					}

				} catch (SQLException s) {
					System.err.println(s.getMessage());
				} finally {
					try {
						if (rs != null)
							rs.close();
						if (st != null)
							st.close();
						DBConnectionPool.releaseConnection(con);
					} catch (SQLException s) {
						System.err.println(s.getMessage());
					}
				}
			}

		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(220, 67, 185, 42);
		contentPane.add(btnNewButton);
	}

}
