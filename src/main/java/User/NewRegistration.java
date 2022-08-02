package user;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import ConnectionEstablish.ConnectToJDBC;

import main.Main;
import main.Main;

public class NewRegistration {

  private static Scanner scan = new Scanner(System.in);
  private static String username = null, password = null;
  private static String firstName = null, lastName = null;
  private static String zipcode = null;
  private static Integer SIN = null;
  private static Date dob = null;
  private static String occupation = null;
  private static String apt_name = null, city = null, country = null;
  private static HashMap<Integer, String> mapUserInfo = new HashMap<>();

  public static void newRegistrationScreenBanner() {
    System.out.println("\t\t\t\t\t\t  New User Account Registration Page\n");
  }

  public static void createNewUserAccount() {
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

    System.out.print("Apartment Number/Street: ");
    apt_name = scan.nextLine();
    mapUserInfo.put(6, apt_name);
    System.out.print("City: ");
    city = scan.nextLine();
    mapUserInfo.put(7, city);
    System.out.print("Country: ");
    country = scan.nextLine();
    mapUserInfo.put(8, country);
    System.out.print("Zipcode: ");
    zipcode = scan.nextLine(); scan.nextLine();
    mapUserInfo.put(9, String.valueOf(zipcode));
    System.out.print(mapUserInfo);
    redirectRegisterOptions();
  }

  public static void redirectRegisterOptions() {
    int userId = user.LoginPage.getValidatedUserId();
    Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
    try {
      Statement stmt1 = connection.createStatement();
      System.out.println();
      // inserting into user table
      String insertIntoUserTbl = "INSERT into User(SIN, firstname, lastname, occupation, dob, username, password) values ("
              + "'" + SIN + "'" + "," + "'" + firstName + "'" + "," + "'" + lastName + "'" + "," + "'" + occupation + "'" + "," + "'" +
              dob + "'" + "," + "'" + username + "'" + "," + "'" + password + "'" + ")";
      System.out.println(insertIntoUserTbl);
      stmt1.executeUpdate(insertIntoUserTbl);

      // inserting address
      String insertIntoAddress = "INSERT into Address(postal_code, city, country, apt_name) "
              + "values(" + "'" + zipcode + "'" + "," + "'" + city + "'" + "," + "'" + country + "'" + "," + apt_name + ")";
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
