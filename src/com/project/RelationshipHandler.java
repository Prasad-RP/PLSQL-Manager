package com.project;

import database.DatabaseConfig;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RelationshipHandler extends CreateTables {
    JButton updateTName, updateCName, many_to_many, none;
    DatabaseConfig db;
    String t1 = "", t2 = "", t3 = "", primary_key_t1 = "", primary_key_type_t1 = "";
    String pkey_col_t1 = "";
    String primary_key_t2 = "", primary_key_type_t2 = "";
    String pkey_col_t2 = "";

    RelationshipHandler() {
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

        t1 = JOptionPane.showInputDialog(null, "Enter first Table Name");

        JLabel relation = new JLabel("Choose Relationship");
        add(relation);
        relation.setBounds(400, 50, 500, 60);
        relation.setBackground(Color.BLACK);
        relation.setForeground(Color.WHITE);
        relation.setFont(new Font("serif", Font.BOLD, 35));

        updateTName = new JButton("One To One");
        add(updateTName);
        updateTName.setBounds(400, 150, 300, 50);
        updateTName.setBackground(Color.BLACK);
        updateTName.setForeground(Color.WHITE);
        updateTName.setFont(new Font("serif", Font.BOLD, 30));


        updateCName = new JButton("One To Many");
        add(updateCName);
        updateCName.setBounds(400, 250, 300, 50);
        updateCName.setBackground(Color.BLACK);
        updateCName.setForeground(Color.WHITE);
        updateCName.setFont(new Font("serif", Font.BOLD, 30));


        many_to_many = new JButton("Many To Many");
        add(many_to_many);
        many_to_many.setBounds(400, 350, 300, 50);
        many_to_many.setBackground(Color.BLACK);
        many_to_many.setForeground(Color.WHITE);
        many_to_many.setFont(new Font("serif", Font.BOLD, 30));


        none = new JButton("None");
        add(none);
        none.setBounds(400, 450, 300, 50);
        none.setBackground(Color.BLACK);
        none.setForeground(Color.WHITE);
        none.setFont(new Font("serif", Font.BOLD, 30));


        // closing frame for none relationship
        none.addActionListener(e -> {
            dispose();
        });

        updateTName.addActionListener(this);
        updateCName.addActionListener(this);
        many_to_many.addActionListener(this);

    }// RelationshipHandler()

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            db = new DatabaseConfig();

            if (ae.getSource() == updateTName || ae.getSource() == updateCName) {

                t2 = JOptionPane.showInputDialog(null, "Enter Second Table Name");

                // to find primary key of table
                db.dbmd = db.con.getMetaData();
                db.rs = db.dbmd.getTables(null, null, t1, new String[] { "TABLES" });
                db.rs = db.dbmd.getPrimaryKeys(null, null, t1);

                while (db.rs.next()) {
                    primary_key_t1 = db.rs.getString(4);
                    pkey_col_t1 = db.rs.getString("KEY_SEQ");
                }
                int pk_col_id = Integer.parseInt(pkey_col_t1);

                db.st = db.con.createStatement();
                db.rs = db.st.executeQuery("select * from " + t1);

                db.rsmd = db.rs.getMetaData();
                primary_key_type_t1 = db.rsmd.getColumnTypeName(pk_col_id);

                String init_querry = "alter table " + t2 + " add " + primary_key_t1 + " " + primary_key_type_t1 + ";";
                db.ps = db.con.prepareStatement(init_querry);
                db.ps.execute();

                String ref_querry = "alter table " + t2 + " add foreign key(" + primary_key_t1 + ") " + "references "
                        + t1
                        + "("
                        + primary_key_t1 + ");";

                db.ps = db.con.prepareStatement(ref_querry);
                db.ps.execute();

                JOptionPane.showMessageDialog(null, "Relationship Sucess", "Sucess", JOptionPane.INFORMATION_MESSAGE);
                // generate delay to back towards the main frame
                Thread.sleep(2000);
                dispose();

            }

            if (ae.getSource() == many_to_many) {
                t2 = JOptionPane.showInputDialog(null, "Enter Second Table Name");
                t3 = JOptionPane.showInputDialog(null, "Enter Third Table Name");

                String createSQl = "create table " + t3 + "();";
                db.ps = db.con.prepareStatement(createSQl);
                db.ps.execute();
                // to find primary key and datatype of t1 and adding it into table 3

                db.dbmd = db.con.getMetaData();
                db.rs = db.dbmd.getTables(null, null, t1, new String[] { "TABLES" });
                db.rs = db.dbmd.getPrimaryKeys(null, null, t1);

                while (db.rs.next()) {
                    primary_key_t1 = db.rs.getString(4);
                    pkey_col_t1 = db.rs.getString("KEY_SEQ");
                }
                int pk_col_id_t1 = Integer.parseInt(pkey_col_t1);

                db.st = db.con.createStatement();
                db.rs = db.st.executeQuery("select * from " + t1);

                db.rsmd = db.rs.getMetaData();
                primary_key_type_t1 = db.rsmd.getColumnTypeName(pk_col_id_t1);

                String init_querry = "alter table " + t3 + " add " + primary_key_t1 + " " + primary_key_type_t1 + ";";
                db.ps = db.con.prepareStatement(init_querry);
                db.ps.execute();

                String ref_querry = "alter table " + t3 + " add foreign key(" + primary_key_t1 + ") " + "references "
                        + t1
                        + "("
                        + primary_key_t1 + ");";

                db.ps = db.con.prepareStatement(ref_querry);
                db.ps.execute();

                // to find primary key and datatype of t2 and adding it into table 3
                db.dbmd = db.con.getMetaData();
                db.rs = db.dbmd.getTables(null, null, t2, new String[] { "TABLES" });
                db.rs = db.dbmd.getPrimaryKeys(null, null, t2);

                while (db.rs.next()) {
                    primary_key_t2 = db.rs.getString(4);

                    pkey_col_t2 = db.rs.getString("KEY_SEQ");

                }
                int pk_col_id_t2 = Integer.parseInt(pkey_col_t2);

                db.st = db.con.createStatement();
                db.rs = db.st.executeQuery("select * from " + t2);

                db.rsmd = db.rs.getMetaData();
                primary_key_type_t2 = db.rsmd.getColumnTypeName(pk_col_id_t2);

                String init_querry1 = "alter table " + t3 + " add " + primary_key_t2 + " " + primary_key_type_t2 + ";";
                db.ps = db.con.prepareStatement(init_querry1);
                db.ps.execute();

                String ref_querry1 = "alter table " + t3 + " add foreign key(" + primary_key_t2 + ") " + "references "
                        + t2
                        + "("
                        + primary_key_t2 + ");";

                db.ps = db.con.prepareStatement(ref_querry1);
                db.ps.execute();

                int confirm = JOptionPane.showConfirmDialog(null, "Do You wants to add some extra fields", "Check",
                        JOptionPane.INFORMATION_MESSAGE);
                if (confirm == 0) {
                    new AddFields(t3);

                } else if (confirm == 1) {
                    JOptionPane.showMessageDialog(null, "Relationship Sucess", "Sucess",
                            JOptionPane.INFORMATION_MESSAGE);
                    // generate delay to back towards the main frame
                    Thread.sleep(2000);
                    dispose();
                }

            }
        } // try
        catch (Exception e) {
        }
    }

}// class RelationshipHandler
