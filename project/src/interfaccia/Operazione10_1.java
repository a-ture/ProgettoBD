package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.InserisciOrdine;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
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
import javax.swing.JScrollPane;

public class Operazione10_1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnInserisciOrdine;
	private JLabel lblPivaCliente;
	private JLabel lblData;
	private JLabel lblTipoOrdine;
	private JLabel lblStato;
	private JTextField textData;
	private JButton btnInserisciArticolo;
	private JComboBox<String> comboBoxTipoOrdine;
	private JComboBox<String> comboBoxStato;
	private JLabel lblModello;
	private JLabel lblQuantit;
	private JComboBox<String> comboBoxNomeProdotto;
	private JTextField textFieldQuantità;

	private static int contaInserisciOrdini = 0;
	private static int contaInserisciArticolo = 0;
	private static int codiceOrdine = 0;
	private JTextField textFieldModello;
	private JTextField textFieldPiva;
	private JTable table;

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
					Operazione10_1 frame = new Operazione10_1();
					frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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

	/**
	 * Create the frame.
	 */
	public Operazione10_1() {
		setTitle("Operazione 12");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1143, 595);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Inserisci i dati del tuo ordine");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(57, 40, 248, 70);
		contentPane.add(lblNewLabel);

		btnInserisciOrdine = new JButton("Inserisci Ordine");
		btnInserisciOrdine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DateFormat df = new SimpleDateFormat("dd MM yyyy");
				Calendar cal = Calendar.getInstance();
				try {

					cal.setTime(df.parse(textData.getText().toString()));
					Date date = new Date(cal.getTimeInMillis());

					String tipo = comboBoxTipoOrdine.getSelectedItem().toString();
					tipo.trim();
					String stato = comboBoxStato.getSelectedItem().toString();
					stato.trim();
					String id = textFieldPiva.getText();
					id.trim();

					InserisciOrdine op12 = new InserisciOrdine();
					int n;
					if ((n = op12.inserisciOridneCliente(date, tipo, stato, id)) != 0) {
						codiceOrdine = op12.torvaCodiceOrdineCliente();
						op12.inserisciFatturazioneCliente(codiceOrdine);
						refreshTable();
					}

					JOptionPane.showMessageDialog(null, "L'inserimento è andato a buon fine");

					contaInserisciOrdini += n;
					refreshTable();
				} catch (ParseException e1)

				{
					JOptionPane.showMessageDialog(null, e1);
				}

			}
		});
		btnInserisciOrdine.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInserisciOrdine.setBounds(113, 238, 192, 41);
		contentPane.add(btnInserisciOrdine);

		lblPivaCliente = new JLabel("PIVA Cliente");
		lblPivaCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPivaCliente.setBounds(45, 106, 83, 22);
		contentPane.add(lblPivaCliente);

		lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(45, 138, 83, 22);
		contentPane.add(lblData);

		lblTipoOrdine = new JLabel("Tipo Ordine");
		lblTipoOrdine.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipoOrdine.setBounds(45, 170, 83, 22);
		contentPane.add(lblTipoOrdine);

		lblStato = new JLabel("Stato");
		lblStato.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStato.setBounds(45, 202, 83, 22);
		contentPane.add(lblStato);

		comboBoxTipoOrdine = new JComboBox<String>();
		comboBoxTipoOrdine.setBounds(177, 173, 161, 21);
		contentPane.add(comboBoxTipoOrdine);
		comboBoxTipoOrdine.addItem("noleggio");
		comboBoxTipoOrdine.addItem("acquisto");
		comboBoxTipoOrdine.addItem("rinnovo noleggio");

		comboBoxStato = new JComboBox<String>();
		comboBoxStato.setBounds(177, 205, 161, 21);
		contentPane.add(comboBoxStato);
		comboBoxStato.addItem("in lavorazione");
		comboBoxStato.addItem("chiuso");

		textData = new JTextField();
		textData.setBounds(177, 142, 161, 19);
		contentPane.add(textData);
		textData.setColumns(10);

		JButton btnStampaTabellaOrdineCliente = new JButton("Visualizza elenco ordini");
		btnStampaTabellaOrdineCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTable();
			}

		});
		btnStampaTabellaOrdineCliente.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnStampaTabellaOrdineCliente.setBounds(569, 55, 293, 41);
		contentPane.add(btnStampaTabellaOrdineCliente);

		btnInserisciArticolo = new JButton("Inserisci Articolo");
		btnInserisciArticolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InserisciOrdine op12 = new InserisciOrdine();
				ResultSet rs=null;
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
							if (numeroPezzi==0) JOptionPane.showMessageDialog(null, "Questo artciolo non è presente nel magazzino");
							if (numeroPezzi >= pezzi1) {
								op12.inserisciForma(modello, codiceOrdine, pezzi1);
								op12.aggiornaStampanteDigitaleCliente(pezzi1, modello);
							} 
							else
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
				}finally {
					try {
						if (rs != null)
							rs.close();
						DBConnectionPool.releaseConnection(con);
					} catch (SQLException s) {
						JOptionPane.showMessageDialog(null, s);
					}}

				contaInserisciArticolo++;
			}
		});
		btnInserisciArticolo.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInserisciArticolo.setBounds(113, 486, 192, 41);
		contentPane.add(btnInserisciArticolo);

		JLabel lblNomeProdotto = new JLabel("Nome Prodotto");
		lblNomeProdotto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeProdotto.setBounds(45, 384, 109, 22);
		contentPane.add(lblNomeProdotto);

		lblModello = new JLabel("Modello");
		lblModello.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModello.setBounds(45, 423, 83, 22);
		contentPane.add(lblModello);

		lblQuantit = new JLabel("Quantità");
		lblQuantit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantit.setBounds(45, 455, 83, 22);
		contentPane.add(lblQuantit);

		comboBoxNomeProdotto = new JComboBox<String>();
		comboBoxNomeProdotto.setBounds(177, 387, 161, 21);
		contentPane.add(comboBoxNomeProdotto);
		comboBoxNomeProdotto.addItem("registratore di cassa");
		comboBoxNomeProdotto.addItem("cassetto contanti");
		comboBoxNomeProdotto.addItem("scanner codice a barre");
		comboBoxNomeProdotto.addItem("display di cortesia");
		comboBoxNomeProdotto.addItem("stampante digitale");

		textFieldQuantità = new JTextField();
		textFieldQuantità.setColumns(10);
		textFieldQuantità.setBounds(177, 459, 161, 19);
		contentPane.add(textFieldQuantità);

		textFieldModello = new JTextField();
		textFieldModello.setBounds(177, 427, 161, 19);
		contentPane.add(textFieldModello);
		textFieldModello.setColumns(10);

		textFieldPiva = new JTextField();
		textFieldPiva.setColumns(10);
		textFieldPiva.setBounds(177, 106, 161, 19);
		contentPane.add(textFieldPiva);

		JLabel lblInserisciIDati = new JLabel("Inserisci i dati degli articoli");
		lblInserisciIDati.setHorizontalAlignment(SwingConstants.CENTER);
		lblInserisciIDati.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblInserisciIDati.setBounds(45, 289, 248, 70);
		contentPane.add(lblInserisciIDati);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(384, 112, 735, 436);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

	}
}
