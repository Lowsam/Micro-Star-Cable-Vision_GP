package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RespondToCustomer extends JInternalFrame implements ActionListener {
    private JLabel heading, customerIDLbl, responseLbl;
    private JTextField idTxt;
    private JTextArea responseTxt;
    private JButton respondBtn;
    private JTable jTable;
    private JScrollPane scrollPane;
    private Container container;

    public RespondToCustomer() {
        super("", true, true, false, false);
        this.setTitle("Respond to Customer");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToPanel();
        setWindowProperties();
        registerListeners();
        tableDetails();
    }

    public void initializeComponents() {
        heading = new JLabel("RESPONSE");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(50, 0, 440, 45);

        customerIDLbl = new JLabel("Customer ID: ");
        customerIDLbl.setBounds(50, 60, 100, 28);
        responseLbl = new JLabel("Response: ");
        responseLbl.setBounds(50, 110, 100, 28);

        idTxt = new JTextField();
        idTxt.setBounds(250, 60, 200, 28);
        responseTxt = new JTextArea();
        responseTxt.setBounds(250, 110, 200, 84);
        responseTxt.setWrapStyleWord(true);
        responseTxt.setLineWrap(true);

        respondBtn = new JButton("Add Response");
        respondBtn.setBounds(250, 210, 200, 28);

        jTable = new JTable();
        scrollPane = new JScrollPane(jTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 250, 955, 300);
    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(customerIDLbl);
        container.add(idTxt);
        container.add(responseLbl);
        container.add(responseTxt);
        container.add(respondBtn);
        container.add(scrollPane);

    }

    private void setWindowProperties() {
        this.setSize(1050, 600);
        this.setVisible(true);
    }

    public void registerListeners() {
        respondBtn.addActionListener(this);
    }

    public void tableDetails() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        tableModel.setColumnCount(0);
        tableModel.addColumn("Date");
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("Complaint Type");
        tableModel.addColumn("Problem Description");
        tableModel.addColumn("Response");
        tableModel.addColumn("Technician ID");
//        tableModel.addColumn("Date Resolved");
        Connection connection = null;

        String ID = idTxt.getText();
        tableModel.setRowCount(0);

        if (!ID.equals("")) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                ID += '%';
                PreparedStatement prepStat = connection.prepareStatement("Select Date, Customer_ID, Complaint_Type, Problem_Description, Response, Tech_ID from complaints where Customer_ID like ?");
                prepStat.setString(1, ID);
                ResultSet rs = prepStat.executeQuery();
                while (rs.next()) {
                    tableModel.addRow(new Object[]
                            {rs.getDate(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6)
                            });
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                Statement st = connection.createStatement();
                ResultSet resultSet = st.executeQuery("Select Date, Customer_ID, Complaint_Type, Problem_Description, Response, Tech_ID from complaints");
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]
                            {resultSet.getDate(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4),
                                    resultSet.getString(5),
                                    resultSet.getString(6)

                            });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == respondBtn) {
            tableDetails();
            String Response = responseTxt.getText();


            String id = idTxt.getText();
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                PreparedStatement prepStat1 = connection.prepareStatement("Select Date, Customer_ID, Complaint_Type, Problem_Description, Response, Tech_ID from complaints where Customer_ID ='" + id + "'");
                ResultSet rs1 = prepStat1.executeQuery();
                if (rs1.next()) {
                    if (rs1.getString("Customer_ID").equalsIgnoreCase(id)) {
                        PreparedStatement pStat = connection.prepareStatement("Update complaints set Response='" + Response + "' where Customer_ID='" + id + "'");
                        pStat.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Updated successfully");
                        idTxt.setText(null);
                        responseTxt.setText(null);
                        tableDetails();
                    } else {
                        JOptionPane.showMessageDialog(null, rs1.getString("Customer_ID") + " has already been responded to.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

}