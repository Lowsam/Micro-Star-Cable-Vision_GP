package classes;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "customerdb")
public class Representative extends User {
    //@Id
    String repID;

    //Getter and Setter for Rep ID
    public String getRepID() {
        return repID;
    }

    public void setRepID(String repID) {
        this.repID = repID;
    }
}
