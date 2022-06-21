package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;
import operazioni.ModificaDatiPagamento;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Operazione5 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldData;
	private JTextField textFieldImporto;
	private JComboBox<String> comboBoxModalitàDiPagamento;
	private int ID = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione5 frame = new Operazione5();
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

			String sql = "SELECT * FROM pagamento";
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
	public Operazione5() {
		setTitle("Operazione5");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1037, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(326, 80, 687, 472);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int row = table.getSelectedRow();

				ID = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());

				Connection con = null;
				try {
					con = DBConnectionPool.getConnection();

					String sql = "SELECT *  FROM  pagamento   WHERE id=?";

					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, ID);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {

						textFieldData.setText(rs.getString("data"));
						comboBoxModalitàDiPagamento.setSelectedItem(rs.getString("modalità"));
						textFieldImporto.setText(rs.getString("Importo"));

					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

			}
		});
		scrollPane.setViewportView(table);

		textFieldData = new JTextField();
		textFieldData.setBounds(178, 169, 126, 19);
		contentPane.add(textFieldData);
		textFieldData.setColumns(10);

		JLabel lblNewLabel = new JLabel("Data ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(53, 170, 56, 13);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Importo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(41, 203, 115, 19);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Modalità ai pagamento");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 240, 158, 19);
		contentPane.add(lblNewLabel_1_1);

		JButton btnNewButton = new JButton("Visualizza elenco  pagamenti");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTable();
			}

		});
		btnNewButton.setBounds(531, 34, 291, 36);
		contentPane.add(btnNewButton);

		JButton btnModifica = new JButton("Modifica");
		btnModifica.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ModificaDatiPagamento op = new ModificaDatiPagamento();

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				try {
					cal.setTime(df.parse(textFieldData.getText()));
					java.sql.Date date = new Date(cal.getTimeInMillis());
					double importo = Double.parseDouble(textFieldImporto.getText());
					String modalita = comboBoxModalitàDiPagamento.getSelectedItem().toString();

					if (importo != 0 && ID != 0)
						op.modificaPagamento(date, modalita, importo, ID);

					else
						JOptionPane.showMessageDialog(null, "Non hai inserito corretamente i dati");

					refreshTable();
				} catch (ParseException e1) {

					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnModifica.setBounds(91, 332, 187, 29);
		contentPane.add(btnModifica);

		comboBoxModalitàDiPagamento = new JComboBox<String>();
		comboBoxModalitàDiPagamento.setBounds(178, 238, 126, 21);
		contentPane.add(comboBoxModalitàDiPagamento);
		comboBoxModalitàDiPagamento.addItem("contanti");
		comboBoxModalitàDiPagamento.addItem("bonifico");
		comboBoxModalitàDiPagamento.addItem("carta di credito");

		textFieldImporto = new JTextField();
		textFieldImporto.setColumns(10);
		textFieldImporto.setBounds(178, 205, 126, 19);
		contentPane.add(textFieldImporto);
	}
}
