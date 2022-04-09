package classes;

import database.DBConnection;

import java.sql.*;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "approject")
public class Customer extends User {
    //@ID
    String cusid;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Customer() {
        try {
            this.connection = new DBConnection().getDatabaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getters and Setters for Customer ID
    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }


}
