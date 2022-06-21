package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.EliminaCliente;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class Operazione3 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	private String piva;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione3 frame = new Operazione3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void refreshTabele() {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM cliente";

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
	public Operazione3() {
		setTitle("Operazione 3");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 732, 562);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 104, 698, 411);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = table.getSelectedRow();

				 piva = table.getModel().getValueAt(row, 0).toString();

			}
		});
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Visualizza elenco clienti");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTabele(); 

			}

		});
		btnNewButton.setBounds(48, 52, 266, 42);
		contentPane.add(btnNewButton);

		JButton btnEliminaCliente = new JButton("Elimina Cliente");
		btnEliminaCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEliminaCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int risposta= JOptionPane.showConfirmDialog(null, "Vuoi procedere all'eliminazione?","Eliminazione",JOptionPane.YES_NO_OPTION);
				
				if (risposta==0)
				{
					EliminaCliente op3= new EliminaCliente();
					op3.EliminaDatiCliente(piva);
					refreshTabele();
				
					
				}
			}
		});
		btnEliminaCliente.setBounds(392, 52, 266, 42);
		contentPane.add(btnEliminaCliente);
	}

}
