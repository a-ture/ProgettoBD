package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.StatisticheDenaro;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class Operazione18 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textGuadagni;
	private JTextField textEntrate;
	private JTextField textUscite;
	private JTable tableProdotti;
	private JComboBox<String> comboBoxPeriodo;
	private JTextField textFieldPiva;
	private JTextField textFieldNomeSocieta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione18 frame = new Operazione18();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Operazione18() {
		setTitle("Operazione 18");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 923, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnOperazione23 = new JButton("Conferma");
		btnOperazione23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Calendar c = Calendar.getInstance();

				if (comboBoxPeriodo.getSelectedItem().toString() == "Ultimo mese") {
					c.add(Calendar.MONTH, -1);

				} else {if (comboBoxPeriodo.getSelectedItem().toString() == "Ultimi sei mesi") 
					c.add(Calendar.MONTH, -6);
				else {
					if (comboBoxPeriodo.getSelectedItem().toString() == "Ultima settimana")
						c.add(Calendar.DAY_OF_MONTH, -7);

					else if (comboBoxPeriodo.getSelectedItem().toString() == "Ultimo anno")
						c.add(Calendar.YEAR, -1);

				}}

				Calendar c1 = Calendar.getInstance();

				java.sql.Date date1 = new Date(c1.getTimeInMillis());

				java.sql.Date date = new Date(c.getTimeInMillis());

				StatisticheDenaro op23 = new StatisticheDenaro();
				double entrate = op23.CalcolaEntrate(date, date1);
				double uscite = op23.CalcolaUscite(date, date1);
				if (entrate == -1 || uscite == -1)
					JOptionPane.showMessageDialog(null, "Non ci sono abbastanza dati per effetuare la statistica");
				else {
					textEntrate.setText("" + entrate);
					textUscite.setText("" + uscite);
					textGuadagni.setText("" + (entrate - uscite));
				}

				Connection con = null;
				Statement st = null;
				ResultSet rs = null;

				try {
					con = DBConnectionPool.getConnection();
					st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					
					
					String sql = "SELECT idProdottoContabile, count(Quantità)"
							+ "FROM  specifica GROUP BY idProdottoContabile";
						

				    rs = st.executeQuery(sql);
					tableProdotti.setModel(DbUtils.resultSetToTableModel(rs));
					rs.relative(-1);

					while (rs.next()) {
						tableProdotti.setModel(DbUtils.resultSetToTableModel(rs));
					}
					
					
					
				    sql="SELECT piva, nomesocietà  FROM cliente  WHERE numeroassistenzerichieste >=ALL(SELECT numeroassistenzerichieste FROM cliente)";
					rs = st.executeQuery(sql);
					rs.next();
					textFieldNomeSocieta.setText(rs.getString("nomesocietà")); 
					textFieldPiva.setText(rs.getString("piva"));
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
		btnOperazione23.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnOperazione23.setBounds(47, 121, 156, 46);
		contentPane.add(btnOperazione23);

		comboBoxPeriodo = new JComboBox<String>();
		comboBoxPeriodo.setBounds(31, 77, 172, 21);
		contentPane.add(comboBoxPeriodo);
		comboBoxPeriodo.addItem("Ultima Settimana");
		comboBoxPeriodo.addItem("Ultimo Mese");
		comboBoxPeriodo.addItem("Ultimi Sei Mesi");
		comboBoxPeriodo.addItem("Ultimo anno");

		JLabel lblNewLabel = new JLabel("Scegli il perido di riferimento");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(31, 46, 228, 21);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("I guadagni sono");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(310, 108, 110, 21);
		contentPane.add(lblNewLabel_1);

		textGuadagni = new JTextField();
		textGuadagni.setBounds(450, 111, 172, 19);
		contentPane.add(textGuadagni);
		textGuadagni.setColumns(10);

		textEntrate = new JTextField();
		textEntrate.setColumns(10);
		textEntrate.setBounds(450, 78, 172, 19);
		contentPane.add(textEntrate);

		textUscite = new JTextField();
		textUscite.setColumns(10);
		textUscite.setBounds(450, 49, 172, 19);
		contentPane.add(textUscite);

		JLabel lblNewLabel_1_1 = new JLabel("Le entrate sono");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(310, 77, 110, 21);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Le uscite sono");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(310, 46, 130, 21);
		contentPane.add(lblNewLabel_1_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(310, 172, 589, 229);
		contentPane.add(scrollPane);

		tableProdotti = new JTable();
		scrollPane.setViewportView(tableProdotti);

		JLabel lblNewLabel_2 = new JLabel("I Prodotti contabili più venduti sono");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(310, 149, 269, 13);
		contentPane.add(lblNewLabel_2);

		textFieldPiva = new JTextField();
		textFieldPiva.setBounds(141, 246, 156, 19);
		contentPane.add(textFieldPiva);
		textFieldPiva.setColumns(10);

		textFieldNomeSocieta = new JTextField();
		textFieldNomeSocieta.setColumns(10);
		textFieldNomeSocieta.setBounds(141, 275, 156, 19);
		contentPane.add(textFieldNomeSocieta);

		JLabel lblNewLabel_3 = new JLabel("PIVA");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(31, 247, 110, 13);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Il cliente che ha richiesto più assistenze");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(10, 215, 269, 21);
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_3_1 = new JLabel("Nome Società");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3_1.setBounds(31, 278, 110, 13);
		contentPane.add(lblNewLabel_3_1);
	}
}
