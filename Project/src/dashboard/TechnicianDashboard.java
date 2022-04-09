package dashboard;

import authentication.HomePage;
import authentication.WelcomeUser;
import view.ViewCustomerComplaints;
import livechat.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class TechnicianDashboard extends JFrame implements ActionListener {
    private Container container;
    private JLabel heading, welcomeUserLbl;
    private JButton viewCustomerComplaint, logOut, chat;
    private JDesktopPane desktop;

    public TechnicianDashboard() {
        this.setTitle("Technician Dashboard");
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
        heading = new JLabel("Technician Dashboard");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(80, 0, 480, 45);
        String name = WelcomeUser.jtx.getText();
        welcomeUserLbl = new JLabel("Welcome Technician: " + name);
        welcomeUserLbl.setBounds(960,20,300,28);
        welcomeUserLbl.setFont(new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 16));

        viewCustomerComplaint = new JButton("View Customer Complaints");
        viewCustomerComplaint.setBounds(50, 60, 200, 28);
        viewCustomerComplaint.setMnemonic(KeyEvent.VK_1);
        logOut = new JButton("Log Out");
        logOut.setBounds(1350,20,100,28);
        logOut.setMnemonic(KeyEvent.VK_BACK_QUOTE);

        ImageIcon icon = new ImageIcon("chat-now-icon-png-11553722060rg3urfboym.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(30,28,Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        chat = new JButton(icon);
        chat.setBounds(1300,20,30,28);

        desktop = new JDesktopPane();
        desktop.setBounds(400, 60, 1050,600);
    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(welcomeUserLbl);
        container.add(viewCustomerComplaint);
        container.add(logOut);
        container.add(chat);
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
        viewCustomerComplaint.addActionListener(this);
        logOut.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewCustomerComplaint) {
            desktop.add(new ViewCustomerComplaints());
        }



        if (e.getSource() == logOut) {
            this.dispose();
            new HomePage();
        }
    }
}
