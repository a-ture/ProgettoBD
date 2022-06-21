package interfaccia;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Menu {

	JFrame frmMen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frmMen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection con = null;
	/**
	 * @wbp.nonvisual location=64,-36
	 */
	private JLabel lblOperazione8;

	/**
	 * Create the application.
	 * 
	 * @throws SQLException
	 */
	public Menu() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMen = new JFrame();
		frmMen.setTitle("Menu");
		frmMen.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 15));
		frmMen.setBounds(100, 100, 1349, 675);
		frmMen.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JButton btnOperazione10 = new JButton("Operazione 10");
		btnOperazione10.setBounds(380, 253, 185, 67);
		btnOperazione10.setFont(new Font("Verdana", Font.BOLD, 15));
		btnOperazione10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String risposta = JOptionPane.showInputDialog(null, "Che tipo di ordine vuoi inserire");
					if (risposta.contains("ordine cliente")) {
						Operazione10_1 ordineCliente = new Operazione10_1();
						ordineCliente.setVisible(true);
					}

					else {
						if (risposta.contains("ordine fornitore")) {
							Operazione10_2 ordineFornitore = new Operazione10_2();
							ordineFornitore.setVisible(true);

						} else {
							JOptionPane.showMessageDialog(null, "Hai inserito una risposta non valida");
						}
					}
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Non hai inserito la risposta");
				}
			}
		});

		JButton btnOperazione17 = new JButton("Operazione 17");
		btnOperazione17.setBounds(825, 253, 185, 65);
		btnOperazione17.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnOperazione17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione17 op17 = new Operazione17();
				op17.setVisible(true);
			}
		});

		JButton btnOperazione21 = new JButton("Operazione 21");
		btnOperazione21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione21 op21 = new Operazione21();
				op21.setVisible(true);
			}
		});
		btnOperazione21.setBounds(825, 562, 185, 63);
		btnOperazione21.setFont(new Font("Tahoma", Font.BOLD, 15));

		JButton btnOperazione1 = new JButton("Operazione 1");
		btnOperazione1.setBounds(10, 99, 185, 67);
		btnOperazione1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione1 op1 = new Operazione1();
				op1.setVisible(true);

			}
		});
		btnOperazione1.setFont(new Font("Verdana", Font.BOLD, 15));

		JLabel lblNewLabel = new JLabel("Menu");
		lblNewLabel.setBounds(566, 10, 203, 65);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 30));

		JButton btnOperazione2 = new JButton("Operazione 2");
		btnOperazione2.setBounds(10, 176, 185, 67);
		btnOperazione2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione2 op2 = new Operazione2();
				op2.setVisible(true);

			}
		});
		btnOperazione2.setFont(new Font("Verdana", Font.BOLD, 15));

		JButton btnOperazione3 = new JButton("Operazione 3");
		btnOperazione3.setBounds(10, 253, 185, 67);
		btnOperazione3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione3 op3 = new Operazione3();
				op3.setVisible(true);

			}
		});
		btnOperazione3.setFont(new Font("Verdana", Font.BOLD, 15));

		JButton btnOperazione4 = new JButton("Operazione 4");
		btnOperazione4.setBounds(10, 330, 185, 67);
		btnOperazione4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione4 op4 = new Operazione4();
				op4.setVisible(true);
			}
		});
		btnOperazione4.setFont(new Font("Verdana", Font.BOLD, 15));

		JButton btnOperazione5 = new JButton("Operazione 5");
		btnOperazione5.setBounds(10, 407, 185, 67);
		btnOperazione5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione5 op5 = new Operazione5();
				op5.setVisible(true);
			}
		});
		btnOperazione5.setFont(new Font("Verdana", Font.BOLD, 15));

		JButton btnOperazione6 = new JButton("Operazione 6");
		btnOperazione6.setBounds(10, 484, 185, 67);
		btnOperazione6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Operazione6 op6 = new Operazione6();
				op6.setVisible(true);
			}
		});
		btnOperazione6.setFont(new Font("Verdana", Font.BOLD, 15));

		JButton btnOperazione7 = new JButton("Operazione 7");
		btnOperazione7.setBounds(10, 561, 185, 67);
		btnOperazione7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione7 op7 = new Operazione7();
				op7.setVisible(true);
			}
		});
		btnOperazione7.setFont(new Font("Verdana", Font.BOLD, 15));

		JButton btnOperazione8 = new JButton("Operazione 8");
		btnOperazione8.setBounds(380, 99, 185, 67);
		btnOperazione8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione8 op8 = new Operazione8();
				op8.setVisible(true);
			}
		});
		btnOperazione8.setFont(new Font("Verdana", Font.BOLD, 15));

		JLabel lblOperazione1 = new JLabel("Inserisci cliente");
		lblOperazione1.setBounds(205, 118, 234, 36);
		lblOperazione1.setFont(new Font("Calibri", Font.PLAIN, 15));

		JButton btnOperazione9 = new JButton("Operazione 9");
		btnOperazione9.setBounds(380, 179, 185, 65);
		btnOperazione9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione9 op9 = new Operazione9();
				op9.setVisible(true);
			}
		});
		btnOperazione9.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblOperazione2 = new JLabel("Modifica dati cliente");
		lblOperazione2.setBounds(205, 194, 234, 36);
		lblOperazione2.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione3 = new JLabel("Rimuovi cliente");
		lblOperazione3.setBounds(205, 272, 234, 36);
		lblOperazione3.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione4 = new JLabel("Inserisci pagamento");
		lblOperazione4.setBounds(205, 347, 234, 36);
		lblOperazione4.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione5 = new JLabel("Modifica pagamento");
		lblOperazione5.setBounds(205, 426, 234, 36);
		lblOperazione5.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione7 = new JLabel("Modifica assistenza");
		lblOperazione7.setBounds(205, 580, 234, 36);
		lblOperazione7.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione6 = new JLabel("Inserisci assistenza");
		lblOperazione6.setBounds(205, 503, 234, 36);
		lblOperazione6.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblOperazione8 = new JLabel("Visualizza elenco assistenze");
		lblOperazione8.setBounds(580, 118, 234, 36);
		lblOperazione8.setFont(new Font("Calibri", Font.PLAIN, 15));

		JButton btnOperazione13 = new JButton("Operazione 13");
		btnOperazione13.setBounds(380, 485, 185, 65);
		btnOperazione13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione13 op13 = new Operazione13();
				op13.setVisible(true);
			}
		});
		btnOperazione13.setFont(new Font("Tahoma", Font.BOLD, 15));

		JButton btnOperazione11 = new JButton("Operazione 11");
		btnOperazione11.setBounds(380, 330, 185, 65);
		btnOperazione11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String risposta = JOptionPane.showInputDialog(null, "Che tipo di ordine vuoi modificare");
					if (risposta.contains("ordine cliente")) {
						Operazione11_1 ordineCliente = new Operazione11_1();
						ordineCliente.setVisible(true);
					}

					else {
						if (risposta.contains("ordine fornitore")) {
							Operazione11_2 ordineFornitore = new Operazione11_2();
							ordineFornitore.setVisible(true);

						} else {
							JOptionPane.showMessageDialog(null, "Hai inserito una risposta non valida");
						}
					}
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Non hai inserito la risposta");
				}
			}

		});
		btnOperazione11.setFont(new Font("Tahoma", Font.BOLD, 15));

		JButton btnOperazione12 = new JButton("Operazione 12");
		btnOperazione12.setBounds(380, 408, 185, 65);
		btnOperazione12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String risposta = JOptionPane.showInputDialog(null, "Che tipo di ordine vuoi eliminare");
					if (!(risposta.isEmpty())) {
						if (risposta.contains("ordine cliente")) {
							Operazione12_1 ordineCliente = new Operazione12_1();
							ordineCliente.setVisible(true);
						}

						else {
							if (risposta.contains("ordine fornitore")) {
								Operazione12_2 ordineFornitore = new Operazione12_2();
								ordineFornitore.setVisible(true);

							} else
								JOptionPane.showMessageDialog(null, "Hai inserito una risposta non valida");
						}

					}
				}

				catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Non hai inserito la risposta");
				}
			}
		});
		btnOperazione12.setFont(new Font("Tahoma", Font.BOLD, 15));

		JButton btnOperazione16 = new JButton("Operazione 16");
		btnOperazione16.setBounds(825, 176, 185, 65);
		btnOperazione16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione16 op16 = new Operazione16();
				op16.setVisible(true);
			}
		});
		btnOperazione16.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblOperazione9 = new JLabel("Visualizza stampanti noleggiate");
		lblOperazione9.setBounds(580, 195, 234, 36);
		lblOperazione9.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblInserisciUnOridine = new JLabel("Inserisci ordine");
		lblInserisciUnOridine.setBounds(580, 272, 234, 36);
		lblInserisciUnOridine.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione11 = new JLabel("Modifica ordine");
		lblOperazione11.setBounds(580, 349, 234, 36);
		lblOperazione11.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione12 = new JLabel("Elimina ordine");
		lblOperazione12.setBounds(580, 426, 234, 36);
		lblOperazione12.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione13 = new JLabel("Inserisci fornitore");
		lblOperazione13.setBounds(580, 503, 234, 36);
		lblOperazione13.setFont(new Font("Calibri", Font.PLAIN, 15));

		JButton btnOperazione18 = new JButton("Operazione 18");
		btnOperazione18.setBounds(825, 328, 185, 65);
		btnOperazione18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione18 op18 = new Operazione18();
				op18.setVisible(true);
			}
		});
		btnOperazione18.setFont(new Font("Tahoma", Font.BOLD, 15));

		JButton btnOperazione15 = new JButton("Operazione 15");
		btnOperazione15.setBounds(825, 99, 185, 65);
		btnOperazione15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione15 op15 = new Operazione15();
				op15.setVisible(true);
			}
		});
		btnOperazione15.setFont(new Font("Tahoma", Font.BOLD, 15));

		JButton btnOperazione20 = new JButton("Operazione 20");
		btnOperazione20.setBounds(825, 484, 185, 65);
		btnOperazione20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String risposta = JOptionPane.showInputDialog(null, "Che tipo di prodotto vuoi eliminare");
					if (risposta.contains("prodotto contabile")) {
						Operazione20_1 op = new Operazione20_1();
						op.setVisible(true);
					} else {
						if (risposta.contains("stampante digitale")) {

							Operazione20_2 op = new Operazione20_2();
							op.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "Risposta non valida");
						}
					}
				}

				catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Non hai inserito la risposta");
				}
			}
		});
		btnOperazione20.setFont(new Font("Tahoma", Font.BOLD, 15));

		JButton btnOperazione19 = new JButton("Operazione 19");
		btnOperazione19.setBounds(825, 407, 185, 65);
		btnOperazione19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione19 op19 = new Operazione19();
				op19.setVisible(true);
			}
		});
		btnOperazione19.setFont(new Font("Tahoma", Font.BOLD, 15));

		JLabel lblOperazione15 = new JLabel("Elenco ordini effettuati presso un fornitore");
		lblOperazione15.setBounds(1020, 117, 308, 36);
		lblOperazione15.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione17 = new JLabel("Visualizzare il costo di noleggio stampanti");
		lblOperazione17.setBounds(1020, 271, 308, 36);
		lblOperazione17.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione18 = new JLabel("Visualizza le statistiche");
		lblOperazione18.setBounds(1020, 348, 234, 36);
		lblOperazione18.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione19 = new JLabel("Elenco prodotti disponibili");
		lblOperazione19.setBounds(1020, 425, 234, 36);
		lblOperazione19.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione20 = new JLabel("Rimuovere prodotto");
		lblOperazione20.setBounds(1020, 502, 234, 36);
		lblOperazione20.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione21 = new JLabel("Ordini di un cliente e relativi pagamenti");
		lblOperazione21.setBounds(1020, 569, 339, 56);
		lblOperazione21.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione14 = new JLabel("Modifica fornitore");
		lblOperazione14.setBounds(580, 580, 234, 36);
		lblOperazione14.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblOperazione16 = new JLabel("Clienti con noleggio in scadenza");
		lblOperazione16.setBounds(1020, 184, 239, 56);
		lblOperazione16.setFont(new Font("Calibri", Font.PLAIN, 15));

		JButton btnOperazione14 = new JButton("Operazione 14");
		btnOperazione14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Operazione14 op14 = new Operazione14();
				op14.setVisible(true);
			}
		});
		btnOperazione14.setBounds(380, 561, 185, 65);
		btnOperazione14.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmMen.getContentPane().setLayout(null);
		frmMen.getContentPane().add(btnOperazione10);
		frmMen.getContentPane().add(btnOperazione17);
		frmMen.getContentPane().add(btnOperazione21);
		frmMen.getContentPane().add(btnOperazione1);
		frmMen.getContentPane().add(lblNewLabel);
		frmMen.getContentPane().add(btnOperazione2);
		frmMen.getContentPane().add(btnOperazione3);
		frmMen.getContentPane().add(btnOperazione4);
		frmMen.getContentPane().add(btnOperazione5);
		frmMen.getContentPane().add(btnOperazione6);
		frmMen.getContentPane().add(btnOperazione7);
		frmMen.getContentPane().add(btnOperazione8);
		frmMen.getContentPane().add(lblOperazione1);
		frmMen.getContentPane().add(btnOperazione9);
		frmMen.getContentPane().add(lblOperazione2);
		frmMen.getContentPane().add(lblOperazione3);
		frmMen.getContentPane().add(lblOperazione4);
		frmMen.getContentPane().add(lblOperazione5);
		frmMen.getContentPane().add(lblOperazione7);
		frmMen.getContentPane().add(lblOperazione6);
		frmMen.getContentPane().add(lblOperazione8);
		frmMen.getContentPane().add(btnOperazione13);
		frmMen.getContentPane().add(btnOperazione11);
		frmMen.getContentPane().add(btnOperazione12);
		frmMen.getContentPane().add(btnOperazione16);
		frmMen.getContentPane().add(lblOperazione9);
		frmMen.getContentPane().add(lblInserisciUnOridine);
		frmMen.getContentPane().add(lblOperazione11);
		frmMen.getContentPane().add(lblOperazione12);
		frmMen.getContentPane().add(lblOperazione13);
		frmMen.getContentPane().add(btnOperazione18);
		frmMen.getContentPane().add(btnOperazione15);
		frmMen.getContentPane().add(btnOperazione20);
		frmMen.getContentPane().add(btnOperazione19);
		frmMen.getContentPane().add(lblOperazione15);
		frmMen.getContentPane().add(lblOperazione17);
		frmMen.getContentPane().add(lblOperazione18);
		frmMen.getContentPane().add(lblOperazione19);
		frmMen.getContentPane().add(lblOperazione20);
		frmMen.getContentPane().add(lblOperazione21);
		frmMen.getContentPane().add(lblOperazione14);
		frmMen.getContentPane().add(lblOperazione16);
		frmMen.getContentPane().add(btnOperazione14);
	}
}
