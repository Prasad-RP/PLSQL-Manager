package com.project;

import database.DatabaseConfig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CreateTables extends JFrame implements ActionListener {
	JComboBox<Object> Datatype;
	JTextField fieldname, dsize;
	JRadioButton r1, r2;
	JButton add_field, view_all, search_table, truncate, done;
	static JTable table;
	String datatype[] = { "char", "int", "varchar", "bigint", "text", "numeric" };

	DatabaseConfig db;
	String tname, one_tname;
	ButtonGroup bg;
	int col_cnt = 1;
	int pk_cnt = 1, t_cnt = 1;

	public void createTableMethod() {
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

		tname = JOptionPane.showInputDialog(null, "Enter Table Name");

		JLabel table = new JLabel("Create Table " + tname + "");
		add(table);
		table.setForeground(Color.BLACK);
		table.setFont(new Font("serif", Font.BOLD, 35));
		table.setBounds(300, 10, 400, 100);

		JLabel name = new JLabel("FieldName");
		add(name);
		name.setBounds(150, 130, 200, 60);
		name.setBackground(Color.BLACK);
		name.setForeground(Color.BLACK);
		name.setFont(new Font("serif", Font.BOLD, 30));

		fieldname = new JTextField(10);
		add(fieldname);
		fieldname.setBounds(400, 130, 200, 60);
		fieldname.setBackground(Color.BLACK);
		fieldname.setForeground(Color.WHITE);
		fieldname.setFont(new Font("serif", Font.BOLD, 25));

		JLabel data = new JLabel("DataType ");
		add(data);
		data.setBounds(150, 230, 200, 60);
		data.setBackground(Color.BLACK);
		data.setForeground(Color.BLACK);
		data.setFont(new Font("serif", Font.BOLD, 30));

		Datatype = new JComboBox<Object>(datatype);
		add(Datatype);
		Datatype.setBounds(400, 230, 200, 60);
		Datatype.setBackground(Color.BLACK);
		Datatype.setForeground(Color.WHITE);
		Datatype.setFont(new Font("serif", Font.BOLD, 25));

		JLabel pkey = new JLabel("Primary Key");
		add(pkey);
		pkey.setBounds(150, 330, 200, 60);
		pkey.setBackground(Color.BLACK);
		pkey.setForeground(Color.BLACK);
		pkey.setFont(new Font("serif", Font.BOLD, 30));

		r1 = new JRadioButton("yes");
		add(r1);
		r1.setBounds(400, 330, 100, 60);
		r1.setActionCommand("yes");
		r1.setBackground(Color.BLACK);
		r1.setForeground(Color.WHITE);
		r1.setFont(new Font("serif", Font.BOLD, 30));

		r2 = new JRadioButton("no");
		add(r2);
		r2.setBounds(510, 330, 100, 60);
		r2.setActionCommand("no");
		r2.setBackground(Color.BLACK);
		r2.setForeground(Color.WHITE);
		r2.setFont(new Font("serif", Font.BOLD, 30));

		bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);

		JLabel size1 = new JLabel("Size");
		add(size1);
		size1.setBounds(150, 430, 100, 60);
		size1.setBackground(Color.BLACK);
		size1.setForeground(Color.BLACK);
		size1.setFont(new Font("serif", Font.BOLD, 30));

		dsize = new JTextField(10);
		add(dsize);
		dsize.setBounds(400, 430, 100, 60);
		dsize.setText("0");
		dsize.setBackground(Color.BLACK);
		dsize.setForeground(Color.WHITE);
		dsize.setFont(new Font("serif", Font.BOLD, 30));

		add_field = new JButton("Add Column");
		add(add_field);
		add_field.setBounds(700, 450, 220, 60);
		add_field.setBackground(Color.BLACK);
		add_field.setForeground(Color.WHITE);
		add_field.setFont(new Font("serif", Font.BOLD, 30));

		done = new JButton("Done");
		add(done);
		done.setBounds(730, 550, 170, 60);
		done.setBackground(Color.BLACK);
		done.setForeground(Color.WHITE);
		done.setFont(new Font("serif", Font.BOLD, 30));
		// closing frame
		done.addActionListener(e -> {
			dispose();
		});

		// action listeners add_field
		add_field.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		db = new DatabaseConfig();

		if (ae.getSource() == add_field) {

			String cname = (fieldname.getText());
			String datatype = (String) (Datatype.getSelectedItem());
			String initSQL = "create table " + tname + "();";
			int sizeOfDatatype = Integer.parseInt(dsize.getText());
			String primary_key = bg.getSelection().getActionCommand();

			try {
				// creating only table structure(empty table)
				if (col_cnt == 1) {
					db.ps = db.con.prepareStatement(initSQL);
					db.ps.execute();
					col_cnt++;
					t_cnt++;
				}

				// adding column of primary key only one per table
				if (primary_key == "yes" && pk_cnt == 1) {

					// checking of integer datatypes since it does not required size
					if (datatype == "int" || datatype == "bigint") {
						String SQL = "alter table " + tname + " add " + cname + " " + datatype + ";";
						db.ps = db.con.prepareStatement(SQL);
						db.ps.execute();

						// to add primary key to above table
						String pk = "alter table " + tname + " add primary key(" + cname + ");";
						db.ps = db.con.prepareStatement(pk);
						db.ps.execute();
						pk_cnt++;

					} // if of int & bigint
					else {
						// for other datatypes since it required size
						String SQL = "alter table " + tname + " add " + cname + " " + datatype + "(" + sizeOfDatatype
								+ ")" + ";";
						db.ps = db.con.prepareStatement(SQL);
						db.ps.execute();

						String pk = "alter table " + tname + " add primary key(" + cname + ");";
						db.ps = db.con.prepareStatement(pk);
						db.ps.execute();
						pk_cnt++;

					}
				} // if of primary key
				else {
					// adding columns of non primary key
					// integer datatypes
					if (datatype == "int" || datatype == "bigint") {
						String SQL = "alter table " + tname + " add " + cname + " " + datatype + ";";
						db.ps = db.con.prepareStatement(SQL);
						db.ps.execute();
					} else { // non int datatypes
						String SQL = "alter table " + tname + " add " + cname + " " + datatype + "(" + sizeOfDatatype
								+ ");";
						db.ps = db.con.prepareStatement(SQL);
						db.ps.execute();

					}

				}

				JOptionPane.showMessageDialog(null, "Column Added Sucessfully", "Sucess",
						JOptionPane.INFORMATION_MESSAGE);

				fieldname.setText("");
				dsize.setText("0");
				db.con.close();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
				System.out.println(e);
			}

		} // add_field

	}// actionPerformed

}// CreateTable