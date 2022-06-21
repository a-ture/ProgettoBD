package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;

import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Operazione8 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione8 frame = new Operazione8();
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
	public Operazione8() {
		setTitle("Operazione 8");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 723, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 98, 661, 303);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		JButton btnNewButton = new JButton("Carica");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				Statement st = null;
				ResultSet rs = null;

				try {
					con = DBConnectionPool.getConnection();
					st = con.createStatement();

					Calendar c = Calendar.getInstance();

					java.sql.Date date = new Date(c.getTimeInMillis());

					String sql = "SELECT * FROM assistenza WHERE data >= ?";
					PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					ps.setDate(1, date);
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
		btnNewButton.setBounds(254, 48, 154, 40);
		contentPane.add(btnNewButton);
	}

}
