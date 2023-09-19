package com.project;

import database.DatabaseConfig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame implements ActionListener {
	JButton createTable, viewTable, dropTable, UpdateTableData, Relationships, Truncate, SQL, Menu, name, done;
	CreateTables cobj;
	RelationshipHandler robj;
	DatabaseConfig db;
	UpdateTable uobj;
	ViewTableInfo vobj;
	LoginClass lobj;
	DropTableData dobj;
	SQL_Executer seobj;

	public void callLogin() {
		lobj = new LoginClass();
	}

	void createMain() {

		setTitle("SQL Automation");
		setSize(1100, 700);
		setVisible(true);
		setLayout(null);
		setResizable(false);
		getContentPane().setBackground(Color.darkGray);

		// For Placing Frame in the Middle
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel head = new JLabel("Let's Make It Easy !");
		add(head);
		head.setBounds(610, 80, 460, 60);
		head.setForeground(Color.WHITE);
		head.setFont(new Font("serif", Font.BOLD, 25));

		name = new JButton("PLSQL'S Manager");
		add(name);
		name.setBounds(410, 20, 650, 60);
		name.setBackground(Color.BLACK);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("serif", Font.BOLD, 25));

		Menu = new JButton("Menu");
		add(Menu);
		Menu.setBounds(10, 20, 400, 60);
		Menu.setBackground(Color.BLACK);
		Menu.setForeground(Color.WHITE);
		Menu.setFont(new Font("serif", Font.BOLD, 25));

		ImageIcon i1 = new ImageIcon(System.getProperty("user.dir") + "//images//logo.jpg");

		Image i2 = i1.getImage().getScaledInstance(460, 360, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l3 = new JLabel(i3);
		l3.setBounds(530, 140, 420, 420);
		add(l3);

		SQL = new JButton("SQL WorkSpace");
		add(SQL);
		SQL.setBounds(10, 80, 400, 60);
		SQL.setBackground(Color.BLACK);
		SQL.setForeground(Color.WHITE);
		SQL.setFont(new Font("serif", Font.BOLD, 20));

		createTable = new JButton("Create Table");
		add(createTable);
		createTable.setBounds(10, 140, 400, 60);
		createTable.setBackground(Color.BLACK);
		createTable.setForeground(Color.WHITE);
		createTable.setFont(new Font("serif", Font.BOLD, 20));

		Relationships = new JButton("Relationships");
		add(Relationships);
		Relationships.setBounds(10, 200, 400, 60);
		Relationships.setBackground(Color.BLACK);
		Relationships.setForeground(Color.WHITE);
		Relationships.setFont(new Font("serif", Font.BOLD, 20));

		viewTable = new JButton("View Table ");
		add(viewTable);
		viewTable.setBounds(10, 260, 400, 60);
		viewTable.setBackground(Color.BLACK);
		viewTable.setForeground(Color.WHITE);
		viewTable.setFont(new Font("serif", Font.BOLD, 20));

		UpdateTableData = new JButton("Update Table ");
		add(UpdateTableData);
		UpdateTableData.setBounds(10, 320, 400, 60);
		UpdateTableData.setBackground(Color.BLACK);
		UpdateTableData.setForeground(Color.WHITE);
		UpdateTableData.setFont(new Font("serif", Font.BOLD, 20));

		dropTable = new JButton("Drop Table");
		add(dropTable);
		dropTable.setBounds(10, 380, 400, 60);
		dropTable.setBackground(Color.BLACK);
		dropTable.setForeground(Color.WHITE);
		dropTable.setFont(new Font("serif", Font.BOLD, 20));

		Truncate = new JButton("Truncate Table");
		add(Truncate);
		Truncate.setBounds(10, 440, 400, 60);
		Truncate.setBackground(Color.BLACK);
		Truncate.setForeground(Color.WHITE);
		Truncate.setFont(new Font("serif", Font.BOLD, 20));

		done = new JButton("Done");
		add(done);
		done.setBounds(10, 500, 400, 60);
		done.setBackground(Color.BLACK);
		done.setForeground(Color.WHITE);
		done.setFont(new Font("serif", Font.BOLD, 20));
		// closing frame
		done.addActionListener(e -> {
			System.exit(0);
		});

		// adding action listeners
		SQL.addActionListener(this);
		createTable.addActionListener(this);
		Relationships.addActionListener(this);
		viewTable.addActionListener(this);
		UpdateTableData.addActionListener(this);
		dropTable.addActionListener(this);
		Truncate.addActionListener(this);

	}// MainCopy()

	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == createTable) {
			cobj = new CreateTables();
			cobj.createTableMethod();
		}

		if (ae.getSource() == SQL) {
			seobj = new SQL_Executer();
		}
		if (ae.getSource() == viewTable) {
			vobj = new ViewTableInfo();
		}

		if (ae.getSource() == dropTable) {

			dobj = new DropTableData();

		}

		if (ae.getSource() == Truncate) {
			try {
				db = new DatabaseConfig();
				String tname = JOptionPane.showInputDialog(null, "Enter Table Name");
				String truncateSQL = "truncate table " + tname;

				db.ps = db.con.prepareStatement(truncateSQL);
				db.ps.execute();
				JOptionPane.showMessageDialog(null, "Table Truncated Sucessfully", "Sucess",
						JOptionPane.WARNING_MESSAGE);

			} catch (Exception e) {
			}

		}

		if (ae.getSource() == UpdateTableData) {
			uobj = new UpdateTable();

		}

		if (ae.getSource() == Relationships) {
			robj = new RelationshipHandler();
		}

	}

	public static void main(String[] args) {
		MainFrame m1 = new MainFrame();
		m1.callLogin();
	}
}// class TableFrame