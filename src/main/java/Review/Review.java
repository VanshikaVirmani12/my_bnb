package Review;
import User.LoginPage;
import ConnectionEstablish.*;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Review {

  private static Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static Scanner scan = new Scanner(System.in);

  public static Statement st;
  public static ResultSet rs;
  public static int host_ID;
  public static int Booking_ID;
  public static int renter_ID;
  public static String comment;
  public static int rating;


  public static void reviewRenter() throws SQLException {
    viewPastBookingsForHost();

    System.out.print("Specify the Booking_id of the booking that you want to review the renter for\n");
    Booking_ID = scan.nextInt();
    scan.nextLine();

    System.out.print("Write a review or press enter to skip\n");
    comment = scan.nextLine();

    System.out.print("Give a rating between 1 to 5 or press enter to skip\n");
    rating = scan.nextInt();
    scan.nextLine();

    String sqlQ;
    sqlQ = "SELECT *\n" +
            "FROM Bookings\n" +
            "WHERE booking_ID=" + Booking_ID + "\n";
    System.out.println(sqlQ);
    ResultSet rs = st.executeQuery(sqlQ);

    while(rs.next()){
      renter_ID = rs.getInt("renter_ID");
    }

    // for each listing_id you own, what are the bookings associated with it?
    host_ID = LoginPage.getSIN();

    sqlQ = "INSERT INTO Review (booking_ID, host_ID, renter_ID, comment, rating, host_to_renter, " +
            "renter_to_host, renter_to_listing) VALUES (" +
            Booking_ID + ", " + host_ID + ", " + renter_ID + ", '" + comment + "', " + rating + ", 1, 0, 0)\n";

    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

  }


  public static void viewPastBookingsForHost() throws SQLException {
    System.out.print("Here are all the bookings in the past 30 days for your listings\n");

    st = connection.createStatement();
    int SIN = User.LoginPage.getSIN();
    // for each listing_id you own, what are the bookings associated with it?

    String sqlQ;
    sqlQ = "SELECT b.booking_ID, b.listing_ID, b.renter_ID, b.start, b.end, b.completed\n" +
            "FROM Bookings b JOIN owns o ON b.listing_ID = o.listing_ID\n" +
            "WHERE SIN=" + SIN + " AND b.completed=1\n";
    System.out.println(sqlQ);
    ResultSet rs = st.executeQuery(sqlQ);

    Date start, end;
    String c = "No";
    int listing_ID, renter_ID, completed, booking_ID;

    while (rs.next()) {
      booking_ID = rs.getInt("booking_ID");
      listing_ID = rs.getInt("listing_ID");
      renter_ID = rs.getInt("renter_ID");
      start = rs.getDate("start");
      end = rs.getDate("end");
      completed = rs.getInt("completed");
      if (completed == 1) {
        c = "Yes";
      } else {
        c = "No";
      }
      System.out.println("Booking ID = " + booking_ID);
      System.out.println("Listing ID = " + listing_ID);
      System.out.println("Renter ID = " + renter_ID);
      System.out.println("Start date of Booking = " + start);
      System.out.println("End date of Booking = " + end);
      System.out.println("Booking completed = " + c);
      System.out.println("------------------------------------\n");

    }
  }
    public static void viewPastBookingsForRenter() throws SQLException {
      System.out.print("Here are all the bookings that you have completed in the past 30 days\n");

      st = connection.createStatement();
      renter_ID = User.LoginPage.getSIN();
      // for each listing_id you rented, what are the bookings associated with it?

      String sqlQ;
      sqlQ = "SELECT *\n" +
              "FROM Bookings b INNER JOIN rents r ON b.booking_ID = r.booking_ID\n" +
              "WHERE r.SIN=" + renter_ID + " AND b.completed = 1\n";
      System.out.println(sqlQ);
      rs = st.executeQuery(sqlQ);
      Date start, end;

      String c = "No";
      int listing_ID, renter_ID, completed, booking_ID;

      while (rs.next()) {
        booking_ID = rs.getInt("booking_ID");
        listing_ID = rs.getInt("listing_ID");
        renter_ID = rs.getInt("renter_ID");
        start = rs.getDate("start");
        end = rs.getDate("end");
        completed = rs.getInt("completed");
        if (completed == 1) {
          c = "Yes";
        } else {
          c = "No";
        }
        System.out.println("Booking ID = " + booking_ID);
        System.out.println("Listing ID = " + listing_ID);
        System.out.println("Renter ID = " + renter_ID);
        System.out.println("Start date of Booking = " + start);
        System.out.println("End date of Booking = " + end);
        System.out.println("Booking completed = " + c);
        System.out.println("------------------------------------\n");

      }

  }

  public static void reviewHost() throws SQLException {
      viewPastBookingsForRenter();

      System.out.print("Specify the Booking_id of the booking that you want to review the Host for\n");
      Booking_ID = scan.nextInt();
      scan.nextLine();

      System.out.print("Write a review or press enter to skip\n");
      comment = scan.nextLine();

      System.out.print("Give a rating between 1 to 5 or press enter to skip\n");
      rating = scan.nextInt();
      scan.nextLine();

      String sqlQ;
      sqlQ = "SELECT *\n" +
              "FROM Bookings b JOIN owns o ON b.listing_ID=o.listing_ID\n" +
              "WHERE booking_ID=" + Booking_ID + "\n";
      System.out.println(sqlQ);
      ResultSet rs = st.executeQuery(sqlQ);

      while(rs.next()){
        host_ID = rs.getInt("SIN");
      }

      // for each listing_id you own, what are the bookings associated with it?
      renter_ID = LoginPage.getSIN();

      sqlQ = "INSERT INTO Review (booking_ID, host_ID, renter_ID, comment, rating, host_to_renter, " +
              "renter_to_host, renter_to_listing) VALUES (" +
              Booking_ID + ", " + host_ID + ", " + renter_ID + ", '" + comment + "', " + rating + ", 0, 1, 0)\n";

      System.out.println(sqlQ);
      st.executeUpdate(sqlQ);

    }

  public static void reviewListing() throws SQLException {
    viewPastBookingsForRenter();

    System.out.print("Specify the Booking_id of the booking that you want to review the Listing for\n");
    Booking_ID = scan.nextInt();
    scan.nextLine();

    System.out.print("Write a review or press enter to skip\n");
    comment = scan.nextLine();

    System.out.print("Give a rating between 1 to 5 or press enter to skip\n");
    rating = scan.nextInt();
    scan.nextLine();

    String sqlQ;
    sqlQ = "SELECT *\n" +
            "FROM Bookings b JOIN owns o ON b.listing_ID=o.listing_ID\n" +
            "WHERE booking_ID=" + Booking_ID + "\n";
    System.out.println(sqlQ);
    ResultSet rs = st.executeQuery(sqlQ);

    renter_ID = LoginPage.getSIN();

    while(rs.next()){
      host_ID = rs.getInt("SIN");
    }

    sqlQ = "INSERT INTO Review (booking_ID, host_ID, renter_ID, comment, rating, host_to_renter, " +
            "renter_to_host, renter_to_listing) VALUES (" +
            Booking_ID + ", " + host_ID + ", " + renter_ID + ", '" + comment + "', " + rating + ", 0, 0, 1)\n";

    System.out.println(sqlQ);
    st.executeUpdate(sqlQ);

  }

  }

