package com.project;

import database.DatabaseConfig;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistrationClass extends JFrame implements ActionListener {

	JLabel l1, l2, name, email;
	JTextField user, tname, temail;
	JPasswordField pass;
	JButton bregister, blogin;
	DatabaseConfig db;
	int Eflag = 0, Pflag = 0;

	RegistrationClass() {

		setTitle("Registration");

		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		name = new JLabel("Name");
		name.setBounds(40, 20, 100, 40);
		name.setFont(new Font("serif", Font.BOLD, 20));
		name.setForeground(Color.BLACK);
		add(name);

		tname = new JTextField();
		tname.setBounds(170, 20, 270, 40);
		tname.setFont(new Font("serif", Font.BOLD, 20));
		add(tname);

		email = new JLabel("Email");
		email.setBounds(40, 90, 100, 40);
		email.setFont(new Font("serif", Font.BOLD, 20));
		email.setForeground(Color.BLACK);
		add(email);

		temail = new JTextField();
		temail.setBounds(170, 90, 270, 40);
		temail.setFont(new Font("serif", Font.BOLD, 20));
		add(temail);

		l1 = new JLabel("Username");
		l1.setBounds(40, 160, 100, 40);
		l1.setFont(new Font("serif", Font.BOLD, 20));
		l1.setForeground(Color.BLACK);
		add(l1);

		user = new JTextField();
		user.setBounds(170, 160, 270, 40);
		user.setFont(new Font("serif", Font.BOLD, 20));
		add(user);

		l2 = new JLabel("Password");
		l2.setBounds(40, 230, 100, 40);
		l2.setFont(new Font("serif", Font.BOLD, 20));
		l2.setForeground(Color.BLACK);
		add(l2);

		pass = new JPasswordField();
		pass.setBounds(170, 230, 270, 40);
		pass.setFont(new Font("serif", Font.BOLD, 20));
		add(pass);
		ImageIcon i1 = new ImageIcon(System.getProperty("user.dir") + "//images//registration.jpg");

		Image i2 = i1.getImage().getScaledInstance(220, 230, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l3 = new JLabel(i3);
		l3.setBounds(450, 50, 250, 250);
		add(l3);

		bregister = new JButton("Register");
		bregister.setBounds(40, 300, 170, 40);
		bregister.setFont(new Font("serif", Font.BOLD, 25));
		bregister.setBackground(Color.CYAN);
		bregister.setForeground(Color.BLACK);
		add(bregister);

		blogin = new JButton("Login");
		blogin.setBounds(260, 300, 130, 40);
		blogin.setFont(new Font("serif", Font.BOLD, 25));
		blogin.setBackground(Color.PINK);
		blogin.setForeground(Color.BLACK);
		add(blogin);

		blogin.addActionListener(this);
		bregister.addActionListener(this);
		getContentPane().setBackground(Color.LIGHT_GRAY);

		setVisible(true);
		setSize(715, 400);
		setLocation(400, 180);
		setResizable(false);

	}// constructor

	public void actionPerformed(ActionEvent ae) {
		try {
			if (ae.getSource() == bregister) {
				String name = tname.getText();
				String email = temail.getText();

				String username = user.getText();
				char arr[] = pass.getPassword();
				String password = "";

				for (char ele : arr) {
					password = password + ele;
				}
				if (email.endsWith(".com") && email.contains("@")) {
					Eflag = 1;

				} else {
					JOptionPane.showMessageDialog(null, "Enter Valid Email", "Error", JOptionPane.ERROR_MESSAGE);

				}
				if (password.length() >= 6) {
					Pflag = 1;

				} else {
					JOptionPane.showMessageDialog(null, "Password must be greater than 6", "Error",
							JOptionPane.ERROR_MESSAGE);

				}

				if (Eflag == 1) {
					if (Pflag == 1) {
						db = new DatabaseConfig();
						db.ps = db.con.prepareStatement("insert into login values(?,?,?,?)");
						db.ps.setString(1, name);
						db.ps.setString(2, email);
						db.ps.setString(3, username);
						db.ps.setString(4, password);
						int result = db.ps.executeUpdate();

						if (result == 1) {

							JOptionPane.showMessageDialog(null, "Succesfully Registered", "Success",
									JOptionPane.INFORMATION_MESSAGE);
							tname.setText("");
							temail.setText("");
							user.setText("");
							pass.setText("");
						}
					}
				}

			}
		} catch (Exception e) {
		}

		if (ae.getSource() == blogin) {
			new LoginClass();
		}
	}

}// class registration
