package livechat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame implements ActionListener {
    private JLabel heading, messageLbl;
    private JTextArea chatbox;
    private JTextField messageTxt;
    private JButton sendBtn;
    private Container container;
    static ServerSocket ssckt;
    static Socket sckt;
    static DataInputStream dtinpt;
    static DataOutputStream dtotpt;


    public Server() {
        this.setTitle("Server");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToPanel();
        setWindowProperties();
        registerListeners();
        waitForConnection();
    }


    public void waitForConnection() {
        try {
            ssckt = new ServerSocket(8888);
            sckt = ssckt.accept();
            this.connectServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void connectServer(){
        try {
        String msgin = "";
        dtinpt = new DataInputStream(sckt.getInputStream());
        dtotpt = new DataOutputStream(sckt.getOutputStream());
        while (!msgin.equals("exit")) {
            msgin = dtinpt.readUTF();
            chatbox.setText(chatbox.getText().trim() + "\n Client:" + msgin);
        }
        } catch (Exception e) {
        }
    }


    public void initializeComponents() {
        heading = new JLabel("Server");
        heading.setBounds(50, 20, 100, 26);

        chatbox = new JTextArea();
        chatbox.setBounds(50, 50, 480, 300);
        chatbox.setWrapStyleWord(true);
        chatbox.setLineWrap(true);

        messageLbl = new JLabel("Type your Message Here: ");
        messageLbl.setBounds(50, 350, 200, 26);

        messageTxt = new JTextField();
        messageTxt.setBounds(50, 380, 370, 30);

        sendBtn = new JButton("Send");
        sendBtn.setBounds(480, 380, 80, 30);
    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(chatbox);
        container.add(messageLbl);
        container.add(messageTxt);
        container.add(sendBtn);
    }

    private void setWindowProperties() {
        this.setSize(600, 475);
        this.setVisible(true);
    }

    public void registerListeners() {
        sendBtn.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendBtn) {

//                waitForConnection();
                String msgout = "";
                msgout = messageTxt.getText().trim();
            try {
                dtotpt.writeUTF(msgout);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            messageTxt.setText(null);
            }

        }

    }


