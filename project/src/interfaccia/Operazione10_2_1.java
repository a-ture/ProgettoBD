package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.InserisciProdottoContabile;
import operazioni.InserisciOrdine;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Operazione10_2_1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldModello;
	private JTextField textFieldPrezzo;
	private JTextField textFieldNumeroDiPezzi;
	private JTextField textFieldAnnoDiProduzione;
	private JComboBox<String> comboBoxTipologia;
	private JComboBox<String> comboBoxMarca;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione10_2_1 frame = new Operazione10_2_1();
					frame.setVisible(true);

						
						frame.addWindowListener(new WindowListener() {

							@Override
							public void windowClosing(WindowEvent e) {
								Operazione10_2 op = new Operazione10_2();
								op.setVisible(true);
								
							}

							public void windowOpened(WindowEvent e) {
							}

							public void windowClosed(WindowEvent e) {
								Operazione10_2 op = new Operazione10_2();
								op.setVisible(true);
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
				
				}}
			
		);
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
	public Operazione10_2_1() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1156, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Operazione10_2 op = new Operazione10_2();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(367, 55, 765, 382);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Visualizza elenco prodotti contabili");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTable();
			}
		});
		btnNewButton.setBounds(600, 10, 341, 37);
		contentPane.add(btnNewButton);

		comboBoxTipologia = new JComboBox<String>();
		comboBoxTipologia.setBounds(178, 148, 157, 21);
		contentPane.add(comboBoxTipologia);
		comboBoxTipologia.addItem("Registratore di cassa");
		comboBoxTipologia.addItem("Cassetto contanti");
		comboBoxTipologia.addItem("Scanner Codici a Barre");
		comboBoxTipologia.addItem("Display di Cortesia");
comboBoxTipologia.setSelectedItem(op.getNomeProdotto());

		textFieldModello = new JTextField();
		textFieldModello.setBounds(178, 119, 157, 19);
		contentPane.add(textFieldModello);
		textFieldModello.setColumns(10);
		textFieldModello.setText(op.getModello());

		textFieldPrezzo = new JTextField();
		textFieldPrezzo.setColumns(10);
		textFieldPrezzo.setBounds(178, 179, 157, 19);
	
		contentPane.add(textFieldPrezzo);

		textFieldNumeroDiPezzi = new JTextField();
		textFieldNumeroDiPezzi.setColumns(10);
		textFieldNumeroDiPezzi.setBounds(178, 210, 157, 19);
		contentPane.add(textFieldNumeroDiPezzi);
		textFieldNumeroDiPezzi.setText(""+op.getPezzi());

		textFieldAnnoDiProduzione = new JTextField();
		textFieldAnnoDiProduzione.setColumns(10);
		textFieldAnnoDiProduzione.setBounds(178, 268, 157, 19);
		contentPane.add(textFieldAnnoDiProduzione);

		JLabel lblNewLabel = new JLabel("Modello");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(79, 120, 54, 13);
		contentPane.add(lblNewLabel);

		JLabel lblTipologia = new JLabel("Tipologia");
		lblTipologia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipologia.setBounds(79, 148, 73, 17);
		contentPane.add(lblTipologia);

		JLabel lblPrezzo = new JLabel("Prezzo");
		lblPrezzo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrezzo.setBounds(79, 180, 54, 13);
		contentPane.add(lblPrezzo);

		JLabel lblNumeroDiPezzi = new JLabel("Numero Di Pezzi");
		lblNumeroDiPezzi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumeroDiPezzi.setBounds(47, 211, 122, 13);
		contentPane.add(lblNumeroDiPezzi);

		JLabel lblAziendaProduttrice = new JLabel("Azienda Produttrice");
		lblAziendaProduttrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAziendaProduttrice.setBounds(42, 240, 126, 13);
		contentPane.add(lblAziendaProduttrice);

		JLabel lblAnnoDiProduzione = new JLabel("Anno Di Produzione");
		lblAnnoDiProduzione.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAnnoDiProduzione.setBounds(42, 269, 140, 18);
		contentPane.add(lblAnnoDiProduzione);

		JButton btnNewButton_1 = new JButton("Inserisci");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					InserisciProdottoContabile op = new InserisciProdottoContabile();

					String modello = textFieldModello.getText();
					String tipologia = comboBoxTipologia.getSelectedItem().toString();
					String marca = comboBoxMarca.getSelectedItem().toString();

					int anno = Integer.parseInt(textFieldAnnoDiProduzione.getText());
					int nPezzi = Integer.parseInt(textFieldNumeroDiPezzi.getText());
					double prezzo = Double.parseDouble(textFieldPrezzo.getText());
					if (!(modello.isBlank() && marca.isBlank() && prezzo == 0 && anno == 0)) {

						op.inserisciNuovoProdottoContabile(modello, marca, anno, nPezzi, prezzo, tipologia);
						refreshTable();

						InserisciOrdine op12 = new InserisciOrdine();
						Operazione10_2 op1 = new Operazione10_2();
						int codiceOrdine = op1.getCodiceOrdine();

						op12.inserisciCompone(modello, codiceOrdine, nPezzi);

						refreshTable();
					} else {
						JOptionPane.showMessageDialog(null, "Non hai inserito tutti i campi");
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnNewButton_1.setBounds(115, 334, 157, 44);
		contentPane.add(btnNewButton_1);

		comboBoxMarca = new JComboBox<String>();
		comboBoxMarca.setBounds(178, 237, 157, 21);
		contentPane.add(comboBoxMarca);

		JLabel lblNewLabel_1 = new JLabel("Inserisici i dati");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(128, 76, 126, 13);
		contentPane.add(lblNewLabel_1);
		comboBoxMarca.addItem("RCH");
		comboBoxMarca.addItem("EMOTIQ");
		comboBoxMarca.addItem("Olivetti");

	}
}
