package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.InserisciStampante;
import operazioni.InserisciOrdine;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Operazione10_2_2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldModello;
	private JTextField textFieldCosto;
	private JTextField textFieldFormatoDiStampa;
	private JTextField textFieldAnnoDiProduzione;
	private JTextField textFieldNPezzi;
	private JComboBox<String> comboBoxMarca;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {

					Operazione10_2_2 frame = new Operazione10_2_2();
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
			}
		});
	}

	/**
	 * Create the frame.
	 */

	private void refreshTable() {
		Connection con = null;
		Statement st = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT *  FROM  stampantedigitale";

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

	public Operazione10_2_2() {

		Operazione10_2 op = new Operazione10_2();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1089, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(385, 95, 680, 353);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		textFieldModello = new JTextField();
		textFieldModello.setBounds(208, 140, 140, 19);
		contentPane.add(textFieldModello);
		textFieldModello.setText(op.getModello());

		textFieldCosto = new JTextField();
		textFieldCosto.setBounds(208, 169, 140, 19);
		contentPane.add(textFieldCosto);
		textFieldCosto.setColumns(10);

		textFieldAnnoDiProduzione = new JTextField();
		textFieldAnnoDiProduzione.setColumns(10);
		textFieldAnnoDiProduzione.setBounds(208, 198, 140, 19);
		contentPane.add(textFieldAnnoDiProduzione);

		textFieldFormatoDiStampa = new JTextField();
		textFieldFormatoDiStampa.setColumns(10);
		textFieldFormatoDiStampa.setBounds(208, 227, 140, 19);
		contentPane.add(textFieldFormatoDiStampa);

		JLabel lblNewLabel = new JLabel("Modello");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(82, 141, 60, 13);
		contentPane.add(lblNewLabel);

		JLabel lblCosto = new JLabel("Costo");
		lblCosto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCosto.setBounds(82, 170, 60, 13);
		contentPane.add(lblCosto);

		JLabel lblAnnoDiProduzione = new JLabel("Anno Di Produzione");
		lblAnnoDiProduzione.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAnnoDiProduzione.setBounds(56, 199, 131, 13);
		contentPane.add(lblAnnoDiProduzione);

		JLabel lblF = new JLabel("Formato Di Stampa Max");
		lblF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblF.setBounds(41, 228, 157, 13);
		contentPane.add(lblF);

		JLabel lblNumeroDiPezzi = new JLabel("Numero di Pezzi");
		lblNumeroDiPezzi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumeroDiPezzi.setBounds(63, 257, 114, 13);
		contentPane.add(lblNumeroDiPezzi);
		

		JLabel lblAziendaProduttirce = new JLabel("Azienda Produttirce");
		lblAziendaProduttirce.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAziendaProduttirce.setBounds(56, 283, 131, 19);
		contentPane.add(lblAziendaProduttirce);

		JButton btnInserisci = new JButton("Inserisci");
		btnInserisci.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String modello = textFieldModello.getText();
				String aziendaProduttrice = comboBoxMarca.getSelectedItem().toString();
				String formatoDiStampa = textFieldFormatoDiStampa.getText();

				try {
					int annoDiProduzione = Integer.parseInt(textFieldAnnoDiProduzione.getText().trim());
					int numeroDiPezzi = Integer.parseInt(textFieldNPezzi.getText().trim());
					double costo = Double.parseDouble(textFieldCosto.getText().trim());

					if (!(modello.isBlank() && aziendaProduttrice.isBlank() && formatoDiStampa.isBlank() && costo == 0
							&& annoDiProduzione == 0)) {
						InserisciStampante op10 = new InserisciStampante();
						op10.inserisciNuovaStampante(modello, costo, numeroDiPezzi, formatoDiStampa, annoDiProduzione,
								aziendaProduttrice);
						InserisciOrdine op12 = new InserisciOrdine();
						Operazione10_2 op = new Operazione10_2();
						int codiceOrdine = op.getCodiceOrdine();

						op12.inserisciDettaglia(modello, codiceOrdine, numeroDiPezzi);
					
						refreshTable();

					} else {
						JOptionPane.showMessageDialog(null, "Non hai inserito tutti i campi");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,
							"Non hai inserito correttamente i dati per: anno di produzione o numero di pezzi o costo \n Ritenta");
				}
			}

		});
		btnInserisci.setBounds(96, 343, 252, 39);
		contentPane.add(btnInserisci);

		JButton btnCarica = new JButton("Visualizza elenco stampanti");
		btnCarica.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCarica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		btnCarica.setBounds(598, 46, 299, 39);
		contentPane.add(btnCarica);

		textFieldNPezzi = new JTextField();
		textFieldNPezzi.setColumns(10);
		textFieldNPezzi.setBounds(208, 256, 140, 19);
		contentPane.add(textFieldNPezzi);
		System.out.print(op.getPezzi());
		textFieldNPezzi.setText(""+op.getPezzi());
		
		comboBoxMarca = new JComboBox<String>();
		comboBoxMarca.setBounds(208, 284, 140, 21);
		contentPane.add(comboBoxMarca);
		comboBoxMarca.addItem("Develop");
		comboBoxMarca.addItem("Canon");
		comboBoxMarca.addItem("Konica Minolta");
		
		
		JLabel lblNewLabel_1 = new JLabel("Inserisci i dati");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(118, 95, 116, 13);
		contentPane.add(lblNewLabel_1);

	}
}
