package dashboard;

import authentication.HomePage;
import authentication.WelcomeUser;
import database.CustomerDatabase;
import livechat.Server;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class RepresentativeDashboard extends JFrame implements ActionListener {
    private Container container;
    private JLabel heading, welcomeUserLbl;
    private JButton chat, viewServices, viewComplaint, viewCustomer, respondBtn, assignTech, editCustomers, logOut;
    private JDesktopPane desktop;

    public RepresentativeDashboard() {
        this.setTitle("Representative Dashboard");
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
        heading = new JLabel("Representative Dashboard");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(80, 0, 480, 45);
        String name = WelcomeUser.jtx.getText();
        welcomeUserLbl = new JLabel("Welcome Representative: " + name);
        welcomeUserLbl.setBounds(960, 20, 300, 28);
        welcomeUserLbl.setFont(new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 16));

        viewServices = new JButton("View Services");
        viewServices.setBounds(50, 60, 160, 28);
        viewServices.setMnemonic(KeyEvent.VK_1);
        viewComplaint = new JButton("View Complaint");
        viewComplaint.setBounds(50, 180, 160, 28);
        viewComplaint.setMnemonic(KeyEvent.VK_2);
        viewCustomer = new JButton("View Customer");
        viewCustomer.setBounds(50, 300, 160, 28);
        viewCustomer.setMnemonic(KeyEvent.VK_3);
        respondBtn = new JButton("Respond to Customer");
        respondBtn.setBounds(50, 420, 160, 28);
        respondBtn.setMnemonic(KeyEvent.VK_4);
        assignTech = new JButton("Assign Technician");
        assignTech.setBounds(50, 540, 160, 28);
        assignTech.setMnemonic(KeyEvent.VK_5);
        editCustomers = new JButton("Edit Database");
        editCustomers.setBounds(50,660,160,28);
        editCustomers.setMnemonic(KeyEvent.VK_6);
        logOut = new JButton("Log Out");
        logOut.setBounds(1350, 20, 100, 28);
        logOut.setMnemonic(KeyEvent.VK_BACK_QUOTE);
        ImageIcon icon = new ImageIcon("chat-now-icon-png-11553722060rg3urfboym.png");
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(30,28,Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        chat = new JButton(icon);
        chat.setBounds(1300,20,30,28);

        desktop = new JDesktopPane();
        desktop.setBounds(400, 60, 1050, 600);
    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(welcomeUserLbl);
        container.add(viewServices);
        container.add(viewComplaint);
        container.add(viewCustomer);
        container.add(respondBtn);
        container.add(assignTech);
        container.add(editCustomers);
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
        viewServices.addActionListener(this);
        viewComplaint.addActionListener(this);
        viewCustomer.addActionListener(this);
        respondBtn.addActionListener(this);
        assignTech.addActionListener(this);
        editCustomers.addActionListener(this);
        logOut.addActionListener(this);
        chat.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewServices) {
            desktop.add(new ViewServices());
        }
        if (e.getSource() == viewComplaint) {
            desktop.add(new ViewComplaints());
        }
        if (e.getSource() == viewCustomer) {
            desktop.add(new ViewCustomer());
        }
        if (e.getSource() == respondBtn) {
            desktop.add(new RespondToCustomer());
        }
        if (e.getSource() == assignTech) {
            desktop.add(new AssignTechnician());
        }
        if (e.getSource() == editCustomers){
            new CustomerDatabase();
        }
        if (e.getSource() == chat){
            new Server();
        }

        if (e.getSource() == logOut) {
            this.dispose();
            new HomePage();
        }
    }
}
