package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.InserisciFornitore;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Operazione13 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldPiva;
	private JTextField textFieldNome;
	private JTextField textFieldVia;
	private JTextField textFieldNCivico;
	private JTextField textFieldCap;

	private static int contaInserisciTelefono = 0;
	private static int contaInserisciFornitore = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione13 frame = new Operazione13();
					frame.setVisible(true);
					frame.addWindowListener(new WindowListener() {

						@Override
						public void windowClosing(WindowEvent e) {
							if (contaInserisciTelefono == 0 && contaInserisciFornitore != 0) {
								JOptionPane.showInternalMessageDialog(null,
										"Non puoi chiudere la finestra se prima non aggiungi almeno un numero di telefono");
								frame.setVisible(true);
							} else {

								frame.setVisible(false);
								System.exit(0);
							}

						}

						public void windowOpened(WindowEvent e) {
						}

						public void windowClosed(WindowEvent e) {
						}

						public void windowIconified(WindowEvent e) {
						}

						public void windowDeiconified(WindowEvent e) {
						}

						public void windowActivated(WindowEvent e) {
						}

						public void windowDeactivated(WindowEvent e) {
						}
					});
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
	public Operazione13() {
		setTitle("Operazione 13");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1004, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(308, 98, 672, 409);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = table.getSelectedRow();

				String piva = table.getModel().getValueAt(row, 0).toString();

				textFieldPiva.setText(piva);

			}
		});
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Visualizza elenco fornitori");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		btnNewButton.setBounds(530, 37, 305, 51);
		contentPane.add(btnNewButton);

		textFieldPiva = new JTextField();
		textFieldPiva.setBounds(158, 130, 134, 19);
		contentPane.add(textFieldPiva);
		textFieldPiva.setColumns(10);

		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(158, 157, 134, 19);
		contentPane.add(textFieldNome);

		textFieldVia = new JTextField();
		textFieldVia.setColumns(10);
		textFieldVia.setBounds(158, 186, 134, 19);
		contentPane.add(textFieldVia);

		textFieldNCivico = new JTextField();
		textFieldNCivico.setColumns(10);
		textFieldNCivico.setBounds(158, 215, 134, 19);
		contentPane.add(textFieldNCivico);

		textFieldCap = new JTextField();
		textFieldCap.setColumns(10);
		textFieldCap.setBounds(158, 244, 134, 19);
		contentPane.add(textFieldCap);

		JLabel lblNewLabel = new JLabel("PIVA Fornitore");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 133, 102, 13);
		contentPane.add(lblNewLabel);

		JLabel lblNomeFornitore = new JLabel("Nome Fornitore");
		lblNomeFornitore.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeFornitore.setBounds(10, 160, 102, 13);
		contentPane.add(lblNomeFornitore);

		JLabel lblVia = new JLabel("Via");
		lblVia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVia.setBounds(10, 187, 102, 13);
		contentPane.add(lblVia);

		JLabel lblNumeroCivico = new JLabel("Numero Civico");
		lblNumeroCivico.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumeroCivico.setBounds(10, 218, 102, 13);
		contentPane.add(lblNumeroCivico);

		JLabel lblCap = new JLabel("Cap");
		lblCap.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCap.setBounds(10, 247, 102, 19);
		contentPane.add(lblCap);

		JButton btnInserisci = new JButton("Inserisci Fornitore");
		btnInserisci.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					InserisciFornitore op = new InserisciFornitore();

					String piva = textFieldPiva.getText();
					String nomeFornitore = textFieldNome.getText();
					String cap = textFieldCap.getText();
					int nCivico = Integer.parseInt(textFieldNCivico.getText());
					String via = textFieldVia.getText();
					if (piva.matches("[0-9]+") == false)
						JOptionPane.showMessageDialog(null, "La partita iva può contenere solo numeri");
					else {
						if (!(nomeFornitore.isEmpty() && via.isEmpty() && nCivico == 0)) {
							int n = op.inserisciNuovoFornitore(piva, nomeFornitore, via, nCivico, cap);
							if (n == 0)
								JOptionPane.showMessageDialog(null, "L'inserimento non è andato a buon fine");
							contaInserisciFornitore += n;
							refreshTable();

						}

						else
							JOptionPane.showMessageDialog(null, "Non hai inserito tutti i campi");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Non hai inserito correttamente i dati. Riprova!");
				}
			}
		});

		btnInserisci.setBounds(49, 298, 221, 51);
		contentPane.add(btnInserisci);

		JButton btnInserisciTelefono = new JButton("Inserisci Telefono");
		btnInserisciTelefono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String telefono = JOptionPane.showInputDialog("Digita numero Di Telefono");
				InserisciFornitore op = new InserisciFornitore();
				if (!(textFieldPiva.getText().isEmpty())) {
					String idFornitore = textFieldPiva.getText();
					if (telefono.matches("[0-9]+") == false)
						JOptionPane.showMessageDialog(null, "Il numero di telefono può contenere solo numeri");
					else {
						int n = op.inserisciNuovoNumeroTelefonoCliente(idFornitore, telefono);
						if (n == 0)
							JOptionPane.showMessageDialog(null, "L'inserimento non è andato a buon fine");
						contaInserisciTelefono += n;
						refreshTableTelefonoFornitore(idFornitore);
					}
				} else
					JOptionPane.showMessageDialog(null, "Inserisci prima la PIVA");

			}
		});
		btnInserisciTelefono.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInserisciTelefono.setBounds(49, 359, 221, 51);
		contentPane.add(btnInserisciTelefono);
	}
}
