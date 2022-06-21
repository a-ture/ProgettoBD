package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.InserisciOrdine;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class Operazione10_2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldData;
	private JTextField textFieldModello;
	private JTextField textFieldQuantità;
	private JComboBox<String> comboBoxNomeProdotto;
	private JComboBox<String> comboBoxPiva;

	private static int contaInserisciOrdini = 0;
	private static int contaInserisciArticolo = 0;
	private static int codiceOrdine;
	private static String modello;
	private static String nomeProdotto;
	private static int pezzi;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione10_2 frame = new Operazione10_2();
				
					frame.setVisible(true);

					frame.addWindowListener(new WindowListener() {

						@Override
						public void windowClosing(WindowEvent e) {
							if (contaInserisciArticolo == 0 && contaInserisciOrdini != 0) {
								JOptionPane.showInternalMessageDialog(null,
										"Non puoi chiudere la finestra se prima non aggiungi un articolo");
								frame.setVisible(true);
							} else {
								frame.setVisible(false);
								System.exit(0);
							}
						}

						@Override
						public void windowOpened(WindowEvent e) {
						}

						@Override
						public void windowClosed(WindowEvent e) {
						}

						@Override
						public void windowIconified(WindowEvent e) {
						}

						@Override
						public void windowDeiconified(WindowEvent e) {

						}

						@Override
						public void windowActivated(WindowEvent e) {
						}

						@Override
						public void windowDeactivated(WindowEvent e) {
						}

					});
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public int getCodiceOrdine() {
		return codiceOrdine;
	}

	public int getPezzi() {
		return pezzi;
	}

	public String getModello() {
		return modello;
	}

	public String getNomeProdotto() {
		return nomeProdotto;
	}

	private void refreshTable() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM ordinefornitore";
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

	private void fillComboBoxPivaFornitore() {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement();

			String sql = "SELECT PIVA FROM fornitore";
			rs = st.executeQuery(sql);

			while (rs.next()) {

				comboBoxPiva.addItem(rs.getString("PIVA"));
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
	public Operazione10_2() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1102, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(386, 77, 692, 412);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(34, 117, 46, 13);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("PIVA Fornitore");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(34, 148, 92, 13);
		contentPane.add(lblNewLabel_3);

		comboBoxPiva = new JComboBox<String>();
		comboBoxPiva.setBounds(180, 146, 148, 21);
		contentPane.add(comboBoxPiva);
		fillComboBoxPivaFornitore();

		textFieldData = new JTextField();
		textFieldData.setBounds(180, 116, 148, 19);
		contentPane.add(textFieldData);
		textFieldData.setColumns(10);

		JButton btnNewButton = new JButton("Insereisci Ordine");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DateFormat df = new SimpleDateFormat("dd MM yyyy");
				Calendar cal = Calendar.getInstance();
				try {
					int n;
					cal.setTime(df.parse(textFieldData.getText().toString()));
					Date date = new Date(cal.getTimeInMillis());
					String id = comboBoxPiva.getSelectedItem().toString();

					InserisciOrdine op12 = new InserisciOrdine();

					if (((n = op12.inserisciOridneFornitore(date, id)) != 0)) {
						codiceOrdine = op12.trovaCodiceOridneFornitore();
						op12.inserisciFatturazioneFornitore(codiceOrdine); 
					} else
						JOptionPane.showMessageDialog(null, "L'inserimento non è andato a buon fine");
				
					contaInserisciOrdini += n;
					refreshTable();
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(109, 201, 190, 51);
		contentPane.add(btnNewButton);

		JButton btnVisualizzaElencoOrdini = new JButton("Visualizza elenco ordini");
		btnVisualizzaElencoOrdini.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVisualizzaElencoOrdini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}

		});
		btnVisualizzaElencoOrdini.setBounds(628, 16, 220, 51);
		contentPane.add(btnVisualizzaElencoOrdini);

		JButton btnNewButton_2 = new JButton("Inserisci Articolo");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!(contaInserisciOrdini == 0)) {
					Connection con = null;
					Statement st = null;
					ResultSet rs = null;

					try {
						con = DBConnectionPool.getConnection();
						st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

						InserisciOrdine op12 = new InserisciOrdine();
						String npezzi1 = textFieldQuantità.getText().toString();
						pezzi = Integer.parseInt(npezzi1);

						nomeProdotto = comboBoxNomeProdotto.getSelectedItem().toString();
						modello = textFieldModello.getText();

						if (!(nomeProdotto.equals("Stampante Digitale"))) {

							String sql = "SELECT modello FROM prodottocontabile WHERE modello =?";
							PreparedStatement ps = con.prepareStatement(sql);
							ps.setString(1, modello);
							rs = ps.executeQuery();

							int conto = 0;
							//rs.next();
							while (rs.next())
								conto++;

							if (conto != 0) {
								op12.inserisciCompone(modello, codiceOrdine, pezzi);
								op12.aggiornaProdottiContabiliFornitore(pezzi, modello);
								

							} else {
								Operazione10_2_1 op9 = new Operazione10_2_1();
								op9.setVisible(true);
							}
						} else {

							String sql = "SELECT modello FROM stampantedigitale WHERE modello =?";
							PreparedStatement ps = con.prepareStatement(sql);
							ps.setString(1, modello);
							rs = ps.executeQuery();

							int conto = 0;

							while (rs.next())
								conto++;

							if (conto != 0) {
								op12.inserisciDettaglia(modello, codiceOrdine, pezzi);
								op12.aggiornaStampanteDigitaleFornitore(pezzi, modello);
					
							} else {
								Operazione10_2_2 op10 = new Operazione10_2_2();
								op10.setVisible(true);
							}
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Non hai inserito correttamente i dati della quantità");
					}

					catch (SQLException s) {
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

					contaInserisciArticolo++;
				}

				else
					JOptionPane.showMessageDialog(null, "Inserisci prima un ordine");

			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.setBounds(109, 422, 190, 51);
		contentPane.add(btnNewButton_2);

		textFieldModello = new JTextField();
		textFieldModello.setColumns(10);
		textFieldModello.setBounds(180, 346, 148, 19);
		contentPane.add(textFieldModello);

		JLabel lblModello = new JLabel("Modello");
		lblModello.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModello.setBounds(34, 347, 64, 13);
		contentPane.add(lblModello);

		JLabel lblNomeProdotto = new JLabel("Nome Prodotto");
		lblNomeProdotto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeProdotto.setBounds(34, 317, 96, 13);
		contentPane.add(lblNomeProdotto);

		textFieldQuantità = new JTextField();
		textFieldQuantità.setBounds(180, 375, 148, 19);
		contentPane.add(textFieldQuantità);
		textFieldQuantità.setColumns(10);

		JLabel lblQuantit = new JLabel("Quantità");
		lblQuantit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantit.setBounds(34, 374, 64, 16);
		contentPane.add(lblQuantit);

		comboBoxNomeProdotto = new JComboBox<String>();
		comboBoxNomeProdotto.setBounds(180, 315, 148, 21);
		contentPane.add(comboBoxNomeProdotto);

		JLabel lblNewLabel_2 = new JLabel("Inserisci i dati dell'ordine");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(87, 77, 212, 13);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Inserisci i dati degli artcoli");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_1.setBounds(65, 277, 221, 13);
		contentPane.add(lblNewLabel_2_1);
		comboBoxNomeProdotto.addItem("Registratore di cassa");
		comboBoxNomeProdotto.addItem("Cassetto contanti");
		comboBoxNomeProdotto.addItem("Scanner Codici a Barre");
		comboBoxNomeProdotto.addItem("Display di Cortesia");
		comboBoxNomeProdotto.addItem("Stampante Digitale");
	}
}
