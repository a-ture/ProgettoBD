package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Operazione17 extends JFrame {

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
					Operazione17 frame = new Operazione17();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Operazione17() {
		setTitle("Operazione 17");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 670, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 96, 636, 346);
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

					String sql = "SELECT modello, canonefisso  FROM stampantedigitale";

					rs = st.executeQuery(sql);
					table.setModel(DbUtils.resultSetToTableModel(rs));
					rs.relative(-1);
			
					
					while (rs.next()){
						table.setModel(DbUtils.resultSetToTableModel(rs));
					}
					
				} catch (SQLException s){
					System.err.println(s.getMessage());
				} finally {
					try {
						if (rs != null)
							rs.close();
						if (st != null)
							st.close();
						DBConnectionPool.releaseConnection(con);
					} catch (SQLException s) {
						JOptionPane.showMessageDialog(null,s);
					}
				}
					
			}		
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(224, 48, 187, 38);
		contentPane.add(btnNewButton);
	}

}
