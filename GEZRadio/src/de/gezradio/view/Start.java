package de.gezradio.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import de.gezradio.exceptions.PluginBrokenException;

public class Start extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5468153855703685352L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start frame = new Start();
					frame.pack();
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
	public Start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
		try {
			JTable table = new JTable(new SenderTableModel());
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			contentPane.add(table);
		} catch (IOException | PluginBrokenException e) {
			e.printStackTrace();
		}
		setContentPane(contentPane);
	}

}
