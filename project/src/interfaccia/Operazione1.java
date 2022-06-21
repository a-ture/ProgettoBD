package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.InserisciCliente;
import operazioni.InserisciOrdine;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
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
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Color;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;

public class Operazione1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldNomeSocietà;
	private JTextField textPIVA;
	private JTextField textEmail;
	private JTextField textVia;
	private JTextField textNumeroCivico;
	private JTextField textCap;
	private static int NumeroDiTelefono = 0;
	private static int InserisciCliente = 0;
	private JTextField textFieldData;
	private JTextField textFieldQuantità;
	private JComboBox<String> comboBoxNomeProdotto;
	private JComboBox<String> comboBoxTipoOrdine;
	private JComboBox<String> comboBoxStato;
	private static int contaInserisciOrdini = 0;
	private static int contaInserisciArticolo = 0;
	private static int codiceOrdine = 0;
	private JTextField textFieldModello;

	private static String modello;

	private static String nomeProdotto;

	private static int pezzi1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione1 frame = new Operazione1();
					frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					frame.setVisible(true);

					frame.addWindowListener(new WindowListener() {

						@Override
						public void windowClosing(WindowEvent e) {
							if (NumeroDiTelefono == 0 && InserisciCliente != 0) {
								JOptionPane.showInternalMessageDialog(null,
										"Non puoi chiudere la finestra se prima non aggiungi almeno un numero di telefono");
								frame.setVisible(true);
							}

							else { if (contaInserisciArticolo == 0 && contaInserisciOrdini == 0) {
								JOptionPane.showInternalMessageDialog(null,
										"Non puoi chiudere la finestra se prima non aggiungi un ordine");
								frame.setVisible(true);}
								else {
									if (contaInserisciArticolo == 0 && contaInserisciOrdini != 0) {
										JOptionPane.showInternalMessageDialog(null,
												"Non puoi chiudere la finestra se prima non aggiungi un articolo");}
								else
								frame.setVisible(false);}
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

	private void refreshTableOrdini() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM ordinecliente";
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

	/**
	 * Create the frame.
	 */
	public Operazione1() {
		setTitle("Operazione 1");

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1097, 689);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(308, 73, 760, 351);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();

				String piva = table.getModel().getValueAt(row, 0).toString();

				textPIVA.setText(piva);

			}
		});
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("PIVA");
		lblNewLabel.setBounds(88, 151, 45, 13);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblNewLabel);

		JLabel lblNomeSociet = new JLabel("Nome Società");
		lblNomeSociet.setBounds(42, 122, 91, 13);
		lblNomeSociet.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblNomeSociet);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(88, 180, 45, 13);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblEmail);

		JLabel lblVia = new JLabel("Via");
		lblVia.setBounds(88, 209, 45, 13);
		lblVia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblVia);

		JLabel lblCap = new JLabel("Cap");
		lblCap.setBounds(88, 263, 30, 21);
		lblCap.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblCap);

		JLabel lblNumeroCivico = new JLabel("Numero Civico");
		lblNumeroCivico.setBounds(47, 238, 96, 13);
		lblNumeroCivico.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblNumeroCivico);

		textFieldNomeSocietà = new JTextField();
		textFieldNomeSocietà.setBounds(143, 121, 128, 19);
		contentPane.add(textFieldNomeSocietà);
		textFieldNomeSocietà.setColumns(10);

		textPIVA = new JTextField();
		textPIVA.setBounds(143, 150, 128, 19);
		contentPane.add(textPIVA);
		textPIVA.setColumns(10);

		textEmail = new JTextField();
		textEmail.setBounds(143, 179, 128, 19);
		contentPane.add(textEmail);
		textEmail.setColumns(10);

		textVia = new JTextField();
		textVia.setBounds(143, 208, 128, 19);
		contentPane.add(textVia);
		textVia.setColumns(10);

		textNumeroCivico = new JTextField();
		textNumeroCivico.setBounds(143, 237, 128, 19);
		contentPane.add(textNumeroCivico);
		textNumeroCivico.setColumns(10);

		textCap = new JTextField();
		textCap.setBounds(143, 266, 128, 19);
		contentPane.add(textCap);
		textCap.setColumns(10);

		JButton btnInserisciCliente = new JButton("Inserisci Cliente");
		btnInserisciCliente.setBounds(57, 309, 214, 38);
		btnInserisciCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInserisciCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InserisciCliente op1 = new InserisciCliente();

				try {

					String piva = textPIVA.getText();
					String nomeSocieta = textFieldNomeSocietà.getText();
					String cap = textCap.getText();
					int nCivico = Integer.parseInt(textNumeroCivico.getText());
					String via = textVia.getText();
					String email = textEmail.getText();

					if (piva.matches("[0-9]+") == true && !(nomeSocieta.isEmpty() && via.isEmpty() && nCivico == 0)) {
						int n = op1.inserisciNuovoCliente(piva, nomeSocieta, email, via, nCivico, cap);
						if (n == 0)
							JOptionPane.showMessageDialog(null, "L'inserimento non è riuscito");
						refreshTable();
						InserisciCliente += n;
					}

					else {
						if (piva.matches("[0-9]+") == false)
							JOptionPane.showMessageDialog(null, "La partita iva può contenere solo numeri");
						else
							JOptionPane.showMessageDialog(null, "Non hai inserito tutti i campi");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Non hai inserito correttamente i dati. Riprova!");
				}
			}
		});
		contentPane.add(btnInserisciCliente);

		JButton btnInseirsciNumeroDi = new JButton("Inseirsci Telefono");
		btnInseirsciNumeroDi.setBounds(57, 357, 214, 38);
		btnInseirsciNumeroDi.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInseirsciNumeroDi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String telefono = JOptionPane.showInputDialog("Digita numero di telefono");
				InserisciCliente op1 = new InserisciCliente();
				if (telefono.matches("[0-9]+") == false)
					JOptionPane.showMessageDialog(null, "Il numero di telefono  può contenere solo numeri");
				else {
					if (!(textPIVA.getText().isEmpty()) || InserisciCliente != 0) {
						String idCliente = textPIVA.getText();
						int n = op1.inserisciNuovoNumeroTelefonoCliente(idCliente, telefono);
						if (n == 0)
							JOptionPane.showMessageDialog(null, "L'inserimento non è riuscito");

						NumeroDiTelefono += n;
						refreshTableTelefonoCliente(idCliente);

					} else
						JOptionPane.showMessageDialog(btnInseirsciNumeroDi, "Inserisci prima la PIVA");
				}
			}
		});
		contentPane.add(btnInseirsciNumeroDi);

		JLabel lblNewLabel_1 = new JLabel("Inserisci i dati del cliente");
		lblNewLabel_1.setBounds(57, 99, 201, 13);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setForeground(Color.BLACK);
		contentPane.add(lblNewLabel_1);

		JButton btnVisualizzaElencoClienti = new JButton("Visualizza elenco clienti");
		btnVisualizzaElencoClienti.setBounds(455, 25, 214, 38);
		btnVisualizzaElencoClienti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTable();
			}
		});
		btnVisualizzaElencoClienti.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnVisualizzaElencoClienti);

		JButton btnVisualizzaElencoOrdini = new JButton("Visualizza elenco ordini");
		btnVisualizzaElencoOrdini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTableOrdini();
			}
		});
		btnVisualizzaElencoOrdini.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVisualizzaElencoOrdini.setBounds(721, 25, 214, 38);
		contentPane.add(btnVisualizzaElencoOrdini);

		textFieldData = new JTextField();
		textFieldData.setColumns(10);
		textFieldData.setBounds(158, 468, 128, 19);
		contentPane.add(textFieldData);

		JLabel lblDa = new JLabel("Data");
		lblDa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDa.setBounds(57, 471, 91, 13);
		contentPane.add(lblDa);

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipo.setBounds(57, 500, 91, 13);
		contentPane.add(lblTipo);

		JLabel lblStato = new JLabel("Stato");
		lblStato.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStato.setBounds(57, 535, 91, 13);
		contentPane.add(lblStato);

		comboBoxTipoOrdine = new JComboBox<String>();
		comboBoxTipoOrdine.setBounds(158, 498, 128, 21);
		comboBoxTipoOrdine.addItem("noleggio");
		comboBoxTipoOrdine.addItem("acquisto");
		comboBoxTipoOrdine.addItem("rinnovo noleggio");
		contentPane.add(comboBoxTipoOrdine);

		comboBoxStato = new JComboBox<String>();
		comboBoxStato.setBounds(158, 527, 128, 21);
		comboBoxStato.addItem("in lavorazione");
		comboBoxStato.addItem("chiuso");
		contentPane.add(comboBoxStato);

		comboBoxNomeProdotto = new JComboBox<String>();
		comboBoxNomeProdotto.setBounds(511, 472, 158, 21);
		comboBoxNomeProdotto.addItem("registratore di cassa");
		comboBoxNomeProdotto.addItem("cassetto contanti");
		comboBoxNomeProdotto.addItem("scanner codice a barre");
		comboBoxNomeProdotto.addItem("display di cortesia");
		comboBoxNomeProdotto.addItem("stampante digitale");
		contentPane.add(comboBoxNomeProdotto);

		JLabel lblTipologiaProdotto = new JLabel("Nome Prodotto");
		lblTipologiaProdotto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipologiaProdotto.setBounds(393, 470, 108, 13);
		contentPane.add(lblTipologiaProdotto);

		JLabel lblModello = new JLabel("Modello");
		lblModello.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModello.setBounds(393, 503, 91, 13);
		contentPane.add(lblModello);

		JLabel lblQunantit = new JLabel("Qunantità");
		lblQunantit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQunantit.setBounds(393, 528, 91, 13);
		contentPane.add(lblQunantit);

		textFieldModello = new JTextField();
		textFieldModello.setColumns(10);
		textFieldModello.setBounds(511, 500, 158, 19);
		contentPane.add(textFieldModello);

		textFieldQuantità = new JTextField();
		textFieldQuantità.setColumns(10);
		textFieldQuantità.setBounds(511, 529, 158, 19);
		contentPane.add(textFieldQuantità);

		JLabel lblNewLabel_1_1 = new JLabel("Inserisci i dati dell'ordine");
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(80, 445, 195, 13);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Inserisci i dati degli articoli");
		lblNewLabel_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(410, 449, 214, 13);
		contentPane.add(lblNewLabel_1_1_1);

		JButton btnInserisciOrdine = new JButton("Inserisci Ordine");
		btnInserisciOrdine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat df = new SimpleDateFormat("dd MM yyyy");
				Calendar cal = Calendar.getInstance();
				try {

					cal.setTime(df.parse(textFieldData.getText().toString()));
					Date date = new Date(cal.getTimeInMillis());

					String tipo = comboBoxTipoOrdine.getSelectedItem().toString();
					tipo.trim();
					String stato = comboBoxStato.getSelectedItem().toString();
					stato.trim();
					String id = textPIVA.getText();
					id.trim();

					InserisciOrdine op12 = new InserisciOrdine();
					int n;
					if ((n = op12.inserisciOridneCliente(date, tipo, stato, id)) != 0) {
						codiceOrdine = op12.torvaCodiceOrdineCliente();
						op12.inserisciFatturazioneCliente(codiceOrdine);
					}

					JOptionPane.showMessageDialog(null, "L'inserimento è andato a buon fine");

					contaInserisciOrdini += n;
					refreshTableOrdini();
				} catch (ParseException e1)

				{
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnInserisciOrdine.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInserisciOrdine.setBounds(72, 573, 214, 38);
		contentPane.add(btnInserisciOrdine);

		JButton btnInserisciArticoli = new JButton("Inserisci Articolo");
		btnInserisciArticoli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InserisciOrdine op12 = new InserisciOrdine();
				ResultSet rs = null;
				Connection con = null;
				try {
					con = DBConnectionPool.getConnection();
					if (contaInserisciOrdini != 0) {
						modello = textFieldModello.getText();

						nomeProdotto = comboBoxNomeProdotto.getSelectedItem().toString();

						pezzi1 = Integer.parseInt(textFieldQuantità.getText());

						if (nomeProdotto.equals("stampante digitale")) {
							String sql = "SELECT numeropezzi FROM stampanteDigitale WHERE modello=?";
							PreparedStatement ps = con.prepareStatement(sql);
							ps.setString(1, modello);
							rs = ps.executeQuery();
							rs.next();
							int numeroPezzi = rs.getInt("numeropezzi");
							if (numeroPezzi == 0)
								JOptionPane.showMessageDialog(null, "Questo artciolo non è presente nel magazzino");
							if (numeroPezzi >= pezzi1) {
								op12.inserisciForma(modello, codiceOrdine, pezzi1);
								op12.aggiornaStampanteDigitaleCliente(pezzi1, modello);
							} else
								JOptionPane.showMessageDialog(null,
										"La quantità inserita è maggiore dei pezzi disponibili in magazzino");

						} else {
							String sql = "SELECT numeropezzi FROM prodottoContabile WHERE modello=?";
							PreparedStatement ps = con.prepareStatement(sql);
							ps.setString(1, modello);
							rs = ps.executeQuery();
							rs.next();
							int numeroPezzi = rs.getInt("numeropezzi");
							if (numeroPezzi >= pezzi1) {

								op12.inserisciSpecifica(modello, codiceOrdine, pezzi1);
								op12.aggiornaProdottiContabiliCliente(pezzi1, modello);
							}

							else
								JOptionPane.showMessageDialog(null,
										"La quantità inserita è maggiore dei pezzi disponibili in magazzino");
						}
					} else
						JOptionPane.showMessageDialog(null, "Devi inserire prima l'ordine");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Non hai inserito correttamente la quantità");
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

				contaInserisciArticolo++;
			}

		});
		btnInserisciArticoli.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInserisciArticoli.setBounds(423, 573, 214, 38);
		contentPane.add(btnInserisciArticoli);
	}
}
