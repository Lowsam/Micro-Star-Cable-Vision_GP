package classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class CustomerView extends JFrame implements ActionListener {
    JButton insertButton, updateButton, selectButton, deleteButton;
    JTextField idTxt, pwdTxt;
    JPanel panTop, panBottom;
    JLabel idLbl, pwdLbl;
    JTable jTable1;
    JScrollPane jScrollPane1;
    User user;

    public CustomerView(User user) {
        this.user = user;
        this.setLayout(new BorderLayout(6, 6));
        initializeComponents();
        addComponentsToPanel();
        addPanelToWindow();
        setWindowProperties();
        registerListeners();
    }

    private void retrieve() throws SQLException {
        DefaultTableModel databaseTable = user.getData();
        jTable1.setModel(databaseTable);
    }

    private void initializeComponents() {
        insertButton = new JButton("Insert");
        updateButton = new JButton("Update");
        selectButton = new JButton("Select");
        deleteButton = new JButton("Delete");

        idLbl = new JLabel("Customer Id");
        idTxt = new JTextField(30);
        pwdLbl = new JLabel("Password");
        pwdTxt = new JTextField(30);


        panTop = new JPanel();
        panBottom = new JPanel();
        jTable1 = new JTable();
        jScrollPane1 = new JScrollPane() {
            public Dimension getPreferredSize() {
                return new Dimension(335, 150);
            }
        };

        jTable1.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String[]{

                }
        ));
        jScrollPane1.setViewportView(jTable1);


    }

    private void addComponentsToPanel() {
        panTop.add(idLbl);
        panTop.add(idTxt);
        panTop.add(pwdLbl);
        panTop.add(pwdTxt);

        panTop.add(jScrollPane1);

        panBottom.add(insertButton);
        panBottom.add(updateButton);
        panBottom.add(selectButton);
        panBottom.add(deleteButton);
    }

    private void addPanelToWindow() {
        this.add(panTop, "Center");
        this.add(panBottom, "South");
    }

    private void setWindowProperties() {
        this.setSize(400, 500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    private void registerListeners() {
        insertButton.addActionListener(this);
        updateButton.addActionListener(this);
        selectButton.addActionListener(this);
        deleteButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String id = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
                String name = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();

                idTxt.setText(id);
                pwdTxt.setText(name);

            }
        });

        if (e.getSource() == insertButton) {
            try {
                user.insert(idTxt.getText(), pwdTxt.getText());
                JOptionPane.showMessageDialog(null, "Successfully Inserted");

                //clear text
                idTxt.setText("");
                pwdTxt.setText("");

                retrieve();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        if (e.getSource() == selectButton) {
            try {
                retrieve();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == updateButton) {
            try {
                user.update(idTxt.getText(), pwdTxt.getText());
                JOptionPane.showMessageDialog(null, "Successfully Updated");

                //clear text
                idTxt.setText("");
                pwdTxt.setText("");
                retrieve();
            } catch (
                    SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == deleteButton) {
            String[] options = {"Yes", "No"};
            int answer = JOptionPane.showOptionDialog(null, "Confirm Request?", "Delete Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

            if (answer == 0) {

                try {
                    user.delete(idTxt.getText());
                    JOptionPane.showMessageDialog(null, "Deleted Updated");

                    //clear text
                    idTxt.setText("");
                    pwdTxt.setText("");
                    retrieve();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }


}
