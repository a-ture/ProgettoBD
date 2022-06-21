package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.InserisciPagamento;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class Operazione4 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldData;
	private JTextField textFieldImporto;
	private JComboBox<String> comboBoxModalitàDiPagamento;
	private JTextField textFieldId;
	private JComboBox<String> comboBoxScelta;
	private JLabel labelId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione4 frame = new Operazione4();
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

			String sql = "SELECT * FROM pagamento";
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
	public Operazione4() {
		setTitle("Operazione4");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1055, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(357, 91, 674, 407);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Visualizza elenco pagamenti");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTable();

			}

		});
		btnNewButton.setBounds(554, 33, 323, 48);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Inserisci Pagamento");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				InserisciPagamento op4 = new InserisciPagamento();

				DateFormat df = new SimpleDateFormat("dd MM yyyy");
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(df.parse(textFieldData.getText()));
					java.sql.Date date = new Date(cal.getTimeInMillis());
					double importo = Double.parseDouble(textFieldImporto.getText());
					String modalita = comboBoxModalitàDiPagamento.getSelectedItem().toString();
					String aCosaSiRiferisce = comboBoxScelta.getSelectedItem().toString();
					int codice = Integer.parseInt(textFieldId.getText());

					if (importo != 0 && codice != 0) {
						if (op4.inserisciPagamento(date, modalita, importo) != 0) {
							int idPagamento = op4.trovaIdPagamento();

							if (aCosaSiRiferisce.equals("Assistenza"))
								op4.inserisciFatturazioneAssistenza(codice, idPagamento);

							if (aCosaSiRiferisce.equals("Ordine Fornitore"))
								op4.inserisciFatturazioneFornitore(codice, idPagamento);

							if (aCosaSiRiferisce.equals("Ordine Cliente"))
								op4.inserisciFatturazioneCliente(codice, idPagamento);
						}

						else
							JOptionPane.showMessageDialog(null, "L'inserimento non ha avuto successo");
					}

					else {
						JOptionPane.showMessageDialog(null, "Non hai inserito corretamente i dati");
					}

					refreshTable();
				} catch (ParseException e1) {

					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

			}
		});
		btnNewButton_1.setBounds(47, 279, 277, 48);
		contentPane.add(btnNewButton_1);

		textFieldData = new JTextField();
		textFieldData.setColumns(10);
		textFieldData.setBounds(191, 109, 155, 19);
		contentPane.add(textFieldData);

		textFieldImporto = new JTextField();
		textFieldImporto.setColumns(10);
		textFieldImporto.setBounds(191, 138, 156, 19);
		contentPane.add(textFieldImporto);

		JLabel lblNewLabel_1_1 = new JLabel("Data");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(20, 114, 45, 13);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Importo");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(20, 137, 115, 16);
		contentPane.add(lblNewLabel_1_2);

		comboBoxModalitàDiPagamento = new JComboBox<String>();
		comboBoxModalitàDiPagamento.setBounds(191, 167, 156, 21);
		contentPane.add(comboBoxModalitàDiPagamento);
		comboBoxModalitàDiPagamento.addItem("Contanti");
		comboBoxModalitàDiPagamento.addItem("Bonifico");
		comboBoxModalitàDiPagamento.addItem("Carta Di Credito");

		JLabel lblNewLabel_1_2_1 = new JLabel("Cosa paghi?");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2_1.setBounds(20, 198, 115, 21);
		contentPane.add(lblNewLabel_1_2_1);

		JLabel lblNewLabel_1_2_1_1 = new JLabel("Modalità di Pagamento");
		lblNewLabel_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2_1_1.setBounds(20, 171, 155, 21);
		contentPane.add(lblNewLabel_1_2_1_1);

		comboBoxScelta = new JComboBox<String>();
		comboBoxScelta.setBounds(191, 200, 156, 21);
		contentPane.add(comboBoxScelta);
		comboBoxScelta.addItem("Assistenza");
		comboBoxScelta.addItem("Ordine Cliente");
		comboBoxScelta.addItem("Ordine Fornitore");

		textFieldId = new JTextField();
		textFieldId.setColumns(10);
		textFieldId.setBounds(191, 231, 156, 19);
		contentPane.add(textFieldId);

		labelId = new JLabel("Id");
		labelId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelId.setBounds(20, 229, 115, 21);
		contentPane.add(labelId);

		JButton btnNewButton_1_1 = new JButton("Elenco ordini clienti non pagati");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				Statement st = null;
				ResultSet rs = null;

				try {
					con = DBConnectionPool.getConnection();
					st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

					String sql = "SELECT idOrdineCliente FROM fatturazioneCliente WHERE idPagamento IS NULL";
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
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1_1.setBounds(47, 337, 277, 48);
		contentPane.add(btnNewButton_1_1);

		JButton btnNewButton_1_2 = new JButton("Elenco ordini fornitori non pagati");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				Statement st = null;
				ResultSet rs = null;

				try {
					con = DBConnectionPool.getConnection();
					st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

					String sql = "SELECT idOrdineFornitore FROM fatturazioneFornitore WHERE idPagamento IS NULL";
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
		});
		btnNewButton_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1_2.setBounds(47, 395, 277, 48);
		contentPane.add(btnNewButton_1_2);

		JButton btnNewButton_1_3 = new JButton("Elenco assistenze non pagate");
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection con = null;
				Statement st = null;
				ResultSet rs = null;

				try {
					con = DBConnectionPool.getConnection();
					st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

					String sql = "SELECT idAssistenza FROM fatturazioneAssistenza WHERE idPagamento IS NULL";
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
		});
		btnNewButton_1_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1_3.setBounds(47, 450, 277, 48);
		contentPane.add(btnNewButton_1_3);

	}
}
