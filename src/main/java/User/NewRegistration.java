package User;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import Main.Main;
public class NewRegistration {

  private static Scanner scan = new Scanner(System.in);
  private static String username = null, password = null;
  private static String firstName = null, lastName = null;
  private static String zipcode = null;
  private static Integer SIN = null;
  private static String dob = null;
  private static String payment_info = null;
  private static String occupation = null;
  private static String apt_name = null, city = null, country = null;
  private static HashMap<Integer, String> mapUserInfo = new HashMap<>();

  public static void newRegistrationScreenBanner() {
    System.out.println("\t\t\t\t\t\t  New User Account Registration Page\n");
  }

  public static void createNewUserAccount(Boolean Renter) {
    newRegistrationScreenBanner();
    System.out.print("username: ");
    username = scan.nextLine();
    mapUserInfo.put(1, username);
    System.out.print("password: ");
    password = scan.nextLine();
    mapUserInfo.put(2, password);

    System.out.print("First Name: ");
    firstName = scan.nextLine();
    mapUserInfo.put(3, firstName);
    System.out.print("Last Name: ");
    lastName = scan.nextLine();
    mapUserInfo.put(4, lastName);

    System.out.print("SIN: ");
    SIN = scan.nextInt();
    scan.nextLine();
    mapUserInfo.put(5, String.valueOf(SIN));

    System.out.print("Occupation: ");
    occupation = scan.nextLine();
    mapUserInfo.put(6, occupation);
    System.out.print("Date of Birth (YYYY-MM-DD): ");
    dob = scan.nextLine();
    mapUserInfo.put(7, dob);

    System.out.print("Apartment Number/Street: ");
    apt_name = scan.nextLine();
    mapUserInfo.put(8, apt_name);
    System.out.print("City: ");
    city = scan.nextLine();
    mapUserInfo.put(9, city);
    System.out.print("Country: ");
    country = scan.nextLine();
    mapUserInfo.put(10, country);
    System.out.print("Zipcode: ");
    zipcode = scan.nextLine(); scan.nextLine();
    mapUserInfo.put(11, String.valueOf(zipcode));
    System.out.print(mapUserInfo);

    if (Renter) {
      System.out.print("Kindly enter your credit card details\n");
      System.out.print("Credit card(DDDD-DDDD): \n");
      payment_info= scan.nextLine();
      mapUserInfo.put(12, payment_info);
    }

    redirectRegisterOptions(Renter);
  }

  public static void redirectRegisterOptions(Boolean Renter) {
    Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
    try {
      Statement stmt1 = connection.createStatement();
      System.out.println();
      // inserting into user table
      String insertIntoUserTbl = "INSERT into User(SIN, firstname, lastname, occupation, dob, username, password) values ("
              + "'" + SIN + "'" + "," + "'" + firstName + "'" + "," + "'" + lastName + "'" + "," + "'" + occupation + "'" + "," + "'" +
              dob + "'" + "," + "'" + username + "'" + "," + "'" + password + "'" + ")\n";
      System.out.println(insertIntoUserTbl);
      stmt1.executeUpdate(insertIntoUserTbl);

      if (Renter) {
        String insertIntoRenterTbl = "INSERT into Renter(SIN, payment) values ("
                + "'" + SIN + "'" + "," + "'" + payment_info + "'" + ")\n";
        System.out.println(insertIntoRenterTbl);
        stmt1.executeUpdate(insertIntoRenterTbl);
      }

      else {
        String insertIntoHostTbl = "INSERT into Host(SIN) values (" + "'" + SIN + "'" + ")\n";

        System.out.println(insertIntoHostTbl);
        stmt1.executeUpdate(insertIntoHostTbl);
      }

      // inserting address
      String insertIntoAddress = "INSERT into Address(postal_code, city, country, apt_name\n) "
              + "values(" + "'" + zipcode + "'" + "," + "'" + city + "'" + "," + "'" + country + "'" + "," + "'" + apt_name +  "'" + ")";
      System.out.println(insertIntoAddress);
      stmt1.executeUpdate(insertIntoAddress);


      System.out.println("Gathering the information...");
      Thread.sleep(200);
      System.out.println("Registering the user...");
      Thread.sleep(200);
      System.out.println("\n\n" + username + " Account is registered successfully...");
      System.out.println("Redirecting to the Main LogIn Page...");
      Thread.sleep(2000);
      Main.getInput();

    } catch (SQLException | InterruptedException e) {
      System.out.println("New registration of user failed...");
      e.printStackTrace();
    }
  }


}
