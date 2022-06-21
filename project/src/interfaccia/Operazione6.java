package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.InserisciAssistenza;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Operazione6 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldData;
	private JComboBox<String> comboBoxTipologia;
	private JTextField textFieldPiva;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione6 frame = new Operazione6();
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

			String sql = "SELECT * FROM assistenza";
			rs = st.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));
			rs.relative(-1);
			while (rs.next()) {

				table.setModel(DbUtils.resultSetToTableModel(rs));
			}

		} catch (SQLException s) {
			System.err.println(s.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
				System.err.println(s.getMessage());
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public Operazione6() {
		setTitle("Operazione 6");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1026, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(291, 85, 693, 350);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("PIVA");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(25, 115, 63, 13);
		contentPane.add(lblNewLabel);

		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(25, 138, 45, 13);
		contentPane.add(lblData);

		JLabel lblTipologia = new JLabel("Tipologia");
		lblTipologia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipologia.setBounds(25, 161, 63, 21);
		contentPane.add(lblTipologia);

		comboBoxTipologia = new JComboBox<String>();
		comboBoxTipologia.setBounds(98, 163, 165, 21);
		contentPane.add(comboBoxTipologia);
		comboBoxTipologia.addItem("Sostituzione DGFE");
		comboBoxTipologia.addItem("Riparazione Stampante");
		comboBoxTipologia.addItem("Sostituzione Merce");

		textFieldData = new JTextField();
		textFieldData.setBounds(98, 137, 165, 19);
		contentPane.add(textFieldData);
		textFieldData.setColumns(10);

		JTextArea textAreaDescrizione = new JTextArea();
		textAreaDescrizione.setBounds(25, 234, 238, 101);
		contentPane.add(textAreaDescrizione);

		JLabel lblNewLabel_1 = new JLabel("Descrizione");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(25, 211, 86, 13);
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Visualizza elenco assistenze");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}

		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(524, 41, 255, 34);
		contentPane.add(btnNewButton);

		JButton btnInserisci = new JButton("Inserisci");
		btnInserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DateFormat df = new SimpleDateFormat("dd MM yyyy");

				try {

					String idCliente = textFieldPiva.getText();
					String tipologia = comboBoxTipologia.getSelectedItem().toString();

					String descrizione = textAreaDescrizione.getText();

					Calendar cal = Calendar.getInstance();
					cal.setTime(df.parse(textFieldData.getText()));
					java.sql.Date date = new Date(cal.getTimeInMillis());
					if (idCliente.matches("[0-9]+" ) == false)
						JOptionPane.showMessageDialog(null, "Il numero di telefono può contenere solo numeri");
					else {
						InserisciAssistenza op6 = new InserisciAssistenza();
						if (op6.inserisciNuovaAssistenza(date, descrizione, tipologia, idCliente) != 0) {
							int idAssistenza = op6.trovaIdAssistenza();
							op6.inserisciFatturazioneAssistenza(idAssistenza);

						}

						else
							JOptionPane.showMessageDialog(null, "L'inserimento non ha avuto sucesso");

						refreshTable();
					}
				} catch (ParseException e1) {

					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnInserisci.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInserisci.setBounds(64, 345, 165, 34);
		contentPane.add(btnInserisci);

		textFieldPiva = new JTextField();
		textFieldPiva.setBounds(98, 114, 165, 19);
		contentPane.add(textFieldPiva);
		textFieldPiva.setColumns(10);
	}
}
