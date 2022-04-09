package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AssignTechnician extends JInternalFrame implements ActionListener {
    private JLabel heading, technicianIDLbl, customerIDLbl;
    private JTextField techIDTxt, custIDTxt;
    private JButton assignTechBtn;
    private JTable jTable;
    private JScrollPane scrollPane;
    private Container container;


    public AssignTechnician() {
        super("", true, true, false, false);
        this.setTitle("Assign Technician");
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
        heading = new JLabel("ASSIGNING TECHNICIAN");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 30)));
        heading.setBounds(50, 0, 440, 45);

        customerIDLbl = new JLabel("Customer ID");
        customerIDLbl.setBounds(50, 60, 100, 28);
        technicianIDLbl = new JLabel("Technician ID");
        technicianIDLbl.setBounds(50, 110, 100, 28);

        custIDTxt = new JTextField();
        custIDTxt.setBounds(250, 60, 200, 28);
        techIDTxt = new JTextField();
        techIDTxt.setBounds(250, 110, 200, 28);

        assignTechBtn = new JButton("Assign Technician");
        assignTechBtn.setBounds(50, 160, 200, 28);

        jTable = new JTable();
        scrollPane = new JScrollPane(jTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 250, 955, 300);
    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(customerIDLbl);
        container.add(custIDTxt);
        container.add(technicianIDLbl);
        container.add(techIDTxt);
        container.add(assignTechBtn);
        container.add(scrollPane);
    }

    private void setWindowProperties() {
        this.setSize(1050, 600);
        this.setVisible(true);
    }

    public void registerListeners() {
        assignTechBtn.addActionListener(this);
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

        String ID = custIDTxt.getText();
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
        if (e.getSource() == assignTechBtn) {
            tableDetails();
            String tech = techIDTxt.getText();


            String id = custIDTxt.getText();
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                PreparedStatement prepStat1 = connection.prepareStatement("Select Date, Customer_ID, Complaint_Type, Problem_Description, Response, Tech_ID from complaints where Customer_ID ='" + id + "'");
                ResultSet rs1 = prepStat1.executeQuery();
                if (rs1.next()) {
                    if (rs1.getString("Customer_ID").equalsIgnoreCase(id)) {
                        PreparedStatement pStat = connection.prepareStatement("Update complaints set Tech_ID='" + tech + "' where Customer_ID='" + id + "'");
                        pStat.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Updated successfully");
                        custIDTxt.setText(null);
                        techIDTxt.setText(null);
                        tableDetails();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

