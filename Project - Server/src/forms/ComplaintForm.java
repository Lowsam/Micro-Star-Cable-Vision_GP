package forms;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class ComplaintForm extends JInternalFrame implements ActionListener {
    private JLabel heading, dateLbl, customerIdLbl, customerFirstNameLbl,
            customerLastNameLbl, phoneLbl, emailLbl, addressLbl, complaintBoxLbl, complaintAreaLbl;
    private JTextField idTxt, firstNameTxt, lastNameTxt, phoneTxt, emailTxt, addressTxt;
    private JComboBox complaintBox;
    private JTextArea complaintArea;
    private JButton submitBtn, clrBtn;
    private JDateChooser dateChooser;
    private Container container;

    public ComplaintForm() {
        super("",true,true,false,false);
        this.setTitle("Complaint Form");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToPanel();
        setWindowProperties();
        registerListeners();
    }

    public void initializeComponents() {
        heading = new JLabel("COMPLAINT FORM");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(300, 0, 440, 45);

        dateLbl = new JLabel("Date: ");
        dateLbl.setBounds(250, 60, 80, 28);
        customerIdLbl = new JLabel("Customer ID: ");
        customerIdLbl.setBounds(250, 110, 80, 28);
        customerFirstNameLbl = new JLabel("First Name: ");
        customerFirstNameLbl.setBounds(250, 150, 100, 28);
        customerLastNameLbl = new JLabel("Last Name: ");
        customerLastNameLbl.setBounds(250, 195, 100, 28);
        phoneLbl = new JLabel("Phone Number: ");
        phoneLbl.setBounds(250, 240, 100, 28);
        emailLbl = new JLabel("Email: ");
        emailLbl.setBounds(250, 285, 100, 28);
        addressLbl = new JLabel("Address: ");
        addressLbl.setBounds(250, 330, 100, 28);

        complaintBoxLbl = new JLabel("Complaint Type");
        complaintBoxLbl.setBounds(250, 375, 100, 28);
        complaintAreaLbl = new JLabel("Problem Description: ");
        complaintAreaLbl.setBounds(250, 420, 130, 28);


        dateChooser = new JDateChooser();
        dateChooser.setBounds(430, 60, 240, 28);
        idTxt = new JTextField();
        idTxt.setBounds(430, 105, 240, 28);
        firstNameTxt = new JTextField();
        firstNameTxt.setBounds(430, 150, 240, 28);
        lastNameTxt = new JTextField();
        lastNameTxt.setBounds(430, 195, 240, 28);
        phoneTxt = new JTextField();
        phoneTxt.setBounds(430, 240, 240, 28);
        emailTxt = new JTextField();
        emailTxt.setBounds(430, 285, 240, 28);
        addressTxt = new JTextField();
        addressTxt.setBounds(430, 330, 240, 28);


        String complaint[] = {"Broadband", "Missing Channels", "No Connection"};
        complaintBox = new JComboBox(complaint);
        complaintBox.setBounds(430, 375, 240, 28);

        complaintArea = new JTextArea();
        complaintArea.setBounds(430, 420, 240, 84);
        complaintArea.setLineWrap(true);
        complaintArea.setWrapStyleWord(true);

        submitBtn = new JButton("Submit");
        submitBtn.setBounds(435, 520, 100, 28);
        submitBtn.setBackground(new Color(0xB9EFE1));
        clrBtn = new JButton("Clear");
        clrBtn.setBounds(565, 520, 100, 28);
        clrBtn.setBackground(new Color(0xB9EFE1));

    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(dateLbl);
        container.add(dateChooser);

        container.add(customerIdLbl);
        container.add(idTxt);
        container.add(customerFirstNameLbl);
        container.add(firstNameTxt);
        container.add(customerLastNameLbl);
        container.add(lastNameTxt);
        container.add(phoneLbl);
        container.add(phoneTxt);
        container.add(emailLbl);
        container.add(emailTxt);
        container.add(addressLbl);
        container.add(addressTxt);

        container.add(complaintBoxLbl);
        container.add(complaintBox);
        container.add(complaintAreaLbl);
        container.add(complaintArea);

        container.add(submitBtn);
        container.add(clrBtn);
    }


    private void setWindowProperties() {
        this.setSize(1050,600);
        this.setVisible(true);
    }

    public void registerListeners() {
        submitBtn.addActionListener(this);
        clrBtn.addActionListener(this);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
                PreparedStatement prepStat = connection.prepareStatement("Insert into complaints values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

                    prepStat.setDate(1, new Date(dateChooser.getDate().getTime()));
                    prepStat.setString(2, idTxt.getText());
                    prepStat.setString(3, firstNameTxt.getText());
                    prepStat.setString(4, lastNameTxt.getText());
                    prepStat.setString(5, phoneTxt.getText());
                    prepStat.setString(6, emailTxt.getText());
                    prepStat.setString(7, addressTxt.getText());
                    prepStat.setString(8, complaintBox.getSelectedItem().toString());
                    prepStat.setString(9, complaintArea.getText());
                    prepStat.setString(10,"Pending");
                    prepStat.setString(11,"Pending");
                    prepStat.setString(12,"Pending");
                    prepStat.setString(13,"Pending");
                    prepStat.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Complaint made successfully. Please wait for a response.");
                } catch(SQLException ex){
                    ex.printStackTrace();
                }
        }
        if (e.getSource() == clrBtn){
            dateChooser.setDate(null);
            idTxt.setText(null);
            firstNameTxt.setText(null);
            lastNameTxt.setText(null);
            phoneTxt.setText(null);
            emailTxt.setText(null);
            addressTxt.setText(null);
            complaintBox.setSelectedIndex(0);
            complaintArea.setText(null);
        }
    }
}
