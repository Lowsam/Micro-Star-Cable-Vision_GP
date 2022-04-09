package view;

import authentication.WelcomeUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SearchComplaints extends JInternalFrame implements ActionListener {

    private JLabel heading, idLbl;
    private JTextField idTxt;
    private JButton searchBtn;
    private JTable jTable;
    private JScrollPane scrollPane;
    private Container container;

    public SearchComplaints() {
        super("", true, true, false, false);
        this.setTitle("Search Compliants");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToPanel();
        setWindowProperties();
        registerListeners();
    }

    public void initializeComponents() {
        heading = new JLabel("COMPLIANTS LIST");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(50, 0, 440, 45);

        idLbl = new JLabel("ID Number: ");
        idLbl.setBounds(50, 60, 100, 28);

        idTxt = new JTextField();
        idTxt.setBounds(250, 60, 200, 28);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(475, 60, 100, 28);


        jTable = new JTable();

        scrollPane = new JScrollPane(jTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 120, 955,400);
    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(idLbl);
        container.add(idTxt);
        container.add(searchBtn);
//        container.add(jTable);
        container.add(scrollPane);
    }

    private void setWindowProperties() {
        this.setSize(1050,600);
        this.setVisible(true);
    }

    public void registerListeners() {
        searchBtn.addActionListener(this);
    }


    public void tableDetails() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        tableModel.setColumnCount(0);
        tableModel.addColumn("Date");
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("Complaint Type");
        tableModel.addColumn("Problem Description");
        tableModel.addColumn("Status");
        tableModel.addColumn("Response");
        tableModel.addColumn("Technician ID");
        tableModel.addColumn("Date Resolved");
        Connection connection = null;

        String ID = idTxt.getText();
        tableModel.setRowCount(0);

        if (!ID.equals("")) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                ID += '%';
                PreparedStatement prepStat = connection.prepareStatement("Select Date, Customer_ID, Complaint_Type, Problem_Description, Status, Response, Tech_ID, Date_of_Visit from complaints where Customer_ID like ? order by First_Name asc");
                prepStat.setString(1, ID);
                ResultSet rs = prepStat.executeQuery();
                while (rs.next()) {
                    tableModel.addRow(new Object[]
                            {rs.getDate(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8)
                            });
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                Statement st = connection.createStatement();
                ResultSet resultSet = st.executeQuery("Select Date, Customer_ID, Complaint_Type, Problem_Description, Status, Response, Tech_ID, Date_of_Visit from complaints order by Customer_ID asc");
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]
                            {resultSet.getDate(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4),
                                    resultSet.getString(5),
                                    resultSet.getString(6),
                                    resultSet.getString(7),
                                    resultSet.getString(8)

                            });
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            tableDetails();
        }
    }

}
