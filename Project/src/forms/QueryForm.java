package forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class QueryForm extends JInternalFrame implements ActionListener {
    private JLabel heading, custID, custName;
    private JTextField idTxt, nameTxt;
    private JButton queryBtn;
    private JTable jTable;
    private JScrollPane scrollPane;
    private Container container;
//    private DefaultTableModel tableModel;

    public QueryForm() {
        super("", true, true, false, false);
        this.setTitle("Query Form");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToPanel();
        setWindowProperties();
        registerListeners();
    }

    public void initializeComponents() {
        heading = new JLabel("QUERY FORM");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(50, 0, 440, 45);

        custID = new JLabel("Customer ID:");
        custID.setBounds(50, 60, 100, 28);

        idTxt = new JTextField();
        idTxt.setBounds(250, 60, 200, 28);

        queryBtn = new JButton("Query");
        queryBtn.setBounds(475, 60, 100, 28);

        jTable = new JTable();
        scrollPane = new JScrollPane(jTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 120, 955, 400);
    }

    private void addComponentsToPanel() {
        container.add(heading);

        container.add(custID);
        container.add(idTxt);


        container.add(queryBtn);
        container.add(scrollPane);
    }

    private void setWindowProperties() {
        this.setSize(1050,600);
        this.setVisible(true);
    }

    public void registerListeners() {
        queryBtn.addActionListener(this);
    }

    public void tableDetails() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        tableModel.setColumnCount(0);
        tableModel.addColumn("Customer ID");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Payment Status");
        tableModel.addColumn("Amount Due");
        tableModel.addColumn("Payment Due Date");
        Connection connection = null;

        String ID = idTxt.getText();
        tableModel.setRowCount(0);

        if (!ID.equals("")) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                ID += '%';
                PreparedStatement prepStat = connection.prepareStatement("Select * from queries where Customer_ID like ? order by First_Name asc");
                prepStat.setString(1, ID);
                ResultSet rs = prepStat.executeQuery();
                while (rs.next()) {
                    tableModel.addRow(new Object[]
                            {rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getDate(6)
                            });
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                Statement st = connection.createStatement();
                ResultSet resultSet = st.executeQuery("Select * from queries order by Customer_ID asc");
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]
                            {resultSet.getString(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3),
                                    resultSet.getString(4),
                                    resultSet.getString(5),
                                    resultSet.getDate(6)
                            });
                }
            } catch (SQLException e){
                e.printStackTrace();
            }

        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == queryBtn){
            idTxt.setText(null);
            tableDetails();
        }
    }
}
