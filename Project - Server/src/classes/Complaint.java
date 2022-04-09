package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;
//
//@Table(name="complaint")
//@Entity
public class Complaint {
    //@Id
    private String complaintId;
    private String category;
    private String date;
    private String email;
    private String contactNo;
    private String details;
    private String status;
    private String techId;
    private String response;

    public Complaint() {
        super();
    }

    //Primary Constructor
    public Complaint(String category, String date, String email, String contactNo, String details) {
        this.category = category;
        this.date = date;
        this.email = email;
        this.contactNo = contactNo;
        this.details = details;
    }

    //Getters and Setters for Complaint
    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTechId() {
        return techId;
    }

    public void setTechId(String techId) {
        this.techId = techId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Complaint [ Complaint ID: " + complaintId + " / Category: " + category + "/ Status: " + status
                + "/ Technician ID: " + techId + "/ Date:" + date + "/ Email: " + email + "/ Contact Number: "
                + contactNo + "/ Details: " + details + "/ Response: " + response + "]";
    }

//    public boolean sendToDatabase() {
//        Connection connection = null;
//        try {
//            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
//                    "root", "");
//            String sql = "INSERT INTO complaint (category, date, email, contactNo, details) VALUES (?,?,?,?,?)";
//            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
//            preparedStatement.setString(1, this.getCategory());
//            preparedStatement.setString(2, this.getDate());
//            preparedStatement.setString(3, this.getEmail());
//            preparedStatement.setString(4, this.getContactNo());
//            preparedStatement.setString(5, this.getDetails());
//
//            preparedStatement.execute();
//            connection.close();
//
//            return true;
//        } catch (SQLException s) {
//            s.printStackTrace();
//        }
//        return false;
//    }
}
