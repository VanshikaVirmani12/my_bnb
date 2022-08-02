package User;
import Main.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ConnectionEstablish.ConnectToJDBC;

public class LoginPage {

  private static Connection connection= ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
  private static Statement st;
  private static ResultSet rs;
  static Scanner scan = new Scanner(System.in);
  private static String username = null;
  private static String password = null;
  public static boolean isValidUser;

  public static void logInScreenBanner() {
    System.out.println("\t\t\t\t\t\t  LogIn Page");
    System.out.println();
  }

  public static void getUserCredentials() throws SQLException {
    logInScreenBanner();
    System.out.print("Input UserName: " );
    username = scan.nextLine();
    System.out.print("Input Password: ");
    password = scan.nextLine();
    try {
      validateUser();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  public static void validateUser() throws SQLException, InterruptedException {
    st = connection.createStatement();

    String getAllUserQuery = "SELECT * FROM User";
    rs = st.executeQuery(getAllUserQuery);

    System.out.println("\n\nValidating the user...");

    while(rs.next()) {
      if( username.equals(rs.getString("username"))
              && password.equals(rs.getString("password")) ){
        isValidUser = true;
        break;
      }else {
        isValidUser = false;
      }
    }

    if(!isValidUser) {
      System.out.println("Invalid Username or Password!!!\nTry Again!!!");
      System.out.println("Redirecting to LogIn Page menu....");
      Thread.sleep(2000);
      Main.clearScreen();
      Main.welcomeScreenBanner();
      Main.getInput();
    }else {
      //User.UserLogDirectory.userLogIn();
      System.out.println("Login successfull....");
      //Thread.sleep(500);

      System.out.println("Redirecting to view/transaction menu....");
      Thread.sleep(500);

      System.out.println("Initiating DB environment values...");
      //ConnectionEstablish.MySqlEnvSetup.mySqlEnvInitialize();
      Thread.sleep(1000);
      Main.selectFilter();
    }
  }


}

