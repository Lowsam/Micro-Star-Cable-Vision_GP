package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewServices extends JInternalFrame {
    private JLabel heading, resolvedLbl, outstandingLbl, rCountLbl, oCountLbl,
            missingLbl, broadbandLbl, noConnLbl, missingCountLbl, broadbandCountLbl, noConnCountLbl;
    private Container container;

    public ViewServices() {
        super("", true, true, false, false);
        this.setTitle("View Services");
        this.setLayout(null);
        container = getContentPane();
        this.container.setBackground(new Color(0x93E9BE));
        initializeComponents();
        addComponentsToPanel();
        setWindowProperties();
        statusCounter();


    }

    public void initializeComponents() {
        heading = new JLabel("VIEW SERVICES");
        heading.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        heading.setBounds(375, 0, 440, 45);

        resolvedLbl = new JLabel("Resolved");
        resolvedLbl.setBounds(80, 130, 200, 40);
        resolvedLbl.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        outstandingLbl = new JLabel("Outstanding");
        outstandingLbl.setBounds(740, 130, 230, 40);
        outstandingLbl.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 35)));
        missingLbl = new JLabel("Missing Channels");
        missingLbl.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 25)));
        missingLbl.setBounds(80, 330, 250, 40);
        broadbandLbl = new JLabel("BroadBand");
        broadbandLbl.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 25)));
        broadbandLbl.setBounds(440, 330, 200, 40);
        noConnLbl = new JLabel("No Connection");
        noConnLbl.setFont((new Font("Georgia", Font.LAYOUT_RIGHT_TO_LEFT, 25)));
        noConnLbl.setBounds(740, 330, 200, 40);

        rCountLbl = new JLabel("0");
        rCountLbl.setFont(new Font("Georgia", Font.CENTER_BASELINE, 40));
        rCountLbl.setBounds(80, 200, 180, 80);
        rCountLbl.setOpaque(true);
        rCountLbl.setBackground(Color.white);
        oCountLbl = new JLabel("0");
        oCountLbl.setFont(new Font("Georgia", Font.CENTER_BASELINE, 40));
        oCountLbl.setBounds(740, 200, 220, 80);
        oCountLbl.setOpaque(true);
        oCountLbl.setBackground(Color.white);
        missingCountLbl = new JLabel("0");
        missingCountLbl.setFont(new Font("Georgia", Font.CENTER_BASELINE, 40));
        missingCountLbl.setBounds(80, 400, 230, 80);
        missingCountLbl.setOpaque(true);
        missingCountLbl.setBackground(Color.white);
        broadbandCountLbl = new JLabel("0");
        broadbandCountLbl.setFont(new Font("Georgia", Font.CENTER_BASELINE, 40));
        broadbandCountLbl.setBounds(440, 400, 150, 80);
        broadbandCountLbl.setOpaque(true);
        broadbandCountLbl.setBackground(Color.white);
        noConnCountLbl = new JLabel("0");
        noConnCountLbl.setFont(new Font("Georgia", Font.CENTER_BASELINE, 40));
        noConnCountLbl.setBounds(740, 400, 190, 80);
        noConnCountLbl.setOpaque(true);
        noConnCountLbl.setBackground(Color.white);


    }

    private void addComponentsToPanel() {
        container.add(heading);
        container.add(resolvedLbl);
        container.add(outstandingLbl);
        container.add(missingLbl);
        container.add(broadbandLbl);
        container.add(noConnLbl);

        container.add(rCountLbl);
        container.add(oCountLbl);
        container.add(missingCountLbl);
        container.add(broadbandCountLbl);
        container.add(noConnCountLbl);
    }

    private void setWindowProperties() {
        this.setSize(1050, 600);
        this.setVisible(true);
    }



    public void statusCounter() {
        int outstanding;
        int resolved;
        int missing;
        int broadband;
        int noConnection;


        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/CustomerDb", "root", "password");
            PreparedStatement prepStat = connection.prepareStatement("Select Count(status) from complaints where NOT status='Resolved'");
            ResultSet rs = prepStat.executeQuery();
            if (rs.next()) {
                outstanding = rs.getInt(1);
                oCountLbl.setText(String.valueOf(outstanding));
            }
            PreparedStatement pStat = connection.prepareStatement("Select Count(status) from complaints where status='Resolved'");
            ResultSet resultSet = pStat.executeQuery();
            if (resultSet.next()) {
                resolved = resultSet.getInt(1);
                rCountLbl.setText(String.valueOf(resolved));
            }
            PreparedStatement pS1 = connection.prepareStatement("Select Count(complaint_type) from complaints where complaint_type='Missing Channels'");
            ResultSet rs1 = pS1.executeQuery();
            if (rs1.next()) {
                missing = rs1.getInt(1);
                missingCountLbl.setText(String.valueOf(missing));
            }
            PreparedStatement pS2 = connection.prepareStatement("Select Count(complaint_type) from complaints where complaint_type='Broadband'");
            ResultSet rs2 = pS2.executeQuery();
            if (rs2.next()) {
                broadband = rs2.getInt(1);
               broadbandCountLbl.setText(String.valueOf(broadband));
            }
            PreparedStatement pS3 = connection.prepareStatement("Select Count(complaint_type) from complaints where complaint_type='No Connection'");
            ResultSet rs3 = pS3.executeQuery();
            if (rs3.next()) {
                noConnection = rs2.getInt(1);
                noConnCountLbl.setText(String.valueOf(noConnection));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
