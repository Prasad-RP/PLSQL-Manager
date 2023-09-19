package com.project;

import database.DatabaseConfig;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class UpdateTable extends JFrame implements ActionListener {

	JButton updateTName, updateCName, none;
	DatabaseConfig db;

	UpdateTable() {
		setTitle("Table");
		setSize(700, 400);
		setVisible(true);
		setLayout(null);
		setResizable(false);

		// For Placing Frame in the Middle
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
		getContentPane().setBackground(Color.lightGray);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel relation = new JLabel("Choose From Following");
		add(relation);
		relation.setBounds(200, 20, 200, 40);
		relation.setBackground(Color.BLACK);
		relation.setForeground(Color.WHITE);

		updateTName = new JButton("Update Table Name");
		add(updateTName);
		updateTName.setBounds(200, 80, 200, 40);
		updateTName.setBackground(Color.BLACK);
		updateTName.setForeground(Color.WHITE);

		updateCName = new JButton("Update Column Name");
		add(updateCName);
		updateCName.setBounds(200, 140, 200, 40);
		updateCName.setBackground(Color.BLACK);
		updateCName.setForeground(Color.WHITE);

		none = new JButton("None");
		add(none);
		none.setBounds(200, 200, 200, 40);
		none.setBackground(Color.BLACK);
		none.setForeground(Color.WHITE);

		// closing frame for none relationship
		none.addActionListener(e -> {
			dispose();
		});

		updateTName.addActionListener(this);
		updateCName.addActionListener(this);

	}// UpdateTable ()

	@Override
	public void actionPerformed(ActionEvent ae) {
		db = new DatabaseConfig();

		if (ae.getSource() == updateTName) {
			try {
				String tstr[] = { "TABLE" };
				int f = 0;
				String t1 = JOptionPane.showInputDialog(null, "Enter old Table Name");

				if (t1 == "null" || t1 == "NULL") {
					JOptionPane.showMessageDialog(null, "Please Enter Valid Table Name", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else {

					db.dbmd = db.con.getMetaData();
					db.rs = db.dbmd.getTables(null, null, null, tstr);

					while (db.rs.next()) {
						if (t1.equals(db.rs.getString(4))) {
							System.out.println(t1);
							System.out.println(db.rs.getString(4));
							f = 1;
							break;
						}

					}
					if (f == 1) {

						String t2 = JOptionPane.showInputDialog(null, "Enter new Table Name");

						String upadteSQl = "alter table " + t1 + " rename to " + t2;
						db.ps = db.con.prepareStatement(upadteSQl);
						db.ps.execute();
						JOptionPane.showMessageDialog(null, "Table Renamed Successfully", "Sucess",
								JOptionPane.INFORMATION_MESSAGE);
					} else {

						// changes suggested

						JOptionPane.showMessageDialog(null, "Table not found or in RelationShip", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (ae.getSource() == updateCName) {
			try {
				String t1 = JOptionPane.showInputDialog(null, "Enter Table Name");
				String tstr[] = { "TABLE" };
				int f = 0;

				if (t1 == "null" || t1 == "NULL") {
					JOptionPane.showMessageDialog(null, "Please Enter Valid Table Name", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else {

					db.dbmd = db.con.getMetaData();
					db.rs = db.dbmd.getTables(null, null, null, tstr);

					while (db.rs.next()) {
						if (t1.equals(db.rs.getString(4))) {
							System.out.println(t1);
							System.out.println(db.rs.getString(4));
							f = 1;
							break;
						}

					}
					if (f == 1) {

						String c1 = JOptionPane.showInputDialog(null, "Enter old column Name");
						String c2 = JOptionPane.showInputDialog(null, "Enter new column Name");

						String upadteColSQl = "alter table " + t1 + " rename column " + c1 + " to " + c2;
						db.ps = db.con.prepareStatement(upadteColSQl);
						db.ps.execute();

						JOptionPane.showMessageDialog(null, "Column Renamed Successfully", "Sucess",
								JOptionPane.INFORMATION_MESSAGE);
					} else {

						JOptionPane.showMessageDialog(null, "Table not found or in RelationShip", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

}
