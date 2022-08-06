package Review;
import User.*;
import Listing.*;
import ConnectionEstablish.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Review {

  private static Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static Scanner scan = new Scanner(System.in);

  private static Statement st;
  private static ResultSet rs;
  private static int host_ID;
  private static int renter_ID;

  public static void reviewRenter() throws SQLException {

    System.out.print("Specify the Renter_id of the renter that you want to review\n");



  }

}
