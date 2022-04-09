package dashboard;

import authentication.WelcomeUser;
import forms.ComplaintForm;
import authentication.HomePage;
import forms.QueryForm;
import view.SearchComplaints;
import view.ViewPayments;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.*;

public class CustomerDashboard extends JFrame implements ActionListener {
    private Container container;
    private JLabel heading, welcomeUserLbl;
    private JButton makeComplaint, makeQuery, searchComplaints, viewPayment, logOut;
    private JDesktopPane desktop;

    public CustomerDashboard() {
        this.setTitle("Customer Dashboard");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToPanel();
        addComponentsToWindow();
        setWindowProperties();
        registerListeners();
    }

    public void initializeComponents() {
        heading = new JLabel("Customer Dashboard");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(80, 0, 440, 45);
        String name = WelcomeUser.userTxt.getText();
        welcomeUserLbl = new JLabel("Welcome User: " + name);
        welcomeUserLbl.setBounds(1060,20,200,28);
        welcomeUserLbl.setFont(new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 16));

        makeComplaint = new JButton("Make a Complaint");
        makeComplaint.setBounds(50, 60, 160, 28);
        makeComplaint.setMnemonic(KeyEvent.VK_1);
        makeQuery = new JButton("Make a Query");
        makeQuery.setBounds(50, 180, 160, 28);
        makeQuery.setMnemonic(KeyEvent.VK_2);
        searchComplaints = new JButton("Search Complaints");
        searchComplaints.setBounds(50, 300, 160, 28);
        searchComplaints.setMnemonic(KeyEvent.VK_3);
        viewPayment = new JButton("View Payment");
        viewPayment.setBounds(50, 420, 160, 28);
        logOut = new JButton("Log Out");
        logOut.setBounds(1350, 20, 100, 28);
        logOut.setMnemonic(KeyEvent.VK_BACK_QUOTE);


        desktop = new JDesktopPane();
        desktop.setBounds(400, 60, 1050, 600);
    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(welcomeUserLbl);
        container.add(makeComplaint);
        container.add(makeQuery);
        container.add(searchComplaints);
        container.add(viewPayment);
        container.add(logOut);
    }

    private void addComponentsToWindow() {
        this.add(desktop);
    }

    private void setWindowProperties() {
        this.setSize(1500, 750);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
    }

    public void registerListeners() {
        makeComplaint.addActionListener(this);
        makeQuery.addActionListener(this);
        searchComplaints.addActionListener(this);
        viewPayment.addActionListener(this);
        logOut.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == makeComplaint) {
            desktop.add(new ComplaintForm());
        }
        if (e.getSource() == makeQuery) {
            desktop.add(new QueryForm());
        }
        if (e.getSource() == searchComplaints) {
            desktop.add(new SearchComplaints());
        }
        if (e.getSource() == viewPayment) {
            desktop.add(new ViewPayments());
        }

        if (e.getSource() == logOut) {
            this.dispose();
            new HomePage();
        }
    }
}
