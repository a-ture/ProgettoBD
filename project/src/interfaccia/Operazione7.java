package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.ModificaAssistenza;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
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

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Operazione7 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldData;
	private JTextArea textArea;
	private int numeroChiamata;
	private JComboBox<String> comboBoxTipologia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione7 frame = new Operazione7();
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
	public Operazione7() {
		setTitle("Operazione 7");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 971, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(319, 74, 628, 355);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = table.getSelectedRow();

				numeroChiamata = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());

				Connection con = null;
				try {
					con = DBConnectionPool.getConnection();

					String sql = "SELECT *  FROM  assistenza  WHERE numerochiamata=?";

					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, numeroChiamata);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						textFieldData.setText(rs.getString("data"));
                        comboBoxTipologia.setSelectedItem(rs.getString("tipo")); 
                        textArea.setText(rs.getString("descrizione")); 
                        
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

			}

		});
		scrollPane.setViewportView(table);

		textFieldData = new JTextField();
		textFieldData.setColumns(10);
		textFieldData.setBounds(123, 107, 169, 19);
		contentPane.add(textFieldData);

		comboBoxTipologia = new JComboBox<String>();
		comboBoxTipologia.setBounds(123, 136, 169, 21);
		contentPane.add(comboBoxTipologia);
		comboBoxTipologia.addItem("Sostituzione DGFE");
		comboBoxTipologia.addItem("Riparazione Stampante");
		comboBoxTipologia.addItem("Sostituzione Merce");

		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(25, 108, 88, 13);
		contentPane.add(lblData);

		JLabel lblT = new JLabel("Tipologia");
		lblT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblT.setBounds(25, 136, 88, 17);
		contentPane.add(lblT);

		textArea = new JTextArea();
		textArea.setBounds(25, 204, 269, 125);
		contentPane.add(textArea);

		JLabel lblNewLabel_1 = new JLabel("Descrizione");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(25, 181, 76, 13);
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Visualizza elenco assistenze");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				{
					refreshTable(); 
				}
				}
		});
		btnNewButton.setBounds(475, 28, 282, 36);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Modifica ");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				try {

					String tipologia = comboBoxTipologia.getSelectedItem().toString();
					String descrizione = textArea.getText();

					Calendar cal = Calendar.getInstance();
					cal.setTime(df.parse(textFieldData.getText().toString()));
					java.sql.Date date = new Date(cal.getTimeInMillis());

					ModificaAssistenza op7 = new ModificaAssistenza();
 					op7.ModificaDatiAssistenza(numeroChiamata, date, descrizione,  tipologia);
					refreshTable();

				} catch (ParseException e1) {

					JOptionPane.showMessageDialog(null, e1);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Non hai inserito correttamente i dati per costo");
				}

			}
		});
		btnNewButton_1.setBounds(65, 350, 191, 36);
		contentPane.add(btnNewButton_1);
	}

}
