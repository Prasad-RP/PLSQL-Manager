package com.project;

import database.DatabaseConfig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SQL_Executer extends JFrame implements ActionListener {
	JLabel exe_query, query;
	JTextField sql_query;
	JButton go;
	DatabaseConfig db;
	static JTable table;
	JFrame frm;

	SQL_Executer() {
		setTitle("Sql Query Execution");
		setLayout(null);

		setVisible(true);
		setSize(1100, 700);

		setResizable(false);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
		getContentPane().setBackground(Color.lightGray);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		exe_query = new JLabel("Execute Queries");
		exe_query.setBounds(400, 20, 320, 50);
		exe_query.setFont(new Font("serif", Font.BOLD, 40));
		exe_query.setForeground(Color.BLACK);
		add(exe_query);

		query = new JLabel("Type SQL Query Here");
		query.setBounds(100, 100, 370, 80);
		query.setFont(new Font("serif", Font.BOLD, 30));
		query.setForeground(Color.BLACK);
		add(query);

		sql_query = new JTextField(200);
		sql_query.setBounds(100, 200, 870, 300);
		add(sql_query);
		sql_query.setFont(new Font("Arial", Font.BOLD, 25));

		go = new JButton("Go :)");
		go.setBounds(700, 550, 150, 70);
		go.setBackground(Color.BLACK);
		go.setForeground(Color.WHITE);
		go.setFont(new Font("serif", Font.BOLD, 25));
		add(go);

		go.addActionListener(this);
	}// constructor

	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == go) {

			String Query = sql_query.getText();
			Query = Query.toLowerCase();

			int flag = 0;
			db = new DatabaseConfig();

			try {

				if (Query.startsWith("create") || Query.startsWith("alter") || Query.startsWith("truncate")) {
					db.ps = db.con.prepareStatement(Query);
					db.ps.execute();
					sql_query.setText("");

					JOptionPane.showMessageDialog(null, "Querry Executed Successfully", "Sucess",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (Query.startsWith("insert") || Query.startsWith("update") || Query.startsWith("delete")) {
					db.ps = db.con.prepareStatement(Query);
					flag = db.ps.executeUpdate();
					sql_query.setText("");

				}

				if (Query.startsWith("select")) {

					db.st = db.con.createStatement();
					db.rs = db.st.executeQuery(Query);
					db.rsmd = db.rs.getMetaData();

					int cnt = db.rsmd.getColumnCount();
					String columnNames[] = new String[cnt];
					String columnData[] = new String[cnt];

					for (int i = 1, j = 0; i <= cnt; i++, j++) {
						columnNames[j] = db.rsmd.getColumnName(i);
					}

					frm = new JFrame(" Query Result");
					frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

					frm.setSize(1100, 700);

					frm.setLayout(new BorderLayout());

					Toolkit toolkit = getToolkit();
					Dimension size = toolkit.getScreenSize();
					frm.setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

					frm.getContentPane().setBackground(Color.lightGray);

					setResizable(false);

					DefaultTableModel model = new DefaultTableModel();

					frm.setFont(new Font("serif", Font.BOLD, 15));

					model.setColumnIdentifiers(columnNames);

					table = new JTable();

					table.setFont(new Font("serif", Font.BOLD, 15));

					table.setModel(model);
					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

					table.setFillsViewportHeight(true);

					JScrollPane scroll = new JScrollPane(table);

					scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

					scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

					db.st = db.con.createStatement();
					db.rs = db.st.executeQuery(Query);

					while (db.rs.next()) {

						for (int i = 1, j = 0; i <= cnt; i++, j++) {
							columnData[j] = db.rs.getString(i);
						}
						model.addRow(columnData);

					} // while
					frm.add(scroll);

					frm.setVisible(true);

				}

				if (flag != 0) {

					JOptionPane.showMessageDialog(null, "Querry Executed Successfully", "Sucess",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

}
