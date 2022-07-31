import java.util.Date;

public class User {

  String name;
  String occupation;
  int SIN;
  Date dob;

  public void setSIN(int SIN) {
    this.SIN = SIN;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public int getSIN() {
    return SIN;
  }

  public String getOccupation() {
    return occupation;
  }

  public String getName() {
    return name;
  }

  public Date getDob() {
    return dob;
  }


}
