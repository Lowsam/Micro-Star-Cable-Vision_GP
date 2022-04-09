package authentication;

import registration.CustomerRegistrationForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame implements ActionListener {
    private JButton loginBtn, registerBtn, logOffBtn;
    private JLabel homePageTitle;
    private Container container;

    public HomePage() {
        this.setTitle("Home Page");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToContainer();
        setWindowProperties();
        registerListeners();
    }

    public void initializeComponents() {
        homePageTitle = new JLabel("Home Page");
        homePageTitle.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        homePageTitle.setBounds(90, 0, 450, 45);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(50, 60, 100, 28);
        registerBtn = new JButton("Register");
        registerBtn.setBounds(245, 60, 100, 28);
        logOffBtn = new JButton("Log Off");
        logOffBtn.setBounds(160, 120, 75, 28);
    }

    private void addComponentsToContainer() {
        container.add(homePageTitle);
        container.add(loginBtn);
        container.add(registerBtn);
        container.add(logOffBtn);
    }

    private void setWindowProperties() {
        this.setSize(400, 200);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void registerListeners() {
        loginBtn.addActionListener(this);
        registerBtn.addActionListener(this);
        logOffBtn.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            HomePage.this.dispose();
            new WelcomeUser();
        }
        if (e.getSource() == registerBtn) {
            HomePage.this.dispose();
            new CustomerRegistrationForm();
        }
        if (e.getSource() == logOffBtn) {
            HomePage.this.dispose();
        }
    }
}
