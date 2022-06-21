package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.ModificaCliente;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Operazione2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldPiva;
	private JTextField textFieldNomeSocieta;
	private JTextField textFieldEmail;
	private JTextField textFieldVia;
	private JTextField textFieldCap;
	private JTextField textFieldNCivico;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JTextField textFieldTelefono;
	private JLabel lblNewLabel_6;
	private JButton btnModificaTelefono;

	private String piva;
	private String tel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione2 frame = new Operazione2();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}

	private void refreshTableTelefonoCliente(String piva) {
		Connection con = null;

		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();

			String sql = "SELECT numerotelefono FROM telefonocliente WHERE idCliente=?";

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

	private void refreshTable() {
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
	public Operazione2() {
		setTitle("Operazione 2 ");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1016, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(280, 75, 709, 382);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();

				piva = table.getModel().getValueAt(row, 0).toString();
				tel = table.getModel().getValueAt(row, 0).toString();

				Connection con = null;
				try {
					con = DBConnectionPool.getConnection();

					String sql = "SELECT *  FROM  cliente WHERE piva=?";

					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, piva);
					ResultSet rs = ps.executeQuery();
					int conto = 0;
					while (rs.next()) {
						textFieldPiva.setText(rs.getString("Piva"));
						textFieldNomeSocieta.setText(rs.getString("NomeSocietà"));
						textFieldEmail.setText(rs.getString("Email"));
						textFieldVia.setText(rs.getString("Via"));
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

		textFieldPiva = new JTextField();
		textFieldPiva.setBounds(129, 97, 124, 19);
		contentPane.add(textFieldPiva);
		textFieldPiva.setColumns(10);

		textFieldNomeSocieta = new JTextField();
		textFieldNomeSocieta.setBounds(129, 126, 124, 19);
		contentPane.add(textFieldNomeSocieta);
		textFieldNomeSocieta.setColumns(10);

		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(129, 155, 124, 19);
		contentPane.add(textFieldEmail);

		textFieldVia = new JTextField();
		textFieldVia.setColumns(10);
		textFieldVia.setBounds(129, 184, 124, 19);
		contentPane.add(textFieldVia);

		textFieldCap = new JTextField();
		textFieldCap.setColumns(10);
		textFieldCap.setBounds(129, 242, 124, 19);
		contentPane.add(textFieldCap);

		textFieldNCivico = new JTextField();
		textFieldNCivico.setColumns(10);
		textFieldNCivico.setBounds(129, 213, 124, 19);
		contentPane.add(textFieldNCivico);

		JLabel lblNewLabel = new JLabel("Piva");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(74, 98, 45, 13);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome Società");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(25, 127, 94, 13);
		contentPane.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(74, 156, 45, 13);
		contentPane.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Via");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(74, 185, 45, 18);
		contentPane.add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("Numero Civico");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(25, 214, 94, 13);
		contentPane.add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("Cap");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(74, 240, 45, 21);
		contentPane.add(lblNewLabel_5);

		btnNewButton = new JButton("Modifica Dati");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModificaCliente op2 = new ModificaCliente();

				String piva = textFieldPiva.getText();
				String nomeSocieta = textFieldNomeSocieta.getText();
				String email = textFieldEmail.getText();
				String via = textFieldVia.getText();

				try {

					int nCivico = Integer.parseInt(textFieldNCivico.getText());
					String Cap = textFieldCap.getText();
					if (piva.matches("[0-9]+") == false)
						JOptionPane.showMessageDialog(null, "La partita iva può contenere solo numeri");
					if (piva.matches("[0-9]+") == true
							&& !(nomeSocieta.isEmpty() && via.isEmpty() && nCivico == 0))
						if (op2.ModificaDatiCliente(piva, nomeSocieta, email, via, nCivico, Cap) == 0)
							JOptionPane.showMessageDialog(null, "La modifica non ha avuto successo");

					refreshTable();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,
							"Non hai inserito correttamente i dati per: Numero Civico o Cap\n Ritenta");
				}

			}
		});
		btnNewButton.setBounds(53, 282, 200, 43);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("Visualliza elenco clienti");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();

			}
		});
		btnNewButton_1.setBounds(280, 32, 234, 35);
		contentPane.add(btnNewButton_1);

		btnNewButton_2 = new JButton("Numeri di telefono");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String piva = textFieldPiva.getText();
				refreshTableTelefonoCliente(piva);

			}
		});
		btnNewButton_2.setBounds(524, 32, 217, 34);
		contentPane.add(btnNewButton_2);

		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(129, 350, 124, 19);
		contentPane.add(textFieldTelefono);
		textFieldTelefono.setColumns(10);

		lblNewLabel_6 = new JLabel("Telefono");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(49, 351, 132, 13);
		contentPane.add(lblNewLabel_6);

		btnModificaTelefono = new JButton("Modifica Telefono");
		btnModificaTelefono.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnModificaTelefono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ModificaCliente op2 = new ModificaCliente();
				String idCliente = textFieldPiva.getText();
				String tel1 = textFieldTelefono.getText();
				if (tel1.matches("[0-9]+") == false)
					JOptionPane.showMessageDialog(null, "Il numero di telefono può contenere solo numeri");
				else {
				op2.ModificaDatiNumeroTelefonoCliente(idCliente, tel1, tel);

				refreshTableTelefonoCliente(idCliente);
				}
			}
		});
		btnModificaTelefono.setBounds(53, 389, 200, 43);
		contentPane.add(btnModificaTelefono);

		JButton btnNewButton_1_1 = new JButton("Elimina Numero Telefono");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int risposta = JOptionPane.showConfirmDialog(null, "Vuoi procedere all'eliminazione?", "Eliminazione",
						JOptionPane.YES_NO_OPTION);

				if (risposta == 0) {
					String piva = textFieldPiva.getText();
					String tel = textFieldTelefono.getText();
					ModificaCliente op = new ModificaCliente();
					op.EliminaTelefono(piva, tel);
					refreshTableTelefonoCliente(piva);

				}

			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1_1.setBounds(751, 30, 234, 35);
		contentPane.add(btnNewButton_1_1);
	}
}
