package view;

import authentication.WelcomeUser;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewCustomerComplaints extends JInternalFrame implements ActionListener {

    private JLabel heading, customerIdLbl, statusLbl, responseLbl, dateLbl;
    private JTextField idTxt, statusTxt;
    private JTextArea responseTxt;
    private JDateChooser dateTxt;
    private JButton searchBtn, updateBtn;
    private JTable jTable;
    private JScrollPane scrollPane;
    private Container container;


    public ViewCustomerComplaints() {
        super("", true, true, false, false);
        this.setTitle("Customer Complaints");
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
        heading = new JLabel("CUSTOMER COMPLAINTS");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(30, 0, 490, 40);

        customerIdLbl = new JLabel("Enter Customer ID: ");
        customerIdLbl.setBounds(50, 60, 150, 28);
        statusLbl = new JLabel("Status: ");
        statusLbl.setBounds(50, 380, 150, 28);
        dateLbl = new JLabel("Proposed Date of Visit: ");
        dateLbl.setBounds(50, 420, 150, 28);
        responseLbl = new JLabel("Response: ");
        responseLbl.setBounds(50, 460, 150, 28);

        idTxt = new JTextField();
        idTxt.setBounds(250, 60, 200, 28);
        statusTxt = new JTextField();
        statusTxt.setBounds(250, 380, 200, 28);
        dateTxt = new JDateChooser();
        dateTxt.setBounds(250, 420, 200, 28);
        responseTxt = new JTextArea();
        responseTxt.setBounds(250, 460, 200, 100);
        responseTxt.setLineWrap(true);
        responseTxt.setWrapStyleWord(true);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(505, 58, 100, 28);
        updateBtn = new JButton("Update");
        updateBtn.setBounds(50, 500, 100, 28);

        jTable = new JTable();
        scrollPane = new JScrollPane(jTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 110, 955, 250);
    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(customerIdLbl);
        container.add(statusLbl);
        container.add(responseLbl);
        container.add(dateLbl);

        container.add(idTxt);
        container.add(statusTxt);
        container.add(responseTxt);
        container.add(dateTxt);
        container.add(searchBtn);
        container.add(updateBtn);
        container.add(scrollPane);
    }

    private void setWindowProperties() {
        this.setSize(1050, 600);
        this.setVisible(true);
    }

    public void registerListeners() {
        searchBtn.addActionListener(this);
        updateBtn.addActionListener(this);
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

        tableModel.setRowCount(0);
        String ID = WelcomeUser.jtx.getText();
        String Pwd = WelcomeUser.jpf.getText();

        if (!ID.equals("")) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                PreparedStatement preparedStatement = connection.prepareStatement("Select Staff_ID from employees where Staff_ID='" + ID + "' and Password='" + Pwd + "'");
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    PreparedStatement st = connection.prepareStatement("Select Date, Customer_ID, Complaint_Type, Problem_Description, Status, Response, Tech_ID, Date_of_Visit from complaints where Tech_ID='" + ID + "'");
                    ResultSet resultSet = st.executeQuery();
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
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
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


                } catch (SQLException ex) {
                    ex.printStackTrace();
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
                } catch (SQLException ex1) {
                    ex1.printStackTrace();
                }


            }
        }
        if (e.getSource() == updateBtn) {
            tableDetails();
            String ID = idTxt.getText();
            Date date = new Date(dateTxt.getDate().getTime());
            String response = responseTxt.getText();
            String status = statusTxt.getText();

            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                PreparedStatement prepStat1 = connection.prepareStatement("Select Date, Customer_ID, Complaint_Type, Problem_Description, Status, Response, Tech_ID, Date_of_Visit from complaints where Customer_ID ='" + ID + "'");
                ResultSet rs1 = prepStat1.executeQuery();
                if (rs1.next()) {
                    if (rs1.getString("Customer_ID").equalsIgnoreCase(ID)) {
                        PreparedStatement pStat = connection.prepareStatement("Update complaints set Response='" + response + "', Date_of_Visit='" + date + "', Status='" + status + "' where Customer_ID='" + ID + "'");
                        pStat.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Updated successfully");
                        idTxt.setText(null);
                        dateTxt.setDate(null);
                        responseTxt.setText(null);
                        statusTxt.setText(null);
                        tableDetails();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

