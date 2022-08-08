package Reports;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class Report {

  public static int Listing_ID = 0;
  private static int Booking_ID;
  private static int Host_ID, Renter_ID;
  private static Connection connection = ConnectionEstablish.ConnectToJDBC.getMySqlConnection();
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private static Scanner scan = new Scanner(System.in);
  private static Statement st;
  private static ResultSet rs;
  private static String zipcode = null;
  private static String room_type = null;
  private static int type;
  private static String apt_name = null, city = null, country = null;
  private static int longitude;
  private static int latitude;
  public static int price;
  public static String startDate;
  public static String endDate;
  private static int wifi, washer, ac, kitchen, dryer;
  private static String[] room_types = {"Apartment", "House", "Room"};

  /*
  Total number of bookings in a specific date range by city.
Total number of bookings in a specific date range by postal code within a city.
Total number of listings per country, per country and city, per country, city and postal code.
Rank the hosts by the total number of listings they have overall per country.
Rank the hosts by the total number of listings they have overall per city.
For every city and country a report should provide the hosts that have a number of listings that is more than 10% of the number of listings in that city and country.
Rank the renters by the number of bookings in a specific time period.
Rank the renters by the number of bookings in a specific time period per city.(renters that have made at least 2 booking in the year).
Hosts and renters with the largest number of cancellations within a year.
A report that presents for each listing the set of most popular noun phrases associated with the listing (noun phrases from review).
   */

  public static void dateRangeAndCity() throws SQLException, InterruptedException {
    System.out.print("Specify the date range\n");
    System.out.print("Start Date (YYYY-MM-DD): ");
    startDate = scan.nextLine();
    System.out.print("End Date (YYYY-MM-DD): ");
    endDate = scan.nextLine();

    System.out.print("Specify city ");
    city = scan.nextLine();



  }
  }
