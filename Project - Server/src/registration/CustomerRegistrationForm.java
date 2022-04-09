package registration;

import authentication.HomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerRegistrationForm extends JFrame implements ActionListener {
    private JLabel title, customerIDLbl, customerPwdLbl, confirmcustomerPwdLbl;
    private JTextField customerIDTxt;
    private JPasswordField customerPwdTxt, confirmcustomerPwdTxt;
    private JButton submitBtn, backBtn;
    private Container container;

    public CustomerRegistrationForm() {
        this.setTitle("Customer Registration Form");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToContainer();
        setWindowProperties();
        registerListeners();
    }

    public void initializeComponents() {
        title = new JLabel("REGISTRATION FORM");
        title.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        title.setBounds(60, 0, 440, 45);

        customerIDLbl = new JLabel("Customer ID: ");
        customerIDLbl.setBounds(50, 60, 130, 28);
        customerPwdLbl = new JLabel("Customer Password: ");
        customerPwdLbl.setBounds(50, 110, 130, 28);
        confirmcustomerPwdLbl = new JLabel("Confirm Password: ");
        confirmcustomerPwdLbl.setBounds(50, 160, 130, 28);
        customerIDTxt = new JTextField();
        customerIDTxt.setBounds(250, 60, 200, 28);
        customerPwdTxt = new JPasswordField();
        customerPwdTxt.setBounds(250, 110, 200, 28);
        confirmcustomerPwdTxt = new JPasswordField();
        confirmcustomerPwdTxt.setBounds(250, 160, 200, 28);

        submitBtn = new JButton("Submit");
        submitBtn.setBounds(165, 210, 200, 28);
        backBtn = new JButton("Back");
        backBtn.setBounds(50,300,100,28);
    }

    private void addComponentsToContainer() {
        container.add(title);
        container.add(customerIDLbl);
        container.add(customerIDTxt);
        container.add(customerPwdLbl);
        container.add(customerPwdTxt);
        container.add(confirmcustomerPwdLbl);
        container.add(confirmcustomerPwdTxt);
        container.add(submitBtn);
        container.add(backBtn);
    }

    private void registerListeners() {
        submitBtn.addActionListener(this);
        backBtn.addActionListener(this);
    }

    private void setWindowProperties() {
        this.setSize(550, 400);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn) {
            try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root","password");
            PreparedStatement prepStat = connection.prepareStatement("Insert into customers values(?,?)");

                prepStat.setString(1,customerIDTxt.getText());
                prepStat.setString(2,customerPwdTxt.getText());

                if (customerPwdTxt.getText().equalsIgnoreCase(confirmcustomerPwdTxt.getText())){
                    prepStat.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Your Data has been registered successfully.");
                    customerIDTxt.setText(null);
                    customerPwdTxt.setText(null);
                    confirmcustomerPwdTxt.setText(null);
                }else{
                    JOptionPane.showMessageDialog(null,"Password did not match. Please try again.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            CustomerRegistrationForm.this.dispose();
            new HomePage();

        }
        if (e.getSource() == backBtn){
            CustomerRegistrationForm.this.dispose();
            new HomePage();
        }
    }
}
