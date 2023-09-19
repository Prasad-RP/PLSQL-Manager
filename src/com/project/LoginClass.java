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

public class LoginClass extends DatabaseConfig implements ActionListener {

	JLabel l1, l2;
	JTextField t1;
	JPasswordField t2;
	JButton btnlogin, btnregister;
	MainFrame mobj;
	int flag = 0;

	LoginClass() {
		JFrame f1 = new JFrame();
		f1.setTitle("Login");
		f1.setBackground(Color.white);
		f1.setLayout(null);

		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		l1 = new JLabel("Username");
		l1.setBounds(40, 30, 250, 50);
		l1.setFont(new Font("serif", Font.BOLD, 25));
		l1.setForeground(Color.BLACK);
		f1.add(l1);

		l2 = new JLabel("Password");
		l2.setBounds(40, 130, 250, 50);
		l2.setFont(new Font("serif", Font.BOLD, 25));
		l2.setForeground(Color.BLACK);
		f1.add(l2);

		t1 = new JTextField();
		t1.setBounds(200, 30, 250, 50);
		t1.setFont(new Font("serif", Font.BOLD, 25));
		f1.add(t1);

		t2 = new JPasswordField();
		t2.setBounds(200, 130, 250, 50);
		t2.setFont(new Font("serif", Font.BOLD, 25));
		f1.add(t2);

		ImageIcon i1 = new ImageIcon(System.getProperty("user.dir") + "//images//login.jpg");

		Image i2 = i1.getImage().getScaledInstance(230, 230, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l3 = new JLabel(i3);
		l3.setBounds(470, 40, 200, 260);
		f1.add(l3);

		btnlogin = new JButton("Login");
		btnlogin.setBounds(40, 250, 180, 50);
		btnlogin.setFont(new Font("serif", Font.BOLD, 25));
		// b1.addActionListener(this);
		btnlogin.setBackground(Color.CYAN);
		btnlogin.setForeground(Color.BLACK);
		f1.add(btnlogin);

		btnregister = new JButton("Register");
		btnregister.setBounds(260, 250, 190, 50);
		btnregister.setFont(new Font("serif", Font.BOLD, 25));
		btnregister.setBackground(Color.PINK);
		btnregister.setForeground(Color.BLACK);
		f1.add(btnregister);

		btnlogin.addActionListener(this);
		btnregister.addActionListener(this);

		f1.getContentPane().setBackground(Color.LIGHT_GRAY);

		f1.setVisible(true);
		f1.setSize(715, 400);
		f1.setLocation(400, 180);
		f1.setResizable(false);
	}

	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == btnlogin) {
			try {

				String username = t1.getText();
				char arr[] = t2.getPassword();
				String password = "";

				for (char ele : arr) {
					password = password + ele;
				}

				ps = con.prepareStatement("select username,password from login where username=? and password=?");
				ps.setString(1, username);
				ps.setString(2, password);
				rs = ps.executeQuery();

				while (rs.next()) {
					flag = 1;

				}

				if (flag == 1) {
					JOptionPane.showMessageDialog(null, "Login Sucessfully", "Sucess", JOptionPane.INFORMATION_MESSAGE);
					mobj = new MainFrame();
					t1.setText("");
					t2.setText("");
					mobj.createMain();

				} else {
					JOptionPane.showMessageDialog(null, "Invalid Login Details", "Error", JOptionPane.ERROR_MESSAGE);

				}

			} catch (Exception e) {
				System.out.println(e);
			}

		}
		if (ae.getSource() == btnregister) {
			new RegistrationClass();
		}

	}

}// class login