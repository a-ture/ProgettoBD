package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.ModificaDatiFornitore;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Operazione14 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldPIVA;
	private JTextField textFieldNome;
	private JTextField textFieldVia;
	private JTextField textFieldNCivico;
	private JTextField textFieldCap;
	private JTextField textFieldTelefono;
	private String tel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione14 frame = new Operazione14();
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

	private void refreshTableTelefonoFornitore(String piva) {
		Connection con = null;

		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "SELECT numerotelefono FROM telefonofornitore WHERE idFornitore=?";

			PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, piva);
			rs = ps.executeQuery();

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
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				JOptionPane.showMessageDialog(null, s);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public Operazione14() {
		setTitle("Operazione 14");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1080, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(328, 53, 727, 321);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = table.getSelectedRow();

				String Piva = table.getModel().getValueAt(row, 0).toString();
				tel = table.getModel().getValueAt(row, 0).toString();
				Connection con = null;
				try {
					con = DBConnectionPool.getConnection();

					String sql = "SELECT *  FROM  fornitore  WHERE piva=?";
					int conto = 0;
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, Piva);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						textFieldPIVA.setText(rs.getString("piva"));
						textFieldNome.setText(rs.getString("nomefornitore"));
						textFieldVia.setText(rs.getString("via"));
						textFieldNCivico.setText(rs.getString("NCivico"));
						textFieldCap.setText(rs.getString("Cap"));
						conto++;
					}

					if (conto == 0) {
						textFieldTelefono.setText(tel);
					}

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Modifica");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String piva = textFieldPIVA.getText();
				String nomeFornitore = textFieldNome.getText();
				String cap = textFieldCap.getText();
				int nCivico = Integer.parseInt(textFieldNCivico.getText());
				String via = textFieldVia.getText();

				ModificaDatiFornitore op = new ModificaDatiFornitore();
				if (piva.matches("[0-9]+") == false)
					JOptionPane.showMessageDialog(null, "La partita iva può contenere solo numeri");
				else {
					op.ModificaFornitore(piva, nomeFornitore, via, nCivico, cap);
					refreshTable();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(98, 212, 166, 45);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Visualizza elenco fornitori");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTable();
			}
		});
		btnNewButton_1.setBounds(341, 10, 263, 37);
		contentPane.add(btnNewButton_1);

		textFieldPIVA = new JTextField();
		textFieldPIVA.setBounds(167, 67, 137, 19);
		contentPane.add(textFieldPIVA);
		textFieldPIVA.setColumns(10);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(167, 96, 137, 19);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);

		textFieldVia = new JTextField();
		textFieldVia.setBounds(167, 125, 137, 19);
		contentPane.add(textFieldVia);
		textFieldVia.setColumns(10);

		textFieldNCivico = new JTextField();
		textFieldNCivico.setBounds(167, 154, 137, 19);
		contentPane.add(textFieldNCivico);
		textFieldNCivico.setColumns(10);

		textFieldCap = new JTextField();
		textFieldCap.setBounds(167, 183, 137, 19);
		contentPane.add(textFieldCap);
		textFieldCap.setColumns(10);

		JLabel lblNewLabel = new JLabel("PIVA Fornitore");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(30, 67, 92, 13);
		contentPane.add(lblNewLabel);

		JLabel lblNomeFornitore = new JLabel("Nome Fornitore");
		lblNomeFornitore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeFornitore.setBounds(30, 96, 127, 13);
		contentPane.add(lblNomeFornitore);

		JLabel lblNewLabel_1_1 = new JLabel("Via");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(30, 125, 92, 13);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Numero Civico");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(30, 154, 96, 13);
		contentPane.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("CAP");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_3.setBounds(30, 183, 92, 13);
		contentPane.add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_3_1 = new JLabel("Telefono");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_3_1.setBounds(30, 287, 92, 13);
		contentPane.add(lblNewLabel_1_3_1);

		textFieldTelefono = new JTextField();
		textFieldTelefono.setColumns(10);
		textFieldTelefono.setBounds(167, 281, 137, 19);
		contentPane.add(textFieldTelefono);

		JButton btnModificaTelefono = new JButton("Modifica Telefono");
		btnModificaTelefono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ModificaDatiFornitore op2 = new ModificaDatiFornitore();
				String idFornitore = textFieldPIVA.getText();
				String tel1 = textFieldTelefono.getText();
				if (tel1.matches("[0-9]+") == false)
					JOptionPane.showMessageDialog(null, "Il numero di telefono può contenere solo numeri");
				else {

					op2.ModificaDatiNumeroTelefonoFornitore(idFornitore, tel1, tel);

					refreshTableTelefonoFornitore(idFornitore);
				}
			}
		});
		btnModificaTelefono.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnModificaTelefono.setBounds(98, 310, 166, 45);
		contentPane.add(btnModificaTelefono);

		JButton btnNewButton_1_1 = new JButton("Numeri Di Telefono");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String piva = textFieldPIVA.getText();
				refreshTableTelefonoFornitore(piva);

			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1_1.setBounds(614, 10, 183, 37);
		contentPane.add(btnNewButton_1_1);

		JButton btnNewButton_1_1_1 = new JButton("Elimina Numero Telefono");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int risposta = JOptionPane.showConfirmDialog(null, "Vuoi procedere all'eliminazione?", "Eliminazione",
						JOptionPane.YES_NO_OPTION);

				if (risposta == 0) {
					String piva = textFieldPIVA.getText();
					String tel = textFieldTelefono.getText();
					ModificaDatiFornitore op = new ModificaDatiFornitore();
					op.EliminaNumeroTelefonoFornitore(piva, tel);
					refreshTableTelefonoFornitore(piva);

				}

			}
		});
		btnNewButton_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1_1_1.setBounds(807, 10, 221, 37);
		contentPane.add(btnNewButton_1_1_1);
	}

}
