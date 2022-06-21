package interfaccia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import operazioni.DBConnectionPool;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Operazione21 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane_1;
	private JTextField textFieldPiva;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Operazione21 frame = new Operazione21();
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
	public Operazione21() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1249, 647);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(272, 101, 953, 478);
		contentPane.add(scrollPane_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	
		
		JLabel lblCliente = new JLabel("Inserisci la PIVA del cliente");
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCliente.setBounds(37, 241, 198, 21);
		contentPane.add(lblCliente);
		
		JButton btnOperazione28 = new JButton("Visualizza risultato");
		btnOperazione28.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
		
				ResultSet rs = null;

				try {
					con = DBConnectionPool.getConnection();
					
		
					String sql = "SELECT  c.piva,c.nomesocietà, "
							+ "	o.codice,p.importo,o.data,p.modalità,o.tipo,o.stato, "
							+ "	p.data"
							+ "	FROM cliente AS c, ordinecliente AS o , pagamento AS p, fatturazionecliente AS f"
							+ "	WHERE c.piva = o.idcliente  AND p.id=f.idPagamento and f.idordineCliente=o.codice AND c.piva=?";
					PreparedStatement ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setString(1, textFieldPiva.getText().toString());
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
						JOptionPane.showMessageDialog(null,s);
					}
				}
			}
			
		});
		btnOperazione28.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnOperazione28.setBounds(658, 47, 212, 44);
		contentPane.add(btnOperazione28);
		
		textFieldPiva = new JTextField();
		textFieldPiva.setBounds(37, 270, 198, 19);
		contentPane.add(textFieldPiva);
		textFieldPiva.setColumns(10);
	}
}
