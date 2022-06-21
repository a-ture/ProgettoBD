package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.RimuoviProdotto;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Operazione20_1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private String modello;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione20_1 frame = new Operazione20_1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void refreshTable() {
		Connection con = null;
		Statement st = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT *  FROM  prodottoContabile";

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

	/**
	 * Create the frame.
	 */
	public Operazione20_1() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 872, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 82, 838, 400);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = table.getSelectedRow();

				modello = table.getModel().getValueAt(row, 0).toString();

			}
		});
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Visualizza elenco prodotti contabili");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(82, 38, 298, 34);
		contentPane.add(btnNewButton);

		JButton btnElimina = new JButton("Elimina");
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int risposta = JOptionPane.showConfirmDialog(null, "Vuoi procedere all'eliminazione?", "Eliminazione",
						JOptionPane.YES_NO_OPTION);

				if (risposta == 0) {
					RimuoviProdotto op = new RimuoviProdotto();
					op.EliminaProdottoContabile(modello);
					refreshTable();

				}

			}
		});
		btnElimina.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnElimina.setBounds(485, 38, 199, 34);
		contentPane.add(btnElimina);
	}
}
