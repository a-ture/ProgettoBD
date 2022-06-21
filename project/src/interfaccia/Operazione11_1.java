package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.ModificaOrdine;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Operazione11_1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldPiva;
	private JTextField textFieldData;
	private JComboBox<String> comboBoxTipo;
	private JComboBox<String> comboBoxStato;
	private int codice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione11_1 frame = new Operazione11_1();
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
	public Operazione11_1() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 877, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(321, 55, 529, 359);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = table.getSelectedRow();

				codice = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());

				Connection con = null;
				try {
					con = DBConnectionPool.getConnection();

					String sql = "SELECT *  FROM  ordineCliente  WHERE codice=?";

					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, codice);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						textFieldPiva.setText(rs.getString("IdCliente"));
						textFieldData.setText(rs.getString("data"));
						comboBoxStato.setSelectedItem(rs.getString("stato"));
						comboBoxTipo.setSelectedItem(rs.getString("tipo"));
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

			}

		});
		scrollPane.setViewportView(table);

		textFieldPiva = new JTextField();
		textFieldPiva.setBounds(127, 104, 152, 19);
		contentPane.add(textFieldPiva);
		textFieldPiva.setColumns(10);

		JLabel lblNewLabel = new JLabel("Piva");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(72, 105, 45, 13);
		contentPane.add(lblNewLabel);

		textFieldData = new JTextField();
		textFieldData.setColumns(10);
		textFieldData.setBounds(127, 133, 152, 19);
		contentPane.add(textFieldData);

		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(72, 134, 45, 13);
		contentPane.add(lblData);

		JButton btnNewButton = new JButton("Visualizza Elenco Ordini");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTable();
			}

		});
		btnNewButton.setBounds(479, 10, 180, 35);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1_1_2 = new JLabel("Stato");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_2.setBounds(72, 167, 45, 13);
		contentPane.add(lblNewLabel_1_1_2);

		JLabel lblNewLabel_1_1_3 = new JLabel("Tipo");
		lblNewLabel_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_3.setBounds(72, 190, 45, 19);
		contentPane.add(lblNewLabel_1_1_3);

		JButton btnNewButton_1 = new JButton("Modifica Dati");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					String nuovoStato = comboBoxStato.getSelectedItem().toString();
					String nuovoTipo = comboBoxTipo.getSelectedItem().toString();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					cal.setTime(df.parse(textFieldData.getText().toString()));
					java.sql.Date date = new Date(cal.getTimeInMillis());
					ModificaOrdine op13 = new ModificaOrdine();
					op13.ModificaOrdineCliente(nuovoStato, nuovoTipo, date, codice);
					refreshTable();
				} catch (ParseException e1) {

					JOptionPane.showMessageDialog(null, e1);
				} 

			}
		});
		btnNewButton_1.setBounds(88, 308, 143, 49);
		contentPane.add(btnNewButton_1);

          comboBoxTipo = new JComboBox<String>();
		comboBoxTipo.setBounds(127, 191, 152, 21);
		comboBoxTipo.addItem("acquisto");
		comboBoxTipo.addItem("noleggio");
		comboBoxTipo.addItem("rinnovo noleggio");
		contentPane.add(comboBoxTipo);
		
	    comboBoxStato = new JComboBox<String>();
		comboBoxStato.setBounds(127, 162, 152, 21);
		comboBoxStato.addItem("in lavorazione");
		comboBoxStato.addItem("chiuso"); 
		comboBoxStato.addItem("annullato");
		contentPane.add(comboBoxStato);
	}
}
