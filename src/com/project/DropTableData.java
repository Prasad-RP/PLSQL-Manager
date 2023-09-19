package com.project;


import database.DatabaseConfig;
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


public class DropTableData extends JFrame implements ActionListener {

    JButton dropTName, dropCName, none;
    DatabaseConfig db;

    DropTableData() {
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
        relation.setBounds(200, 20, 250, 40);
        relation.setForeground(Color.BLACK);
        relation.setFont(new Font("serif", Font.BOLD, 20));

        dropTName = new JButton("Drop Table Name");
        add(dropTName);
        dropTName.setBounds(200, 80, 200, 40);
        dropTName.setBackground(Color.BLACK);
        dropTName.setForeground(Color.WHITE);

        dropCName = new JButton("Drop Column Name");
        add(dropCName);
        dropCName.setBounds(200, 140, 200, 40);
        dropCName.setBackground(Color.BLACK);
        dropCName.setForeground(Color.WHITE);

        none = new JButton("None");
        add(none);
        none.setBounds(200, 200, 200, 40);
        none.setBackground(Color.BLACK);
        none.setForeground(Color.WHITE);

        // closing frame for none relationship
        none.addActionListener(e -> {
            dispose();
        });

        dropTName.addActionListener(this);
        dropCName.addActionListener(this);

    }// UpdateTable ()

    @Override
    public void actionPerformed(ActionEvent ae) {
        db = new DatabaseConfig();
        try {
            if (ae.getSource() == dropTName) {
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

                        String upadteSQl = "drop table " + t1;
                        db.ps = db.con.prepareStatement(upadteSQl);
                        db.ps.execute();
                        JOptionPane.showMessageDialog(null, "Table Dropped Successfully", "Sucess",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                           // changes suggested


                        JOptionPane.showMessageDialog(null, "Table not found or in RelationShip", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }

            if (ae.getSource() == dropCName) {
                String tableString[] = { "TABLE" };

                String tn = JOptionPane.showInputDialog(null, "Enter Table Name");
                int f1 = 0;

                if (tn == "null") {
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Table Name", "Error",
                            JOptionPane.ERROR_MESSAGE);

                } else {

                    db.dbmd = db.con.getMetaData();
                    db.rs = db.dbmd.getTables(null, null, null, tableString);

                    while (db.rs.next()) {
                        if (tn.equals(db.rs.getString("TABLE_NAME"))) {
                            f1 = 1;
                            break;
                        }

                    }
                    if (f1 == 1) {
                        String c1 = JOptionPane.showInputDialog(null, "Enter Column Name");
                        String upadteSQl = "alter table " + tn + " drop column " + c1;
                        db.ps = db.con.prepareStatement(upadteSQl);
                        db.ps.execute();
                        JOptionPane.showMessageDialog(null, "column Dropped Successfully", "Sucess",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Table not found or in RelationShip", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
