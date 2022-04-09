package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewCustomer extends JInternalFrame implements ActionListener {
    private JLabel heading, customerIDLbl;
    private JTextField idTxt;
    private JButton searchBtn;
    private JTable jTable;
    private JScrollPane scrollPane;
    private Container container;

    public ViewCustomer() {
        super("", true, true, false, false);
        this.setTitle("Customer Database");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToPanel();
        setWindowProperties();
        registerListeners();
    }

    public void initializeComponents() {
        heading = new JLabel("CUSTOMER DATABASE");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(50, 0, 440, 45);

        customerIDLbl = new JLabel("Customer ID: ");
        customerIDLbl.setBounds(50, 60, 100, 28);

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
        container.add(customerIDLbl);
        container.add(idTxt);
        container.add(searchBtn);
        container.add(scrollPane);
    }

    private void setWindowProperties() {
        this.setSize(1050, 600);
        this.setVisible(true);
    }

    public void registerListeners() {
        searchBtn.addActionListener(this);
    }

    public void viewCustomerDetails() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        tableModel.setColumnCount(0);
        tableModel.addColumn("Date");
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("Complaint Type");
        tableModel.addColumn("Problem Description");
        tableModel.addColumn("Response");
        tableModel.addColumn("Technician ID");
        tableModel.addColumn("Status");

        String ID = idTxt.getText();
        tableModel.setRowCount(0);

        Connection connection = null;
        if (!ID.equals("")) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                ID += '%';
                PreparedStatement prepStat = connection.prepareStatement("Select Date, Customer_ID, Complaint_Type, Problem_Description, Response, Tech_ID, Status from complaints where Customer_ID like ? ");
                prepStat.setString(1, ID);
                ResultSet resultSet = prepStat.executeQuery();
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getDate(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                Statement st = connection.createStatement();
                ResultSet resultSet = st.executeQuery("Select Date, Customer_ID, Complaint_Type, Problem_Description, Response, Tech_ID, Status from complaints order by Customer_ID asc");
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]
                            {resultSet.getDate(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4),
                                    resultSet.getString(5),
                                    resultSet.getString(6),
                                    resultSet.getString(7)

                            });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            viewCustomerDetails();
        }
    }
}
