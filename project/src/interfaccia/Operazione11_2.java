package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.ModificaOrdine;

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

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Operazione11_2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldData;
	private JTable table;

	private int codice;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione11_2 frame = new Operazione11_2();
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
	
	
	/**
	 * Create the frame.
	 */
	public Operazione11_2() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 965, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Data");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(82, 144, 45, 13);
		contentPane.add(lblNewLabel);
		
		textFieldData = new JTextField();
		textFieldData.setBounds(203, 143, 96, 19);
		contentPane.add(textFieldData);
		textFieldData.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(339, 56, 602, 306);
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

					String sql = "SELECT *  FROM  ordineFornitore WHERE codice=?";

					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, codice);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
					
						textFieldData.setText(rs.getString("data"));
			
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

			}
			}
		);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Modifica");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
			
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				cal.setTime(df.parse(textFieldData.getText().toString()));
				java.sql.Date date = new Date(cal.getTimeInMillis());
				ModificaOrdine op13 = new ModificaOrdine();
				op13.ModificaOrdineFornitore(date,codice);
				refreshTable();
			} catch (ParseException e1) {

				JOptionPane.showMessageDialog(null, e1);
			} 
	
				
				refreshTable();
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(120, 216, 107, 50);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Visualizza elenco ordini");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
			
				refreshTable();
				}
			

		});
				
	
		btnNewButton_1.setBounds(504, 10, 194, 36);
		contentPane.add(btnNewButton_1);
	}

}
