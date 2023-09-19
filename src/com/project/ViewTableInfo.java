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
import javax.swing.table.DefaultTableModel;

public class ViewTableInfo extends JFrame implements ActionListener {

	JButton viewAllTables, viewTable, none;
	DatabaseConfig db, dbobj;
	JFrame frm;
	static JTable table;
	int flag = 0;

	ViewTableInfo() {
		setTitle("Table");
		setSize(1100, 700);
		setVisible(true);
		setLayout(null);
		setResizable(false);

		// For Placing Frame in the Middle
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
		getContentPane().setBackground(Color.gray);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel relation = new JLabel("Choose From Following");
		add(relation);
		relation.setBounds(350, 20, 400, 60);
		relation.setFont(new Font("serif", Font.BOLD, 35));
		relation.setForeground(Color.WHITE);

		viewAllTables = new JButton("View All Tables");
		add(viewAllTables);
		viewAllTables.setBounds(400, 160, 250, 50);
		viewAllTables.setBackground(Color.BLACK);
		viewAllTables.setForeground(Color.WHITE);
		viewAllTables.setFont(new Font("serif", Font.BOLD, 30));

		viewTable = new JButton("View Table");
		add(viewTable);
		viewTable.setBounds(400, 300, 250, 50);
		viewTable.setBackground(Color.BLACK);
		viewTable.setForeground(Color.WHITE);
		viewTable.setFont(new Font("serif", Font.BOLD, 30));

		none = new JButton("None");
		add(none);
		none.setBounds(400, 440, 250, 50);
		none.setBackground(Color.BLACK);
		none.setForeground(Color.WHITE);
		none.setFont(new Font("serif", Font.BOLD, 30));

		// closing frame for none relationship
		none.addActionListener(e -> {
			dispose();
		});

		viewAllTables.addActionListener(this);
		viewTable.addActionListener(this);

	}// ViewTableInfo ()

	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == viewTable) {
			try {

				db = new DatabaseConfig();
				String tstr[] = { "TABLE" };

				String t1 = JOptionPane.showInputDialog(null, "Enter Table Name");
				int f = 0;

				if (t1 == "null") {
					JOptionPane.showMessageDialog(null, "Please Enter Valid Table Name", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else {

					db.dbmd = db.con.getMetaData();
					db.rs = db.dbmd.getTables(null, null, null, tstr);

					while (db.rs.next()) {
						if (t1.equals(db.rs.getString("TABLE_NAME"))) {
							f = 1;
							break;
						}

					}

					if (f == 1) {

						String ColumnSQL = "select * from " + t1;
						db.st = db.con.createStatement();
						db.rs = db.st.executeQuery(ColumnSQL);
						db.rsmd = db.rs.getMetaData();

						int cnt = db.rsmd.getColumnCount();
						String columnNames[] = new String[cnt];
						String columnData[] = new String[cnt];

						for (int i = 1, j = 0; i <= cnt; i++, j++) {
							columnNames[j] = db.rsmd.getColumnName(i);
						}

						frm = new JFrame(t1 + " Tables info");
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
						db.rs = db.st.executeQuery("select * from " + t1);

						while (db.rs.next()) {

							for (int i = 1, j = 0; i <= cnt; i++, j++) {
								columnData[j] = db.rs.getString(i);
							}
							model.addRow(columnData);

						} // while
						frm.add(scroll);

						frm.setVisible(true);
					} else {

						JOptionPane.showMessageDialog(null, "Table Does not exist", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}

		}
		if (ae.getSource() == viewAllTables) {
			try {

				db = new DatabaseConfig();

				String tableStr[] = { "TABLE" };
				String columns[] = { "All Tables of Database" };
				getContentPane().setBackground(Color.lightGray);
				String temp = "";

				frm = new JFrame("Tables info");
				frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frm.setSize(1100, 700);
				setResizable(false);
				frm.setLayout(new BorderLayout());
				Toolkit toolkit = getToolkit();
				Dimension size = toolkit.getScreenSize();
				frm.setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
				frm.getContentPane().setBackground(Color.lightGray);
				DefaultTableModel model = new DefaultTableModel();
				frm.setFont(new Font("serif", Font.BOLD, 15));
				model.setColumnIdentifiers(columns);

				table = new JTable();
				table.setFont(new Font("serif", Font.BOLD, 15));
				table.setModel(model);

				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

				table.setFillsViewportHeight(true);

				JScrollPane scroll = new JScrollPane(table);

				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

				frm.add(scroll);
				db.dbmd = db.con.getMetaData();
				db.rs = db.dbmd.getTables(null, null, null, tableStr);
				int i = 1;
				while (db.rs.next()) {
					String TableName[] = new String[1];

					temp = db.rs.getString("TABLE_NAME");
					temp = " " + i + ":   " + temp;
					i++;
					TableName[0] = temp;
					model.addRow(TableName);
				}

				frm.setVisible(true);

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}// actionPerformed

}// ViewTableInfo
