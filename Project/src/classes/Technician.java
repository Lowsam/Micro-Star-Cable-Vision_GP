package classes;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "customerdb")
public class Technician extends User{
    //@Id
    String techID;

    //Getter and Setter for Tech ID
    public String getTechID() {
        return techID;
    }

    public void setTechID(String techID) {
        this.techID = techID;
    }
}
