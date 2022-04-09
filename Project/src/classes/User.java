package classes;

import database.DBConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

//mport javax.persistence.Entity;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "customerdb")
public class User {
    String password = "";
    String id = "";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private CustomerView customerView;

    //Default Constructor
    public User() {
        try {
            this.connection = new DBConnection().getDatabaseConnection();
            this.customerView = new CustomerView(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getters and Setters for User
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String type) {
        this.id = id;
    }

    //To String Method
    @Override
    public String toString() {
        return "User's Password: " + password + "\n" +
                "ID Number: " + id;
    }

    //INSERT
    public boolean insert(String password, String id) throws SQLException {
        String insertSql = "INSERT INTO Customers (Customer_ID, Password) VALUES(?,?)";
        PreparedStatement prepStat = connection.prepareStatement(insertSql);
        prepStat.setString(1, password);
        prepStat.setString(2, id);

        prepStat.executeUpdate();
        return false;
    }

    //RETRIEVE
    public DefaultTableModel getData() throws SQLException {
        DefaultTableModel databaseTable = new DefaultTableModel();
        databaseTable.addColumn("Customer ID");
        databaseTable.addColumn("Password");

        String selectSql = "SELECT * FROM Customers";
        statement = connection.prepareStatement(selectSql);
        resultSet = statement.executeQuery(selectSql);

        while (resultSet.next()){

            String id = resultSet.getString(1);
            String password = resultSet.getString(2);


            databaseTable.addRow(new String[]{id,password});
        }
        return databaseTable;
    }

    //UPDATE
    public boolean update(String password, String id) throws SQLException {
        String updateSql = "UPDATE Customers SET Password = ? WHERE Customer_ID = ?";

        PreparedStatement prepStat = connection.prepareStatement(updateSql);
        prepStat.setString(1, password);
        prepStat.setString(2, id);


        int rowsUpdated = prepStat.executeUpdate();
        if (rowsUpdated > 0){

        }
        return false;
    }

    //DELETE
    public boolean delete(String id) throws SQLException {
        String deleteSql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement prepStat = connection.prepareStatement(deleteSql);
        prepStat.setString(1, id);

        int rowsDeleted = prepStat.executeUpdate();

        if (rowsDeleted > 0) {

        }
        return false;
    }




}
