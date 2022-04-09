package livechat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame implements ActionListener {
    private JLabel heading, messageLbl;
    private JTextArea chatbox;
    private JTextField messageTxt;
    private JButton sendBtn;
    private Container container;
    static Socket sckt;
    static DataInputStream dtinpt;
    static DataOutputStream dtotpt;

    public Client() {
        this.setTitle("Client");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        createConnection();
    }

    public void createConnection() {
        try {
            initializeComponents();
            addComponentsToPanel();
            setWindowProperties();
            registerListeners();
            sckt = new Socket("127.0.0.1", 8888);
            dtinpt = new DataInputStream(sckt.getInputStream());
            dtotpt = new DataOutputStream(sckt.getOutputStream());
            String msgin = "";
            while (!msgin.equals("Exit")) {
                msgin = dtinpt.readUTF();
                chatbox.setText(chatbox.getText().trim() + "\n Server:" + msgin);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void initializeComponents() {
        heading = new JLabel("Client");
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
//            createConnection();
            try {
                String msgout = "";
                msgout = messageTxt.getText().trim();
                dtotpt.writeUTF(msgout);

                messageTxt.setText(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
