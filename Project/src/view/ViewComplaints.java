package view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewComplaints extends JInternalFrame implements ActionListener {
    private JButton allComplaintsBtn, broadbandBtn, noConnectionBtn, missingChannelBtn;
    private JLabel heading;
    private JTable jTable;
    private JScrollPane scrollPane;
    private Container container;

    public ViewComplaints() {
        super("", true, true, false, false);
        this.setTitle("View Complaints");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToPanel();
        setWindowProperties();
        registerListeners();
    }

    public void initializeComponents() {
        heading = new JLabel("COMPLAINT STATUS");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(50, 0, 440, 45);

        allComplaintsBtn = new JButton("All Complaints");
        allComplaintsBtn.setBounds(50, 60, 155, 28);
        broadbandBtn = new JButton("Broadband");
        broadbandBtn.setBounds(220, 60, 155, 28);
        noConnectionBtn = new JButton("No Connection");
        noConnectionBtn.setBounds(390, 60, 155, 28);
        missingChannelBtn = new JButton("Missing Channels");
        missingChannelBtn.setBounds(560, 60, 155, 28);

        jTable = new JTable();
        scrollPane = new JScrollPane(jTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 120, 955, 400);
    }

    private void addComponentsToPanel() {
        container.add(heading);

        container.add(allComplaintsBtn);
        container.add(broadbandBtn);
        container.add(noConnectionBtn);
        container.add(missingChannelBtn);

        container.add(scrollPane);
    }

    private void setWindowProperties() {
        this.setSize(1050, 600);
        this.setVisible(true);
    }

    public void registerListeners() {
        allComplaintsBtn.addActionListener(this);
        broadbandBtn.addActionListener(this);
        noConnectionBtn.addActionListener(this);
        missingChannelBtn.addActionListener(this);
    }

    public void tableDetails(String name) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        tableModel.setColumnCount(0);
        tableModel.addColumn("Date");
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("Complaint Type");
        tableModel.addColumn("Problem Description");
        tableModel.addColumn("Response");
        tableModel.addColumn("Technician ID");
        tableModel.addColumn("Status");
        tableModel.addColumn("Date Solved");


        tableModel.setRowCount(0);
        if (!name.equalsIgnoreCase("All Complaints")) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                PreparedStatement prepStat = connection.prepareStatement("Select * from complaints");
                ResultSet rs = prepStat.executeQuery();
                if (rs.next()) {
                    PreparedStatement pStat = connection.prepareStatement("Select Date, Customer_ID, Complaint_Type, Problem_Description, Response, Tech_ID, Status, Date_of_Visit from complaints where Complaint_Type='" + name + "'");
                    ResultSet resultSet = pStat.executeQuery();
                    while (resultSet.next()) {
                        tableModel.addRow(new Object[]{
                                resultSet.getDate(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8)
                        });
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                PreparedStatement prepStat = connection.prepareStatement("Select * from complaints");
                ResultSet rs = prepStat.executeQuery();
                if (rs.next()) {
                    PreparedStatement pStat = connection.prepareStatement("Select Date, Customer_ID, Complaint_Type, Problem_Description, Response, Tech_ID, Status, Date_of_Visit from complaints");
                    ResultSet resultSet = pStat.executeQuery();
                    while (resultSet.next()) {
                        tableModel.addRow(new Object[]{
                                resultSet.getDate(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8)
                        });
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == allComplaintsBtn) {
            tableDetails("All Complaints");
        }
        if (e.getSource() == broadbandBtn) {
            tableDetails("Broadband");
        }
        if (e.getSource() == noConnectionBtn) {
            tableDetails("No Connection");
        }
        if (e.getSource() == missingChannelBtn) {
            tableDetails("Missing Channels");
        }
    }
}
