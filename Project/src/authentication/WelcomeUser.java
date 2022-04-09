package authentication;

import dashboard.CustomerDashboard;
import dashboard.RepresentativeDashboard;
import dashboard.TechnicianDashboard;
import forms.ComplaintForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class WelcomeUser extends JFrame implements MouseListener {
    private JLabel title, customer, admin;
    public static JTextField userTxt, jtx;
    public static JPasswordField userPwd, jpf;
    private Container container;
    private static final Logger logger = LogManager.getLogger(WelcomeUser.class);

    public WelcomeUser() {
        this.setTitle("User Selection");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToContainer();
        setWindowProperties();
        registerListeners();

    }

    public void initializeComponents() {
        title = new JLabel("Welcome User");
        title.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        title.setBounds(60, 0, 440, 45);

        customer = new JLabel("Customer", JLabel.CENTER);
        customer.setOpaque(true);
        customer.setBackground(Color.white);
        customer.setBounds(50, 60, 80, 28);

        admin = new JLabel("Employee", JLabel.CENTER);
        admin.setOpaque(true);
        admin.setBackground(Color.white);
        admin.setBounds(260, 60, 80, 28);
    }


    private void addComponentsToContainer() {
        container.add(title);
        container.add(customer);
        container.add(admin);
    }

    private void setWindowProperties() {
        this.setSize(400, 200);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    private void registerListeners() {
        admin.addMouseListener(this);
        customer.addMouseListener(this);
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == admin) {
            //placeholder code for database
            jtx = new JTextField();
            jtx.setBounds(50, 100, 200, 30);
            jpf = new JPasswordField();
            jpf.setBounds(50, 150, 200, 30);
            WelcomeUser.this.add(jtx);
            WelcomeUser.this.add(jpf);
            Object[] inputFields = new Object[]{"Staff ID", jtx, "Password", jpf};


            int option = JOptionPane.showConfirmDialog(WelcomeUser.this, inputFields, "Employee Login", 2, 1);
            if (option == 0) {
                Connection connection = null;
                String position = "";
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                    PreparedStatement prepStat = connection.prepareStatement("Select Staff_ID from employees where Staff_ID = '" + jtx.getText() + "' and Password = '" + jpf.getText() + "'");
                    ResultSet resultSet = prepStat.executeQuery();

                    String query = "SELECT Position FROM employees WHERE Staff_ID='" + jtx.getText() + "'";
                    Statement statement = connection.createStatement();
                    ResultSet rSet = statement.executeQuery(query);

                    while (rSet.next()) {
                        position = rSet.getString(1);
                    }
                    if (resultSet.next()) {
                        String Staff_ID = resultSet.getString("Staff_ID");

                        if (position.equalsIgnoreCase("Representative")) {
                            new RepresentativeDashboard();
                            setVisible(false);
                        } else if (position.equalsIgnoreCase("Technician")) {
                            new TechnicianDashboard();
                            setVisible(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect username or Password");
                        logger.error("Wrong info inputted");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }
        if (e.getSource() == customer) {

            userTxt = new JTextField();
            userTxt.setBounds(50, 100, 200, 30);
            userPwd = new JPasswordField();
            userPwd.setBounds(50, 150, 200, 30);
            WelcomeUser.this.add(userTxt);
            WelcomeUser.this.add(userPwd);
            Object[] inputFields = new Object[]{"Customer ID", userTxt, "Password", userPwd};
            int option = JOptionPane.showConfirmDialog(WelcomeUser.this, inputFields, "Customer Login", 2, 1);
            if (option == 0) {

                Connection connection = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                    PreparedStatement prepStat = connection.prepareStatement("Select Customer_ID from customers where Customer_ID = '" + userTxt.getText() + "' and Password = '" + userPwd.getText() + "'");
                    ResultSet resultSet = prepStat.executeQuery();
                    if (resultSet.next()) {
                        String Customer_ID = resultSet.getString("Customer_ID");
                        setVisible(false);
                        new CustomerDashboard();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect username or Password");
                        logger.error("Wrong info inputted.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}

