package database;

import classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDatabase extends JFrame {
    private JDesktopPane desktop;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem menuInsert;


    public CustomerDatabase() {
        initializeComponents();
        addMenuItemsToMenu();
        addMenusToMenuBar();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        desktop = new JDesktopPane();
        menuBar = new JMenuBar();
        file = new JMenu("File");
        menuInsert = new JMenuItem("Customer Database");

    }

    private void addMenuItemsToMenu() {
        file.add(menuInsert);

    }

    private void addMenusToMenuBar() {
        menuBar.add(file);
    }

    private void addComponentsToWindow() {
        this.add(desktop);
    }

    private void setWindowProperties() {
        this.setJMenuBar(menuBar);
        this.setSize(1020, 700);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void registerListeners() {
        menuInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new User();
            }
        });


    }
}